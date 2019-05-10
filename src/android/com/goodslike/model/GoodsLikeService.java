package android.com.goodslike.model;

import java.util.List;


public class GoodsLikeService {
	private GoodsLikeDAO_interface dao;
	
	public GoodsLikeService() {
		dao = new GoodsLikeDAO();
	}
	
	public GoodsLikeVO addGoodsLike(String memId, String goodId) {

		GoodsLikeVO goodsLikeVO = new GoodsLikeVO();

		goodsLikeVO.setMemId(memId);
		goodsLikeVO.setGoodId(goodId);
		dao.insert(goodsLikeVO);

		return goodsLikeVO;

	}

	public void deleteGoodsLike(String memId, String goodId) {
		dao.delete(goodId, memId);
	}

	public List<GoodsLikeVO> getMemIdGoodsLike(String memId) {
		return dao.getByMemId(memId);
	}

	public List<GoodsLikeVO> getAll() {
		return dao.getAll();
	}
	

}
