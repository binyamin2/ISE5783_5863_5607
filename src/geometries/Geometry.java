package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;

/**
 * interface for all the geometries
 */
public abstract class Geometry extends Intersectable {
    protected Color emission = Color.BLACK;
    private Material material=new Material();

    /**
     * return the emission light of the geometry
     * @return color
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * set the emission light of the geometry
     * @param emission
     * @return geometry
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /**
     * return the material of the geometry
     * @return
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * set the material
     * @param material
     * @return
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    /**
     * return the normal of the geometry from the point
     * @param p point on the geometry
     * @return vector normal to the point
     */
    public abstract Vector getNormal(Point p);
}
