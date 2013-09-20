package com.outdooractive.api;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class TourList {

	ArrayList<TourHeader> tours;

	public TourList(JSONObject json) {
		this.tours = new ArrayList<TourHeader>();
		JSONArray tourArray = json.optJSONArray("tour");
		if (tourArray != null) {
			for (int i = 0; i < tourArray.length(); i++) {
				JSONObject tour = tourArray.optJSONObject(i);
				this.tours.add(new TourHeader(tour));
			}
		}
	}

	public ArrayList<TourHeader> getTours() {
		return this.tours;
	}

	public class TourHeader {

		private int id;
		private String title;

		private TourHeader(JSONObject json) {
			this.id = json.optInt("id", 0);
			this.title = json.optString("title", "no title");
		}

		public int getId() {
			return this.id;
		}

		public String getTitle() {
			return this.title;
		}

		@Override
		public String toString() {
			return this.title;
		}
	}
}
