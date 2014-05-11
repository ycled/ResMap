package com.ycled.resmap.model;

import java.util.ArrayList;

import android.util.Log;

@SuppressWarnings("serial")
public class NearbyPlaceList extends ArrayList<Restaurant> {
	
	private static final String TAG = "NearbyPlaceList";
	
	public NearbyPlaceList () {		
		super();
	}
	
	/**
	 * return name list of the places
	 */
	public String[] getPlaceNameList() {

		Log.d(TAG, "NearbyPlaceList this.placeList=>" + super.size());
		
		
		String[] nameList = new String[super.size()];

		for (int i = 0; i < super.size(); i++) {
			nameList[i] = super.get(i).getName();
			Log.d(TAG, "NearbyPlaceList nameList=>" + nameList[i]);
		}
		
		return nameList;
	}
		
}
