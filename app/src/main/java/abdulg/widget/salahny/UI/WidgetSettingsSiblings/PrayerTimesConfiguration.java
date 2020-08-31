package abdulg.widget.salahny.UI.WidgetSettingsSiblings;

import abdulg.widget.salahny.Controllere.MainSalahTimesController;
import abdulg.widget.salahny.Model.Enums.AsrJuristicMethod;
import abdulg.widget.salahny.Model.Enums.HighAltitudeAdjustmentMethod;
import abdulg.widget.salahny.Model.Enums.PrayerTimesCalculationMethod;
import abdulg.widget.salahny.Model.View.WidgetSettingsModel;
import abdulg.widget.salahny.UI.Fragments.TabFragment;
import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import abdulg.widget.salahny.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.shawnlin.numberpicker.NumberPicker;

import com.shawnlin.numberpicker.NumberPicker.OnValueChangeListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ag on 16-06-2017.
 */

public class PrayerTimesConfiguration extends TabFragment {

    private MainSalahTimesController controller;
    private WidgetSettingsModel widget;

    @BindView(R.id.headline)     TextView headline;
    @BindView(R.id.timedifference_listview) ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.widgetsettings_timedifference, container, false);
        ButterKnife.bind(this, rootView);

        controller = new MainSalahTimesController(getContext());
        List<ConfigurationItem> configurationItems = new ArrayList<>();
        configurationItems.add(new ConfigurationItem(R.string.name,R.string.name_explain));
        configurationItems.add(new ConfigurationItem(R.string.primary,controller.getPrimaryWidget() != null && controller.getPrimaryWidget().getWidgetId() == widget.getWidgetId() ? R.string.primary_explain_primary : R.string.primary_explain_notprimary));
        configurationItems.add(new ConfigurationItem(R.string.calculation_method,R.string.calculation_method_explain));
        configurationItems.add(new ConfigurationItem(R.string.high_altitude_adjustment,R.string.high_altitude_adjustment_explain));
        configurationItems.add(new ConfigurationItem(R.string.asr_juristic_method,R.string.asr_juristic_method_explain));
        configurationItems.add(new ConfigurationItem(R.string.timedifferencessetup,R.string.timedifferencessetup_explain));
        final ConfigurationAdapter adapter = new ConfigurationAdapter(getLayoutInflater(), configurationItems);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        showNameDialog();
                        break;
                    case 1:
                        setAsPrimary(view);
                        adapterView.requestLayout();
                        break;
                    case 2:
                        showCalculationMethodDialog();
                        break;
                    case 3:
                        showHighAltitudeDialog();
                        break;
                    case 4:
                        showAsrJuristicDialog();
                        break;
                    case 5:
                        showTimeDifferencesDialog();
                        break;
                }
            }
        });
        return rootView;
    }

    public void showAsrJuristicDialog() {
        List<String> arr = new ArrayList<>();
        AsrJuristicMethod currentasrJuristicMethod = controller.getCurrentAsrJuristicMethod(widget);
        int i =0;
        int currentIndex = 0;
        for (AsrJuristicMethod asrJuristicMethod : AsrJuristicMethod.values()) {
            arr.add(this.getContext().getText(asrJuristicMethod.getNameKey()).toString());
            if (currentasrJuristicMethod.equals(asrJuristicMethod))
                currentIndex = i;
            i = i + 1;
        }
        MaterialDialog show = new MaterialDialog.Builder(this.getContext())
                .title(R.string.calculation_method)
                .items(arr)
                .itemsCallbackSingleChoice(currentIndex, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        /**
                         * If you use alwaysCallSingleChoiceCallback(), which is discussed below,
                         * returning false here won't allow the newly selected radio button to actually be selected.
                         **/
                        return true;
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        controller.setCurrentAsrJuristicMethod(widget, AsrJuristicMethod.values()[dialog.getSelectedIndex()]);
                        controller.updateWidget(widget);
                    }
                })
                .positiveText(R.string.choose)
                .show();
    }

    public void showHighAltitudeDialog() {
        List<String> arr = new ArrayList<>();
        HighAltitudeAdjustmentMethod currentHighAltitudeAdjustmentMethod = controller.getCurrentHighAltitudeAdjustmentMethod(widget);
        int i =0;
        int currentIndex = 0;
        for (HighAltitudeAdjustmentMethod highAltitudeAdjustmentMethod : HighAltitudeAdjustmentMethod.values()) {
            arr.add(this.getContext().getText(highAltitudeAdjustmentMethod.getNameKey()).toString());
            if (currentHighAltitudeAdjustmentMethod.equals(highAltitudeAdjustmentMethod))
                currentIndex = i;
            i = i + 1;
        }
        MaterialDialog show = new MaterialDialog.Builder(this.getContext())
                .title(R.string.calculation_method)
                .items(arr)
                .itemsCallbackSingleChoice(currentIndex, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        /**
                         * If you use alwaysCallSingleChoiceCallback(), which is discussed below,
                         * returning false here won't allow the newly selected radio button to actually be selected.
                         **/
                        return true;
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        controller.setCurrentHighAltitudeAdjustmentMethod(widget, HighAltitudeAdjustmentMethod.values()[dialog.getSelectedIndex()]);
                        controller.updateWidget(widget);
                    }
                })
                .positiveText(R.string.choose)
                .show();
    }

    public void showTimeDifferencesDialog(){
        boolean wrapInScrollView = false;
        int[] allNumberPickers = new int[]{R.id.timedifferencefajr, R.id.timedifferenceshuruk, R.id.timedifferencedhuhr, R.id.timedifferenceasr, R.id.timedifferencemaghrib, R.id.timedifferenceisha};
        final MaterialDialog show = new MaterialDialog.Builder(this.getContext())
                .title(R.string.timedifferencessetup)
                .customView(R.layout.timedifferencesetup, wrapInScrollView)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Integer fajr = ((NumberPicker) dialog.findViewById(R.id.timedifferencefajr)).getValue();
                        Integer shuruk = ((NumberPicker) dialog.findViewById(R.id.timedifferenceshuruk)).getValue();
                        Integer dhuhr = ((NumberPicker) dialog.findViewById(R.id.timedifferencedhuhr)).getValue();
                        Integer asr = ((NumberPicker) dialog.findViewById(R.id.timedifferenceasr)).getValue();
                        Integer maghrib = ((NumberPicker) dialog.findViewById(R.id.timedifferencemaghrib)).getValue();
                        Integer isha = ((NumberPicker) dialog.findViewById(R.id.timedifferenceisha)).getValue();
                        controller.setCurrentTimeDifferences(widget, new int[]{fajr,shuruk,dhuhr,asr,maghrib,isha});
                        controller.updateWidget(widget);
                    }
                })
                .positiveText(R.string.choose)
                .show();
        int[] currentTimeDifferences = controller.getCurrentTimeDifferences(widget);
        setNumberPickerValue(show, allNumberPickers, new int[]{currentTimeDifferences[0], currentTimeDifferences[1], currentTimeDifferences[2], currentTimeDifferences[3], currentTimeDifferences[4], currentTimeDifferences[5]});
        final NumberPicker.OnValueChangeListener onValueChangeListener = new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                setNumberPickerValue(show, allNumberPickers, new int[]{newVal});
            }
        };
        ((CheckBox)show.getCustomView().findViewById(R.id.timedifference_cb_changeallsametime)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    setNumberPickerValue(show, allNumberPickers, new int[]{((NumberPicker) show.findViewById(R.id.timedifferencefajr)).getValue()});
                    setNumberPickerValueChangedListener(show, allNumberPickers, new OnValueChangeListener[]{onValueChangeListener});
                } else {
                    setNumberPickerValueChangedListener(show, allNumberPickers, new OnValueChangeListener[]{null});
                }
            }
        });

    }

    private void setNumberPickerValue(MaterialDialog show, int[] ids, int[] values){
        for (int i=0;i<ids.length;i++) {
            int value = 0;
            if (values.length == 1){
                value = values[0];
            } else {
                value = values[i];
            }
            ((NumberPicker) show.getCustomView().findViewById(ids[i])).setValue(value);
        }
    }

    private void setNumberPickerValueChangedListener(MaterialDialog show, int[] ids, OnValueChangeListener[] onValueChangedListener){
        for (int i=0;i<ids.length;i++) {
            OnValueChangeListener value = null;
            if (onValueChangedListener.length == 1){
                value = onValueChangedListener[0];
            } else {
                value = onValueChangedListener[i];
            }
            ((NumberPicker) show.getCustomView().findViewById(ids[i])).setOnValueChangedListener(value);
        }
    }

    public void showNameDialog(){
        new MaterialDialog.Builder(this.getContext())
                .title(R.string.name)
                .content(R.string.name_dialog)
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input("", controller.getName(widget), new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        controller.setName(widget, input.toString());
                    }
                }).show();
    }
    public void setAsPrimary(View view){
        controller.setAsPrimaryWidget(widget);
        ((TextView)view.findViewById(R.id.timediffence_listitem_text)).setText(getText(R.string.primary_explain_primary));
    }

    public void showCalculationMethodDialog(){
        List<String> arr = new ArrayList<>();
        PrayerTimesCalculationMethod currentCalculationMethod = controller.getCurrentCalculationMethod(widget);
        int i =0;
        int currentIndex = 0;
        for (PrayerTimesCalculationMethod prayerTimesCalculationMethod : PrayerTimesCalculationMethod.values()) {
            arr.add(this.getContext().getText(prayerTimesCalculationMethod.getNameKey()).toString());
            if (currentCalculationMethod.equals(prayerTimesCalculationMethod))
                currentIndex = i;
            i = i + 1;
        }
        MaterialDialog show = new MaterialDialog.Builder(this.getContext())
                .title(R.string.calculation_method)
                .items(arr)
                .itemsCallbackSingleChoice(currentIndex, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        /**
                         * If you use alwaysCallSingleChoiceCallback(), which is discussed below,
                         * returning false here won't allow the newly selected radio button to actually be selected.
                         **/
                        return true;
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        controller.setCurrentCalculationMethod(widget, PrayerTimesCalculationMethod.values()[dialog.getSelectedIndex()]);
                        controller.updateWidget(widget);
                    }
                })
                .positiveText(R.string.choose)
                .show();
    }

    public WidgetSettingsModel getWidget() {
        return widget;
    }

    public void setWidget(WidgetSettingsModel widget) {
        this.widget = widget;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        headline.setText(R.string.prayer_times_onfiguration);
    }

    @Override
    public void onTabInFocus(Activity activity) {

    }

    public class ConfigurationAdapter extends BaseAdapter{
        private LayoutInflater inflater;
        private List<ConfigurationItem> data;

        public ConfigurationAdapter(LayoutInflater inflater, List<ConfigurationItem> data) {
            this.inflater = inflater;
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int i) {
            return data.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View row = inflater.inflate(R.layout.timedifference_listitem, viewGroup, false);
            ((TextView)row.findViewById(R.id.timediffence_listitem_headline)).setText(getText(data.get(i).getHeadline()));
            ((TextView)row.findViewById(R.id.timediffence_listitem_text)).setText(getText(data.get(i).getText()));
            return row;
        }
    }

    public class ConfigurationItem{
        private int headline;
        private int text;

        public ConfigurationItem(int headline, int text) {
            this.headline = headline;
            this.text = text;
        }

        public int getHeadline() {
            return headline;
        }

        public void setHeadline(int headline) {
            this.headline = headline;
        }

        public int getText() {
            return text;
        }

        public void setText(int text) {
            this.text = text;
        }
    }
}
