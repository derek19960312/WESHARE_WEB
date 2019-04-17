package com.member.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.member.model.*;
import com.teacher.model.TeacherService;
import com.teacher.model.TeacherVO;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

@WebServlet("/MemberServlet")
public class MemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		doGet(req, res);
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		
		Gson gson = new GsonBuilder()  
				  .setDateFormat("yyyy-MM-dd HH:mm:ss")  
				  .create();  
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/plain; charset=UTF-8");
		String action = req.getParameter("action");
		PrintWriter out = res.getWriter(); 
		
		
		
		
		
		if("get_one_by_android".equals(action)){
			
			
			String teacherId = req.getParameter("teacherId");
			
			TeacherService teacherSvc = new TeacherService();
			MemberService memberSvc = new MemberService();
			
			TeacherVO teacherVO= teacherSvc.findOneById(teacherId);
			String memId = teacherVO.getMemId();
			
			MemberVO memberVO= memberSvc.getOneMember(memId);
			memberVO.setMemImage(null);
			
			out.println(gson.toJson(memberVO));
	
		}
		


		if ("login".equals(action)) {
			final int SUCCESS = 0;
		    final int FALSE = 1;
		    MemberVO memberVO = null;
			String errorMsgs = null;
			JsonObject jsonString = new JsonObject();
			for(int i=0 ; i<1; i++) {
				try {
					
					String memId = req.getParameter("memId");
					if (memId == null || (memId.trim()).length() == 0) {
						errorMsgs = "NoAccount";
						break;
					}
	
					String memPsw = req.getParameter("memPsw");
					if (memPsw == null || (memPsw.trim()).length() == 0) {
						errorMsgs = "NoPassword";
						break;
					}
					
					MemberService memSvc = new MemberService();
					memberVO = memSvc.getOneMember(memId);
					memberVO.setMemImage(null);
					if (memberVO == null || !memPsw.equals(memberVO.getMemPsw())) {
						errorMsgs = "LoginFalse";
						break;
					}
				} catch (Exception e) {
					errorMsgs = "ConnectionProblem";
					System.out.println("ERROR");
					break;
				}
			}
			try {
				if(errorMsgs == null && memberVO != null) {
					out.println(gson.toJson(memberVO));
				}else {
					jsonString.addProperty("LoginStatus", FALSE);
					jsonString.addProperty("errorMsgs", errorMsgs);
					out.println(gson.toJson(jsonString));
				}
			}catch(Exception e){
				
			}
			
			

		}
		


	}

}
