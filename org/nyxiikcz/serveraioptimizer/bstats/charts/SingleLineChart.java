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
/*    */ public class SingleLineChart
/*    */   extends CustomChart
/*    */ {
/*    */   private final Callable<Integer> callable;
/*    */   
/*    */   public SingleLineChart(String paramString, Callable<Integer> paramCallable) {
/* 18 */     super(paramString);
/* 19 */     this.callable = paramCallable;
/*    */   }
/*    */ 
/*    */   
/*    */   protected JsonObjectBuilder.JsonObject getChartData() {
/* 24 */     int i = ((Integer)this.callable.call()).intValue();
/* 25 */     if (i == 0)
/*    */     {
/* 27 */       return null;
/*    */     }
/* 29 */     return (new JsonObjectBuilder())
/* 30 */       .appendField("value", i)
/* 31 */       .build();
/*    */   }
/*    */ }


/* Location:              C:\Users\IK\Videos\Server-Ai-Optimizer-2.5.jar!\org\nyxiikcz\serveraioptimizer\bstats\charts\SingleLineChart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */