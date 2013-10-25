package com.outdooractive.api;

import org.json.JSONObject
import android.text.Html

class Tour(jsonString: String) {
  val json = new JSONObject(jsonString)
  val tours = json.optJSONArray("tour");
  val tour = tours.optJSONObject(0);
  val metaData = Option(tour.optJSONObject("meta"));             // optional
  val sourceObject =  metaData map { _.optJSONObject("source") } // optional
  val primaryImage = Option(tour.optJSONObject("primaryImage")); // optional
  val start = tour.optJSONObject("startingPoint");               // mandatory

  def title: String = tour.optString("title", "no title");
  def longText: String = Html.fromHtml(tour.optString("longText")).toString();
  def geometry: String = tour.optString("geometry");
  def author: String = (metaData map { _.optString("author") }) getOrElse "";
  def source: String = sourceObject map { _.optString("name")  } getOrElse "";
  def imageId: Int = primaryImage map { _.optInt("id") } getOrElse 0;
  def startingPoint: String = start.optString("lon", "") + "," + start.optString("lat", "");
  def isWinterTour: Boolean = tour.optBoolean("winterActivity", false);
}
