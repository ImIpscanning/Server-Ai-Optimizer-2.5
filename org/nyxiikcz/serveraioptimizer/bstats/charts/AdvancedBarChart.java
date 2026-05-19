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
/*    */ public class AdvancedBarChart
/*    */   extends CustomChart
/*    */ {
/*    */   private final Callable<Map<String, int[]>> callable;
/*    */   
/*    */   public AdvancedBarChart(String paramString, Callable<Map<String, int[]>> paramCallable) {
/* 19 */     super(paramString);
/* 20 */     this.callable = paramCallable;
/*    */   }
/*    */ 
/*    */   
/*    */   protected JsonObjectBuilder.JsonObject getChartData() {
/* 25 */     JsonObjectBuilder jsonObjectBuilder = new JsonObjectBuilder();
/* 26 */     Map map = this.callable.call();
/* 27 */     if (map == null || map.isEmpty())
/*    */     {
/* 29 */       return null;
/*    */     }
/* 31 */     boolean bool = true;
/* 32 */     for (Map.Entry entry : map.entrySet()) {
/* 33 */       if (((int[])entry.getValue()).length == 0) {
/*    */         continue;
/*    */       }
/* 36 */       bool = false;
/* 37 */       jsonObjectBuilder.appendField((String)entry.getKey(), (int[])entry.getValue());
/*    */     } 
/* 39 */     if (bool)
/*    */     {
/* 41 */       return null;
/*    */     }
/*    */     
/* 44 */     return (new JsonObjectBuilder())
/* 45 */       .appendField("values", jsonObjectBuilder.build())
/* 46 */       .build();
/*    */   }
/*    */ }


/* Location:              C:\Users\IK\Videos\Server-Ai-Optimizer-2.5.jar!\org\nyxiikcz\serveraioptimizer\bstats\charts\AdvancedBarChart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */