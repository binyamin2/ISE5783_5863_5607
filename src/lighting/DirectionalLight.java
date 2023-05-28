package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * class for DirectionalLight
 */
public class DirectionalLight extends Light implements LightSource {

    private Vector direction;

    /**
     * ctor
     * @param intensity
     * @param direction
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction;
    }

    @Override
    public Color getIntensity(Point p) {
        return getIntensity();
    }

    @Override
    public Vector getL(Point p) {
        return this.direction.normalize();

    }

    @Override
    public double getDistance(Point point) {
        return Double.POSITIVE_INFINITY;
    }
}
