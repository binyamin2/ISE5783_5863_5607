package geometries;

import primitives.Point;
import primitives.Vector;

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
    public Sphere(Double radius, Point center) {
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
}
