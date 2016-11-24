package edu.muc.jxd.url;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

public class Subway {

	/**
	 * 将流转化为字符串
	 * 
	 * @param inputStream
	 * @return
	 */
	public static String ConvertToString(InputStream inputStream) {
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		StringBuilder result = new StringBuilder();
		String line = null;
		try {
			while ((line = bufferedReader.readLine()) != null) {
				result.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				inputStreamReader.close();
				inputStream.close();
				bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result.toString();
	}

	/**
	 * 读文件
	 * 
	 * @param inputStream
	 * @return
	 */
	public static String ConvertToString(FileInputStream inputStream) {
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		StringBuilder result = new StringBuilder();
		String line = null;
		try {
			while ((line = bufferedReader.readLine()) != null) {
				result.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				inputStreamReader.close();
				inputStream.close();
				bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result.toString();
	}

	/**
	 * 将一个XML字符串转化为JSON
	 * 
	 * @param text
	 * @return
	 */
	public JSONObject getJSONFromXML(String text) {
		JSONObject jsonObject = null;
		XMLSerializer xmlSerializer = new XMLSerializer();
		jsonObject = (JSONObject) xmlSerializer.read(text);
		return jsonObject;
	}

	public ArrayList<JSONObject> getAllSubways() {
		ArrayList<JSONObject> list = new ArrayList<JSONObject>();
		for (int i = 1; i <= 40; i++) {
			URL url;
			try {
				url = new URL(
						"http://api.map.baidu.com/place/v2/search?ak=MS9nczGbGFj2sGnpFv5MBnPf&output=xml&query=地铁站&page_size=20&page_num="
								+ i + "&scope=1&region=北京");
				HttpURLConnection urlConnection = (HttpURLConnection) url
						.openConnection();
				urlConnection.setRequestMethod("GET");
				urlConnection.connect();
				InputStream inputStream = urlConnection.getInputStream();
				String responseStr = Subway.ConvertToString(inputStream);
				JSONObject jsonObject = getJSONFromXML(responseStr);
				list.add(jsonObject);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return list;
	}

	public static void main(String[] args) {
		Subway subway = new Subway();
		ArrayList<JSONObject> list = subway.getAllSubways();
		for (int i = 0; i < list.size(); i++) {
			JSONObject jo = list.get(i);
			if (jo.getInt("total") > 0) {
				System.out.println(jo.toString());
			}

		}
	}
}
