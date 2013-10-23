package com.outdooractive.api;

import org.json.JSONObject
import android.text.Html

class Tour(json: JSONObject) {
  val tours = json.optJSONArray("tour");
  val tour = tours.optJSONObject(0);

  def title: String = tour.optString("title", "no title");
  def longText: String = Html.fromHtml(tour.optString("longText")).toString();
  def geometry: String = tour.optString("geometry");

  val metaData = tour.optJSONObject("meta");
  def author: String = if (metaData != null) metaData.optString("author") else "";

  val sourceObject = if (metaData != null) metaData.optJSONObject("source") else null;
  def source: String = if (sourceObject != null) sourceObject.optString("name") else "";

  val primaryImage = tour.optJSONObject("primaryImage");
  def imageId: Int = if (primaryImage != null) primaryImage.optInt("id") else 0;

  val start = tour.optJSONObject("startingPoint");
  def startingPoint: String = start.optString("lon", "") + "," + start.optString("lat", "");
  def isWinterTour: Boolean = tour.optBoolean("winterActivity", false);
}
