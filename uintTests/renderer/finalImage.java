package renderer;

import geometries.*;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;

public class finalImage {

    private Scene scene = new Scene("Test scene");
    Material material =new Material();
    public Camera camera = new Camera(new Point(1000, 0, 500), new Vector(-1, 0, 2),
            new Vector(-1, 0, -0.5)) //
            .setVPSize(170, 170).setVPDistance(1000);
    public Geometry lightSphere1=new Sphere(5,new Point(3, -4, 40))
            .setMaterial(new Material().setKt(1).setKd(0.7).setKs(0.3));
    public Geometry lightSphere2=new Sphere(5,new Point(19, 12, 82))
            .setMaterial(new Material().setKt(1).setKd(0.7).setKs(0.3));
    public Geometry lightSphere3=new Sphere(5,new Point(34,32,78))
            .setMaterial(new Material().setKt(1).setKd(0.7).setKs(0.3));
    public Geometry lightSphere4=new Sphere(5,new Point(42,43,61))
            .setMaterial(new Material().setKt(1).setKd(0.7).setKs(0.3));
    public Geometry lightSphere5=new Sphere(5,new Point(40,43,38))
            .setMaterial(new Material().setKt(1).setKd(0.7).setKs(0.3));
    public Geometry lightSphere6=new Sphere(5,new Point(33,35,24))
            .setMaterial(new Material().setKt(1).setKd(0.7).setKs(0.3));
    public Geometry lightSphere7=new Sphere(5,new Point(20,19,19))
            .setMaterial(new Material().setKt(1).setKd(0.7).setKs(0.3));
    public Geometry lightSphere8=new Sphere(5,new Point(10,6,24))
            .setMaterial(new Material().setKt(1).setKd(0.7).setKs(0.3));
    public Geometry lightSphere9=new Sphere(5,new Point(5, -4, 65))
            .setMaterial(new Material().setKt(1).setKd(0.7).setKs(0.3));
    public Geometry bigSphere=new Sphere(20,new Point(17.1315, 18.19544, 50))
            .setEmission(new Color(212,100,55)) //
            .setMaterial(new Material().setKd(0.2).setKs(1).setShininess(200).setKr(0.8));
    public Geometry floor=new Polygon(new Point(0,-90,0),new Point(0,90,0),
            new Point(150,90,0),new Point(150,-90,0))
            .setMaterial(new Material().setKd(0.25).setKs(1).setShininess(200).setKr(1));

    public Geometry wall=new Polygon(new Point(-50, -80, 80),new Point(-50, 80, 80),
            new Point(-30, 80, 0),new Point(-30, -80, 0))
            .setMaterial(new Material().setKd(0.25).setKs(1).setShininess(200).setKr(1));
    public Geometry tri1=new Triangle(new Point(70,-45,0),new Point(25,-25,0),
            new Point(50,-45,55)).
            setEmission(new Color(71,20,105)).
            setMaterial(new Material().setKs(0.8).setKt(0.8).setKd(0.2).setShininess(20));
    public Geometry tri2=new Triangle(new Point(70,-45,0),new Point(50,-45,55),
            new Point(25, -65, 0)).
            setEmission(new Color(71,20,105)).
            setMaterial(new Material().setKs(0.8).setKt(0.8).setKd(0.2).setDiffuseness(10).setShininess(20));
    public Geometry tri3=new Triangle(new Point(25, -25, 0),new Point(50,-45,55),
            new Point(25, -65, 0)).
            setEmission(new Color(71,20,105)).
            setMaterial(new Material().setKs(0.8).setKt(0.8).setKd(0.2).setShininess(20));
    public Geometry cyl=new Cylinder(5,new Ray(new Point(42, -45, 0),new Vector(0,0,1)),30).
            setEmission(new Color(212,100,55)).
            setMaterial(new Material().setKs(0.3).setKd(0.7).setShininess(20));
  /*  public Geometry tube=new Tube(5,new Ray(new Point(33,47,54),new Vector(-117,-153,104))).
            setEmission(new Color(239,33,74)).
            setMaterial(new Material().setKd(0.7).setKs(0.3).setShininess(20));*/
    @Test
    public void finalImage(){
        scene.geometries.add(floor,bigSphere,tri1,tri2,tri3,cyl,lightSphere1,lightSphere2,lightSphere3,
                lightSphere4,lightSphere5,lightSphere6,lightSphere7,lightSphere8, lightSphere9);
        scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(40, 40, 115), new Vector(-1, -1, -4)) //
                .setKl(0.003).setKq(2E-5));
        scene.lights.add(
                new PointLight(new Color(56, 230, 50),new Point(-10,70,-11))
                        .setKl(0.0003).setKq(2E-5));
        scene.lights.add(
                new PointLight(new Color(56, 230, 50),new Point(15,30,20))
                        .setKl(0.00003).setKq(2E-5));
//        scene.lights.add(new SpotLight(new Color(33, 239, 47),new Point(450,300,40),
//                new Vector(-114,-53,18)).setKl(0.004).setKq(0.00006));

        camera.setImageWriter(new ImageWriter("finalImage", 500, 500)) //
                .setRayTracer(new RayTracerBasic(scene))
                .renderImage() //
                .writeToImage();

    }


}
