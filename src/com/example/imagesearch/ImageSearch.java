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
	}

	public void setupViews() {
		etQuery = (EditText) findViewById(R.id.etQuery);
		gvResults = (GridView) findViewById(R.id.gvResults);
		btnSearch = (Button) findViewById(R.id.btnSearch);
	}
	
	public void onImageSearch(View v) {
		String query = etQuery.getText().toString();
		AsyncHttpClient client = new AsyncHttpClient();
		final String googleImgSearchUrl =
				"https://ajax.googleapis.com/ajax/services/search/images?" +
				"rsz=8&start=0&v=1.0&q=" + Uri.encode(query);
		client.get(googleImgSearchUrl,
				new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {
				JSONArray jsonImageResults = null;
				try {
					jsonImageResults = response.getJSONObject("responseData").getJSONArray("results");
					imageResults.clear();
					imageAdapter.addAll(
							ImageResult.fromJsonArray(jsonImageResults));
					Log.d("DEBUG", imageResults.toString());
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
