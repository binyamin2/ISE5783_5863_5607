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

        if (intersectables.size() == 0)
            return null;
        else{
            List<Point> list = new LinkedList<Point>();

            for (Intersectable var : intersectables)
            {
                List<Point> intersections = var.findIntersections(ray);
                if (intersections != null) {
                    list.addAll(intersections);
                }
            }

            return List.of(list.toArray(new Point[0]));
        }
    }
}
