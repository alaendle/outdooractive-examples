package com.outdooractive.api

import android.content.Context

import scala.concurrent.Future

object ObjectLoader {
  private final val ApiId = "app-outdooractive-tage-2013-android"
  private final val ApiKey = "yourtest-outdoora-ctiveapi"

  def loadTourCategories(context: Context): Future[String] = {
    val request = s"http://www.outdooractive.com/api/project/$ApiId/category/tree/tour/pruned?lang=de&key=$ApiKey"
    WebLoaderTask.loadFromWeb(context, request)
  }

  def loadTourList(context: Context, categoryId: String): Future[String] = {
    val request = s"http://www.outdooractive.com/api/project/$ApiId/category/$categoryId/oois?lang=de&display=minimal&categoryHandling=fallback&key=$ApiKey"
    WebLoaderTask.loadFromWeb(context, request)
  }

  def loadTour(context: Context, tourId: String): Future[String] = {
    val request = s"http://www.outdooractive.com/api/project/$ApiId/oois/$tourId?lang=de&display=full&categoryHandling=fallback&key=$ApiKey"
    WebLoaderTask.loadFromWeb(context, request)
  }
}
