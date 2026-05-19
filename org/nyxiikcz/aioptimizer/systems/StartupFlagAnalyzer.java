/*    */ package org.nyxiikcz.aioptimizer.systems;
/*    */ 
/*    */ import java.lang.management.ManagementFactory;
/*    */ import java.lang.management.RuntimeMXBean;
/*    */ import java.util.List;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.nyxiikcz.aioptimizer.ServerAiOptimizer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StartupFlagAnalyzer
/*    */ {
/*    */   private final ServerAiOptimizer plugin;
/* 15 */   private final String CUSTOM_STARTUP_COMMAND = "java -Xms128M -XX:MaxRAMPercentage=95.0 -XX:+UseG1GC -XX:+ParallelRefProcEnabled -XX:MaxGCPauseMillis=200 -XX:+UnlockExperimentalVMOptions -XX:+DisableExplicitGC -XX:+AlwaysPreTouch -XX:G1NewSizePercent=30 -XX:G1MaxNewSizePercent=40 -XX:G1HeapRegionSize=8M -XX:G1ReservePercent=20 -XX:G1HeapWastePercent=5 -XX:G1MixedGCCountTarget=4 -XX:InitiatingHeapOccupancyPercent=15 -XX:G1MixedGCLiveThresholdPercent=90 -XX:G1RSetUpdatingPauseTimePercent=5 -XX:SurvivorRatio=32 -XX:+PerfDisableSharedMem -XX:MaxTenuringThreshold=1 -Dterminal.jline=false -Dterminal.ansi=true -jar server.jar";
/*    */   
/*    */   public StartupFlagAnalyzer(ServerAiOptimizer paramServerAiOptimizer) {
/* 18 */     this.plugin = paramServerAiOptimizer;
/*    */   }
/*    */   
/*    */   public void analyze() {
/* 22 */     RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
/* 23 */     List<String> list = runtimeMXBean.getInputArguments();
/* 24 */     String str = list.toString();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 30 */     boolean bool1 = str.contains("G1MixedGCCountTarget");
/*    */ 
/*    */     
/* 33 */     boolean bool2 = str.contains("+UseZGC");
/*    */ 
/*    */ 
/*    */     
/* 37 */     if (bool1 || bool2) {
/* 38 */       this.plugin.getServer().getConsoleSender().sendMessage(String.valueOf(ChatColor.GREEN) + "[ServerAiOptimizer] ✔ Optimized startup flags detected. Good job!");
/*    */ 
/*    */ 
/*    */       
/*    */       return;
/*    */     } 
/*    */ 
/*    */     
/* 46 */     sendBorder();
/* 47 */     this.plugin.getLogger().warning("⚠ WARNING! Your server is not using optimized startup flags!");
/* 48 */     this.plugin.getLogger().warning("Inefficient Java settings cause lag spikes and performance issues.");
/* 49 */     this.plugin.getLogger().warning("");
/* 50 */     this.plugin.getLogger().warning("✔ RECOMMENDED FIX (Copy this to your startup script):");
/* 51 */     this.plugin.getLogger().warning("");
/*    */ 
/*    */     
/* 54 */     this.plugin.getServer().getConsoleSender().sendMessage("java -Xms128M -XX:MaxRAMPercentage=95.0 -XX:+UseG1GC -XX:+ParallelRefProcEnabled -XX:MaxGCPauseMillis=200 -XX:+UnlockExperimentalVMOptions -XX:+DisableExplicitGC -XX:+AlwaysPreTouch -XX:G1NewSizePercent=30 -XX:G1MaxNewSizePercent=40 -XX:G1HeapRegionSize=8M -XX:G1ReservePercent=20 -XX:G1HeapWastePercent=5 -XX:G1MixedGCCountTarget=4 -XX:InitiatingHeapOccupancyPercent=15 -XX:G1MixedGCLiveThresholdPercent=90 -XX:G1RSetUpdatingPauseTimePercent=5 -XX:SurvivorRatio=32 -XX:+PerfDisableSharedMem -XX:MaxTenuringThreshold=1 -Dterminal.jline=false -Dterminal.ansi=true -jar server.jar");
/*    */     
/* 56 */     this.plugin.getLogger().warning("");
/* 57 */     sendBorder();
/*    */   }
/*    */   
/*    */   private void sendBorder() {
/* 61 */     this.plugin.getServer().getConsoleSender().sendMessage("======================================================");
/*    */   }
/*    */ }


/* Location:              C:\Users\IK\Videos\Server-Ai-Optimizer-2.5.jar!\org\nyxiikcz\aioptimizer\systems\StartupFlagAnalyzer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */