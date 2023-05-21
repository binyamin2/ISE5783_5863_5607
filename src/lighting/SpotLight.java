package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * class of SpotLight
 */
public class SpotLight extends PointLight{

    private Vector direction;

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

    @Override
    public Color getIntensity(Point p) {
        //calculate the light propagation model for Point light
        Vector L = getL(p);
        return super.getIntensity(p).scale(Math.max(0,direction.dotProduct(L)));
    }
}
