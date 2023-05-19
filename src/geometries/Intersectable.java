package geometries;
import primitives.Point;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * interface for all the Intersectable geometries
 */
public abstract class Intersectable {
    /**
     * for connecting between Point and geometry
     */
    public static class GeoPoint {
        public Geometry geometry;
        public Point point;

        /**
         * ctor
         * @param geometry
         * @param point
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            GeoPoint geoPoint = (GeoPoint) o;

            if (!Objects.equals(geometry, geoPoint.geometry)) return false;
            return Objects.equals(point, geoPoint.point);
        }

        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }

    }

    /**
     * the method gets ray and check all the intersections with the current geometry
     * @param ray
     * @return geopoint
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray){
        return findGeoIntersectionsHelper(ray);
    }
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);
    /**
     * the method gets ray and check all the intersections with the current geometry
     * @param ray
     * @return list of intersection points
     */
    public List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }




}
