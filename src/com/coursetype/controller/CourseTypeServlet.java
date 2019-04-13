package com.coursetype.controller;

import java.io.*;
import java.sql.*;
import java.util.*;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.coursetype.model.CourseTypeDAO_interface;
import com.coursetype.model.CourseTypeJDBCDAO;
import com.coursetype.model.CourseTypeService;
import com.coursetype.model.CourseTypeVO;
import com.google.gson.Gson;

@WebServlet("/CourseType")
public class CourseTypeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Gson gson = new Gson();
		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		List<CourseTypeVO> courseTypes = new CourseTypeService().getAll();
		
		out.println(gson.toJson(courseTypes));	
		
		
	}

}
