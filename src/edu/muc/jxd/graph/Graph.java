package edu.muc.jxd.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Shimon
 *
 */
public class Graph {

	// 节点链表
	private List<Node> nodeList;

	/**
	 * 构造方法
	 */
	public Graph() {
		this.nodeList = new ArrayList<Node>();
	}

	public List<Node> getNodeList() {
		return nodeList;
	}

	public void setNodeList(List<Node> nodeList) {
		this.nodeList = nodeList;
	}

}
