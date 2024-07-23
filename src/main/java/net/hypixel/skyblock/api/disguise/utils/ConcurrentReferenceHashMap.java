package net.hypixel.skyblock.api.disguise.utils;

import java.lang.ref.WeakReference;
import java.lang.ref.SoftReference;
import java.lang.ref.ReferenceQueue;
import java.util.NoSuchElementException;
import java.util.Iterator;
import java.util.AbstractSet;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.Arrays;
import java.lang.reflect.Array;
import org.jetbrains.annotations.Nullable;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.AbstractMap;

public class ConcurrentReferenceHashMap<K, V> extends AbstractMap<K, V> implements ConcurrentMap<K, V>
{
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    private static final int DEFAULT_CONCURRENCY_LEVEL = 16;
    private static final ReferenceType DEFAULT_REFERENCE_TYPE;
    private static final int MAXIMUM_CONCURRENCY_LEVEL = 65536;
    private static final int MAXIMUM_SEGMENT_SIZE = 1073741824;
    private final Segment[] segments;
    private final float loadFactor;
    private final ReferenceType referenceType;
    private final int shift;
    @Nullable
    private volatile Set<Map.Entry<K, V>> entrySet;
    
    public ConcurrentReferenceHashMap() {
        this(16, 0.75f, 16, ConcurrentReferenceHashMap.DEFAULT_REFERENCE_TYPE);
    }
    
    public ConcurrentReferenceHashMap(final int initialCapacity) {
        this(initialCapacity, 0.75f, 16, ConcurrentReferenceHashMap.DEFAULT_REFERENCE_TYPE);
    }
    
    public ConcurrentReferenceHashMap(final int initialCapacity, final float loadFactor) {
        this(initialCapacity, loadFactor, 16, ConcurrentReferenceHashMap.DEFAULT_REFERENCE_TYPE);
    }
    
    public ConcurrentReferenceHashMap(final int initialCapacity, final int concurrencyLevel) {
        this(initialCapacity, 0.75f, concurrencyLevel, ConcurrentReferenceHashMap.DEFAULT_REFERENCE_TYPE);
    }
    
    public ConcurrentReferenceHashMap(final int initialCapacity, final ReferenceType referenceType) {
        this(initialCapacity, 0.75f, 16, referenceType);
    }
    
    public ConcurrentReferenceHashMap(final int initialCapacity, final float loadFactor, final int concurrencyLevel) {
        this(initialCapacity, loadFactor, concurrencyLevel, ConcurrentReferenceHashMap.DEFAULT_REFERENCE_TYPE);
    }
    
    public ConcurrentReferenceHashMap(final int initialCapacity, final float loadFactor, final int concurrencyLevel, final ReferenceType referenceType) {
        Assert.isTrue(initialCapacity >= 0, "Initial capacity must not be negative");
        Assert.isTrue(loadFactor > 0.0f, "Load factor must be positive");
        Assert.isTrue(concurrencyLevel > 0, "Concurrency level must be positive");
        Assert.notNull(referenceType, "Reference type must not be null");
        this.loadFactor = loadFactor;
        this.shift = calculateShift(concurrencyLevel, 65536);
        final int size = 1 << this.shift;
        this.referenceType = referenceType;
        final int roundedUpSegmentCapacity = (int)((initialCapacity + size - 1L) / size);
        final int initialSize = 1 << calculateShift(roundedUpSegmentCapacity, 1073741824);
        final Segment[] segments = (Segment[])Array.newInstance((Class)Segment.class, size);
        final int resizeThreshold = (int)(initialSize * this.getLoadFactor());
        for (int i = 0; i < segments.length; ++i) {
            segments[i] = new Segment(initialSize, resizeThreshold);
        }
        this.segments = segments;
    }
    
    protected final float getLoadFactor() {
        return this.loadFactor;
    }
    
    protected final int getSegmentsSize() {
        return this.segments.length;
    }
    
    protected final Segment getSegment(final int index) {
        return this.segments[index];
    }
    
    protected ReferenceManager createReferenceManager() {
        return new ReferenceManager();
    }
    
    protected int getHash(@Nullable final Object o) {
        int hash = (o != null) ? o.hashCode() : 0;
        hash += (hash << 15 ^ 0xFFFFCD7D);
        hash ^= hash >>> 10;
        hash += hash << 3;
        hash ^= hash >>> 6;
        hash += (hash << 2) + (hash << 14);
        hash ^= hash >>> 16;
        return hash;
    }
    
    @Nullable
    public V get(@Nullable final Object key) {
        final Reference<K, V> ref = this.getReference(key, Restructure.WHEN_NECESSARY);
        final Entry<K, V> entry = (ref != null) ? ref.get() : null;
        return (entry != null) ? entry.getValue() : null;
    }
    
    @Nullable
    public V getOrDefault(@Nullable final Object key, @Nullable final V defaultValue) {
        final Reference<K, V> ref = this.getReference(key, Restructure.WHEN_NECESSARY);
        final Entry<K, V> entry = (ref != null) ? ref.get() : null;
        return (entry != null) ? entry.getValue() : defaultValue;
    }
    
    public boolean containsKey(@Nullable final Object key) {
        final Reference<K, V> ref = this.getReference(key, Restructure.WHEN_NECESSARY);
        final Entry<K, V> entry = (ref != null) ? ref.get() : null;
        return entry != null && nullSafeEquals(entry.getKey(), key);
    }
    
    private static boolean nullSafeEquals(@Nullable final Object o1, @Nullable final Object o2) {
        if (o1 == o2) {
            return true;
        }
        if (o1 == null || o2 == null) {
            return false;
        }
        if (o1.equals(o2)) {
            return true;
        }
        if (!o1.getClass().isArray() || !o2.getClass().isArray()) {
            return false;
        }
        if (o1 instanceof Object[] && o2 instanceof Object[]) {
            return Arrays.equals((Object[])o1, (Object[])o2);
        }
        if (o1 instanceof boolean[] && o2 instanceof boolean[]) {
            return Arrays.equals((boolean[])o1, (boolean[])o2);
        }
        if (o1 instanceof byte[] && o2 instanceof byte[]) {
            return Arrays.equals((byte[])o1, (byte[])o2);
        }
        if (o1 instanceof char[] && o2 instanceof char[]) {
            return Arrays.equals((char[])o1, (char[])o2);
        }
        if (o1 instanceof double[] && o2 instanceof double[]) {
            return Arrays.equals((double[])o1, (double[])o2);
        }
        if (o1 instanceof float[] && o2 instanceof float[]) {
            return Arrays.equals((float[])o1, (float[])o2);
        }
        if (o1 instanceof int[] && o2 instanceof int[]) {
            return Arrays.equals((int[])o1, (int[])o2);
        }
        if (o1 instanceof long[] && o2 instanceof long[]) {
            return Arrays.equals((long[])o1, (long[])o2);
        }
        return o1 instanceof short[] && o2 instanceof short[] && Arrays.equals((short[])o1, (short[])o2);
    }
    
    @Nullable
    protected final Reference<K, V> getReference(@Nullable final Object key, final Restructure restructure) {
        final int hash = this.getHash(key);
        return this.getSegmentForHash(hash).getReference(key, hash, restructure);
    }
    
    @Nullable
    public V put(@Nullable final K key, @Nullable final V value) {
        return this.put(key, value, true);
    }
    
    @Nullable
    public V putIfAbsent(@Nullable final K key, @Nullable final V value) {
        return this.put(key, value, false);
    }
    
    @Nullable
    private V put(@Nullable final K key, @Nullable final V value, final boolean overwriteExisting) {
        return this.doTask(key, (Task<V>)new Task<V>(new TaskOption[] { TaskOption.RESTRUCTURE_BEFORE, TaskOption.RESIZE }) {
            @Nullable
            @Override
            protected V execute(@Nullable final Reference<K, V> ref, @Nullable final Entry<K, V> entry, @Nullable final Entries<V> entries) {
                if (entry != null) {
                    final V oldValue = entry.getValue();
                    if (overwriteExisting) {
                        entry.setValue(value);
                    }
                    return oldValue;
                }
                Assert.state(entries != null, "No entries segment");
                entries.add(value);
                return null;
            }
        });
    }
    
    @Nullable
    public V remove(@Nullable final Object key) {
        return this.doTask(key, (Task<V>)new Task<V>(new TaskOption[] { TaskOption.RESTRUCTURE_AFTER, TaskOption.SKIP_IF_EMPTY }) {
            @Nullable
            @Override
            protected V execute(@Nullable final Reference<K, V> ref, @Nullable final Entry<K, V> entry) {
                if (entry != null) {
                    if (ref != null) {
                        ref.release();
                    }
                    return (V)((Entry<Object, Object>)entry).value;
                }
                return null;
            }
        });
    }
    
    public boolean remove(@Nullable final Object key, @Nullable final Object value) {
        final Boolean result = this.doTask(key, (Task<Boolean>)new Task<Boolean>(new TaskOption[] { TaskOption.RESTRUCTURE_AFTER, TaskOption.SKIP_IF_EMPTY }) {
            @Override
            protected Boolean execute(@Nullable final Reference<K, V> ref, @Nullable final Entry<K, V> entry) {
                if (entry != null && nullSafeEquals(entry.getValue(), value)) {
                    if (ref != null) {
                        ref.release();
                    }
                    return true;
                }
                return false;
            }
        });
        return Boolean.TRUE.equals((Object)result);
    }
    
    public boolean replace(@Nullable final K key, @Nullable final V oldValue, @Nullable final V newValue) {
        final Boolean result = this.doTask(key, (Task<Boolean>)new Task<Boolean>(new TaskOption[] { TaskOption.RESTRUCTURE_BEFORE, TaskOption.SKIP_IF_EMPTY }) {
            @Override
            protected Boolean execute(@Nullable final Reference<K, V> ref, @Nullable final Entry<K, V> entry) {
                if (entry != null && nullSafeEquals(entry.getValue(), oldValue)) {
                    entry.setValue(newValue);
                    return true;
                }
                return false;
            }
        });
        return Boolean.TRUE.equals((Object)result);
    }
    
    @Nullable
    public V replace(@Nullable final K key, @Nullable final V value) {
        return this.doTask(key, (Task<V>)new Task<V>(new TaskOption[] { TaskOption.RESTRUCTURE_BEFORE, TaskOption.SKIP_IF_EMPTY }) {
            @Nullable
            @Override
            protected V execute(@Nullable final Reference<K, V> ref, @Nullable final Entry<K, V> entry) {
                if (entry != null) {
                    final V oldValue = entry.getValue();
                    entry.setValue(value);
                    return oldValue;
                }
                return null;
            }
        });
    }
    
    public void clear() {
        for (final Segment segment : this.segments) {
            segment.clear();
        }
    }
    
    public void purgeUnreferencedEntries() {
        for (final Segment segment : this.segments) {
            segment.restructureIfNecessary(false);
        }
    }
    
    public int size() {
        int size = 0;
        for (final Segment segment : this.segments) {
            size += segment.getCount();
        }
        return size;
    }
    
    public boolean isEmpty() {
        for (final Segment segment : this.segments) {
            if (segment.getCount() > 0) {
                return false;
            }
        }
        return true;
    }
    
    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> entrySet = this.entrySet;
        if (entrySet == null) {
            entrySet = (Set<Map.Entry<K, V>>)new EntrySet();
            this.entrySet = entrySet;
        }
        return entrySet;
    }
    
    @Nullable
    private <T> T doTask(@Nullable final Object key, final Task<T> task) {
        final int hash = this.getHash(key);
        return this.getSegmentForHash(hash).doTask(hash, key, task);
    }
    
    private Segment getSegmentForHash(final int hash) {
        return this.segments[hash >>> 32 - this.shift & this.segments.length - 1];
    }
    
    protected static int calculateShift(final int minimumValue, final int maximumValue) {
        int shift = 0;
        for (int value = 1; value < minimumValue && value < maximumValue; value <<= 1, ++shift) {}
        return shift;
    }
    
    static {
        DEFAULT_REFERENCE_TYPE = ReferenceType.SOFT;
    }
    
    public enum ReferenceType
    {
        SOFT, 
        WEAK;
    }
    
    protected final class Segment extends ReentrantLock
    {
        private final ReferenceManager referenceManager;
        private final int initialSize;
        private volatile Reference<K, V>[] references;
        private final AtomicInteger count;
        private int resizeThreshold;
        
        public Segment(final int initialSize, final int resizeThreshold) {
            this.count = new AtomicInteger();
            this.referenceManager = ConcurrentReferenceHashMap.this.createReferenceManager();
            this.initialSize = initialSize;
            this.references = this.createReferenceArray(initialSize);
            this.resizeThreshold = resizeThreshold;
        }
        
        @Nullable
        public Reference<K, V> getReference(@Nullable final Object key, final int hash, final Restructure restructure) {
            if (restructure == Restructure.WHEN_NECESSARY) {
                this.restructureIfNecessary(false);
            }
            if (this.count.get() == 0) {
                return null;
            }
            final Reference<K, V>[] references = this.references;
            final int index = this.getIndex(hash, references);
            final Reference<K, V> head = references[index];
            return this.findInChain(head, key, hash);
        }
        
        @Nullable
        public <T> T doTask(final int hash, @Nullable final Object key, final Task<T> task) {
            final boolean resize = task.hasOption(TaskOption.RESIZE);
            if (task.hasOption(TaskOption.RESTRUCTURE_BEFORE)) {
                this.restructureIfNecessary(resize);
            }
            if (task.hasOption(TaskOption.SKIP_IF_EMPTY) && this.count.get() == 0) {
                return task.execute(null, null, null);
            }
            this.lock();
            try {
                final int index = this.getIndex(hash, this.references);
                final Reference<K, V> head = this.references[index];
                final Reference<K, V> ref = this.findInChain(head, key, hash);
                final Entry<K, V> entry = (ref != null) ? ref.get() : null;
                final Entries<V> entries = value -> {
                    final Entry<K, V> newEntry = new Entry<K, V>((K)key, (V)value);
                    final Reference<K, V> newReference = this.referenceManager.createReference(newEntry, hash, head);
                    this.references[index] = newReference;
                    this.count.incrementAndGet();
                    return;
                };
                return task.execute(ref, entry, entries);
            }
            finally {
                this.unlock();
                if (task.hasOption(TaskOption.RESTRUCTURE_AFTER)) {
                    this.restructureIfNecessary(resize);
                }
            }
        }
        
        public void clear() {
            if (this.count.get() == 0) {
                return;
            }
            this.lock();
            try {
                this.references = this.createReferenceArray(this.initialSize);
                this.resizeThreshold = (int)(this.references.length * ConcurrentReferenceHashMap.this.getLoadFactor());
                this.count.set(0);
            }
            finally {
                this.unlock();
            }
        }
        
        protected final void restructureIfNecessary(final boolean allowResize) {
            final int currCount = this.count.get();
            final boolean needsResize = allowResize && currCount > 0 && currCount >= this.resizeThreshold;
            final Reference<K, V> ref = this.referenceManager.pollForPurge();
            if (ref != null || needsResize) {
                this.restructure(allowResize, ref);
            }
        }
        
        private void restructure(final boolean allowResize, @Nullable Reference<K, V> ref) {
            this.lock();
            try {
                int countAfterRestructure = this.count.get();
                Set<Reference<K, V>> toPurge = (Set<Reference<K, V>>)Collections.emptySet();
                if (ref != null) {
                    toPurge = (Set<Reference<K, V>>)new HashSet();
                    while (ref != null) {
                        toPurge.add((Object)ref);
                        ref = this.referenceManager.pollForPurge();
                    }
                }
                countAfterRestructure -= toPurge.size();
                final boolean needsResize = countAfterRestructure > 0 && countAfterRestructure >= this.resizeThreshold;
                boolean resizing = false;
                int restructureSize = this.references.length;
                if (allowResize && needsResize && restructureSize < 1073741824) {
                    restructureSize <<= 1;
                    resizing = true;
                }
                final Reference<K, V>[] restructured = resizing ? this.createReferenceArray(restructureSize) : this.references;
                for (int i = 0; i < this.references.length; ++i) {
                    ref = this.references[i];
                    if (!resizing) {
                        restructured[i] = null;
                    }
                    while (ref != null) {
                        if (!toPurge.contains((Object)ref)) {
                            final Entry<K, V> entry = ref.get();
                            if (entry != null) {
                                final int index = this.getIndex(ref.getHash(), restructured);
                                restructured[index] = this.referenceManager.createReference(entry, ref.getHash(), restructured[index]);
                            }
                        }
                        ref = ref.getNext();
                    }
                }
                if (resizing) {
                    this.references = restructured;
                    this.resizeThreshold = (int)(this.references.length * ConcurrentReferenceHashMap.this.getLoadFactor());
                }
                this.count.set(Math.max(countAfterRestructure, 0));
            }
            finally {
                this.unlock();
            }
        }
        
        @Nullable
        private Reference<K, V> findInChain(final Reference<K, V> ref, @Nullable final Object key, final int hash) {
            for (Reference<K, V> currRef = ref; currRef != null; currRef = currRef.getNext()) {
                if (currRef.getHash() == hash) {
                    final Entry<K, V> entry = currRef.get();
                    if (entry != null) {
                        final K entryKey = entry.getKey();
                        if (nullSafeEquals(entryKey, key)) {
                            return currRef;
                        }
                    }
                }
            }
            return null;
        }
        
        private Reference<K, V>[] createReferenceArray(final int size) {
            return new Reference[size];
        }
        
        private int getIndex(final int hash, final Reference<K, V>[] references) {
            return hash & references.length - 1;
        }
        
        public final int getSize() {
            return this.references.length;
        }
        
        public final int getCount() {
            return this.count.get();
        }
    }
    
    protected static final class Entry<K, V> implements Map.Entry<K, V>
    {
        @Nullable
        private final K key;
        @Nullable
        private volatile V value;
        
        public Entry(@Nullable final K key, @Nullable final V value) {
            this.key = key;
            this.value = value;
        }
        
        @Nullable
        public K getKey() {
            return this.key;
        }
        
        @Nullable
        public V getValue() {
            return this.value;
        }
        
        @Nullable
        public V setValue(@Nullable final V value) {
            final V previous = this.value;
            this.value = value;
            return previous;
        }
        
        @Override
        public String toString() {
            return (Object)this.key + "=" + (Object)this.value;
        }
        
        @Override
        public final boolean equals(@Nullable final Object other) {
            return this == other || (other instanceof Map.Entry && nullSafeEquals(this.getKey(), ((Map.Entry)other).getKey()) && nullSafeEquals(this.getValue(), ((Map.Entry)other).getValue()));
        }
        
        @Override
        public final int hashCode() {
            return this.nullSafeHashCode(this.key) ^ this.nullSafeHashCode(this.value);
        }
        
        private int nullSafeHashCode(@Nullable final Object obj) {
            if (obj == null) {
                return 0;
            }
            if (obj.getClass().isArray()) {
                if (obj instanceof Object[]) {
                    return this.nullSafeHashCode(obj);
                }
                if (obj instanceof boolean[]) {
                    return this.nullSafeHashCode(obj);
                }
                if (obj instanceof byte[]) {
                    return this.nullSafeHashCode(obj);
                }
                if (obj instanceof char[]) {
                    return this.nullSafeHashCode(obj);
                }
                if (obj instanceof double[]) {
                    return this.nullSafeHashCode(obj);
                }
                if (obj instanceof float[]) {
                    return this.nullSafeHashCode(obj);
                }
                if (obj instanceof int[]) {
                    return this.nullSafeHashCode(obj);
                }
                if (obj instanceof long[]) {
                    return this.nullSafeHashCode(obj);
                }
                if (obj instanceof short[]) {
                    return this.nullSafeHashCode(obj);
                }
            }
            return obj.hashCode();
        }
    }
    
    private abstract class Task<T>
    {
        private final EnumSet<TaskOption> options;
        
        public Task(final TaskOption... options) {
            this.options = (EnumSet<TaskOption>)((options.length == 0) ? EnumSet.noneOf((Class)TaskOption.class) : EnumSet.of((Enum)options[0], (Enum[])options));
        }
        
        public boolean hasOption(final TaskOption option) {
            return this.options.contains((Object)option);
        }
        
        @Nullable
        protected T execute(@Nullable final Reference<K, V> ref, @Nullable final Entry<K, V> entry, @Nullable final Entries<V> entries) {
            return this.execute(ref, entry);
        }
        
        @Nullable
        protected T execute(@Nullable final Reference<K, V> ref, @Nullable final Entry<K, V> entry) {
            return null;
        }
    }
    
    private enum TaskOption
    {
        RESTRUCTURE_BEFORE, 
        RESTRUCTURE_AFTER, 
        SKIP_IF_EMPTY, 
        RESIZE;
    }
    
    private class EntrySet extends AbstractSet<Map.Entry<K, V>>
    {
        public Iterator<Map.Entry<K, V>> iterator() {
            return (Iterator<Map.Entry<K, V>>)new EntryIterator();
        }
        
        public boolean contains(@Nullable final Object o) {
            if (o instanceof Map.Entry) {
                final Map.Entry<?, ?> entry = (Map.Entry<?, ?>)o;
                final Reference<K, V> ref = ConcurrentReferenceHashMap.this.getReference(entry.getKey(), Restructure.NEVER);
                final Entry<K, V> otherEntry = (ref != null) ? ref.get() : null;
                if (otherEntry != null) {
                    return nullSafeEquals(entry.getValue(), otherEntry.getValue());
                }
            }
            return false;
        }
        
        public boolean remove(final Object o) {
            if (o instanceof Map.Entry) {
                final Map.Entry<?, ?> entry = (Map.Entry<?, ?>)o;
                return ConcurrentReferenceHashMap.this.remove(entry.getKey(), entry.getValue());
            }
            return false;
        }
        
        public int size() {
            return ConcurrentReferenceHashMap.this.size();
        }
        
        public void clear() {
            ConcurrentReferenceHashMap.this.clear();
        }
    }
    
    private class EntryIterator implements Iterator<Map.Entry<K, V>>
    {
        private int segmentIndex;
        private int referenceIndex;
        @Nullable
        private Reference<K, V>[] references;
        @Nullable
        private Reference<K, V> reference;
        @Nullable
        private Entry<K, V> next;
        @Nullable
        private Entry<K, V> last;
        
        public EntryIterator() {
            this.moveToNextSegment();
        }
        
        public boolean hasNext() {
            this.getNextIfNecessary();
            return this.next != null;
        }
        
        public Entry<K, V> next() {
            this.getNextIfNecessary();
            if (this.next == null) {
                throw new NoSuchElementException();
            }
            this.last = this.next;
            this.next = null;
            return this.last;
        }
        
        private void getNextIfNecessary() {
            while (this.next == null) {
                this.moveToNextReference();
                if (this.reference == null) {
                    return;
                }
                this.next = this.reference.get();
            }
        }
        
        private void moveToNextReference() {
            if (this.reference != null) {
                this.reference = this.reference.getNext();
            }
            while (this.reference == null && this.references != null) {
                if (this.referenceIndex >= this.references.length) {
                    this.moveToNextSegment();
                    this.referenceIndex = 0;
                }
                else {
                    this.reference = this.references[this.referenceIndex];
                    ++this.referenceIndex;
                }
            }
        }
        
        private void moveToNextSegment() {
            this.reference = null;
            this.references = null;
            if (this.segmentIndex < ConcurrentReferenceHashMap.this.segments.length) {
                this.references = ConcurrentReferenceHashMap.this.segments[this.segmentIndex].references;
                ++this.segmentIndex;
            }
        }
        
        public void remove() {
            Assert.state(this.last != null, "No element to remove");
            ConcurrentReferenceHashMap.this.remove(this.last.getKey());
            this.last = null;
        }
    }
    
    protected enum Restructure
    {
        WHEN_NECESSARY, 
        NEVER;
    }
    
    protected class ReferenceManager
    {
        private final ReferenceQueue<Entry<K, V>> queue;
        
        protected ReferenceManager() {
            this.queue = (ReferenceQueue<Entry<K, V>>)new ReferenceQueue();
        }
        
        public Reference<K, V> createReference(final Entry<K, V> entry, final int hash, @Nullable final Reference<K, V> next) {
            if (ConcurrentReferenceHashMap.this.referenceType == ReferenceType.WEAK) {
                return new WeakEntryReference<K, V>(entry, hash, next, this.queue);
            }
            return new SoftEntryReference<K, V>(entry, hash, next, this.queue);
        }
        
        @Nullable
        public Reference<K, V> pollForPurge() {
            return (Reference)this.queue.poll();
        }
    }
    
    private static final class SoftEntryReference<K, V> extends SoftReference<Entry<K, V>> implements Reference<K, V>
    {
        private final int hash;
        @Nullable
        private final Reference<K, V> nextReference;
        
        public SoftEntryReference(final Entry<K, V> entry, final int hash, @Nullable final Reference<K, V> next, final ReferenceQueue<Entry<K, V>> queue) {
            super((Object)entry, (ReferenceQueue)queue);
            this.hash = hash;
            this.nextReference = next;
        }
        
        public int getHash() {
            return this.hash;
        }
        
        @Nullable
        public Reference<K, V> getNext() {
            return this.nextReference;
        }
        
        public void release() {
            this.enqueue();
            this.clear();
        }
    }
    
    private static final class WeakEntryReference<K, V> extends WeakReference<Entry<K, V>> implements Reference<K, V>
    {
        private final int hash;
        @Nullable
        private final Reference<K, V> nextReference;
        
        public WeakEntryReference(final Entry<K, V> entry, final int hash, @Nullable final Reference<K, V> next, final ReferenceQueue<Entry<K, V>> queue) {
            super((Object)entry, (ReferenceQueue)queue);
            this.hash = hash;
            this.nextReference = next;
        }
        
        public int getHash() {
            return this.hash;
        }
        
        @Nullable
        public Reference<K, V> getNext() {
            return this.nextReference;
        }
        
        public void release() {
            this.enqueue();
            this.clear();
        }
    }
    
    protected interface Reference<K, V>
    {
        @Nullable
        Entry<K, V> get();
        
        int getHash();
        
        @Nullable
        Reference<K, V> getNext();
        
        void release();
    }
    
    private interface Entries<V>
    {
        void add(@Nullable final V p0);
    }
}
