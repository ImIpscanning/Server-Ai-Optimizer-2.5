/*    */ package org.nyxiikcz.aioptimizer.listeners;
/*    */ 
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.player.PlayerJoinEvent;
/*    */ import org.nyxiikcz.aioptimizer.ServerAiOptimizer;
/*    */ 
/*    */ public class UpdateListener
/*    */   implements Listener {
/*    */   private final ServerAiOptimizer plugin;
/*    */   
/*    */   public UpdateListener(ServerAiOptimizer paramServerAiOptimizer) {
/* 14 */     this.plugin = paramServerAiOptimizer;
/*    */   }
/*    */ 
/*    */   
/*    */   @EventHandler
/*    */   public void onJoin(PlayerJoinEvent paramPlayerJoinEvent) {
/* 20 */     if (!paramPlayerJoinEvent.getPlayer().hasPermission("ai.admin")) {
/*    */       return;
/*    */     }
/* 23 */     if (!this.plugin.getConfig().getBoolean("update-checker.enabled", true)) {
/*    */       return;
/*    */     }
/* 26 */     if (this.plugin.getLatestVersion() == null)
/*    */       return; 
/* 28 */     String str1 = this.plugin.getDescription().getVersion();
/* 29 */     String str2 = this.plugin.getLatestVersion();
/*    */ 
/*    */     
/* 32 */     if (!str1.equalsIgnoreCase(str2)) {
/* 33 */       paramPlayerJoinEvent.getPlayer().sendMessage(String.valueOf(ChatColor.GRAY) + "----------------------------------------");
/* 34 */       paramPlayerJoinEvent.getPlayer().sendMessage(String.valueOf(ChatColor.AQUA) + String.valueOf(ChatColor.AQUA) + " AI OPTIMIZER UPDATE");
/* 35 */       paramPlayerJoinEvent.getPlayer().sendMessage("");
/* 36 */       paramPlayerJoinEvent.getPlayer().sendMessage(String.valueOf(ChatColor.GRAY) + " A new version is available!");
/* 37 */       paramPlayerJoinEvent.getPlayer().sendMessage(String.valueOf(ChatColor.GRAY) + " Your version: " + String.valueOf(ChatColor.GRAY) + String.valueOf(ChatColor.RED));
/* 38 */       paramPlayerJoinEvent.getPlayer().sendMessage(String.valueOf(ChatColor.GRAY) + " New version:  " + String.valueOf(ChatColor.GRAY) + String.valueOf(ChatColor.GREEN));
/* 39 */       paramPlayerJoinEvent.getPlayer().sendMessage("");
/* 40 */       paramPlayerJoinEvent.getPlayer().sendMessage(String.valueOf(ChatColor.YELLOW) + " Download at SpigotMC or BuiltByBit.");
/* 41 */       paramPlayerJoinEvent.getPlayer().sendMessage(String.valueOf(ChatColor.GRAY) + "----------------------------------------");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\IK\Videos\Server-Ai-Optimizer-2.5.jar!\org\nyxiikcz\aioptimizer\listeners\UpdateListener.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */