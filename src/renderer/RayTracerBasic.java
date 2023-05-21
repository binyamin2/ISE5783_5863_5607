package renderer;

import lighting.LightSource;
import primitives.*;
import geometries.Intersectable.GeoPoint;

import scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;

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
        return calcColor(closestPoint,ray);
    }

    /**
     * calc the color in the specific point
     *
     * @param gp
     * @return color
     */
    private Color calcColor(GeoPoint gp ,Ray ray) {
        return scene.ambientLight.getIntensity()
                .add(calcLocalEffects(gp,ray));

    }

    private Color calcLocalEffects(GeoPoint gp, Ray ray) {
        Color color = gp.geometry.getEmission();
        Vector v = ray.getV0 (); Vector n = gp.geometry.getNormal(gp.point);
        double nv = alignZero(n.dotProduct(v)); if (nv == 0) return color;
        Material material = gp.geometry.getMaterial();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                Color iL = lightSource.getIntensity(gp.point);
                color = color.add(iL.scale(calcDiffusive(material, nl)),
                        iL.scale(calcSpecular(material, n, l, nl, v)));
            }
        }
        return color;
    }

    private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
        Vector r=l.subtract(n.scale(l.dotProduct(n)*2));
        double temp = v.scale(-1).dotProduct(r);

        return material.kS.scale( Math.pow(Math.max(0,temp),material.Shininess ));

    }

    private Double3 calcDiffusive(Material material, double nl) {
        return material.kD.scale(Math.abs(nl));
    }

}
