package com.ycled.resmap.basic;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.ycled.resmap.R;
import com.ycled.resmap.api.GooglePlaceApiHelper;
import com.ycled.resmap.api.SearchRestaurantsTask;
import com.ycled.resmap.listener.SearchRestaurantsTaskListener;
import com.ycled.resmap.model.Restaurant;
import com.ycled.resmap.util.GPSManager;

public class RestListActivity extends Activity implements
		SearchRestaurantsTaskListener {

	private static final String TAG = "RestListActivity";

	private ListView lv;
	ArrayAdapter<String> adapter;
	EditText inputSearch;
	GPSManager mGPSManager;

	// ArrayList for Listview
	ArrayList<HashMap<String, String>> productList;

	// search
	private SearchRestaurantsTask mSearchRestTask;
	private ArrayList<Restaurant> mRestaurants;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);

		mSearchRestTask = new SearchRestaurantsTask(this);

		// GPS: current location
		mGPSManager = new GPSManager(RestListActivity.this);
		
		


		lv = (ListView) findViewById(R.id.list_view);
		inputSearch = (EditText) findViewById(R.id.inputSearch);
		
		Button button= (Button) findViewById(R.id.btn_search);
		button.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		    	// search the nearby place
				 searchNearbyRestaurants();
		    }
		});

		
		
		// row listener

	    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

	      @Override
	      public void onItemClick(AdapterView<?> parent, final View view,
	          int position, long id) {
	    	  
	    	  final String item = (String) parent.getItemAtPosition(position);
	        
		     Toast.makeText(RestListActivity.this, item + " selected", Toast.LENGTH_LONG).show(); 
		      // go to detail page 
		      Intent intent = new Intent(RestListActivity.this, RestDetailActivity.class);
		  	  intent.putExtra("RestName", item); startActivity(intent);
	      }

	    });

		

	}

	/*
	 * @Override protected void onListItemClick(ListView l, View v, int
	 * position, long id) { String item = (String)
	 * getListAdapter().getItem(position); //Toast.makeText(this, item +
	 * " selected", Toast.LENGTH_LONG).show(); // go to detail page Intent
	 * intent = new Intent(this, RestDetailActivity.class);
	 * intent.putExtra("RestName", item); startActivity(intent);
	 * 
	 * }
	 */

	private String[] getRestsNames(ArrayList<Restaurant> restList) {
		String[] nameList = new String[restList.size()];

		for (int i = 0; i < restList.size(); i++) {
			nameList[i] = restList.get(i).getName();
		}
		return nameList;
	}

	/**
	 * //TODO:: search the nearby place
	 */
	private void searchNearbyRestaurants() {

		//Location mCenter = new Location(mGPSManager.getLatitude() + "," + mGPSManager.getLongitude());
		int radius = 1000;
		boolean sensor = false;

		String searchUrl = GooglePlaceApiHelper.formatGooglePlaceApiSearchUrl(
				mGPSManager.getLatitude(), mGPSManager.getLongitude(), radius, sensor);
		
		
		Log.d(TAG, "searchUrl=>" + searchUrl);
		mSearchRestTask.execute(searchUrl);
		
	}

	/**
	 * This method is called after completion of asynctask
	 */
	@Override
	public void onTaskComplete(ArrayList<Restaurant> list) {

		mRestaurants = list;

		// update the UI after fetch the list
		if (mRestaurants == null || mRestaurants.isEmpty()) {
			Log.d(TAG, "mRestaurants rst=>null");
		} else {

			
//			 String[] restNameList = getRestsNames(mRestaurants);
//			 ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//			 R.layout.list_item, R.id.label, restNameList);
//			 setListAdapter(adapter);
			 
			
			// Adding items to listview
			String[] restNameList = getRestsNames(mRestaurants);
			adapter = new ArrayAdapter<String>(this, R.layout.list_item,
					R.id.label, restNameList);
			lv.setAdapter(adapter);
		}

	}

}