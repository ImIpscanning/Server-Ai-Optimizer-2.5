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
/*    */ public class SimpleBarChart
/*    */   extends CustomChart
/*    */ {
/*    */   private final Callable<Map<String, Integer>> callable;
/*    */   
/*    */   public SimpleBarChart(String paramString, Callable<Map<String, Integer>> paramCallable) {
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
/* 32 */     for (Map.Entry entry : map.entrySet()) {
/* 33 */       jsonObjectBuilder.appendField((String)entry.getKey(), new int[] { ((Integer)entry.getValue()).intValue() });
/*    */     } 
/*    */     
/* 36 */     return (new JsonObjectBuilder())
/* 37 */       .appendField("values", jsonObjectBuilder.build())
/* 38 */       .build();
/*    */   }
/*    */ }


/* Location:              C:\Users\IK\Videos\Server-Ai-Optimizer-2.5.jar!\org\nyxiikcz\serveraioptimizer\bstats\charts\SimpleBarChart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */