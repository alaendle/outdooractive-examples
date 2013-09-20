package com.outdooractive.map;

import android.graphics.Color;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.outdooractive.example.magicOfWinter.R;

public final class MapObjectFactory {

	public static PolylineOptions createRoute(String geometry) {
		String[] coordinates = geometry.split(" ");

		PolylineOptions options = new PolylineOptions();
		options.zIndex(2);
		options.color(Color.MAGENTA);

		for (int i = 0; i < coordinates.length; i++) {
			String[] values = coordinates[i].split(",");

			double latitude = Double.valueOf(values[1]);
			double longitude = Double.valueOf(values[0]);

			options.add(new LatLng(latitude, longitude));
		}

		return options;
	}

	public static MarkerOptions createMarker(String geometry) {
		return new MarkerOptions().position(getPosition(geometry)).icon(
				BitmapDescriptorFactory.fromResource(R.drawable.tour_start));
	}

	public static CameraUpdate updateCamera(String geometry) {
		return CameraUpdateFactory
				.newCameraPosition(new CameraPosition.Builder()
						.target(getPosition(geometry)).bearing(0).tilt(90)
						.zoom(17).build());
	}

	private static LatLng getPosition(String geometry) {
		String[] coordinates = geometry.split(" ");
		String[] values = coordinates[0].split(",");

		double latitude = Double.valueOf(values[1]);
		double longitude = Double.valueOf(values[0]);

		return new LatLng(latitude, longitude);
	}
}
