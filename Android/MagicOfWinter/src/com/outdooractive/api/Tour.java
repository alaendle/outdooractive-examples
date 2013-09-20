package com.outdooractive.api;

import org.json.JSONArray;
import org.json.JSONObject;

import android.text.Html;

public class Tour {

	private String title;
	private String longText;
	private String author;
	private String source;
	private String geometry;
	private int imageId;
	private String startingPoint;
	private boolean isWinterTour;

	public Tour(JSONObject json) {
		JSONArray tours = json.optJSONArray("tour");
		JSONObject tour = tours.optJSONObject(0);
		this.title = tour.optString("title", "no title");
		this.longText = tour.optString("longText");
		this.geometry = tour.optString("geometry");

		JSONObject metaData = tour.optJSONObject("meta");
		if (metaData != null) {
			this.author = metaData.optString("author");
			JSONObject sourceObject = metaData.optJSONObject("source");
			if (sourceObject != null) {
				this.source = sourceObject.optString("name");
			}
		}

		JSONObject primaryImage = tour.optJSONObject("primaryImage");
		this.imageId = primaryImage != null ? primaryImage.optInt("id") : 0;

		JSONObject start = tour.optJSONObject("startingPoint");
		this.startingPoint = start.optString("lon", "") + ","
				+ start.optString("lat", "");

		this.isWinterTour = tour.optBoolean("winterActivity", false);
	}

	public String getTitle() {
		return this.title;
	}

	public String getStartingPoint() {
		return this.startingPoint;
	}

	public String getGeometry() {
		return this.geometry;
	}

	public int getImageId() {
		return this.imageId;
	}

	public String getLongText() {
		return Html.fromHtml(this.longText).toString();
	}

	public String getAuthor() {
		return this.author != null ? this.author : "";
	}

	public String getSource() {
		return this.source != null ? this.source : "";
	}

	public boolean isWinterTour() {
		return this.isWinterTour;
	}
}
