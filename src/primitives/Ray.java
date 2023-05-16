package primitives;

import java.util.List;

/**
 * class to represent a ray
 */
public class Ray {
    final Point p0;
    final Vector v0;

    /**
     * constructor
     *
     * @param p0 Point
     * @param v0 Vector
     */
    public Ray(Point p0, Vector v0) {
        this.p0 = p0;
        this.v0 = v0.normalize();
    }

    public Point getP0() {
        return p0;
    }

    public Vector getV0() {
        return v0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ray ray = (Ray) o;

        if (!p0.equals(ray.p0)) return false;
        return v0.equals(ray.v0);
    }

    @Override
    public int hashCode() {
        int result = p0.hashCode();
        result = 31 * result + v0.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Ray{" +
                "p0=" + p0 +
                ", v0=" + v0 +
                '}';
    }

    public Point getPoint(double t) {
        return this.getP0().add(this.getV0().scale(t));
    }

    /**
     * Return the closest point to the start of the ray
     *
     * @param points
     * @return Point
     */
    public Point findClosetPoint(List<Point> points) {
        //return null if empty list
        if (points == null)
            return null;
        double currdis = 0;
        double minDis = Double.POSITIVE_INFINITY;
        Point minPoint = new Point(0, 0, 0);
        //find the point with the smallest distance from p0
        for (Point p : points) {
            currdis = this.getP0().distanceSquared(p);
            if (currdis < minDis) {
                minPoint = p;
                minDis = currdis;
            }
        }
        return minPoint;
    }

}
