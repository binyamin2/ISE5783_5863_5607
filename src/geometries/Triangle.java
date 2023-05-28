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
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {

            Point trianglePoint1 = vertices.get(0);
            Point trianglePoint2 = vertices.get(1);;
            Point trianglePoint3 = vertices.get(2);;

            Vector edge1 = new Vector(trianglePoint2.getX() - trianglePoint1.getX(), trianglePoint2.getY() - trianglePoint1.getY(), trianglePoint2.getZ() - trianglePoint1.getZ());
            Vector edge2 = new Vector(trianglePoint3.getX() - trianglePoint1.getX(), trianglePoint3.getY() - trianglePoint1.getY(), trianglePoint3.getZ() - trianglePoint1.getZ());
            Vector h = ray.getV0().crossProduct(edge2);
            double a = edge1.dotProduct(h);

            if (isZero(a)) {
                return null; // ray is parallel to triangle
            }


            Vector s=ray.getP0().subtract(trianglePoint1);
            double f = 1.0 / a;
            double u=s.dotProduct(h)*f;


            if (u < 0 || u > 1) {
                return null; // intersection point is outside triangle
            }

            Vector q = new Vector(
                    s.getY()*edge1.getZ() - s.getZ()*edge1.getY(),
                    s.getZ()*edge1.getX() - s.getX()*edge1.getZ(),
                    s.getX()*edge1.getY() - s.getY()*edge1.getX());

            double v = f * ray.getV0().dotProduct(q);

            if (v < 0.0 || u + v > 1.0) {
                return null; // intersection point is outside triangle
            }
        double w = 1.0 - u - v;
            // check if the intersection point is on one of the triangle's edges or vertices
            if (isZero(u) || isZero(v) || isZero(w)) {
                return null;
            }
            Point resultPoint= new Point(w*trianglePoint1.getX() + u*trianglePoint2.getX() + v*trianglePoint3.getX(),
                    w*trianglePoint1.getY() + u*trianglePoint2.getY() + v*trianglePoint3.getY(),
                    w*trianglePoint1.getZ() + u*trianglePoint2.getZ() + v*trianglePoint3.getZ());


            return List.of(new GeoPoint(this,resultPoint));


        }

}

