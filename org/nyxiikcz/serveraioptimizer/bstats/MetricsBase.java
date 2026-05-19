/*     */ package org.nyxiikcz.serveraioptimizer.bstats;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.net.URL;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.HashSet;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ScheduledExecutorService;
/*     */ import java.util.concurrent.ScheduledThreadPoolExecutor;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.zip.GZIPOutputStream;
/*     */ import javax.net.ssl.HttpsURLConnection;
/*     */ import org.nyxiikcz.serveraioptimizer.bstats.charts.CustomChart;
/*     */ import org.nyxiikcz.serveraioptimizer.bstats.json.JsonObjectBuilder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MetricsBase
/*     */ {
/*     */   public static final String METRICS_VERSION = "3.1.0";
/*     */   private static final String REPORT_URL = "https://bStats.org/api/v2/data/%s";
/*     */   private final ScheduledExecutorService scheduler;
/*     */   private final String platform;
/*     */   private final String serverUuid;
/*     */   private final int serviceId;
/*     */   private final Consumer<JsonObjectBuilder> appendPlatformDataConsumer;
/*     */   private final Consumer<JsonObjectBuilder> appendServiceDataConsumer;
/*     */   private final Consumer<Runnable> submitTaskConsumer;
/*     */   private final Supplier<Boolean> checkServiceEnabledSupplier;
/*     */   private final BiConsumer<String, Throwable> errorLogger;
/*     */   private final Consumer<String> infoLogger;
/*     */   private final boolean logErrors;
/*     */   private final boolean logSentData;
/*     */   private final boolean logResponseStatusText;
/*  51 */   private final Set<CustomChart> customCharts = new HashSet<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean enabled;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MetricsBase(String paramString1, String paramString2, int paramInt, boolean paramBoolean1, Consumer<JsonObjectBuilder> paramConsumer1, Consumer<JsonObjectBuilder> paramConsumer2, Consumer<Runnable> paramConsumer, Supplier<Boolean> paramSupplier, BiConsumer<String, Throwable> paramBiConsumer, Consumer<String> paramConsumer3, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5) {
/* 101 */     ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1, paramRunnable -> {
/*     */           Thread thread = new Thread(paramRunnable, "bStats-Metrics");
/*     */ 
/*     */           
/*     */           thread.setDaemon(true);
/*     */ 
/*     */           
/*     */           return thread;
/*     */         });
/*     */     
/* 111 */     scheduledThreadPoolExecutor.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
/* 112 */     this.scheduler = scheduledThreadPoolExecutor;
/*     */     
/* 114 */     this.platform = paramString1;
/* 115 */     this.serverUuid = paramString2;
/* 116 */     this.serviceId = paramInt;
/* 117 */     this.enabled = paramBoolean1;
/* 118 */     this.appendPlatformDataConsumer = paramConsumer1;
/* 119 */     this.appendServiceDataConsumer = paramConsumer2;
/* 120 */     this.submitTaskConsumer = paramConsumer;
/* 121 */     this.checkServiceEnabledSupplier = paramSupplier;
/* 122 */     this.errorLogger = paramBiConsumer;
/* 123 */     this.infoLogger = paramConsumer3;
/* 124 */     this.logErrors = paramBoolean2;
/* 125 */     this.logSentData = paramBoolean3;
/* 126 */     this.logResponseStatusText = paramBoolean4;
/*     */     
/* 128 */     if (!paramBoolean5) {
/* 129 */       checkRelocation();
/*     */     }
/*     */     
/* 132 */     if (paramBoolean1)
/*     */     {
/* 134 */       startSubmitting();
/*     */     }
/*     */   }
/*     */   
/*     */   public void addCustomChart(CustomChart paramCustomChart) {
/* 139 */     this.customCharts.add(paramCustomChart);
/*     */   }
/*     */   
/*     */   public void shutdown() {
/* 143 */     this.scheduler.shutdown();
/*     */   }
/*     */   
/*     */   private void startSubmitting() {
/* 147 */     Runnable runnable = () -> {
/*     */         if (!this.enabled || !((Boolean)this.checkServiceEnabledSupplier.get()).booleanValue()) {
/*     */           this.scheduler.shutdown();
/*     */ 
/*     */ 
/*     */           
/*     */           return;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/*     */         if (this.submitTaskConsumer != null) {
/*     */           this.submitTaskConsumer.accept(this::submitData);
/*     */         } else {
/*     */           submitData();
/*     */         } 
/*     */       };
/*     */ 
/*     */ 
/*     */     
/* 167 */     long l1 = (long)(60000.0D * (3.0D + Math.random() * 3.0D));
/* 168 */     long l2 = (long)(60000.0D * Math.random() * 30.0D);
/* 169 */     this.scheduler.schedule(runnable, l1, TimeUnit.MILLISECONDS);
/* 170 */     this.scheduler.scheduleAtFixedRate(runnable, l1 + l2, 1800000L, TimeUnit.MILLISECONDS);
/*     */   }
/*     */   
/*     */   private void submitData() {
/* 174 */     JsonObjectBuilder jsonObjectBuilder1 = new JsonObjectBuilder();
/* 175 */     this.appendPlatformDataConsumer.accept(jsonObjectBuilder1);
/*     */     
/* 177 */     JsonObjectBuilder jsonObjectBuilder2 = new JsonObjectBuilder();
/* 178 */     this.appendServiceDataConsumer.accept(jsonObjectBuilder2);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 183 */     JsonObjectBuilder.JsonObject[] arrayOfJsonObject = (JsonObjectBuilder.JsonObject[])this.customCharts.stream().map(paramCustomChart -> paramCustomChart.getRequestJsonObject(this.errorLogger, this.logErrors)).filter(Objects::nonNull).toArray(paramInt -> new JsonObjectBuilder.JsonObject[paramInt]);
/*     */     
/* 185 */     jsonObjectBuilder2.appendField("id", this.serviceId);
/* 186 */     jsonObjectBuilder2.appendField("customCharts", arrayOfJsonObject);
/* 187 */     jsonObjectBuilder1.appendField("service", jsonObjectBuilder2.build());
/* 188 */     jsonObjectBuilder1.appendField("serverUUID", this.serverUuid);
/* 189 */     jsonObjectBuilder1.appendField("metricsVersion", "3.1.0");
/*     */     
/* 191 */     JsonObjectBuilder.JsonObject jsonObject = jsonObjectBuilder1.build();
/*     */     
/* 193 */     this.scheduler.execute(() -> {
/*     */           
/*     */           try {
/*     */             sendData(paramJsonObject);
/* 197 */           } catch (Exception exception) {
/*     */             if (this.logErrors) {
/*     */               this.errorLogger.accept("Could not submit bStats metrics data", exception);
/*     */             }
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private void sendData(JsonObjectBuilder.JsonObject paramJsonObject) {
/* 207 */     if (this.logSentData) {
/* 208 */       this.infoLogger.accept("Sent bStats metrics data: " + paramJsonObject.toString());
/*     */     }
/*     */     
/* 211 */     String str = String.format("https://bStats.org/api/v2/data/%s", new Object[] { this.platform });
/* 212 */     HttpsURLConnection httpsURLConnection = (HttpsURLConnection)(new URL(str)).openConnection();
/*     */ 
/*     */     
/* 215 */     byte[] arrayOfByte = compress(paramJsonObject.toString());
/*     */     
/* 217 */     httpsURLConnection.setRequestMethod("POST");
/* 218 */     httpsURLConnection.addRequestProperty("Accept", "application/json");
/* 219 */     httpsURLConnection.addRequestProperty("Connection", "close");
/* 220 */     httpsURLConnection.addRequestProperty("Content-Encoding", "gzip");
/* 221 */     httpsURLConnection.addRequestProperty("Content-Length", String.valueOf(arrayOfByte.length));
/* 222 */     httpsURLConnection.setRequestProperty("Content-Type", "application/json");
/* 223 */     httpsURLConnection.setRequestProperty("User-Agent", "Metrics-Service/1");
/*     */     
/* 225 */     httpsURLConnection.setDoOutput(true);
/* 226 */     DataOutputStream dataOutputStream = new DataOutputStream(httpsURLConnection.getOutputStream()); 
/* 227 */     try { dataOutputStream.write(arrayOfByte);
/* 228 */       dataOutputStream.close(); } catch (Throwable throwable) { try { dataOutputStream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */        throw throwable; }
/* 230 */      StringBuilder stringBuilder = new StringBuilder();
/* 231 */     BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream())); 
/*     */     try { String str1;
/* 233 */       while ((str1 = bufferedReader.readLine()) != null) {
/* 234 */         stringBuilder.append(str1);
/*     */       }
/* 236 */       bufferedReader.close(); } catch (Throwable throwable) { try { bufferedReader.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */        throw throwable; }
/* 238 */      if (this.logResponseStatusText) {
/* 239 */       this.infoLogger.accept("Sent data to bStats and received response: " + stringBuilder);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkRelocation() {
/* 248 */     if (System.getProperty("bstats.relocatecheck") == null || 
/* 249 */       !System.getProperty("bstats.relocatecheck").equals("false")) {
/*     */ 
/*     */       
/* 252 */       String str1 = new String(new byte[] { 111, 114, 103, 46, 98, 115, 116, 97, 116, 115 });
/*     */       
/* 254 */       String str2 = new String(new byte[] { 121, 111, 117, 114, 46, 112, 97, 99, 107, 97, 103, 101 });
/*     */ 
/*     */ 
/*     */       
/* 258 */       if (MetricsBase.class.getPackage().getName().startsWith(str1) || MetricsBase.class
/* 259 */         .getPackage().getName().startsWith(str2)) {
/* 260 */         throw new IllegalStateException("bStats Metrics class has not been relocated correctly!");
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static byte[] compress(String paramString) {
/* 272 */     if (paramString == null) {
/* 273 */       return null;
/*     */     }
/* 275 */     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
/* 276 */     GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream); 
/* 277 */     try { gZIPOutputStream.write(paramString.getBytes(StandardCharsets.UTF_8));
/* 278 */       gZIPOutputStream.close(); } catch (Throwable throwable) { try { gZIPOutputStream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }
/* 279 */      return byteArrayOutputStream.toByteArray();
/*     */   }
/*     */ }


/* Location:              C:\Users\IK\Videos\Server-Ai-Optimizer-2.5.jar!\org\nyxiikcz\serveraioptimizer\bstats\MetricsBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */