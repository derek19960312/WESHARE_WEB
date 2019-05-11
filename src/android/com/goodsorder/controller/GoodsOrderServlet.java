package android.com.goodsorder.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.sql.Date;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import android.com.goods.model.GoodsService;
import android.com.goods.model.GoodsVO;
import android.com.goodsdetails.model.GoodsDetailsService;
import android.com.goodsdetails.model.GoodsDetailsVO;
import android.com.goodsorder.model.GoodsOrderService;
import android.com.goodsorder.model.GoodsOrderVO;
import android.com.member.model.MemberService;
import android.com.member.model.MemberVO;
import android.com.withdrawalrecord.model.WithdrawalRecordService;

@WebServlet("/android/GoodsOrderServlet")
public class GoodsOrderServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		Gson gson = new GsonBuilder()

				.setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = res.getWriter();
		String action = req.getParameter("action");

		if ("find_good_order_by_TeacherId".equals(action)) {

			String teacherId = req.getParameter("teacherId");

			GoodsService goodSvc = new GoodsService();
			List<GoodsVO> myGoods = goodSvc.getByTeacherId(teacherId);

			GoodsDetailsService goodDetailSvc = new GoodsDetailsService();

			List<GoodsDetailsVO> goodsdetailsVO = new ArrayList<>();
			myGoods.stream().map(goods -> goods.getGoodId()).map(gId -> goodDetailSvc.findByGoodId(gId))
					.forEach(g -> goodsdetailsVO.addAll(g));
			
			
			GoodsOrderService goSvc = new GoodsOrderService();			
			
			Map<GoodsOrderVO, List<GoodsDetailsVO>> goodDetailMap = goodsdetailsVO.stream()
					.collect(Collectors.groupingBy(gdvo -> (GoodsOrderVO)goSvc.findGoodByMemId(gdvo.getGoodOrderId())));
			
			
			
			Gson gon = new GsonBuilder()
					.enableComplexMapKeySerialization()
					.setDateFormat("yyyy-MM-dd HH:mm:ss").create();
			
			out.print(gon.toJson(goodDetailMap));
			

		}

		if ("find_my_good_by_memId".equals(action)) {

			String memId = req.getParameter("memId");
			GoodsOrderService goSvc = new GoodsOrderService();
			List<GoodsOrderVO> gvos = goSvc.findGoodByMemId(memId);

			out.print(gson.toJson(gvos));

		}

		if ("add_new_good_order".equals(action)) {

			GoodsOrderVO goodsOrderVO = gson.fromJson(req.getParameter("goodsOrderVO"), GoodsOrderVO.class);
			// 取出memId 比對餘額是否充足
			String memId = goodsOrderVO.getMemId();
			MemberService memSvc = new MemberService();
			MemberVO memberVO = memSvc.getOneMember(memId);
			if (memberVO.getMemBalance() < goodsOrderVO.getGoodTotalPrice()) {
				// 餘額不足
				out.print("Insufficient_account_balance");
				return;
			}

			// 取出我的車
			Type mapType = new TypeToken<Map<GoodsVO, Integer>>() {
			}.getType();
			Map<GoodsVO, Integer> myCart = gson.fromJson(req.getParameter("myCart"), mapType);

			// 建立訂單VO
			goodsOrderVO.setBuyerName(memberVO.getMemName());
			goodsOrderVO.setBuyerAddress(memberVO.getMemAdd());
			goodsOrderVO.setBuyerPhone(memberVO.getMemPhone());
			goodsOrderVO.setGoodOrdStatus(1);

			// 建立訂單Service
			GoodsOrderService goodsOrderSvc = new GoodsOrderService();

			goodsOrderSvc.insert(goodsOrderVO, myCart);

			// 扣除款項
			memberVO.setMemBalance(memberVO.getMemBalance() - goodsOrderVO.getGoodTotalPrice());
			memSvc.update_balance(memberVO);

			// 新增交易紀錄
			WithdrawalRecordService wrSvc = new WithdrawalRecordService();
			wrSvc.addWithdrawalRecord(memberVO.getMemId(), -goodsOrderVO.getGoodTotalPrice(),
					new Date(new GregorianCalendar().getTimeInMillis()));

			out.print("Success");

		}

	}

}
