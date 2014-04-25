package com.ycled.resmap.basic;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import com.ycled.resmap.R;

public class RestDetailActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		
		Bundle extras = getIntent().getExtras();
		String restName = extras.getString("RestName");
		
		TextView nameTextView = (TextView)findViewById(R.id.textView1);
		nameTextView.setText(restName);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
}
