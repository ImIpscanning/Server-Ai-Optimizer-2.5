/*    */ package org.nyxiikcz.serveraioptimizer.bstats.charts;
/*    */ 
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.Callable;
/*    */ import org.nyxiikcz.serveraioptimizer.bstats.json.JsonObjectBuilder;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AdvancedPie
/*    */   extends CustomChart
/*    */ {
/*    */   private final Callable<Map<String, Integer>> callable;
/*    */   
/*    */   public AdvancedPie(String paramString, Callable<Map<String, Integer>> paramCallable) {
/* 19 */     super(paramString);
/* 20 */     this.callable = paramCallable;
/*    */   }
/*    */ 
/*    */   
/*    */   protected JsonObjectBuilder.JsonObject getChartData() {
/* 25 */     JsonObjectBuilder jsonObjectBuilder = new JsonObjectBuilder();
/*    */     
/* 27 */     Map map = this.callable.call();
/* 28 */     if (map == null || map.isEmpty())
/*    */     {
/* 30 */       return null;
/*    */     }
/* 32 */     boolean bool = true;
/* 33 */     for (Map.Entry entry : map.entrySet()) {
/* 34 */       if (((Integer)entry.getValue()).intValue() == 0) {
/*    */         continue;
/*    */       }
/* 37 */       bool = false;
/* 38 */       jsonObjectBuilder.appendField((String)entry.getKey(), ((Integer)entry.getValue()).intValue());
/*    */     } 
/* 40 */     if (bool)
/*    */     {
/* 42 */       return null;
/*    */     }
/*    */     
/* 45 */     return (new JsonObjectBuilder())
/* 46 */       .appendField("values", jsonObjectBuilder.build())
/* 47 */       .build();
/*    */   }
/*    */ }


/* Location:              C:\Users\IK\Videos\Server-Ai-Optimizer-2.5.jar!\org\nyxiikcz\serveraioptimizer\bstats\charts\AdvancedPie.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */