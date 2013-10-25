package com.outdooractive.api;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TourList {

  ArrayList<TourHeader> tours;

  public TourList(String jsonString) {
    JSONObject json;
    try {
      json = new JSONObject(jsonString);
      this.tours = new ArrayList<TourHeader>();
      JSONArray tourArray = json.optJSONArray("tour");
      if (tourArray != null) {
        for (int i = 0; i < tourArray.length(); i++) {
          JSONObject tour = tourArray.optJSONObject(i);
          this.tours.add(new TourHeader(tour));
        }
      }
    } catch (JSONException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public ArrayList<TourHeader> getTours() {
    return this.tours;
  }
}
