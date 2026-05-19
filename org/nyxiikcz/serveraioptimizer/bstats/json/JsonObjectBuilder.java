/*     */ package org.nyxiikcz.serveraioptimizer.bstats.json;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.stream.Collectors;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JsonObjectBuilder
/*     */ {
/*  14 */   private StringBuilder builder = new StringBuilder();
/*     */   private boolean hasAtLeastOneField = false;
/*     */   
/*     */   public JsonObjectBuilder() {
/*  18 */     this.builder.append("{");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonObjectBuilder appendNull(String paramString) {
/*  28 */     appendFieldUnescaped(paramString, "null");
/*  29 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonObjectBuilder appendField(String paramString1, String paramString2) {
/*  40 */     if (paramString2 == null) {
/*  41 */       throw new IllegalArgumentException("JSON value must not be null");
/*     */     }
/*  43 */     appendFieldUnescaped(paramString1, "\"" + escape(paramString2) + "\"");
/*  44 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonObjectBuilder appendField(String paramString, int paramInt) {
/*  55 */     appendFieldUnescaped(paramString, String.valueOf(paramInt));
/*  56 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonObjectBuilder appendField(String paramString, JsonObject paramJsonObject) {
/*  67 */     if (paramJsonObject == null) {
/*  68 */       throw new IllegalArgumentException("JSON object must not be null");
/*     */     }
/*  70 */     appendFieldUnescaped(paramString, paramJsonObject.toString());
/*  71 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonObjectBuilder appendField(String paramString, String[] paramArrayOfString) {
/*  82 */     if (paramArrayOfString == null) {
/*  83 */       throw new IllegalArgumentException("JSON values must not be null");
/*     */     }
/*     */ 
/*     */     
/*  87 */     String str = Arrays.<String>stream(paramArrayOfString).map(paramString -> "\"" + escape(paramString) + "\"").collect(Collectors.joining(","));
/*  88 */     appendFieldUnescaped(paramString, "[" + str + "]");
/*  89 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonObjectBuilder appendField(String paramString, int[] paramArrayOfint) {
/* 100 */     if (paramArrayOfint == null) {
/* 101 */       throw new IllegalArgumentException("JSON values must not be null");
/*     */     }
/*     */ 
/*     */     
/* 105 */     String str = Arrays.stream(paramArrayOfint).<CharSequence>mapToObj(String::valueOf).collect(Collectors.joining(","));
/* 106 */     appendFieldUnescaped(paramString, "[" + str + "]");
/* 107 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonObjectBuilder appendField(String paramString, JsonObject[] paramArrayOfJsonObject) {
/* 118 */     if (paramArrayOfJsonObject == null) {
/* 119 */       throw new IllegalArgumentException("JSON values must not be null");
/*     */     }
/*     */ 
/*     */     
/* 123 */     String str = Arrays.<JsonObject>stream(paramArrayOfJsonObject).map(JsonObject::toString).collect(Collectors.joining(","));
/* 124 */     appendFieldUnescaped(paramString, "[" + str + "]");
/* 125 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void appendFieldUnescaped(String paramString1, String paramString2) {
/* 135 */     if (this.builder == null) {
/* 136 */       throw new IllegalStateException("JSON has already been built");
/*     */     }
/* 138 */     if (paramString1 == null) {
/* 139 */       throw new IllegalArgumentException("JSON key must not be null");
/*     */     }
/* 141 */     if (this.hasAtLeastOneField) {
/* 142 */       this.builder.append(",");
/*     */     }
/* 144 */     this.builder.append("\"").append(escape(paramString1)).append("\":").append(paramString2);
/*     */     
/* 146 */     this.hasAtLeastOneField = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonObject build() {
/* 155 */     if (this.builder == null) {
/* 156 */       throw new IllegalStateException("JSON has already been built");
/*     */     }
/* 158 */     JsonObject jsonObject = new JsonObject(this.builder.append("}").toString());
/* 159 */     this.builder = null;
/* 160 */     return jsonObject;
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
/*     */   private static String escape(String paramString) {
/* 173 */     StringBuilder stringBuilder = new StringBuilder();
/* 174 */     for (byte b = 0; b < paramString.length(); b++) {
/* 175 */       char c = paramString.charAt(b);
/* 176 */       if (c == '"') {
/* 177 */         stringBuilder.append("\\\"");
/* 178 */       } else if (c == '\\') {
/* 179 */         stringBuilder.append("\\\\");
/* 180 */       } else if (c <= '\017') {
/* 181 */         stringBuilder.append("\\u000").append(Integer.toHexString(c));
/* 182 */       } else if (c <= '\037') {
/* 183 */         stringBuilder.append("\\u00").append(Integer.toHexString(c));
/*     */       } else {
/* 185 */         stringBuilder.append(c);
/*     */       } 
/*     */     } 
/* 188 */     return stringBuilder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class JsonObject
/*     */   {
/*     */     private final String value;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private JsonObject(String param1String) {
/* 202 */       this.value = param1String;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 207 */       return this.value;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\IK\Videos\Server-Ai-Optimizer-2.5.jar!\org\nyxiikcz\serveraioptimizer\bstats\json\JsonObjectBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */