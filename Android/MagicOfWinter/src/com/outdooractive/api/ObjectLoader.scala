package com.outdooractive.api

import android.content.Context

object ObjectLoader {
  def loadTourCategories(context: Context) = {
    val request = s"http://www.outdooractive.com/api/project/$PROJECT_ID/category/tree/tour/pruned?lang=de&key=$PROJECT_KEY"
    WebLoaderTask.loadFromWeb(context, request)
  }

  def loadTourList(context: Context, categoryId: String) = {
    val request = s"http://www.outdooractive.com/api/project/$PROJECT_ID/category/$categoryId/oois?lang=de&display=minimal&categoryHandling=fallback&key=$PROJECT_KEY"
    WebLoaderTask.loadFromWeb(context, request)
  }

  def loadTour(context: Context, tourId: String) = {
    val request = s"http://www.outdooractive.com/api/project/$PROJECT_ID/oois/$tourId?lang=de&display=full&categoryHandling=fallback&key=$PROJECT_KEY"
    WebLoaderTask.loadFromWeb(context, request)
  }

  private final val PROJECT_ID: String = "app-outdooractive-tage-2013-android"
  private final val PROJECT_KEY: String = "yourtest-outdoora-ctiveapi"
}
