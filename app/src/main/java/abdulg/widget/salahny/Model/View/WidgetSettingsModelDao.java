package abdulg.widget.salahny.Model.View;

import abdulg.widget.salahny.Model.Prayer.PrayerTimesMethodPeriod;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface WidgetSettingsModelDao {
	@Query("SELECT * FROM WidgetSettingsModel WHERE widgetId = :id")
	WidgetSettingsModel getWidgetSettingsModel(int id);

	@Query("SELECT * FROM WidgetSettingsModel")
	List<WidgetSettingsModel> getAllWidgetSettingsModel();

	//@Query("SELECT * FROM WidgetSettingsModel where widgetId = :widgetId")
	//WidgetSettingsModelPeriodLists getWidgetSettingsModelPeriodList(int widgetId);

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	void saveWidgetSettingsModelPeriodList(List<PrayerTimesMethodPeriod> widgetList);

	@Query("DELETE FROM PrayerTimesMethodPeriod WHERE widgetSettingsModelId = :id")
	void deleteAllWidgetSettingsModelPeriodList(int id);

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	void save(WidgetSettingsModel widgetSettingsModel);

	@Query("DELETE FROM WidgetSettingsModel WHERE widgetId = :id")
	void deleteWidgetSettignsModel(int id);

	//@Query("SELECT * FROM PaymentsDTO WHERE id = :paymentId")
	//PaymentsDTO getPayments(int paymentId);

	//@Insert(onConflict = OnConflictStrategy.REPLACE)
	//long savePayment(PaymentsDTO paymentsDTO);
}
