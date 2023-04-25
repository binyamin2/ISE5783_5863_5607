package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.*;
import java.util.List;

/**
 * class of Triangle that extends Polygon
 */
public class Triangle extends Polygon{

    /**
     * ctor that take points
     * @param vertices
     */
    public Triangle(Point... vertices) {
        super(vertices);
    }

    @Override
    public List<Point> findIntersections(Ray ray) {

            //List<Point> intersections = new ArrayList<>();
            Point rayPoint = ray.getP0();
            Vector rayVector = ray.getV0();
            Point trianglePoint1 = vertices.get(0);
            Point trianglePoint2 = vertices.get(1);;
            Point trianglePoint3 = vertices.get(2);;

            Vector edge1 = new Vector(trianglePoint2.getX() - trianglePoint1.getX(), trianglePoint2.getY() - trianglePoint1.getY(), trianglePoint2.getZ() - trianglePoint1.getZ());
            Vector edge2 = new Vector(trianglePoint3.getX() - trianglePoint1.getX(), trianglePoint3.getY() - trianglePoint1.getY(), trianglePoint3.getZ() - trianglePoint1.getZ());
            Vector h = rayVector.crossProduct(edge2);
            double a = edge1.dotProduct(h);

            if (isZero(a)) {
                return null; // ray is parallel to triangle
            }

            //double[] s = new double[] {rayPoint.getX() - trianglePoint1.getX(),
                    //rayPoint.getY() - trianglePoint1.getY(), rayPoint.getZ() - trianglePoint1.getZ()};
            Vector s=rayPoint.subtract(trianglePoint1);
            double f = 1.0 / a;
            double u=s.dotProduct(h)*f;
            //double u = f * (s.getX()*h.getX() + s[1]*h.getY() + s[2]*h.getZ());

            if (u < 0.0 || u > 1.0) {
                return intersections; // intersection point is outside triangle
            }

            Vector q = new Vector(s[1]*edge1.getZ() - s[2]*edge1.getY(), s[2]*edge1.getX() - s[0]*edge1.getZ(), s[0]*edge1.getY() - s[1]*edge1.getX());
            double v = f * rayVector.dotProduct(q);

            if (v < 0.0 || u + v > 1.0) {
                return intersections; // intersection point is outside triangle
            }

            double w = 1.0 - u - v;
            Point intersectionPoint = new Point(w*trianglePoint1.getX() + u*trianglePoint2.getX() + v*trianglePoint3.getX(),
                    w*trianglePoint1.getY() + u*trianglePoint2.getY() + v*trianglePoint3.getY(),
                    w*trianglePoint1.getZ() + u*trianglePoint2.getZ() + v*trianglePoint3.getZ());
            intersections.add(intersectionPoint);

            return intersections;
        }

    }
}
