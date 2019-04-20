package com.goodsorder.model;

import java.util.List;

public interface GoodsOrderDAO_interface {
	
	void insert(GoodsOrderVO goodOrderVO);
	void updateBuyerData(GoodsOrderVO goodOrderVO);
	void updateAll(GoodsOrderVO goodOrderVO);
	void delete(String goodOrderId);
	GoodsOrderVO findByPk(String memId);
	List<GoodsOrderVO> getAll();
}
