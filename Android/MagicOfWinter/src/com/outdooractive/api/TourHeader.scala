package com.outdooractive.api

import org.json.JSONObject

class TourHeader(val id: Int, val title: String) {
  override def toString: String = title
}
