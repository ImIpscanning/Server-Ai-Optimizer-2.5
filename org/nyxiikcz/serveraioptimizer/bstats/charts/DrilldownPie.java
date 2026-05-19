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
/*    */ public class DrilldownPie
/*    */   extends CustomChart
/*    */ {
/*    */   private final Callable<Map<String, Map<String, Integer>>> callable;
/*    */   
/*    */   public DrilldownPie(String paramString, Callable<Map<String, Map<String, Integer>>> paramCallable) {
/* 19 */     super(paramString);
/* 20 */     this.callable = paramCallable;
/*    */   }
/*    */ 
/*    */   
/*    */   public JsonObjectBuilder.JsonObject getChartData() {
/* 25 */     JsonObjectBuilder jsonObjectBuilder = new JsonObjectBuilder();
/*    */     
/* 27 */     Map map = this.callable.call();
/* 28 */     if (map == null || map.isEmpty())
/*    */     {
/* 30 */       return null;
/*    */     }
/* 32 */     boolean bool = true;
/* 33 */     for (Map.Entry entry : map.entrySet()) {
/* 34 */       JsonObjectBuilder jsonObjectBuilder1 = new JsonObjectBuilder();
/* 35 */       boolean bool1 = true;
/* 36 */       for (Map.Entry entry1 : ((Map)map.get(entry.getKey())).entrySet()) {
/* 37 */         jsonObjectBuilder1.appendField((String)entry1.getKey(), ((Integer)entry1.getValue()).intValue());
/* 38 */         bool1 = false;
/*    */       } 
/* 40 */       if (!bool1) {
/* 41 */         bool = false;
/* 42 */         jsonObjectBuilder.appendField((String)entry.getKey(), jsonObjectBuilder1.build());
/*    */       } 
/*    */     } 
/* 45 */     if (bool)
/*    */     {
/* 47 */       return null;
/*    */     }
/*    */     
/* 50 */     return (new JsonObjectBuilder())
/* 51 */       .appendField("values", jsonObjectBuilder.build())
/* 52 */       .build();
/*    */   }
/*    */ }


/* Location:              C:\Users\IK\Videos\Server-Ai-Optimizer-2.5.jar!\org\nyxiikcz\serveraioptimizer\bstats\charts\DrilldownPie.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */