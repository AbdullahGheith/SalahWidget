package abdulg.widget.salahny.UI.Fragments.MainActivity;

import abdulg.widget.salahny.R;
import abdulg.widget.salahny.UI.Fragments.PositionSelection.PositionSelectionNavigation;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;

/**
 * Created by Abdullah Gheith on 15/05/2018.
 */
public class NoWidgetFragment extends Fragment {

    MainActivityNavigation navigation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nowidget, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            navigation = (MainActivityNavigation) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement MainActivityNavigation");
        }
    }
}
