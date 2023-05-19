package geometries;

import primitives.Point;
import primitives.Vector;

public abstract class RadialGeometry extends Geometry{
    final protected Double radius;

    public RadialGeometry(Double radius) {
        this.radius = radius;
    }
}
