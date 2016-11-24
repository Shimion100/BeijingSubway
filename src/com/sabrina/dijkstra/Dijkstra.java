package com.sabrina.dijkstra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import edu.muc.jxd.graph.Graph;
import edu.muc.jxd.graph.Node;
import edu.muc.jxd.graph.Edge;
import edu.muc.jxd.init.InitGraph;

public class Dijkstra {

	// 起始节点编号
	private String startNodeID;
	// 未被处理的节点集合
	private List<String> sourceNodeIDList = null;
	// 已被处理的节点集合
	private List<String> desNodeIDList = null;

	// '节点编号' 和 '起始节点与当前节点之间的最短路径' 的映射关系
	private Map<String, Double> nodeidShortestRouteMapping = null;
	// '节点编号' 和 '起始节点到当前节点之间的最短路径 的 上一跳节点编号' 的映射关系
	private Map<String, String> nodeidLastNodeidMapping = null;
	// '节点编号' 和 '节点对象'的 映射关系
	private Map<String, Node> idNodeMapping = null;

	private HashMap<String, List<String>> route = null;

	public Dijkstra(Graph graph, String startNodeID) {
		this.startNodeID = startNodeID;

		// 初始化
		sourceNodeIDList = new ArrayList<String>();
		desNodeIDList = new ArrayList<String>();
		nodeidShortestRouteMapping = new HashMap<String, Double>();
		nodeidLastNodeidMapping = new HashMap<String, String>();
		idNodeMapping = new HashMap<String, Node>();

		route = new HashMap<String, List<String>>();

		for (Node node : graph.getNodeList()) {
			if (node.getID().equals(startNodeID)) {
				desNodeIDList.add(startNodeID);
				// 起始节点到起始节点的最短路径长度为0
				nodeidShortestRouteMapping.put(startNodeID, 0d);
			} else {
				sourceNodeIDList.add(node.getID());
				// 非起始节点到起始节点的最短路径长度为 '无穷大'
				nodeidShortestRouteMapping.put(node.getID(), Double.MAX_VALUE);
			}
			// 设置到节点最短路径的上一跳节点为'null'
			nodeidLastNodeidMapping.put(node.getID(), null);
			idNodeMapping.put(node.getID(), node);
			List<String> aList = new ArrayList<String>();
			route.put(node.getID(), aList);
		}
	}

	public void start() {
		Node nextNode = null;
		Node currentNode = null;
		String nextNodeID = "";
		do {
			if (nextNode == null) {
				currentNode = (Node) idNodeMapping.get(startNodeID);
			} else {
				currentNode = nextNode;
			}
			nextNodeID = getNextNode(currentNode);
			if (null == nextNodeID || nextNodeID.length() == 0) {
				break;
			}
			nextNode = (Node) idNodeMapping.get(nextNodeID);

			sourceNodeIDList.remove(nextNode.getID());
			desNodeIDList.add(nextNode.getID());

		} while (sourceNodeIDList.size() > 0);
	}

	public String getNextNode(Node currentNode) {
		double shortestPath = Double.MAX_VALUE;
		String nextNodeID = "";
		// 遍历从当前节点出发的邻接节点
		for (Edge nextEdge : currentNode.getOutEdges()) {
			// 判断 '经过当前节点至邻接节点的距离' < '之前记录的从源节点出发到邻接节点的距离'
			if ((nextEdge.getWeight() + nodeidShortestRouteMapping
					.get(currentNode.getID())) < nodeidShortestRouteMapping
					.get(nextEdge.getEndNodeID())) {
				// 更新路由表
				nodeidShortestRouteMapping.put(
						nextEdge.getEndNodeID(),
						nextEdge.getWeight()
								+ nodeidShortestRouteMapping.get(nextEdge
										.getStarNodeID()));

				nodeidLastNodeidMapping.put(nextEdge.getEndNodeID(),
						currentNode.getID());
			}
		}
		// 从未被处理过的节点集合中，取出权值最小的节点
		for (String nodeID : sourceNodeIDList) {
			if (nodeidShortestRouteMapping.get(nodeID) < shortestPath) {
				shortestPath = nodeidShortestRouteMapping.get(nodeID);
				nextNodeID = nodeID;
			}
		}
		if (sourceNodeIDList.size() == 1) // 从未被处理过的节点集合中，取出最后一个节点
			return sourceNodeIDList.get(0);
		return nextNodeID;
	}

	public Map<String, Double> getNodeidShortestRouteMapping() {
		return nodeidShortestRouteMapping;
	}

	public HashMap<String, List<String>> getRoute() {
		return route;
	}

	public void setRoute(HashMap<String, List<String>> route) {
		this.route = route;
	}

	public Map<String, String> getNodeidLastNodeidMapping() {
		return nodeidLastNodeidMapping;
	}

	public ArrayList<String> getRouteFrom(String starStringID, String endID) {

		ArrayList<String> resultList = new ArrayList<String>();

		/*
		 * Graph graph = new Graph(); graph.init();
		 */
		Graph graph = InitGraph.getGraph();

		HashMap<String, String> nodeMap = InitGraph.getNodeMap();

		HashMap<String, String> IDNamenodeMap = InitGraph.getNodeMap();
		Iterator<String> iterator = nodeMap.keySet().iterator();
		while (iterator.hasNext()) {
			String name = iterator.next();
			IDNamenodeMap.put(nodeMap.get(name), name);
		}

		Dijkstra dijkstra = new Dijkstra(graph, starStringID);
		dijkstra.start();

		for (String aList : dijkstra.getRoute().keySet()) {
			List<String> routList = dijkstra.getRoute().get(aList);
			String preSting = aList;
			while (null != preSting && !preSting.equals(starStringID)
					&& preSting.length() > 0) {
				preSting = dijkstra.getNodeidLastNodeidMapping().get(preSting);
				routList.add(preSting);
			}

		}
		for (String aList : dijkstra.getRoute().keySet()) {
			List<String> routList = dijkstra.getRoute().get(aList);

			if (aList.equals(endID)) {
				System.out.print("到'" + IDNamenodeMap.get(aList) + "'的路径是 ");
				resultList.add(IDNamenodeMap.get(aList));
				for (String string : routList) {
					System.out.print(IDNamenodeMap.get(string) + "--");
					resultList.add(IDNamenodeMap.get(string));
				}
			}

			System.out.println();
		}

		return resultList;
	}

	public static void main(String[] args) {
		Dijkstra dijkstra = new Dijkstra(InitGraph.getGraph(), "10");
		dijkstra.getRouteFrom("10", "78");

	}
}