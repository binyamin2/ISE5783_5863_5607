package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TubeTest {

    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Ray p=new Ray(new Point(0,0,0),new Vector(0,1,0));
        double r=1;
        Tube tu = new Tube(r,p);
        // ensure there are no exceptions
        assertDoesNotThrow(() -> tu.getNormal(new Point(1, 1, 0)), "");
        // generate the test result
        Vector result = tu.getNormal(new Point(1, 1, 0));
        // ensure |result| = 1
        assertEquals(1, result.length(), 0.00000001, "tube's normal is not a unit vector");
        // ensure the result is equivalent to the vector between the point to the center ray
        Point temp=new Point(1,1,0);
        Vector dir=temp.subtract(new Point(0,1,0));
        assertEquals(
                dir.normalize(),
                tu.getNormal(temp),
                "the normal is not correct in regular point");

                                                                                                                                                                // =============== Boundary Values Tests ==================
        //TC10
        //check the case when the point equivalent to base point
        Point pbvt=new Point(0,0,1);
        Vector vbvt=pbvt.subtract(new Point(0,0,0));
        assertDoesNotThrow(() -> tu.getNormal(new Point(0, 0, 1)), "");
        assertEquals(vbvt.normalize(),tu.getNormal(pbvt),
                "the normal is not correct when the point equivalent to base point");
    }
    @Test
    void testfindIntersections() {
        Tube tu=new Tube(1,new Ray(new Point(0,0,0),new Vector(0,0,1)));
        // ============ Equivalence Partitions Tests ==============
        //TC01 Ray intersects the tube from outside (2 points)
        List<Point> result01=tu.findIntersections(
                new Ray(new Point(0,-2,0.5),new Vector(0,4,0)));
        assertEquals(
                2,
                result01.size(),
                "wrong number of intersection");
        result01=List.of(result01.get(0),result01.get(1));
        Point p1=new Point(0,-1,0.5);
        Point p2=new Point(0,1,0.5);
        assertEquals(List.of(p1,p2),result01,"wrong point values");

        //TC02 Ray intersects the tube from inside (1 point)
        List<Point> result02=tu.findIntersections(
                new Ray(new Point(0,0,0.5),new Vector(0,2,0)));
        assertEquals(
                1,
                result02.size(),
                "wrong number of intersection");
        result02=List.of(result02.get(0));
        assertEquals(List.of(p2),result02,"wrong point values");

        //TC03 Ray not intersect the tube (0 points)
        assertNull(tu.findIntersections(
                new Ray(new Point(0,1.5,0.5),new Vector(0,2,0))),
                "wrong number of intersections");


        // =============== Boundary Values Tests ==================
        //***start at the tube
        //TC04 Ray start at the tube to inside (1 point)
        List<Point> result03=tu.findIntersections(
                new Ray(new Point(0,-1,0.5),new Vector(0,2,0)));
        assertEquals(
                1,
                result03.size(),
                "wrong number of intersection");

        //TC05 Ray start at the tube to outside (0 point)
        assertNull(tu.findIntersections(
                        new Ray(new Point(0,1,0.5),new Vector(0,2,0))),
                "wrong number of intersections");


        //***ray equivalent to the tube ray
        //TC06 inside the tube (0 point)
        assertNull(tu.findIntersections(
                        new Ray(new Point(0,0.5,0),new Vector(0,0,1))),
                "wrong number of intersections");

        //TC07 outside the tube (0 point)
        assertNull(tu.findIntersections(
                        new Ray(new Point(2,0,0),new Vector(0,0,1))),
                "wrong number of intersections");

        //TC08 on the tube (0 point)
        assertNull(tu.findIntersections(
                        new Ray(new Point(0,1,0),new Vector(0,0,1))),
                "wrong number of intersections");

        //***ray tangent to tube
        //TC09 ray start before tangent point(0 points)
        assertNull(tu.findIntersections(
                        new Ray(new Point(1,-2,0.5),new Vector(0,4,0))),
                "wrong number of intersections");

        //TC10 ray start on the tangent point(0 points)
        assertNull(tu.findIntersections(
                        new Ray(new Point(1,0,0.5),new Vector(0,4,0))),
                "wrong number of intersections");

        //TC11 ray start after the tangent point(0 points)
        assertNull(tu.findIntersections(
                        new Ray(new Point(1,1,0.5),new Vector(0,4,0))),
                "wrong number of intersections");
    }
}