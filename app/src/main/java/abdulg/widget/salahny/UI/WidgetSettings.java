package abdulg.widget.salahny.UI;

import abdulg.widget.salahny.Model.View.WidgetSettingsModel;
import abdulg.widget.salahny.UI.Fragments.TabFragment;
import abdulg.widget.salahny.UI.WidgetSettingsSiblings.PrayerTimesConfiguration;
import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.Toast;

import abdulg.widget.salahny.Consts.Consts;
import abdulg.widget.salahny.R;
import abdulg.widget.salahny.UI.WidgetSettingsSiblings.Qibla;
import abdulg.widget.salahny.UI.WidgetSettingsSiblings.Theme;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * Created by ag on 15-06-2017.
 */

public class WidgetSettings extends AppCompatActivity {

    private ViewPager mPager;
    private PagerAdapter mPageAdapter;
    private int widgetId = 0;
    private Activity activity;
    private Qibla qiblaFragment;
    private Theme themeFragment;
    private PrayerTimesConfiguration prayerTimesConfiguration;
    public final int requestCode = 125;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widgetsettings);
        this.activity = this;
        if (getIntent() != null)
            if (getIntent().getExtras() != null)
                widgetId = getIntent().getExtras().getInt(Consts.PROVIDER_TO_MAINSETTINGSUI_WIDGETID);

        if (widgetId == 0) {
            Toast.makeText(getApplicationContext(), "WidgetID not present. Contact developer", Toast.LENGTH_LONG);
            finish();
        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, requestCode);
        final BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        final RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.widgetsettings);
        mPager = (ViewPager) findViewById(R.id.widgetsettingspager);
        relativeLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    relativeLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    relativeLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }

                ViewGroup.LayoutParams layoutParams = mPager.getLayoutParams();
                layoutParams.height = mPager.getHeight() - bottomNavigationView.getHeight();
                mPager.setLayoutParams(layoutParams);
                relativeLayout.updateViewLayout(mPager, layoutParams);
            }
        });



        mPageAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPageAdapter);
        mPager.setCurrentItem(1); //defaults to qibla
        TabFragment item = (TabFragment) ((ScreenSlidePagerAdapter) mPageAdapter).getItem(1);
        item.onTabInFocus(activity);
        bottomNavigationView.setSelectedItemId(R.id.action_qibla);
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                System.out.println("");
            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        bottomNavigationView.setSelectedItemId(R.id.action_theme);
                        break;
                    case 1:
                        bottomNavigationView.setSelectedItemId(R.id.action_qibla);
                        break;
                    case 2:
                        bottomNavigationView.setSelectedItemId(R.id.action_timedifference);
                        break;
                }
                TabFragment item = (TabFragment) ((ScreenSlidePagerAdapter) mPageAdapter).getItem(position);
                item.onTabInFocus(activity);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                System.out.println("");
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_theme:
                                mPager.setCurrentItem(0);
                                break;
                            case R.id.action_qibla:
                                mPager.setCurrentItem(1);
                                break;
                            case R.id.action_timedifference:
                                mPager.setCurrentItem(2);
                                break;

                        }
                        return true;
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == this.requestCode) {
            if (grantResults.length != 0){
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    themeFragment.setBackgroundForPreviewToHomeBackground();
                }
            }
        }
    }


    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    if (themeFragment == null) {
                        themeFragment = new Theme();
                        themeFragment.setWidget(getApplicationContext(), WidgetSettingsModel.getWidgetSettingsWithId(widgetId));
                    }
                    return themeFragment;
                case 1:
                    if (qiblaFragment == null) {
                        qiblaFragment = new Qibla();
                        qiblaFragment.setWidget(WidgetSettingsModel.getWidgetSettingsWithId(widgetId));
                    }
                    return qiblaFragment;
                case 2:
                    if (prayerTimesConfiguration == null){
                        prayerTimesConfiguration = new PrayerTimesConfiguration();
                        prayerTimesConfiguration.setWidget(WidgetSettingsModel.getWidgetSettingsWithId(widgetId));
                    }
                    return prayerTimesConfiguration;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    public Theme getThemeFragment() {
        return themeFragment;
    }

    public void setThemeFragment(Theme themeFragment) {
        this.themeFragment = themeFragment;
    }

    public PrayerTimesConfiguration getPrayerTimesConfiguration() {
        return prayerTimesConfiguration;
    }

    public void setPrayerTimesConfiguration(PrayerTimesConfiguration prayerTimesConfiguration) {
        this.prayerTimesConfiguration = prayerTimesConfiguration;
    }

    public Qibla getQiblaFragment() {
        return qiblaFragment;
    }

    public void setQiblaFragment(Qibla qiblaFragment) {
        this.qiblaFragment = qiblaFragment;
    }

}
