package net.hypixel.skyblock.util;

import org.bukkit.entity.Player;
import java.util.UUID;
import java.util.HashMap;

public class TradeUtil
{
    private static HashMap<UUID, UUID> tradeReq;
    public static final HashMap<UUID, Boolean> trading;
    
    public static boolean hasRequest(final Player p, final Player requester) {
        return TradeUtil.tradeReq.containsKey((Object)requester.getUniqueId()) && TradeUtil.tradeReq.get((Object)requester.getUniqueId()) == p.getUniqueId();
    }
    
    public static void requestTrade(final Player requester, final Player p) {
        TradeUtil.tradeReq.put((Object)requester.getUniqueId(), (Object)p.getUniqueId());
    }
    
    public static void resetTrade(final Player requester) {
        TradeUtil.tradeReq.remove((Object)requester.getUniqueId());
    }
    
    public static boolean isTrading(final Player req) {
        if (!TradeUtil.trading.containsKey((Object)req.getUniqueId())) {
            TradeUtil.trading.put((Object)req.getUniqueId(), (Object)false);
        }
        return (boolean)TradeUtil.trading.get((Object)req.getUniqueId());
    }
    
    static {
        TradeUtil.tradeReq = (HashMap<UUID, UUID>)new HashMap();
        trading = new HashMap();
    }
}
