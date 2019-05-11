package android.com.goods.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class GoodsDAO implements GoodsDAO_interface {
	private static DataSource ds = null;

	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
		} catch (NamingException e) {

			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO Goods VALUES (('GD'||LPAD(to_char(Goods_seq.NEXTVAL), 5, '0')),?,?,?,?,?,?)";
	private static final String UPDATE_STMT = "UPDATE Goods SET TeacherId=?, goodName=?, goodPrice=?, goodInfo=?, goodImg=?, goodStatus=? WHERE goodId = ?";
	private static final String FINDBYPK_STMT = "SELECT * FROM Goods WHERE GoodId = ?";
	private static final String UpdateStatus_STMT = "UPDATE Goods SET goodStatus = ? WHERE GOODID = ?";
	private static final String GET_ALL = "SELECT GoodId, TeacherId, goodName, goodPrice, goodInfo, goodStatus FROM GOODS";
	private static final String DELETE_STMT = "DELETE FROM GOODS WHERE goodId = ?";
	private static final String FIND_BY_TEACHERID = "SELECT * FROM GOODS WHERE teacherId = ?";
	

	@Override
	public void insert(GoodsVO goodVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, goodVO.getTeacherId());
			pstmt.setString(2, goodVO.getGoodName());
			pstmt.setDouble(3, goodVO.getGoodPrice());
			pstmt.setString(4, goodVO.getGoodInfo());
			pstmt.setBytes(5, goodVO.getGoodImg());
			pstmt.setInt(6, goodVO.getGoodStatus());
			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public void updateGood(GoodsVO goodVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STMT);
			pstmt.setString(1, goodVO.getTeacherId());
			pstmt.setString(2, goodVO.getGoodName());
			pstmt.setDouble(3, goodVO.getGoodPrice());
			pstmt.setString(4, goodVO.getGoodInfo());
			pstmt.setBytes(5, goodVO.getGoodImg());
			pstmt.setInt(6, goodVO.getGoodStatus());
			pstmt.setString(7, goodVO.getGoodId());

			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public void updateStatus(GoodsVO goodVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UpdateStatus_STMT);

			pstmt.setInt(1, goodVO.getGoodStatus());
			pstmt.setString(2, goodVO.getGoodId());
			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public void delete(String goodId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_STMT);

			pstmt.setString(1, goodId);
			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public GoodsVO findByPK(String goodId) {
		GoodsVO goodsVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FINDBYPK_STMT);

			pstmt.setString(1, goodId);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				goodsVO = new GoodsVO();
				goodsVO.setGoodId(rs.getString("goodId"));
				goodsVO.setTeacherId(rs.getString("teacherId"));
				goodsVO.setGoodName(rs.getString("goodName"));
				goodsVO.setGoodPrice(rs.getInt("goodPrice"));
				goodsVO.setGoodInfo(rs.getString("goodInfo"));
				goodsVO.setGoodStatus(rs.getInt("goodStatus"));
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return goodsVO;
	}

	@Override
	public List<GoodsVO> getAll() {
		List<GoodsVO> goodsList = new ArrayList<GoodsVO>();
		GoodsVO goodsVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				goodsVO = new GoodsVO();
				goodsVO.setGoodId(rs.getString("goodId"));
				goodsVO.setTeacherId(rs.getString("teacherId"));
				goodsVO.setGoodName(rs.getString("goodName"));
				goodsVO.setGoodPrice(rs.getInt("goodPrice"));
				goodsVO.setGoodInfo(rs.getString("goodInfo"));
				goodsVO.setGoodStatus(rs.getInt("goodStatus"));
				goodsList.add(goodsVO);
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return goodsList;
	}
	
	@Override
	public List<GoodsVO> findGoodsByTeacherId(String teacherId) {
		List<GoodsVO> goodsList = new ArrayList<GoodsVO>();
		GoodsVO goodsVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_BY_TEACHERID);

			pstmt.setString(1, teacherId);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				goodsVO = new GoodsVO();
				goodsVO.setGoodId(rs.getString("goodId"));
				goodsVO.setTeacherId(rs.getString("teacherId"));
				goodsVO.setGoodName(rs.getString("goodName"));
				goodsVO.setGoodPrice(rs.getInt("goodPrice"));
				goodsVO.setGoodInfo(rs.getString("goodInfo"));
				goodsVO.setGoodStatus(rs.getInt("goodStatus"));
				goodsList.add(goodsVO);
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return goodsList;
	}

}
