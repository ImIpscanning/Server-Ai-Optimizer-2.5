/*     */ package org.nyxiikcz.aioptimizer.systems;
/*     */ 
/*     */ import com.sun.net.httpserver.HttpExchange;
/*     */ import com.sun.net.httpserver.HttpHandler;
/*     */ import java.io.InputStream;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.Item;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ApiActionHandler
/*     */   implements HttpHandler
/*     */ {
/*     */   private final WebServerManager manager;
/*     */   
/*     */   public ApiActionHandler(WebServerManager paramWebServerManager) {
/* 168 */     this.manager = paramWebServerManager;
/*     */   }
/*     */   public void handle(HttpExchange paramHttpExchange) {
/* 171 */     if (!this.manager.isAuthenticated(paramHttpExchange)) { WebServerManager.sendJson(paramHttpExchange, "{\"error\":\"Unauthorized\"}", 401); return; }
/* 172 */      if ("POST".equalsIgnoreCase(paramHttpExchange.getRequestMethod())) {
/* 173 */       InputStream inputStream = paramHttpExchange.getRequestBody();
/* 174 */       String str = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
/*     */       
/* 176 */       Bukkit.getScheduler().runTask((Plugin)this.manager.getPlugin(), () -> {
/*     */             if (paramString.equals("panic")) {
/*     */               Bukkit.getWorlds().forEach(()); Bukkit.broadcastMessage("§c[AI Optimizer] Administrator activated Panic Mode! Items cleared.");
/*     */             } else if (paramString.startsWith("kick:")) {
/*     */               String str = paramString.split(":")[1];
/*     */               Player player = Bukkit.getPlayer(str);
/*     */               if (player != null)
/*     */                 player.kickPlayer("§cKicked from Web Dashboard."); 
/*     */             } else if (paramString.startsWith("cmd:")) {
/*     */               String str = paramString.substring(4);
/*     */               Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), str);
/*     */             } 
/*     */           });
/* 189 */       WebServerManager.sendJson(paramHttpExchange, "{\"status\":\"success\"}", 200);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\IK\Videos\Server-Ai-Optimizer-2.5.jar!\org\nyxiikcz\aioptimizer\systems\WebServerManager$ApiActionHandler.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */