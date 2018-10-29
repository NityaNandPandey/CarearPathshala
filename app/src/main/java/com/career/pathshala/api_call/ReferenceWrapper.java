package com.career.pathshala.api_call;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class ReferenceWrapper {
	public static String arr_userans[]=new String[30];
	

	public static ReferenceWrapper referenceWrapper;

	public ReferenceWrapper(Activity activity) {
		// TODO Auto-generated constructor stub
	}

	public static ReferenceWrapper getReferenceWrapper(Activity activity){
		if(referenceWrapper==null){
			referenceWrapper=new ReferenceWrapper(activity);
		}
		return referenceWrapper;
	}

	public String getRecordStore(Activity activity, String key){
		SharedPreferences prefs = activity.getSharedPreferences(activity.getPackageName(), Context.MODE_PRIVATE);
		return prefs.getString(key, null);
	}

	public void addRecordStore(Activity activity, String key, String value){
		SharedPreferences prefs = activity.getSharedPreferences(activity.getPackageName(), Context.MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putString(key,value);
		editor.commit();
	}

	public static String[] getArr_ans() {
		return arr_userans;
	}

	public static void setArr_ans(String[] arr_ans) {
		ReferenceWrapper.arr_userans = arr_ans;
	}
	
	
}
