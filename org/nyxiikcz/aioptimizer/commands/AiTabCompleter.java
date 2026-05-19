/*    */ package org.nyxiikcz.aioptimizer.commands;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.command.TabCompleter;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AiTabCompleter
/*    */   implements TabCompleter
/*    */ {
/*    */   @Nullable
/*    */   public List<String> onTabComplete(@NotNull CommandSender paramCommandSender, @NotNull Command paramCommand, @NotNull String paramString, @NotNull String[] paramArrayOfString) {
/* 18 */     if (!paramCommandSender.hasPermission("ai.admin")) {
/* 19 */       return new ArrayList<>();
/*    */     }
/*    */ 
/*    */     
/* 23 */     if (paramArrayOfString.length == 1) {
/* 24 */       ArrayList<String> arrayList1 = new ArrayList();
/* 25 */       ArrayList<String> arrayList2 = new ArrayList();
/*    */ 
/*    */       
/* 28 */       arrayList2.add("status");
/* 29 */       arrayList2.add("clear");
/* 30 */       arrayList2.add("inspect");
/* 31 */       arrayList2.add("bossbar");
/* 32 */       arrayList2.add("checkupdate");
/* 33 */       arrayList2.add("reload");
/* 34 */       arrayList2.add("help");
/* 35 */       arrayList2.add("serverinfo");
/* 36 */       arrayList2.add("scoreboard");
/* 37 */       arrayList2.add("restart");
/* 38 */       arrayList2.add("ping");
/*    */       
/* 40 */       String str = paramArrayOfString[0].toLowerCase();
/* 41 */       for (String str1 : arrayList2) {
/* 42 */         if (str1.startsWith(str)) {
/* 43 */           arrayList1.add(str1);
/*    */         }
/*    */       } 
/*    */       
/* 47 */       return arrayList1;
/*    */     } 
/*    */     
/* 50 */     return new ArrayList<>();
/*    */   }
/*    */ }


/* Location:              C:\Users\IK\Videos\Server-Ai-Optimizer-2.5.jar!\org\nyxiikcz\aioptimizer\commands\AiTabCompleter.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */