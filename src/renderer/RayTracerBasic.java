package renderer;

import lighting.DirectionalLight;
import lighting.LightSource;
import lighting.PointLight;
import primitives.*;
import geometries.Intersectable.GeoPoint;

import scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * class of ray tracing
 */
public class RayTracerBasic extends RayTracerBase {

    private static final double DELTA = 0.1;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    private static final Double3 INITIAL_K = Double3.ONE;



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
        GeoPoint closestPoint = findClosestIntersection(ray);
        if (closestPoint == null) return scene.background;
        return calcColor(closestPoint,ray);
    }



    /**
     * calc the color in the specific point
     *
     * @param gp
     * @return color
     */

    private Color calcColor(GeoPoint gp, Ray ray) {
        return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(scene.ambientLight.getIntensity());
    }

    /**
     * help function for calc color
     * @param gp
     * @param ray
     * @param level
     * @param k
     * @return
     */
    private Color calcColor(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(gp, ray,k);
        return 1 == level ? color : color.add(calcGlobalEffects(gp, ray, level, k));
    }

    /**
     * cakc the global effect on point (reflection,refraction..)
     * @param gp
     * @param ray
     * @param level
     * @param k
     * @return
     */
    private Color calcGlobalEffects(GeoPoint gp, Ray ray,
                                    int level, Double3 k) {
        Color color = Color.BLACK;
        Vector v = ray.getV0();
        Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();
        return calcGlobalEffect(constructReflected(gp, v, n),level, k, material.kR).
                add(calcGlobalEffect(constructRefracted(gp, v,n),level, k, material.kT));}

    /**
     * recursive help function to calc the global effects
     * @param ray
     * @param level
     * @param k
     * @param kx
     * @return
     */
    private Color calcGlobalEffect(Ray ray, int level, Double3 k, Double3 kx) {Double3 kkx = k.product(kx);
        if (kkx.lowerThan(MIN_CALC_COLOR_K)) return Color.BLACK;
        GeoPoint gp = findClosestIntersection(ray);
        if (gp == null)
            return scene.background.scale(kx);
        return isZero(gp.geometry.getNormal(gp.point).dotProduct(ray.getV0()))?
                Color.BLACK : calcColor(gp, ray, level-1, kkx).scale(kx);}

    /**
     * calc the local effect (diffusive,specular,emission,lights)
     * @param gp
     * @param ray
     * @return
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray, Double3 k) {

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
                Double3 ktr = transparency(gp, lightSource, l, n); //intensity of shadow
                if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
                    Color iL = lightSource.getIntensity(gp.point).scale(ktr);;
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

//    /**
//     * check if the point is shaded
//     * @param gp
//     * @param l
//     * @param n
//     * @param nl
//     * @return
//     */
//    private boolean unshaded(GeoPoint gp,LightSource light, Vector l, Vector n, double nl) {
//        if (!gp.geometry.getMaterial().kT.equals(Double3.ZERO))
//            return true;
//        Vector lightDirection = l.scale(-1); // from point to light source
//        Vector epsVector = n.scale(nl < 0 ? DELTA : -DELTA);
//        Point point = gp.point.add(epsVector);
//        Ray lightRay = new Ray(point, lightDirection);
//        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
//
//
//        if (intersections == null) return true;
//        //return that shaded if its diractional light
//        /*if (light instanceof DirectionalLight)
//            return false;*/
//        //check if there is pointbetween the light and geoPoint
//
//        double lightDistance = light.getDistance(point);
//        for (GeoPoint geo : intersections) {
//            if (point.distance(geo.point) < lightDistance ) {
//                return false;
//            }
//        }
//
//        return true;
//    }

    /**
     * return the level of the shadow
     * @param geopoint
     * @param light
     * @param l
     * @param n
     * @return
     */
    private Double3 transparency(GeoPoint geopoint, LightSource light, Vector l, Vector n) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(geopoint.point, lightDirection, n); //build ray with delta
        double lightDistance = light.getDistance(geopoint.point);

        var intersections = this.scene.geometries.findGeoIntersections(lightRay);
        if (intersections == null){
            return Double3.ONE; //no intersections
        }
        Double3 ktr = Double3.ONE;
        for (GeoPoint gp : intersections) {
            if (alignZero(gp.point.distance(geopoint.point) - lightDistance) <= 0) {
                ktr = ktr.product(gp.geometry.getMaterial().kT); //the more transparency the less shadow
                if (ktr.lowerThan(MIN_CALC_COLOR_K)) return Double3.ZERO;
            }
        }
        return ktr;
    }
    /**
     * constract the new ray for reflection
     * @param gp
     * @param ray
     * @param n
     * @return
     */
    private Ray constructReflected(GeoPoint gp, Vector v,Vector n) {
        double nv = alignZero(v.dotProduct(n));

        //excepted formula
        Vector r = v.subtract(n.scale(2d * nv)).normalize();
        //move the point with delta to the new direction
        return new Ray(gp.point,r,n);
    }

    /**
     * constract the new ray for refraction
     * @param gp
     * @param v
     * @return
     */
    private Ray constructRefracted(GeoPoint gp, Vector v,Vector n) {

        return new Ray(gp.point,v,n);

    }

    /**
     * find the closest intersection on excepted ray
     * @param ray
     * @return
     */
    private GeoPoint findClosestIntersection(Ray ray){
        List<GeoPoint> intersections=this.scene.geometries.findGeoIntersections(ray);
        return ray.findClosestGeoPoint(intersections);
    }

}
