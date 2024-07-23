package net.hypixel.skyblock.user;

import java.util.Iterator;
import java.util.Collection;
import java.util.Arrays;
import java.util.ArrayList;

public class DoublePlayerStatistic implements PlayerStatistic<Double>
{
    private final double defaultValue;
    private final ArrayList<Double> values;
    
    public DoublePlayerStatistic(final double defaultValue) {
        this.defaultValue = defaultValue;
        (this.values = (ArrayList<Double>)new ArrayList(6)).addAll((Collection)Arrays.asList((Object[])new Double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }));
    }
    
    public DoublePlayerStatistic() {
        this(0.0);
    }
    
    @Override
    public Double addAll() {
        double result = this.defaultValue;
        for (final Double value : this.values) {
            result += value;
        }
        return result;
    }
    
    @Override
    public void add(final int slot, final Double d) {
        this.set(slot, Double.valueOf(this.safeGet(slot) + d));
    }
    
    @Override
    public void sub(final int slot, final Double d) {
        this.set(slot, Double.valueOf(this.safeGet(slot) - d));
    }
    
    @Override
    public void zero(final int slot) {
        this.set(slot, Double.valueOf(0.0));
    }
    
    @Override
    public boolean contains(final int slot) {
        return slot >= 0 && slot < this.values.size();
    }
    
    @Override
    public Double safeGet(final int index) {
        if (index < 0 || index > this.values.size() - 1) {
            this.set(index, Double.valueOf(0.0));
        }
        return (Double)this.values.get(index);
    }
    
    @Override
    public void set(final int slot, final Double d) {
        this.values.ensureCapacity(slot + 1);
        while (this.values.size() < slot + 1) {
            this.values.add((Object)0.0);
        }
        this.values.set(slot, (Object)d);
    }
    
    @Override
    public int next() {
        return this.values.size();
    }
    
    @Override
    public Double getFor(final int slot) {
        return this.safeGet(slot);
    }
    
    public ArrayList<Double> forInventory() {
        final ArrayList<Double> list = (ArrayList<Double>)new ArrayList();
        for (int i = 6; i < this.values.size(); ++i) {
            list.add((Object)this.safeGet(i));
        }
        return list;
    }
    
    public double getDefaultValue() {
        return this.defaultValue;
    }
}
