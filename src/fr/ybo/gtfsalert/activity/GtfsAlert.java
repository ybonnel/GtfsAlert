package fr.ybo.gtfsalert.activity;


import android.app.ListActivity;
import android.os.Bundle;
import fr.ybo.gtfsalert.R;
import fr.ybo.gtfsalert.adapter.GtfsAdapter;
import fr.ybo.gtfsalert.util.TacheAvecProgressDialog;
import fr.ybo.opendata.rennes.Gtfs;
import fr.ybo.opendata.rennes.KeolisReseauException;
import fr.ybo.opendata.rennes.modele.bus.GtfsFile;

import java.util.ArrayList;
import java.util.List;

public class GtfsAlert extends ListActivity {

    private List<GtfsFile> files = new ArrayList<GtfsFile>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste);
        setListAdapter(new GtfsAdapter(this, files));
        new TacheAvecProgressDialog<Void, Void, Void>(this, getString(R.string.loadingInfo)){

            @Override
            protected void myDoBackground() throws KeolisReseauException {
                Gtfs gtfs = new Gtfs();
                List<GtfsFile> filesTmp = gtfs.getUpdates();
                files.addAll(filesTmp);
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                ((GtfsAdapter)getListAdapter()).notifyDataSetChanged();
            }
        }.execute((Void) null);
    }
}
