package com.allforcars.all4cars.classes.apicall;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

public class VolleyStringRequest extends StringRequest {

	private Map<String, String> mRequestparams;
	public static final String mNetworkTag	= "Network";

	private VolleyStringRequest(int method, String url, UpdateListener updateListener, Map<String, String> params) {
		super(method, url, updateListener, updateListener);
		mRequestparams = params;
	}

	public static VolleyStringRequest doPost(String url, UpdateListener updateListener, Map<String, String> params) {
		if (BuildConfig.DEBUG) {
			Log.i(mNetworkTag, url);
			Log.i(mNetworkTag, params.toString());
		}
		return new VolleyStringRequest(Method.POST, url, updateListener, params);
	}

	public static VolleyStringRequest doGet(String url, UpdateListener updateListener) {
		return new VolleyStringRequest(Method.GET, url, updateListener, null);
	}

	@Override
	protected Map<String, String> getParams() throws AuthFailureError {
		return mRequestparams;
	}

}
