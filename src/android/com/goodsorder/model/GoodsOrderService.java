package android.com.goodsorder.model;

import java.util.List;

public class GoodsOrderService {
	
	private GoodsOrderDAO_interface dao;
	
	public GoodsOrderService() {
		dao = new GoodsOrderDAO();
	}
	
	public void add(GoodsOrderVO goodsOrderVO) {
		dao.insert(goodsOrderVO);
	}
	
	public void update(GoodsOrderVO goodsOrderVO) {
		dao.update(goodsOrderVO);
	}
	
	public List<GoodsOrderVO> getAllOrder() {
		return dao.getAll();
	}
	
	public List<GoodsOrderVO> findByAnyGoodsOrderVO(GoodsOrderVO goodsOrderVO) {
		return dao.findByAnyGoodsOrderVO(goodsOrderVO);
	}
}
