package com.outdooractive.example.magicOfWinter;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.outdooractive.api.ImageLoaderTask;
import com.outdooractive.api.ImageLoaderTask.IImageResultListener;
import com.outdooractive.api.ObjectLoader;
import com.outdooractive.api.ObjectLoader.IObjectLoaderListener;
import com.outdooractive.api.Tour;

public class TourDetailsFragment extends Fragment {

	private ImageView imageView;
	private TextView descriptionTextView;
	private TextView authorTextView;
	private TextView sourceTextView;
	private Button openMapButton;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		getActivity().getActionBar().setTitle(R.string.tour_details);
		getActivity().getActionBar().show();

		View view = inflater.inflate(R.layout.tour_details_fragment, container,
				false);

		// set tour title
		TextView titleView = (TextView) view
				.findViewById(R.id.details_title_view);
		titleView.setText(getArguments().getString("tourTitle"));

		// get views that will be adjusted after tour details have been loaded
		imageView = (ImageView) view.findViewById(R.id.tour_image);
		openMapButton = (Button) view.findViewById(R.id.btn_map_with_tour);
		descriptionTextView = (TextView) view
				.findViewById(R.id.details_text_view);
		authorTextView = (TextView) view.findViewById(R.id.author_text_view);
		sourceTextView = (TextView) view.findViewById(R.id.source_text_view);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// load the tour details
		ObjectLoader objectLoader = new ObjectLoader(this.getActivity());
		objectLoader.setListener(new IObjectLoaderListener() {
			@Override
			public void onObjectLoaded(String object) {
				TourDetailsFragment.this.setTour(new Tour(object));
			}
		});
		objectLoader.loadTour(getArguments().getInt("tourId"));
	}

	private void setTour(final Tour tour) {
		// set image
		new ImageLoaderTask(new IImageResultListener() {
			@Override
			public void onImageLoaded(Drawable image) {
				imageView.setImageDrawable(image);
			}
		}).loadFromWeb(tour.imageId());

		// handle button click to view tour in map
		openMapButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				((IActionListener) getActivity()).onOpenMapRequest(tour);
			}
		});

		// set description, author, and source
		descriptionTextView.setText(tour.longText());
		authorTextView.setText(tour.author());
		sourceTextView.setText(tour.source());
	}
}
