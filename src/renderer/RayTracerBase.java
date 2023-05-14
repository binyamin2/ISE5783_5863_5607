package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

public abstract class RayTracerBase {
    protected Scene scene;

    /**
     * ctor
     * @param scene
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * return the color of  a ray
     * @param ray
     * @return
     */
    public abstract Color traceRay(Ray ray);
}
