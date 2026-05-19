/*     */ package org.nyxiikcz.aioptimizer.systems;
/*     */ 
/*     */ import com.sun.net.httpserver.HttpExchange;
/*     */ import com.sun.net.httpserver.HttpHandler;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.entity.Player;
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
/*     */ class ApiPlayersHandler
/*     */   implements HttpHandler
/*     */ {
/*     */   private final WebServerManager manager;
/*     */   
/*     */   public ApiPlayersHandler(WebServerManager paramWebServerManager) {
/* 149 */     this.manager = paramWebServerManager;
/*     */   }
/*     */   public void handle(HttpExchange paramHttpExchange) {
/* 152 */     if (!this.manager.isAuthenticated(paramHttpExchange)) { WebServerManager.sendJson(paramHttpExchange, "{\"error\":\"Unauthorized\"}", 401); return; }
/* 153 */      StringBuilder stringBuilder = new StringBuilder("[");
/* 154 */     byte b = 0;
/* 155 */     int i = Bukkit.getOnlinePlayers().size();
/* 156 */     for (Player player : Bukkit.getOnlinePlayers()) {
/* 157 */       stringBuilder.append(String.format("{\"name\":\"%s\", \"uuid\":\"%s\", \"ping\":%d}", new Object[] { player.getName(), player.getUniqueId().toString(), Integer.valueOf(player.getPing()) }));
/* 158 */       if (b < i - 1) stringBuilder.append(","); 
/* 159 */       b++;
/*     */     } 
/* 161 */     stringBuilder.append("]");
/* 162 */     WebServerManager.sendJson(paramHttpExchange, stringBuilder.toString(), 200);
/*     */   }
/*     */ }


/* Location:              C:\Users\IK\Videos\Server-Ai-Optimizer-2.5.jar!\org\nyxiikcz\aioptimizer\systems\WebServerManager$ApiPlayersHandler.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */