package com.outdooractive.map

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapFragment
import com.outdooractive.example.magicOfWinter.R

class MapViewFragment extends Fragment with OnClickListener {
  override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle): View = {
    getActivity.getActionBar.setTitle(R.string.action_map)
    getActivity.getActionBar.show()
    inflater.inflate(R.layout.map_view_fragment, container, false)
  }

  override def onDestroyView() {
    super.onDestroyView()
    val mapFragment = getChildFragmentManager.findFragmentById(R.id.map_fragment)
    getFragmentManager.beginTransaction.remove(mapFragment).commit()
  }

  override def onActivityCreated(savedInstanceState: Bundle) {
    super.onActivityCreated(savedInstanceState)
    val isWinter = getArguments.getBoolean("winter")
    oaWinter.setChecked(isWinter)
    oaWinter.setOnClickListener(this)
    oaSlope.setChecked(isWinter)
    oaSlope.setOnClickListener(this)
    googleMaps.setOnClickListener(this)
    googleHybrid.setChecked(!isWinter)
    googleHybrid.setOnClickListener(this)
    this.updateMap()
    this.setCameraPosition()
  }

  def onClick(v: View) {
    if (v.getId == R.id.cbx_winter && oaWinter.isChecked) {
      googleMaps.setChecked(false)
      googleHybrid.setChecked(false)
    }
    if (v.getId == R.id.cbx_google_maps && googleMaps.isChecked) {
      oaWinter.setChecked(false)
      googleHybrid.setChecked(false)
    }
    if (v.getId == R.id.cbx_google_hybrid && googleHybrid.isChecked) {
      oaWinter.setChecked(false)
      googleMaps.setChecked(false)
    }
    updateMap()
  }

  private def updateMap() {
    setMapLogo()
    setMapOverlays()
    setMapObjects()
  }

  private def setMapLogo() {
    val oaChecked = oaSlope.isChecked || oaWinter.isChecked
    oaLogo.setVisibility(if (oaChecked) View.VISIBLE else View.GONE)
  }

  private def setMapOverlays() {
    map match {
      case Some(map) =>
        map.clear()
        map.setMapType(GoogleMap.MAP_TYPE_NONE)
        if (googleMaps.isChecked) {
          map.setMapType(GoogleMap.MAP_TYPE_NORMAL)
        } else if (googleHybrid.isChecked) {
          map.setMapType(GoogleMap.MAP_TYPE_HYBRID)
        } else if (oaWinter.isChecked) {
          map.addTileOverlay(MapLayerFactory.outdooractiveWinter)
        }
        if (oaSlope.isChecked) {
          map.addTileOverlay(MapLayerFactory.outdooractiveSkiresorts)
        }
      case None =>
    }
  }

  private def setMapObjects() {
    if (geometry != null && geometry.length > 0) {
      map map (_.addPolyline(MapObjectFactory.createRoute(geometry)))
    }
    if (startPosition != null && startPosition.length > 0) {
      map map (_.addMarker(MapObjectFactory.createMarker(startPosition)))
    }
  }

  private def setCameraPosition() {
    val position = if (startPosition != null && startPosition.length > 0) startPosition else geometry
    if (position != null && position.length > 0) {
      map map (_.moveCamera(MapObjectFactory.updateCamera(position)))
    }
  }

  private def map = Option(getChildFragmentManager.findFragmentById(R.id.map_fragment).asInstanceOf[MapFragment].getMap)
  private def oaWinter = getView.findViewById(R.id.cbx_winter).asInstanceOf[CheckBox]
  private def oaSlope = getView.findViewById(R.id.cbx_ski).asInstanceOf[CheckBox]
  private def googleMaps = getView.findViewById(R.id.cbx_google_maps).asInstanceOf[CheckBox]
  private def googleHybrid = getView.findViewById(R.id.cbx_google_hybrid).asInstanceOf[CheckBox]
  private def oaLogo = getView.findViewById(R.id.map_logo).asInstanceOf[ImageView]
  private def geometry = getArguments.getString("geometry")
  private def startPosition = getArguments.getString("start")
}
