package com.outdooractive.api

import java.io.IOException
import java.io.InputStream
import java.net.URL

import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.util.Log

trait IImageResultListener {
  def onImageLoaded(image: Drawable)
}

class ImageLoaderTask(val listener: IImageResultListener) extends MyAsyncTask[Void, Drawable] {

  def loadFromWeb(imageId: String) {
    this.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, imageId)
  }

  override def doInBackground1(args: Array[String]): Drawable = {
    val imageId: String = args(0)
    val imageUrl: String = "http://img.oastatic.com/img/%d/%d/%s/%s/t" format (400, 400, "", imageId)
    Log.i("ImageLoaderTask", "Loading image: " + imageUrl)
    try {
      val stream: InputStream = new URL(imageUrl).getContent.asInstanceOf[InputStream]
      Drawable.createFromStream(stream, "tour_image_" + imageId)
    } catch {
      case e: IOException =>
        e.printStackTrace()
        null
    }
  }

  override def onPostExecute(result: Drawable) {
    if (result != null && this.listener != null) {
      this.listener.onImageLoaded(result)
    }
  }
}
