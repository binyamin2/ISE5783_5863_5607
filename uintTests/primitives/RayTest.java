package primitives;

import geometries.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RayTest {

    @Test
    void testFindClosetPoint() {
        String error = "It isn't the closet point";

        // ============ Equivalence Partitions Tests ==============
        //TC01 The closet point is in the middle of the list
        Geometries geo = new Geometries();
        geo.add(new Triangle(new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 0)),
                new Sphere(3, new Point(0.25, 0.25, 0)),
                new Plane(new Point(0, 0, 1), new Vector(0, 0, 1))
        );

        Ray r = new Ray(new Point(0.25, 0.25, -4), new Vector(0, 0, 1));
        List<Point> result0 = geo.findIntersections(r);

        assertEquals(new Point(0.25, 0.25, -3),
                r.findClosetPoint(result0),
                error);

        // =============== Boundary Values Tests ==================
        //TC10 empty list
        Ray r10 = new Ray(new Point(-10, 0, 0), new Vector(0, 0, -1));
        assertNull(r10.findClosetPoint(geo.findIntersections(r10)),
                "the number of intersection is wrong");

        //TC20 The closet point is the first point
        Geometries geo20 = new Geometries();
        geo20.add(new Sphere(3, new Point(0.25, 0.25, 0)),
                new Triangle(new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 0)),
                new Plane(new Point(0, 0, 1), new Vector(0, 0, 1))
        );

        Ray r20 = new Ray(new Point(0.25, 0.25, -4), new Vector(0, 0, 1));

        assertEquals(new Point(0.25, 0.25, -3),
                geo20.findIntersections(r20),
                error);

        //TC30 The closet point is the first point
        Geometries geo30 = new Geometries();
        geo30.add(
                new Triangle(new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 0)),
                new Plane(new Point(0, 0, 1), new Vector(0, 0, 1)),
                new Sphere(3, new Point(0.25, 0.25, 0))
        );

        Ray r30 = new Ray(new Point(0.25, 0.25, -4), new Vector(0, 0, 1));

        assertEquals(new Point(0.25, 0.25, -3),
                geo30.findIntersections(r30),
                error);
    }
}