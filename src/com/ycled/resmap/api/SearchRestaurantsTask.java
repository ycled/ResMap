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

import com.ycled.resmap.listener.SearchRestaurantsTaskListener;
import com.ycled.resmap.model.Restaurant;

public class SearchRestaurantsTask extends AsyncTask<String, Void, String> {

	private static final String TAG = "SearchRestaurantsTask";

	private Activity activity;
	private SearchRestaurantsTaskListener listener;

	public SearchRestaurantsTask(Activity activity) {
		this.activity = activity;
		this.listener = (SearchRestaurantsTaskListener) activity;
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
		Log.d(TAG, "search rst=>" + result);

		// decode json
		ArrayList<Restaurant> mRestaurants = GooglePlaceApiHelper
				.getRestaurantsFromJSON(result);

		// call method from here
		listener.onTaskComplete(mRestaurants);
	}
}
