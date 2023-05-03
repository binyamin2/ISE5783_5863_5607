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
        Point center=this.getCenter();
        // Check if the ray is equivalent to the axis ray of the cylinder
        if (ray.getV0().normalize().dotProduct(getAxisRay().getV0().normalize()) == 1 ||
                ray.getV0().normalize().dotProduct(getAxisRay().getV0().normalize()) == -1) {
            List<Point> intersections = new ArrayList<>();

            // Calculate the intersection of the ray with the planes that define the top and bottom ends of the cylinder
            double t1 = (height / 2.0 - (ray.getP0().subtract(getAxisRay().getP0())).dotProduct(getAxisRay().getV0().normalize())) / ray.getV0().dotProduct(getAxisRay().getV0().normalize());
            double t2 = (-height / 2.0 - (ray.getP0().subtract(getAxisRay().getP0())).dotProduct(getAxisRay().getV0().normalize())) / ray.getV0().dotProduct(getAxisRay().getV0().normalize());

            // Check if the intersection points are within the cylinder's radius
            Point intersection1 = ray.getPoint(t1);
            if (intersection1.subtract(center).lengthSquared() <= radius * radius) {
                intersections.add(intersection1);
            }
            Point intersection2 = ray.getPoint(t2);
            if (intersection2.subtract(center).lengthSquared() <= radius * radius) {
                intersections.add(intersection2);
            }

            if (intersections.isEmpty()) {
                return null;
            }

            return intersections;
        }

        // Get the intersections with the tube
        List<Point> intersections = super.findIntersections(ray);

        if (intersections == null) {
            // No intersections with the tube, so no intersections with the cylinder
            return null;
        }

        // Calculate the intersection points with the ends of the cylinder
        Point p0 = getAxisRay().getP0();
        Vector v = getAxisRay().getV0().normalize();

        List<Point> endIntersections = new ArrayList<>();
        double t1 = (height / 2.0 - p0.subtract(ray.getP0()).dotProduct(v)) / ray.getV0().dotProduct(v);
        if (t1 > 0) {
            Point intersection1 = ray.getP0().add(ray.getV0().scale(t1));
            if (intersection1.subtract(center).lengthSquared() <= radius * radius) {
                endIntersections.add(intersection1);
            }
        }

        double t2 = (-height / 2.0 - p0.subtract(ray.getP0()).dotProduct(v)) / ray.getV0().dotProduct(v);
        if (t2 > 0) {
            Point intersection2 = ray.getP0().add(ray.getV0().scale(t2));
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

    public Point getCenter() {
        return getAxisRay().getP0().add(getAxisRay().getV0().scale(height / 2.0));
    }
}

