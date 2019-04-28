package com.withdrawalrecord.contoller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.member.model.MemberService;
import com.member.model.MemberVO;
import com.withdrawalrecord.model.*;

@WebServlet("/android/WithdrawalRecordServlet")
public class WithdrawalRecordServlet extends HttpServlet {


	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		Gson gson = new GsonBuilder()  
				  .setDateFormat("yyyy-MM-dd HH:mm:ss")  
				  .create(); 
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = res.getWriter();

		String action = req.getParameter("action");
		System.out.println(action);
		
		if("get_my_money_record".equals(action)) {
			
			String memId = req.getParameter("memId");
			WithdrawalRecordService wrSvc = new WithdrawalRecordService();
			List<WithdrawalRecordVO> wrVOs = wrSvc.findByKey(memId);
			
			out.print(gson.toJson(wrVOs));
			
		}
		

		

	}
}
