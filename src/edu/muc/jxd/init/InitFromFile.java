package edu.muc.jxd.init;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import edu.muc.jxd.vo.Station;

public class InitFromFile {
	/**
	 * 获取所有的station
	 * 
	 * @param file
	 * @return
	 */
	public static ArrayList<Station> getContentOfFile(File file,
			String charSetString) {
		InputStream inputStream = null;
		InputStreamReader reader = null;
		BufferedReader buffReader = null;
		ArrayList<Station> smallList = new ArrayList<Station>();
		ArrayList<Station> bigList = new ArrayList<Station>();
		try {
			inputStream = new FileInputStream(file);
			reader = new InputStreamReader(inputStream, charSetString);
			buffReader = new BufferedReader(reader);
			String line = "";
			line = buffReader.readLine();
			if (null == line || line.equals(null)) {
				return null;
			}

			while (null != line && line.length() > 0) {
				smallList = getAllStations(line);
				System.out.println("size=" + smallList.size());
				bigList.addAll(smallList);
				line = buffReader.readLine();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				buffReader.close();
				reader.close();
				inputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return bigList;
	}

	/**
	 * 对一 行，也就是一条线，获取其路线
	 * 
	 * @param textString
	 * @return
	 */
	public static ArrayList<Station> getAllStations(String textString) {
		ArrayList<Station> list = new ArrayList<Station>();
		System.out.println(textString);
		String[] stationStrings = textString.split(" ");
		for (int i = 0; i < stationStrings.length; i++) {
			Station aStation = new Station();
			aStation.setName(stationStrings[i]);
			list.add(aStation);
		}
		return list;
	}

	public static void main(String[] args) {
		/*
		 * 测试获取所有的Station
		 */
		ArrayList<Station> stations = InitFromFile.getContentOfFile(new File(
				"E:" + File.separator + "station.txt"), "gbk");
		for (int i = 0; i < stations.size(); i++) {
			System.out.println(stations.get(i).getName());
		}

	}

}
