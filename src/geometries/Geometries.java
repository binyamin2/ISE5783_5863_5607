package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

public class Geometries implements Intersectable {
    List <Intersectable> geometries;

    public Geometries() {
        this.geometries = new LinkedList<Intersectable>();
    }
    public Geometries (Intersectable... geometries ){
        this.geometries = new LinkedList<Intersectable>(List.of(geometries));
    }

    public void add (Intersectable... geometries ){
        this.geometries.addAll(List.of(geometries));
    }

    @Override
    public List<Point> findIntersections(Ray ray) {

        if (geometries.size() == 0)
            return null;
        else{
            List<Point> result = new LinkedList<Point>();

            for (Intersectable geometry : geometries)
            {
                List<Point> intersections = geometry.findIntersections(ray);
                if (intersections != null) {
                    result.addAll(intersections);
                }
            }
            if(result.isEmpty()){
                return null;
            }
            return result;
        }
    }
}
