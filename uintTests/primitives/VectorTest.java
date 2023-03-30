package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/** Testing vector
 * @author oz */
class VectorTest {

    /** Test method for  */
    @Test
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        Vector v1 = new Vector(1,2,3);
        Vector v2 = new Vector(1,2,3);
        //check add 2 positive vector
        assertEquals(
                new Vector(2,4,6),
                v1.add(v2),
                "ERROR: Point + Point does not work correctly");
        //check add 1 positive vector and 1 negative
        assertEquals(
                new Vector(-1,-2,-3),
                v1.add(new Vector(-2,-4,-6)),
                "ERROR: Point + Point does not work correctly");
        // =============== Boundary Values Tests ==================
        //check if vector zero created
        assertThrows(
                IllegalArgumentException.class,
                ()->v1.add(new Vector(-1,-2,-3)),
                  "ERROR: Vector + -itself does not throw an exception"  );
    }

    @Test
    void testScale() {

        Vector v1 = new Vector(1,2,3);
        // ============ Equivalence Partitions Tests ==============
        //scale is positive
        assertEquals(
                new Vector(2,4,6),
                v1.scale(2.0),
                "scale action not correct");
        //scale is negative
        assertEquals(
                new Vector(-2,-4,-6),
                v1.scale(-2.0),
                "scale action not correct");
        // =============== Boundary Values Tests ==================
        //scale is negative
        assertThrows(
                IllegalArgumentException.class,
                ()-> v1.scale(0.0),
                "scale 0 action not throw an exception");

    }

    @Test
    void testDotProduct() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        Vector v3 = new Vector(0, 3, -2);
        // ============ Equivalence Partitions Tests ==============
        //check dot 2 vectors
        assertEquals(
                -28,
                v1.dotProduct(v2),
                "dotProduct() wrong value");
        // =============== Boundary Values Tests ==================
        //check dot 2 orthogonal vectors
        assertEquals(
                0,
                v1.dotProduct(v3),
                " dotProduct() for orthogonal vectors is not zero");

    }

    @Test
    void testCrossProduct() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        Vector v3 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v3);
        // ============ Equivalence Partitions Tests ==============
        //cross 2 vectors
        assertEquals(
                vr.length(),
                v1.length() * v3.length(),
                0.0001,
                "crossProduct() wrong result length");
        //check if the result of cross is orthogonal to v1,v1
        assertEquals(
                0,
                vr.dotProduct(v1),
                "crossProduct() result is not orthogonal to its operands");
        assertEquals(
                0,
                vr.dotProduct(v2),
                "crossProduct() result is not orthogonal to its operands");
        // =============== Boundary Values Tests ==================
        //check parallel vectors
        assertThrows(
                IllegalArgumentException.class,
                ()->v1.crossProduct(v2),
                "crossProduct() for parallel vectors does not throw an exception"
        );

    }

    @Test
    void testLengthSquared() {
        Vector v1= new Vector(0,3,4);
        assertEquals(
                25,
                v1.lengthSquared(),
                0.001,
                "LengthSquared() is not correct");
    }

    @Test
    void testLength() {
        Vector v1= new Vector(0,3,4);
        assertEquals(
                5,
                v1.length(),
                0.001,
                "Length() is not correct");
    }

    @Test
    void testNormalize() {
        Vector v = new Vector(1, 2, 3);
        Vector u = v.normalize();
        // ============ Equivalence Partitions Tests ==============
        //check if the action correct
        assertEquals(
                1,
                u.length(),
                0.001,
                "the normalized vector is not a unit vector");
        // =============== Boundary Values Tests ==================
        //check if dos parallel to the origin vector
        assertThrows(
                IllegalArgumentException.class,
                ()->v.crossProduct(u),
                "the normalized vector is not parallel to the original one"
        );
        //check if the normalize vector is in the right direction
        assertTrue(
                v.dotProduct(u) > 0,
                "the normalized vector is opposite to the original one");

    }
}