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

package fr.ybo.gtfsalert.database.modele;

import fr.ybo.database.annotation.Column;
import fr.ybo.database.annotation.Entity;
import fr.ybo.database.annotation.PrimaryKey;
import fr.ybo.gtfsalert.application.GtfsAlertApplication;
import fr.ybo.opendata.rennes.Gtfs;
import fr.ybo.opendata.rennes.KeolisReseauException;
import fr.ybo.opendata.rennes.modele.bus.GtfsFeedInfo;
import fr.ybo.opendata.rennes.modele.bus.GtfsFile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class GtfsInfos implements Serializable {
    @PrimaryKey
    @Column( type = Column.TypeColumn.DATE)
    private Date dateGtfs;
    @Column
    private String info;
    @Column( type = Column.TypeColumn.DATE)
    private Date startDate;
    @Column( type = Column.TypeColumn.DATE)
    private Date endDate;

    public Date getDateGtfs() {
        return dateGtfs;
    }

    public String getInfo() {
        return info;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public static List<GtfsInfos> getInfos() throws KeolisReseauException {
        List<GtfsInfos> infos = GtfsAlertApplication.getDatabase().selectAll(GtfsInfos.class);
        if (infos.isEmpty()) {
            refreshInfos();
            infos = GtfsAlertApplication.getDatabase().selectAll(GtfsInfos.class);
        }
        return infos;
    }

    public static List<GtfsInfos> refreshInfos() throws KeolisReseauException {
        List<GtfsInfos> newGtfs = new ArrayList<GtfsInfos>();
        Gtfs gtfs = new Gtfs();
        for (GtfsFile file : gtfs.getUpdates() ) {
            GtfsInfos info = new GtfsInfos();
            info.dateGtfs = file.getDate();
            if (GtfsAlertApplication.getDatabase().selectSingle(info) == null) {
                List<GtfsFeedInfo> feedInfos = gtfs.getFeedInfo(file);
                if (feedInfos != null && !feedInfos.isEmpty()) {
                    GtfsFeedInfo feedInfo = feedInfos.get(0);
                    info.info = feedInfo.getVersion();
                    info.startDate = feedInfo.getStartDate();
                    info.endDate = feedInfo.getEndDate();
                }
                GtfsAlertApplication.getDatabase().insert(info);
                newGtfs.add(info);
            }
        }
        return newGtfs;
    }
}
