package com.coursereservation.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coursereservation.model.CourseReservationService;
import com.coursereservation.model.CourseReservationVO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@WebServlet("/CourseReservationServlet")
public class CourseReservationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		Gson gson = new GsonBuilder()  
				  .setDateFormat("yyyy-MM-dd HH:mm:ss")  
				  .create(); 
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = res.getWriter();

		String action = req.getParameter("action");
		System.out.println(action);
		
		//我的預約
		if("find_my_reservation".equals(action)) {
			
			String param = req.getParameter("param");
			System.out.println(param);
			CourseReservationService crSvc = new CourseReservationService();
			List<CourseReservationVO> crList = crSvc.findByPrimaryKey(param);
			out.print(gson.toJson(crList));
			
		}
		
		
		//預約
		if("make_new_reservation".equals(action)) {
			
		}
		//取消
		if("cancel_my_reservation".equals(action)) {
			
		}
		//完成預約
		if("finish_reservation".equals(action)) {
			
		}
	}

}
