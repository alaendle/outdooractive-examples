package com.outdooractive.map

import java.net.MalformedURLException
import java.net.URL

import com.google.android.gms.maps.model.TileOverlayOptions
import com.google.android.gms.maps.model.UrlTileProvider

final object MapLayerFactory {
  def outdooractiveWinter: TileOverlayOptions = createOptions("AlpsteinWinter", -2)

  def outdooractiveSkiresorts: TileOverlayOptions = createOptions("xPiste", -1)

  private def createOptions(tag: String, zIndex: Int) =
    new TileOverlayOptions().tileProvider(createTileProvider(tag)).zIndex(zIndex)

  private def createTileProvider(layerTag: String) =
    new UrlTileProvider(256, 256) {
      def getTileUrl(x: Int, y: Int, zoom: Int): URL = {
        try {
          val serverId: Int = (x + y) % 4
          new URL(s"http://e$serverId.oastatic.com/map/$layerTag/$zoom/$x/$y.png")
        } catch {
          case e: MalformedURLException => {
            null
          }
        }
      }
    }
}
