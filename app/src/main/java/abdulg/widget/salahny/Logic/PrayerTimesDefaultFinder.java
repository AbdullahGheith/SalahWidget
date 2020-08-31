package abdulg.widget.salahny.Logic;

import abdulg.widget.salahny.Model.Enums.HighAltitudeAdjustmentMethod;
import abdulg.widget.salahny.Model.Enums.PrayerTimesCalculationMethod;
import abdulg.widget.salahny.Model.Places.Coordinates;
import abdulg.widget.salahny.Model.Places.CoordinatesPolygon;
import abdulg.widget.salahny.Model.Prayer.PrayerTimesMethodPeriod;

/**
 * Created by Abdullah Gheith on 30/04/2018.
 */
public class PrayerTimesDefaultFinder
{
//  private static CoordinatesPolygon europe;
//  private static CoordinatesPolygon northamerica;
//  private static CoordinatesPolygon africaSyriaLebanon;
//  private static CoordinatesPolygon malaysia;
//  private static CoordinatesPolygon middleeast;
//  private static CoordinatesPolygon pakistanArea;
//  private static CoordinatesPolygon iran;


  public static PrayerTimesMethodPeriod matchCoordinates(Coordinates location){

    if (matchAarhus(location)){
      return new PrayerTimesMethodPeriod(PrayerTimesCalculationMethod.DenmarkMalmo_OldTimes, 9);
    } else if (matchMalmo(location)){
      return new PrayerTimesMethodPeriod(PrayerTimesCalculationMethod.DenmarkMalmo_OldTimes, -2);
    } else if (matchOdense(location)){
      return new PrayerTimesMethodPeriod(PrayerTimesCalculationMethod.DenmarkMalmo_OldTimes, 8);
    }else if (matchSjaelland(location)){
      return new PrayerTimesMethodPeriod(PrayerTimesCalculationMethod.Denmark_NewTimes, HighAltitudeAdjustmentMethod.None);
    } else if (matchDenmark(location)){
      return new PrayerTimesMethodPeriod(PrayerTimesCalculationMethod.DenmarkMalmo_OldTimes);
    }
    else if (PrayerTimesDefaultFinder.matchEurope(location)) {
      return new PrayerTimesMethodPeriod(PrayerTimesCalculationMethod.MWL);
    }
    else {
      return new PrayerTimesMethodPeriod(PrayerTimesCalculationMethod.MWL);
    }
  }

  private static boolean matchOdense(Coordinates coordinate){
    CoordinatesPolygon odense = new CoordinatesPolygon();
    odense.addCoordinate(55.522134317209286,9.742628287499997);
    odense.addCoordinate(55.6688726336791,10.214953263581492);
    odense.addCoordinate(55.62973471305697,10.661863113385834);
    odense.addCoordinate(55.193170513614945,10.93417878577759);
    odense.addCoordinate(54.92932639342457,10.934900702427058);
    odense.addCoordinate(54.701430416594384,10.756372870395808);
    odense.addCoordinate(54.913541810211065,10.094446600864558);
    odense.addCoordinate(55.204220378164564,9.809881792721285);
    odense.addCoordinate(55.429512116409335,9.706564112107571);
    odense.addCoordinate(55.49802503374933,9.662618799607571);
    return odense.checkCoordinate(coordinate);
  }
  private static boolean matchAarhus(Coordinates coordinate){
    CoordinatesPolygon denmark = new CoordinatesPolygon();
    denmark.addCoordinate(56.292645703852415,9.679419226436494);
    denmark.addCoordinate(56.47056366256227,10.187536902217744);
    denmark.addCoordinate(56.159808469803416,10.871435827998994);
    denmark.addCoordinate(55.8218396704992,10.160071081905244);
    denmark.addCoordinate(56.05872796305041,9.319616980342744);
    return denmark.checkCoordinate(coordinate);
  }
  private static boolean matchMalmo(Coordinates coordinate){
    CoordinatesPolygon denmark = new CoordinatesPolygon();
    denmark.addCoordinate(55.691518227565,12.867973983224715);
    denmark.addCoordinate(55.84761868376219,12.972633453281105);
    denmark.addCoordinate(55.34124562264205,13.77252104810725);
    denmark.addCoordinate(55.360593899946544,12.606822028294573);
    denmark.addCoordinate(55.53503152340765,12.6223517189419);
    return denmark.checkCoordinate(coordinate);
  }
  private static boolean matchDenmark(Coordinates coordinate){
    CoordinatesPolygon denmark = new CoordinatesPolygon();
    denmark.addCoordinate(54.907011096630654,8.659966476867794);
    denmark.addCoordinate(54.53900819389571,11.587365621360505);
    denmark.addCoordinate(54.51470031175685,12.164829807930118);
    denmark.addCoordinate(55.302644078277865,13.208006577220317);
    denmark.addCoordinate(56.54235182485765,12.325859109833573);
    denmark.addCoordinate(57.91627139201098,10.602591660885992);
    denmark.addCoordinate(56.87446034243063,7.724173692135992);
    denmark.addCoordinate(54.90930538195171,7.987845567135992);
    return denmark.checkCoordinate(coordinate);
  }

  private static boolean matchSjaelland(Coordinates coordinate){
    CoordinatesPolygon denmark = new CoordinatesPolygon();
    denmark.addCoordinate(55.806879, 10.766703);
    denmark.addCoordinate(56.348420, 12.328477);
    denmark.addCoordinate(55.775001, 12.790434);
    denmark.addCoordinate(54.541770, 12.567475);
    denmark.addCoordinate(54.582612, 10.855096);
    denmark.addCoordinate(55.297104, 11.023874);
    return denmark.checkCoordinate(coordinate);
  }

  private static boolean matchEurope(Coordinates coordinate)
  {
    CoordinatesPolygon europe = new CoordinatesPolygon();
    europe.addCoordinate(35.85998605573864, -6.934652483346554);
    europe.addCoordinate(38.28557048932058, 11.193359127473968);
    europe.addCoordinate(33.15129127835626, 32.55078100247397);
    europe.addCoordinate(42.04516191449255, 40.54882787747397);
    europe.addCoordinate(50.21074927051736, 40.88169032515134);
    europe.addCoordinate(59.56111690751932, 29.01645595015134);
    europe.addCoordinate(62.33378483669551, 33.302743788789485);
    europe.addCoordinate(73.96166196279175, 29.699228163789485);
    europe.addCoordinate(66.42947844830978, -27.517568711210515);
    europe.addCoordinate(35.240506629028644, -10.326602505366509);

    return europe.checkCoordinate(coordinate);
  }
}
