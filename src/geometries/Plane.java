package geometries;

import primitives.Point;
import primitives.Vector;

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
        normal = null;
    }

    @Override
    public Vector getNormal(Point p) {
        return null;
    }

    public Point getQ0() {
        return q0;
    }

    public Vector getNormal() {
        return normal;
    }
}
