package com.example.imagesearch;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ImageResult implements Serializable {
	private static final long serialVersionUID = 1L;
	private String fullUrl;
	private String thumbUrl;
	
	ImageResult(JSONObject json) {
		try {
			this.fullUrl = json.getString("url");
			this.thumbUrl = json.getString("tbUrl");
		} catch (JSONException e) {
			this.fullUrl = null;
			this.thumbUrl = null;
		}
	}
	
	public static ArrayList<ImageResult> fromJsonArray(JSONArray jsonImageResults) {
		ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
		for (int i = 0; i < jsonImageResults.length(); i++) {
			try {
				imageResults.add(new ImageResult(jsonImageResults.getJSONObject(i)));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return imageResults;
	}

	public String getFullUrl() {
		return fullUrl;
	}

	public String getThumbUrl() {
		return thumbUrl;
	}

	public String toString() {
		return thumbUrl;
	}
}
