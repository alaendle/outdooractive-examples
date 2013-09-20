package com.outdooractive.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.util.Log;

import com.outdooractive.example.magicOfWinter.R;

public class WebLoaderTask extends AsyncTask<String, Void, JSONObject> {

	public interface IWebResultListener {
		public void onResultLoaded(JSONObject result);
	}

	private ProgressDialog progressDialog;
	private Context context;
	private IWebResultListener listener;

	public WebLoaderTask(Context context, IWebResultListener listener) {
		this.context = context;
		this.listener = listener;
	}

	public void loadFromWeb(String request) {
		this.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, request);
	}

	@Override
	protected void onPreExecute() {
		progressDialog = ProgressDialog.show(this.context, "",
				this.context.getText(R.string.loading_data), true, true,
				new OnCancelListener() {
					public void onCancel(DialogInterface arg0) {
						arg0.dismiss();
					}
				});
	}

	@Override
	protected JSONObject doInBackground(String... params) {
		String request = params[0];
		Log.i("WebLoaderTask", "Request: " + request);

		HttpGet httpGet = new HttpGet(request);
		httpGet.addHeader("Accept", "application/json");
		httpGet.addHeader("User-Agent", "Android Test OA");

		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(entity.getContent()));

				String line;
				StringBuilder builder = new StringBuilder();
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}

				String resultString = builder.substring(0);
				Log.i("WebLoaderTask", "Result: " + resultString);
				return new JSONObject(resultString);
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(JSONObject result) {
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
		}

		if (result != null && this.listener != null) {
			this.listener.onResultLoaded(result);
		}
	}
}
