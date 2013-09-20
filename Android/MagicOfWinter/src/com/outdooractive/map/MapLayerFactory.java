package com.outdooractive.map;

import java.net.MalformedURLException;
import java.net.URL;

import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.TileProvider;
import com.google.android.gms.maps.model.UrlTileProvider;

public final class MapLayerFactory {

	public static TileOverlayOptions outdooractiveWinter() {
		return createOptions("AlpsteinWinter", -2);
	}

	public static TileOverlayOptions outdooractiveSkiresorts() {
		return createOptions("xPiste", -1);
	}

	private static TileOverlayOptions createOptions(String tag, int zIndex) {
		return new TileOverlayOptions().tileProvider(createTileProvider(tag))
				.zIndex(zIndex);
	}

	private static TileProvider createTileProvider(final String layerTag) {
		return new UrlTileProvider(256, 256) {

			@Override
			public URL getTileUrl(int x, int y, int zoom) {
				try {
					// http://e0.oastatic.com/map/AlpsteinWinter/4/8/5.png
					int serverId = (x + y) % 4;
					return new URL(String.format(
							"http://e%d.oastatic.com/map/%s/%d/%d/%d.png",
							serverId, layerTag, zoom, x, y));
				} catch (MalformedURLException e) {
					return null;
				}
			}
		};
	}
}
