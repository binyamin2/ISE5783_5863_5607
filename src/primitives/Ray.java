package primitives;

/**
 * class to represent a ray
 */
public class Ray {
    final Point p0;
    final Vector v0;

    /**
     * constructor
     * @param p0 Point
     * @param v0 Vector
     */
    public Ray(Point p0, Vector v0) {
        this.p0 = p0;
        this.v0 = v0.normalize();
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
}
