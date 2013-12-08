package com.outdooractive.api

import scala.concurrent.ExecutionContext
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import java.util.concurrent.LinkedBlockingQueue

trait Implicits {
  implicit val exec = ExecutionContext.fromExecutor(
    new ThreadPoolExecutor(100, 100, 1000, TimeUnit.SECONDS,
      new LinkedBlockingQueue[Runnable]))
}
