package abdulg.widget.salahny.Controllere;

import abdulg.widget.salahny.Model.AppDatabase;
import abdulg.widget.salahny.Model.DatabaseMigrations;
import android.app.Application;
import androidx.room.Room;


/**
 * Created by Abdullah on 12/01/2018.
 */

public class MyApplication extends Application {
    public static MyApplication sInstance;
    private AppDatabase database;

    @Override
    public void onTerminate() {
        super.onTerminate();

    }

    @Override
    public void onCreate() {
        super.onCreate();
        String dbName = "SalahWidget.db";
        database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, dbName).allowMainThreadQueries().fallbackToDestructiveMigration()
            .addMigrations(DatabaseMigrations.MIGRATION_1_2)
            .addMigrations(DatabaseMigrations.MIGRATION_2_3)
            .build();

        sInstance = this;
    }

    public static AppDatabase getDatabase(){
        return sInstance.database;
    }

    //used for testing
    public static void setDatabase(AppDatabase database){
        sInstance.database = database;
    }

}
