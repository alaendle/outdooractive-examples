package com.outdooractive.api

import java.io.InputStream
import java.net.URL

import scala.concurrent.Future
import scala.concurrent.future

import android.graphics.drawable.Drawable
import android.util.Log

object ImageLoaderTask extends Implicits {

  def loadFromWeb(imageId: String): Future[Drawable] = {
    future {
      val imageUrl: String = "http://img.oastatic.com/img/%d/%d/%s/%s/t" format (400, 400, "", imageId)
      Log.i("ImageLoaderTask", "Loading image: " + imageUrl)
      val stream: InputStream = new URL(imageUrl).getContent.asInstanceOf[InputStream]
      Drawable.createFromStream(stream, "tour_image_" + imageId)
    }
  }
}
