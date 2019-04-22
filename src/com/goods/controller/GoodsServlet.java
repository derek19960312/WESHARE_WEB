package com.goods.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.goods.model.GoodsService;
import com.goods.model.GoodsVO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


@WebServlet("/GoodsServlet")
public class GoodsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       


	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}


	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		Gson gson = new Gson();  
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = res.getWriter();

		String action = req.getParameter("action");
		
		
		if("get_one_by_Id".equals(action)) {
			
			String goodsId = req.getParameter("goodsId");
			
			GoodsService goodsSvc = new GoodsService();
			
			GoodsVO goodsVO = goodsSvc.getOneGood(goodsId);
			goodsVO.setGoodImg(null);
			out.print(gson.toJson(goodsVO));
			
			
		}
		
		if("get_all".equals(action)) {
			
			
			GoodsService goodsSvc = new GoodsService();
			List<GoodsVO> goodsVOs = goodsSvc.getAll();
			for(GoodsVO gdVO : goodsVOs) {
				gdVO.setGoodImg(null);
			}
			out.print(gson.toJson(goodsVOs));
		}
		
		
		
		
	}

}
