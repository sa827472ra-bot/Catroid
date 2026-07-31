package org.catrobat.catroid.camera;

import com.badlogic.gdx.math.Vector2;
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
        // TODO: resolve sprite targets, read sprite world positions and update Camera.position
        // using simple LERP when followMode == SMOOTH_FOLLOW.
    }

    public Map<String, Camera> getCameras() {
        return cameras;
    }

}
