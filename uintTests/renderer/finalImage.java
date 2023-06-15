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
            .setVPSize(170, 170).setVPDistance(700);
    public Geometry lightSphere=new Sphere(5,new Point(15,30,20))
            .setMaterial(new Material().setKt(1).setKd(0.7).setKs(0.3).setDiffuseness(20).setShininess(10));
    public Geometry bigSphere=new Sphere(25,new Point(33,47,54))
            .setEmission(new Color(212,100,55)) //
            .setMaterial(new Material().setKd(0.2).setKs(1).setShininess(200).setKr(0.8));
    public Geometry wall=new Polygon(new Point(0,-80,0),new Point(0,80,0),
            new Point(0,80,80),new Point(0,-80,80))
            .setMaterial(new Material().setKd(0.25).setKs(1).setShininess(200).setKr(1).setGlossiness(50));

    public Geometry floor=new Polygon(new Point(0,80,0),new Point(0,-80,0),
            new Point(80,-80,0),new Point(80,80,0))
            .setMaterial(new Material().setKd(0.25).setKs(1).setShininess(200).setKr(1).setGlossiness(50));
    public Geometry tri1=new Triangle(new Point(70,-40,0),new Point(40,-40,0),
            new Point(55,-55,45)).
            setEmission(new Color(71,20,105)).
            setMaterial(new Material().setKs(0.8).setKt(0.8).setKd(0.2).setShininess(20));
    public Geometry tri2=new Triangle(new Point(70,-40,0),new Point(70,-70,0),
            new Point(55,-55,45)).
            setEmission(new Color(71,20,105)).
            setMaterial(new Material().setKs(0.8).setKt(0.8).setKd(0.2).setDiffuseness(10).setShininess(20));
    public Geometry tri3=new Triangle(new Point(70,-70,0),new Point(40,-70,0),
            new Point(55,-55,45)).
            setEmission(new Color(71,20,105)).
            setMaterial(new Material().setKs(0.8).setKt(0.8).setKd(0.2).setShininess(20));
    public Geometry tri4=new Triangle(new Point(40,-70,0),new Point(40,-40,0),
            new Point(55,-55,45)).
            setEmission(new Color(71,20,105)).
            setMaterial(new Material().setKs(0.8).setKt(0.8).setKd(0.2).setShininess(20));
    public Geometry cyl=new Cylinder(5,new Ray(new Point(55,-55,0),new Vector(0,0,1)),25).
            setEmission(new Color(212,100,55)).
            setMaterial(new Material().setKs(0.3).setKd(0.7).setShininess(20));
    public Geometry tube=new Tube(5,new Ray(new Point(33,47,54),new Vector(-117,-153,104))).
            setEmission(new Color(239,33,74)).
            setMaterial(new Material().setKd(0.7).setKs(0.3).setShininess(20));
    @Test
    public void finalImage(){
        scene.geometries.add(wall,floor,bigSphere,tri1,tri2,tri3,tri4,tube,cyl,lightSphere);
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
                .setRayTracer(new RayTracerBasic(scene).setDiffusiveRaysAmount(10).setGlossyRaysAmount(10).setAdaptive()).
                setBlackboard(9)
                .renderImage() //
                .writeToImage();

    }


}
