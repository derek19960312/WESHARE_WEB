package com.goodsdetails.model;

import java.util.List;

public interface GoodsDetailsDAO_interface {
	void insert (GoodsDetailsVO goodDetailsVO);
	void update(GoodsDetailsVO goodDetailsVO);
	void delete(String goodOrderId, String goodId);
	GoodsDetailsVO findByPK(String goodOrderId, String goodId);
	List<GoodsDetailsVO>getAll();
}
