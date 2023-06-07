package primitives;

import static primitives.Util.isZero;

/**
 * Vector class represent by 3 doubles
 */
public class Vector extends Point{

    /**
     * constructor
     * @param x coordinate x
     * @param y coordinate y
     * @param z coordinate z
     * @exception if vector is Zero
     */
    public Vector(double x, double y, double z) {
        super(x, y, z);
        if(new Double3(x,y,z).equals(Double3.ZERO))
            throw new IllegalArgumentException("vector cannot be zero");

    }

    /**
     * constructor
     * @param xyz double3
     */
    public Vector(Double3 xyz) {
        super(xyz);
        if(xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("vector cannot be zero");
    }

    public Vector(Point point) {
        super(point.xyz);
    }

    /**
     * vector addition
     * @param v vector
     * @return vector
     */
    public Vector add(Vector v){
        return new Vector(this.xyz.add(v.xyz));
    }

    /**
     * multiple vector in scalar
     * @param s double
     * @return vector
     */
    public Vector scale(double s){
        return new Vector(this.xyz.scale(s));
    }

    /**
     * calc the dot product between vectors
     * @param v vector
     * @return the result
     */
    public Double dotProduct(Vector v){
        return this.xyz.d1*v.xyz.d1+
                this.xyz.d2*v.xyz.d2+
                this.xyz.d3*v.xyz.d3;

    }

    /**
     * calc the cross product between vectors
     * @param v vector
     * @return the new vector
     */
    public Vector crossProduct(Vector v){
        return new Vector(this.xyz.d2*v.xyz.d3-this.xyz.d3*v.xyz.d2,
                this.xyz.d3*v.xyz.d1-this.xyz.d1*v.xyz.d3,
                this.xyz.d1*v.xyz.d2-this.xyz.d2*v.xyz.d1);
    }

    /**
     * calc the length Squared of vector
     * @return the length in Squared
     */
    public Double lengthSquared(){
        return this.dotProduct(this);
    }

    /**
     * calc the length of vector
     * @return the length of the vector
     */
    public Double length(){
        return Math.sqrt(this.lengthSquared());
    }

    /**
     * normalize the vector
     * @return new vector
     */
    public Vector normalize(){
        return new Vector(this.xyz.reduce(this.length()));
    }

    public Vector rotate(Vector axis, double angle){
        double x, y, z;
        double u, v, w;
        x=getX();y=getY();z=getZ();
        u=axis.getX();v=axis.getY();w=axis.getZ();
        double xPrime = u*(u*x + v*y + w*z)*(1d - Math.cos(angle))
                + x*Math.cos(angle)
                + (-w*y + v*z)*Math.sin(angle);
        double yPrime = v*(u*x + v*y + w*z)*(1d - Math.cos(angle))
                + y*Math.cos(angle)
                + (w*x - u*z)*Math.sin(angle);
        double zPrime = w*(u*x + v*y + w*z)*(1d - Math.cos(angle))
                + z*Math.cos(angle)
                + (-v*x + u*y)*Math.sin(angle);
        return new Vector(xPrime, yPrime, zPrime);
    }

    /**
     * find the orthogonal vector
     * @return
     */
    public Vector findOrthogonal(){
        double a, b, c;
        a = getX(); b = getY(); c = getZ();
        if(!isZero(c)){
            return new Vector(1,1,-(a+b)/c).normalize();
        }
        else if(!isZero(b))
        {
            return new Vector(1,-(a+c)/b,1).normalize();
        }
        else{
            return new Vector(-(b+c)/a,1,1).normalize();
        }
    }

    @Override
    public String toString() {
        return "Vector{" +
                "xyz=" + xyz +
                '}';
    }
}
