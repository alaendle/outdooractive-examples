package com.outdooractive.api

import scala.concurrent.Future

import android.content.Context

class ObjectLoader(val context: Context) {
  def loadTourCategories: Future[String] = {
    val request = s"http://www.outdooractive.com/api/project/$PROJECT_ID/category/tree/tour/pruned?lang=de&key=$PROJECT_KEY"
    this.loadFromWeb(request)
  }

  def loadTourList(categoryId: String): Future[String] = {
    val request = s"http://www.outdooractive.com/api/project/$PROJECT_ID/category/$categoryId/oois?lang=de&display=minimal&categoryHandling=fallback&key=$PROJECT_KEY"
    this.loadFromWeb(request)
  }

  def loadTour(tourId: String): Future[String] = {
    val request = s"http://www.outdooractive.com/api/project/$PROJECT_ID/oois/$tourId?lang=de&display=full&categoryHandling=fallback&key=$PROJECT_KEY"
    this.loadFromWeb(request)
  }

  private def loadFromWeb(request: String): Future[String] = {
    WebLoaderTask.loadFromWeb(this.context, request)
  }

  private final val PROJECT_ID: String = "app-outdooractive-tage-2013-android"
  private final val PROJECT_KEY: String = "yourtest-outdoora-ctiveapi"
}
