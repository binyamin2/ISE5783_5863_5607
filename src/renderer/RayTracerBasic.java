package renderer;

import lighting.DirectionalLight;
import lighting.LightSource;
import lighting.PointLight;
import primitives.*;
import geometries.Intersectable.GeoPoint;

import scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * class of ray tracing
 */
public class RayTracerBasic extends RayTracerBase {

    private static final double DELTA = 0.1;


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

        Vector v = ray.getV0 ();
        Vector n = gp.geometry.getNormal(gp.point);
        //if the ray of camara and the normal at the point are vertical
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) return color;

        Material material = gp.geometry.getMaterial();
        //for about all the light and add the light
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                if (unshaded(gp, lightSource,l, n, nl))
                {
                    Color iL = lightSource.getIntensity(gp.point);
                    color = color.add(iL.scale(calcDiffusive(material, nl)),
                            iL.scale(calcSpecular(material, n, l, nl, v)));
                }
            }
        }
        return color;
    }

    /**
     * calculate specularity
     * @param material
     * @param n
     * @param l
     * @param nl
     * @param v
     * @return
     */
    private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
        Vector r=l.subtract(n.scale(l.dotProduct(n)*2));
        double temp = v.scale(-1).dotProduct(r);

        return material.kS.scale( Math.pow(Math.max(0,temp),material.Shininess ));

    }

    /**
     * calculate diffusivity
     * @param material
     * @param nl
     * @return
     */
    private Double3 calcDiffusive(Material material, double nl) {
        return material.kD.scale(Math.abs(nl));
    }

    /**
     * check if the point is shaded
     * @param gp
     * @param l
     * @param n
     * @param nl
     * @return
     */
    private boolean unshaded(GeoPoint gp,LightSource light, Vector l, Vector n, double nl) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Vector epsVector = n.scale(nl < 0 ? DELTA : -DELTA);
        Point point = gp.point.add(epsVector);
        Ray lightRay = new Ray(point, lightDirection);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);


        if (intersections == null) return true;
        //return that shaded if its diractional light
        /*if (light instanceof DirectionalLight)
            return false;*/
        //check if there is pointbetween the light and geoPoint

        double lightDistance = light.getDistance(point);
        for (GeoPoint geo : intersections) {
            if (point.distance(geo.point) < lightDistance ) {
                return false;
            }
        }

        return true;
    }


}
