package edu.muc.jxd.init;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import edu.muc.jxd.graph.Edge;
import edu.muc.jxd.graph.Graph;
import edu.muc.jxd.graph.Node;
import edu.muc.jxd.vo.Station;

public class InitGraph {

	public static Graph getGraph() {
		Graph graph = new Graph();
		HashMap<String, String> nodeMap = getNodeMap();
		HashMap<String, List<String>> graphMap = getGraphMap();

		for (String aNameString : graphMap.keySet()) {
			Node node = new Node();
			node.setID(nodeMap.get(aNameString));
			List<String> edgeList = graphMap.get(aNameString);
			List<Edge> edges = new ArrayList<Edge>();
			/**
			 * 获取边集合
			 */
			for (String string : edgeList) {
				Edge edge = new Edge();
				edge.setWeight(1);
				// 起始节点是本节点
				edge.setStarNodeID(nodeMap.get(aNameString.trim()));
				// 结束节点时edge的节点
				edge.setEndNodeID(nodeMap.get(string.trim()));
				edges.add(edge);
			}
			node.setOutEdges(edges);
			graph.getNodeList().add(node);

			/**
			 * 注意，没有填写station
			 */
		}
		return graph;
	}

	public static ArrayList<String> getAllStrings(File file,
			String charSetString) {
		InputStream inputStream = null;
		InputStreamReader reader = null;
		BufferedReader buffReader = null;
		ArrayList<String> bigList = new ArrayList<String>();
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
				bigList.add(line);
				System.out.println(line);
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

	public static void createXML() {
		/*
		 * 获取所有的Station
		 */
		ArrayList<Station> stations = InitFromFile.getContentOfFile(new File(
				"E:" + File.separator + "station.txt"), "gbk");

		/**
		 * 然后开始读取其左右的信息，开始生成NodeList
		 */
		HashMap<String, Station> nodeMap = new HashMap<String, Station>();
		for (Station station : stations) {
			nodeMap.put(station.getName(), station);
		}
		/*
		 * 用于生成edge的内容
		 */
		ArrayList<String> lineList = getAllStrings(new File("E:"
				+ File.separator + "station.txt"), "gbk");

		Document document = DocumentHelper.createDocument();

		Document stationDocument = DocumentHelper.createDocument();

		Element rootStationElement = stationDocument.addElement("stationList");

		// 生成根节点
		Element rootElement = document.addElement("graph");
		// 生成数据列表
		Element dataListElement = rootElement.addElement("nodeList");

		int id = 1;

		for (String nameString : nodeMap.keySet()) {
			Element aNodeElement = dataListElement.addElement("node");
			Element idElement = aNodeElement.addElement("id");
			idElement.setText("" + id);
			Element aaStationElement = rootStationElement.addElement("staiont");
			Element aaIDElement = aaStationElement.addElement("id");
			aaIDElement.setText("" + id);
			Element aanameElement = aaStationElement.addElement("name");
			aanameElement.setText(nameString);
			id++;
			Element namElement = aNodeElement.addElement("name");
			namElement.setText(nameString);
			Element edgeListElement = aNodeElement.addElement("edgeList");
			/*
			 * 获取所有的 Edge
			 */
			for (int i = 0; i < lineList.size(); i++) {
				String lineString = lineList.get(i);
				// 在此行找到了String
				if (lineString.indexOf(nameString) > -1) {
					System.out.println("在此行找到了String");
					String[] strs = lineString.split(" ");
					for (int j = 0; j < strs.length; j++) {
						if (strs[j].equals(nameString)) {

							if (j != 0) {
								Element edgeElement = edgeListElement
										.addElement("edge");
								Element edgeIdElementLeft = edgeElement
										.addElement("id");
								edgeIdElementLeft.setText("");
								Element edgeNameElementLeft = edgeElement
										.addElement("name");
								edgeNameElementLeft.setText(strs[j - 1]);
							}
							if (j != strs.length - 1) {
								Element edgeElement = edgeListElement
										.addElement("edge");
								Element edgeIdElementRight = edgeElement
										.addElement("id");
								edgeIdElementRight.setText("");
								Element edgeNameElementRight = edgeElement
										.addElement("name");
								edgeNameElementRight.setText(strs[j + 1]);
							}
						}
					}
				}

			}
		}
		/**
		 * 写出测试
		 */
		OutputStream os;
		try {
			os = new FileOutputStream("E:" + File.separator
					+ "SqlMapConfig.xml");
			XMLWriter writer = new XMLWriter(os,
					OutputFormat.createPrettyPrint());

			writer.write(document);
			writer.close();

			os = new FileOutputStream("E:" + File.separator + "stations.xml");
			XMLWriter writer2 = new XMLWriter(os,
					OutputFormat.createPrettyPrint());

			writer2.write(stationDocument);
			writer2.close();

		} catch (FileNotFoundException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) { //
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 返回保存 {name， ID}的Map
	 * 
	 * @return
	 */
	public static HashMap<String, String> getNodeMap() {
		HashMap<String, String> nodeMap = new HashMap<String, String>();
		File xmlFile = new File("E:" + File.separator + "stations.xml");
		if (xmlFile.exists() && xmlFile.isFile()) {
			SAXReader reader = new SAXReader();
			Document document = null;
			try {
				document = reader.read(xmlFile);
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 获取根节点
			Element root = document.getRootElement();
			/*
			 * 获取表名
			 */
			for (Iterator<Element> iterator = root.elementIterator("staiont"); iterator
					.hasNext();) {
				Element stationElement = iterator.next();
				nodeMap.put(stationElement.elementText("name").trim(),
						stationElement.elementText("id").trim());
			}
		}
		return nodeMap;
	}

	/**
	 * 返回{name,{edge,edge}}
	 * 
	 * @return
	 */
	public static HashMap<String, List<String>> getGraphMap() {
		HashMap<String, List<String>> graphMap = new HashMap<String, List<String>>();
		File xmlFile = new File("E:" + File.separator + "graph.xml");
		if (xmlFile.exists() && xmlFile.isFile()) {
			SAXReader reader = new SAXReader();
			Document document = null;
			try {
				document = reader.read(xmlFile);
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 获取根节点
			Element root = document.getRootElement();
			Element nodeList = root.element("nodeList");
			for (Iterator<Element> iterator = nodeList.elementIterator("node"); iterator
					.hasNext();) {
				Element nodeElement = iterator.next();
				Element nameElement = nodeElement.element("name");
				List<String> edgeList = new ArrayList<String>();
				for (Iterator<Element> iterator2 = nodeElement.element(
						"edgeList").elementIterator("edge"); iterator2
						.hasNext();) {
					Element edge = iterator2.next();
					edgeList.add(edge.elementText("name"));

				}
				graphMap.put(nameElement.getText(), edgeList);
			}

		}

		return graphMap;
	}

	public static void main(String[] args) {
		Graph graph = getGraph();
		System.out.println(graph.getNodeList().get(5).getOutEdges().get(0)
				.getEndNodeID());

		// createXML();
	}
}
