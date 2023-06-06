package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CylinderTest {

    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============

        Ray p=new Ray(new Point(0,0,0),new Vector(0,1,0));
        double h=4;
        double r=1;
        Cylinder cy = new Cylinder(r,p,h);
        // TC01: There is a simple single test here-check the casing of the cylinder
        // ensure there are no exceptions
        assertDoesNotThrow(() -> cy.getNormal(new Point(1, 1, 0)), "");
        // generate the test result
        Vector result = cy.getNormal(new Point(1, 1, 0));
        // ensure |result| = 1
        assertEquals(1, result.length(), 0.00000001, "tube's normal is not a unit vector");
        // ensure the result is equivalent to the vector between the point to the center ray
        Point temp=new Point(1,1,0);
        Vector dir=temp.subtract(new Point(0,1,0));
        assertEquals(
                dir.normalize(),
                cy.getNormal(temp),
                "the normal is not correct in regular point");

        //TC02 the plane under the cylinder :base 1
        Vector result2= cy.getNormal(new Point(0.5,0,0));
        Vector tc2=new Vector(0,-1,0);
        assertEquals(tc2,
                result2,
                "the normal is not correct in base 1");

        //TC03 the plane above the cylinder
        Vector result3= cy.getNormal(new Point(0.5,4,0));
        Vector tc3=new Vector(0,1,0);
        assertEquals(tc3,
                result3,
                "the normal is not correct in base 1");
        // =============== Boundary Values Tests ==================
        //TC10
        //check get normal from point that on the center ray of the tube:base1
        Vector vbvt=new Vector(0,-1,0);
        Point pbvt=new Point(0,0,0);
        assertEquals(
                vbvt,
                cy.getNormal(pbvt),
                "the normal is not correct in canter base 1");
        //check get normal from point that on the center ray of the tube:base2
        vbvt=vbvt.scale(-1);
        assertEquals(
                vbvt,
                cy.getNormal(new Point(0,h,0)),
                "the normal is not correct in canter base 2");
    }
    @Test
    void testfindIntersections() {
        Ray p=new Ray(new Point(0,0,0),new Vector(0,0,1));
        double h=4;
        double r=1;
        Cylinder cy = new Cylinder(r,p,h);

        // ============ Equivalence Partitions Tests ==============
        //*****equivalent to the main ray
        //TC01 from outside the cylinder(2 points)
        List<Point> result01=cy.findIntersections(
                new Ray(new Point(0,0.5,-1),new Vector(0,0,4)));
        assertEquals(
                2,
                result01.size(),
                "wrong number of intersection");
        result01=List.of(result01.get(0),result01.get(1));
        Point p1=new Point(0,0.5,0);
        Point p2=new Point(0,0.5,4);
        assertEquals(List.of(p1,p2),result01,"wrong point values");

        //TC02 from inside the cylinder(1 point)
        List<Point> result02=cy.findIntersections(
                new Ray(new Point(0,0.5,1),new Vector(0,0,4)));
        assertEquals(
                1,
                result02.size(),
                "wrong number of intersection");
        result02=List.of(result01.get(0));
        assertEquals(List.of(p1),result02,"wrong point values");

        //TC03 from outside the cylinder(0 point)
        assertNull(cy.findIntersections(new Ray(new Point(0,0,5),new Vector(0,0,1))),
                "wrong number of intersection");

        // =============== Boundary Values Tests ==================
        //***on the plane base of the cylinder
        //TC04 the ray is inside the plane
        assertNull(cy.findIntersections(new Ray(new Point(-2,0,0),new Vector(2,0,0))),
                "wrong number of intersection");

        //TC05 the ray starts on the plane inside(1 point)
        List<Point> result05=cy.findIntersections(
                new Ray(new Point(0,0.5,0),new Vector(0,1,4)));
        assertEquals(
                1,
                result05.size(),
                "wrong number of intersection");

        //TC05 the ray starts on the plane outside(0 point)
        assertNull(cy.findIntersections(new Ray(new Point(0,0.5,0),new Vector(0,1,-1))),
                "wrong number of intersection");

    }
}