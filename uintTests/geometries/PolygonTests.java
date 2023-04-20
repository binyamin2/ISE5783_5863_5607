package geometries;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;
import static primitives.Util.isZero;

import org.junit.jupiter.api.Test;

import geometries.Polygon;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/** Testing Polygons
 * @author Dan */
public class PolygonTests {

   /** Test method for {@link geometries.Polygon#Polygon(primitives.Point...)}. */
   @Test
   public void testConstructor() {
      // ============ Equivalence Partitions Tests ==============

      // TC01: Correct concave quadrangular with vertices in correct order
      try {
         new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1));
      } catch (IllegalArgumentException e) {
         fail("Failed constructing a correct polygon");
      }

      // TC02: Wrong vertices order
      assertThrows(IllegalArgumentException.class, //
                   () -> new Polygon(new Point(0, 0, 1), new Point(0, 1, 0), new Point(1, 0, 0), new Point(-1, 1, 1)), //
                   "Constructed a polygon with wrong order of vertices");

      // TC03: Not in the same plane
      assertThrows(IllegalArgumentException.class, //
                   () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 2, 2)), //
                   "Constructed a polygon with vertices that are not in the same plane");

      // TC04: Concave quadrangular
      assertThrows(IllegalArgumentException.class, //
                   () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
                                     new Point(0.5, 0.25, 0.5)), //
                   "Constructed a concave polygon");

      // =============== Boundary Values Tests ==================

      // TC10: Vertex on a side of a quadrangular
      assertThrows(IllegalArgumentException.class, //
                   () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
                                     new Point(0, 0.5, 0.5)),
                   "Constructed a polygon with vertix on a side");

      // TC11: Last point = first point
      assertThrows(IllegalArgumentException.class, //
                   () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1)),
                   "Constructed a polygon with vertice on a side");

      // TC12: Co-located points
      assertThrows(IllegalArgumentException.class, //
                   () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 1, 0)),
                   "Constructed a polygon with vertice on a side");

   }

   /** Test method for {@link geometries.Polygon#getNormal(primitives.Point)}. */
   @Test
   public void testGetNormal() {
      // ============ Equivalence Partitions Tests ==============
      // TC01: There is a simple single test here - using a quad
      Point[] pts =
         { new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1) };
      Polygon pol = new Polygon(pts);
      // ensure there are no exceptions
      assertDoesNotThrow(() -> pol.getNormal(new Point(0, 0, 1)), "");
      // generate the test result
      Vector result = pol.getNormal(new Point(0, 0, 1));
      // ensure |result| = 1
      assertEquals(1, result.length(), 0.00000001, "Polygon's normal is not a unit vector");
      // ensure the result is orthogonal to all the edges
      for (int i = 0; i < 3; ++i)
         assertTrue(isZero(result.dotProduct(pts[i].subtract(pts[i == 0 ? 3 : i - 1]))),
                    "Polygon's normal is not orthogonal to one of the edges");
   }
   @Test
   void testfindIntersections() {
      Point[] pts =
              { new Point(0, 0, 0), new Point(1, 0, 0), new Point(0, 1, 0) };
      Polygon tri = new Polygon(pts);
      // ============ Equivalence Partitions Tests ==============
      //TC01 Ray intersects the Polygon
      List<Point> result01=tri.findIntersections(
              new Ray(new Point(0.25,0.25,1),new Vector(0,0,-1)));
      assertEquals(
              1,
              result01.get(0),
              "wrong number of intersection");
      result01=List.of(result01.get(0));
      assertEquals(
              new Point(0.25,0.25,0),
              result01.get(0),
              "the result is not correct");

      //TC02 Ray go against vertex of the Polygon
      assertNull(tri.findIntersections(
                      new Ray(new Point(-0.25,-0.25,1),new Vector(0,0,-1))),
              "wrong number of intersection");

      //TC03 Ray go against side of the Polygon
      assertNull(tri.findIntersections(
                      new Ray(new Point(1,1,1),new Vector(0,0,-1))),
              "wrong number of intersection");


      // =============== Boundary Values Tests ==================
      //TC04 Ray go on vertex of the Polygon
      assertNull(tri.findIntersections(
                      new Ray(new Point(1,0,1),new Vector(0,0,-1))),
              "wrong number of intersection");

      //TC05 Ray go on side of the Polygon
      assertNull(tri.findIntersections(
                      new Ray(new Point(0.5,0,1),new Vector(0,0,-1))),
              "wrong number of intersection");

      //TC06 Ray go on the continue of the side of the Polygon
      assertNull(tri.findIntersections(
                      new Ray(new Point(2,0,1),new Vector(0,0,-1))),
              "wrong number of intersection");


   }
}
