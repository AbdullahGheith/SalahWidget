package abdulg.widget.salahny.Model.Places;



import java.util.ArrayList;
import java.util.List;

import abdulg.widget.salahny.Model.Places.Lib.Point;
import abdulg.widget.salahny.Model.Places.Lib.Polygon;

/**
 * Created by Abdullah Gheith on 30/04/2018.
 */
public class CoordinatesPolygon
{
  List<Coordinates> coordinates;
  Polygon polygon;

  public CoordinatesPolygon()
  {
    coordinates = new ArrayList<>();
  }

  public void addCoordinate(Double latitude, Double longitude){
    addCoordinate(new Coordinates(latitude, longitude));
  }

  public void addCoordinate(Coordinates coordinate){
    if (coordinates == null)
      coordinates = new ArrayList<>();

    coordinates.add(coordinate);
  }

  private void ensurePolygon()
  {
    if (polygon == null){
      Polygon.Builder builder = Polygon.Builder();
      for (Coordinates coordinate : coordinates)
      {
        builder.addVertex(new Point(Double.valueOf(coordinate.getLatitude()), Double.valueOf(coordinate.getLongitude())));
      }
      polygon = builder.build();

    }

  }

  public boolean checkCoordinate(Coordinates coordinate)
  {
    ensurePolygon();
    return polygon.contains(new Point(Double.valueOf(coordinate.getLatitude()), Double.valueOf(coordinate.getLongitude())));
  }

  public static void main(String[] args)
  {
    CoordinatesPolygon coordinatesPolygon = new CoordinatesPolygon();
    coordinatesPolygon.addCoordinate(new Coordinates(55.45169981569036, 10.220132599655244));
    coordinatesPolygon.addCoordinate(new Coordinates(55.29082884821555, 10.21797355065246));
    coordinatesPolygon.addCoordinate(new Coordinates(55.29082884821555, 10.21797355065246));
    coordinatesPolygon.addCoordinate(new Coordinates(55.28394978139586, 10.594127936279847));
    coordinatesPolygon.addCoordinate(new Coordinates(55.46286550577149, 10.573427811590363));
    System.out.println(coordinatesPolygon.checkCoordinate(new Coordinates(55.40364947845819, 10.413439408270051)));
    System.out.println(coordinatesPolygon.checkCoordinate(new Coordinates(55.499441505515314, 9.933474198309113)));

  }
}
