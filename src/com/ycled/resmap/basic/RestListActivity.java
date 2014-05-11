package com.ycled.resmap.basic;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.ycled.resmap.R;
import com.ycled.resmap.api.GooglePlaceApiHelper;
import com.ycled.resmap.api.PlaceAutoCompleteTask;
import com.ycled.resmap.api.PlaceNearbySearchTask;
import com.ycled.resmap.listener.PlaceAutoCompleteTaskListener;
import com.ycled.resmap.listener.PlaceNearbySearchTaskListener;
import com.ycled.resmap.model.AutoCompletePlaceList;
import com.ycled.resmap.model.NearbyPlaceList;
import com.ycled.resmap.util.GPSManager;

public class RestListActivity extends Activity implements
		PlaceNearbySearchTaskListener, PlaceAutoCompleteTaskListener {

	private static final String TAG = "RestListActivity";

	private ListView placeListView;
	private AutoCompleteTextView inputSearchTestView;
	private GPSManager mGPSManager;



	// nearby search place task
	private PlaceNearbySearchTask mSearchRestTask;
	private NearbyPlaceList mNearbyPlaceList;
	
	// place auto complete task
	private PlaceAutoCompleteTask mPlaceAutoCompleteTask;
	private AutoCompletePlaceList mAutoCompletePlaceList;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);

		mSearchRestTask = new PlaceNearbySearchTask(this);

		// GPS: current location
		mGPSManager = new GPSManager(RestListActivity.this);
		
		// list view
		placeListView = (ListView) findViewById(R.id.list_view);
		// auto complete search bar
		inputSearchTestView = (AutoCompleteTextView) findViewById(R.id.autoCompleteInputSearch);
		inputSearchTestView.setThreshold(3);		
		
		/*
		inputSearchTestView.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {				
				mPlaceAutoCompleteTask = new PlaceAutoCompleteTask(RestListActivity.this);		
				
				
				mPlaceAutoCompleteTask.execute(s.toString());
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub				
			}
		});	
		*/
		
		
		//TODO:: current test GPS
		Button button= (Button) findViewById(R.id.btn_search);
		button.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		    	// search the nearby place
				 searchNearbyRestaurants();
		    }
		});

		
		
		// row listener
		placeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

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



	/**
	 * TODO:: search the nearby place
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
	public void onPlaceNearbySearchTaskComplete(NearbyPlaceList result) {
		//Log.d(TAG, "NearbyPlaceList rst=>" + result.size());
		
		
		mNearbyPlaceList = result;
		
		Log.d(TAG, "mNearbyPlaceList=>" + mNearbyPlaceList.size());

		// update the UI after fetch the list
		if (mNearbyPlaceList == null || mNearbyPlaceList.isEmpty()) {
			Log.d(TAG, "mNearbyPlaceList rst=>null");
		
		} else {
			
			// Adding items to list view
			String[] nameList = mNearbyPlaceList.getPlaceNameList();
			
			Log.d(TAG, "NearbyPlaceList nameList=>" + nameList.length);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item,
					R.id.label, nameList);
			placeListView.setAdapter(adapter);
		}

	}
	
	
	
	/**
	 * This method is called after completion of AutoCompletePlace asynctask
	 */
	@Override
	public void onPlaceAutoCompleteTaskComplete(AutoCompletePlaceList result) {
		
		mAutoCompletePlaceList = result;

		// update the UI after fetch the list
		if (mAutoCompletePlaceList == null || mAutoCompletePlaceList.isEmpty()) {
			Log.d(TAG, "mAutoCompletePlaceList rst=>null");
		
		} else {
			
			// update auto complete input text view UI
			String[] placeList = mAutoCompletePlaceList.getPlaceDescriptionList();
			
			ArrayAdapter<String> adapter =  new ArrayAdapter<String>(getBaseContext(),
						android.R.layout.simple_dropdown_item_1line,
						placeList);
			 
			inputSearchTestView.setAdapter(adapter);
		
		}		
	}

}