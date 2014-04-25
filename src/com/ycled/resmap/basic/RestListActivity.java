package com.ycled.resmap.basic;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ycled.resmap.R;
import com.ycled.resmap.api.GooglePlaceApiHelper;
import com.ycled.resmap.api.SearchRestaurantsTask;
import com.ycled.resmap.listener.SearchRestaurantsTaskListener;
import com.ycled.resmap.model.Restaurant;

public class RestListActivity extends ListActivity implements
		SearchRestaurantsTaskListener {

	private static final String TAG = "MapActivity";

	private SearchRestaurantsTask mSearchRestTask;
	private ArrayList<Restaurant> mRestaurants;

	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		mSearchRestTask = new SearchRestaurantsTask(this);

		// search the nearby place
		searchNearbyRestaurants();

		
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		String item = (String) getListAdapter().getItem(position);
		//Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
		// go to detail page
		Intent intent = new Intent(this, RestDetailActivity.class);
		intent.putExtra("RestName", item);
		startActivity(intent);
		
	}

	
	private String[] getRestsNames(ArrayList<Restaurant> restList) {
		String[] nameList = new String[restList.size()];
		
		for(int i=0; i<restList.size(); i++){
			nameList[i] = restList.get(i).getName();
		}
		return nameList;
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
	 * This method is called after completion of asynctask
	 */
	@Override
	public void onTaskComplete(ArrayList<Restaurant> list) {
		
		mRestaurants = list;

		//update the UI after fetch the list		
		if (mRestaurants == null || mRestaurants.isEmpty()) {
			Log.d(TAG, "mRestaurants rst=>null");
		} else {
			
			String[] restNameList = getRestsNames(mRestaurants);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					R.layout.rowlayout, R.id.label, restNameList);
			setListAdapter(adapter);
		}

	}

}