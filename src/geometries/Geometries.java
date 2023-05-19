package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

public class Geometries extends Intersectable {
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
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {

        if (geometries.size() == 0)
            return null;
        else{
            List<GeoPoint> result = new LinkedList<>();

            for (Intersectable geometry : geometries)
            {
                List<GeoPoint> intersections = geometry.findGeoIntersections(ray);
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
