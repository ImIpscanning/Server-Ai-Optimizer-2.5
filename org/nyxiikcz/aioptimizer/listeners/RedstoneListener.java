/*    */ package org.nyxiikcz.aioptimizer.listeners;
/*    */ 
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.EventPriority;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.block.BlockIgniteEvent;
/*    */ import org.bukkit.event.block.BlockRedstoneEvent;
/*    */ import org.bukkit.event.block.LeavesDecayEvent;
/*    */ import org.nyxiikcz.aioptimizer.ServerAiOptimizer;
/*    */ import org.nyxiikcz.aioptimizer.systems.RedstoneOptimizer;
/*    */ 
/*    */ public class RedstoneListener
/*    */   implements Listener {
/*    */   private final ServerAiOptimizer plugin;
/*    */   
/*    */   public RedstoneListener(ServerAiOptimizer paramServerAiOptimizer) {
/* 17 */     this.plugin = paramServerAiOptimizer;
/*    */   }
/*    */ 
/*    */   
/*    */   @EventHandler(priority = EventPriority.LOWEST)
/*    */   public void onRedstone(BlockRedstoneEvent paramBlockRedstoneEvent) {
/* 23 */     RedstoneOptimizer redstoneOptimizer = this.plugin.getRedstoneOptimizer();
/*    */ 
/*    */     
/* 26 */     if (redstoneOptimizer == null) {
/*    */       return;
/*    */     }
/* 29 */     if (redstoneOptimizer.shouldFreezePhysics("REDSTONE")) {
/* 30 */       paramBlockRedstoneEvent.setNewCurrent(paramBlockRedstoneEvent.getOldCurrent());
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 35 */     boolean bool = redstoneOptimizer.registerRedstoneActivity(paramBlockRedstoneEvent.getBlock());
/* 36 */     if (bool) {
/* 37 */       paramBlockRedstoneEvent.setNewCurrent(paramBlockRedstoneEvent.getOldCurrent());
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @EventHandler(priority = EventPriority.LOW)
/*    */   public void onFireSpread(BlockIgniteEvent paramBlockIgniteEvent) {
/* 45 */     if (paramBlockIgniteEvent.getCause() == BlockIgniteEvent.IgniteCause.SPREAD || paramBlockIgniteEvent
/* 46 */       .getCause() == BlockIgniteEvent.IgniteCause.LAVA)
/*    */     {
/* 48 */       if (this.plugin.getRedstoneOptimizer().shouldFreezePhysics("FIRE")) {
/* 49 */         paramBlockIgniteEvent.setCancelled(true);
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   @EventHandler(priority = EventPriority.LOW)
/*    */   public void onLeafDecay(LeavesDecayEvent paramLeavesDecayEvent) {
/* 57 */     if (this.plugin.getRedstoneOptimizer().shouldFreezePhysics("LEAF"))
/* 58 */       paramLeavesDecayEvent.setCancelled(true); 
/*    */   }
/*    */ }


/* Location:              C:\Users\IK\Videos\Server-Ai-Optimizer-2.5.jar!\org\nyxiikcz\aioptimizer\listeners\RedstoneListener.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */