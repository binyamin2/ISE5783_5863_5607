package lighting;

import primitives.Util.*;
import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * class for PointLight
 */
public class PointLight extends Light implements LightSource {

    protected Point position;

    private double kC = 1, kL = 0,kQ = 0;

    /**
     * Ctor
     * @param intensity
     * @param position
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;


    }


    /*setters*/
    public PointLight setkC(double kC) {
        this.kC = kC;
        return this;
    }

    public PointLight setkL(double kL) {
        this.kL = kL;
        return this;
    }

    public PointLight setkQ(double kQ) {
        this.kQ = kQ;
        return this;
    }
    @Override
    public Color getIntensity(Point p) {
        //calculate the light propagation model for Point light
        double distance = this.position.distance(p);
        double denominator= kC + kL * distance + kQ * distance * distance;

        return  getIntensity().reduce(denominator);
    }

    @Override
    public Vector getL(Point p) {
        return this.position.subtract(p).normalize();
    }
}
