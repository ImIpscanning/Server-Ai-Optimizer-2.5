/*    */ package org.nyxiikcz.aioptimizer.listeners;
/*    */ 
/*    */ import org.bukkit.entity.EntityType;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.EventPriority;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.entity.CreatureSpawnEvent;
/*    */ import org.nyxiikcz.aioptimizer.ServerAiOptimizer;
/*    */ 
/*    */ public class EntityListener
/*    */   implements Listener {
/*    */   private final ServerAiOptimizer plugin;
/*    */   
/*    */   public EntityListener(ServerAiOptimizer paramServerAiOptimizer) {
/* 15 */     this.plugin = paramServerAiOptimizer;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @EventHandler(priority = EventPriority.LOWEST)
/*    */   public void onEntitySpawn(CreatureSpawnEvent paramCreatureSpawnEvent) {
/* 25 */     if (!this.plugin.getConfig().getBoolean("entity-optimizer.master-enabled")) {
/*    */       return;
/*    */     }
/*    */ 
/*    */     
/* 30 */     if (!this.plugin.getMonitor().isCritical()) {
/*    */       return;
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 38 */     CreatureSpawnEvent.SpawnReason spawnReason = paramCreatureSpawnEvent.getSpawnReason();
/*    */ 
/*    */ 
/*    */     
/* 42 */     if (spawnReason == CreatureSpawnEvent.SpawnReason.NATURAL || spawnReason == CreatureSpawnEvent.SpawnReason.DEFAULT || spawnReason == CreatureSpawnEvent.SpawnReason.REINFORCEMENTS) {
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 47 */       if (paramCreatureSpawnEvent.getEntityType() == EntityType.ENDER_DRAGON || paramCreatureSpawnEvent
/* 48 */         .getEntityType() == EntityType.WITHER || paramCreatureSpawnEvent
/* 49 */         .getEntityType() == EntityType.VILLAGER) {
/*    */         return;
/*    */       }
/*    */       
/* 53 */       paramCreatureSpawnEvent.setCancelled(true);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\IK\Videos\Server-Ai-Optimizer-2.5.jar!\org\nyxiikcz\aioptimizer\listeners\EntityListener.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */