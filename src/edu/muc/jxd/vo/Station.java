package edu.muc.jxd.vo;

/**
 * 描述地铁站的类
 * 
 * @author Shimon
 *
 */
public class Station {

	// 编号
	private String id;

	// 站点名字
	private String name;

	// 所在的路线名
	private String line;

	// 经度
	private String lng;

	// 维度
	private String lat;

	/**
	 * 构造方法
	 */
	public Station() {
		// TODO Auto-generated constructor stub
	}

	public Station(String name) {
		this.name = name;
		// TODO Auto-generated constructor stub
	}

	/*
	 * Getters and Setters
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public String getLat() {
		return lat;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

}
