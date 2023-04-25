package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.*;
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

    @Override
    public List<Point> findIntersections(Ray ray) {
        Vector u=center.subtract(ray.getP0());
        double tm=alignZero(ray.getV0().dotProduct(u));
        double d=Math.sqrt(u.lengthSquared()-tm*tm);
        if (d>=radius)
            return null;
    }
}
