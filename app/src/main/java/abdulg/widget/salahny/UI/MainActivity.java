package abdulg.widget.salahny.UI;

import abdulg.widget.salahny.Controllere.MainSalahTimesController;
import abdulg.widget.salahny.Model.View.WidgetSettingsModel;
import abdulg.widget.salahny.Providers.AdhanBroadcastReceiver;
import abdulg.widget.salahny.R;
import abdulg.widget.salahny.UI.Fragments.MainActivity.*;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity
		implements NavigationView.OnNavigationItemSelectedListener, MainActivityNavigation {

	private int fragmentContainerTop = R.id.MainActivity_Fragment;

	private NoWidgetFragment noWidgetFragment;
	private MainContentFragment mainContentFragment;
	private AdhanPreference adhanFragment;

	private Calendar chosenDate = Calendar.getInstance();

	private MainSalahTimesController controller;

	//	@BindView(R.id.toolbar)
//	Toolbar toolbar;
	@BindView(R.id.navigation)
	BottomNavigationView bottomNavigation;

	//@BindView(R.id.nav_view)
	//NavigationView navigationView;


	boolean nowidget = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
//		setSupportActionBar(toolbar);

		AdhanBroadcastReceiver.startAdhanSetup(getApplicationContext());

		//TextView widgetName = navigationView.getHeaderView(0).findViewById(R.id.navigationheader_widgetname);
		//navigationView.setNavigationItemSelectedListener(this);

		controller = new MainSalahTimesController(getApplicationContext());
		noWidgetFragment = new NoWidgetFragment();
		mainContentFragment = new MainContentFragment();
		adhanFragment = new AdhanPreference();

		WidgetSettingsModel primaryWidget = controller.getPrimaryWidget();
		if (primaryWidget == null) {

			swapFragment(noWidgetFragment);
			bottomNavigation.setVisibility(View.GONE);
		} else {
			nowidget = false;
			// ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
			// drawer.addDrawerListener(toggle);
			// toggle.syncState();

			//widgetName.setText(primaryWidget.getWidgetName());
			mainContentFragment.setController(controller);
			mainContentFragment.setWidget(primaryWidget);
			mainContentFragment.setChosenDate(chosenDate);
			initFragments();
			//swapFragment(mainContentFragment);

			initBottomNavigation();
		}
	}

	private void initBottomNavigation() {
		bottomNavigation.setOnNavigationItemSelectedListener(menuItem -> {
			switch (menuItem.getItemId()) {
				case R.id.navigation_home:
					swapFragment(mainContentFragment);
					break;
				case R.id.navigation_adhan:
					swapFragment(adhanFragment);
					break;
			}
			return true;
		});
	}

	private void swapFragment(Fragment fragment) {
		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		if (fragment instanceof MainContentFragment){
			ft.hide(adhanFragment);
			ft.show(mainContentFragment);
		} else if (fragment instanceof AdhanPreference){
			ft.hide(mainContentFragment);
			ft.show(adhanFragment);
		} else{
			ft.setCustomAnimations(android.R.anim.fade_in,
					android.R.anim.fade_out);
			ft.replace(fragmentContainerTop, fragment);
		}
		ft.commit();
	}

	private void initFragments(){
		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		ft.add(fragmentContainerTop, mainContentFragment);
		ft.add(fragmentContainerTop, adhanFragment);
		ft.show(mainContentFragment);
		ft.hide(adhanFragment);
		ft.commit();
	}

	@Override
	public void onBackPressed() {
		//DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		//if (drawer.isDrawerOpen(GravityCompat.START)) {
		//	drawer.closeDrawer(GravityCompat.START);
		//} else {
			super.onBackPressed();
		//}
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		if (!nowidget) {
			getMenuInflater().inflate(R.menu.main, menu);
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
//		 Handle action bar item clicks here. The action bar will
//		 automatically handle clicks on the Home/Up button, so long
//		 as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_setDate) {
			if (mainContentFragment != null) {
				Toast.makeText(getApplicationContext(), getText(R.string.datehint), Toast.LENGTH_LONG).show();
			}
			mainContentFragment.openDatePicker();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		// Handle navigation view item clicks here.
		int id = item.getItemId();

		if (id == R.id.nav_camera) {
			swapFragment(new AdhanSettings());
		} else if (id == R.id.nav_gallery) {

		} else if (id == R.id.nav_slideshow) {

		} else if (id == R.id.nav_manage) {

		} else if (id == R.id.nav_share) {

		} else if (id == R.id.nav_send) {

		}

		//DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		//drawer.closeDrawer(GravityCompat.START);
		return true;
	}
}
