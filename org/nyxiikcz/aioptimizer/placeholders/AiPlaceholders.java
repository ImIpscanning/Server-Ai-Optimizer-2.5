/*     */ package org.nyxiikcz.aioptimizer.placeholders;
/*     */ 
/*     */ import com.sun.management.OperatingSystemMXBean;
/*     */ import java.lang.management.ManagementFactory;
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import me.clip.placeholderapi.expansion.PlaceholderExpansion;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.nyxiikcz.aioptimizer.ServerAiOptimizer;
/*     */ 
/*     */ public class AiPlaceholders
/*     */   extends PlaceholderExpansion
/*     */ {
/*     */   private final ServerAiOptimizer plugin;
/*  18 */   private final DecimalFormat df = new DecimalFormat("0.00");
/*     */   
/*     */   public AiPlaceholders(ServerAiOptimizer paramServerAiOptimizer) {
/*  21 */     this.plugin = paramServerAiOptimizer;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public String getIdentifier() {
/*  26 */     return "ai";
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public String getAuthor() {
/*  31 */     return "NyxiikCZ";
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public String getVersion() {
/*  36 */     return this.plugin.getDescription().getVersion();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean persist() {
/*  41 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String onPlaceholderRequest(Player paramPlayer, @NotNull String paramString) {
/*  50 */     if (paramString.equalsIgnoreCase("tps")) {
/*  51 */       return this.df.format(this.plugin.getMonitor().getStatsTps());
/*     */     }
/*     */ 
/*     */     
/*  55 */     if (paramString.equalsIgnoreCase("tps_formatted")) {
/*  56 */       return this.plugin.getMonitor().getFormattedTps();
/*     */     }
/*     */ 
/*     */     
/*  60 */     if (paramString.equalsIgnoreCase("mspt")) {
/*  61 */       return this.df.format(Bukkit.getAverageTickTime());
/*     */     }
/*     */ 
/*     */     
/*  65 */     if (paramString.equalsIgnoreCase("status")) {
/*  66 */       return this.plugin.getMonitor().getFormattedStatus();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  71 */     if (paramString.equalsIgnoreCase("stress_formatted")) {
/*  72 */       return this.plugin.getMonitor().getFormattedStress();
/*     */     }
/*     */ 
/*     */     
/*  76 */     if (paramString.equalsIgnoreCase("uptime")) {
/*  77 */       long l1 = ManagementFactory.getRuntimeMXBean().getUptime();
/*  78 */       long l2 = TimeUnit.MILLISECONDS.toHours(l1);
/*  79 */       long l3 = TimeUnit.MILLISECONDS.toMinutes(l1) % 60L;
/*  80 */       return String.format("%02dh %02dm", new Object[] { Long.valueOf(l2), Long.valueOf(l3) });
/*     */     } 
/*     */ 
/*     */     
/*  84 */     if (paramString.equalsIgnoreCase("view_distance")) {
/*  85 */       if (paramPlayer != null && paramPlayer.isOnline())
/*     */       {
/*  87 */         return String.valueOf(paramPlayer.getWorld().getViewDistance());
/*     */       }
/*     */       
/*  90 */       return String.valueOf(((World)Bukkit.getWorlds().get(0)).getViewDistance());
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  96 */     if (paramString.equalsIgnoreCase("cpu")) {
/*  97 */       double d = 0.0D;
/*     */       try {
/*  99 */         OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean)ManagementFactory.getOperatingSystemMXBean();
/* 100 */         d = operatingSystemMXBean.getSystemCpuLoad();
/* 101 */       } catch (Exception exception) {}
/* 102 */       return (d < 0.0D) ? "N/A" : (this.df.format(d * 100.0D) + "%");
/*     */     } 
/*     */ 
/*     */     
/* 106 */     if (paramString.equalsIgnoreCase("ram")) {
/* 107 */       Runtime runtime = Runtime.getRuntime();
/* 108 */       long l1 = runtime.maxMemory() / 1024L / 1024L;
/* 109 */       long l2 = runtime.totalMemory() / 1024L / 1024L;
/* 110 */       long l3 = l2 - runtime.freeMemory() / 1024L / 1024L;
/* 111 */       return "" + l3 + " / " + l3 + " MB";
/*     */     } 
/*     */ 
/*     */     
/* 115 */     if (paramString.equalsIgnoreCase("entities")) {
/* 116 */       int i = 0;
/* 117 */       for (World world : Bukkit.getWorlds()) i += world.getEntityCount(); 
/* 118 */       return String.valueOf(i);
/*     */     } 
/*     */ 
/*     */     
/* 122 */     if (paramString.equalsIgnoreCase("chunks")) {
/* 123 */       int i = 0;
/* 124 */       for (World world : Bukkit.getWorlds()) i += (world.getLoadedChunks()).length; 
/* 125 */       return String.valueOf(i);
/*     */     } 
/*     */     
/* 128 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\IK\Videos\Server-Ai-Optimizer-2.5.jar!\org\nyxiikcz\aioptimizer\placeholders\AiPlaceholders.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */