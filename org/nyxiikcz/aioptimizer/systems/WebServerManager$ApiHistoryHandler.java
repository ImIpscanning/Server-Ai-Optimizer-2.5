/*     */ package org.nyxiikcz.aioptimizer.systems;
/*     */ 
/*     */ import com.sun.net.httpserver.HttpExchange;
/*     */ import com.sun.net.httpserver.HttpHandler;
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
/*     */ class ApiHistoryHandler
/*     */   implements HttpHandler
/*     */ {
/*     */   private final WebServerManager manager;
/*     */   
/*     */   public ApiHistoryHandler(WebServerManager paramWebServerManager) {
/* 135 */     this.manager = paramWebServerManager;
/*     */   }
/*     */   public void handle(HttpExchange paramHttpExchange) {
/* 138 */     if (!this.manager.isAuthenticated(paramHttpExchange)) { WebServerManager.sendJson(paramHttpExchange, "{\"error\":\"Unauthorized\"}", 401); return; }
/* 139 */      String str = "{}";
/* 140 */     if (this.manager.getPlugin().getConfig().getBoolean("database.enabled") && this.manager.getPlugin().getDatabaseManager() != null) {
/* 141 */       str = this.manager.getPlugin().getDatabaseManager().getHistoryAsJson(24);
/*     */     }
/* 143 */     WebServerManager.sendJson(paramHttpExchange, str, 200);
/*     */   }
/*     */ }


/* Location:              C:\Users\IK\Videos\Server-Ai-Optimizer-2.5.jar!\org\nyxiikcz\aioptimizer\systems\WebServerManager$ApiHistoryHandler.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */