package renderer;


import geometries.*;
import lighting.AmbientLight;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.awt.Color.WHITE;

class SuperSampling {
     private Scene scene = new Scene("Test scene");
     @Test
     void glossyReflectionSuperSampling() {
         Camera camera = new Camera(new Point(0, 0, 10000), new Vector(0, 1, 0), new Vector(0, 0, -1)) //
                 .setVPSize(2500, 2500).setVPDistance(10000); //

         scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), new Double3(0.1)));

         scene.geometries.add( //
                 new Polygon(new Point(1000, -700, -4000), new Point(1000, 1000, -4000), new Point(-700, 1000, -4000), new Point(-700, -700, -4000))
                         .setMaterial(new Material().setKd(0.25).setKs(1).setShininess(200).setKr(1).setGlossiness(15)),
                 new Triangle(new Point(-930,-630,-500), new Point(500,-500,250), new Point(320,280,-1000)).setEmission(new Color(0, 0, 100)) //
                         .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
                 new Triangle(new Point(-930,-630,-500), new Point(500,-500,250), new Point(600,-1300,-1000)).setEmission(new Color(0, 0, 100)) //
                         .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
                 new Triangle(new Point(-930,-630,-500), new Point(600,-1300,-1000), new Point(320,280,-1000)).setEmission(new Color(0, 0, 100)) //
                         .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
                 new Triangle(new Point(500,-500,250), new Point(600,-1300,-1000), new Point(320,280,-1000)).setEmission(new Color(0, 0, 100)) //
                         .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)));
         //new Sphere(new Point(200, 200, -1000), 400d).setEmission(new Color(0, 0, 100)) //
         //		.setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)));


         scene.lights.add(new PointLight(new Color(255, 255, 255), new Point(0, 1000, 300)) //
                 .setKl(0.00000001).setKq(0.000000000005));

         ImageWriter imageWriter = new ImageWriter("BirdGlossyReflectionSuperSampling", 500, 500);
         camera.setImageWriter(imageWriter).setBlackboard(81)//
                 .setRayTracer(new RayTracerBasic(scene).setGlossyRaysAmount(15)) //
                 .renderImage() //
                 .writeToImage();
     }
     @Test
     void blurryRefractionSuperSampling(){
         Camera camera = new Camera(new Point(0, 0, 10000), new Vector(0, 1, 0), new Vector(0, 0, -1)) //
                 .setVPSize(2500, 2500).setVPDistance(10000); //

         scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), new Double3(0.1)));

         scene.geometries.add( //
                 new Polygon(new Point(800, -400, 0), new Point(800, 800, 0), new Point(-400, 800, 0), new Point(-400, -400, 0))
                         .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20).setKt(0.3).setDiffuseness(20)),
                 new Triangle(new Point(-930,-630,-1500), new Point(500,-500,-750), new Point(320,280,-2000)).setEmission(new Color(0, 0, 100)) //
                         .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
                 new Triangle(new Point(-930,-630,-1500), new Point(500,-500,-750), new Point(600,-1300,-2000)).setEmission(new Color(0, 0, 100)) //
                         .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
                 new Triangle(new Point(-930,-630,-1500), new Point(600,-1300,-2000), new Point(320,280,-2000)).setEmission(new Color(0, 0, 100)) //
                         .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
                 new Triangle(new Point(500,-500,-750), new Point(600,-1300,-2000), new Point(320,280,-2000)).setEmission(new Color(0, 0, 100)) //
                         .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)));
         //new Sphere(new Point(200, 200, -1000), 400d).setEmission(new Color(0, 0, 100)) //
         //		.setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)));


         scene.lights.add(new PointLight(new Color(255, 255, 255), new Point(0, 0, 300)) //
                 .setKl(0.00000001).setKq(0.000000000005));

         ImageWriter imageWriter = new ImageWriter("BirdBlurryRefractionSuperSampling", 500, 500);
         camera.setImageWriter(imageWriter).setBlackboard(15)
                 //
                 .setRayTracer(new RayTracerBasic(scene).setDiffusiveRaysAmount(15)) //
                 .renderImage() //
                 .writeToImage();
     }
    @Test
    public void trianglesSphere3() {
        Camera        camera     = new Camera(new Point(0, 0, 1000), new Vector(0, 1, 0), new Vector(0, 0, -1))   //
                .setVPSize(200, 200).setVPDistance(1000)                                                                       //
                .setRayTracer(new RayTracerBasic(scene).setGlossyRaysAmount(10).setDiffusiveRaysAmount(10)).setThreads().setAdaptive();
        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

         Geometry tri1=new Triangle(new Point(70,-40,0),new Point(40,-40,0),
                new Point(55,-55,45)).
                setEmission(new Color(71,20,105)).
                setMaterial(new Material().setKs(0.8).setKt(0.8).setKd(0.2).setShininess(20));
         Geometry tri2=new Triangle(new Point(70,-40,0),new Point(70,-70,0),
                new Point(55,-55,45)).
                setEmission(new Color(71,20,105)).
                setMaterial(new Material().setKs(0.8).setKt(0.8).setKd(0.2).setDiffuseness(10).setShininess(20));
         Geometry tri3=new Triangle(new Point(70,-70,0),new Point(40,-70,0),
                new Point(55,-55,45)).
                setEmission(new Color(71,20,105)).
                setMaterial(new Material().setKs(0.8).setKt(0.8).setKd(0.2).setShininess(20));
         Geometry tri4=new Triangle(new Point(40,-50,0),new Point(40,-20,0),
                new Point(55,-35,45)).
                setEmission(new Color(71,20,105)).
                setMaterial(new Material().setKs(0.8).setKt(0.8).setKd(0.2).setShininess(20));

        scene.geometries.add( //
//                new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135),
//                        new Point(75, 75, -150)) //
//                        .setMaterial(new Material().setKd(0.25).setKs(1).setShininess(200).setKr(1).setGlossiness(5)),
//                new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150)) //
//                        .setMaterial(new Material().setKd(0.25).setKs(1).setShininess(200).setKr(1).setGlossiness(5)),
                new Plane(new Point(0,-30,-11),new Vector(0, 1, 0))
                        .setMaterial(new Material().setKd(0.25).setKs(1).setShininess(200).setKr(1).setGlossiness(5)),
                new Plane(new Point(0,-30,-110),new Vector(0, 0, 1))
                        .setMaterial(new Material().setKd(0.25).setKs(1).setShininess(200).setKr(1).setGlossiness(50)),
                new Cylinder(10,new Ray(new Point(40,10,-11),new Vector(1,1,0)),50)
                        .setEmission(new Color(13,140,179)) //
                        .setMaterial(new Material().setKd(0.9).setKs(0.3).setShininess(20).setKr(0.8)),
                new Sphere(30d,new Point(-10, 0, -11)) //
                        .setEmission(new Color(212,100,55)) //
                        .setMaterial(new Material().setKd(0.2).setKs(1).setShininess(200).setKr(0.8)),
                new Sphere(10d,new Point(-50, 0, -11)) //
                        .setEmission(new Color(212,10,55)) //
                        .setMaterial(new Material().setKd(0.2).setKs(1).setShininess(200).setKr(0.8)),
                new Sphere(7d,new Point(20, 0, -11)) //
                        .setEmission(new Color(21,100,55)) //
                        .setMaterial(new Material().setKd(0.2).setKs(1).setShininess(200).setKr(0.8)),
                new Sphere(15d,new Point(-10, 34, -11)) //
                        .setEmission(new Color(212,100,10)) //
                        .setMaterial(new Material().setKd(0.2).setKs(1).setShininess(200).setKr(0.8))
        );
        scene.lights.add( //
                new SpotLight(new Color(700, 400, 400), new Point(40, 40, 115), new Vector(-1, -1, -4)) //
                        .setKl(0.003).setKq(2E-5));
                scene.lights.add(
                new PointLight(new Color(56, 230, 50),new Point(-10,70,-11))
                        .setKl(0.003).setKq(2E-5));

        camera.setImageWriter(new ImageWriter("stage_8", 600, 600))
                .setBlackboard(9) //
                .renderImage() //
                .writeToImage();
    }


 }
