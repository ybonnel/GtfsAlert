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

package fr.ybo.gtfsalert.application;


import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import fr.ybo.gtfsalert.database.GtfsAlertDatabase;
import fr.ybo.gtfsalert.services.UpdateTimeService;

public class GtfsAlertApplication extends Application {

    private static GtfsAlertDatabase database = null;

    public static GtfsAlertDatabase getDatabase() {
        return database;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        database = new GtfsAlertDatabase(this);
        startService(new Intent(UpdateTimeService.ACTION_UPDATE));
        PackageManager pm = getPackageManager();
        pm.setComponentEnabledSetting(new ComponentName("fr.ybo.gtfsalert", ".services.UpdateTimeService"),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
    }
}