package com.ycled.resmap.basic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.ycled.resmap.R;
import com.ycled.resmap.util.GPSManager;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_gotomap) {
			startActivity(new Intent(this, MapActivity.class));
			return true;
		} else if (id == R.id.action_gotolist) {
			startActivity(new Intent(this, RestListActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
