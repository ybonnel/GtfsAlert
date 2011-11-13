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

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import fr.ybo.gtfsalert.R;
import fr.ybo.gtfsalert.util.TacheAvecProgressDialog;
import fr.ybo.opendata.rennes.Gtfs;
import fr.ybo.opendata.rennes.KeolisReseauException;
import fr.ybo.opendata.rennes.modele.bus.GtfsFeedInfo;
import fr.ybo.opendata.rennes.modele.bus.GtfsFile;

import java.text.SimpleDateFormat;
import java.util.IllegalFormatFlagsException;
import java.util.List;

public class GtfsDetail extends Activity {

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    private GtfsFile file;

    private TextView gtfsInfo;
    private TextView gtfsDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gtfsdetail);
        file = (GtfsFile) getIntent().getSerializableExtra("gtfsFile");
        TextView gtfsTitre = (TextView) findViewById(R.id.gtfsTitre);
        gtfsInfo = (TextView) findViewById(R.id.gtfsInfo);
        gtfsDate = (TextView) findViewById(R.id.gtfsDate);
        gtfsTitre.setText("GTFS du " + SIMPLE_DATE_FORMAT.format(file.getDate()));
        new TacheAvecProgressDialog(this, getResources().getString(R.string.loadingInfo)) {

            private GtfsFeedInfo info = null;

            @Override
            protected void myDoBackground() throws KeolisReseauException {
                List<GtfsFeedInfo> infos = new Gtfs().getFeedInfo(file);
                if (infos != null && !infos.isEmpty()) {
                    info = infos.get(0);
                }
            }

            @Override
            protected void onPostExecute(Void result) {
                if (info != null) {
                    gtfsInfo.setText(info.getVersion());
                    gtfsDate.setText("Du " + SIMPLE_DATE_FORMAT.format(info.getStartDate()) + " au " + SIMPLE_DATE_FORMAT.format(info.getEndDate()));
                }
                super.onPostExecute(result);
            }
        }.execute((Void) null);
    }
}
