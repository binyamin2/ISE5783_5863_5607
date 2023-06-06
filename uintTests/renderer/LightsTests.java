package renderer;

import static java.awt.Color.*;

import org.junit.jupiter.api.Test;

import geometries.*;
import lighting.*;
import primitives.*;
import renderer.*;
import scene.Scene;

/** Test rendering a basic image
 * @author Dan */
public class LightsTests {
   private final Scene          scene1                  = new Scene("Test scene");
   private final Scene          scene2                  = new Scene("Test scene")
      .setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15)));

   private final Scene          scene3                  = new Scene("Test scene")
           .setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15)));


   private final Camera         camera1                 = new Camera(new Point(0, 0, 1000), new Vector(0, 1, 0),
                                                                     new Vector(0, 0, -1))
      .setVPSize(150, 150).setVPDistance(1000);
   private final Camera         camera2                 = new Camera(new Point(0, 0, 1000), new Vector(0, 1, 0),
                                                                     new Vector(0, 0, -1))
      .setVPSize(200, 200).setVPDistance(1000);

   private final Camera         camera3                 = new Camera(
           new Point(0, 0, -500),    // Camera position
           new Vector(0, 1, 0),     // Up vector
           new Vector(0, 0, 1)      // Direction vector
   )  .setVPSize(200, 200).setVPDistance(1000);

   private static final int     SHININESS               = 301;
   private static final double  KD                      = 0.5;
   private static final Double3 KD3                     = new Double3(0.2, 0.6, 0.4);

   private static final double  KS                      = 0.5;
   private static final Double3 KS3                     = new Double3(0.2, 0.4, 0.3);

   private final Material       material                = new Material().setKd(KD3).setKs(KS3).setShininess(SHININESS);
   private final Color          trianglesLightColor     = new Color(800, 500, 250);
   private final Color          sphereLightColor        = new Color(800, 500, 0);
   private final Color          sphereColor             = new Color(BLUE).reduce(2);

   private final Point          sphereCenter            = new Point(0, 0, -50);
   private static final double  SPHERE_RADIUS           = 50d;

   // The triangles' vertices:
   private final Point[]        vertices                =
      {
        // the shared left-bottom:
        new Point(-110, -110, -150),
        // the shared right-top:
        new Point(95, 100, -150),
        // the right-bottom
        new Point(110, -110, -150),
        // the left-top
        new Point(-75, 78, 100)
      };
   private final Point          sphereLightPosition     = new Point(-50, -50, 25);
   private final Point          trianglesLightPosition  = new Point(30, 10, -100);
   private final Vector         trianglesLightDirection = new Vector(-2, -2, -2);

   private final Geometry       sphere                  = new Sphere( SPHERE_RADIUS,sphereCenter)
      .setEmission(sphereColor).setMaterial(new Material().setKd(KD).setKs(KS).setShininess(SHININESS));
   private final Geometry       triangle1               = new Polygon(vertices[0], vertices[1], vertices[2])
      .setMaterial(material);
   private final Geometry       triangle2               = new Triangle(vertices[0], vertices[1], vertices[3])
      .setMaterial(material);

   private final Geometry       tube              = new Cylinder(10, new Ray(new Point(0,0,0),
           new Vector(1,0,0)),70)
           .setMaterial(material);

   private final Geometry       tube1              = new Cylinder(10, new Ray(new Point(0,0,0),
           new Vector(0,1,0)),70)
           .setMaterial(material);

   private final Geometry       tube2              = new Cylinder(10, new Ray(new Point(-10,0,0),
           new Vector(0,0,1)),70)
           .setMaterial(material);

   private Geometry triangle11 = new Triangle(
           new Point(-10, 10, 0),
           new Point(0, 10, 0),
           new Point(-5, 20, 0)
   ).setEmission(new Color(156, 200, 100)).setMaterial(material.setKt(1));

   private Geometry triangle22 = new Triangle(
           new Point(10, 10, 0),
           new Point(20, 10, 0),
           new Point(15, 20, 0)
   ).setEmission(new Color(255, 0, 0)).setMaterial(material.setKt(0.5).setKr(0.3));

   private Geometry polygon = new Polygon(
           new Point(0, 0, 0),
           new Point(5, 0, 0),
           new Point(7.5, 4.33, 0),
           new Point(5, 8.66, 0),
           new Point(0, 8.66, 0),
           new Point(-2.5, 4.33, 0)
   ).setEmission(new Color(0, 0, 255)).setMaterial(material.setKt(0.5).setKr(0.7));

   private Geometry sphere1 = new Sphere(
           7.5,
           new Point(-10, -10, 0)
   ).setEmission(new Color(255, 255, 0)).setMaterial(material.setKt(0.5).setKr(0.7));

   private Geometry sphere2 = new Sphere(
           5,
           new Point(10, -10, 0)
   ).setEmission(new Color(255, 255, 255)).setMaterial(material.setKt(0.5).setKr(0.7));

   private Geometry tube11 = new Tube(
           2,
           new Ray(new Point(-15, 0, 0), new Vector(0, 1, 0))
   ).setEmission(new Color(0, 255, 0)).setMaterial(material.setKt(0.5).setKr(0.7));

   private Geometry tube22 = new Tube(
           1,
           new Ray(new Point(5, -15, 0), new Vector(0, 1, 0))
   ).setEmission(new Color(128, 128, 128)).setMaterial(material.setKt(0.5).setKr(0.7));

   private Geometry tube3 = new Tube(
           1.5,
           new Ray(new Point(-15, -15, 0), new Vector(0, 1, 0))
   ).setEmission(new Color(255, 128, 0)).setMaterial(material.setKt(0.5).setKr(0.7));

   private Geometry tube4 = new Tube(
           1.5,
           new Ray(new Point(15, -15, 0), new Vector(0, 1, 0))
   ).setEmission(new Color(0, 255, 255)).setMaterial(material.setKt(0.5).setKr(0.7));

   private Geometry tube5 = new Tube(
           2,
           new Ray(new Point(-5, -20, 0), new Vector(0, 1, 0))
   ).setEmission(new Color(255, 0, 255)).setMaterial(material.setKt(0.5).setKr(0.7));


   /** Produce a picture of a sphere lighted by a directional light */
   @Test
   public void sphereDirectional() {
      scene1.geometries.add(sphere);
      scene1.lights.add(new DirectionalLight(sphereLightColor, new Vector(1, 1, -0.5)));

      ImageWriter imageWriter = new ImageWriter("lightSphereDirectional", 500, 500);
      camera1.setImageWriter(imageWriter) //
         .setRayTracer(new RayTracerBasic(scene1)) //
         .renderImage() //
          .writeToImage(); //
   }

   /** Produce a picture of a sphere lighted by a point light */
   @Test
   public void spherePoint() {
      scene1.geometries.add(sphere);
      scene1.lights.add(new PointLight(sphereLightColor, sphereLightPosition)
         .setKl(0.001).setKq(0.0002));

      ImageWriter imageWriter = new ImageWriter("lightSpherePoint", 500, 500);
      camera1.setImageWriter(imageWriter) //
         .setRayTracer(new RayTracerBasic(scene1)) //
         .renderImage() //
         .writeToImage(); //
   }

   /** Produce a picture of a sphere lighted by a spotlight */
   @Test
   public void sphereSpot() {
      scene1.geometries.add(sphere);
      scene1.lights.add(new SpotLight(sphereLightColor, sphereLightPosition, new Vector(1, 1, -0.5))
         .setKl(0.001).setKq(0.0001));

      ImageWriter imageWriter = new ImageWriter("lightSphereSpot", 500, 500);
      camera1.setImageWriter(imageWriter) //
         .setRayTracer(new RayTracerBasic(scene1)) //
         .renderImage() //
         .writeToImage(); //
   }

   /** Produce a picture of two triangles lighted by a directional light */
   @Test
   public void trianglesDirectional() {
      scene2.geometries.add(triangle1, triangle2);
      scene2.lights.add(new DirectionalLight(trianglesLightColor, trianglesLightDirection));

      ImageWriter imageWriter = new ImageWriter("lightTrianglesDirectional", 500, 500);
      camera2.setImageWriter(imageWriter) //
         .setRayTracer(new RayTracerBasic(scene2)) //
         .renderImage() //
         .writeToImage(); //
   }

   /** Produce a picture of two triangles lighted by a point light */
   @Test
   public void trianglesPoint() {
      scene2.geometries.add(triangle1, triangle2);
      scene2.lights.add(new PointLight(trianglesLightColor, trianglesLightPosition)
         .setKl(0.001).setKq(0.0002));

      ImageWriter imageWriter = new ImageWriter("lightTrianglesPoint", 500, 500);
      camera2.setImageWriter(imageWriter) //
         .setRayTracer(new RayTracerBasic(scene2)) //
         .renderImage() //
         .writeToImage(); //
   }

   /** Produce a picture of two triangles lighted by a spotlight */
   @Test
   public void trianglesSpot() {
      scene2.geometries.add(triangle1, triangle2);
      scene2.lights.add(new SpotLight(trianglesLightColor, trianglesLightPosition, trianglesLightDirection)
         .setKl(0.001).setKq(0.0001));

      ImageWriter imageWriter = new ImageWriter("lightTrianglesSpot", 500, 500);
      camera2.setImageWriter(imageWriter) //
         .setRayTracer(new RayTracerBasic(scene2)) //
         .renderImage() //
         .writeToImage(); //
   }

   /** Produce a picture of a sphere lighted by a narrow spotlight */
   @Test
   public void sphereSpotSharp() {
      scene1.geometries.add(sphere);
      scene1.lights
         .add(new SpotLight(sphereLightColor, sphereLightPosition, new Vector(1, 1, -0.5))
            .setNarrowBeam(10).setKl(0.001).setKq(0.00004));

      ImageWriter imageWriter = new ImageWriter("lightSphereSpotSharp", 500, 500);
      camera1.setImageWriter(imageWriter) //
         .setRayTracer(new RayTracerBasic(scene1)) //
         .renderImage() //
         .writeToImage(); //
   }

   /** Produce a picture of two triangles lighted by a narrow spotlight */
   @Test
   public void trianglesSpotSharp() {
      scene2.geometries.add(triangle1, triangle2);
      scene2.lights.add(new SpotLight(trianglesLightColor, trianglesLightPosition, trianglesLightDirection)
         .setNarrowBeam(10).setKl(0.001).setKq(0.00004));

      ImageWriter imageWriter = new ImageWriter("lightTrianglesSpotSharp", 500, 500);
      camera2.setImageWriter(imageWriter) //
         .setRayTracer(new RayTracerBasic(scene2)) //
         .renderImage() //
         .writeToImage(); //
   }

   /** Produce a picture of two triangles lighted by a multiple light sources */
   @Test
   public void testtriangleMultipleLights() {
      scene2.geometries.add(triangle1, triangle2);
      scene2.lights.add(new SpotLight(sphereLightColor, trianglesLightPosition, trianglesLightDirection)
              .setKl(0.001).setKq(0.0001));
      scene2.lights
              .add(new PointLight(trianglesLightColor, new Point(0,25,25))
                      .setKl(0.002).setKq(0.00004));
      scene2.lights
              .add(new DirectionalLight(new Color(800,0,400), new Vector(-1,-1,-1)));

      ImageWriter imageWriter = new ImageWriter("lightTrianglesmultiple", 500, 500);
      camera2.setImageWriter(imageWriter) //
              .setRayTracer(new RayTracerBasic(scene2)) //
              .renderImage() //
              .writeToImage(); //
   }

   /** Produce a picture of a sphere lighted by a multiple light sources */
   @Test
   public void testtubeMultipleLights() {
      scene3.geometries.add(tube);
      scene3.geometries.add(tube1);
      scene3.geometries.add(tube2);
      scene3.lights
              .add(new SpotLight(sphereLightColor, sphereLightPosition, new Vector(1, 1, -0.5))
                      .setKl(0.001).setKq(0.00004));
      scene3.lights
              .add(new PointLight(trianglesLightColor, new Point(75,-25,25))
                      .setKl(0.002).setKq(0.0004));
      scene3.lights
              .add(new DirectionalLight(new Color(800,0,400), new Vector(0,-1,0)));


      ImageWriter imageWriter = new ImageWriter("lighttubemultipleLigts", 500, 500);
      camera3.setImageWriter(imageWriter) //
              .setRayTracer(new RayTracerBasic(scene3)) //
              .renderImage() //
              .writeToImage(); //
   }
   @Test
   public void testsphereSpotMultipleLights() {
      scene1.geometries.add(sphere);
      scene1.lights
              .add(new SpotLight(sphereLightColor, sphereLightPosition, new Vector(1, 1, -0.5))
                      .setKl(0.001).setKq(0.00004));
      scene1.lights
              .add(new PointLight(trianglesLightColor, new Point(75,-25,25))
                      .setKl(0.002).setKq(0.0004));
      scene1.lights
              .add(new DirectionalLight(new Color(800,0,400), new Vector(0,-1,0)));


      ImageWriter imageWriter = new ImageWriter("lightSpheremultipleLigts", 500, 500);
      camera1.setImageWriter(imageWriter) //
              .setRayTracer(new RayTracerBasic(scene1)) //
              .renderImage() //
              .writeToImage(); //
   }
   @Test
   public void testMultiplegieometries() {

      scene3.geometries.add(triangle11);
      scene3.geometries.add(triangle22);
      scene3.geometries.add(tube11);
      scene3.geometries.add(tube22);
      scene3.geometries.add(tube3);
      scene3.geometries.add(tube4);
      scene3.geometries.add(tube5);
      scene3.geometries.add(sphere1);
      scene3.geometries.add(sphere2);
      scene3.geometries.add(polygon);
      scene3.lights
              .add(new SpotLight(sphereLightColor, sphereLightPosition, new Vector(1, 1, -0.5))
                      .setKl(0.001).setKq(0.00004));
      scene3.lights
              .add(new PointLight(trianglesLightColor, new Point(0,10,0))
                      .setKl(0.002).setKq(0.0004));
      scene3.lights
              .add(new DirectionalLight(new Color(800,0,400), new Vector(0,-1,0)));


      ImageWriter imageWriter = new ImageWriter("multipleGeometries", 500, 500);
      camera3.setImageWriter(imageWriter) //
              .setRayTracer(new RayTracerBasic(scene3)) //
              .renderImage() //
              .writeToImage(); //
   }
}
