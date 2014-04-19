package com.ycled.resmap.basic;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ycled.resmap.R;
import com.ycled.resmap.api.GooglePlaceApiHelper;
import com.ycled.resmap.model.Restaurant;

public class MapActivity extends FragmentActivity {

	private static final String TAG = "MapActivity::";
	private ArrayList<Restaurant> mRestaurants;
	private static final String SYDNEY_LOCATION = "-33.867, 151.206";
	
	/**
	 * Note that this may be null if the Google Play services APK is not
	 * available.
	 */
	private GoogleMap mMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_fragment);

		setUpMapIfNeeded();
	}

	@Override
	protected void onResume() {
		super.onResume();
		setUpMapIfNeeded();
	}

	/**
	 * Sets up the map if it is possible to do so (i.e., the Google Play
	 * services APK is correctly installed) and the map has not already been
	 * instantiated.. This will ensure that we only ever call
	 * {@link #setUpMap()} once when {@link #mMap} is not null.
	 * <p>
	 * If it isn't installed {@link SupportMapFragment} (and
	 * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt
	 * for the user to install/update the Google Play services APK on their
	 * device.
	 * <p>
	 * A user can return to this FragmentActivity after following the prompt and
	 * correctly installing/updating/enabling the Google Play services. Since
	 * the FragmentActivity may not have been completely destroyed during this
	 * process (it is likely that it would only be stopped or paused),
	 * {@link #onCreate(Bundle)} may not be called again so we should call this
	 * method in {@link #onResume()} to guarantee that it will be called.
	 */
	private void setUpMapIfNeeded() {
		// Do a null check to confirm that we have not already instantiated the
		// map.
		if (mMap == null) {
			// Try to obtain the map from the SupportMapFragment.
			mMap = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map)).getMap();
			// Check if we were successful in obtaining the map.
			if (mMap != null) {
				setUpMap();
			}
		}
	}

	/**
	 * This is where we can add markers or lines, add listeners or move the
	 * camera.
	 * 
	 * In this case, just add a marker at Sydney.
	 */
	private void setUpMap() {
		// mMap.addMarker(new MarkerOptions().position(new LatLng(0,
		// 0)).title("Marker"));

		LatLng sydney = new LatLng(-33.867, 151.206);

		mMap.setMyLocationEnabled(true);
		mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));

		mMap.addMarker(new MarkerOptions().title("Sydney")
				.snippet("The most populous city in Australia.")
				.position(sydney));

		// TODO:: search the nearby place
		searchNearbyPlace();
		
		// TODO:: add marker
		addRestMarker();
	}

	/**
	 * //TODO:: 
	 * search the nearby place
	 */
	private void searchNearbyPlace() {

		Location mCenter = new Location("-33.867, 151.206");
		int radius = 1000;
		boolean sensor = false;

		String searchUrl = GooglePlaceApiHelper.formatGooglePlaceApiSearchUrl(
				mCenter, radius, sensor);
		if (searchUrl != null) {
			Log.d(TAG, "searchUrl=>" + searchUrl);
			String[] searchUrlArr = new String[] { searchUrl };

			SearchGooglePlaceTask task = new SearchGooglePlaceTask();
			task.execute(searchUrlArr);
		} else {
			Log.d(TAG, "searchUrl=>" + null);
		}

	}
	
	private void addRestMarker() {
		
		if(mRestaurants != null) {
			
			for(Restaurant mRest : mRestaurants) {
				
				
				String mTitle = mRest.getName();
				String mAddress = mRest.getAddress();
				LatLng mLatLng = mRest.getLocation();
				
				Log.d(TAG, "marker, loc=>" + mLatLng.toString());
				
				mMap.addMarker(new MarkerOptions().title(mTitle)
						.snippet(mAddress)
						.position(mLatLng));
			}
		}
		

	}

	/**
	 * 
	 * AsyncTask to send HTTP request
	 * 
	 */
	private class SearchGooglePlaceTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... urls) {
			String response = "";
			for (String url : urls) {
				DefaultHttpClient client = new DefaultHttpClient();
				HttpGet httpGet = new HttpGet(url);
				try {
					HttpResponse execute = client.execute(httpGet);
					InputStream content = execute.getEntity().getContent();

					BufferedReader buffer = new BufferedReader(
							new InputStreamReader(content));
					String s = "";
					while ((s = buffer.readLine()) != null) {
						response += s;
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return response;
		}

		@Override
		protected void onPostExecute(String result) {
			// textView.setText(result);
			Log.d(TAG, "search rst=>" + result);
			mRestaurants = GooglePlaceApiHelper.getRestaurantsFromJSON(result);
		}

	}
}
