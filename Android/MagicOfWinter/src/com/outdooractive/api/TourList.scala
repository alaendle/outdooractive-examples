package com.outdooractive.api;

import scala.collection.JavaConversions.seqAsJavaList
import scala.util.parsing.json.JSON
import android.util.Log

class CC[T] { def unapply(a: Any): Option[T] = Some(a.asInstanceOf[T]) }

object M extends CC[Map[String, Any]]
object L extends CC[List[Any]]
object S extends CC[String]
object D extends CC[Double]
object B extends CC[Boolean]

class TourList(jsonString: String) {
  val tours: java.util.List[TourHeader] = {
    val start = System.currentTimeMillis()
    val result = for {
      Some(M(map)) <- List(JSON.parseFull(jsonString))
      L(tours) = map("tour")
      M(tour) <- tours
    } yield {
      val id = tour.getOrElse("id", "0").asInstanceOf[String]
      val title = tour.getOrElse("title", "no title").asInstanceOf[String]
      new TourHeader(id.toInt, title)
    }

    val end = System.currentTimeMillis()
    val duration = end - start

    Log.i("Profiling", "Parsing took " + duration)

    result
  }
}
