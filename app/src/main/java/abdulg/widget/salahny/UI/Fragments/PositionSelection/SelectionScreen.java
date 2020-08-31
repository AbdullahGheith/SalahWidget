package abdulg.widget.salahny.UI.Fragments.PositionSelection;


import abdulg.widget.salahny.Controllere.MainSalahTimesController;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import abdulg.widget.salahny.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Abdullah on 06/02/2018.
 */

public class SelectionScreen extends Fragment {
    PositionSelectionNavigation salahSelection;
    MainSalahTimesController controller;

    @BindView(R.id.WhenAdded_Button_UseGPSForLocation) Button useGPSForLocation;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_selectionscreen, container, false);
        ButterKnife.bind(this, view);
        controller = new MainSalahTimesController(getContext());
        return view;
    }

    @OnClick(R.id.WhenAdded_Button_UseGPSForLocation)
    public void gpsSelected(){
        salahSelection.onGPSSelected();
    }

    @OnClick(R.id.WhenAdded_Button_UseManualForLocation)
    public void manualSelected(){
        salahSelection.onManualSelected();
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
}
