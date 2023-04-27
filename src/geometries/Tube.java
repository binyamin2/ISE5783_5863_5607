package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.*;
import java.util.List;

/**
 * class that represent the Tube geometry
 */
public class Tube extends RadialGeometry {
    final Ray axisRay;

    /**
     * ctor
     * @param radius
     * @param axisRay
     */
    public Tube(double radius, Ray axisRay) {
        super(radius);
        this.axisRay = axisRay;
    }

    public Ray getAxisRay() {
        return axisRay;
    }

    /**
     * get normal from tube
     * @param p point on the geometry
     * @return normalize vector
     */
    @Override
    public Vector getNormal(Point p) {
        //find the distance between p0 to o
        double t=this.axisRay.getV0().dotProduct(p.subtract(axisRay.getP0()));
        //if  p0 equivalent to p
        if (t==0)
            return p.subtract(axisRay.getP0()).normalize();
        //find o
        Point o=axisRay.getP0().add(getAxisRay().getV0().scale(t));
        return p.subtract(o).normalize();
    }



    public List<Point> findIntersections(Ray ray) {
        Vector v = axisRay.getV0();
        Vector u = ray.getV0();

        Point p0 = axisRay.getP0();
        Point p1 = ray.getP0();

        double a = v.dotProduct(v);
        double b = 2 * v.dotProduct(u);
        double c = u.dotProduct(u) - radius * radius;

        double discriminant = b * b - 4 * a * c;

        if (isZero(discriminant)) {
            double t = -b / (2 * a);
            Point intersectionPoint;
            if (isZero(t))
                intersectionPoint = ray.getP0();
            else
                intersectionPoint = ray.getP0().add(ray.getV0().scale(t));

            if (!isZero(intersectionPoint.distance(p0))) {
                return List.of(intersectionPoint);
            } else {
                // ray is tangent to the tube, return both endpoints of the segment
                double t1 = (p0.subtract(ray.getP0())).dotProduct(v) / v.dotProduct(v);
                double t2 = t1 + Math.sqrt(radius * radius - p0.subtract(ray.getP0()).lengthSquared() / v.lengthSquared());
                Point intersectionPoint1 = ray.getP0().add(ray.getV0().scale(t1));
                Point intersectionPoint2 = ray.getP0().add(ray.getV0().scale(t2));

                return List.of(intersectionPoint1, intersectionPoint2);
            }
        } else if (discriminant > 0) {
            double t1 = (-b + Math.sqrt(discriminant)) / (2 * a);
            double t2 = (-b - Math.sqrt(discriminant)) / (2 * a);

            Point intersectionPoint1 = ray.getP0().add(ray.getV0().scale(t1));
            Point intersectionPoint2 = ray.getP0().add(ray.getV0().scale(t2));

            boolean inside1 = !isZero(intersectionPoint1.distance(p0));
            boolean inside2 = !isZero(intersectionPoint2.distance(p0));

            if (inside1 && inside2) {
                return List.of(intersectionPoint1, intersectionPoint2);
            } else if (inside1) {
                return List.of(intersectionPoint1);
            } else if (inside2) {
                return List.of(intersectionPoint2);
            }
        }

        return null;
    }



}
