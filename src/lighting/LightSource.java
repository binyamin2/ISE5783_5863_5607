package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * interface for light source
 */
public interface LightSource {
    /**
     * get intensity
     * @param p
     * @return Color
     */
    public Color getIntensity(Point p);

    /**
     * get the direction of the light
     * @param p
     * @return Vector
     */
    public Vector getL(Point p);

    double getDistance(Point point);

}
