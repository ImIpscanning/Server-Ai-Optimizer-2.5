/*     */ package org.nyxiikcz.aioptimizer.systems;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.nyxiikcz.aioptimizer.ServerAiOptimizer;
/*     */ 
/*     */ public class StressLogger
/*     */ {
/*     */   private final ServerAiOptimizer plugin;
/*     */   private final File logFile;
/*  18 */   private long lastLogTime = 0L;
/*  19 */   private final long COOLDOWN_MS = 60000L;
/*     */   
/*     */   private static final int W_TIME = 21;
/*     */   
/*     */   private static final int W_STRESS = 10;
/*     */   private static final int W_STATUS = 12;
/*     */   private static final int W_TPS = 8;
/*     */   private static final int W_MSPT = 10;
/*     */   private static final int W_CPU = 8;
/*     */   private static final int W_RAM = 8;
/*     */   private static final int W_CHUNKS = 8;
/*     */   private static final int W_ENTITIES = 10;
/*     */   private static final int W_PLAYERS = 9;
/*     */   
/*     */   public StressLogger(ServerAiOptimizer paramServerAiOptimizer) {
/*  34 */     this.plugin = paramServerAiOptimizer;
/*  35 */     this.logFile = new File(paramServerAiOptimizer.getDataFolder(), "log.txt");
/*  36 */     createFileIfNotExists();
/*     */   }
/*     */   
/*     */   private void createFileIfNotExists() {
/*  40 */     if (!this.plugin.getDataFolder().exists()) {
/*  41 */       this.plugin.getDataFolder().mkdirs();
/*     */     }
/*  43 */     if (!this.logFile.exists()) {
/*     */       try {
/*  45 */         this.logFile.createNewFile();
/*     */ 
/*     */         
/*  48 */         log("=== AI OPTIMIZER STRESS LOG CREATED ===");
/*     */ 
/*     */         
/*  51 */         String str = String.format("|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|", new Object[] {
/*  52 */               centerText("Time", 21), 
/*  53 */               centerText("Stress", 10), 
/*  54 */               centerText("Status", 12), 
/*  55 */               centerText("TPS", 8), 
/*  56 */               centerText("MSPT", 10), 
/*  57 */               centerText("CPU", 8), 
/*  58 */               centerText("RAM", 8), 
/*  59 */               centerText("Chunks", 8), 
/*  60 */               centerText("Entities", 10), 
/*  61 */               centerText("Players", 9)
/*     */             });
/*     */         
/*  64 */         log(str);
/*     */         
/*  66 */         log(str.replaceAll("[^|]", "-"));
/*     */       }
/*  68 */       catch (IOException iOException) {
/*  69 */         this.plugin.getLogger().warning("Could not create log.txt!");
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void logHighStress(double paramDouble1, String paramString, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5) {
/*  76 */     if (System.currentTimeMillis() - this.lastLogTime < 60000L) {
/*     */       return;
/*     */     }
/*  79 */     this.lastLogTime = System.currentTimeMillis();
/*     */ 
/*     */     
/*  82 */     int i = 0;
/*  83 */     int j = 0;
/*  84 */     for (World world : Bukkit.getWorlds()) {
/*  85 */       i += (world.getLoadedChunks()).length;
/*  86 */       j += world.getEntityCount();
/*     */     } 
/*  88 */     int k = Bukkit.getOnlinePlayers().size();
/*     */     
/*  90 */     String str1 = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
/*     */ 
/*     */     
/*  93 */     String str2 = String.format("|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|", new Object[] {
/*  94 */           centerText(str1, 21), 
/*  95 */           centerText(String.format("%.1f%%", new Object[] { Double.valueOf(paramDouble1) }), 10), 
/*  96 */           centerText(paramString, 12), 
/*  97 */           centerText(String.format("%.2f", new Object[] { Double.valueOf(paramDouble2) }), 8), 
/*  98 */           centerText(String.format("%.1fms", new Object[] { Double.valueOf(paramDouble3) }), 10), 
/*  99 */           centerText(String.format("%.1f%%", new Object[] { Double.valueOf(paramDouble4) }), 8), 
/* 100 */           centerText(String.format("%.1f%%", new Object[] { Double.valueOf(paramDouble5) }), 8), 
/* 101 */           centerText(String.valueOf(i), 8), 
/* 102 */           centerText(String.valueOf(j), 10), 
/* 103 */           centerText(String.valueOf(k), 9)
/*     */         });
/*     */ 
/*     */     
/* 107 */     logAsync(str2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String centerText(String paramString, int paramInt) {
/* 115 */     if (paramString == null) paramString = "null";
/*     */ 
/*     */     
/* 118 */     if (paramString.length() >= paramInt) {
/* 119 */       return paramString.substring(0, paramInt);
/*     */     }
/*     */     
/* 122 */     int i = paramInt - paramString.length();
/* 123 */     int j = i / 2;
/* 124 */     int k = i - j;
/*     */     
/* 126 */     StringBuilder stringBuilder = new StringBuilder();
/*     */     
/*     */     byte b;
/* 129 */     for (b = 0; b < j; b++) {
/* 130 */       stringBuilder.append(" ");
/*     */     }
/*     */ 
/*     */     
/* 134 */     stringBuilder.append(paramString);
/*     */ 
/*     */     
/* 137 */     for (b = 0; b < k; b++) {
/* 138 */       stringBuilder.append(" ");
/*     */     }
/*     */     
/* 141 */     return stringBuilder.toString();
/*     */   }
/*     */   
/*     */   private void logAsync(String paramString) {
/* 145 */     Bukkit.getScheduler().runTaskAsynchronously((Plugin)this.plugin, () -> log(paramString));
/*     */   }
/*     */   private void log(String paramString) {
/*     */     
/* 149 */     try { FileWriter fileWriter = new FileWriter(this.logFile, true); 
/* 150 */       try { PrintWriter printWriter = new PrintWriter(fileWriter); 
/* 151 */         try { printWriter.println(paramString);
/* 152 */           printWriter.close(); } catch (Throwable throwable) { try { printWriter.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  fileWriter.close(); } catch (Throwable throwable) { try { fileWriter.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (IOException iOException)
/* 153 */     { iOException.printStackTrace(); }
/*     */   
/*     */   }
/*     */ }


/* Location:              C:\Users\IK\Videos\Server-Ai-Optimizer-2.5.jar!\org\nyxiikcz\aioptimizer\systems\StressLogger.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */