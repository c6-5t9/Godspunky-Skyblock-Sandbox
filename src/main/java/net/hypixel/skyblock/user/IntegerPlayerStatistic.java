package net.hypixel.skyblock.user;

import java.util.Iterator;
import java.util.Collection;
import java.util.Arrays;
import java.util.ArrayList;

public class IntegerPlayerStatistic implements PlayerStatistic<Integer>
{
    private final int defaultValue;
    private final ArrayList<Integer> values;
    
    public IntegerPlayerStatistic(final int defaultValue) {
        this.defaultValue = defaultValue;
        (this.values = (ArrayList<Integer>)new ArrayList(6)).addAll((Collection)Arrays.asList((Object[])new Integer[] { 0, 0, 0, 0, 0, 0 }));
    }
    
    public IntegerPlayerStatistic() {
        this(0);
    }
    
    @Override
    public Integer addAll() {
        int result = this.defaultValue;
        for (final Integer value : new ArrayList((Collection)this.values)) {
            result += value;
        }
        return result;
    }
    
    @Override
    public void add(final int slot, final Integer i) {
        this.set(slot, Integer.valueOf(this.safeGet(slot) + i));
    }
    
    @Override
    public void sub(final int slot, final Integer i) {
        this.set(slot, Integer.valueOf(this.safeGet(slot) - i));
    }
    
    @Override
    public void zero(final int slot) {
        this.set(slot, Integer.valueOf(0));
    }
    
    @Override
    public boolean contains(final int slot) {
        return slot >= 0 && slot < this.values.size();
    }
    
    @Override
    public Integer safeGet(final int index) {
        if (index < 0 || index > this.values.size() - 1) {
            this.set(index, Integer.valueOf(0));
        }
        return (Integer)this.values.get(index);
    }
    
    @Override
    public void set(final int slot, final Integer i) {
        this.values.ensureCapacity(slot + 1);
        while (this.values.size() < slot + 1) {
            this.values.add((Object)0);
        }
        this.values.set(slot, (Object)i);
    }
    
    @Override
    public int next() {
        return this.values.size();
    }
    
    @Override
    public Integer getFor(final int slot) {
        return this.safeGet(slot);
    }
    
    public ArrayList<Integer> forInventory() {
        final ArrayList<Integer> list = (ArrayList<Integer>)new ArrayList();
        for (int i = 6; i < this.values.size(); ++i) {
            list.add((Object)this.safeGet(i));
        }
        return list;
    }
    
    public int getDefaultValue() {
        return this.defaultValue;
    }
}
