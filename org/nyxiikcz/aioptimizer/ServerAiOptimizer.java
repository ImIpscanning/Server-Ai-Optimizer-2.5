/*     */ package org.nyxiikcz.aioptimizer;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.nyxiikcz.aioptimizer.database.DatabaseManager;
/*     */ import org.nyxiikcz.aioptimizer.listeners.RedstoneListener;
/*     */ import org.nyxiikcz.aioptimizer.placeholders.AiPlaceholders;
/*     */ import org.nyxiikcz.aioptimizer.systems.AiScoreboard;
/*     */ import org.nyxiikcz.aioptimizer.systems.AutoRestartSystem;
/*     */ import org.nyxiikcz.aioptimizer.systems.BossBarManager;
/*     */ import org.nyxiikcz.aioptimizer.systems.EntityOptimizer;
/*     */ import org.nyxiikcz.aioptimizer.systems.RedstoneOptimizer;
/*     */ import org.nyxiikcz.aioptimizer.systems.ServerMonitor;
/*     */ import org.nyxiikcz.aioptimizer.systems.WebServerManager;
/*     */ import org.nyxiikcz.aioptimizer.systems.WorldManager;
/*     */ import org.nyxiikcz.serveraioptimizer.bstats.bukkit.Metrics;
/*     */ import org.nyxiikcz.serveraioptimizer.bstats.charts.CustomChart;
/*     */ 
/*     */ public class ServerAiOptimizer extends JavaPlugin {
/*  20 */   private String latestVersion = null; private static ServerAiOptimizer instance;
/*  21 */   private final int RESOURCE_ID = 131348;
/*     */   
/*     */   private ServerMonitor monitor;
/*     */   
/*     */   private EntityOptimizer entityOptimizer;
/*     */   
/*     */   private RedstoneOptimizer redstoneOptimizer;
/*     */   private WorldManager worldManager;
/*     */   private BossBarManager bossBarManager;
/*     */   private DatabaseManager databaseManager;
/*     */   private AutoRestartSystem restartSystem;
/*     */   private AiScoreboard aiScoreboard;
/*     */   private WebServerManager webServer;
/*     */   
/*     */   public static ServerAiOptimizer getInstance() {
/*  36 */     return instance;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  41 */     loadConfig0(); instance = this;
/*     */     
/*  43 */     char c = '烀';
/*  44 */     Metrics metrics = new Metrics((Plugin)this, c);
/*  45 */     metrics.addCustomChart((CustomChart)new SimplePie("my_chart", () -> "My Value"));
/*     */     
/*  47 */     boolean bool = checkForPaper();
/*  48 */     String str = bool ? (String.valueOf(ChatColor.GREEN) + "PaperMC (Recommended)") : (String.valueOf(ChatColor.RED) + "Spigot/Bukkit (Limited)");
/*     */     
/*  50 */     this.webServer = new WebServerManager(this);
/*  51 */     this.webServer.start();
/*  52 */     saveDefaultConfig();
/*  53 */     reloadConfig();
/*  54 */     (new StartupFlagAnalyzer(this)).analyze();
/*     */ 
/*     */ 
/*     */     
/*  58 */     this.monitor = new ServerMonitor(this);
/*     */ 
/*     */     
/*  61 */     this.restartSystem = new AutoRestartSystem(this);
/*  62 */     this.aiScoreboard = new AiScoreboard(this);
/*     */     
/*  64 */     getServer().getPluginManager().registerEvents((Listener)new VillagerListener(this), (Plugin)this);
/*     */ 
/*     */     
/*  67 */     if (getConfig().getBoolean("database.enabled")) {
/*     */       try {
/*  69 */         this.databaseManager = new DatabaseManager(this);
/*  70 */         int i = getConfig().getInt("database.log-interval", 60) * 20;
/*  71 */         (new DatabaseLogger(this, this.databaseManager)).runTaskTimer((Plugin)this, i, i);
/*     */         
/*  73 */         getLogger().info("Database Logging System: ENABLED");
/*  74 */       } catch (Exception exception) {
/*  75 */         getLogger().severe("Failed to start database: " + exception.getMessage());
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*  80 */     initializeSystems();
/*     */     
/*  82 */     if (getCommand("ai") != null) {
/*  83 */       getCommand("ai").setExecutor((CommandExecutor)new AiCommand(this));
/*  84 */       getCommand("ai").setTabCompleter((TabCompleter)new AiTabCompleter());
/*     */     } 
/*     */     
/*  87 */     registerListeners();
/*     */ 
/*     */ 
/*     */     
/*  91 */     if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
/*  92 */       (new AiPlaceholders(this)).register();
/*  93 */       getLogger().info("PlaceholderAPI found -> Placeholders activated.");
/*     */     } else {
/*  95 */       getLogger().warning("PlaceholderAPI not found -> Placeholders will not work.");
/*     */     } 
/*     */     
/*  98 */     if (getConfig().getBoolean("update-checker.enabled", true)) {
/*  99 */       (new UpdateChecker(this, 131348)).getVersion(paramString -> {
/*     */             if (!getDescription().getVersion().equalsIgnoreCase(paramString)) {
/*     */               this.latestVersion = paramString;
/*     */               
/*     */               getLogger().warning("---------------------------------------");
/*     */               
/*     */               getLogger().warning("New version of AI Optimizer found!");
/*     */               getLogger().warning("Current: " + getDescription().getVersion());
/*     */               getLogger().warning("New: " + paramString);
/*     */               getLogger().warning("---------------------------------------");
/*     */             } 
/*     */           });
/*     */     }
/* 112 */     sendConsoleMessage(String.valueOf(ChatColor.DARK_AQUA) + "========================================");
/* 113 */     sendConsoleMessage(String.valueOf(ChatColor.AQUA) + "       SERVER AI OPTIMIZER       ");
/* 114 */     sendConsoleMessage(String.valueOf(ChatColor.DARK_AQUA) + "========================================");
/* 115 */     sendConsoleMessage("");
/* 116 */     sendConsoleMessage(String.valueOf(ChatColor.GRAY) + " ▸ Platform: " + String.valueOf(ChatColor.GRAY));
/* 117 */     sendConsoleMessage(String.valueOf(ChatColor.GRAY) + " ▸ Autor: " + String.valueOf(ChatColor.GRAY) + "nyxiikcz");
/* 118 */     sendConsoleMessage(String.valueOf(ChatColor.GRAY) + " ▸ Version: " + String.valueOf(ChatColor.GRAY) + String.valueOf(ChatColor.GREEN));
/* 119 */     sendConsoleMessage("");
/* 120 */     sendConsoleMessage(String.valueOf(ChatColor.GRAY) + " [MODULES]");
/* 121 */     logModuleStatus("Entity Brain", getConfig().getBoolean("entity-optimizer.master-enabled"));
/* 122 */     logModuleStatus("Redstone Guard", getConfig().getBoolean("redstone-optimizer.master-enabled"));
/* 123 */     logModuleStatus("World Manager", getConfig().getBoolean("world-manager.master-enabled"));
/* 124 */     logModuleStatus("MySQL Database", getConfig().getBoolean("database.enabled"));
/* 125 */     logModuleStatus("Update Checker", getConfig().getBoolean("update-checker.enabled"));
/* 126 */     sendConsoleMessage("");
/* 127 */     sendConsoleMessage(String.valueOf(ChatColor.GREEN) + " ✔ AI System is ONLINE and ready.");
/* 128 */     sendConsoleMessage(String.valueOf(ChatColor.DARK_AQUA) + "========================================");
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/* 133 */     sendConsoleMessage(String.valueOf(ChatColor.RED) + "========================================");
/* 134 */     sendConsoleMessage(String.valueOf(ChatColor.RED) + "       SERVER AI OPTIMIZER - SHUTDOWN       ");
/* 135 */     sendConsoleMessage(String.valueOf(ChatColor.RED) + "========================================");
/*     */ 
/*     */     
/* 138 */     getServer().getScheduler().cancelTasks((Plugin)this);
/* 139 */     sendConsoleMessage(String.valueOf(ChatColor.GRAY) + " ▸ Stopping tasks...");
/* 140 */     sendConsoleMessage(String.valueOf(ChatColor.GRAY) + " ▸ Saving data...");
/*     */     
/* 142 */     instance = null;
/* 143 */     if (this.webServer != null) {
/* 144 */       this.webServer.stop();
/*     */     }
/*     */     
/* 147 */     if (this.databaseManager != null) {
/* 148 */       this.databaseManager.close();
/*     */     }
/*     */     
/* 151 */     sendConsoleMessage(String.valueOf(ChatColor.DARK_RED) + " ✖ AI System is OFFLINE.");
/* 152 */     sendConsoleMessage(String.valueOf(ChatColor.RED) + "========================================");
/*     */     
/* 154 */     if (this.bossBarManager != null) {
/* 155 */       this.bossBarManager.removeAllPlayers();
/*     */     }
/*     */   }
/*     */   
/*     */   private void initializeSystems() {
/* 160 */     this.entityOptimizer = new EntityOptimizer(this);
/* 161 */     this.redstoneOptimizer = new RedstoneOptimizer(this);
/*     */ 
/*     */ 
/*     */     
/* 165 */     this.worldManager = new WorldManager(this);
/* 166 */     this.worldManager.runTaskTimer((Plugin)this, 100L, 200L);
/*     */     
/* 168 */     this.bossBarManager = new BossBarManager(this);
/*     */   }
/*     */ 
/*     */   
/*     */   private void sendConsoleMessage(String paramString) {
/* 173 */     getServer().getConsoleSender().sendMessage(paramString);
/*     */   }
/*     */   
/*     */   private void logModuleStatus(String paramString, boolean paramBoolean) {
/* 177 */     String str = paramBoolean ? (String.valueOf(ChatColor.GREEN) + "ENABLE") : (String.valueOf(ChatColor.RED) + "DISABLE");
/* 178 */     sendConsoleMessage(String.valueOf(ChatColor.GRAY) + " ▸ " + String.valueOf(ChatColor.GRAY) + ": " + paramString);
/*     */   }
/*     */   
/*     */   private void registerListeners() {
/* 182 */     getServer().getPluginManager().registerEvents((Listener)new RedstoneListener(this), (Plugin)this);
/* 183 */     getServer().getPluginManager().registerEvents((Listener)new UpdateListener(this), (Plugin)this);
/*     */   }
/*     */   
/*     */   private boolean checkForPaper() {
/*     */     try {
/* 188 */       Class.forName("com.destroystokyo.paper.PaperConfig");
/* 189 */       return true;
/* 190 */     } catch (ClassNotFoundException classNotFoundException) {
/* 191 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public ServerMonitor getMonitor() {
/* 196 */     return this.monitor;
/*     */   }
/*     */   
/*     */   public EntityOptimizer getEntityOptimizer() {
/* 200 */     return this.entityOptimizer;
/*     */   }
/*     */   
/*     */   public RedstoneOptimizer getRedstoneOptimizer() {
/* 204 */     return this.redstoneOptimizer;
/*     */   }
/*     */   
/*     */   public String getLatestVersion() {
/* 208 */     return this.latestVersion;
/*     */   }
/*     */   
/*     */   public BossBarManager getBossBarManager() {
/* 212 */     return this.bossBarManager;
/*     */   }
/*     */   
/*     */   public int getResourceId() {
/* 216 */     return 131348;
/*     */   }
/*     */   
/*     */   public AiScoreboard getAiScoreboard() {
/* 220 */     return this.aiScoreboard;
/*     */   }
/*     */   
/*     */   public AutoRestartSystem getRestartSystem() {
/* 224 */     return this.restartSystem;
/*     */   }
/*     */   
/*     */   public DatabaseManager getDatabaseManager() {
/* 228 */     return this.databaseManager;
/*     */   }
/*     */ }


/* Location:              C:\Users\IK\Videos\Server-Ai-Optimizer-2.5.jar!\org\nyxiikcz\aioptimizer\ServerAiOptimizer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */