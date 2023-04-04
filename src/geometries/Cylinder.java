package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * class that represent the Cylinder geometry
 */
public class Cylinder extends Tube{

    final double height;

    /**
     * ctor
     * @param radius
     * @param axisRay
     * @param height
     */
    public Cylinder(Double radius, Ray axisRay, double height) {
        super(radius, axisRay);
        this.height = height;
    }

    public double getHeight() {
        return height;
    }

    @Override
    public Vector getNormal(Point p) {
        //TODO implement
        return super.getNormal(p);
    }
}
