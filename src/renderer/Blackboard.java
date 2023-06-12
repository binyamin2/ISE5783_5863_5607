package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

import static primitives.Util.isZero;
import static primitives.Util.random;

/**
 * class board of points in pixel for the ray beam
 */
public class Blackboard {



    /**
     * calculate the ray beam
     * @param ray center ray
     * @return
     */
    public static List<Ray> constructRayBeam(Ray ray,int numberOfRays, double distance, double pixelWidth, double pixelHeight ) {
        ArrayList<Ray> resultList = new ArrayList<Ray>();

        //find the X,Y
        Vector rotatedVector, vUp = ray.getV0().findOrthogonal(),
                vRight = vUp.crossProduct(ray.getV0()).normalize();
        Point point, targetAreaCenter = ray.getPoint(distance);//move the point to the target area
        resultList.add(ray);
        if (numberOfRays == 0)
            return resultList;
        double randomWidth, randomHeight; //randomise points on circle
        for (int i = 1; i < numberOfRays; i++) {
            randomWidth = random(-pixelWidth /2, pixelWidth /2);
            randomHeight = random(-pixelHeight/2, pixelHeight/2);
            rotatedVector = vUp.rotate(vRight, randomHeight);
            point = targetAreaCenter;
            if (!isZero(randomWidth))
                point = point.add(vRight.scale(randomWidth));
            if (!isZero(randomHeight))
                point = point.add(vUp.scale(randomHeight));
            resultList.add(new Ray(ray.getP0(), point.subtract(ray.getP0())));
        }
        return resultList;
    }





    //bounos jiterd
    /* *//**
     * set the number of points in every pixel and build the tamplate of jitterd
     * @param numberOfPoints
     * @return
     *//*
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
    }*/

    /**
     * construct ray beam for pixel
     * @param x
     * @param y
     * @param z
     * @param CameraPosition
     * @return
     */
/*    public List<Ray> constructRayBeam(int x, int y, double z, Point CameraPosition) {
        List<Ray> rayBeam = new ArrayList<>();
        double pixelWidth = PixelWidth / (nX);
        double pixelHeight = PixelHeight / (ny);
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
    }*/


}
