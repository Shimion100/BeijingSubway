package edu.muc.jxd.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.sabrina.dijkstra.Dijkstra;

import edu.muc.jxd.init.InitGraph;

/**
 * Servlet implementation class SearchLines
 * 
 * @param <K>
 */
public class SearchLines<K> extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchLines() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String info = new String(request.getParameter("info").getBytes(
				"iso8859-1"), "utf-8");
		System.out.println(info);
		String[] arrayStrings = info.split(",");
		String fromString = arrayStrings[0];
		String toString = arrayStrings[1];

		HashMap<String, String> nodeMap = InitGraph.getNodeMap();

		HashMap<String, String> IDNamenodeMap = InitGraph.getNodeMap();
		Iterator<String> iterator = nodeMap.keySet().iterator();
		while (iterator.hasNext()) {
			String name = iterator.next();
			IDNamenodeMap.put(nodeMap.get(name), name);
		}

		String startID = nodeMap.get(fromString);
		String endID = nodeMap.get(toString);
		System.out.println(startID + "----" + endID);
		Dijkstra dijkstra = new Dijkstra(InitGraph.getGraph(), startID);
		ArrayList<String> resuArrayList = dijkstra.getRouteFrom(startID, endID);
		System.out.println(resuArrayList);
		JSONObject jo = new JSONObject();
		jo.put("route", resuArrayList);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(jo);
		out.flush();
		out.close();
	}
}
