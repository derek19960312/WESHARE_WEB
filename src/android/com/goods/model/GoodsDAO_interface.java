package android.com.goods.model;

import java.util.List;

public interface GoodsDAO_interface {
	
	void insert(GoodsVO goodVO);
	void updateGood(GoodsVO goodVO);
	void updateStatus(GoodsVO goodVO);
	void delete(String goodId);
	GoodsVO findByPK(String goodId);
	List<GoodsVO>getAll();
	
}
