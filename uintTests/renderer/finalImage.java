package renderer;

import geometries.*;
import lighting.PointLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;

public class finalImage {

    private Scene scene = new Scene("Test scene");
    Material material =new Material();
    public Camera camera = new Camera(new Point(1000, 0, 1000), new Vector(-1, 0, 1),
            new Vector(-1, 0, -1)) //
            .setVPSize(150, 150).setVPDistance(1000);
    public Sphere bigSphere=new Sphere(25,new Point(33,47,54));
    public Geometry wall=new Polygon(new Point(0,-80,0),new Point(0,80,0),
            new Point(0,80,80),new Point(0,-80,80))
            .setEmission(new Color(212,14,45));

    public Geometry floor=new Polygon(new Point(0,80,0),new Point(0,-80,0),
            new Point(80,-80,0),new Point(80,80,0))
            .setEmission(new Color(212,134,45));;
    public Triangle tri1=new Triangle(new Point(70,-40,0),new Point(40,-40,0),
            new Point(55,-55,45));
    public Triangle tri2=new Triangle(new Point(70,-40,0),new Point(70,-70,0),
            new Point(55,-55,45));
    public Triangle tri3=new Triangle(new Point(70,-70,0),new Point(40,-70,0),
            new Point(55,-55,45));
    public Triangle tri4=new Triangle(new Point(40,-70,0),new Point(40,-40,0),
            new Point(55,-55,45));
    public Cylinder cyl=new Cylinder(5,new Ray(new Point(55,-55,0),new Vector(0,1,0)),25);
    public Tube tube=new Tube(5,new Ray(new Point(33,47,54),new Vector(-117,-153,54)));
    @Test
    public void finalImage(){
        scene.geometries.add(wall,floor);
        scene.lights.add(new PointLight(new Color(1000, 600, 0),new Point(0,100,100)).
                setKl(0.0004).setKq(0.0000006));

        camera.setImageWriter(new ImageWriter("finalImage", 500, 500)) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();

    }


}
