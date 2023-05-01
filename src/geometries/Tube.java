package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.*;

import java.util.ArrayList;
import java.util.List;

/**
 * class that represent the Tube geometry
 */
public class Tube extends RadialGeometry {
    final Ray axisRay;

    /**
     * ctor
     *
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
     *
     * @param p point on the geometry
     * @return normalize vector
     */
    @Override
    public Vector getNormal(Point p) {
        //find the distance between p0 to o
        double t = this.axisRay.getV0().dotProduct(p.subtract(axisRay.getP0()));
        //if  p0 equivalent to p
        if (t == 0)
            return p.subtract(axisRay.getP0()).normalize();
        //find o
        Point o = axisRay.getP0().add(getAxisRay().getV0().scale(t));
        return p.subtract(o).normalize();
    }

    /**
     * find intersections between ray and tube
     * @param ray
     * @return
     */
    public List<Point> findIntersections(Ray ray) {
        // Get the starting point of the current object's axis ray and the input Ray object
        Vector v = axisRay.getV0();
        Vector u = ray.getV0();

        // Get the starting point of the current object's axis ray and the input Ray object
        Point p0 = axisRay.getP0();
        Point p1 = ray.getP0();

        // Calculate the necessary values for solving the quadratic equation for intersection points
        Vector deltaP = p1.subtract(p0);
        double deltaPDotV = deltaP.dotProduct(v);
        double deltaPDotU = deltaP.dotProduct(u);
        double vDotU = v.dotProduct(u);
        double vDotV = v.dotProduct(v);

        double a = vDotV * (u.lengthSquared()) - vDotU * vDotU;
        double b = 2.0 * (vDotV * deltaPDotU - vDotU * deltaPDotV);
        double c = vDotV * (deltaP.lengthSquared()) - (deltaPDotV * deltaPDotV) - radius * radius * vDotV;

        // Calculate the discriminant of the quadratic equation
        double discriminant = b * b - 4 * a * c;

        // Check for no intersection
        if (discriminant < 0) {
            return null;
        } else if (isZero(discriminant)) { // Check for a single intersection
            // Check for no intersection when the quadratic is degenerate
            if(isZero(a))
                return null;
            double t = -b / (2.0 * a);

            // Check for intersection in the correct direction of the input Ray object
            if (t <= 0) {
                return null;
            }

            // Calculate the intersection point
            Point intersectionPoint = p1.add(u.scale(t));

            // Check if the intersection point is on the surface of the current object
            if (!isZero(intersectionPoint.distance(p0))) {
                double deltaPDotV1 = intersectionPoint.subtract(p0).dotProduct(v);

                // Check if the intersection point is inside the current object
                if (deltaPDotU <= 0 && deltaP.subtract(v.scale(deltaP.dotProduct(v) / vDotV)).lengthSquared() > radius * radius) {
                    return null;
                }

                // Check if the intersection point is outside the current object
                if (deltaP.subtract(v.scale(deltaPDotV1 / vDotV)).lengthSquared() > radius * radius) {
                    return null;
                }
                return List.of(intersectionPoint);
            } else { // Calculate two intersection points
                double t1 = deltaPDotV / vDotV;
                double t2 = t1 + Math.sqrt(radius * radius / vDotV - deltaP.lengthSquared() / vDotV / vDotV);

                Point intersectionPoint1 = p1.add(u.scale(t1));
                Point intersectionPoint2 = p1.add(u.scale(t2));

                return List.of(intersectionPoint1, intersectionPoint2);
            }
        } else { // Calculate two intersection points
            double t1 = (-b - Math.sqrt(discriminant)) / (2.0 * a);
            double t2 = (-b + Math.sqrt(discriminant)) / (2.0 * a);

            // Check for intersection in the correct direction of the input Ray object
            List<Point> intersections = new ArrayList<>();
            if (!isZero(t1)) {
                Point intersectionPoint1 = p1.add(u.scale(t1));
                if (!isZero(intersectionPoint1.distance(p0)) && intersectionPoint1.subtract(p1).dotProduct(u) > 0) {
                    intersections.add(intersectionPoint1);
                }
            }
            if (!isZero(t2)) {
                Point intersectionPoint2 = p1.add(u.scale(t2));
                if (!isZero(intersectionPoint2.distance(p0)) && intersectionPoint2.subtract(p1).dotProduct(u) > 0) {
                    intersections.add(intersectionPoint2);
                }
            }

            return intersections.isEmpty() ? null : List.of(intersections.toArray(new Point[0]));
        }
    }


}
