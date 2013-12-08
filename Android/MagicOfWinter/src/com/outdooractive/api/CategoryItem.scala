package com.outdooractive.api;

import scala.collection.JavaConversions.asScalaBuffer
import scala.collection.JavaConversions.bufferAsJavaList
import scala.collection.JavaConversions.seqAsJavaList

import org.json4s.DefaultFormats
import org.json4s.JsonAST.JArray
import org.json4s.JsonAST.JValue
import org.json4s.native.JsonParser

class CategoryItem(val id: String, val name: String, childJson: JValue) {
  def this(jsonString: String) = this("0", "root", JsonParser.parse(jsonString) \ "category")

  implicit lazy val formats = DefaultFormats
  val children: java.util.List[CategoryItem] =
    childJson match {
      case JArray(categories) => categories.map(x => new CategoryItem((x \ "id").extract[String], (x \ "name").extract[String], x \ "category"))
      case _ => Nil
    }

  def findById(id: String): Option[CategoryItem] = {
    if (this.id == id) {
      Some(this)
    } else {
      children.map(_.findById(id)).find(_ != None).getOrElse(None)
    }
  }

  def hasChildren: Boolean = !children.isEmpty()

  override def toString: String = name;

  def getChildrenIds: java.util.ArrayList[String] = new java.util.ArrayList[String](children.map(x => x.id))

  def getChildrenNames: java.util.ArrayList[String] = new java.util.ArrayList[String](children.map(x => x.name))
}
