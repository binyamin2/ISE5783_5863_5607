package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

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
    public Cylinder(double radius, Ray axisRay, double height) {
        super(radius, axisRay);
        this.height = height;
    }

    
    public double getHeight() {
        return height;
    }

    @Override
    public Vector getNormal(Point p) {
        // same point of the center base 1
        if (p.equals(this.axisRay.getP0()))
            return this.axisRay.getV0().scale(-1).normalize();
        // same point of the center base 2
        else if (p.equals(this.axisRay.getPoint(height)))
            return this.axisRay.getV0();

        //find the distance between p0 to o
        double t=this.axisRay.getV0().dotProduct(p.subtract(axisRay.getP0()));

        // if t = 0 is mean that on base 1
        if (t == 0)
            return this.axisRay.getV0().scale(-1).normalize();
        // point on base 2
        else if (t == height)
            return this.axisRay.getV0();
        //point like tube on casing
        else if (t > 0 && t < this.height)
           return super.getNormal(p);
        else
            throw new IllegalArgumentException("the point not in the range");
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        // Get the intersections with the tube
        List<Point> intersections = super.findIntersections(ray);

        if (intersections == null) {
            // No intersections with the tube, so no intersections with the cylinder
            return null;
        }

        // Calculate the intersection points with the ends of the cylinder
        Point p0 = getAxisRay().getP0();
        Vector v = getAxisRay().getV0().normalize();
        Point center1 = p0.add(v.scale(height/2.0));
        Point center2 = p0.subtract(v.scale(height/2.0));
        Point center = new Point((center1.getX()+center2.getX())*0.5,(center1.getY()+center2.getY())*0.5,(center1.getZ()+center2.getZ())*0.5);


        List<Point> endIntersections = new ArrayList<>();
        double t1 = (height / 2.0 - p0.subtract(ray.getP0()).dotProduct(v)) / ray.getV0().dotProduct(v);
        if (t1 > 0) {
            Point intersection1 = ray.getPoint(t1);
            if (intersection1.subtract(center).lengthSquared() <= radius * radius) {
                endIntersections.add(intersection1);
            }
        }

        double t2 = (-height / 2.0 - p0.subtract(ray.getP0()).dotProduct(v)) / ray.getV0().dotProduct(v);
        if (t2 > 0) {
            Point intersection2 = ray.getPoint(t2);
            if (intersection2.subtract(center).lengthSquared() <= radius * radius) {
                endIntersections.add(intersection2);
            }
        }

        if (endIntersections.isEmpty()) {
            // No intersections with the ends of the cylinder
            return intersections;
        }

        if (intersections.isEmpty()) {
            // No intersections with the tube, only with the ends of the cylinder
            return endIntersections;
        }

        // Merge the intersection points from the tube and the ends of the cylinder
        intersections.addAll(endIntersections);
        return intersections;
    }
}

