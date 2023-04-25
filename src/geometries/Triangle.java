package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * class of Triangle that extends Polygon
 */
public class Triangle extends Polygon{

    /**
     * ctor that take points
     * @param vertices
     */
    public Triangle(Point... vertices) {
        super(vertices);
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        return super.findIntersections(ray);
    }
}
