package net.hypixel.skyblock.features.reforge;

public enum ReforgeType
{
    OVERPOWERED((Class<? extends Reforge>)OverpoweredReforge.class, false), 
    GENIUS((Class<? extends Reforge>)GeniusReforge.class), 
    STRONK((Class<? extends Reforge>)StronkReforge.class), 
    SUPERGENIUS((Class<? extends Reforge>)SupergeniusReforge.class, false), 
    HASTY((Class<? extends Reforge>)Hasty.class), 
    FAST((Class<? extends Reforge>)FastReforge.class), 
    SPICY((Class<? extends Reforge>)SpicyReforge.class), 
    FIERCE((Class<? extends Reforge>)FierceReforge.class), 
    HEROIC((Class<? extends Reforge>)HeroicReforge.class), 
    ODD((Class<? extends Reforge>)OddReforge.class), 
    RAPID((Class<? extends Reforge>)RapidReforge.class), 
    ANCIENT((Class<? extends Reforge>)Ancient.class), 
    WITHERED((Class<? extends Reforge>)WitheredReforge.class), 
    LEGENDARY((Class<? extends Reforge>)LegendaryReforge.class), 
    SHARP((Class<? extends Reforge>)SharpReforge.class), 
    EPIC((Class<? extends Reforge>)EpicReforge.class), 
    FABLED((Class<? extends Reforge>)Fabled.class), 
    RENOWNED((Class<? extends Reforge>)Renowned.class), 
    SPIRITUAL((Class<? extends Reforge>)Spiritual.class), 
    UNREAL((Class<? extends Reforge>)Unreal.class), 
    WISE((Class<? extends Reforge>)Wise.class), 
    NECROTIC((Class<? extends Reforge>)Necrotic.class);
    
    private final Class<? extends Reforge> clazz;
    private final boolean accessible;
    
    private ReforgeType(final Class<? extends Reforge> clazz, final boolean accessible) {
        this.clazz = clazz;
        this.accessible = accessible;
    }
    
    private ReforgeType(final Class<? extends Reforge> clazz) {
        this(clazz, true);
    }
    
    public Reforge getReforge() {
        try {
            return (Reforge)this.clazz.newInstance();
        }
        catch (final InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static ReforgeType getReforgeType(final String name) {
        return valueOf(name.toUpperCase());
    }
    
    public static ReforgeType getByClass(final Class<? extends Reforge> clazz) {
        for (final ReforgeType type : values()) {
            if (type.clazz == clazz) {
                return type;
            }
        }
        return null;
    }
    
    public boolean isAccessible() {
        return this.accessible;
    }
}
