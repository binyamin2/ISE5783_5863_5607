package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class TubeTest {

    @Test
    void getNormal() {
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
        Point temp=new Point(0,1,0);
        Vector dir=temp.subtract(new Point(1,1,0));
        assertThrows(IllegalArgumentException.class ,
                ()->dir.crossProduct(result),
                "the normal is not in the right direction");
    }
    //TODO check the boundary cases    4
}