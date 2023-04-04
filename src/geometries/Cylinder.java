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
        // same point of the center base 1
        if (p == this.axisRay.getP0())
            return this.axisRay.getV0().scale(-1).normalize();
        // same point of the center base 2
        else if (p == this.axisRay.getP0().add(this.axisRay.getV0().scale(height)))
            return this.axisRay.getV0().normalize();
        //find the distance between p0 to o
        //TODO find the bag!! 
        double t=this.axisRay.getV0().dotProduct(p.subtract(axisRay.getP0()));

        // if t = 0 is mean that on base 1
        if (t == 0)
            return this.axisRay.getV0().scale(-1).normalize();
        // point on base 2
        else if (t == height)
            return this.axisRay.getV0().normalize();
        //point like tube on casing
        else if (t > 0 && t < this.height)
           return super.getNormal(p);
        else
            throw new IllegalArgumentException("the point not in the range");
    }
}
