package com.goodsdetails.model;

import java.util.List;

import com.goodsorder.model.GoodsOrderVO;



public class GoodsDetailsService {
	private GoodsDetailsDAO_interface dao;

	public GoodsDetailsService() {
		this.dao = new GoodsDetailsJDBCDAO();
	}
	
	public List<GoodsDetailsVO> findByOrderId(String goodOrderId) {
		return dao.findByOrderId(goodOrderId);
	}
	
	
	
}
