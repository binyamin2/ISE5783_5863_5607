package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import geometries.Intersectable.GeoPoint;

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
        var intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections == null) return scene.background;
        GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
        return calcColor(closestPoint);
    }

    /**
     * calc the color in the specific point
     *
     * @param p
     * @return color
     */
    private Color calcColor(GeoPoint gp) {
        return scene.ambientLight.getIntensity()
                .add(gp.geometry.getEmission());
    }
}
