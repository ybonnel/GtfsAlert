/*
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package fr.ybo.gtfsalert.activity;


import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import fr.ybo.gtfsalert.R;
import fr.ybo.gtfsalert.adapter.GtfsAdapter;
import fr.ybo.gtfsalert.database.modele.GtfsInfos;
import fr.ybo.gtfsalert.util.TacheAvecProgressDialog;
import fr.ybo.opendata.rennes.exceptions.KeolisReseauException;

public class GtfsAlert extends ListActivity {

    private List<GtfsInfos> infos = new ArrayList<GtfsInfos>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste);
        setListAdapter(new GtfsAdapter(this, infos));
        new TacheAvecProgressDialog(this, getString(R.string.loadingInfo)){

            @Override
            protected void myDoBackground() throws KeolisReseauException {
                infos.addAll(GtfsInfos.getInfos());
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                ((GtfsAdapter)getListAdapter()).notifyDataSetChanged();
            }
        }.execute((Void) null);
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                GtfsInfos info = (GtfsInfos) adapterView.getItemAtPosition(position);
                Intent intent = new Intent(GtfsAlert.this, GtfsDetail.class);
                intent.putExtra("info", info);
                startActivity(intent);
            }
        });
    }
}
