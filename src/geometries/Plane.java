package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * class of plane
 */
public class Plane implements Geometry{

    final Point q0;
    final Vector normal;

    /**
     * constructor from point and vector
     * @param q0 Point
     * @param normal Vector normal
     */
    public Plane(Point q0, Vector normal) {
        this.q0 = q0;
        this.normal = normal.normalize();
    }

    /**
     * constructor from 3 points
     * @param q0 point
     * @param q1 point
     * @param q2 point
     */
    public Plane(Point q0, Point q1, Point q2) {
        this.q0 = q0;
        Vector v1=q0.subtract(q1);
        Vector v2=q2.subtract(q1);
        normal = v1.crossProduct(v2).normalize();
    }

    @Override
    public Vector getNormal(Point p) {
        return this.normal;
    }

    public Point getQ0() {
        return q0;
    }

    public Vector getNormal() {
        return normal;
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }
}
