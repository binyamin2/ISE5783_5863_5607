package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

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
}