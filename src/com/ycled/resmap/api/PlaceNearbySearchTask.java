package com.ycled.resmap.api;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.ycled.resmap.listener.PlaceNearbySearchTaskListener;
import com.ycled.resmap.model.NearbyPlaceList;
import com.ycled.resmap.model.Restaurant;

public class PlaceNearbySearchTask extends AsyncTask<String, Void, String> {

	private static final String TAG = "PlaceNearbySearchTask";

	private Activity activity;
	private PlaceNearbySearchTaskListener listener;

	public PlaceNearbySearchTask(Activity activity) {
		this.activity = activity;
		this.listener = (PlaceNearbySearchTaskListener) activity;
	}

	@Override
	protected String doInBackground(String... urls) {
		String response = "";

		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(urls[0]);
		try {
			HttpResponse execute = client.execute(httpGet);
			InputStream content = execute.getEntity().getContent();

			BufferedReader buffer = new BufferedReader(new InputStreamReader(
					content));
			String s = "";
			while ((s = buffer.readLine()) != null) {
				response += s;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	@Override
	protected void onPostExecute(String result) {
		Log.d(TAG, "resultt=>" + result);

		// decode json
		NearbyPlaceList list = GooglePlaceApiHelper.getPlacesFromJSON(result);
		listener.onPlaceNearbySearchTaskComplete(list);
	}
}
