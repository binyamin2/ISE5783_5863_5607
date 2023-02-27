package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

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
    public Tube(Double radius, Ray axisRay) {
        super(radius);
        this.axisRay = axisRay;
    }

    public Ray getAxisRay() {
        return axisRay;
    }

    @Override
    public Vector getNormal(Point p) {
        return null;
    }
}
