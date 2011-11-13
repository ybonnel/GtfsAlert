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
package fr.ybo.gtfsalert.database;

import android.content.Context;
import fr.ybo.database.DataBaseHelper;
import fr.ybo.gtfsalert.database.modele.GtfsInfos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GtfsAlertDatabase extends DataBaseHelper {

    private static final String DATABASE_NAME = "gtfs-alert.db";
    private static final int DATABASE_VERSION = 1;

    private static final List<Class<?>> LIST_CLASSES_DATABASE = new ArrayList<Class<?>>();
    static {
        LIST_CLASSES_DATABASE.add(GtfsInfos.class);
    }

    public GtfsAlertDatabase(Context context) {
        super(context, LIST_CLASSES_DATABASE, DATABASE_NAME, DATABASE_VERSION);
    }

    private Map<Integer, UpgradeDatabase> mapUpgrades;

    protected Map<Integer, UpgradeDatabase> getUpgrades() {
        if (mapUpgrades == null) {
            mapUpgrades = new HashMap<Integer, UpgradeDatabase>();
        }
        return mapUpgrades;
    }
}
