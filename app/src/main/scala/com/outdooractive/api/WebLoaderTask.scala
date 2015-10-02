package com.outdooractive.api

import java.net.URL

import scala.concurrent.Future
import scala.io.Source

import com.outdooractive.example.magicOfWinter.R

import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log

object WebLoaderTask extends Implicits {

  def loadFromWeb(context: Context, request: String): Future[String] = {
    val progressDialog = ProgressDialog.show(context, "", context.getText(R.string.loading_data), true, true, new DialogInterface.OnCancelListener {
      def onCancel(arg0: DialogInterface) {
        arg0.dismiss()
      }
    })

    val f = load(request)
    f onComplete {
      case _ =>
        if (progressDialog.isShowing) {
          progressDialog.dismiss()
        }
    }
    f
  }

  private def load(request: String) = Future {
    Log.i(WebLoaderTask.getClass.getName, "Request: " + request)
    val url = new URL(request)
    val urlConnection = url.openConnection()
    urlConnection.setRequestProperty("Accept", "application/json")
    urlConnection.setRequestProperty("User-Agent", "Android Test OA")
    val resultString = Source.fromInputStream(urlConnection.getInputStream()).mkString
    Log.i(WebLoaderTask.getClass.getName, "Result: " + resultString)
    resultString
  }
}
