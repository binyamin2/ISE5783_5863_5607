package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

class PlaneTest {


    @Test
    void testConstractor() {

        // =============== Boundary Values Tests ==================
        //TC10 two point are the same
        assertThrows(
                IllegalArgumentException.class,
                ()->new Plane(new Point(1,1,2),new Point(1,1,2),new Point(1,1,3)),
                "2 same point in the plane ctor dont throw exception"
                );
        //TC20 all the points on the same vector
        assertThrows(
                IllegalArgumentException.class,
                ()->new Plane(new Point(1,1,0),new Point(1,1,2),new Point(1,1,3)),
                "all the points on the same vector, plane ctor dont throw exception"
        );

    }
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here - using a quad
        Point[] pts =
                { new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0) };
        Plane pl = new Plane(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0));
        // ensure there are no exceptions
        assertDoesNotThrow(() -> pl.getNormal(new Point(0, 0, 1)), "");
        // generate the test result
        Vector result = pl.getNormal(new Point(0, 0, 1));
        // ensure |result| = 1
        assertEquals(1, result.length(), 0.00000001, "Plane's normal is not a unit vector");
        // ensure the result is orthogonal to all the edges
        for (int i = 0; i < 2; ++i)
            assertTrue(isZero(result.dotProduct(pts[i].subtract(pts[i == 0 ? 2 : i - 1]))),
                    "Plane's normal is not orthogonal to one of the edges");

    }

    @Test
    void testfindIntersections() {
        Plane plane=new Plane(new Point(0,0,0),new Vector(0,0,1));
        // =============== Boundary Values Tests ==================
        //*****Ray is parallel to the plane
        //TC01 the ray included in the plane
        assertNull(
                plane.findIntersections(new Ray (new Point(0,0,-1),new Vector(1,1,0))),
                "wrong number of intersection");

        //TC02 the ray not included in the plane
        assertNull(
                plane.findIntersections(new Ray (new Point(0,0,0),new Vector(1,0,-1))),
                "wrong number of intersection");


        //*****Ray is orthogonal to the plane
        //TC03 before the plane
        List<Point> result03=plane.findIntersections(
                new Ray(new Point(-1,-1,-1),new Vector(0,0,1)));
        assertEquals(
                1,
                result03.size(),
                "wrong number of intersection");

        //TC04 in the plane
        assertNull(
                plane.findIntersections(
                        new Ray (new Point(-1,-1,0),new Vector(0,0,1))),
                "wrong number of intersection");

        //TC05 after the plane
        assertNull(
                plane.findIntersections(
                        new Ray (new Point(-1,-1,1),new Vector(0,0,1))),
                "wrong number of intersection");


        //*****Ray is neither orthogonal nor parallel to and begins at the plane
        //TC06 begins at the plane
        assertNull(
                plane.findIntersections(
                        new Ray (new Point(-1,-1,0),new Vector(0,1,1))),
                "wrong number of intersection");


        //*****spacial cases
        //TC07 begins in the same point which appears as reference point in the plane (Q)
        assertNull(
                plane.findIntersections(
                        new Ray (new Point(0,0,0),new Vector(0,0,1))),
                "wrong number of intersection");


        // ============ Equivalence Partitions Tests ==============
        //TC08 Ray intersects the plane
        List<Point> result08=plane.findIntersections(
                new Ray(new Point(-1,-1,-1),new Vector(1,1,1)));
        result08=List.of(result08.get(0));
        assertEquals(
                1,
                result08.size(),
                "wrong number of intersection");
        assertEquals(
                new Point(0,0,0),
                result08.get(0),
                "not coorect point");

        //TC09 Ray does not intersect the plane
        assertNull(
                plane.findIntersections(
                        new Ray (new Point(-1,-1,-1),new Vector(-1,-1,-1))),
                "wrong number of intersection");

    }
}