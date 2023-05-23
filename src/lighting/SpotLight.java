package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * class of SpotLight
 */
public class SpotLight extends PointLight{

    private Vector direction;
    private double NarrowBeam=1;

    /**
     * ctor
     * @param intensity
     * @param position
     * @param direction
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction;
    }

    public SpotLight setNarrowBeam(double narrowBeam) {
        NarrowBeam = narrowBeam;
        return this;
    }

    @Override
    public Color getIntensity(Point p) {
        //calculate the light propagation model for Point light
        Vector L = getL(p);
        //choose the power of the light according the angle between the direction of the light and Vector p-position
        //and pow according the NarrowBeam
        double power=Math.pow(Math.max(0,direction.normalize().dotProduct(L)),NarrowBeam);
        return super.getIntensity(p).scale(power);
    }
}
