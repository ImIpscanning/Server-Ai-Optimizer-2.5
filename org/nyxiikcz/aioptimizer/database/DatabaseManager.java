/*     */ package org.nyxiikcz.aioptimizer.database;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.nyxiikcz.aioptimizer.ServerAiOptimizer;
/*     */ 
/*     */ public class DatabaseManager {
/*     */   private final ServerAiOptimizer plugin;
/*     */   private Connection connection;
/*     */   private final String host;
/*     */   private final String database;
/*     */   private final String username;
/*     */   private final String password;
/*     */   private final String table;
/*     */   private final int port;
/*     */   private final String serverName;
/*     */   
/*     */   public DatabaseManager(ServerAiOptimizer paramServerAiOptimizer) {
/*  24 */     this.plugin = paramServerAiOptimizer;
/*  25 */     this.host = paramServerAiOptimizer.getConfig().getString("database.host");
/*  26 */     this.port = paramServerAiOptimizer.getConfig().getInt("database.port", 3306);
/*  27 */     this.database = paramServerAiOptimizer.getConfig().getString("database.database");
/*  28 */     this.username = paramServerAiOptimizer.getConfig().getString("database.username");
/*  29 */     this.password = paramServerAiOptimizer.getConfig().getString("database.password");
/*  30 */     this.serverName = paramServerAiOptimizer.getConfig().getString("database.server-name", "Unknown");
/*     */     
/*  32 */     this.table = "ai_performance_log";
/*     */     
/*  34 */     connect();
/*  35 */     createTable();
/*     */   }
/*     */   
/*     */   private void connect() {
/*     */     try {
/*  40 */       if (this.connection != null && !this.connection.isClosed())
/*  41 */         return;  synchronized (this) {
/*  42 */         if (this.connection != null && !this.connection.isClosed())
/*  43 */           return;  Class.forName("com.mysql.cj.jdbc.Driver");
/*  44 */         this.connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database + "?autoReconnect=true&useSSL=false", this.username, this.password);
/*  45 */         this.plugin.getLogger().info("✅ MySQL pripojeno (" + this.serverName + ") - Rezim: HISTORIE (Time-Series).");
/*     */       } 
/*  47 */     } catch (Exception exception) {
/*  48 */       this.plugin.getLogger().severe("❌ Chyba MySQL: " + exception.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   private void createTable() {
/*  53 */     String str = "CREATE TABLE IF NOT EXISTS " + this.table + " (id INT AUTO_INCREMENT PRIMARY KEY, server_name VARCHAR(50) NOT NULL, timestamp DATETIME DEFAULT CURRENT_TIMESTAMP, tps DOUBLE, mspt DOUBLE, cpu_usage DOUBLE, ram_usage DOUBLE, disk_usage DOUBLE, total_chunks INT, total_entities INT, players INT, INDEX (server_name, timestamp));";
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
/*  68 */     Bukkit.getScheduler().runTaskAsynchronously((Plugin)this.plugin, () -> { try { PreparedStatement preparedStatement = this.connection.prepareStatement(paramString); try { preparedStatement.execute(); if (preparedStatement != null)
/*  69 */                 preparedStatement.close();  } catch (Throwable throwable) { if (preparedStatement != null) try { preparedStatement.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }
/*     */              }
/*  71 */           catch (SQLException sQLException)
/*     */           { this.plugin.getLogger().warning("Nepodarilo se vytvorit tabulku: " + sQLException.getMessage()); }
/*     */         
/*     */         });
/*     */   }
/*     */   
/*     */   public void logData(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, int paramInt1, int paramInt2, int paramInt3) {
/*  78 */     Bukkit.getScheduler().runTaskAsynchronously((Plugin)this.plugin, () -> { try {
/*     */             if (this.connection == null || this.connection.isClosed())
/*     */               connect(); 
/*  81 */           } catch (SQLException sQLException) {
/*     */             return;
/*     */           }  String str = "INSERT INTO " + this.table + " (server_name, tps, mspt, cpu_usage, ram_usage, disk_usage, total_chunks, total_entities, players) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"; try {
/*     */             PreparedStatement preparedStatement = this.connection.prepareStatement(str); 
/*     */             try { preparedStatement.setString(1, this.serverName); preparedStatement.setDouble(2, paramDouble1); preparedStatement.setDouble(3, paramDouble2); preparedStatement.setDouble(4, paramDouble3); preparedStatement.setDouble(5, paramDouble4); preparedStatement.setDouble(6, paramDouble5); preparedStatement.setInt(7, paramInt1); preparedStatement.setInt(8, paramInt2); preparedStatement.setInt(9, paramInt3); preparedStatement.executeUpdate(); if (preparedStatement != null)
/*     */                 preparedStatement.close();  }
/*  87 */             catch (Throwable throwable) { if (preparedStatement != null) try { preparedStatement.close(); } catch (Throwable throwable1)
/*     */                 { throwable.addSuppressed(throwable1); }
/*     */               
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/*     */               throw throwable; }
/*     */           
/*  98 */           } catch (SQLException sQLException) {
/*     */             this.plugin.getLogger().warning("Chyba pri zapisu do MySQL: " + sQLException.getMessage());
/*     */           } 
/*     */           purgeOldData();
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private void purgeOldData() {
/* 107 */     String str = "DELETE FROM " + this.table + " WHERE server_name = ? AND timestamp < NOW() - INTERVAL 24 HOUR"; 
/* 108 */     try { PreparedStatement preparedStatement = this.connection.prepareStatement(str); 
/* 109 */       try { preparedStatement.setString(1, this.serverName);
/* 110 */         preparedStatement.executeUpdate();
/* 111 */         if (preparedStatement != null) preparedStatement.close();  } catch (Throwable throwable) { if (preparedStatement != null) try { preparedStatement.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (SQLException sQLException) {}
/*     */   }
/*     */   
/*     */   public String getHistoryAsJson(int paramInt) {
/*     */     
/* 116 */     try { if (this.connection == null || this.connection.isClosed()) connect();  }
/* 117 */     catch (SQLException sQLException) { return "{}"; }
/*     */     
/* 119 */     String str = "SELECT DATE_FORMAT(timestamp, '%H:%i') as time_lbl, tps, mspt, cpu_usage, ram_usage, disk_usage, players, total_entities, total_chunks FROM " + this.table + " WHERE server_name = ? AND timestamp >= NOW() - INTERVAL ? HOUR ORDER BY timestamp ASC";
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 124 */     ArrayList<String> arrayList = new ArrayList();
/* 125 */     ArrayList<Double> arrayList1 = new ArrayList();
/* 126 */     ArrayList<Double> arrayList2 = new ArrayList();
/* 127 */     ArrayList<Double> arrayList3 = new ArrayList();
/* 128 */     ArrayList<Double> arrayList4 = new ArrayList();
/* 129 */     ArrayList<Double> arrayList5 = new ArrayList();
/* 130 */     ArrayList<Integer> arrayList6 = new ArrayList();
/* 131 */     ArrayList<Integer> arrayList7 = new ArrayList();
/* 132 */     ArrayList<Integer> arrayList8 = new ArrayList();
/*     */     
/* 134 */     try { PreparedStatement preparedStatement = this.connection.prepareStatement(str); 
/* 135 */       try { preparedStatement.setString(1, this.serverName);
/* 136 */         preparedStatement.setInt(2, paramInt);
/*     */         
/* 138 */         ResultSet resultSet = preparedStatement.executeQuery(); 
/* 139 */         try { while (resultSet.next()) {
/* 140 */             arrayList.add("\"" + resultSet.getString("time_lbl") + "\"");
/* 141 */             arrayList1.add(Double.valueOf(resultSet.getDouble("tps")));
/* 142 */             arrayList2.add(Double.valueOf(resultSet.getDouble("mspt")));
/* 143 */             arrayList3.add(Double.valueOf(resultSet.getDouble("cpu_usage")));
/* 144 */             arrayList4.add(Double.valueOf(resultSet.getDouble("ram_usage")));
/* 145 */             arrayList5.add(Double.valueOf(resultSet.getDouble("disk_usage")));
/* 146 */             arrayList6.add(Integer.valueOf(resultSet.getInt("players")));
/* 147 */             arrayList7.add(Integer.valueOf(resultSet.getInt("total_entities")));
/* 148 */             arrayList8.add(Integer.valueOf(resultSet.getInt("total_chunks")));
/*     */           } 
/* 150 */           if (resultSet != null) resultSet.close();  } catch (Throwable throwable) { if (resultSet != null)
/* 151 */             try { resultSet.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  if (preparedStatement != null) preparedStatement.close();  } catch (Throwable throwable) { if (preparedStatement != null) try { preparedStatement.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (SQLException sQLException)
/* 152 */     { sQLException.printStackTrace();
/* 153 */       return "{}"; }
/*     */ 
/*     */     
/* 156 */     return String.format(Locale.US, "{\"labels\":[%s], \"tps\":[%s], \"mspt\":[%s], \"cpu\":[%s], \"ram\":[%s], \"disk\":[%s], \"players\":[%s], \"entities\":[%s], \"chunks\":[%s]}", new Object[] {
/*     */           
/* 158 */           String.join(",", (Iterable)arrayList), 
/* 159 */           joinDoubles(arrayList1), joinDoubles(arrayList2), joinDoubles(arrayList3), joinDoubles(arrayList4), joinDoubles(arrayList5), 
/* 160 */           joinInts(arrayList6), joinInts(arrayList7), joinInts(arrayList8)
/*     */         });
/*     */   }
/*     */   
/*     */   private String joinDoubles(List<Double> paramList) {
/* 165 */     StringBuilder stringBuilder = new StringBuilder();
/* 166 */     for (byte b = 0; b < paramList.size(); b++) {
/* 167 */       stringBuilder.append(String.format(Locale.US, "%.2f", new Object[] { paramList.get(b) }));
/* 168 */       if (b < paramList.size() - 1) stringBuilder.append(","); 
/*     */     } 
/* 170 */     return stringBuilder.toString();
/*     */   }
/*     */   
/*     */   private String joinInts(List<Integer> paramList) {
/* 174 */     StringBuilder stringBuilder = new StringBuilder();
/* 175 */     for (byte b = 0; b < paramList.size(); b++) {
/* 176 */       stringBuilder.append(paramList.get(b));
/* 177 */       if (b < paramList.size() - 1) stringBuilder.append(","); 
/*     */     } 
/* 179 */     return stringBuilder.toString();
/*     */   }
/*     */   
/*     */   public void close() {
/*     */     try {
/* 184 */       if (this.connection != null && !this.connection.isClosed()) this.connection.close(); 
/* 185 */     } catch (SQLException sQLException) {}
/*     */   }
/*     */ }


/* Location:              C:\Users\IK\Videos\Server-Ai-Optimizer-2.5.jar!\org\nyxiikcz\aioptimizer\database\DatabaseManager.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */