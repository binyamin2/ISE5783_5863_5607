package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.*;

public class Camera {
    private Point location;
    private Vector vright;
    private Vector vup;
    private Vector vto;
    private double height;
    private double width;
    private double distance;

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
     * set the size of view plane with builder pattern
     * @param width
     * @param height
     * @return
     */
    public Camera setVPSize(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }

    /**
     * set vp distance from camera
     * @param distance
     * @return
     */
    public Camera setVPDistance(double distance) {
        this.distance = distance;
        return this;
    }

    /**
     * return the ray that go through the pixel
     * @param nX number of columns
     * @param nY number of rows
     * @param j number of the specific row pixel
     * @param i number of the specific column pixel
     * @return ray
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        return null;
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
