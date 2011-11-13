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
package fr.ybo.gtfsalert.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import fr.ybo.gtfsalert.R;
import fr.ybo.opendata.rennes.modele.bus.GtfsFile;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Adapteur pour les GtfsFiles..
 */
public class GtfsAdapter extends ArrayAdapter<GtfsFile> {

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    private final List<GtfsFile> files;
    private final LayoutInflater inflater;

    public GtfsAdapter(Context context, List<GtfsFile> objects) {
        super(context, R.layout.gtfsfile, objects);
        files = objects;
        inflater = LayoutInflater.from(getContext());
    }

    private static class ViewHolder {
        private TextView gtfsTitre;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View convertView1 = convertView;
        GtfsFile file = files.get(position);
        GtfsAdapter.ViewHolder holder;
        if (convertView1 == null) {
            convertView1 = inflater.inflate(R.layout.gtfsfile, null);
            holder = new GtfsAdapter.ViewHolder();
            holder.gtfsTitre = (TextView) convertView1.findViewById(R.id.gtfsTitre);
            convertView1.setTag(holder);
        } else {
            holder = (GtfsAdapter.ViewHolder) convertView1.getTag();
        }

        holder.gtfsTitre.setText("GTFS du " + SIMPLE_DATE_FORMAT.format(file.getDate()));
        return convertView1;
    }
}
