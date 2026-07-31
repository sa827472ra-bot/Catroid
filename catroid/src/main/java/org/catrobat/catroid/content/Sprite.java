@@
 @XStreamFieldKeyOrder({
 		"name",
 		"lookList",
 		"soundList",
 		"scriptList",
 		"nfcTagList",
 		"userVariables",
-		"userLists",
+		"userLists",
 		"userDefinedBrickList"
 })
@@
 public class Sprite implements Nameable, Serializable {
@@
     private transient Plot plot = new Plot();
+    @XStreamAsAttribute
+    private String layerName = "game";
@@
     public Sprite() {
     }
@@
     public String getName() {
         return name;
     }
 
     public void setName(String name) {
         this.name = name;
     }
+
+    public String getLayerName() {
+        return layerName;
+    }
+
+    public void setLayerName(String layerName) {
+        this.layerName = layerName;
+    }
*** End Patch
