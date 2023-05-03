package IntegrationTest;

import geometries.Intersectable;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import primitives.Point;
import primitives.Vector;
import renderer.Camera;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class renderer_findintersection {
    @Test
    void  testSphere(){
        //TC01 sphere radius 1 (2 points)
        Camera ca=new Camera(new Point(0,0,0),new Vector(0,1,0),new Vector(0,0,-1)).
                setVPSize(3,3).
                setVPDistance(1);
        Sphere sp=new Sphere(1,new Point(0,0,-3));
        assertEquals(2,
                countIntersection(ca,sp,3,3),
                "number of intersection incorrect");
        //TC02 sphere radius 2.5 (18 points)
        Camera ca02=new Camera(new Point(0,0,0.5),new Vector(0,1,0),new Vector(0,0,-1)).
                setVPSize(3,3).
                setVPDistance(1);
        Sphere sp02=new Sphere(2.5,new Point(0,0,-2.5));
        assertEquals(18,
                countIntersection(ca02,sp02,3,3),
                "number of intersection incorrect");
        //TC03 sphere radius 2 (10 points)
        Sphere sp03=new Sphere(2,new Point(0,0,-2));
        assertEquals(10,
                countIntersection(ca02,sp03,3,3),
                "number of intersection incorrect");
        //TC04 view plane inside sphere (9 points)
        Camera ca04=new Camera(new Point(0,0,1),new Vector(0,1,0),new Vector(0,0,-1)).
                setVPSize(3,3).
                setVPDistance(1);
        Sphere sp04=new Sphere(4,new Point(0,0,0));
        assertEquals(9,
                countIntersection(ca04,sp04,3,3),
                "number of intersection incorrect");
        //TC04 sphere out of camera sight (0 points)
        Sphere sp05=new Sphere(0.5,new Point(0,0,3));
        assertEquals(0,
                countIntersection(ca04,sp05,3,3),
                "number of intersection incorrect");
    }

    @Test
    void  testPlane(){
        //TC01 plane parallel to view plane(9 points)
        Camera ca=new Camera(new Point(0,0,0),new Vector(0,1,0),new Vector(0,0,-1)).
                setVPSize(3,3).
                setVPDistance(1);
        Plane pl=new Plane(new Point(0,0,-4),new Vector(0,0,1));
        assertEquals(9,
                countIntersection(ca,pl,3,3),
                "number of intersection incorrect");

        //TC02 plane not parallel but small angle
        Plane pl02=new Plane(new Point(0,0,-4),new Vector(0,-0.2,1));
        assertEquals(9,
                countIntersection(ca,pl02,3,3),
                "number of intersection incorrect");
        //TC03 plane not parallel bau bigger angle(6 points)
        Plane pl03=new Plane(new Point(0,0,-4),new Vector(0,-1,1));
        assertEquals(6,
                countIntersection(ca,pl03,3,3),
                "number of intersection incorrect");

    }

    @Test
    void  testTriangle(){
        //TC01 small triangle parallel to view plane(1 point)
        Camera ca=new Camera(new Point(0,0,0),new Vector(0,1,0),new Vector(0,0,-1)).
                setVPSize(3,3).
                setVPDistance(1);
        Triangle tr=new Triangle(new Point(0,1,-2),new Point(-1,-1,-2),new Point(1,-1,-2));
        assertEquals(1,
                countIntersection(ca,tr,3,3),
                "number of intersection incorrect");

        //TC02 high triangle parallel to view plane(2 points)
        Triangle tr02=new Triangle(new Point(0,20,-2),new Point(-1,-1,-2),new Point(1,-1,-2));
        assertEquals(2,
                countIntersection(ca,tr02,3,3),
                "number of intersection incorrect");

    }
    /**
     * function to check how many intersection overall
     * @param ca
     * @param body
     * @param nX
     * @param nY
     * @return number of intersection
     */
    int countIntersection(Camera ca, Intersectable body,int nX,int nY){
        List<Point> res= new ArrayList<>() ;
        for (int i=0;i<nX;i++){
            for (int j=0;j<nX;j++){
                res.addAll(body.findIntersections(ca.constructRay(nX,nY,j,i)));
            }
        }
        return res.size();

    }
}
