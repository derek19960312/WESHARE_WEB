package android.com.goodslike.model;

import java.util.List;

public interface GoodsLikeDAO_interface {
	
	public void insert(GoodsLikeVO goodLikeVO);
	public void delete(String goodId,String memId);	
	public List<GoodsLikeVO> getByMemId(String memId);
	List<GoodsLikeVO>getAll();
}
