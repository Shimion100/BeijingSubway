package edu.muc.jxd.graph;

import java.util.ArrayList;
import java.util.List;

import edu.muc.jxd.vo.Station;

public class Node {
	private String ID;

	private Station station;

	private List<Edge> outEdges;

	public Node() {
		this.outEdges = new ArrayList<Edge>();
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}

	public List<Edge> getOutEdges() {
		return outEdges;
	}

	public void setOutEdges(List<Edge> outEdges) {
		this.outEdges = outEdges;
	}

}
