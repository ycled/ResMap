package com.ycled.resmap.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.location.Location;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.ycled.resmap.listener.PlaceNearbySearchTaskListener;
import com.ycled.resmap.model.AutoCompletePlace;
import com.ycled.resmap.model.AutoCompletePlaceList;
import com.ycled.resmap.model.NearbyPlaceList;
import com.ycled.resmap.model.Restaurant;

public class GooglePlaceApiHelper {

	private static final String TAG = "GooglePlaceApiHelper";
	private static final String GOOGLE_API_KEY = "AIzaSyA-KpnCbqazJ8hAIZ-zXy8Kr0X8Qqv7U7Y";
	private static final String GOOGLE_PLACE_API_BASE_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";
	//private static final String GOOGLE_PLACE_API_TYPES = "food|restaurant|cafe";
	private static final String GOOGLE_PLACE_API_TYPES = "food";
	
	private static final String GOOGLE_PLACE_AUTO_COMPLETE_API_BASE_URI = "https://maps.googleapis.com/maps/api/place/autocomplete/json?";
	
	PlaceNearbySearchTaskListener mGetRestaurantListListener;
	
	
	public void setRestaurantListFromGooglePlaceAPIListener(PlaceNearbySearchTaskListener listener){
		mGetRestaurantListListener = listener;
	}

	/**
	 * Format the URL to access the Google place API.
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
		return GOOGLE_PLACE_API_BASE_URL + "location=-33.867,151.206"
				+ "&radius=500" + "&types=restaurant" + "&sensor=false"
				+ "&key=" + GOOGLE_API_KEY;
		


	}
	
	public static String formatGooglePlaceApiSearchUrl(double lat, double lng,
			int radius, boolean sensor) {
	
		return GOOGLE_PLACE_API_BASE_URL + 
				"location=" + String.valueOf(lat) + "," + String.valueOf(lng)
				+ "&radius=" + String.valueOf(radius)
				+ "&types=" + GOOGLE_PLACE_API_TYPES 
				+ "&sensor=" + String.valueOf(sensor)
				+ "&key=" + GOOGLE_API_KEY;

	}
	
	public static String formatGooglePlaseAutoCompleteURL(String str){
		
		String input="";
		
		try {
			input = "input=" + URLEncoder.encode(str, "utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}		
		
		
		String url = GOOGLE_PLACE_AUTO_COMPLETE_API_BASE_URI
					+ input
					+ "&types=geocode"
					+ "&sensor=false"
					+ "&key=" + GOOGLE_API_KEY;
		
		return url;
	}
	
	
	/**
	 * Parse Google Places in JSON format
	 */
	
	/*
	public static ArrayList<Restaurant> getRestaurantsFromJSON(String str) {
		ArrayList<Restaurant> mRestaurants = new ArrayList<Restaurant>();

		try {
			JSONObject rstJsonObj = new JSONObject(str);
			JSONArray resList = rstJsonObj.getJSONArray("results");

			if (resList != null) {
				// TODO::size, time
				for (int i = 0; i < 5; i++) {
					Restaurant mRestaurant = new Restaurant();
					JSONObject restObj = resList.getJSONObject(i);
					// TODO::set all attr
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

		// Log.d(TAG, "mRestaurants.get(1).getname=>" +
		// mRestaurants.get(1).getName());

		return mRestaurants;
	}
*/
	
	/**
	 * Parse Google Places in JSON format
	 */
	public static NearbyPlaceList getPlacesFromJSON(String str) {
		
		NearbyPlaceList list = new NearbyPlaceList();

		try {
			JSONObject obj = new JSONObject(str);
			JSONArray arr = obj.getJSONArray("results");

			if (arr != null) {
				// TODO:: Limit to the first 5 result
				// use task to parson json
				
				for (int i = 0; i < 5; i++) {
					Restaurant mRestaurant = new Restaurant();
					JSONObject restObj = arr.getJSONObject(i);
					// TODO::set all attr
					mRestaurant.setName(restObj.getString("name"));
					mRestaurant.setAddress(restObj.getString("vicinity"));

					JSONObject geoObj = restObj.getJSONObject("geometry");
					JSONObject locObj = geoObj.getJSONObject("location");
					double lat = locObj.getDouble("lat");
					double lng = locObj.getDouble("lng");
					LatLng location = new LatLng(lat, lng);
					mRestaurant.setLocation(location);

					list.add(mRestaurant);

				}
			}

		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
	
		return list;
	}
	
	
	
	
	
	
	
	/**
	 * Parse auto complete google place in JSON format
	 */
	public static AutoCompletePlaceList getAutoCompletePlaceFromJSON(String str) {
		
		AutoCompletePlaceList list = new AutoCompletePlaceList();
		
		
		try {
			JSONObject placeJsonObj = new JSONObject(str);
			JSONArray placeJsonArray = placeJsonObj.getJSONArray("predictions");

			if (placeJsonArray != null) {

				for (int i = 0; i < placeJsonArray.length(); i++) {
					
					// create place instance
					AutoCompletePlace mPlace = new AutoCompletePlace();
					JSONObject placeObj = placeJsonArray.getJSONObject(i);
					
					mPlace.setDescription(placeObj.getString("description"));
					mPlace.setId(placeObj.getString("id"));
					mPlace.setReference(placeObj.getString("reference"));
					
					// add to list
					list.add(mPlace);
				}
			}

		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
		
		return list;
	}


}
