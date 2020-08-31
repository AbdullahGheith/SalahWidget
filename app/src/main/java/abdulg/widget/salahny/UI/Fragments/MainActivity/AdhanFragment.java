package abdulg.widget.salahny.UI.Fragments.MainActivity;

import abdulg.widget.salahny.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AdhanFragment extends Fragment {

	View view;

	AdhanPreference adhanPreference;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		if (view != null) {
			FragmentManager manager = getChildFragmentManager();
			FragmentTransaction ft = manager.beginTransaction();
			ft.replace(R.id.adhan_settings_container_framelayout, adhanPreference);
			ft.commit();
			return view;
		}

	  view = inflater.inflate(R.layout.adhan_settings_container, container, false);
		adhanPreference = new AdhanPreference();
		//ButterKnife.bind(view);
		FragmentManager manager = getChildFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		ft.replace(R.id.adhan_settings_container_framelayout, adhanPreference);
		ft.commit();
		return view;
	}
}
