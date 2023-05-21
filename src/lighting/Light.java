package lighting;

import primitives.Color;

/**
 * Abstract class for light
 */
abstract class Light {

    private Color intensity;

    /**
     * ctor
     * @param intensity
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    public Color getIntensity() {
        return intensity;
    }


}
