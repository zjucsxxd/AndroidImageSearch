package com.example.imagesearch;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class ImageSearch extends Activity {
	EditText etQuery;
	GridView gvResults;
	Button btnSearch;
	ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
	ImageResultArrayAdapter imageAdapter;
	
	String query;

	String imgSz="";
	String imgType="";
	String imgColor="";
	String siteFilter="";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_search);
		setupViews();
		imageAdapter = new ImageResultArrayAdapter(this, imageResults);
		gvResults.setAdapter(imageAdapter);
		gvResults.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View parent, int position, long arg3) {
				Intent i = new Intent(getApplicationContext(), ImageDisplayActivity.class);
				ImageResult imageResult = imageResults.get(position);
				i.putExtra("result", imageResult);
				startActivity(i);
			}
		});
		gvResults.setOnScrollListener(new EndlessScroll() {
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				runImageSearch(page);
			}
		});
	}

	public void setupViews() {
		etQuery = (EditText) findViewById(R.id.etQuery);
		gvResults = (GridView) findViewById(R.id.gvResults);
		btnSearch = (Button) findViewById(R.id.btnSearch);
	}
	
	public void onSettingsClick(MenuItem v) {
		Intent i = new Intent(this, SettingsActivity.class);
		i.putExtra("imgSz", imgSz);
		i.putExtra("imgType", imgType);
		i.putExtra("imgColor", imgColor);
		i.putExtra("siteFilter", siteFilter);
		startActivityForResult(i, 1337);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent i) {
		imgSz = i.getExtras().getString("imgSz");
		imgColor = i.getExtras().getString("imgColor");
		imgType = i.getExtras().getString("imgType");
		siteFilter = i.getExtras().getString("siteFilter");
	} 
	
	public void onImageSearch(View v) {
		query = etQuery.getText().toString();
		imageResults.clear();
		runImageSearch(0);
	}
	
	public void runImageSearch(int page) {
		AsyncHttpClient client = new AsyncHttpClient();
		final String googleImgSearchUrl =
				"https://ajax.googleapis.com/ajax/services/search/images?" +
				"imgsz=" + imgSz +
				"&imgtype=" + imgType +
				"&imgcolor=" + imgColor +
				"&as_sitesearch=" + siteFilter +
				"&rsz=8" +
				"&start=" + Integer.toString(page) +
				"&v=1.0" +
				"&q=" + Uri.encode(query);
		Log.d("DEBUG", googleImgSearchUrl);
		client.get(googleImgSearchUrl,
				new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {
				JSONArray jsonImageResults = null;
				try {
					jsonImageResults = response.getJSONObject("responseData").getJSONArray("results");
					imageAdapter.addAll(
							ImageResult.fromJsonArray(jsonImageResults));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_search, menu);
		return true;
	}

}
