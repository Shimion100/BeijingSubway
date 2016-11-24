package edu.muc.jxd.graph;

/**
 * 邻接链表的边
 * 
 * @author Shimon
 *
 */
public class Edge {

	// 起始节点
	private String starNodeID;

	// 结束节点
	private String endNodeID;

	// 权重
	private int weight;

	public String getStarNodeID() {
		return starNodeID;
	}

	public void setStarNodeID(String starNodeID) {
		this.starNodeID = starNodeID;
	}

	public String getEndNodeID() {
		return endNodeID;
	}

	public void setEndNodeID(String string) {
		this.endNodeID = string;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

}
