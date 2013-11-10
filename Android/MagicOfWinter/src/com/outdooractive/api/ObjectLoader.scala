package com.outdooractive.api

import android.content.Context
import java.util.Locale

class ObjectLoader(val context: Context, listener: IStringResultListener) {
  def loadTourCategories {
    val request = String.format(Locale.GERMAN, "http://www.outdooractive.com/api/project/%s/category/tree/tour/pruned?lang=de&key=%s", PROJECT_ID, PROJECT_KEY)
    this.loadFromWeb(request)
  }

  def loadTourList(categoryId: String) {
    val request = String.format(Locale.GERMAN, "http://www.outdooractive.com/api/project/%s/category/%s/oois?lang=de&display=minimal&categoryHandling=fallback&key=%s", PROJECT_ID, categoryId, PROJECT_KEY)
    this.loadFromWeb(request)
  }

  def loadTour(tourId: String) {
    val request = String.format(Locale.GERMAN, "http://www.outdooractive.com/api/project/%s/oois/%s?lang=de&display=full&categoryHandling=fallback&key=%s", PROJECT_ID, tourId, PROJECT_KEY)
    this.loadFromWeb(request)
  }

  private def loadFromWeb(request: String) {
    new WebLoaderTask(this.context, this.listener).loadFromWeb(request)
  }

  private final val PROJECT_ID: String = "app-outdooractive-tage-2013-android"
  private final val PROJECT_KEY: String = "yourtest-outdoora-ctiveapi"
}