package net.hypixel.skyblock.entity.nms;

public class TieredValue<T>
{
    private final T i;
    private final T ii;
    private final T iii;
    private final T iv;
    
    public TieredValue(final T i, final T ii, final T iii, final T iv) {
        this.i = i;
        this.ii = ii;
        this.iii = iii;
        this.iv = iv;
    }
    
    public T getByNumber(final int n) {
        switch (n) {
            case 2: {
                return this.ii;
            }
            case 3: {
                return this.iii;
            }
            case 4: {
                return this.iv;
            }
            default: {
                return this.i;
            }
        }
    }
    
    public T getTierI() {
        return this.i;
    }
    
    public T getTierII() {
        return this.ii;
    }
    
    public T getTierIII() {
        return this.iii;
    }
    
    public T getTierIV() {
        return this.iv;
    }
}
