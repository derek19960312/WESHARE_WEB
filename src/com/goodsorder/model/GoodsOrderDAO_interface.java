package com.goodsorder.model;

import java.util.List;
import java.util.Map;

import com.goods.model.GoodsVO;

public interface GoodsOrderDAO_interface {
	
	void insert(GoodsOrderVO goodOrderVO,Map<GoodsVO,Integer> myCart);
	void updateBuyerData(GoodsOrderVO goodOrderVO);
	void updateAll(GoodsOrderVO goodOrderVO);
	void delete(String goodOrderId);
	GoodsOrderVO findByPk(String memId);
	List<GoodsOrderVO> getAll();
	public List<GoodsOrderVO> findGoodByMemId(String memId);
}
