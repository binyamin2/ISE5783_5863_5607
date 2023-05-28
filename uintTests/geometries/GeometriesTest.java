package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GeometriesTest {



    @Test
    void testfindIntersections() {
        Geometries geo=new Geometries();
        Geometries geo1=new Geometries();
        geo1.add(new Triangle(new Point(1,0,0),new Point(0,1,0),new Point(0,0,0)),
                new Plane(new Point(0,0,0),new Vector(0,0,1)),
                new Polygon(new Point(0,0,0),new Point(0,1,0),new Point(1,1,0),new Point(1,0,0)),
                new Sphere(3,new Point(0,0,0)),
                new Tube(1,new Ray(new Point(0,0,0),new Vector(0,0,1)))
               /* new Cylinder(2,new Ray(new Point(0,0,0),new Vector(0,0,1)),2)*/
        );
        // ============ Equivalence Partitions Tests ==============
        //TC01 few intersections not all(sphere,tube,cylinder)
        List<Point> result01=geo1.findIntersections(
                new Ray(new Point(0,0,1.5),new Vector(0,1,0)));
        assertEquals(2,
                result01.size(),
                "the number of intersection is wrong");
        // =============== Boundary Values Tests ==================
        //TC10 empty list
        assertNull(geo.findIntersections(
                new Ray(new Point(0,0,0),new Vector(1,1,1))),
                "the number of intersection is wrong");

        //TC11 no intersections
        assertNull(geo.findIntersections(
                new Ray(new Point(5,0,0),new Vector(1,0,0))),
                "the number of intersection is wrong");

        //TC12 only 1 geometry intersects (tube)
        List<Point> result12=geo1.findIntersections(
                new Ray(new Point(0,0,5),new Vector(0,1,0)));
        assertEquals(1,
                result12.size(),
                "the number of intersection is wrong");

        //TC13 all geometries intersects (tube)
        List<Point> result13=geo1.findIntersections(
                new Ray(new Point(0,0,-0.2),new Vector(1,1,1)));
        assertEquals(5,
                result13.size(),
                "the number of intersection is wrong");

    }
}