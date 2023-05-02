package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.*;

import java.util.ArrayList;
import java.util.List;

/**
 * class that represent the Sphere geometry
 */
public class Sphere extends RadialGeometry{

    final Point center;

    /**
     * ctor
     * @param radius
     * @param center
     */
    public Sphere(double radius, Point center) {
        super(radius);
        this.center = center;

    }

    public Point getCenter() {
        return center;
    }

    /**
     * returns the normal to the received point
     * @param p point on the geometry
     * @return  normalized vector
     */
    @Override
    public Vector getNormal(Point p) {
        return p.subtract(this.center).normalize();
    }

    /**
     * returns the intersection of the geometry with the accepted ray
     * @param ray
     * @return List<Point>
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        //formula of find intersection ray with sphere
        double tm=0;
        double d=0;
        //if p0!=center of sphere
        if (!center.equals(ray.getP0())){
            Vector u=center.subtract(ray.getP0());
            tm=alignZero(ray.getV0().dotProduct(u));
            d=Math.sqrt(u.lengthSquared()-tm*tm);
        }
        //no interection
        if (d>=radius)
            return null;
        double th=Math.sqrt(radius*radius-d*d);
        double t1=tm-th;
        double t2=tm+th;
        if (alignZero(t1)>0){
            if (alignZero(t2)>0)
                return List.of(ray.getPoint(t1),ray.getPoint(t2));
            else
                return List.of(ray.getPoint(t1));
        }
        if (alignZero(t2)>0){
            return List.of(ray.getPoint(t2));
        }
    return null;
    }
}
