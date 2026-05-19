/*    */ package org.nyxiikcz.serveraioptimizer.bstats.charts;
/*    */ 
/*    */ import java.util.function.BiConsumer;
/*    */ import org.nyxiikcz.serveraioptimizer.bstats.json.JsonObjectBuilder;
/*    */ 
/*    */ 
/*    */ public abstract class CustomChart
/*    */ {
/*    */   private final String chartId;
/*    */   
/*    */   protected CustomChart(String paramString) {
/* 12 */     if (paramString == null) {
/* 13 */       throw new IllegalArgumentException("chartId must not be null");
/*    */     }
/* 15 */     this.chartId = paramString;
/*    */   }
/*    */   
/*    */   public JsonObjectBuilder.JsonObject getRequestJsonObject(BiConsumer<String, Throwable> paramBiConsumer, boolean paramBoolean) {
/* 19 */     JsonObjectBuilder jsonObjectBuilder = new JsonObjectBuilder();
/* 20 */     jsonObjectBuilder.appendField("chartId", this.chartId);
/*    */     try {
/* 22 */       JsonObjectBuilder.JsonObject jsonObject = getChartData();
/* 23 */       if (jsonObject == null)
/*    */       {
/* 25 */         return null;
/*    */       }
/* 27 */       jsonObjectBuilder.appendField("data", jsonObject);
/* 28 */     } catch (Throwable throwable) {
/* 29 */       if (paramBoolean)
/*    */       {
/* 31 */         paramBiConsumer.accept("Failed to get data for custom chart with id " + this.chartId, throwable);
/*    */       }
/* 33 */       return null;
/*    */     } 
/* 35 */     return jsonObjectBuilder.build();
/*    */   }
/*    */   
/*    */   protected abstract JsonObjectBuilder.JsonObject getChartData();
/*    */ }


/* Location:              C:\Users\IK\Videos\Server-Ai-Optimizer-2.5.jar!\org\nyxiikcz\serveraioptimizer\bstats\charts\CustomChart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */