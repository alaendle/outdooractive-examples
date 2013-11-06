package com.outdooractive.api

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

import org.apache.http.HttpEntity
import org.apache.http.HttpResponse
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.DefaultHttpClient

import com.outdooractive.example.magicOfWinter.R

import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.os.AsyncTask
import android.util.Log

abstract trait IWebResultListener {
  def onResultLoaded(result: String)
}

class WebLoaderTask(val context: Context, val listener: IWebResultListener) extends AsyncTask[AnyRef, Void, String] {

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
      val httpClient: DefaultHttpClient = new DefaultHttpClient
      val response: HttpResponse = httpClient.execute(httpGet)
      val entity: HttpEntity = response.getEntity
      if (entity != null) {
        val reader: BufferedReader = new BufferedReader(new InputStreamReader(entity.getContent))
        var line: String = null
        val builder: StringBuilder = new StringBuilder
        while ((({
          line = reader.readLine; line
        })) != null) {
          builder.append(line)
        }
        val resultString: String = builder.substring(0)
        Log.i("WebLoaderTask", "Result: " + resultString)
        resultString
      } else {
        null
      }
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
      this.listener.onResultLoaded(result)
    }
  }

  private var progressDialog: ProgressDialog = null
}
