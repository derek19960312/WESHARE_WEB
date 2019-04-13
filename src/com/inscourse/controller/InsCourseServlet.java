package com.inscourse.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.course.model.CourseVO;
import com.google.gson.Gson;
import com.inscourse.model.InsCourseService;
import com.inscourse.model.InsCourseVO;


@WebServlet("/InsCourseServlet")
public class InsCourseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8"); 
		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		String action = request.getParameter("action");
		
		
		if("search_by_CourseType".equals(action)) {
			List<String> erroMsgs = new LinkedList();
			
			String courseId = request.getParameter("keyword");
			InsCourseService insCourseService = new InsCourseService();
			//insCourseService.findByCourse(courseId);
			List<InsCourseVO> insCourseVOs = insCourseService.findByCourse("0005");
			
			Gson gson = new Gson();
			out.print(gson.toJson(insCourseVOs));
		}
	}

}
