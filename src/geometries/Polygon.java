package geometries;

import static primitives.Util.isZero;

import java.util.List;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/** Polygon class represents two-dimensional polygon in 3D Cartesian coordinate
 * system
 * @author Dan */
public class Polygon extends Geometry {
    /** List of polygon's vertices */
    protected final List<Point> vertices;
    /** Associated plane in which the polygon lays */
    protected final Plane       plane;
    private final int           size;

    /** Polygon constructor based on vertices list. The list must be ordered by edge
     * path. The polygon must be convex.
     * @param  vertices                 list of vertices according to their order by
     *                                  edge path
     * @throws IllegalArgumentException in any case of illegal combination of
     *                                  vertices:
     *                                  <ul>
     *                                  <li>Less than 3 vertices</li>
     *                                  <li>Consequent vertices are in the same
     *                                  point
     *                                  <li>The vertices are not in the same
     *                                  plane</li>
     *                                  <li>The order of vertices is not according
     *                                  to edge path</li>
     *                                  <li>Three consequent vertices lay in the
     *                                  same line (180&#176; angle between two
     *                                  consequent edges)
     *                                  <li>The polygon is concave (not convex)</li>
     *                                  </ul>
     */
    public Polygon(Point... vertices) {
        if (vertices.length < 3)
            throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
        this.vertices = List.of(vertices);
        size          = vertices.length;

        // Generate the plane according to the first three vertices and associate the
        // polygon with this plane.
        // The plane holds the invariant normal (orthogonal unit) vector to the polygon
        plane         = new Plane(vertices[0], vertices[1], vertices[2]);
        if (size == 3) return; // no need for more tests for a Triangle

        Vector  n        = plane.getNormal();
        // Subtracting any subsequent points will throw an IllegalArgumentException
        // because of Zero Vector if they are in the same point
        Vector  edge1    = vertices[vertices.length - 1].subtract(vertices[vertices.length - 2]);
        Vector  edge2    = vertices[0].subtract(vertices[vertices.length - 1]);

        // Cross Product of any subsequent edges will throw an IllegalArgumentException
        // because of Zero Vector if they connect three vertices that lay in the same
        // line.
        // Generate the direction of the polygon according to the angle between last and
        // first edge being less than 180 deg. It is hold by the sign of its dot product
        // with
        // the normal. If all the rest consequent edges will generate the same sign -
        // the
        // polygon is convex ("kamur" in Hebrew).
        boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;
        for (var i = 1; i < vertices.length; ++i) {
            // Test that the point is in the same plane as calculated originally
            if (!isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
                throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");
            // Test the consequent edges have
            edge1 = edge2;
            edge2 = vertices[i].subtract(vertices[i - 1]);
            if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
                throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
        }
    }

    @Override
    public Vector getNormal(Point point) { return plane.getNormal(); }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        // Check if ray intersects the plane containing the polygon
        List<GeoPoint> intersections = plane.findGeoIntersectionsHelper(ray);
        if (intersections == null){
            return  null;
        }

        Point[] vertices = this.vertices.toArray(new Point[0]);
//        Vector normal = this.plane.getNormal();
//        double denominator = normal.dotProduct(ray.getV0());
//        double numerator = normal.dotProduct(vertices[0].subtract(ray.getP0()));
//        double t = numerator / denominator;

        Point intersectionPoint = intersections.get(0).point;

        // Check if intersection point is inside the polygon
        int n = vertices.length;
        for (int i = 0; i < n; i++) {
            Point p1 = vertices[i];
            Point p2 = vertices[(i + 1) % n];
            Point p3 = vertices[(i + 2) % n];

            // Calculate barycentric coordinates for triangle formed by p1, p2, and p3
            Vector v1 = p2.subtract(p1);
            Vector v2 = p3.subtract(p1);
            Vector v3 = intersectionPoint.subtract(p1);

            double dot11 = v1.dotProduct(v1);
            double dot12 = v1.dotProduct(v2);
            double dot13 = v1.dotProduct(v3);
            double dot22 = v2.dotProduct(v2);
            double dot23 = v2.dotProduct(v3);

            double invDenom = 1.0 / (dot11 * dot22 - dot12 * dot12);
            double u = (dot22 * dot13 - dot12 * dot23) * invDenom;
            double v = (dot11 * dot23 - dot12 * dot13) * invDenom;

            // Check if intersection point is inside the triangle
            if (u >= 0 && v >= 0 && u + v <= 1) {
                // Check if intersection point is on an edge or vertex
                if (isZero(u) || isZero(v) || isZero(u + v - 1)) {
                    return null;
                } else {
                    return intersections;
                }}
        }
        return null; // Intersection point is outside the polygon
    }




}
