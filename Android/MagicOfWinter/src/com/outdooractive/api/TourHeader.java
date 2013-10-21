package com.outdooractive.api;

import org.json.JSONObject;

public class TourHeader {

  private int id;
  private String title;

  public TourHeader(JSONObject json) {
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
