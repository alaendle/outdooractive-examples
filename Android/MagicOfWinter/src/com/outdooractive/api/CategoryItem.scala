package com.outdooractive.api;

import scala.util.parsing.json.JSON
import scala.collection.JavaConversions.asScalaBuffer
import scala.collection.JavaConversions.bufferAsJavaList
import scala.collection.JavaConversions.seqAsJavaList
import android.util.Log
import org.json4s.native.JsonParser
import org.json4s.DefaultFormats

class CategoryItem(x: Map[String, Any]) {
  def this(jsonString: String) = this(JSON.parseFull(jsonString).get.asInstanceOf[Map[String, Any]])

  val id = x.getOrElse("id", "0").asInstanceOf[String].toInt
  val name = x.getOrElse("name", "root").asInstanceOf[String]
  val children: java.util.List[CategoryItem] = for {
    M(map) <- List(x)
    L(categories) = map getOrElse ("category", Nil)
    M(category) <- categories
  } yield {
    new CategoryItem(category)
  }

  def findById(id: Int): CategoryItem = {
    if (this.id == id) {
      this
    } else {
      children.map(_.findById(id)).find(_ != null).getOrElse(null)
    }
  }

  def hasChildren: Boolean = children.size() > 0

  override def toString: String = name;

  def getChildrenIds: java.util.ArrayList[Integer] = new java.util.ArrayList[Integer](children.map(x => x.id.asInstanceOf[Integer]))

  def getChildrenNames: java.util.ArrayList[String] = new java.util.ArrayList[String](children.map(x => x.name))
}
