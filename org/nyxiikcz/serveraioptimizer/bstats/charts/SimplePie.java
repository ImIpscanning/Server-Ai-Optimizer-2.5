/*    */ package org.nyxiikcz.serveraioptimizer.bstats.charts;
/*    */ 
/*    */ import java.util.concurrent.Callable;
/*    */ import org.nyxiikcz.serveraioptimizer.bstats.json.JsonObjectBuilder;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SimplePie
/*    */   extends CustomChart
/*    */ {
/*    */   private final Callable<String> callable;
/*    */   
/*    */   public SimplePie(String paramString, Callable<String> paramCallable) {
/* 18 */     super(paramString);
/* 19 */     this.callable = paramCallable;
/*    */   }
/*    */ 
/*    */   
/*    */   protected JsonObjectBuilder.JsonObject getChartData() {
/* 24 */     String str = this.callable.call();
/* 25 */     if (str == null || str.isEmpty())
/*    */     {
/* 27 */       return null;
/*    */     }
/* 29 */     return (new JsonObjectBuilder())
/* 30 */       .appendField("value", str)
/* 31 */       .build();
/*    */   }
/*    */ }


/* Location:              C:\Users\IK\Videos\Server-Ai-Optimizer-2.5.jar!\org\nyxiikcz\serveraioptimizer\bstats\charts\SimplePie.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */