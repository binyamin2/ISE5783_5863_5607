package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * interface for all the geometries
 */
public interface Geometry {
    /**
     * return the normal of the geometry from the point
     * @param p point on the geometry
     * @return vector normal to the point
     */
    public Vector getNormal(Point p);
}
