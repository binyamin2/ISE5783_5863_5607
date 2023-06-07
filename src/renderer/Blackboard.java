package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static primitives.Util.random;

/**
 * class board of points in pixel for the ray beam
 */
public class Blackboard {
    List<Point> jitteredTamplate;
    List<Point> glossyTamplate;

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

    /**
     * calculate the ray beam
     * @param ray center ray
     * @param raysAmount how many rays
     * @param distanceFromTargetArea for the opennes of the angle
     * @param targetAreaSize
     * @return
     */
    public static List<Ray> constructMultiSamplingRaysRandom(Ray ray, double raysAmount, double distanceFromTargetArea, double targetAreaSize) {
        ArrayList<Ray> resultList = new ArrayList<Ray>();

        //find the X,Y
        Vector rotatedVector, v1 = ray.getV0().findOrthogonal(),
                v2 = v1.crossProduct(ray.getV0());
        Point point, targetAreaCenter = ray.getPoint(distanceFromTargetArea);//move the point to the target area
        resultList.add(ray);
        if (raysAmount == 0)
            return resultList;
        double randomRadius, randomAngle; //randomise points on circle
        for (int i = 1; i < raysAmount; i++) {
            randomRadius = random(0.01, targetAreaSize);
            randomAngle = random(0, 360);
            rotatedVector = v1.rotate(v2, randomAngle);
            point = targetAreaCenter.add(rotatedVector.scale(randomRadius));
            resultList.add(new Ray(ray.getP0(), point.subtract(ray.getP0())));
        }
        return resultList;
    }
}
