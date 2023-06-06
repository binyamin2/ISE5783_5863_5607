package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.LinkedList;
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
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        List<GeoPoint> result = new LinkedList<>();
        Vector va = this.axisRay.getV0();
        Point p1 = this.axisRay.getP0();
        Point p2 = p1.add(this.axisRay.getV0().scale(this.height));

        Plane plane1 = new Plane(p1, this.axisRay.getV0()); //get plane of bottom base
        List<GeoPoint> result2 = plane1.findGeoIntersections(ray); //intersections with bottom's plane

        if (result2 != null) {
            //Add all intersections of bottom's plane that are in the base's bounders
            for (GeoPoint point : result2) {
                if (point.point.equals(p1)) { //to avoid vector ZERO
                    result.add(point);
                }
                //checks that point is inside the base
                else if ((point.point.subtract(p1).dotProduct(point.point.subtract(p1)) < this.radius * this.radius)) {
                    result.add(point);
                }
            }
        }

        List<GeoPoint> result1 = super.findGeoIntersectionsHelper(ray); //get intersections for tube

        if (result1 != null) {
            //Add all intersections of tube that are in the cylinder's bounders
            for (GeoPoint point : result1) {
                if (va.dotProduct(point.point.subtract(p1)) > 0 && va.dotProduct(point.point.subtract(p2)) < 0) {
                    result.add(point);
                }
            }
        }

        Plane plane2 = new Plane(p2, this.axisRay.getV0()); //get plane of top base
        List<GeoPoint> result3 = plane2.findGeoIntersections(ray); //intersections with top's plane

        if (result3 != null) {
            for (GeoPoint point : result3) {
                if (point.point.equals(p2)) { //to avoid vector ZERO
                    result.add(point);
                }
                //Formula that checks that point is inside the base
                else if ((point.point.subtract(p2).dotProduct(point.point.subtract(p2)) < this.radius * this.radius)) {
                    result.add(point);
                }
            }
        }

        if (result.size() > 0) {
            return result;
        }

        return null;
    }

    public Point getCenter() {
        return getAxisRay().getP0().add(getAxisRay().getV0().scale(height / 2.0));
    }
}

