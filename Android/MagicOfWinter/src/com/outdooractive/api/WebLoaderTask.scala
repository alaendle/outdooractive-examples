package com.outdooractive.api

import java.io.IOException

import scala.io.Source

import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.DefaultHttpClient

import com.outdooractive.example.magicOfWinter.R

import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.os.AsyncTask
import android.util.Log

abstract trait IStringResultListener {
  def onResult(result: String)
}

class WebLoaderTask(val context: Context, val listener: IStringResultListener) extends AsyncTask[AnyRef, Void, String] {

  def loadFromWeb(request: String) {
    this.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, request)
  }

  protected override def onPreExecute {
    progressDialog = ProgressDialog.show(this.context, "", this.context.getText(R.string.loading_data), true, true, new DialogInterface.OnCancelListener {
      def onCancel(arg0: DialogInterface) {
        arg0.dismiss
      }
    })
  }

  protected def doInBackground(params: AnyRef*): String = {
    val request: String = params(0).asInstanceOf[String]
    Log.i("WebLoaderTask", "Request: " + request)
    val httpGet: HttpGet = new HttpGet(request)
    httpGet.addHeader("Accept", "application/json")
    httpGet.addHeader("User-Agent", "Android Test OA")
    try {
      val httpClient = new DefaultHttpClient
      val response = httpClient.execute(httpGet)
      val entity = Option(response.getEntity)
      val resultString = entity map (x => Source.fromInputStream(x.getContent).mkString(""))
      Log.i("WebLoaderTask", "Result: " + resultString)
      resultString getOrElse (null)
    } catch {
      case e: IOException => {
        e.printStackTrace()
        null
      }
    }
  }

  protected override def onPostExecute(result: String) {
    if (progressDialog != null && progressDialog.isShowing) {
      progressDialog.dismiss
    }
    if (result != null && this.listener != null) {
      this.listener.onResult(result)
    }
  }

  private var progressDialog: ProgressDialog = null
}
