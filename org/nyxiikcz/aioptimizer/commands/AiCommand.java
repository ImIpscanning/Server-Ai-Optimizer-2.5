/*     */ package org.nyxiikcz.aioptimizer.commands;
/*     */ import com.sun.management.OperatingSystemMXBean;
/*     */ import java.io.File;
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Scanner;
/*     */ import net.md_5.bungee.api.ChatColor;
/*     */ import net.md_5.bungee.api.chat.BaseComponent;
/*     */ import net.md_5.bungee.api.chat.ClickEvent;
/*     */ import net.md_5.bungee.api.chat.HoverEvent;
/*     */ import net.md_5.bungee.api.chat.TextComponent;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Chunk;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.nyxiikcz.aioptimizer.ServerAiOptimizer;
/*     */ import org.nyxiikcz.aioptimizer.systems.ServerMonitor;
/*     */ 
/*     */ public class AiCommand implements CommandExecutor {
/*     */   private final ServerAiOptimizer plugin;
/*  26 */   private final DecimalFormat df = new DecimalFormat("0.00");
/*     */   
/*     */   public AiCommand(ServerAiOptimizer paramServerAiOptimizer) {
/*  29 */     this.plugin = paramServerAiOptimizer;
/*     */   }
/*     */   public boolean onCommand(@NotNull CommandSender paramCommandSender, @NotNull Command paramCommand, @NotNull String paramString, @NotNull String[] paramArrayOfString) {
/*     */     ServerMonitor.ServerStatus serverStatus;
/*     */     int i;
/*     */     String str;
/*  35 */     if (!paramCommandSender.hasPermission("ai.admin")) {
/*  36 */       paramCommandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin
/*  37 */             .getConfig().getString("messages.command-no-perm", "&cYou do not have permission.")));
/*  38 */       return true;
/*     */     } 
/*     */     
/*  41 */     if (paramArrayOfString.length == 0 || paramArrayOfString[0].equalsIgnoreCase("help")) {
/*  42 */       sendHelp(paramCommandSender);
/*  43 */       return true;
/*     */     } 
/*     */     
/*  46 */     if (paramArrayOfString[0].equalsIgnoreCase("serverinfo")) {
/*  47 */       sendServerInfo(paramCommandSender);
/*  48 */       return true;
/*     */     } 
/*     */ 
/*     */     
/*  52 */     if (paramArrayOfString[0].equalsIgnoreCase("ping") || paramArrayOfString[0].equalsIgnoreCase("lag")) {
/*     */ 
/*     */       
/*  55 */       double d = Bukkit.getAverageTickTime();
/*  56 */       int j = 0;
/*  57 */       byte b1 = 0;
/*  58 */       int k = -1;
/*  59 */       String str1 = "N/A";
/*     */       
/*  61 */       for (Player player : Bukkit.getOnlinePlayers()) {
/*  62 */         int m = player.getPing();
/*     */         
/*  64 */         j += m;
/*  65 */         b1++;
/*     */         
/*  67 */         if (m > k) {
/*  68 */           k = m;
/*  69 */           str1 = player.getName();
/*     */         } 
/*     */       } 
/*     */       
/*  73 */       byte b2 = (b1 > 0) ? (j / b1) : 0;
/*     */ 
/*     */       
/*  76 */       String str2 = (d < 40.0D) ? "&a" : ((d < 50.0D) ? "&e" : "&c");
/*  77 */       String str3 = (b2 < 60) ? "&a" : ((b2 < '') ? "&e" : "&c");
/*  78 */       String str4 = (k < 100) ? "&a" : ((k < 300) ? "&e" : "&c");
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  83 */       paramCommandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', " &8&m        &r &b&lPING &r&8&m        "));
/*     */ 
/*     */       
/*  86 */       if (paramCommandSender instanceof Player) {
/*  87 */         int m = ((Player)paramCommandSender).getPing();
/*  88 */         String str5 = (m < 60) ? "&a" : ((m < 150) ? "&e" : "&c");
/*  89 */         paramCommandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', " &7Your Latency: " + str5 + m + "ms"));
/*     */       } 
/*     */ 
/*     */       
/*  93 */       paramCommandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', " &7Network Avg: " + str3 + b2 + "ms &8(Players: " + b1 + ")"));
/*     */       
/*  95 */       if (b1 > 0) {
/*  96 */         paramCommandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', " &7Highest Ping: " + str4 + k + "ms &8(" + str1 + ")"));
/*     */       }
/*     */ 
/*     */       
/* 100 */       paramCommandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', " &7Server Tick: " + str2 + String.format("%.1f", new Object[] { Double.valueOf(d) }) + "ms &8(MSPT)"));
/*     */ 
/*     */       
/* 103 */       paramCommandSender.sendMessage("");
/* 104 */       if (d > 50.0D) {
/* 105 */         paramCommandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', " &c⚠ ISSUE: Server Overload (Low TPS)."));
/* 106 */       } else if (b2 > 100) {
/* 107 */         paramCommandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', " &e⚠ ISSUE: Player Network Lag."));
/*     */       } else {
/* 109 */         paramCommandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', " &a✔ System Stable."));
/*     */       } 
/*     */       
/* 112 */       paramCommandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', " &8&m                           "));
/* 113 */       return true;
/*     */     } 
/*     */     
/* 116 */     if (paramArrayOfString[0].equalsIgnoreCase("restart")) {
/*     */       
/* 118 */       if (!paramCommandSender.hasPermission("ai.restart")) {
/* 119 */         paramCommandSender.sendMessage(String.valueOf(ChatColor.RED) + "You don't have permission.");
/* 120 */         return true;
/*     */       } 
/*     */ 
/*     */       
/* 124 */       int j = 60;
/*     */ 
/*     */       
/* 127 */       if (paramArrayOfString.length > 1) {
/*     */         try {
/* 129 */           j = Integer.parseInt(paramArrayOfString[1]);
/* 130 */         } catch (NumberFormatException numberFormatException) {
/* 131 */           paramCommandSender.sendMessage(String.valueOf(ChatColor.RED) + "Invalid number. Use: /ai restart <seconds>");
/* 132 */           return true;
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/* 137 */       paramCommandSender.sendMessage(String.valueOf(ChatColor.GREEN) + "✔ Restart sequence initiated (" + String.valueOf(ChatColor.GREEN) + "s).");
/* 138 */       this.plugin.getRestartSystem().forceRestart(j);
/* 139 */       return true;
/*     */     } 
/*     */     
/* 142 */     if (paramArrayOfString[0].equalsIgnoreCase("scoreboard") || paramArrayOfString[0].equalsIgnoreCase("sb")) {
/* 143 */       if (paramCommandSender instanceof Player) {
/* 144 */         this.plugin.getAiScoreboard().toggleScoreboard((Player)paramCommandSender);
/*     */       } else {
/* 146 */         paramCommandSender.sendMessage(String.valueOf(ChatColor.RED) + "This command is only for players.");
/*     */       } 
/* 148 */       return true;
/*     */     } 
/*     */     
/* 151 */     switch (paramArrayOfString[0].toLowerCase())
/*     */     { case "status":
/* 153 */         sendStatus(paramCommandSender);
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
/* 219 */         return true;case "clear": serverStatus = this.plugin.getMonitor().getStatus(); i = this.plugin.getEntityOptimizer().performCleanup(serverStatus, true); str = this.plugin.getConfig().getString("settings.prefix", "&8[&bAI&8] "); if (i > 0) { String str1 = this.plugin.getConfig().getString("messages.manual-cleanup-success", "&a✔ Manual cleanup finished. Removed &e%count% &aentities."); str1 = str1.replace("%count%", String.valueOf(i)); paramCommandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', str + str)); } else { String str1 = this.plugin.getConfig().getString("messages.manual-cleanup-fail", "&c✖ No trash entities found to remove."); paramCommandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', str + str)); }  return true;case "checkupdate": paramCommandSender.sendMessage(String.valueOf(ChatColor.GRAY) + "Checking for updates..."); (new UpdateChecker(this.plugin, this.plugin.getResourceId())).getVersion(paramString -> { String str = this.plugin.getDescription().getVersion(); if (str.equalsIgnoreCase(paramString)) { paramCommandSender.sendMessage(String.valueOf(ChatColor.GREEN) + "✔ You are running the latest version (" + String.valueOf(ChatColor.GREEN) + ")."); } else { paramCommandSender.sendMessage(String.valueOf(ChatColor.GRAY) + "----------------------------------------"); paramCommandSender.sendMessage(String.valueOf(ChatColor.AQUA) + String.valueOf(ChatColor.AQUA) + " AI OPTIMIZER UPDATE"); paramCommandSender.sendMessage(""); paramCommandSender.sendMessage(String.valueOf(ChatColor.GRAY) + " A new version is available!"); paramCommandSender.sendMessage(String.valueOf(ChatColor.GRAY) + " Your version: " + String.valueOf(ChatColor.GRAY) + String.valueOf(ChatColor.RED)); paramCommandSender.sendMessage(String.valueOf(ChatColor.GRAY) + " New version:  " + String.valueOf(ChatColor.GRAY) + String.valueOf(ChatColor.GREEN)); paramCommandSender.sendMessage(""); paramCommandSender.sendMessage(String.valueOf(ChatColor.YELLOW) + " Download at SpigotMC / BuiltByBit."); paramCommandSender.sendMessage(String.valueOf(ChatColor.GRAY) + "----------------------------------------"); }  }); return true;case "inspect": sendInspectReport(paramCommandSender); return true;case "bossbar": if (paramCommandSender instanceof Player) { this.plugin.getBossBarManager().togglePlayer((Player)paramCommandSender); } else { paramCommandSender.sendMessage(String.valueOf(ChatColor.RED) + "Only players can use this command."); }  return true;case "reload": this.plugin.reloadConfig(); this.plugin.onDisable(); this.plugin.onEnable(); paramCommandSender.sendMessage(String.valueOf(ChatColor.GREEN) + "✔ Configuration reloaded."); return true; }  paramCommandSender.sendMessage(String.valueOf(ChatColor.RED) + "Unknown command."); sendHelp(paramCommandSender); return true;
/*     */   }
/*     */   private void sendHelp(CommandSender paramCommandSender) {
/* 222 */     paramCommandSender.sendMessage(String.valueOf(ChatColor.DARK_AQUA) + "=== [ AI Optimizer Help ] ===");
/* 223 */     paramCommandSender.sendMessage(String.valueOf(ChatColor.AQUA) + "/ai status " + String.valueOf(ChatColor.AQUA) + "- Performance Dashboard.");
/* 224 */     paramCommandSender.sendMessage(String.valueOf(ChatColor.AQUA) + "/ai inspect " + String.valueOf(ChatColor.AQUA) + "- Find chunks with most entities.");
/* 225 */     paramCommandSender.sendMessage(String.valueOf(ChatColor.AQUA) + "/ai bossbar " + String.valueOf(ChatColor.AQUA) + "- Toggle performance bar.");
/* 226 */     paramCommandSender.sendMessage(String.valueOf(ChatColor.AQUA) + "/ai clear " + String.valueOf(ChatColor.AQUA) + "- Manually clear items/mobs.");
/* 227 */     paramCommandSender.sendMessage(String.valueOf(ChatColor.AQUA) + "/ai reload " + String.valueOf(ChatColor.AQUA) + "- Reload config.yml.");
/* 228 */     paramCommandSender.sendMessage(String.valueOf(ChatColor.AQUA) + "/ai checkupdate " + String.valueOf(ChatColor.AQUA) + "- Manually check for updates.");
/* 229 */     paramCommandSender.sendMessage(String.valueOf(ChatColor.AQUA) + "/ai serverinfo " + String.valueOf(ChatColor.AQUA) + "- You will receive server information");
/* 230 */     paramCommandSender.sendMessage(String.valueOf(ChatColor.AQUA) + "/ai scoreboard " + String.valueOf(ChatColor.AQUA) + "- Displays a scoreboard with information");
/* 231 */     paramCommandSender.sendMessage(String.valueOf(ChatColor.AQUA) + "/ai restart (seconds) " + String.valueOf(ChatColor.AQUA) + "- You will force the server to restart.");
/* 232 */     paramCommandSender.sendMessage(String.valueOf(ChatColor.AQUA) + "/ai ping " + String.valueOf(ChatColor.AQUA) + "- View ping values");
/*     */   }
/*     */   
/*     */   private void sendStatus(CommandSender paramCommandSender) {
/* 236 */     ServerMonitor serverMonitor = this.plugin.getMonitor();
/* 237 */     DecimalFormat decimalFormat = new DecimalFormat("0.0");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 242 */     double d1 = serverMonitor.getStress();
/* 243 */     String str1 = serverMonitor.getFormattedStress();
/*     */ 
/*     */     
/* 246 */     int i = (int)(d1 / 5.0D);
/* 247 */     StringBuilder stringBuilder1 = new StringBuilder();
/* 248 */     for (byte b1 = 0; b1 < 20; b1++) {
/* 249 */       if (b1 < i) { stringBuilder1.append(String.valueOf(ChatColor.RED) + "|"); }
/* 250 */       else { stringBuilder1.append(String.valueOf(ChatColor.DARK_GRAY) + "|"); }
/*     */     
/*     */     } 
/*     */     
/* 254 */     Runtime runtime = Runtime.getRuntime();
/* 255 */     long l1 = runtime.maxMemory() / 1024L / 1024L;
/* 256 */     long l2 = runtime.totalMemory() / 1024L / 1024L;
/* 257 */     long l3 = l2 - runtime.freeMemory() / 1024L / 1024L;
/*     */ 
/*     */     
/* 260 */     StringBuilder stringBuilder2 = new StringBuilder();
/* 261 */     int j = (int)(l3 / l1 * 10.0D);
/* 262 */     for (byte b2 = 0; b2 < 10; b2++) {
/* 263 */       if (b2 < j) { stringBuilder2.append(String.valueOf(ChatColor.AQUA) + "■"); }
/* 264 */       else { stringBuilder2.append(String.valueOf(ChatColor.DARK_GRAY) + "■"); }
/*     */     
/*     */     } 
/*     */     
/* 268 */     double d2 = 0.0D;
/*     */     
/* 270 */     try { OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean)ManagementFactory.getOperatingSystemMXBean();
/* 271 */       d2 = operatingSystemMXBean.getSystemCpuLoad(); }
/* 272 */     catch (Exception exception) { d2 = -1.0D; }
/* 273 */      String str2 = (d2 < 0.0D || Double.isNaN(d2)) ? "N/A" : ("" + (int)(d2 * 100.0D) + "%");
/*     */ 
/*     */     
/* 276 */     long l4 = ManagementFactory.getRuntimeMXBean().getUptime();
/* 277 */     long l5 = TimeUnit.MILLISECONDS.toHours(l4);
/* 278 */     long l6 = TimeUnit.MILLISECONDS.toMinutes(l4) % 60L;
/* 279 */     String str3 = String.format("%02dh %02dm", new Object[] { Long.valueOf(l5), Long.valueOf(l6) });
/*     */ 
/*     */     
/* 282 */     double d3 = Bukkit.getAverageTickTime();
/* 283 */     ChatColor chatColor = (d3 < 40.0D) ? ChatColor.GREEN : ((d3 < 50.0D) ? ChatColor.YELLOW : ChatColor.RED);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 288 */     paramCommandSender.sendMessage(String.valueOf(ChatColor.DARK_AQUA) + String.valueOf(ChatColor.DARK_AQUA) + "----------------" + String.valueOf(ChatColor.STRIKETHROUGH) + " [ AI MONITOR ] " + String.valueOf(ChatColor.AQUA) + String.valueOf(ChatColor.DARK_AQUA) + "----------------");
/*     */ 
/*     */     
/* 291 */     paramCommandSender.sendMessage(String.valueOf(ChatColor.GRAY) + "  Status: " + String.valueOf(ChatColor.GRAY) + serverMonitor.getFormattedStatus() + "  |  " + String.valueOf(ChatColor.DARK_GRAY) + "Uptime: " + String.valueOf(ChatColor.GRAY) + String.valueOf(ChatColor.WHITE));
/* 292 */     paramCommandSender.sendMessage("");
/*     */ 
/*     */     
/* 295 */     paramCommandSender.sendMessage(String.valueOf(ChatColor.AQUA) + " ■ Performance Core");
/* 296 */     paramCommandSender.sendMessage(String.valueOf(ChatColor.GRAY) + "    TPS: " + String.valueOf(ChatColor.GRAY) + serverMonitor.getFormattedTps() + " | " + String.valueOf(ChatColor.DARK_GRAY) + "MSPT: " + String.valueOf(ChatColor.GRAY) + String.valueOf(chatColor) + "ms" + decimalFormat
/*     */         
/* 298 */         .format(d3) + " | " + String.valueOf(ChatColor.DARK_GRAY) + "CPU: " + String.valueOf(ChatColor.GRAY) + String.valueOf(ChatColor.WHITE));
/*     */ 
/*     */ 
/*     */     
/* 302 */     paramCommandSender.sendMessage(String.valueOf(ChatColor.GRAY) + "    Stress: " + String.valueOf(ChatColor.GRAY) + "[" + String.valueOf(ChatColor.DARK_GRAY) + stringBuilder1.toString() + "] " + String.valueOf(ChatColor.DARK_GRAY));
/*     */ 
/*     */     
/* 305 */     paramCommandSender.sendMessage("");
/* 306 */     paramCommandSender.sendMessage(String.valueOf(ChatColor.AQUA) + " ■ System Memory");
/* 307 */     paramCommandSender.sendMessage(String.valueOf(ChatColor.GRAY) + "    RAM: " + String.valueOf(ChatColor.GRAY) + stringBuilder2.toString() + " (" + String.valueOf(ChatColor.DARK_GRAY) + String.valueOf(ChatColor.WHITE) + "MB" + l3 + " / " + String.valueOf(ChatColor.GRAY) + "MB" + l1 + ")");
/*     */ 
/*     */ 
/*     */     
/* 311 */     int k = 0;
/* 312 */     int m = 0;
/* 313 */     for (World world : Bukkit.getWorlds()) {
/* 314 */       k += world.getEntityCount();
/* 315 */       m += (world.getLoadedChunks()).length;
/*     */     } 
/*     */     
/* 318 */     paramCommandSender.sendMessage("");
/* 319 */     paramCommandSender.sendMessage(String.valueOf(ChatColor.AQUA) + " ■ World Manager " + String.valueOf(ChatColor.AQUA) + "(Total: " + String.valueOf(ChatColor.DARK_GRAY) + "e / " + k + "ch)");
/*     */ 
/*     */     
/* 322 */     for (World world : Bukkit.getWorlds()) {
/* 323 */       int n = world.getEntityCount();
/* 324 */       int i1 = world.getViewDistance();
/*     */ 
/*     */       
/* 327 */       ChatColor chatColor1 = ChatColor.GREEN;
/* 328 */       if (i1 < 8) chatColor1 = ChatColor.YELLOW; 
/* 329 */       if (i1 <= 4) chatColor1 = ChatColor.RED;
/*     */       
/* 331 */       paramCommandSender.sendMessage(String.valueOf(ChatColor.DARK_GRAY) + "    » " + String.valueOf(ChatColor.DARK_GRAY) + String.valueOf(ChatColor.GRAY) + ": " + world.getName() + String.valueOf(ChatColor.WHITE) + " ents " + n + "- " + String.valueOf(ChatColor.DARK_GRAY) + "View: " + String.valueOf(ChatColor.GRAY) + String.valueOf(chatColor1));
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 338 */     paramCommandSender.sendMessage(String.valueOf(ChatColor.DARK_AQUA) + String.valueOf(ChatColor.DARK_AQUA) + "---------------------------------------------");
/*     */   }
/*     */ 
/*     */   
/*     */   private void sendServerInfo(CommandSender paramCommandSender) {
/* 343 */     String str1 = System.getProperty("os.name");
/* 344 */     String str2 = System.getProperty("os.arch");
/* 345 */     String str3 = System.getProperty("java.version");
/*     */ 
/*     */     
/* 348 */     int i = Runtime.getRuntime().availableProcessors();
/* 349 */     String str4 = getCpuModelName();
/*     */ 
/*     */     
/* 352 */     Runtime runtime = Runtime.getRuntime();
/* 353 */     long l1 = runtime.maxMemory() / 1024L / 1024L;
/* 354 */     long l2 = runtime.totalMemory() / 1024L / 1024L;
/* 355 */     long l3 = runtime.freeMemory() / 1024L / 1024L;
/* 356 */     long l4 = l2 - l3;
/*     */ 
/*     */     
/* 359 */     int j = (int)(l4 / l1 * 100.0D);
/*     */ 
/*     */     
/* 362 */     StringBuilder stringBuilder1 = new StringBuilder();
/* 363 */     for (byte b1 = 0; b1 < 20; b1++) {
/* 364 */       if (b1 < j / 5) { stringBuilder1.append(String.valueOf(ChatColor.GREEN) + "|"); }
/* 365 */       else { stringBuilder1.append(String.valueOf(ChatColor.DARK_GRAY) + "|"); }
/*     */     
/*     */     } 
/*     */     
/* 369 */     File file = new File(".");
/* 370 */     long l5 = file.getTotalSpace();
/* 371 */     long l6 = file.getFreeSpace();
/* 372 */     long l7 = l5 - l6;
/*     */     
/* 374 */     double d1 = l5 / 1.073741824E9D;
/* 375 */     double d2 = l7 / 1.073741824E9D;
/* 376 */     int k = (int)(d2 / d1 * 100.0D);
/*     */     
/* 378 */     StringBuilder stringBuilder2 = new StringBuilder();
/* 379 */     for (byte b2 = 0; b2 < 20; b2++) {
/* 380 */       if (b2 < k / 5) { stringBuilder2.append(String.valueOf(ChatColor.GREEN) + "|"); }
/* 381 */       else { stringBuilder2.append(String.valueOf(ChatColor.DARK_GRAY) + "|"); }
/*     */     
/*     */     } 
/*     */     
/* 385 */     paramCommandSender.sendMessage(String.valueOf(ChatColor.DARK_AQUA) + String.valueOf(ChatColor.DARK_AQUA) + "----------------" + String.valueOf(ChatColor.STRIKETHROUGH) + " [ SERVER HARDWARE ] " + String.valueOf(ChatColor.AQUA) + String.valueOf(ChatColor.DARK_AQUA) + "----------------");
/*     */     
/* 387 */     paramCommandSender.sendMessage("");
/* 388 */     paramCommandSender.sendMessage(String.valueOf(ChatColor.AQUA) + " ■ SYSTEM:");
/* 389 */     paramCommandSender.sendMessage(String.valueOf(ChatColor.GRAY) + "   OS: " + String.valueOf(ChatColor.GRAY) + String.valueOf(ChatColor.WHITE) + " " + str1);
/* 390 */     paramCommandSender.sendMessage(String.valueOf(ChatColor.GRAY) + "   Java: " + String.valueOf(ChatColor.GRAY) + String.valueOf(ChatColor.WHITE));
/*     */     
/* 392 */     paramCommandSender.sendMessage("");
/* 393 */     paramCommandSender.sendMessage(String.valueOf(ChatColor.AQUA) + " ■ CPU:");
/* 394 */     paramCommandSender.sendMessage(String.valueOf(ChatColor.GRAY) + "   Cores: " + String.valueOf(ChatColor.GRAY) + String.valueOf(ChatColor.WHITE) + " threads");
/* 395 */     paramCommandSender.sendMessage(String.valueOf(ChatColor.GRAY) + "   Model: " + String.valueOf(ChatColor.GRAY) + String.valueOf(ChatColor.WHITE));
/*     */ 
/*     */     
/* 398 */     paramCommandSender.sendMessage("");
/* 399 */     paramCommandSender.sendMessage(String.valueOf(ChatColor.AQUA) + " ■ MEMORY (RAM):");
/* 400 */     paramCommandSender.sendMessage(String.valueOf(ChatColor.GRAY) + "   Usage: " + String.valueOf(ChatColor.GRAY) + " " + stringBuilder1.toString() + String.valueOf(ChatColor.WHITE) + "%");
/* 401 */     paramCommandSender.sendMessage(String.valueOf(ChatColor.GRAY) + "   Used: " + String.valueOf(ChatColor.GRAY) + String.valueOf(ChatColor.WHITE) + " MB");
/* 402 */     paramCommandSender.sendMessage(String.valueOf(ChatColor.GRAY) + "   Limit (-Xmx): " + String.valueOf(ChatColor.GRAY) + String.valueOf(ChatColor.GREEN) + " MB");
/*     */     
/* 404 */     paramCommandSender.sendMessage("");
/* 405 */     paramCommandSender.sendMessage(String.valueOf(ChatColor.AQUA) + " ■ STORAGE (SSD/NVMe):");
/* 406 */     paramCommandSender.sendMessage(String.valueOf(ChatColor.GRAY) + "   Usage: " + String.valueOf(ChatColor.GRAY) + " " + stringBuilder2.toString() + String.valueOf(ChatColor.WHITE) + "%");
/* 407 */     paramCommandSender.sendMessage(String.valueOf(ChatColor.GRAY) + "   Space: " + String.valueOf(ChatColor.GRAY) + String.valueOf(ChatColor.WHITE) + " GB " + String.format("%.1f", new Object[] { Double.valueOf(d2) }) + "/ " + String.valueOf(ChatColor.GRAY) + String.valueOf(ChatColor.WHITE) + " GB");
/*     */     
/* 409 */     paramCommandSender.sendMessage(String.valueOf(ChatColor.DARK_AQUA) + String.valueOf(ChatColor.DARK_AQUA) + "---------------------------------------------");
/*     */   }
/*     */ 
/*     */   
/*     */   private String getCpuModelName() {
/*     */     try {
/* 415 */       if (System.getProperty("os.name").toLowerCase().contains("linux")) {
/* 416 */         Scanner scanner = new Scanner(new File("/proc/cpuinfo"));
/* 417 */         while (scanner.hasNextLine()) {
/* 418 */           String str = scanner.nextLine();
/* 419 */           if (str.startsWith("model name"))
/*     */           {
/* 421 */             return str.split(":")[1].trim();
/*     */           }
/*     */         } 
/*     */       } 
/* 425 */     } catch (Exception exception) {}
/*     */ 
/*     */     
/* 428 */     return "Unknown (" + System.getProperty("os.arch") + ")";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void sendInspectReport(CommandSender paramCommandSender) {
/* 436 */     paramCommandSender.sendMessage(String.valueOf(ChatColor.GRAY) + "Scanning chunks...");
/*     */     
/* 438 */     Bukkit.getScheduler().runTask((Plugin)this.plugin, () -> {
/*     */           HashMap<Object, Object> hashMap = new HashMap<>();
/*     */           for (World world : Bukkit.getWorlds()) {
/*     */             for (Chunk chunk : world.getLoadedChunks()) {
/*     */               int j = (chunk.getEntities()).length;
/*     */               if (j > 5)
/*     */                 hashMap.put(chunk, Integer.valueOf(j)); 
/*     */             } 
/*     */           } 
/*     */           ArrayList<Map.Entry> arrayList = new ArrayList(hashMap.entrySet());
/*     */           arrayList.sort(());
/*     */           paramCommandSender.sendMessage(String.valueOf(ChatColor.DARK_AQUA) + String.valueOf(ChatColor.DARK_AQUA) + "----------------" + String.valueOf(ChatColor.STRIKETHROUGH) + " [ CHUNK MONITOR ] " + String.valueOf(ChatColor.AQUA) + String.valueOf(ChatColor.DARK_AQUA) + "----------------");
/*     */           int i = Math.min(8, arrayList.size());
/*     */           if (i == 0) {
/*     */             paramCommandSender.sendMessage(String.valueOf(ChatColor.GRAY) + "   No heavy chunks detected.");
/*     */             paramCommandSender.sendMessage(String.valueOf(ChatColor.DARK_AQUA) + String.valueOf(ChatColor.DARK_AQUA) + "---------------------------------------------");
/*     */             return;
/*     */           } 
/*     */           for (byte b = 0; b < i; b++) {
/*     */             Map.Entry entry = arrayList.get(b);
/*     */             Chunk chunk = (Chunk)entry.getKey();
/*     */             int j = ((Integer)entry.getValue()).intValue();
/*     */             int k = chunk.getX() * 16;
/*     */             int m = chunk.getZ() * 16;
/*     */             int n = chunk.getWorld().getHighestBlockYAt(k + 8, m + 8) + 1;
/*     */             if (n < 0)
/*     */               n = 100; 
/*     */             ChatColor chatColor = ChatColor.WHITE;
/*     */             if (j > 30)
/*     */               chatColor = ChatColor.YELLOW; 
/*     */             if (j > 80)
/*     */               chatColor = ChatColor.RED; 
/*     */             if (paramCommandSender instanceof Player) {
/*     */               Player player = (Player)paramCommandSender;
/*     */               TextComponent textComponent1 = new TextComponent();
/*     */               TextComponent textComponent2 = new TextComponent(" " + b + 1 + ". ");
/*     */               textComponent2.setColor(ChatColor.GRAY);
/*     */               textComponent1.addExtra((BaseComponent)textComponent2);
/*     */               String str = chunk.getWorld().getName() + " (" + chunk.getWorld().getName() + ", " + k + ")";
/*     */               TextComponent textComponent3 = new TextComponent(str);
/*     */               textComponent3.setColor(ChatColor.WHITE);
/*     */               textComponent3.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder("Click to Teleport")).color(ChatColor.GRAY).create()));
/*     */               textComponent3.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/minecraft:tp " + player.getName() + " " + k + 8 + " " + n + " " + m + 8));
/*     */               textComponent1.addExtra((BaseComponent)textComponent3);
/*     */               TextComponent textComponent4 = new TextComponent(" | ");
/*     */               textComponent4.setColor(ChatColor.DARK_GRAY);
/*     */               textComponent1.addExtra((BaseComponent)textComponent4);
/*     */               TextComponent textComponent5 = new TextComponent(String.valueOf(j));
/*     */               textComponent5.setColor(chatColor);
/*     */               textComponent1.addExtra((BaseComponent)textComponent5);
/*     */               TextComponent textComponent6 = new TextComponent(" entities");
/*     */               textComponent6.setColor(ChatColor.GRAY);
/*     */               textComponent1.addExtra((BaseComponent)textComponent6);
/*     */               TextComponent textComponent7 = new TextComponent(" [TP]");
/*     */               textComponent7.setColor(ChatColor.DARK_AQUA);
/*     */               textComponent7.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/minecraft:tp " + player.getName() + " " + k + 8 + " " + n + " " + m + 8));
/*     */               textComponent1.addExtra((BaseComponent)textComponent7);
/*     */               player.spigot().sendMessage((BaseComponent)textComponent1);
/*     */             } else {
/*     */               paramCommandSender.sendMessage(" " + b + 1 + ". " + chunk.getWorld().getName() + " (" + k + "," + m + ") | " + j + " entities");
/*     */             } 
/*     */           } 
/*     */           paramCommandSender.sendMessage(String.valueOf(ChatColor.DARK_AQUA) + String.valueOf(ChatColor.DARK_AQUA) + "---------------------------------------------");
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\IK\Videos\Server-Ai-Optimizer-2.5.jar!\org\nyxiikcz\aioptimizer\commands\AiCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */