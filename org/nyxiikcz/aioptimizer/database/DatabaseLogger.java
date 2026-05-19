/*    */ package org.nyxiikcz.aioptimizer.database;
/*    */ 
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.scheduler.BukkitRunnable;
/*    */ import org.nyxiikcz.aioptimizer.ServerAiOptimizer;
/*    */ 
/*    */ public class DatabaseLogger
/*    */   extends BukkitRunnable {
/*    */   private final ServerAiOptimizer plugin;
/*    */   private final DatabaseManager dbManager;
/*    */   
/*    */   public DatabaseLogger(ServerAiOptimizer paramServerAiOptimizer, DatabaseManager paramDatabaseManager) {
/* 13 */     this.plugin = paramServerAiOptimizer;
/* 14 */     this.dbManager = paramDatabaseManager;
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/* 19 */     double d1 = this.plugin.getMonitor().getStatsTps();
/* 20 */     double d2 = this.plugin.getMonitor().getStatsMspt();
/* 21 */     double d3 = this.plugin.getMonitor().getStatsCpu();
/* 22 */     double d4 = this.plugin.getMonitor().getStatsRam();
/* 23 */     double d5 = this.plugin.getMonitor().getStatsDisk();
/* 24 */     int i = this.plugin.getMonitor().getChunkCount();
/* 25 */     int j = this.plugin.getMonitor().getEntityCount();
/* 26 */     int k = Bukkit.getOnlinePlayers().size();
/*    */ 
/*    */     
/* 29 */     this.dbManager.logData(d1, d2, d3, d4, d5, i, j, k);
/*    */   }
/*    */ }


/* Location:              C:\Users\IK\Videos\Server-Ai-Optimizer-2.5.jar!\org\nyxiikcz\aioptimizer\database\DatabaseLogger.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */