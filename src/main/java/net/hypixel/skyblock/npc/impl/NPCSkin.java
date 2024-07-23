package net.hypixel.skyblock.npc.impl;

public class NPCSkin
{
    private final String texture;
    private final String signature;
    
    public String getTexture() {
        return this.texture;
    }
    
    public String getSignature() {
        return this.signature;
    }
    
    public NPCSkin(final String texture, final String signature) {
        this.texture = texture;
        this.signature = signature;
    }
}
