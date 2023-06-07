package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * class board of points in pixel for the ray beam
 */
public class Blackboard {
    List<Point> jitteredTamplate;

    double nX;

    double ny;

    double width;
    double height;
    int numberOfPoints;

    /**
     * ctor
     * @param nX
     * @param ny
     * @param width
     * @param height
     */
    public Blackboard(double nX, double ny, double width, double height) {
        this.nX = nX;
        this.ny = ny;
        this.width = width;
        this.height = height;
        this.numberOfPoints = 1;


    }

    /**
     * set the number of points in every pixel and build the tamplate of jitterd
     * @param numberOfPoints
     * @return
     */
    public Blackboard setNumberOfPoints(int numberOfPoints) {
        this.numberOfPoints = numberOfPoints;
        this.jitteredTamplate = new ArrayList<>();

        double pixelWidth = width / (nX);
        double pixelHeight = height / (ny);

        double numSubPixel = Math.sqrt(numberOfPoints);
        double subWidth = pixelWidth / numSubPixel;
        double subHeight = pixelHeight / numSubPixel;

        Random random = new Random();
// for each sub pixel rondom point inside and build a list
        for (int i = 0; i < numSubPixel; i++) {
            for (int j = 0; j < numSubPixel; j++) {
                double randomX = i * subWidth + random.nextDouble(0, subWidth);
                double randomY = -j * subHeight - random.nextDouble(0, subHeight);
                double randomZ = 0;

                this.jitteredTamplate.add(new Point(randomX, randomY, randomZ));
            }
        }
        return this;
    }

    /**
     * construct ray beam for pixel
     * @param x
     * @param y
     * @param z
     * @param CameraPosition
     * @return
     */
    public List<Ray> constructRayBeam(int x, int y, double z, Point CameraPosition) {
        List<Ray> rayBeam = new ArrayList<>();
        double pixelWidth = width / (nX);
        double pixelHeight = height / (ny);
        //the up left edge of pixel
        Point start = new Point(
                -nX / 2 * pixelWidth + pixelWidth * x,
                ny / 2 * pixelHeight - pixelHeight * y,
                z
        );
        //for each point in the tamplate add the start point
        for (var point : jitteredTamplate) {
            //  Point t = start.add(new Vector(point));
            Point t = new Point(
                    start.getX() + point.getX(),
                    start.getY() + point.getY()
                    ,start.getZ()+point.getZ());
            rayBeam.add(new Ray(CameraPosition, t.subtract(CameraPosition).normalize()));
        }
        return rayBeam;
    }
}
