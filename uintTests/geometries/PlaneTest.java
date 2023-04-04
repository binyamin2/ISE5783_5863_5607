package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

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
}