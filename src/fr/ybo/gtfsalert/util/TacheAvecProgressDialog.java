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
package fr.ybo.gtfsalert.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import fr.ybo.gtfsalert.R;
import fr.ybo.opendata.rennes.KeolisReseauException;

public abstract class TacheAvecProgressDialog extends AsyncTask<Void, Void, Void> {

	private String message;

	private ProgressDialog myProgressDialog;
	private Context context;

	public TacheAvecProgressDialog(Context context, String message) {
		this.message = message;
		this.context = context;
	}

	private boolean erreur = false;

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		try {
			myProgressDialog = ProgressDialog.show(context, "", message, true);
		} catch (Exception ignore) {

		}
	}

	protected abstract void myDoBackground() throws KeolisReseauException;

	protected Void doInBackground(Void... params) {
		try {
			myDoBackground();
		} catch (KeolisReseauException erreurReseau) {
            erreurReseau.printStackTrace();
			erreur = true;
		}
		return null;
	}

    @Override
	protected void onPostExecute(Void result) {
		try {
			myProgressDialog.dismiss();
		} catch (IllegalArgumentException ignore) {
		}
		if (erreur) {
			Toast.makeText(context, context.getString(R.string.erreurReseau), Toast.LENGTH_LONG).show();
		}
		super.onPostExecute(result);
	}
}
