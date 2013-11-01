package com.outdooractive.api;

import android.text.Html
import play.api.libs.json.Json

class Tour(jsonString: String) {
  private val json = Json.parse(jsonString)
  private val tour = (json \ "tour")(0)
  private val meta = tour \ "meta"
  private val start = tour \ "startingPoint"

  val title: String = (tour \ "title").asOpt[String] getOrElse "no title"
  val longText: String = Html.fromHtml((tour \ "longText").asOpt[String] getOrElse "").toString
  val geometry: String = (tour \ "geometry").asOpt[String] getOrElse ""
  val author: String = (meta \ "author").asOpt[String] getOrElse ""
  val source: String = (meta \ "source" \ "name").asOpt[String] getOrElse ""
  val imageId: Int = (tour \ "primaryImage" \ "id").asOpt[Int] getOrElse 0
  val startingPoint: String = ((start \ "lon").asOpt[String] getOrElse "") + "," + ((start \ "lat").asOpt[String] getOrElse "")
  val isWinterTour: Boolean = (tour \ "winterActivity").asOpt[Boolean] getOrElse false
}
