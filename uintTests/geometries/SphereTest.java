package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

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
    //TODO check if radius positive

}