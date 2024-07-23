package net.hypixel.skyblock.item.accessory;

import net.hypixel.skyblock.item.Rarity;

public class JacobusRegister implements AccessoryStatistics
{
    @Override
    public String getDisplayName() {
        return "Jacobus Register";
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.LEGENDARY;
    }
    
    @Override
    public double getBaseHealth() {
        return 10.0;
    }
    
    @Override
    public String getURL() {
        return "3b648b9a44e280bcdf25f4a66a97bd5c33542e5e82415e15b475c6b999b8d635";
    }
}
