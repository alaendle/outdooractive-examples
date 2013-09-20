package com.outdooractive.map;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.outdooractive.example.magicOfWinter.R;

public class MapViewFragment extends Fragment implements OnClickListener {

	private MapView mapView;
	private GoogleMap map;
	private CheckBox oaWinter;
	private CheckBox oaSlope;
	private CheckBox googleMaps;
	private CheckBox googleHybrid;
	private ImageView oaLogo;
	String geometry;
	String startPosition;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		getActivity().getActionBar().setTitle(R.string.action_map);
		getActivity().getActionBar().show();

		View view = inflater.inflate(R.layout.map_view_fragment, container,
				false);
		try {
			MapsInitializer.initialize(getActivity());
		} catch (GooglePlayServicesNotAvailableException e) {
			Toast.makeText(getActivity(), R.string.map_not_available,
					Toast.LENGTH_SHORT).show();
			return view;
		}

		mapView = (MapView) view.findViewById(R.id.map_view);
		mapView.onCreate(savedInstanceState);

		boolean isWinter = getArguments().getBoolean("winter");

		oaWinter = (CheckBox) view.findViewById(R.id.cbx_winter);
		oaWinter.setChecked(isWinter);
		oaWinter.setOnClickListener(this);

		oaSlope = (CheckBox) view.findViewById(R.id.cbx_ski);
		oaSlope.setChecked(isWinter);
		oaSlope.setOnClickListener(this);

		googleMaps = (CheckBox) view.findViewById(R.id.cbx_google_maps);
		googleMaps.setOnClickListener(this);

		googleHybrid = (CheckBox) view.findViewById(R.id.cbx_google_hybrid);
		googleHybrid.setChecked(!isWinter);
		googleHybrid.setOnClickListener(this);

		oaLogo = (ImageView) view.findViewById(R.id.map_logo);

		geometry = getArguments().getString("geometry");
		startPosition = getArguments().getString("start");

		this.updateMap();
		this.setCameraPosition();

		return view;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.cbx_winter && oaWinter.isChecked()) {
			googleMaps.setChecked(false);
			googleHybrid.setChecked(false);
		}
		if (v.getId() == R.id.cbx_google_maps && googleMaps.isChecked()) {
			oaWinter.setChecked(false);
			googleHybrid.setChecked(false);
		}
		if (v.getId() == R.id.cbx_google_hybrid && googleHybrid.isChecked()) {
			oaWinter.setChecked(false);
			googleMaps.setChecked(false);
		}
		updateMap();
	}

	private void updateMap() {
		if (map == null) {
			map = mapView.getMap();
		}

		if (map != null) {
			setMapLogo();
			setMapOverlays();
			setMapObjects();
		}
	}

	private void setMapLogo() {
		boolean oaChecked = oaSlope.isChecked() || oaWinter.isChecked();
		oaLogo.setVisibility(oaChecked ? View.VISIBLE : View.GONE);
	}

	private void setMapOverlays() {
		map.clear();
		map.setMapType(GoogleMap.MAP_TYPE_NONE);
		if (googleMaps.isChecked()) {
			map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		} else if (googleHybrid.isChecked()) {
			map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		} else if (oaWinter.isChecked()) {
			map.addTileOverlay(MapLayerFactory.outdooractiveWinter());
		}

		if (oaSlope.isChecked()) {
			map.addTileOverlay(MapLayerFactory.outdooractiveSkiresorts());
		}
	}

	private void setMapObjects() {
		String geometry = getArguments().getString("geometry");
		String startPosition = getArguments().getString("start");

		// add route
		if (geometry != null && geometry.length() > 0) {
			map.addPolyline(MapObjectFactory.createRoute(geometry));
		}

		// add point of interest
		if (startPosition != null && startPosition.length() > 0) {
			map.addMarker(MapObjectFactory.createMarker(startPosition));
		}
	}

	private void setCameraPosition() {
		String position = startPosition != null && startPosition.length() > 0 ? startPosition
				: geometry;
		if (position != null && position.length() > 0) {
			map.moveCamera(MapObjectFactory.updateCamera(position));
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		if (mapView != null) {
			mapView.onResume();
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		if (mapView != null) {
			mapView.onPause();
		}
	}

	@Override
	public void onDestroy() {
		if (mapView != null) {
			mapView.onDestroy();
		}
		super.onDestroy();
	}
}
