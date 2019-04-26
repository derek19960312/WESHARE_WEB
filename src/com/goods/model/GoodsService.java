package com.goods.model;

import java.util.List;
			
public class GoodsService {  
	
	private GoodsDAO_interface dao;

	public GoodsService() {
		dao = new GoodsDAO();
		
	}
	
	public GoodsVO insertGood(String teacherId, String goodName, Integer goodPrice,String goodInfo,byte[] goodImg,Integer goodStatus) {
		GoodsVO goodsVO = new GoodsVO();
		goodsVO.setTeacherId(teacherId);
		goodsVO.setGoodName(goodName);
		goodsVO.setGoodPrice(goodPrice);
		goodsVO.setGoodInfo(goodInfo);
		goodsVO.setGoodImg(goodImg);
		goodsVO.setGoodStatus(goodStatus);
		dao.insert(goodsVO);
		
		return goodsVO;
	}
	
	public GoodsVO updateGood(String goodId, String teacherId, String goodName, Integer goodPrice, String goodInfo, byte[] goodImg, Integer goodStatus) {
		
		GoodsVO goodsVO = new GoodsVO();
		goodsVO.setGoodId(goodId);
		goodsVO.setTeacherId(teacherId);
		goodsVO.setGoodName(goodName);
		goodsVO.setGoodPrice(goodPrice);
		goodsVO.setGoodInfo(goodInfo);
		goodsVO.setGoodImg(goodImg);
		goodsVO.setGoodStatus(goodStatus);
		dao.updateGood(goodsVO);
		
		return goodsVO;
	}
	
	public void delete(String goodId) {
		dao.delete(goodId);
	}
	
	public void updateStatus(GoodsVO goodVO) {
		dao.updateStatus(goodVO);
	}
	
	public GoodsVO getOneGood(String goodId) {		
//		GoodsVO goodsVO = dao.findByPK(goodId);		
		return dao.findByPK(goodId);
	}
	
	public List<GoodsVO> getAll(){
		return dao.getAll();
	}
}

