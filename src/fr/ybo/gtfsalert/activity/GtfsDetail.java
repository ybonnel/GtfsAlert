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
import fr.ybo.gtfsalert.database.modele.GtfsInfos;

import java.text.SimpleDateFormat;

public class GtfsDetail extends Activity {

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gtfsdetail);
        GtfsInfos info = (GtfsInfos) getIntent().getSerializableExtra("info");
        TextView gtfsTitre = (TextView) findViewById(R.id.gtfsTitre);
        TextView gtfsInfo = (TextView) findViewById(R.id.gtfsInfo);
        TextView gtfsDate = (TextView) findViewById(R.id.gtfsDate);
        gtfsTitre.setText("GTFS du " + SIMPLE_DATE_FORMAT.format(info.getDateGtfs()));
        gtfsInfo.setText(info.getInfo());
        String startDate = "?";
        if (info.getStartDate() != null){
            startDate = SIMPLE_DATE_FORMAT.format(info.getStartDate());
        }
        String endDate = "?";
        if (info.getEndDate() != null) {
            endDate = SIMPLE_DATE_FORMAT.format(info.getEndDate());
        }
        gtfsDate.setText("Du " + startDate + " au " + endDate);
    }
}
