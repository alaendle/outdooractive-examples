package com.outdooractive.api;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

public class ImageLoaderTask extends AsyncTask<String, Void, Drawable> {

	public interface IImageResultListener {
		public void onImageLoaded(Drawable image);
	}

	private IImageResultListener listener;

	public ImageLoaderTask(IImageResultListener listener) {
		this.listener = listener;
	}

	public void loadFromWeb(String imageId) {
		this.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, imageId);
	}

	@Override
	protected Drawable doInBackground(String... params) {
		String imageId = params[0];

		// http://img.oastatic.com/img/400/400//1252046/t
		String imageUrl = String.format(Locale.GERMAN,
				"http://img.oastatic.com/img/%d/%d/%s/%s/t", 400, 400, "",
				imageId);

		Log.i("ImageLoaderTask", "Loading image: " + imageUrl);
		try {
			InputStream stream = (InputStream) new URL(imageUrl).getContent();
			return Drawable.createFromStream(stream, "tour_image_" + imageId);
		} catch (MalformedURLException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	protected void onPostExecute(Drawable result) {
		if (result != null && this.listener != null) {
			this.listener.onImageLoaded(result);
		}
	}
}
