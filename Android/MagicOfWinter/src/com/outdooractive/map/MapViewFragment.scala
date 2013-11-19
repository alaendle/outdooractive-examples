package com.outdooractive.map

import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.MapsInitializer
import com.outdooractive.example.magicOfWinter.R
import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.Toast

class MapViewFragment extends Fragment with OnClickListener {
  override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle): View = {
    getActivity.getActionBar.setTitle(R.string.action_map)
    getActivity.getActionBar.show
    val view: View = inflater.inflate(R.layout.map_view_fragment, container, false)
    try {
      MapsInitializer.initialize(getActivity)
    } catch {
      case e: GooglePlayServicesNotAvailableException => {
        Toast.makeText(getActivity, R.string.map_not_available, Toast.LENGTH_SHORT).show
        view
      }
    }
    mapView = view.findViewById(R.id.map_view).asInstanceOf[MapView]
    mapView.onCreate(savedInstanceState)
    val isWinter: Boolean = getArguments.getBoolean("winter")
    oaWinter = view.findViewById(R.id.cbx_winter).asInstanceOf[CheckBox]
    oaWinter.setChecked(isWinter)
    oaWinter.setOnClickListener(this)
    oaSlope = view.findViewById(R.id.cbx_ski).asInstanceOf[CheckBox]
    oaSlope.setChecked(isWinter)
    oaSlope.setOnClickListener(this)
    googleMaps = view.findViewById(R.id.cbx_google_maps).asInstanceOf[CheckBox]
    googleMaps.setOnClickListener(this)
    googleHybrid = view.findViewById(R.id.cbx_google_hybrid).asInstanceOf[CheckBox]
    googleHybrid.setChecked(!isWinter)
    googleHybrid.setOnClickListener(this)
    oaLogo = view.findViewById(R.id.map_logo).asInstanceOf[ImageView]
    geometry = getArguments.getString("geometry")
    startPosition = getArguments.getString("start")
    this.updateMap
    this.setCameraPosition
    view
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
    updateMap
  }

  private def updateMap {
    if (map == null) {
      map = mapView.getMap
    }
    if (map != null) {
      setMapLogo
      setMapOverlays
      setMapObjects
    }
  }

  private def setMapLogo {
    val oaChecked: Boolean = oaSlope.isChecked || oaWinter.isChecked
    oaLogo.setVisibility(if (oaChecked) View.VISIBLE else View.GONE)
  }

  private def setMapOverlays {
    map.clear
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
  }

  private def setMapObjects {
    val geometry: String = getArguments.getString("geometry")
    val startPosition: String = getArguments.getString("start")
    if (geometry != null && geometry.length > 0) {
      map.addPolyline(MapObjectFactory.createRoute(geometry))
    }
    if (startPosition != null && startPosition.length > 0) {
      map.addMarker(MapObjectFactory.createMarker(startPosition))
    }
  }

  private def setCameraPosition {
    val position: String = if (startPosition != null && startPosition.length > 0) startPosition else geometry
    if (position != null && position.length > 0) {
      map.moveCamera(MapObjectFactory.updateCamera(position))
    }
  }

  override def onResume {
    super.onResume
    if (mapView != null) {
      mapView.onResume
    }
  }

  override def onPause {
    super.onPause
    if (mapView != null) {
      mapView.onPause
    }
  }

  override def onDestroy {
    if (mapView != null) {
      mapView.onDestroy
    }
    super.onDestroy
  }

  private var mapView: MapView = null
  private var map: GoogleMap = null
  private var oaWinter: CheckBox = null
  private var oaSlope: CheckBox = null
  private var googleMaps: CheckBox = null
  private var googleHybrid: CheckBox = null
  private var oaLogo: ImageView = null
  private[map] var geometry: String = null
  private[map] var startPosition: String = null
}
