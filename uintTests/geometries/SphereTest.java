package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

class SphereTest {


    @Test
    void testGetNormal() {

        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Point p=new Point(0,1,-1);
        double r=1;
        Sphere sp = new Sphere(r,p);
        // ensure there are no exceptions
        assertDoesNotThrow(() -> sp.getNormal(new Point(0, 1, 0)), "");
        // generate the test result
        Vector result = sp.getNormal(new Point(0, 1, 0));
        // ensure |result| = 1
        assertEquals(1, result.length(), 0.00000001, "sphere's normal is not a unit vector");
        // ensure the result is equivalent to the vector between the point to the center
        Vector dir=p.subtract(new Point(0,1,0));
        assertThrows(IllegalArgumentException.class ,
                ()->dir.crossProduct(result),
                "the normal is not in the right direction");



    }
    /**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */

    @Test
    void testfindIntersections() {
        Sphere sphere = new Sphere(1d, new Point (1, 0, 0));
        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(
                sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(1, 1, 0))),
                "Ray's line out of sphere");

        // TC02: Ray starts before and crosses the sphere (2 points)
        Point p1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        Point p2 = new Point(1.53484692283495, 0.844948974278318, 0);
        List<Point> result = sphere.findIntersections(
                new Ray(new Point(-1, 0, 0), new Vector(3, 1, 0)));
        assertEquals(2, result.size(), "Wrong number of points");
        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(p1, p2), result, "Ray crosses sphere");

        // TC03: Ray starts inside the sphere (1 point)
        Point p3 = new Point(1, 0, 1);
        List<Point> result1 = sphere.findIntersections(
                new Ray(new Point(1, 0, 0), new Vector(0, 0, 1)));
        assertEquals(1, result1.size(), "Wrong number of points in tc03");
        result1=List.of(result1.get(0));
        assertEquals(List.of(p3), result1, "Ray from inside sphere sphere");

        // TC04: Ray starts after the sphere (0 points)
        assertNull(
                sphere.findIntersections(new Ray(new Point(1, 0, 2), new Vector(0, 0, 1))),
                "Ray starts after the sphere");
        // =============== Boundary Values Tests ==================
        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 point)
        List<Point> result11 = sphere.findIntersections(
                new Ray(new Point(0, 0, 0), new Vector(1, 0, 1)));
        assertEquals(1, result11.size(), "Wrong number of points in tc11");

        // TC12: Ray starts at sphere and goes outside (0 points)
        assertNull(
                sphere.findIntersections(
                new Ray(new Point(0, 0, 0), new Vector(-2, 0, 1))),
                "Wrong number of points in tc12");


        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)

        List<Point> result13 = sphere.findIntersections(
                new Ray(new Point(-1, 0, 0), new Vector(1, 0, 0)));
        assertEquals(2, result13.size(), "Wrong number of pointstc13");

        // TC14: Ray starts at sphere and goes inside (1 point)
        List<Point> result14 = sphere.findIntersections(
                new Ray(new Point(0, 0, 0), new Vector(2, 0, 0)));
        assertEquals(1, result14.size(), "Wrong number of points in tc14");

        // TC15: Ray starts inside (1 point)
        List<Point> result15 = sphere.findIntersections(
                new Ray(new Point(1.3, 0, 0), new Vector(2, 0, 0)));
        assertEquals(1, result15.size(), "Wrong number of points in tc15");

        // TC16: Ray starts at the center (1 point)
        List<Point> result16 = sphere.findIntersections(
                new Ray(new Point(1, 0, 0), new Vector(0, 0, 1)));
        assertEquals(1, result16.size(), "Wrong number of points in tc16");

        // TC17: Ray starts at sphere and goes outside (0 points)
        assertNull(
                sphere.findIntersections(
                        new Ray(new Point(0, 0, 0), new Vector(-2, 0, 0))),
                "Wrong number of points in tc17");

        // TC18: Ray starts after sphere (0 points)
        assertNull(
                sphere.findIntersections(
                        new Ray(new Point(-1, 0, 0), new Vector(-2, 0, 1))),
                "Wrong number of points in tc18");


        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        assertNull(
                sphere.findIntersections(
                        new Ray(new Point(0, 0, -1), new Vector(0, 0, 2))),
                "Wrong number of points in tc19");
        // TC20: Ray starts at the tangent point
        assertNull(
                sphere.findIntersections(
                        new Ray(new Point(0, 0, 0), new Vector(0, 0, 2))),
                "Wrong number of points in tc20");
        // TC21: Ray starts after the tangent point
        assertNull(
                sphere.findIntersections(
                        new Ray(new Point(0, 0, 1), new Vector(0, 0, 2))),
                "Wrong number of points in tc21");


        // **** Group: Special cases
        // TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        assertNull(
                sphere.findIntersections(
                        new Ray(new Point(-1, 0, 0), new Vector(0, 0, 2))),
                "Wrong number of points in tc22");
    }
}