package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

import java.util.List;

/**
 * class of ray tracing
 */
public class RayTracerBasic extends RayTracerBase {
    /**
     * ctor
     * call super ctor
     *
     * @param scene
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * trace the ray and return the color of the pixel
     * @param ray
     * @return color
     */
    @Override
    public Color traceRay(Ray ray) {
        List<Point> list = scene.geometries.findIntersections(ray);
        if (list == null ) {
            return scene.background;
        }
        Point p = ray.findClosetPoint(list);
        return calcColor(p);
    }

    /**
     * calc the color in the specific point
     *
     * @param p
     * @return color
     */
    public Color calcColor(Point p) {
        return scene.ambientLight.getIntensity();
    }
}
