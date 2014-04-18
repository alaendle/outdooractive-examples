package com.outdooractive.map

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
    val coordinates = geometry.split(" ")
    val options = new PolylineOptions
    options.zIndex(2)
    options.color(Color.MAGENTA)
    coordinates.map(coordinate => {
      val values = coordinate.split(",")
      val latitude = values(1).toDouble
      val longitude = values(0).toDouble
      options.add(new LatLng(latitude, longitude))
    })

    options
  }

  def createMarker(geometry: String): MarkerOptions = {
    new MarkerOptions().position(getPosition(geometry)).icon(BitmapDescriptorFactory.fromResource(R.drawable.tour_start))
  }

  def updateCamera(geometry: String): CameraUpdate = {
    CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder().target(getPosition(geometry)).bearing(0).tilt(90).zoom(17).build)
  }

  private def getPosition(geometry: String) = {
    val coordinates = geometry.split(" ")
    val values = coordinates(0).split(",")
    val latitude = values(1).toDouble
    val longitude = values(0).toDouble
    new LatLng(latitude, longitude)
  }
}
