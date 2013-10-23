package com.outdooractive.api

import org.json.JSONObject

class TourHeader(json: JSONObject) {
  def id : Int = json.optInt("id", 0);
  def title : String = json.optString("title", "no title");
  override def toString : String = title
}
