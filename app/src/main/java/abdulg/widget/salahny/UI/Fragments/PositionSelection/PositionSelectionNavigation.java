package abdulg.widget.salahny.UI.Fragments.PositionSelection;

import abdulg.widget.salahny.Model.Places.Mosque;

/**
 * Created by Abdullah on 06/02/2018.
 */

public interface PositionSelectionNavigation {
    public void onGPSSelected();
    public void onManualSelected();

    public void onCoordinatesChosen(Double Latitude, Double Longitude, String cityName);

}
