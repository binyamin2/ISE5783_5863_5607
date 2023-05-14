package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.MissingResourceException;

import static primitives.Util.*;

/**
 * class for camera represent a camera with details on view plane
 */
public class Camera {
    private Point location;
    private Vector vright;
    private Vector vup;
    private Vector vto;
    private double height;
    private double width;
    private double distance;
    private ImageWriter iw;
    private RayTracerBase rtb;



    /**
     * ctor
     * @param location
     * @param vup
     * @param vto
     */
    public Camera(Point location, Vector vup, Vector vto) {
        if (!isZero(vup.dotProduct(vto)))
            throw new IllegalArgumentException("the vectors arent vertical ");

        this.location = location;
        this.vup = vup.normalize();
        this.vto = vto.normalize();
        this.vright = vto.crossProduct(vup).normalize();
    }
    /**
     * set the image writer
     * @param iw
     * @return camera
     */
    public Camera setImageWriter(ImageWriter iw) {
        this.iw = iw;
        return this;
    }

    /**
     * set the ray tracer
     * @param rtb
     * @return camera
     */
    public Camera setRayTracer(RayTracerBase rtb) {
        this.rtb = rtb;
        return this;
    }

    /**
     * set the size of view plane with builder pattern
     * @param width
     * @param height
     * @return camera
     */
    public Camera setVPSize(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }

    /**
     * set vp distance from camera
     * @param distance
     * @return camera
     */
    public Camera setVPDistance(double distance) {
        this.distance = distance;
        return this;
    }

    /**
     * return the ray that go through the pixel
     * @param nX number of columns
     * @param nY number of rows
     * @param j number of the specific column pixel
     * @param i number of the specific row pixel
     * @return ray
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        //calculate the center of the excepted pixel with the given formula
        Point pc=this.location.add(vto.scale(distance));
        if (nY==0 && nX==0)
            throw new IllegalArgumentException("divided by zero");
        double ry=height/nY;
        double rx=width/nX;
        double xj=(j-(nX-1.0)/2.0)*rx;
        double yi=-(i-(nY-1.0)/2.0)*ry;
        Point pij=pc;
        if (xj!=0)
            pij=pij.add(vright.scale(xj));
        if (yi!=0)
            pij=pij.add(vup.scale(yi));
        return new Ray(location,pij.subtract(location));
    }

    /**
     * for each pixel in the view plane cast ray from ray to color
     */
    public void renderImage(){
        if (location == null) {
            throw new MissingResourceException("location is missing", "Point", "location");
        }
        if (vright == null) {
            throw new MissingResourceException("vright is missing", "Vector", "vright");
        }
        if (vup == null) {
            throw new MissingResourceException("vup is missing", "Vector", "vup");
        }
        if (vto == null) {
            throw new MissingResourceException("vto is missing", "Vector", "vto");
        }
        if (Double.isNaN(height)) {
            throw new MissingResourceException("height is missing or NaN", "double", "height");
        }
        if (Double.isNaN(width)) {
            throw new MissingResourceException("width is missing or NaN", "double", "width");
        }
        if (Double.isNaN(distance)) {
            throw new MissingResourceException("distance is missing or NaN", "double", "distance");
        }
        if (iw == null) {
            throw new MissingResourceException("iw is missing", "ImageWriter", "iw");
        }
        if (rtb == null) {
            throw new MissingResourceException("rtb is missing", "RayTracerBase", "rtb");
        }
        for (int x = 0 ; x < iw.getNx(); x++ ){
            for (int y = 0 ; y < iw.getNy(); y++ ){
                this.iw.writePixel(x,y,this.castRay(iw.getNx(),iw.getNy(),x,y));
            }
        }
    }

    /**
     * cast the ray to color
     *@param nx number of columns
     *@param ny number of rows
     *@param x number of the specific column pixel
     *@param y number of the specific row pixel
     * @return
     */
    public Color castRay(int nx, int ny, int x, int y) {
        return rtb.traceRay(this.constructRay(nx,ny,x,y));
    }

    /**
     * print grid on the picture
     * @param interval
     * @param color
     */
    public void printGrid (int interval , Color color){
        if (this.iw==null){
            throw new MissingResourceException("iw is missing", "ImageWriter", "iw");
        }
        for (int x = 0 ; x < iw.getNx(); x++ ){
            for (int y = 0 ; y < iw.getNy(); y++ ){

                if (x % interval == 0 || y % interval ==0)
                    iw.writePixel(x,y,color);

            }
        }
    }

    /**
     * write the data to image
     */
    public void writeToImage(){
        if (this.iw==null){
            throw new MissingResourceException("iw is missing", "ImageWriter", "iw");
        }
        iw.writeToImage();
    }

    public Point getLocation() {
        return location;
    }

    public Vector getVright() {
        return vright;
    }

    public Vector getVup() {
        return vup;
    }

    public Vector getVto() {
        return vto;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public double getDistance() {
        return distance;
    }
}
