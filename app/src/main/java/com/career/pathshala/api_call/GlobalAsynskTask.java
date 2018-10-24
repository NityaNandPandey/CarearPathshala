package com.career.pathshala.api_call;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;


public class GlobalAsynskTask extends AsyncTask<String, Void, String> {

    private ProgressDialog progressDialog = null;
    private String progressMessage = "";
    private Context context;
    private String url = "";
   private List<NameValuePair> list;
    private MyServiceListener myServiceListener;

    public GlobalAsynskTask(Context context, String progessMsg, String url, List<NameValuePair> list, MyServiceListener listener) {
        this.context = context;
        this.progressMessage = progessMsg;
        this.url = url;
        this.myServiceListener = listener;
        this.list = list;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (progressMessage.equals("")) {
            // progressDialog = new ProgressDialog(context);
//            Don't show progress dialog
        } else {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage(progressMessage);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }

    @Override
    protected String doInBackground(String... params) {
//        String response = "";
//        try {
//            DefaultHttpClient client = new DefaultHttpClient();
//            HttpPost httpPost = new HttpPost(url);
//            httpPost.setEntity(new UrlEncodedFormEntity(list));
//            HttpResponse httpResponse = client.execute(httpPost);
//            int statusCode = httpResponse.getStatusLine().getStatusCode();
//
//            if (statusCode == 200) {
//                HttpEntity httpEntity = httpResponse.getEntity();
//                response = EntityUtils.toString(httpEntity);
//            }
//
//            return response;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;


        String strReturn = "";
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(list, "UTF-8"));
            HttpResponse httpResponse = new DefaultHttpClient().execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            InputStream is = httpEntity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            is.close();
            strReturn = sb.toString();

            return strReturn;
        } catch (Exception e) {
            Log.e("postEx", e.getMessage());
        }
        Log.e("return post", strReturn);
        return null;


    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (result != null) {
            myServiceListener.onSuccess(result);

        } else {
            myServiceListener.onFailed();
        }

        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }


}

