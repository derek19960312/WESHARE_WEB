package com.goodsorder.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.goodsorder.model.GoodsOrderService;
import com.goodsorder.model.GoodsOrderVO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


@WebServlet("/GoodsOrderServlet")
public class GoodsOrderServlet extends HttpServlet {

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
		
		
		if("find_my_good_by_memId".equals(action)) {
			
			
			String memId = req.getParameter("memId");
			GoodsOrderService goSvc = new GoodsOrderService();
			List<GoodsOrderVO> gvos = goSvc.findGoodByMemId(memId);
			
			out.print(gson.toJson(gvos));
			
			
		}
		
	}

}
