package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//Uint test for Points
class PointTest {

    @Test
    void testSubtract() {
        Point p1 = new Point(1,2,3);
        Point p2 = new Point(3,3,4);

        // ============ Equivalence Partitions Tests ==============

        //01: Check sub to point, regular point
        assertEquals(new Vector(-1,-1,-1), p1.subtract(p2), "Subtract Action not correct");


        // =============== Boundary Values Tests ==================

        //10: Check sub same point, need throw exception
        assertThrows(
                IllegalArgumentException.class,
                ()-> p1.subtract(p1),
                "Zero vector is created");

    }

    @Test
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        Point p1 = new Point(1,2,3);

        //01: Check add Vector to point
        assertEquals(
                new Point(2,3,4),
                p1.add(new Vector(1,1,1)),
                "Add action not working");

    }

    @Test
    void testDistanceSquared() {
        // ============ Equivalence Partitions Tests ==============
        Point p1 = new Point(1,2,3);


        assertEquals(
                14,
                p1.distanceSquared(new Point(0,0,0)),
                0.0001,
                "distance Squered not correct");


    }

    @Test
    void testDistance() {
        // ============ Equivalence Partitions Tests ==============
        Point p1 = new Point(1,2,3);

        //check distance between tow points
        assertEquals(
                Math.sqrt(14),
                p1.distance(new Point(0,0,0)),
                0.0001,
                "distance not correct");




    }
}