package com.outdooractive.api;

import scala.collection.JavaConversions.asScalaBuffer
import scala.collection.JavaConversions.bufferAsJavaList
import scala.collection.JavaConversions.seqAsJavaList

import org.json4s.DefaultFormats
import org.json4s.JsonAST.JArray
import org.json4s.JsonAST.JField
import org.json4s.JsonAST.JObject
import org.json4s.JsonAST.JString
import org.json4s.JsonAST.JValue
import org.json4s.native.JsonParser

class CategoryItem(x: JValue) {
  def this(jsonString: String) = this(JsonParser.parse(jsonString))

  implicit lazy val formats = DefaultFormats
  val id = (x \ "id").extractOpt[String] getOrElse "0"
  val name = (x \ "name").extractOpt[String] getOrElse "root"
  val children: java.util.List[CategoryItem] =
    for {
      JArray(categories) <- (x \ "category")
      category <- categories
    } yield new CategoryItem(category)

  def findById(id: String): CategoryItem = {
    if (this.id == id) {
      this
    } else {
      children.map(_.findById(id)).find(_ != null).getOrElse(null)
    }
  }

  def hasChildren: Boolean = children.size() > 0

  override def toString: String = name;

  def getChildrenIds: java.util.ArrayList[String] = new java.util.ArrayList[String](children.map(x => x.id))

  def getChildrenNames: java.util.ArrayList[String] = new java.util.ArrayList[String](children.map(x => x.name))
}
