package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class CylinderTest {

    @Test
    void getNormal() {
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
        Point temp=new Point(0,1,0);
        Vector dir=temp.subtract(new Point(1,1,0));
        assertThrows(IllegalArgumentException.class ,
                ()->dir.crossProduct(result),
                "the normal is not in the right direction");
        //TC02 the plane under the cylinder
        Vector result2= cy.getNormal(new Point(0.5,0,0));
        assertThrows(IllegalArgumentException.class ,
                ()->result2.crossProduct(new Vector(0,1,0)),
                "the normal is not in the right direction");
        //TC03 the plane above the cylinder
        Vector result3= cy.getNormal(new Point(0.5,4,0));
        assertThrows(IllegalArgumentException.class ,
                ()->result2.crossProduct(new Vector(0,1,0)),
                "the normal is not in the right direction");
        // TODO //TODO check the 2 boundary cases


    }
}