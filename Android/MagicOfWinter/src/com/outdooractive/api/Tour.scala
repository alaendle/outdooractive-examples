package com.outdooractive.api;

import org.json4s.DefaultFormats
import org.json4s.native.JsonParser
import android.text.Html

class Tour(jsonString: String) {
  private val json = JsonParser.parse(jsonString)
  private val tour = (json \ "tour")(0)
  private val meta = tour \ "meta"
  private val start = tour \ "startingPoint"

  implicit lazy val formats = DefaultFormats
  val title: String = (tour \ "title").extractOpt[String] getOrElse "no title"
  val longText: String = Html.fromHtml((tour \ "longText").extractOpt[String] getOrElse "").toString
  val geometry: String = (tour \ "geometry").extractOpt[String] getOrElse ""
  val author: String = (meta \ "author").extractOpt[String] getOrElse ""
  val source: String = (meta \ "source" \ "name").extractOpt[String] getOrElse ""
  val imageId: String = (tour \ "primaryImage" \ "id").extractOpt[String] getOrElse "0"
  val startingPoint: String = ((start \ "lon").extractOpt[String] getOrElse "") + "," + ((start \ "lat").extractOpt[String] getOrElse "")
  val isWinterTour: Boolean = (tour \ "winterActivity").extractOpt[Boolean] getOrElse false
}
