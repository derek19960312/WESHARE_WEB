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

import com.course.model.CourseService;
import com.course.model.CourseVO;
import com.coursetype.model.CourseTypeDAO_interface;
import com.coursetype.model.CourseTypeJDBCDAO;
import com.coursetype.model.CourseTypeService;
import com.coursetype.model.CourseTypeVO;
import com.google.gson.Gson;
import com.inscourse.model.InsCourseService;
import com.inscourse.model.InsCourseVO;
import com.member.model.MemberService;
import com.member.model.MemberVO;
import com.teacher.model.TeacherService;
import com.teacher.model.TeacherVO;

@WebServlet("/CourseTypeServlet")
public class CourseTypeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		Gson gson = new Gson();
		res.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = res.getWriter();
		
		String action = req.getParameter("action");
		
		if("get_all_type".equals(action)) {
			List<CourseTypeVO> courseTypes = new CourseTypeService().getAll();
			out.println(gson.toJson(courseTypes));	
		}
		
		if("find_by_coursetype".equals(action)) {
			
			CourseService courseSvc = new CourseService();
			Integer courseTypeId = new Integer(req.getParameter("courseTypeId"));
			
			List<CourseVO> courseVOs = courseSvc.finByCourseType(courseTypeId);
			
			List<InsCourseVO> insCourseVOs = new ArrayList();
			
			
			//查詢對應課程
			for(CourseVO cvo : courseVOs) {
				
				InsCourseService insCourseSvc = new InsCourseService();
				List<InsCourseVO> insCourseVOmass = insCourseSvc.findByCourse(cvo.getCourseId());
				insCourseVOs.addAll(insCourseVOmass);
				
			}
			
			
			
			
			//去除工程師資料
			for(InsCourseVO icvo : insCourseVOs) {
				
				for(CourseVO cvo : courseVOs) {
					if(icvo.getCourseId().equals(cvo.getCourseId())) {
						icvo.setCourseId(cvo.getCourseName());
					}
				}
			}
			
		
			
			
			out.println(gson.toJson(insCourseVOs));
			
		}
		
	}

}
