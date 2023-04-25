package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

class TriangleTest {

    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here - using a quad
        Point[] pts =
                { new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0) };
        Triangle tri = new Triangle(pts);
        // ensure there are no exceptions
        assertDoesNotThrow(() -> tri.getNormal(new Point(0, 0, 1)), "");
        // generate the test result
        Vector result = tri.getNormal(new Point(0, 0, 1));
        // ensure |result| = 1
        assertEquals(1, result.length(), 0.00000001, "Triangle's normal is not a unit vector");
        // ensure the result is orthogonal to all the edges
        for (int i = 0; i < 2; ++i)
            assertTrue(isZero(result.dotProduct(pts[i].subtract(pts[i == 0 ? 2 : i - 1]))),
                    "Triangle's normal is not orthogonal to one of the edges");

    }
    @Test
    void testfindIntersections() {
        Point[] pts =
                { new Point(0, 0, 0), new Point(1, 0, 0), new Point(0, 1, 0) };
        Triangle tri = new Triangle(pts);
        // ============ Equivalence Partitions Tests ==============
        //TC01 Ray intersects the triangle
        List<Point> result01=tri.findIntersections(
                new Ray(new Point(0.25,0.25,1),new Vector(0,0,-1)));
        //assertEquals(
         //       1,
             //   result01.get(0),
          //      "wrong number of intersection");
        result01=List.of(result01.get(0));
        assertEquals(
                new Point(0.25,0.25,0),
                result01.get(0),
                "the result is not correct");

        //TC02 Ray go against vertex of the triangle
        assertNull(tri.findIntersections(
                new Ray(new Point(-0.25,-0.25,1),new Vector(0,0,-1))),
                "wrong number of intersection");

        //TC03 Ray go against side of the triangle
        assertNull(tri.findIntersections(
                        new Ray(new Point(1,1,1),new Vector(0,0,-1))),
                "wrong number of intersection");


        // =============== Boundary Values Tests ==================
        //TC04 Ray go on vertex of the triangle
        assertNull(tri.findIntersections(
                        new Ray(new Point(1,0,1),new Vector(0,0,-1))),
                "wrong number of intersection");

        //TC05 Ray go on sid e of the triangle
        assertNull(tri.findIntersections(
                        new Ray(new Point(0.5,0,1),new Vector(0,0,-1))),
                "wrong number of intersection");

        //TC06 Ray go on the continue of the side of the triangle
        assertNull(tri.findIntersections(
                        new Ray(new Point(2,0,1),new Vector(0,0,-1))),
                "wrong number of intersection");


    }
}