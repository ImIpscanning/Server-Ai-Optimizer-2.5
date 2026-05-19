/*     */ package org.nyxiikcz.aioptimizer.systems;
/*     */ 
/*     */ import com.sun.net.httpserver.HttpExchange;
/*     */ import com.sun.net.httpserver.HttpHandler;
/*     */ import java.util.Locale;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.nyxiikcz.aioptimizer.ServerAiOptimizer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ApiStatsHandler
/*     */   implements HttpHandler
/*     */ {
/*     */   private final WebServerManager manager;
/*     */   
/*     */   public ApiStatsHandler(WebServerManager paramWebServerManager) {
/* 116 */     this.manager = paramWebServerManager;
/*     */   }
/*     */   public void handle(HttpExchange paramHttpExchange) {
/* 119 */     if (!this.manager.isAuthenticated(paramHttpExchange)) { WebServerManager.sendJson(paramHttpExchange, "{\"error\":\"Unauthorized\"}", 401); return; }
/* 120 */      ServerAiOptimizer serverAiOptimizer = this.manager.getPlugin();
/* 121 */     String str1 = "[" + String.join(",", (Iterable)serverAiOptimizer.getMonitor().getWorstChunks()) + "]";
/* 122 */     String str2 = String.format(Locale.US, "{\"tps\":%.2f, \"mspt\":%.1f, \"cpu\":%.1f, \"ram\":%.1f, \"disk\":%.1f, \"stress\":%.1f, \"status\":\"%s\", \"players\":%d, \"max_players\":%d, \"entities\":%d, \"chunks\":%d, \"worst_chunks\":%s}", new Object[] {
/*     */           
/* 124 */           Double.valueOf(serverAiOptimizer.getMonitor().getStatsTps()), Double.valueOf(serverAiOptimizer.getMonitor().getStatsMspt()), Double.valueOf(serverAiOptimizer.getMonitor().getStatsCpu()), 
/* 125 */           Double.valueOf(serverAiOptimizer.getMonitor().getStatsRam()), Double.valueOf(serverAiOptimizer.getMonitor().getStatsDisk()), Double.valueOf(serverAiOptimizer.getMonitor().getStress()), serverAiOptimizer
/* 126 */           .getMonitor().getStatus().name(), Integer.valueOf(Bukkit.getOnlinePlayers().size()), Integer.valueOf(Bukkit.getMaxPlayers()), 
/* 127 */           Integer.valueOf(serverAiOptimizer.getMonitor().getEntityCount()), Integer.valueOf(serverAiOptimizer.getMonitor().getChunkCount()), str1
/*     */         });
/* 129 */     WebServerManager.sendJson(paramHttpExchange, str2, 200);
/*     */   }
/*     */ }


/* Location:              C:\Users\IK\Videos\Server-Ai-Optimizer-2.5.jar!\org\nyxiikcz\aioptimizer\systems\WebServerManager$ApiStatsHandler.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */