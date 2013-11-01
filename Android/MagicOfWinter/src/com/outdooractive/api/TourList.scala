package com.outdooractive.api;

import scala.collection.JavaConversions.seqAsJavaList
import org.json4s.JsonAST.JField
import org.json4s.JsonAST.JObject
import org.json4s.JsonAST.JString
import org.json4s.native.JsonParser

class TourList(jsonString: String) {
  val tours: java.util.List[TourHeader] = {
    for {
      JObject(tour) <- JsonParser.parse(jsonString)
      JField("id", JString(id)) <- tour
      JField("title", JString(title)) <- tour
    } yield new TourHeader(id.toInt, title)
  }
}
