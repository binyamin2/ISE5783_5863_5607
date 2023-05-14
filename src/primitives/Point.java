package primitives;

/**
 * class to represent a point
 */
public class Point {
    //zero point
    public static final Point ZERO =new Point(0,0,0) ;
    final Double3  xyz;

    /**
     * constructor
     *
     * @param x coordinate x
     * @param y coordinate y
     * @param z coordinate z
     */
    public Point(double x, double y, double z) {
        this.xyz = new Double3(x, y, z);
    }

    /**
     * constructor
     *
     * @param xyz Double3
     */
    public Point(Double3 xyz) {
        this.xyz = xyz;
    }

    /**
     * sub tow point and return new vector
     *
     * @param p Point
     * @return the new vector
     */
    public Vector subtract(Point p) {
        return new Vector(this.xyz.subtract(p.xyz));
    }

    /**
     * add vector to point
     *
     * @param v vector
     * @return the new point
     */
    public Point add(Vector v) {
        return new Point(this.xyz.add(v.xyz));
    }

    /**
     * calculate distance square between 2 points
     *
     * @param p point
     * @return the distance square
     */
    public Double distanceSquared(Point p) {
        return (this.xyz.d1 - p.xyz.d1) * (this.xyz.d1 - p.xyz.d1) +
                (this.xyz.d2 - p.xyz.d2) * (this.xyz.d2 - p.xyz.d2) +
                (this.xyz.d3 - p.xyz.d3) * (this.xyz.d3 - p.xyz.d3);
    }

    /**
     * calculate distance between 2 points
     *
     * @param p point
     * @return the distance
     */
    public Double distance(Point p) {
        return Math.sqrt(this.distanceSquared(p));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        return xyz.equals(point.xyz);
    }

    @Override
    public int hashCode() {
        return xyz.hashCode();
    }

    @Override
    public String toString() {
        return "Point{" +
                "xyz=" + xyz +
                '}';
    }

    public double getX() {
        return xyz.d1;
    }
    public double getY() {
        return xyz.d2;
    }
    public double getZ() {
        return xyz.d3;
    }


}

