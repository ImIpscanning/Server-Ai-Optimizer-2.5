/*    */ package org.nyxiikcz.aioptimizer.update;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.net.URL;
/*    */ import java.util.Scanner;
/*    */ import java.util.function.Consumer;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.nyxiikcz.aioptimizer.ServerAiOptimizer;
/*    */ 
/*    */ public class UpdateChecker {
/*    */   private final ServerAiOptimizer plugin;
/*    */   private final int resourceId;
/*    */   
/*    */   public UpdateChecker(ServerAiOptimizer paramServerAiOptimizer, int paramInt) {
/* 17 */     this.plugin = paramServerAiOptimizer;
/* 18 */     this.resourceId = paramInt;
/*    */   }
/*    */   
/*    */   public void getVersion(Consumer<String> paramConsumer) {
/* 22 */     Bukkit.getScheduler().runTaskAsynchronously((Plugin)this.plugin, () -> { try { InputStream inputStream = (new URL("https://api.spigotmc.org/legacy/update.php?resource=" + this.resourceId)).openStream(); try { Scanner scanner = new Scanner(inputStream); try { if (scanner.hasNext())
/* 23 */                   paramConsumer.accept(scanner.next());  scanner.close(); } catch (Throwable throwable) { try { scanner.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  if (inputStream != null) inputStream.close();  } catch (Throwable throwable) { if (inputStream != null) try { inputStream.close(); } catch (Throwable throwable1)
/*    */                 { throwable.addSuppressed(throwable1); }
/*    */               
/*    */               
/*    */               throw throwable; }
/*    */              }
/* 29 */           catch (IOException iOException)
/*    */           { this.plugin.getLogger().info("Unable to check for updates: " + iOException.getMessage()); }
/*    */         
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\IK\Videos\Server-Ai-Optimizer-2.5.jar!\org\nyxiikcz\aioptimize\\update\UpdateChecker.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */