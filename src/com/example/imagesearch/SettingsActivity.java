package com.example.imagesearch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

public class SettingsActivity extends Activity {
	Spinner spImgSz;
	Spinner spImgColor;
	Spinner spImgType;
	EditText etSiteFilter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		String imgSz = (String) getIntent().getStringExtra("imgSz");
		String imgType = (String) getIntent().getStringExtra("imgType");
		String imgColor = (String) getIntent().getStringExtra("imgColor");
		String siteFilter = (String) getIntent().getStringExtra("siteFilter");

		spImgSz = (Spinner) findViewById(R.id.spImgSz);
		setSpinnerToValue(spImgSz, imgSz);
		spImgColor = (Spinner) findViewById(R.id.spImgColor);
		setSpinnerToValue(spImgColor, imgColor);
		spImgType = (Spinner) findViewById(R.id.spImgType);
		setSpinnerToValue(spImgType, imgType);
		etSiteFilter = (EditText) findViewById(R.id.etSiteFilter);
		etSiteFilter.setText(siteFilter);
	}
	
	public void setSpinnerToValue(Spinner spinner, String value) {
		for (int i = 0; i < spinner.getCount(); i++) {
			if (spinner.getItemAtPosition(i).equals(value)) {
				spinner.setSelection(i);
				break;
			}
		}
	}
	
	public void onSaveClick(View v) {
		String imgSz = spImgSz.getSelectedItem().toString();
		String imgColor = spImgColor.getSelectedItem().toString();
		String imgType = spImgType.getSelectedItem().toString();
		String siteFilter = etSiteFilter.getText().toString();
		
		Intent i = new Intent();
		i.putExtra("imgSz", imgSz);
		i.putExtra("imgColor", imgColor);
		i.putExtra("imgType", imgType);
		i.putExtra("siteFilter", siteFilter);
		setResult(RESULT_OK, i);
		finish();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}

}
