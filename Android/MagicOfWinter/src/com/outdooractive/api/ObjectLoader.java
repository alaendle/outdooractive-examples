package com.outdooractive.api;

import java.util.Locale;

import android.content.Context;

import com.outdooractive.api.WebLoaderTask.IWebResultListener;

public class ObjectLoader {

	private final String PROJECT_ID = "app-outdooractive-tage-2013-android";
	private final String PROJECT_KEY = "yourtest-outdoora-ctiveapi";

	public interface IObjectLoaderListener {
		public void onObjectLoaded(String json);
	}

	private Context context;
	private IObjectLoaderListener listener;

	public ObjectLoader(Context context) {
		this.context = context;
	}

	public void setListener(IObjectLoaderListener listener) {
		this.listener = listener;
	}

	public void loadTourCategories() {
		// http://www.outdooractive.com/api/project/app-outdooractive-tage-2013-android/category/tree/tour/pruned?lang=de&key=yourtest-outdoora-ctiveapi
		String request = String
				.format(Locale.GERMAN,
						"http://www.outdooractive.com/api/project/%s/category/tree/tour/pruned?lang=de&key=%s",
						PROJECT_ID, PROJECT_KEY);
		this.loadFromWeb(request);
	}

	public void loadTourList(int categoryId) {
		// http://www.outdooractive.com/api/project/app-outdooractive-tage-2013-android/category/1566501/oois?lang=de&display=minimal&categoryHandling=fallback&key=yourtest-outdoora-ctiveapi
		String request = String
				.format(Locale.GERMAN,
						"http://www.outdooractive.com/api/project/%s/category/%d/oois?lang=de&display=minimal&categoryHandling=fallback&key=%s",
						PROJECT_ID, categoryId, PROJECT_KEY);
		this.loadFromWeb(request);
	}

	public void loadTour(int tourId) {
		// http://www.outdooractive.com/api/project/app-outdooractive-tage-2013-android/oois/1358951?display=full&categoryHandling=fallback&key=yourtest-outdoora-ctiveapi
		String request = String
				.format(Locale.GERMAN,
						"http://www.outdooractive.com/api/project/%s/oois/%d?lang=de&display=full&categoryHandling=fallback&key=%s",
						PROJECT_ID, tourId, PROJECT_KEY);
		this.loadFromWeb(request);
	}

	private void loadFromWeb(String request) {
		new WebLoaderTask(this.context, new IWebResultListener() {
			@Override
			public void onResultLoaded(String result) {
				ObjectLoader.this.onWebResult(result);
			}
		}).loadFromWeb(request);
	}

	private void onWebResult(String result) {
		if (this.listener != null) {
			this.listener.onObjectLoaded(result);
		}
	}
}
