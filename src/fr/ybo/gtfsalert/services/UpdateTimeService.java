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

package fr.ybo.gtfsalert.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import fr.ybo.gtfsalert.R;
import fr.ybo.gtfsalert.activity.GtfsDetail;
import fr.ybo.gtfsalert.database.modele.GtfsInfos;
import fr.ybo.opendata.rennes.KeolisReseauException;

import java.util.Calendar;

public class UpdateTimeService extends Service {

    /**
     * Used by the AppWidgetProvider to notify the Service that the views need
     * to be updated and redrawn.
     */
    public static final String ACTION_UPDATE = "fr.ybo.gtfsalert.action.UPDATE";

    private final static IntentFilter sIntentFilter;

    static {
        sIntentFilter = new IntentFilter();
        sIntentFilter.addAction(Intent.ACTION_TIME_TICK);
        sIntentFilter.addAction(Intent.ACTION_TIMEZONE_CHANGED);
        sIntentFilter.addAction(Intent.ACTION_TIME_CHANGED);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate() {
        super.onCreate();
        registerReceiver(mTimeChangedReceiver, sIntentFilter);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mTimeChangedReceiver);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        if (intent != null && ACTION_UPDATE.equals(intent.getAction())) {
            update();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    /**
     * Updates and redraws the Widget.
     */
    private void update() {
        Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.MINUTE) == 0) {
            updateNotifs();
        }
    }

    private void updateNotifs() {
        try {
            for (GtfsInfos info : GtfsInfos.refreshInfos()) {
                createNotification(info);
            }
        } catch (KeolisReseauException ignore) {

        }
    }


    private void createNotification(GtfsInfos info) {
        String texte = getResources().getString(R.string.notifText);
        Intent notificationIntent = new Intent(this, GtfsDetail.class);
        notificationIntent.putExtra("info", info);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        android.app.Notification notif = new android.app.Notification(R.drawable.icon, texte, System.currentTimeMillis());
        notif.setLatestEventInfo(this, texte, texte, contentIntent);
        notif.defaults |= android.app.Notification.DEFAULT_ALL;
        notif.flags |= android.app.Notification.FLAG_AUTO_CANCEL;
        NotificationManager mNotificationManager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(info.getDateGtfs().getDay() + info.getDateGtfs().getMonth() * 100, notif);
    }

    /**
     * Automatically registered when the Service is created, and unregistered
     * when the Service is destroyed.
     */
    private final BroadcastReceiver mTimeChangedReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            update();
        }
    };

}
