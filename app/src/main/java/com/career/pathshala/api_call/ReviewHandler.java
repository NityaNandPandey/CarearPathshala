package com.career.pathshala.api_call;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.util.Linkify;

public class ReviewHandler {
	private static String googleReviewURL = "https://play.google.com/store/apps/details?id=com.gk.currentaffairs";
	private static String amazonReviewURL = "http://goo.gl/V3UFSN";
	private static String samsungReviewUrl = "http://goo.gl/VmV5WW";
	

	@SuppressLint("WrongConstant")
	public static void startReview(Context context) {
		Intent browserIntent = new Intent("android.intent.action.VIEW",	Uri.parse(getReviewURL()));
		browserIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		browserIntent.setFlags(Linkify.WEB_URLS | Linkify.EMAIL_ADDRESSES);
		context.startActivity(browserIntent);
	}

	public static String getReviewURL() {

		if (StoreType.SAMSUNG == true) {
			return samsungReviewUrl;
		} else if (StoreType.AMAZON == true) {
			return amazonReviewURL;
		} else if(StoreType.OTHERS==true){
			return googleReviewURL; 
		}
		return googleReviewURL;
	}

}
