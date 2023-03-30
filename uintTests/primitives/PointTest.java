package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointTest {

    @Test
    void testSubtract() {
        Point p1 = new Point(1,2,3);
        Point p2 = new Point(3,3,4);

        // ============ Equivalence Partitions Tests ==============


        assertEquals(new Vector(-1,-1,-1), p1.subtract(p2), "Subtract Action not correct");


        // =============== Boundary Values Tests ==================

        assertThrows(IllegalArgumentException.class,()-> p1.subtract(p1),"Zero vector is created");

    }

    @Test
    void testAdd() {
    }

    @Test
    void testDistanceSquared() {
    }

    @Test
    void testDistance() {
    }
}