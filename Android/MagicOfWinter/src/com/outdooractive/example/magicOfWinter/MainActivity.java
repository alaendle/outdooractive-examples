package com.outdooractive.example.magicOfWinter;

import org.json.JSONObject;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.Window;

import com.outdooractive.api.CategoryItem;
import com.outdooractive.api.ObjectLoader;
import com.outdooractive.api.ObjectLoader.IObjectLoaderListener;
import com.outdooractive.api.Tour;
import com.outdooractive.api.TourList.TourHeader;
import com.outdooractive.map.MapViewFragment;

public class MainActivity extends Activity implements IActionListener {

	private CategoryItem categoryRoot;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		getActionBar().setDisplayShowHomeEnabled(false);
		getActionBar().hide();

		setContentView(R.layout.main_activity);

		if (savedInstanceState != null) {
			return;
		}

		getFragmentManager().beginTransaction()
				.add(R.id.fragment_container, new IntroFragment()).commit();
	}

	@Override
	public void onOpenMapRequest() {
		this.onOpenMapRequest(null);
	}

	@Override
	public void onOpenMapRequest(Tour tour) {
		Bundle args = new Bundle();
		args.putString("start", tour != null ? tour.getStartingPoint() : "");
		args.putString("geometry", tour != null ? tour.getGeometry() : "");
		args.putBoolean("winter", tour != null ? tour.isWinterTour() : false);

		Fragment mapViewFragment = new MapViewFragment();
		mapViewFragment.setArguments(args);

		this.setFragment(mapViewFragment);
	}

	@Override
	public void onOpenTourCategoriesRequest() {
		if (this.categoryRoot != null) {
			openCategoryList(categoryRoot);
		} else {
			ObjectLoader objectLoader = new ObjectLoader(this);
			objectLoader.setListener(new IObjectLoaderListener() {
				@Override
				public void onObjectLoaded(JSONObject object) {
					categoryRoot = new CategoryItem(object);
					openCategoryList(categoryRoot);
				}
			});
			objectLoader.loadTourCategories();
		}
	}

	@Override
	public void onOpenCategoryRequest(int categoryId) {
		CategoryItem root = this.categoryRoot.findById(categoryId);
		if (root == null) {
			return;
		}

		if (root.hasChildren()) {
			this.openCategoryList(root);
		} else {
			this.openTourList(root);
		}
	}

	@Override
	public void onOpenTourDetailsRequest(TourHeader tourHeader) {
		Bundle args = new Bundle();
		args.putInt("tourId", tourHeader.getId());
		args.putString("tourTitle", tourHeader.getTitle());

		Fragment tourDetailsFragment = new TourDetailsFragment();
		tourDetailsFragment.setArguments(args);

		this.setFragment(tourDetailsFragment);
	}

	private void openCategoryList(CategoryItem root) {
		String header = root.getId() == 0 ? getString(R.string.action_tours)
				: root.getName();

		Bundle args = new Bundle();
		args.putString("header", header);
		args.putIntegerArrayList("categoryIds", root.getChildrenIds());
		args.putStringArrayList("categoryNames", root.getChildrenNames());

		Fragment categoryListFragment = new CategoryListFragment();
		categoryListFragment.setArguments(args);

		this.setFragment(categoryListFragment);
	}

	private void openTourList(CategoryItem parent) {
		Bundle args = new Bundle();
		args.putString("header", parent.getName());
		args.putInt("categoryId", parent.getId());

		Fragment tourListFragment = new TourListFragment();
		tourListFragment.setArguments(args);

		this.setFragment(tourListFragment);
	}

	private void setFragment(Fragment fragment) {
		getFragmentManager().beginTransaction()
				.replace(R.id.fragment_container, fragment)
				.addToBackStack(null).commit();
	}
}
