package com.outdooractive.api

import scala.concurrent.ExecutionContext

import android.os.AsyncTask

trait Implicits {
  implicit lazy val exec = ExecutionContext.fromExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
}
