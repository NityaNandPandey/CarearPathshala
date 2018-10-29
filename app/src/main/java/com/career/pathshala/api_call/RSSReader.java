package com.career.pathshala.api_call;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.StrictMode;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

@SuppressLint("NewApi")
public class RSSReader {
	DefaultHttpClient httpClient = new DefaultHttpClient();

	public Document getRSSFromServer(String url) {
		Document response = null;

		response = getDomFromXMLString(getFeedFromServer(url));
		return response;
	}

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	private String getFeedFromServer(String url) {
		String xml = null;
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		try {
			Log.e("rssreader","try");
			HttpGet httpget =new HttpGet(url);
			Log.e("rssreader","try1");
			//HttpPost httpPost = new HttpPost(url);
			HttpResponse httpResponse = httpClient.execute(httpget);
			Log.e("rssreader","try2");
			HttpEntity httpEntity = httpResponse.getEntity();
			Log.e("rssreader","try3");
			xml = EntityUtils.toString(httpEntity);
			Log.e("...","xml= "+xml);
		} catch (Exception e) {
			Log.e("...","**************************************error= "+e.getMessage());
			e.printStackTrace();
			Log.e("","********************");
		}
		return xml;
	}

	private Document getDomFromXMLString(String xml) {
		Document doc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xml));
			doc = db.parse(is);

		} catch (Exception e) {
		}
		return doc;
	}

	public String getValue(Element item, String key) {
		NodeList nodeList = item.getElementsByTagName(key);
		return this.getElementValue(nodeList.item(0));
	}

	public final String getElementValue(Node node) {
		Node child;
		if (node != null) {
			if (node.hasChildNodes()) {
				for (child = node.getFirstChild(); child != null; child = child
						.getNextSibling()) {
					if (child.getNodeType() == Node.TEXT_NODE) {
						return child.getNodeValue();
					}
				}
			}
		}
		return "";
	}
}
