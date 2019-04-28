package com.goodslike.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.goods.model.GoodsService;
import com.goods.model.GoodsVO;
import com.goodslike.model.GoodsLikeService;
import com.goodslike.model.GoodsLikeVO;
import com.google.gson.Gson;

@WebServlet("/android/GoodsLikeServlet")
public class GoodsLikeServlet extends HttpServlet {

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

		// 加入收藏
		if ("add_to_favorites".equals(action)) {
			String goodId = req.getParameter("goodId");
			String memId = req.getParameter("memId");
			GoodsLikeService glSvc = new GoodsLikeService();

			glSvc.addGoodsLike(memId, goodId);

		}

		// 移除收藏
		if ("delete_from_favorites".equals(action)) {
			String goodId = req.getParameter("goodId");
			String memId = req.getParameter("memId");
			GoodsLikeService glSvc = new GoodsLikeService();
			glSvc.deleteGoodsLike(memId, goodId);
		}

		// 查看我的收藏
		if ("look_my_favorites".equals(action)) {
			String memId = req.getParameter("memId");
			GoodsLikeService glSvc = new GoodsLikeService();
			List<GoodsLikeVO> goodsLikeVOs = glSvc.getMemIdGoodsLike(memId);
			System.out.println(memId);
			
			GoodsService goodsSvc = new GoodsService();
			List<GoodsVO> GoodsVOs = new ArrayList<>();

			for(GoodsLikeVO glvo : goodsLikeVOs) {
				GoodsVO gvo = goodsSvc.getOneGood(glvo.getGoodId());
				gvo.setGoodImg(null);
				GoodsVOs.add(gvo);
			}
			
			out.print(gson.toJson(GoodsVOs));

		}

	}

}
