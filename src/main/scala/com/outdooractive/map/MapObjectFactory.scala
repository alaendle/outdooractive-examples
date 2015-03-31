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

object MapObjectFactory {
  def createRoute(geometry: String): PolylineOptions = {
    val options = new PolylineOptions
    options.zIndex(2)
    options.color(Color.MAGENTA)
    positions(geometry) foreach (coordinate => options.add(convert(coordinate)))
    options
  }

  def createMarker(geometry: String): MarkerOptions = {
    new MarkerOptions().position(getFirstPosition(geometry)).icon(BitmapDescriptorFactory.fromResource(R.drawable.tour_start))
  }

  def updateCamera(geometry: String): CameraUpdate = {
    CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder().target(getFirstPosition(geometry)).bearing(0).tilt(90).zoom(17).build)
  }

  private def getFirstPosition(geometry: String) = convert(positions(geometry)(0))

  private def positions(geometry: String) = geometry.split(" ")

  private def convert(lngLat: String) = {
    val values = lngLat.split(",")
    val latitude = values(1).toDouble
    val longitude = values(0).toDouble
    new LatLng(latitude, longitude)
  }
}
