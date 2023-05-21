package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * class of ambient light
 */
public class AmbientLight extends Light{

    public static AmbientLight NONE = new AmbientLight(Color.BLACK,Double3.ZERO);


    /**
     * ctor with Double3
     * @param ia
     * @param ka
     */
    public AmbientLight(Color ia, Double3 ka) {
        super(ia.scale(ka));
    }
    /**
     * ctor with double
     * @param ia
     * @param ka
     */
    public AmbientLight(Color ia, double ka) {
        super(ia.scale(ka));
    }

}
