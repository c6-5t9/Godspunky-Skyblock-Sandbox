package net.hypixel.skyblock.api.protocol;

public class EIDGen
{
    private static int lastIssuedEID;
    
    public static int generateEID() {
        final int i = EIDGen.lastIssuedEID;
        ++EIDGen.lastIssuedEID;
        return i;
    }
    
    static {
        EIDGen.lastIssuedEID = 2000000000;
    }
}
