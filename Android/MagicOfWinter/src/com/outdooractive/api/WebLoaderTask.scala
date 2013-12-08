package com.outdooractive.api

import scala.concurrent.Future
import scala.concurrent.future
import scala.io.Source

import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.DefaultHttpClient

import com.outdooractive.example.magicOfWinter.R

import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log

class WebLoaderTask(val context: Context) extends Implicits {

  def loadFromWeb(request: String): Future[String] = {
    val progressDialog = ProgressDialog.show(this.context, "", this.context.getText(R.string.loading_data), true, true, new DialogInterface.OnCancelListener {
      def onCancel(arg0: DialogInterface) {
        arg0.dismiss
      }
    })

    val f = load(request)
    f onComplete {
      case _ =>
        if (progressDialog != null && progressDialog.isShowing) {
          progressDialog.dismiss
        }
    }
    f
  }

  private def load(request: String): Future[String] = future {
    Log.i("WebLoaderTask", "Request: " + request)
    val httpGet: HttpGet = new HttpGet(request)
    httpGet.addHeader("Accept", "application/json")
    httpGet.addHeader("User-Agent", "Android Test OA")
    val httpClient = new DefaultHttpClient
    val response = httpClient.execute(httpGet)
    val entity = Option(response.getEntity)
    val resultString = entity map (x => Source.fromInputStream(x.getContent).mkString(""))
    Log.i("WebLoaderTask", "Result: " + resultString)
    resultString get
  }
}
