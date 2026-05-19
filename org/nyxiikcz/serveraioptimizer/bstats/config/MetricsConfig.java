/*     */ package org.nyxiikcz.serveraioptimizer.bstats.config;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileReader;
/*     */ import java.io.FileWriter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import java.util.UUID;
/*     */ import java.util.regex.Pattern;
/*     */ import java.util.stream.Collector;
/*     */ import java.util.stream.Collectors;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MetricsConfig
/*     */ {
/*     */   private final File file;
/*     */   private final boolean defaultEnabled;
/*     */   private String serverUUID;
/*     */   private boolean enabled;
/*     */   private boolean logErrors;
/*     */   private boolean logSentData;
/*     */   private boolean logResponseStatusText;
/*     */   private boolean didExistBefore = true;
/*     */   
/*     */   public MetricsConfig(File paramFile, boolean paramBoolean) {
/*  35 */     this.file = paramFile;
/*  36 */     this.defaultEnabled = paramBoolean;
/*     */     
/*  38 */     setupConfig();
/*     */   }
/*     */   
/*     */   public String getServerUUID() {
/*  42 */     return this.serverUUID;
/*     */   }
/*     */   
/*     */   public boolean isEnabled() {
/*  46 */     return this.enabled;
/*     */   }
/*     */   
/*     */   public boolean isLogErrorsEnabled() {
/*  50 */     return this.logErrors;
/*     */   }
/*     */   
/*     */   public boolean isLogSentDataEnabled() {
/*  54 */     return this.logSentData;
/*     */   }
/*     */   
/*     */   public boolean isLogResponseStatusTextEnabled() {
/*  58 */     return this.logResponseStatusText;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean didExistBefore() {
/*  67 */     return this.didExistBefore;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setupConfig() {
/*  74 */     if (!this.file.exists()) {
/*  75 */       this.didExistBefore = false;
/*  76 */       writeConfig();
/*     */     } 
/*  78 */     readConfig();
/*  79 */     if (this.serverUUID == null) {
/*     */       
/*  81 */       writeConfig();
/*  82 */       readConfig();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeConfig() {
/*  90 */     ArrayList<String> arrayList = new ArrayList();
/*  91 */     arrayList.add("# bStats (https://bStats.org) collects some basic information for plugin authors, like");
/*  92 */     arrayList.add("# how many people use their plugin and their total player count. It's recommended to keep");
/*  93 */     arrayList.add("# bStats enabled, but if you're not comfortable with this, you can turn this setting off.");
/*  94 */     arrayList.add("# There is no performance penalty associated with having metrics enabled, and data sent to");
/*  95 */     arrayList.add("# bStats is fully anonymous.");
/*  96 */     arrayList.add("enabled=" + this.defaultEnabled);
/*  97 */     arrayList.add("server-uuid=" + UUID.randomUUID().toString());
/*  98 */     arrayList.add("log-errors=false");
/*  99 */     arrayList.add("log-sent-data=false");
/* 100 */     arrayList.add("log-response-status-text=false");
/* 101 */     writeFile(this.file, arrayList);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readConfig() {
/* 108 */     List<String> list = readFile(this.file);
/* 109 */     if (list == null) {
/* 110 */       throw new AssertionError("Content of newly created file is null");
/*     */     }
/*     */     
/* 113 */     this.enabled = ((Boolean)getConfigValue("enabled", list).<Boolean>map("true"::equals).orElse(Boolean.valueOf(true))).booleanValue();
/* 114 */     this.serverUUID = getConfigValue("server-uuid", list).orElse(null);
/* 115 */     this.logErrors = ((Boolean)getConfigValue("log-errors", list).<Boolean>map("true"::equals).orElse(Boolean.valueOf(false))).booleanValue();
/* 116 */     this.logSentData = ((Boolean)getConfigValue("log-sent-data", list).<Boolean>map("true"::equals).orElse(Boolean.valueOf(false))).booleanValue();
/* 117 */     this.logResponseStatusText = ((Boolean)getConfigValue("log-response-status-text", list).<Boolean>map("true"::equals).orElse(Boolean.valueOf(false))).booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Optional<String> getConfigValue(String paramString, List<String> paramList) {
/* 128 */     return paramList.stream()
/* 129 */       .filter(paramString2 -> paramString2.startsWith(paramString1 + "="))
/* 130 */       .map(paramString2 -> paramString2.replaceFirst(Pattern.quote(paramString1 + "="), ""))
/* 131 */       .findFirst();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<String> readFile(File paramFile) {
/* 141 */     if (!paramFile.exists()) {
/* 142 */       return null;
/*     */     }
/*     */     
/* 145 */     FileReader fileReader = new FileReader(paramFile); 
/* 146 */     try { BufferedReader bufferedReader = new BufferedReader(fileReader);
/*     */       
/* 148 */       try { List<String> list = bufferedReader.lines().collect((Collector)Collectors.toList());
/* 149 */         bufferedReader.close(); fileReader.close(); return list; }
/*     */       catch (Throwable throwable) { try { bufferedReader.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */          throw throwable; }
/*     */        }
/*     */     catch (Throwable throwable) { try {
/*     */         fileReader.close();
/*     */       } catch (Throwable throwable1) {
/*     */         throwable.addSuppressed(throwable1);
/*     */       }  throw throwable; }
/* 159 */      } private void writeFile(File paramFile, List<String> paramList) { if (!paramFile.exists()) {
/* 160 */       paramFile.getParentFile().mkdirs();
/* 161 */       paramFile.createNewFile();
/*     */     } 
/*     */     
/* 164 */     FileWriter fileWriter = new FileWriter(paramFile); try {
/* 165 */       BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
/*     */       
/* 167 */       try { for (String str : paramList) {
/* 168 */           bufferedWriter.write(str);
/* 169 */           bufferedWriter.newLine();
/*     */         } 
/* 171 */         bufferedWriter.close(); } catch (Throwable throwable) { try { bufferedWriter.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  fileWriter.close();
/*     */     } catch (Throwable throwable) {
/*     */       try {
/*     */         fileWriter.close();
/*     */       } catch (Throwable throwable1) {
/*     */         throwable.addSuppressed(throwable1);
/*     */       } 
/*     */       throw throwable;
/*     */     }  }
/*     */ 
/*     */ }


/* Location:              C:\Users\IK\Videos\Server-Ai-Optimizer-2.5.jar!\org\nyxiikcz\serveraioptimizer\bstats\config\MetricsConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */