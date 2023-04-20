package geometries;
import primitives.Point;
import primitives.Ray;
import java.util.List;

/**
 * interface for all the Intersectable geometries
 */
public interface Intersectable {
    /**
     * the method gets ray and check all the intersections with the current geometry
     * @param ray
     * @return list of intersection points
     */
    List<Point> findIntersections(Ray ray);

}
