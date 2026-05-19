/*     */ package org.nyxiikcz.aioptimizer.systems;
/*     */ 
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ import org.nyxiikcz.aioptimizer.ServerAiOptimizer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldManager
/*     */   extends BukkitRunnable
/*     */ {
/*     */   private final ServerAiOptimizer plugin;
/*     */   private final int maxView;
/*     */   private final int minView;
/*  19 */   private long lastViewChange = 0L;
/*  20 */   private long lastCleanup = 0L;
/*     */   
/*     */   public WorldManager(ServerAiOptimizer paramServerAiOptimizer) {
/*  23 */     this.plugin = paramServerAiOptimizer;
/*  24 */     this.maxView = paramServerAiOptimizer.getConfig().getInt("world-manager.dynamic-view.max-view", 10);
/*  25 */     this.minView = paramServerAiOptimizer.getConfig().getInt("world-manager.dynamic-view.min-view", 4);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void run() {
/*  31 */     double d = this.plugin.getMonitor().getStress();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  36 */     if (d < 50.0D) {
/*  37 */       manageViewDistance(d, 1);
/*     */     
/*     */     }
/*  40 */     else if (d >= 50.0D && d < 60.0D) {
/*  41 */       manageCleanup(false);
/*     */     
/*     */     }
/*  44 */     else if (d >= 60.0D && d < 80.0D) {
/*  45 */       manageViewDistance(d, -2);
/*  46 */       manageCleanup(true);
/*     */     }
/*     */     else {
/*     */       
/*  50 */       manageViewDistance(d, -99);
/*  51 */       manageCleanup(true);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void manageViewDistance(double paramDouble, int paramInt) {
/*  58 */     if (!this.plugin.getConfig().getBoolean("world-manager.dynamic-view.enabled")) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  63 */     if (System.currentTimeMillis() - this.lastViewChange < 15000L)
/*     */       return; 
/*  65 */     boolean bool = false;
/*     */     
/*  67 */     for (World world : Bukkit.getWorlds()) {
/*  68 */       int i = world.getViewDistance();
/*  69 */       int j = i + paramInt;
/*     */       
/*  71 */       if (paramInt <= -99) j = this.minView;
/*     */ 
/*     */       
/*  74 */       if (j < this.minView) j = this.minView; 
/*  75 */       if (j > this.maxView) j = this.maxView;
/*     */ 
/*     */       
/*  78 */       if (j != i) {
/*  79 */         world.setViewDistance(j);
/*     */         
/*     */         try {
/*  82 */           int k = Math.max(this.minView, j - 2);
/*  83 */           world.setSimulationDistance(k);
/*  84 */         } catch (NoSuchMethodError noSuchMethodError) {}
/*     */ 
/*     */         
/*  87 */         String str = String.format("&8[&bAI&8] &7World &f%s &7View Distance: &c%d &7-> &a%d &7(Stress: %d%%)", new Object[] { world
/*     */               
/*  89 */               .getName(), Integer.valueOf(i), Integer.valueOf(j), Integer.valueOf((int)paramDouble) });
/*     */ 
/*     */ 
/*     */         
/*  93 */         this.plugin.getLogger().info("AI Autopilot: Changed View Distance in world " + world.getName() + " from " + i + " to " + j + " (Stress: " + (int)paramDouble + "%)");
/*     */ 
/*     */         
/*  96 */         for (Player player : Bukkit.getOnlinePlayers()) {
/*  97 */           if (player.isOp() || player.hasPermission("ai.admin")) {
/*  98 */             player.sendMessage(ChatColor.translateAlternateColorCodes('&', str));
/*     */           }
/*     */         } 
/*     */         
/* 102 */         bool = true;
/*     */       } 
/*     */     } 
/*     */     
/* 106 */     if (bool) this.lastViewChange = System.currentTimeMillis();
/*     */   
/*     */   }
/*     */   
/*     */   private void manageCleanup(boolean paramBoolean) {
/* 111 */     if (System.currentTimeMillis() - this.lastCleanup < 60000L) {
/*     */       return;
/*     */     }
/* 114 */     this.plugin.getLogger().info("AI Autopilot: Starting " + (paramBoolean ? "AGGRESSIVE" : "STANDARD") + " entity cleanup.");
/*     */ 
/*     */     
/* 117 */     String str = "&8[&bAI&8] &7Running " + (paramBoolean ? "&cAGGRESSIVE" : "&eSTANDARD") + " &7entity cleanup.";
/*     */     
/* 119 */     for (Player player : Bukkit.getOnlinePlayers()) {
/* 120 */       if (player.isOp() || player.hasPermission("ai.admin")) {
/* 121 */         player.sendMessage(ChatColor.translateAlternateColorCodes('&', str));
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 132 */     ServerMonitor.ServerStatus serverStatus = paramBoolean ? ServerMonitor.ServerStatus.CRITICAL : this.plugin.getMonitor().getStatus();
/*     */ 
/*     */ 
/*     */     
/* 136 */     this.plugin.getEntityOptimizer().performCleanup(serverStatus, false);
/*     */     
/* 138 */     this.lastCleanup = System.currentTimeMillis();
/*     */   }
/*     */ }


/* Location:              C:\Users\IK\Videos\Server-Ai-Optimizer-2.5.jar!\org\nyxiikcz\aioptimizer\systems\WorldManager.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */