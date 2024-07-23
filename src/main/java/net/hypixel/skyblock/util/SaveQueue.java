package net.hypixel.skyblock.util;

import java.util.LinkedList;

public class SaveQueue<T>
{
    private final LinkedList<T> queue;
    
    public SaveQueue() {
        this.queue = (LinkedList<T>)new LinkedList();
    }
    
    public void enqueue(final T element) {
        this.queue.add((Object)element);
    }
    
    public void add(final T element) {
        this.queue.add((Object)element);
    }
    
    public T dequeue() {
        if (this.isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        return (T)this.queue.remove();
    }
    
    public boolean isEmpty() {
        return this.queue.isEmpty();
    }
    
    public int size() {
        return this.queue.size();
    }
}
