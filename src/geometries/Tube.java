package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

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

    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }
}
