package edu.muc.jxd.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import edu.muc.jxd.url.Subway;

/**
 * Servlet implementation class GetAllSubways
 */
public class GetAllSubways extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetAllSubways() {
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
		Subway subway = new Subway();
		ArrayList<JSONObject> list = subway.getAllSubways();
		ArrayList<Object> resultList = new ArrayList<Object>();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getInt("total") > 0) {
				JSONArray arryArray = list.get(i).getJSONArray("results");
				for (int j = 0; j < arryArray.size(); j++) {
					resultList.add(arryArray.get(j));
				}
			}

		}
		JSONObject jo = new JSONObject();
		jo.put("resultsList", resultList);
		System.out.println(jo);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(jo);
		out.flush();
		out.close();
	}
}
