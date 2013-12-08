package com.outdooractive.map

import java.lang.Double
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.outdooractive.example.magicOfWinter.R
import android.graphics.Color

final object MapObjectFactory {
  def createRoute(geometry: String): PolylineOptions = {
    val coordinates: Array[String] = geometry.split(" ")
    val options: PolylineOptions = new PolylineOptions
    options.zIndex(2)
    options.color(Color.MAGENTA)
    var i: Int = 0
    while (i < coordinates.length) {
      val values: Array[String] = coordinates(i).split(",")
      val latitude: Double = Double.valueOf(values(1))
      val longitude: Double = Double.valueOf(values(0))
      options.add(new LatLng(latitude, longitude))
      i += 1
    }
    options
  }

  def createMarker(geometry: String): MarkerOptions = {
    new MarkerOptions().position(getPosition(geometry)).icon(BitmapDescriptorFactory.fromResource(R.drawable.tour_start))
  }

  def updateCamera(geometry: String): CameraUpdate = {
    CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder().target(getPosition(geometry)).bearing(0).tilt(90).zoom(17).build)
  }

  private def getPosition(geometry: String): LatLng = {
    val coordinates: Array[String] = geometry.split(" ")
    val values: Array[String] = coordinates(0).split(",")
    val latitude: Double = Double.valueOf(values(1))
    val longitude: Double = Double.valueOf(values(0))
    new LatLng(latitude, longitude)
  }
}
