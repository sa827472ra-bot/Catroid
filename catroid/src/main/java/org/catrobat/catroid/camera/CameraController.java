package org.catrobat.catroid.camera;

import com.badlogic.gdx.math.Vector2;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Sprite;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * CameraController manages multiple cameras, their targets and smoothing settings.
 *
 * NOTE: This is a lightweight skeleton. The implementation will be extended to integrate
 * with the Stage/StageListener renderer, support viewports, layer masks and persistence.
 */
public class CameraController {

    public enum FollowMode {
        NONE,
        LOCK_ON,
        SMOOTH_FOLLOW
    }

    public static class Camera {
        public final String id;
        public String name;
        public boolean enabled = true;
        public String targetSpriteName = null; // name or id of the sprite to follow
        public float speed = 0.12f; // lerp factor 0..1
        public FollowMode followMode = FollowMode.SMOOTH_FOLLOW;
        public float viewportX = 0f;
        public float viewportY = 0f;
        public float viewportWidth = 1f; // normalized for now
        public float viewportHeight = 1f;
        public String[] layerMask = null; // null = all layers
        public boolean clampToScene = false;

        // internal state
        public Vector2 position = new Vector2();

        public Camera(String id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    private final Map<String, Camera> cameras = new ConcurrentHashMap<>();

    public CameraController() {
    }

    public Camera createCamera(String id, String name) {
        Camera camera = new Camera(id, name);
        cameras.put(id, camera);
        return camera;
    }

    public Camera getCamera(String id) {
        return cameras.get(id);
    }

    public void removeCamera(String id) {
        cameras.remove(id);
    }

    /**
     * Update method called each frame (delta seconds). The controller will update camera positions
     * according to their follow targets and smoothing settings. Integration with the Stage renderer
     * is done elsewhere (StageListener will query camera positions and render accordingly).
     */
    public void update(float delta) {
        Scene currentScene = ProjectManager.getInstance().getCurrentlyPlayingScene();
        if (currentScene == null) {
            return;
        }

        for (Camera camera : cameras.values()) {
            if (!camera.enabled || camera.targetSpriteName == null) {
                continue;
            }

            try {
                Sprite target = currentScene.getSprite(camera.targetSpriteName);
                if (target == null) {
                    continue;
                }

                // Attempt to read the sprite's world position from its Look
                float targetX = 0f;
                float targetY = 0f;

                try {
                    // Many parts of the engine expose look through sprite.look
                    // Fallback to 0,0 if not available
                    Object lookObj = null;
                    try {
                        lookObj = target.getClass().getField("look").get(target);
                    } catch (NoSuchFieldException nsf) {
                        // ignore
                    }

                    if (lookObj != null) {
                        // use reflection to call getXInUserInterfaceDimensionUnit / getYInUserInterfaceDimensionUnit
                        try {
                            targetX = ((Number) lookObj.getClass()
                                    .getMethod("getXInUserInterfaceDimensionUnit").invoke(lookObj)).floatValue();
                            targetY = ((Number) lookObj.getClass()
                                    .getMethod("getYInUserInterfaceDimensionUnit").invoke(lookObj)).floatValue();
                        } catch (Exception e) {
                            // reflection failed, try alternative methods on Sprite
                            try {
                                targetX = ((Number) target.getClass()
                                        .getMethod("getXInUserInterfaceDimensionUnit").invoke(target)).floatValue();
                                targetY = ((Number) target.getClass()
                                        .getMethod("getYInUserInterfaceDimensionUnit").invoke(target)).floatValue();
                            } catch (Exception ex) {
                                // give up, leave at 0,0
                            }
                        }
                    } else {
                        // try sprite methods directly
                        try {
                            targetX = ((Number) target.getClass()
                                    .getMethod("getXInUserInterfaceDimensionUnit").invoke(target)).floatValue();
                            targetY = ((Number) target.getClass()
                                    .getMethod("getYInUserInterfaceDimensionUnit").invoke(target)).floatValue();
                        } catch (Exception ex) {
                            // ignore
                        }
                    }
                } catch (Exception e) {
                    // ignore and continue
                }

                Vector2 targetPos = new Vector2(targetX, targetY);

                if (camera.followMode == FollowMode.NONE) {
                    // do nothing
                } else if (camera.followMode == FollowMode.LOCK_ON) {
                    camera.position.set(targetPos);
                } else if (camera.followMode == FollowMode.SMOOTH_FOLLOW) {
                    // LERP towards the target using speed as factor (0..1)
                    camera.position.lerp(targetPos, clamp01(camera.speed));
                }

                if (camera.clampToScene) {
                    // Clamp to scene bounds (simple approach using project virtual screen)
                    try {
                        float sceneWidth = ProjectManager.getInstance().getCurrentProject().getXmlHeader().virtualScreenWidth;
                        float sceneHeight = ProjectManager.getInstance().getCurrentProject().getXmlHeader().virtualScreenHeight;

                        float x = Math.max(0f, Math.min(camera.position.x, sceneWidth));
                        float y = Math.max(0f, Math.min(camera.position.y, sceneHeight));
                        camera.position.set(x, y);
                    } catch (Exception e) {
                        // ignore if cannot clamp
                    }
                }

            } catch (Exception e) {
                // keep camera as-is on errors
            }
        }
    }

    private float clamp01(float v) {
        if (Float.isNaN(v) || v <= 0f) {
            return 0f;
        }
        if (v >= 1f) {
            return 1f;
        }
        return v;
    }

    public Map<String, Camera> getCameras() {
        return cameras;
    }

}
