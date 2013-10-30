package com.outdooractive.api;

import org.json.JSONObject
import android.text.Html

class Tour(jsonString: String) {
  private val json = new JSONObject(jsonString)
  private val tours = json.optJSONArray("tour")
  private val tour = tours.optJSONObject(0)
  private val metaData = Option(tour.optJSONObject("meta")) // optional
  private val sourceObject = metaData map { _.optJSONObject("source") } // optional
  private val primaryImage = Option(tour.optJSONObject("primaryImage")) // optional
  private val start = tour.optJSONObject("startingPoint") // mandatory

  val title: String = tour.optString("title", "no title")
  val longText: String = Html.fromHtml(tour.optString("longText")).toString
  val geometry: String = tour.optString("geometry")
  val author: String = (metaData map { _.optString("author") }) getOrElse ""
  val source: String = sourceObject map { _.optString("name") } getOrElse ""
  val imageId: Int = primaryImage map { _.optInt("id") } getOrElse 0
  val startingPoint: String = start.optString("lon", "") + "," + start.optString("lat", "")
  val isWinterTour: Boolean = tour.optBoolean("winterActivity", false)
}
