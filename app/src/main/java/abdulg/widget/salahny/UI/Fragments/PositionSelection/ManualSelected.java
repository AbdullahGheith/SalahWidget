package abdulg.widget.salahny.UI.Fragments.PositionSelection;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import abdulg.widget.salahny.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Abdullah on 09/02/2018.
 */

public class ManualSelected extends Fragment implements OnMapReadyCallback {
    PositionSelectionNavigation salahSelection;

    @BindView(R.id.mapView) MapView mapView;
    private GoogleMap map;
    private Fragment thisthing;
    private Marker marker;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manualselected, container, false);
        ButterKnife.bind(this, view);
        thisthing = this;

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            salahSelection = (PositionSelectionNavigation) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement SalahSelection");
        }
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                salahSelection.onCoordinatesChosen(marker.getPosition().latitude, marker.getPosition().longitude, null);
                //Toast.makeText(getActivity(), marker.getPosition().latitude+", "+marker.getPosition().longitude, Toast.LENGTH_LONG).show();
            }
        });

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title("Click here to use this location");
                if (marker != null){
                    marker.remove();
                }
                marker = googleMap.addMarker(markerOptions);
                marker.showInfoWindow();
            }
        });
    }
    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

}
