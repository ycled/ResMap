package com.ycled.resmap.api;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.location.Location;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.ycled.resmap.model.Restaurant;

public class GooglePlaceApiHelper {

	private static final String TAG = "GooglePlaceApiHelper";
	private static final String GOOGLE_API_KEY = "AIzaSyDOZhFs0gzKKDf77LtQNOJz7BjTbgY5wnY";
	private static final String GOOGLE_PLACE_API_BASE_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";
	private static final String GOOGLE_PLACE_API_TYPES = "food|restaurant|cafe";

	/**
	 * Format the URL to access the Google place API.
	 * 
	 * @param GoogleAPIKey
	 * @param center	=>	the center of the search
	 * @param radius	=>  the radius of the search circle
	 * @param sensor	=>	whether the result is read from a sensor
	 * @return the formatted String
	 */
	public static String formatGooglePlaceApiSearchUrl(Location center,
			int radius, boolean sensor) {

		// String url = GOOGLE_PLACE_API_BASE_URL + "key=" + GOOGLE_API_KEY
		// + "&location=" + "55.864237,-4.251805999999988"
		// + "&types=" + GOOGLE_PLACE_API_TYPES
		// + "&radius=" + String.valueOf(radius)
		// + "&sensor=" + String.valueOf(sensor);
		//
		// try {
		// return URLEncoder.encode(url,"UTF-8");
		// } catch (UnsupportedEncodingException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
		// return null;
		//
		// TODO:
		return GOOGLE_PLACE_API_BASE_URL
				+ "location=-33.867,151.206"
				+ "&radius=500"
				+ "&types=restaurant"
				+ "&sensor=false"
				+ "&key=" + GOOGLE_API_KEY;

	}

	public static ArrayList<Restaurant> getRestaurantsFromJSON(String str) {
		ArrayList<Restaurant> mRestaurants = new ArrayList<Restaurant>();

		try {
			JSONObject rstJsonObj = new JSONObject(str);
			JSONArray resList = rstJsonObj.getJSONArray("results");
			
			if(resList != null){
				//TODO::size, time 
				for(int i=0; i<5; i++){
					Restaurant mRestaurant = new Restaurant();
					JSONObject restObj = resList.getJSONObject(i);
					//TODO::set all attr
					mRestaurant.setName(restObj.getString("name"));
					mRestaurant.setAddress(restObj.getString("vicinity"));
					
					JSONObject geoObj = restObj.getJSONObject("geometry");
					JSONObject locObj = geoObj.getJSONObject("location");
					double lat = locObj.getDouble("lat");
					double lng = locObj.getDouble("lng");
					LatLng location = new LatLng(lat, lng); 
					mRestaurant.setLocation(location);
					
					
					mRestaurants.add(mRestaurant);
					
				}
			}
			
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
		
		//Log.d(TAG, "mRestaurants.get(1).getname=>" + mRestaurants.get(1).getName());

		return mRestaurants;
	}

}
