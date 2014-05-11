package com.ycled.resmap.basic;

import java.util.ArrayList;

import android.location.Location;
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
import com.ycled.resmap.api.PlaceNearbySearchTask;
import com.ycled.resmap.model.Restaurant;

public class MapActivity extends FragmentActivity{

	private static final String TAG = "MapActivity";

	// private static final String SYDNEY_LOCATION = "-33.867, 151.206";

	private PlaceNearbySearchTask mSearchRestTask;
	private GoogleMap mMap;
	private ArrayList<Restaurant> mRestaurants;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_fragment);

		mSearchRestTask = new PlaceNearbySearchTask(this);

		setUpMapIfNeeded();

		// search the nearby place
		searchNearbyRestaurants();
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

	}

	/**
	 * //TODO:: search the nearby place
	 */
	private void searchNearbyRestaurants() {

		Location mCenter = new Location("-33.867, 151.206");
		int radius = 1000;
		boolean sensor = false;

		String searchUrl = GooglePlaceApiHelper.formatGooglePlaceApiSearchUrl(
				mCenter, radius, sensor);
		if (searchUrl != null) {
			Log.d(TAG, "searchUrl=>" + searchUrl);
			mSearchRestTask.execute(searchUrl);
		} else {
			Log.d(TAG, "searchUrl=>" + null);
		}

	}

	


	/**
	 * add marker on the map to show the location of searched result
	 */
	private void addRestMarkerOnMap() {

		for (Restaurant mRest : mRestaurants) {

			String mTitle = mRest.getName();
			String mAddress = mRest.getAddress();
			LatLng mLatLng = mRest.getLocation();

			Log.d(TAG, "marker, loc=>" + mLatLng.toString());

			mMap.addMarker(new MarkerOptions().title(mTitle).snippet(mAddress)
					.position(mLatLng));
		}

	}
}
