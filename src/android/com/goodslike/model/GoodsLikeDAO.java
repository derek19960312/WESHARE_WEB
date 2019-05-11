package android.com.goodslike.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.query.Query;

import android.com.goods.model.GoodsVO;
import android.com.member.model.MemberVO;
import android.com.teacher.model.TeacherVO;
import hibernate.util.HibernateUtil;
import hibernate.util.TransBean2Map;

public class GoodsLikeDAO implements GoodsLikeDAO_interface {


	private static final String GET_ALL = "SELECT * FROM GoodsLike";

	@Override
	public void insert(GoodsLikeVO goodLikeVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(goodLikeVO);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		
	}

	@Override
	public void delete(GoodsLikeVO goodLikeVO) {
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.delete(goodLikeVO);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}

	@Override
	public List<GoodsLikeVO> getAll() {
		List<GoodsLikeVO> list = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query<GoodsLikeVO> query = session.createQuery(GET_ALL, GoodsLikeVO.class);
			list = query.getResultList();
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}

	@Override
	public List<GoodsLikeVO> findByAnyGoodsLikeVO(GoodsLikeVO goodLikeVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List<GoodsLikeVO> list = null;
		try {
			// 【●創建 CriteriaBuilder】
			CriteriaBuilder builder = session.getCriteriaBuilder();
			// 【●創建 CriteriaQuery】
			CriteriaQuery<GoodsLikeVO> criteriaQuery = builder.createQuery(GoodsLikeVO.class);
			// 【●創建 Root】
			Root<GoodsLikeVO> root = criteriaQuery.from(GoodsLikeVO.class);

			List<Predicate> predicateList = new ArrayList<Predicate>();
			
			
			Map<String, Object> map = TransBean2Map.transBean2Map(goodLikeVO);
			
			
			Set<String> keys = map.keySet();
			for (String key : keys) {
				Object value = map.get(key);
				if (value != null) {
					predicateList.add(get_aPredicate_For_AnyDB(builder, root, key, value));
				}
			}
			
			criteriaQuery.where(predicateList.toArray(new Predicate[predicateList.size()]));
			
			Query<GoodsLikeVO> query = session.createQuery(criteriaQuery); 
			list = query.getResultList();

			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return list;

	}

	public static Predicate get_aPredicate_For_AnyDB(CriteriaBuilder builder, Root<GoodsLikeVO> root, String columnName,
			Object value) {

		Predicate predicate = null;

		
		if ("goodsVO".equals(columnName)) 
			predicate = builder.equal(root.get(columnName), ((GoodsVO)value).getGoodId());
		else if ("memberVO".equals(columnName)) 
			predicate = builder.equal(root.get(columnName), ((MemberVO)value).getMemId());

		return predicate;
	}

	
}
