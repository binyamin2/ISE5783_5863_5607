package geometries;

import primitives.Point;
import primitives.Vector;

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
    public Vector getNormal(Point point) {
        return super.getNormal(point);
    }
}
