package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

public class Geometries implements Intersectable {
    List <Intersectable> intersectables;

    public Geometries() {
        this.intersectables = new LinkedList<Intersectable>();
    }
    public Geometries (Intersectable... geometries ){
        this.intersectables = new LinkedList<Intersectable>(List.of(geometries));
    }

    public void add (Intersectable... geometries ){
        intersectables.addAll(List.of(geometries));
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }
}
