/*     */ package org.nyxiikcz.serveraioptimizer.bstats.bukkit;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.Collection;
/*     */ import java.util.Objects;
/*     */ import java.util.UUID;
/*     */ import java.util.logging.Level;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.configuration.file.YamlConfiguration;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.nyxiikcz.serveraioptimizer.bstats.MetricsBase;
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
/*     */ public class Metrics
/*     */ {
/*     */   private final Plugin plugin;
/*     */   private final MetricsBase metricsBase;
/*     */   
/*     */   public Metrics(Plugin paramPlugin, int paramInt) {
/*  31 */     this.plugin = paramPlugin;
/*     */ 
/*     */     
/*  34 */     File file1 = new File(paramPlugin.getDataFolder().getParentFile(), "bStats");
/*  35 */     File file2 = new File(file1, "config.yml");
/*  36 */     YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file2);
/*     */     
/*  38 */     if (!yamlConfiguration.isSet("serverUuid")) {
/*  39 */       yamlConfiguration.addDefault("enabled", Boolean.valueOf(true));
/*  40 */       yamlConfiguration.addDefault("serverUuid", UUID.randomUUID().toString());
/*  41 */       yamlConfiguration.addDefault("logFailedRequests", Boolean.valueOf(false));
/*  42 */       yamlConfiguration.addDefault("logSentData", Boolean.valueOf(false));
/*  43 */       yamlConfiguration.addDefault("logResponseStatusText", Boolean.valueOf(false));
/*     */ 
/*     */       
/*  46 */       yamlConfiguration.options().header("bStats (https://bStats.org) collects some basic information for plugin authors, like how\nmany people use their plugin and their total player count. It's recommended to keep bStats\nenabled, but if you're not comfortable with this, you can turn this setting off. There is no\nperformance penalty associated with having metrics enabled, and data sent to bStats is fully\nanonymous.")
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  52 */         .copyDefaults(true);
/*     */       try {
/*  54 */         yamlConfiguration.save(file2);
/*  55 */       } catch (IOException iOException) {}
/*     */     } 
/*     */ 
/*     */     
/*  59 */     boolean bool1 = yamlConfiguration.getBoolean("enabled", true);
/*  60 */     String str = yamlConfiguration.getString("serverUuid");
/*  61 */     boolean bool2 = yamlConfiguration.getBoolean("logFailedRequests", false);
/*  62 */     boolean bool3 = yamlConfiguration.getBoolean("logSentData", false);
/*  63 */     boolean bool4 = yamlConfiguration.getBoolean("logResponseStatusText", false);
/*     */     
/*  65 */     boolean bool = false;
/*     */     try {
/*  67 */       bool = (Class.forName("io.papermc.paper.threadedregions.RegionizedServer") != null) ? true : false;
/*  68 */     } catch (Exception exception) {}
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
/*  79 */     Objects.requireNonNull(paramPlugin); this.metricsBase = new MetricsBase("bukkit", str, paramInt, bool1, this::appendPlatformData, this::appendServiceData, bool ? null : (paramRunnable -> Bukkit.getScheduler().runTask(paramPlugin, paramRunnable)), paramPlugin::isEnabled, (paramString, paramThrowable) -> this.plugin.getLogger().log(Level.WARNING, paramString, paramThrowable), paramString -> this.plugin.getLogger().log(Level.INFO, paramString), bool2, bool3, bool4, false);
/*     */   }
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
/*     */   public void shutdown() {
/*  94 */     this.metricsBase.shutdown();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addCustomChart(CustomChart paramCustomChart) {
/* 103 */     this.metricsBase.addCustomChart(paramCustomChart);
/*     */   }
/*     */   
/*     */   private void appendPlatformData(JsonObjectBuilder paramJsonObjectBuilder) {
/* 107 */     paramJsonObjectBuilder.appendField("playerAmount", getPlayerAmount());
/* 108 */     paramJsonObjectBuilder.appendField("onlineMode", Bukkit.getOnlineMode() ? 1 : 0);
/* 109 */     paramJsonObjectBuilder.appendField("bukkitVersion", Bukkit.getVersion());
/* 110 */     paramJsonObjectBuilder.appendField("bukkitName", Bukkit.getName());
/*     */     
/* 112 */     paramJsonObjectBuilder.appendField("javaVersion", System.getProperty("java.version"));
/* 113 */     paramJsonObjectBuilder.appendField("osName", System.getProperty("os.name"));
/* 114 */     paramJsonObjectBuilder.appendField("osArch", System.getProperty("os.arch"));
/* 115 */     paramJsonObjectBuilder.appendField("osVersion", System.getProperty("os.version"));
/* 116 */     paramJsonObjectBuilder.appendField("coreCount", Runtime.getRuntime().availableProcessors());
/*     */   }
/*     */   
/*     */   private void appendServiceData(JsonObjectBuilder paramJsonObjectBuilder) {
/* 120 */     paramJsonObjectBuilder.appendField("pluginVersion", this.plugin.getDescription().getVersion());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int getPlayerAmount() {
/*     */     try {
/* 127 */       Method method = Class.forName("org.bukkit.Server").getMethod("getOnlinePlayers", new Class[0]);
/* 128 */       return method.getReturnType().equals(Collection.class) ? (
/* 129 */         (Collection)method.invoke(Bukkit.getServer(), new Object[0])).size() : (
/* 130 */         (Player[])method.invoke(Bukkit.getServer(), new Object[0])).length;
/* 131 */     } catch (Exception exception) {
/* 132 */       return Bukkit.getOnlinePlayers().size();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\IK\Videos\Server-Ai-Optimizer-2.5.jar!\org\nyxiikcz\serveraioptimizer\bstats\bukkit\Metrics.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */