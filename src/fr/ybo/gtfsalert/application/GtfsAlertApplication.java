package fr.ybo.gtfsalert.application;


import android.app.Application;
import fr.ybo.gtfsalert.database.GtfsAlertDatabase;

public class GtfsAlertApplication extends Application {

    private GtfsAlertDatabase database = null;

    public GtfsAlertDatabase getDatabase() {
        return database;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        database = new GtfsAlertDatabase(this);
    }
}