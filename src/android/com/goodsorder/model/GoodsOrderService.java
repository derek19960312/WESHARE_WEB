package android.com.goodsorder.model;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import android.com.goods.model.GoodsVO;

public class GoodsOrderService {
	
	private GoodsOrderDAO_interface dao;
	
	public GoodsOrderService() {
		dao = new GoodsOrderJDBCDAO();
	}
	
	public GoodsOrderVO getOneOrder(String memId) {
		return dao.findByPk(memId);
	}
	
	public List<GoodsOrderVO> getAllOrder() {
		return dao.getAll();
	}
	
	public GoodsOrderVO insert(GoodsOrderVO goodsOrderVO,Map<GoodsVO,Integer> myCart) {
		
		
		dao.insert(goodsOrderVO,myCart);
		
		return goodsOrderVO;
	}
	
	public GoodsOrderVO updateBuyerData(String buyerName, String buyerAddress, String buyerPhone) {
		
		GoodsOrderVO goodOrderVO = new GoodsOrderVO();
		goodOrderVO.setBuyerName(buyerName);
		goodOrderVO.setBuyerAddress(buyerAddress);
		goodOrderVO.setBuyerPhone(buyerPhone);
		dao.updateBuyerData(goodOrderVO);
		
		return goodOrderVO;
	}
	
	public GoodsOrderVO updateAll(String goodOrderId,String memId, Integer goodTotalPrice, Timestamp goodDate, 
			String buyerName, String buyerAddress,String buyerPhone,Integer goodOrdStatus) {
			
		GoodsOrderVO goodOrderVO = new GoodsOrderVO();
		goodOrderVO.setGoodOrderId(goodOrderId);
		goodOrderVO.setMemId(memId);
		goodOrderVO.setGoodTotalPrice(goodTotalPrice);
		goodOrderVO.setGoodDate(goodDate);
		goodOrderVO.setBuyerName(buyerName);
		goodOrderVO.setBuyerAddress(buyerAddress);
		goodOrderVO.setBuyerPhone(buyerPhone);
		goodOrderVO.setGoodOrdStatus(goodOrdStatus);
		dao.updateAll(goodOrderVO);
		
		return goodOrderVO;
	}
	
	
	public List<GoodsOrderVO> findGoodByMemId(String memId) {
		return dao.findGoodByMemId(memId);
	}
}
