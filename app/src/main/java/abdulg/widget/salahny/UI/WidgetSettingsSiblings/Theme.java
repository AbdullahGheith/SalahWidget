package abdulg.widget.salahny.UI.WidgetSettingsSiblings;

import abdulg.widget.salahny.Controllere.MainSalahTimesController;
import abdulg.widget.salahny.Misc.Utils;
import abdulg.widget.salahny.Model.View.ThemeConfiguration;
import abdulg.widget.salahny.Model.View.WidgetSettingsModel;
import abdulg.widget.salahny.UI.Fragments.TabFragment;
import android.Manifest;
import android.Manifest.permission;
import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.widget.RelativeLayout.LayoutParams;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.h6ah4i.android.widget.verticalseekbar.VerticalSeekBarWrapper;
import com.madrapps.pikolo.HSLColorPicker;
import com.madrapps.pikolo.listeners.OnColorSelectionListener;

import abdulg.widget.salahny.Providers.SalahWidgetProvider;
import abdulg.widget.salahny.R;
import abdulg.widget.salahny.WidgetResource.WidgetResource;

/**
 * Created by ag on 16-06-2017.
 */

public class Theme extends TabFragment implements SeekBar.OnSeekBarChangeListener, OnColorSelectionListener, RadioGroup.OnCheckedChangeListener {
    private WidgetSettingsModel widget;
    private WidgetResource widgetResource;
    private ThemeConfiguration themeConfigurationTemp;
    private MainSalahTimesController controller;
    private Activity thisActivity;

    @BindView(R.id.themePreview)
    RelativeLayout widgetContainer;

    @BindView(R.id.ThemeColorPicker)
    HSLColorPicker colorPicker;

    @BindView(R.id.theme_radiogroup)
    RadioGroup radioGroup;

    @BindView(R.id.themeTransperancySeekBar)
    SeekBar transparency;

    @BindView(R.id.themeFontSizeSeekBar)
    SeekBar fontSize;

    @BindView(R.id.themebackground)
    ConstraintLayout backgroundForPreview;

    @BindView(R.id.theme_seekbarwrapper_fontsize)
    VerticalSeekBarWrapper seekBarWrapperFontSize;

    @BindView(R.id.theme_img_textsize)
    ImageView imgFontSize;

    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = (ViewGroup) inflater.inflate(
                R.layout.widgetsettings_theme, container, false);
        ButterKnife.bind(this, view);
        thisActivity = getActivity();
        controller = new MainSalahTimesController(getContext());

        //Inflate the widget
        view.inflate(getContext(), widgetResource.getLayout(), widgetContainer);
        //setFixedWidgetSize();

        //Get widgetConfiguration
        themeConfigurationTemp = widget.getThemeConfiguration();

        //Apply widgetConfiguration
        applyWidgetConfiguration();

        return view;
    }

    private void setFixedWidgetSize(){
        int widthsize = 0;
        int heightsize = 0;
        switch(widgetResource.getLayout()){
            case R.layout.fivebyone:
                widthsize = 5;
                heightsize = 1;
                break;
                case R.layout.fourbyone:
                widthsize = 4;
                heightsize = 1;
                break;
                case R.layout.twobytwo:
                widthsize = 2;
                heightsize = 2;
                break;
        }
        if (widthsize != 0 || heightsize != 0){

            (widgetContainer.getChildAt(0)).setLayoutParams(new LayoutParams(Utils.getDip(getContext(),(widthsize * 70)-30),Utils.getDip(getContext(),(heightsize*70)-30)));

        }
    }

    @OnClick(R.id.theme_reset)
    public void themeReset(){
        themeConfigurationTemp = new ThemeConfiguration();
        applyWidgetConfiguration();
    }

    @OnClick(R.id.theme_save)
    public void themeSave(){
        controller.setWidgetThemeConfiguration(widget, themeConfigurationTemp);
        controller.updateWidget(widget);
        Toast.makeText(getContext(), getText(R.string.widget_updated), Toast.LENGTH_LONG).show();
    }

    public void applyWidgetConfiguration(){
        setTextColorForGroupBackground(view, abdulg.widget.salahny.Model.View.Color.fromString(themeConfigurationTemp.getBackgroundColor()).toAndroidColor());
//        setTextColorForGroupCorner(view, abdulg.widget.salahny.Model.View.Color.fromString(themeConfigurationTemp.getCornerColor()).toAndroidColor());
        setTextColorForGroupTexts(view, abdulg.widget.salahny.Model.View.Color.fromString(themeConfigurationTemp.getTextColor()).toAndroidColor());
        setTextColorForGroupTimes(view, abdulg.widget.salahny.Model.View.Color.fromString(themeConfigurationTemp.getTimesColor()).toAndroidColor());

        setTransparencyForGroupBackground(view, abdulg.widget.salahny.Model.View.Color.fromString(themeConfigurationTemp.getBackgroundColor()).getA());
//        setTransparencyForGroupCorner(view, abdulg.widget.salahny.Model.View.Color.fromString(themeConfigurationTemp.getCornerColor()).getA());
        setTransparencyForGroupTexts(view, abdulg.widget.salahny.Model.View.Color.fromString(themeConfigurationTemp.getTextColor()).getA());
        setTransparencyForGroupTimes(view, abdulg.widget.salahny.Model.View.Color.fromString(themeConfigurationTemp.getTimesColor()).getA());
        setTextSizeForGroupTimes(view, themeConfigurationTemp.getTimesSize());
        setTextSizeForGroupTexts(view, themeConfigurationTemp.getTextSize());
    }

    private void setColorPickerAndTransparency(int color, int alpha, Integer textSize){
        colorPicker.setColor(color);
        if (alpha < 1) //maybe not necessary
            transparency.setProgress(alpha*255);
        else
            transparency.setProgress(alpha);

        if (textSize == null){
            hideTextSize();
        } else {
            showTextSize();
            if (textSize == -1){
                fontSize.setProgress((int)(getResources().getDimensionPixelSize(R.dimen.textsize)/getContext().getResources().getDisplayMetrics().density));
            } else {
                fontSize.setProgress(textSize);
            }
        }
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        if (ActivityCompat.checkSelfPermission(thisActivity, permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            setBackgroundForPreviewToHomeBackground();
        }
            onCheckedChanged(radioGroup, radioGroup.getCheckedRadioButtonId());
            radioGroup.setOnCheckedChangeListener(this);

            transparency.setOnSeekBarChangeListener(this);

            fontSize.setOnSeekBarChangeListener(this);

            colorPicker.setColorSelectionListener(this);
    }

    public void setBackgroundForPreviewToHomeBackground() {
        Drawable background = null;
        if (ActivityCompat.checkSelfPermission(thisActivity, permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            background = WallpaperManager.getInstance(view.getContext()).getDrawable();
        } else {
            background = new ColorDrawable();
            ((ColorDrawable) background).setColor(Color.BLACK);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            backgroundForPreview.setBackground(background);
        } else {
            backgroundForPreview.setBackgroundDrawable(background);
        }
    }

    public void setTextColorForGroupBackground(View view, int color){
        abdulg.widget.salahny.Model.View.Color tempColor = abdulg.widget.salahny.Model.View.Color.fromString(themeConfigurationTemp.getBackgroundColor());
        tempColor.setR(Color.red(color));
        tempColor.setB(Color.blue(color));
        tempColor.setG(Color.green(color));
        themeConfigurationTemp.setBackgroundColor(tempColor.toString());


        ColorDrawable background = (ColorDrawable)(view.findViewById(widgetResource.getLayoutWrapper())).getBackground();
        if (background == null){
            background = new ColorDrawable();
	        if (VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN) {
		        view.findViewById(widgetResource.getLayoutWrapper()).setBackground(background);
	        } else {
		        view.findViewById(widgetResource.getLayoutWrapper()).setBackgroundDrawable(background);
	        }
        }
        background.setColor(tempColor.toAndroidColorWithAlpha());


    }

    public void setTextColorForGroupTimes(View view, int color){
        abdulg.widget.salahny.Model.View.Color tempColor = abdulg.widget.salahny.Model.View.Color.fromString(themeConfigurationTemp.getTimesColor());
        tempColor.setR(Color.red(color));
        tempColor.setB(Color.blue(color));
        tempColor.setG(Color.green(color));
        themeConfigurationTemp.setTimesColor(tempColor.toString());

        ((TextView)view.findViewById(widgetResource.getFajrTime())).setTextColor(tempColor.toAndroidColorWithAlpha());
        ((TextView)view.findViewById(widgetResource.getShurukTime())).setTextColor(tempColor.toAndroidColorWithAlpha());
        ((TextView)view.findViewById(widgetResource.getDhuhrTime())).setTextColor(tempColor.toAndroidColorWithAlpha());
        ((TextView)view.findViewById(widgetResource.getAsrTime())).setTextColor(tempColor.toAndroidColorWithAlpha());
        ((TextView)view.findViewById(widgetResource.getMaghribTime())).setTextColor(tempColor.toAndroidColorWithAlpha());
        ((TextView)view.findViewById(widgetResource.getIshaTime())).setTextColor(tempColor.toAndroidColorWithAlpha());


    }

    public void setTextColorForGroupTexts(View view, int color){
        abdulg.widget.salahny.Model.View.Color tempColor = abdulg.widget.salahny.Model.View.Color.fromString(themeConfigurationTemp.getTextColor());
        tempColor.setR(Color.red(color));
        tempColor.setB(Color.blue(color));
        tempColor.setG(Color.green(color));
        themeConfigurationTemp.setTextColor(tempColor.toString());

        ((TextView)view.findViewById(widgetResource.getFajrText())).setTextColor(tempColor.toAndroidColorWithAlpha());
        ((TextView)view.findViewById(widgetResource.getShurukText())).setTextColor(tempColor.toAndroidColorWithAlpha());
        ((TextView)view.findViewById(widgetResource.getDhuhrText())).setTextColor(tempColor.toAndroidColorWithAlpha());
        ((TextView)view.findViewById(widgetResource.getAsrText())).setTextColor(tempColor.toAndroidColorWithAlpha());
        ((TextView)view.findViewById(widgetResource.getMaghribText())).setTextColor(tempColor.toAndroidColorWithAlpha());
        ((TextView)view.findViewById(widgetResource.getIshaText())).setTextColor(tempColor.toAndroidColorWithAlpha());


    }

//    public void setTextColorForGroupCorner(View view, int color){
//
//        abdulg.widget.salahny.Model.View.Color tempColor = abdulg.widget.salahny.Model.View.Color.fromString(themeConfigurationTemp.getCornerColor());
//        tempColor.setR(Color.red(color));
//        tempColor.setB(Color.blue(color));
//        tempColor.setG(Color.green(color));
//        themeConfigurationTemp.setCornerColor(tempColor.toString());
//
//
//        GradientDrawable background = (GradientDrawable)((RelativeLayout) view.findViewById(widgetResource.getLayoutWrapper())).getBackground();
//        background.setStroke(getDip(view.getContext(),3), tempColor.toAndroidColorWithAlpha());
//
//
//    }
    public void setTransparencyForGroupBackground(View view, int alpha /*0 to 255*/){
        abdulg.widget.salahny.Model.View.Color color = abdulg.widget.salahny.Model.View.Color.fromString(themeConfigurationTemp.getBackgroundColor());

        ColorDrawable background = (ColorDrawable) (view.findViewById(widgetResource.getLayoutWrapper())).getBackground();
        background.setColor(Color.argb(alpha, color.getR(), color.getG(), color.getB()));
        //((RelativeLayout)view.findViewById(widgetResource.getLayoutWrapper()).setbac
        color.setA(alpha);
        themeConfigurationTemp.setBackgroundColor(color.toString());
    }

    public void setTransparencyForGroupTimes(View view, int alpha){
        abdulg.widget.salahny.Model.View.Color color = abdulg.widget.salahny.Model.View.Color.fromString(themeConfigurationTemp.getTimesColor());
        color.setA(alpha);
        themeConfigurationTemp.setTimesColor(color.toString());

        ((TextView)view.findViewById(widgetResource.getFajrTime())).setTextColor(color.toAndroidColorWithAlpha());
        ((TextView)view.findViewById(widgetResource.getShurukTime())).setTextColor(color.toAndroidColorWithAlpha());
        ((TextView)view.findViewById(widgetResource.getDhuhrTime())).setTextColor(color.toAndroidColorWithAlpha());
        ((TextView)view.findViewById(widgetResource.getAsrTime())).setTextColor(color.toAndroidColorWithAlpha());
        ((TextView)view.findViewById(widgetResource.getMaghribTime())).setTextColor(color.toAndroidColorWithAlpha());
        ((TextView)view.findViewById(widgetResource.getIshaTime())).setTextColor(color.toAndroidColorWithAlpha());
    }

    public void setTextSizeForGroupTimes(View view, int textsize){
        themeConfigurationTemp.setTimesSize(textsize);
        float value = textsize;
        if (textsize == -1){
            value = getContext().getResources().getDimension(R.dimen.textsize) / getContext().getResources().getDisplayMetrics().density;
        }
        ((TextView)view.findViewById(widgetResource.getFajrTime())).setTextSize(value);
        ((TextView)view.findViewById(widgetResource.getShurukTime())).setTextSize(value);
        ((TextView)view.findViewById(widgetResource.getDhuhrTime())).setTextSize(value);
        ((TextView)view.findViewById(widgetResource.getAsrTime())).setTextSize(value);
        ((TextView)view.findViewById(widgetResource.getMaghribTime())).setTextSize(value);
        ((TextView)view.findViewById(widgetResource.getIshaTime())).setTextSize(value);

    }

    public void setTextSizeForGroupTexts(View view, int textsize){

        themeConfigurationTemp.setTextSize(textsize);
        float value = textsize;
        if (textsize == -1){
            value = getContext().getResources().getDimension(R.dimen.textsize) / getContext().getResources().getDisplayMetrics().density;
        }
        ((TextView)view.findViewById(widgetResource.getFajrText())).setTextSize(value);
        ((TextView)view.findViewById(widgetResource.getShurukText())).setTextSize(value);
        ((TextView)view.findViewById(widgetResource.getDhuhrText())).setTextSize(value);
        ((TextView)view.findViewById(widgetResource.getAsrText())).setTextSize(value);
        ((TextView)view.findViewById(widgetResource.getMaghribText())).setTextSize(value);
        ((TextView)view.findViewById(widgetResource.getIshaText())).setTextSize(value);


    }
    public void setTransparencyForGroupTexts(View view, int alpha){
        abdulg.widget.salahny.Model.View.Color color = abdulg.widget.salahny.Model.View.Color.fromString(themeConfigurationTemp.getTextColor());
        color.setA(alpha);
        themeConfigurationTemp.setTextColor(color.toString());

        ((TextView)view.findViewById(widgetResource.getFajrText())).setTextColor(color.toAndroidColorWithAlpha());
        ((TextView)view.findViewById(widgetResource.getShurukText())).setTextColor(color.toAndroidColorWithAlpha());
        ((TextView)view.findViewById(widgetResource.getDhuhrText())).setTextColor(color.toAndroidColorWithAlpha());
        ((TextView)view.findViewById(widgetResource.getAsrText())).setTextColor(color.toAndroidColorWithAlpha());
        ((TextView)view.findViewById(widgetResource.getMaghribText())).setTextColor(color.toAndroidColorWithAlpha());
        ((TextView)view.findViewById(widgetResource.getIshaText())).setTextColor(color.toAndroidColorWithAlpha());


    }
//    public void setTransparencyForGroupCorner(View view, int alpha){
//
//        abdulg.widget.salahny.Model.View.Color color = abdulg.widget.salahny.Model.View.Color.fromString(themeConfigurationTemp.getCornerColor());
//        color.setA(alpha);
//        themeConfigurationTemp.setCornerColor(color.toString());
//
//        int transparent = Color.argb(alpha, color.getR(), color.getR(), color.getB());
//        GradientDrawable background = (GradientDrawable)((RelativeLayout) view.findViewById(widgetResource.getLayoutWrapper())).getBackground();
//        background.setStroke(getDip(view.getContext(),3), transparent);
//
//
//    }

    public int getDip(Context context,int pixel)
    {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pixel * scale + 0.5f);
    }

    public WidgetSettingsModel getWidget() {
        return widget;
    }

    public void setWidget(Context context, WidgetSettingsModel widget) {
        this.widget = widget;
        widgetResource = SalahWidgetProvider.getWidgetResourceFromWidgetId(context,widget.getWidgetId());
    }

    @Override
    public void onTabInFocus(Activity activity) {
        //Toast.makeText(activity, getText(R.string.themedisclaimer), Toast.LENGTH_LONG).show();
    }

    //-----------------------SeekBar.OnSeekBarChangeListener-----------------------\\

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

        if (seekBar == fontSize){
            switch (radioGroup.getCheckedRadioButtonId()) {
                case R.id.theme_radiobutton_times:
                    setTextSizeForGroupTimes(view, i);
                    break;
                case R.id.theme_radiobutton_texts:
                    setTextSizeForGroupTexts(view, i);
                    break;
            }
        } else if (seekBar == transparency) {
            switch (radioGroup.getCheckedRadioButtonId()) {
                case R.id.theme_radiobutton_background:
                    setTransparencyForGroupBackground(view, i);
                    break;
                case R.id.theme_radiobutton_times:
                    setTransparencyForGroupTimes(view, i);
                    break;
                case R.id.theme_radiobutton_texts:
                    setTransparencyForGroupTexts(view, i);
                    break;
//            case R.id.theme_radiobutton_corner:
//                setTransparencyForGroupCorner(view, i);
//                break;
            }
        }

    }

    private void hideTextSize(){
        fontSize.setVisibility(View.INVISIBLE);
        imgFontSize.setImageDrawable(null);
    }
    private void showTextSize(){
        fontSize.setVisibility(View.VISIBLE);
        imgFontSize.setImageResource(R.drawable.textsize);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    //-----------------------OnColorSelectionListener-----------------------\\

    @Override
    public void onColorSelected(int i) {
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.theme_radiobutton_background:
                setTextColorForGroupBackground(view, i);
                break;
            case R.id.theme_radiobutton_times:
                setTextColorForGroupTimes(view, i);
                break;
            case R.id.theme_radiobutton_texts:
                setTextColorForGroupTexts(view, i);
                break;
//            case R.id.theme_radiobutton_corner:
//                setTextColorForGroupCorner(view, i);
//                break;
        }
    }

    @Override
    public void onColorSelectionStart(int i) {

    }

    @Override
    public void onColorSelectionEnd(int i) {

    }

    //-----------------------onCheckedChanged-----------------------\\

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.theme_radiobutton_background:
                abdulg.widget.salahny.Model.View.Color bgColor = abdulg.widget.salahny.Model.View.Color.fromString(themeConfigurationTemp.getBackgroundColor());
                setColorPickerAndTransparency(Color.rgb(bgColor.getR(), bgColor.getG(), bgColor.getB()), bgColor.getA(), null);
                break;
            case R.id.theme_radiobutton_times:
                abdulg.widget.salahny.Model.View.Color timesColor = abdulg.widget.salahny.Model.View.Color.fromString(themeConfigurationTemp.getTimesColor());
                setColorPickerAndTransparency(Color.rgb(timesColor.getR(), timesColor.getG(), timesColor.getB()), timesColor.getA(), themeConfigurationTemp.getTimesSize());
                break;
            case R.id.theme_radiobutton_texts:
                abdulg.widget.salahny.Model.View.Color textColor = abdulg.widget.salahny.Model.View.Color.fromString(themeConfigurationTemp.getTextColor());
                setColorPickerAndTransparency(Color.rgb(textColor.getR(), textColor.getG(), textColor.getB()), textColor.getA(), themeConfigurationTemp.getTextSize());
                break;
//            case R.id.theme_radiobutton_corner:
//                abdulg.widget.salahny.Model.View.Color cornerColor = abdulg.widget.salahny.Model.View.Color.fromString(themeConfigurationTemp.getBackgroundColor());
//                setColorPickerAndTransparency(Color.rgb(cornerColor.getR(), cornerColor.getG(), cornerColor.getB()), cornerColor.getA());
//                break;
        }
    }
}
