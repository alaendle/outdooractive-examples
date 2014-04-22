package com.outdooractive.example.wintermap;

import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.UrlTileProvider;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);

		try {
			MapsInitializer.initialize(this);
		} catch (GooglePlayServicesNotAvailableException e) {
			Toast.makeText(this, R.string.map_not_available, Toast.LENGTH_SHORT)
					.show();
			return;
		}

		MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map_fragment);
		GoogleMap map = mapFragment.getMap();
		if (map != null) {
			map.addTileOverlay(new TileOverlayOptions()
					.tileProvider(createWinterTileProvider()));
		}
	}

	private static UrlTileProvider createWinterTileProvider() {
		return new UrlTileProvider(256, 256) {
			@Override
			public URL getTileUrl(int x, int y, int zoom) {
				try {
					// e.g. http://e0.oastatic.com/map/AlpsteinWinter/4/8/5.png
					int serverId = (x + y) % 4;
					return new URL(
							String.format(
									"http://e%d.oastatic.com/map/AlpsteinWinter/%d/%d/%d.png",
									serverId, zoom, x, y));
				} catch (MalformedURLException e) {
					return null;
				}
			}
		};
	}
}
