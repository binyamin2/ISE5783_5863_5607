package renderer;


import primitives.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

 class SuperSampling {

     public static List<Point> generateJitteredPoints(int nx, int ny, int x, int y, double viewWidth, double viewHeight, Point center, int numberOfPoints) {
         List<Point> points = new ArrayList<>();

         double pixelWidth = viewWidth / (nx );
         double pixelHeight = viewHeight / (ny);


         double pixelX = center.getX() - (viewWidth / 2) + (x * pixelWidth);
         double pixelY = center.getY() + (viewHeight / 2) - (y * pixelHeight);
         double pixelZ = center.getZ();
         double numSubPixel =Math.sqrt(numberOfPoints);
         double subWidth = pixelWidth/numSubPixel;
         double subHeight = pixelHeight/numSubPixel;

         Random random = new Random();

         for (int i = 0; i < numSubPixel; i++) {
             for (int j = 0; j < numSubPixel; j++){
             double randomX = pixelX + i * subWidth + random.nextDouble(0,subWidth);
             double randomY = pixelY - j * subHeight - random.nextDouble(0,subHeight);
             double randomZ = pixelZ;

             points.add(new Point(randomX, randomY, randomZ));
         }}

         return points;
     }

     public static void main(String[] args) {
         // Example usage
         int nx = 9;
         int ny = 9;
         int x = 6;
         int y = 6;
         double viewWidth = 9.0;
         double viewHeight = 9.0;
         Point center = new Point(0, 0, 0);
         int numberOfPoints = 9;

         List<Point> jitteredPoints = generateJitteredPoints(nx, ny, x, y, viewWidth, viewHeight, center, numberOfPoints);

         for (Point point : jitteredPoints) {
             System.out.println(point);
         }
     }

}
