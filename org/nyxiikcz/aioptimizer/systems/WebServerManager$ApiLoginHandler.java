/*     */ package org.nyxiikcz.aioptimizer.systems;
/*     */ 
/*     */ import com.sun.net.httpserver.HttpExchange;
/*     */ import com.sun.net.httpserver.HttpHandler;
/*     */ import java.io.InputStream;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ApiLoginHandler
/*     */   implements HttpHandler
/*     */ {
/*     */   private final WebServerManager manager;
/*     */   
/*     */   public ApiLoginHandler(WebServerManager paramWebServerManager) {
/*  95 */     this.manager = paramWebServerManager;
/*     */   }
/*     */   public void handle(HttpExchange paramHttpExchange) {
/*  98 */     if ("POST".equalsIgnoreCase(paramHttpExchange.getRequestMethod())) {
/*  99 */       InputStream inputStream = paramHttpExchange.getRequestBody();
/* 100 */       String str1 = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
/* 101 */       String str2 = "\"u\":\"" + this.manager.webUser + "\"";
/* 102 */       String str3 = "\"p\":\"" + this.manager.webPass + "\"";
/*     */       
/* 104 */       if (str1.contains(str2) && str1.contains(str3)) {
/* 105 */         paramHttpExchange.getResponseHeaders().add("Set-Cookie", "ai_session=" + this.manager.sessionToken + "; Path=/; HttpOnly");
/* 106 */         WebServerManager.sendJson(paramHttpExchange, "{\"success\":true}", 200);
/*     */       } else {
/* 108 */         WebServerManager.sendJson(paramHttpExchange, "{\"success\":false}", 401);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\IK\Videos\Server-Ai-Optimizer-2.5.jar!\org\nyxiikcz\aioptimizer\systems\WebServerManager$ApiLoginHandler.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */