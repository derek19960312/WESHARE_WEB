package android.com.goodsdetails.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GoodsDetailsJDBCDAO implements GoodsDetailsDAO_interface{
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "weshare";
	private static final String PASSWORD = "123456";

	private static final String INSERT_STMT = "INSERT INTO GoodsDetails VALUES (?,?,?,?,?)";
	private static final String UPDATE_STMT = "UPDATE GoodsDetails SET GOODAMOUNT = ?, goodScore = ? , goodRate = ? WHERE GOODORDERID = ?and GOODID = ?";
	private static final String DELETE_STMT = "DELETE FROM GoodsDetails where GOODORDERID = ? and GOODID = ?";
	private static final String FIND_BY_PK = "SELECT * FROM GoodsDetails WHERE GOODORDERID = ?and GOODID = ?";
	private static final String GET_ALL = "SELECT * FROM GoodsDetails";
	
	
	private static final String FIND_BY_ORDERID = "SELECT * FROM GOODSDETAILS WHERE GOODORDERID=?";
	private static final String INSERT_BY_GOODSORDER = "INSERT INTO GoodsDetails VALUES (?,?,?,null,null)";
	private static final String FIND_BY_GOODID = "SELECT * FROM GOODSDETAILS WHERE GOODID=?";
	
	
	
	@Override
	public void insert_By_GoodsOrder(GoodsDetailsVO goodDetailsVO,Connection con) {
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(INSERT_BY_GOODSORDER);
			
			pstmt.setString(1, goodDetailsVO.getGoodOrderId());
			pstmt.setString(2, goodDetailsVO.getGoodId());
			pstmt.setInt(3, goodDetailsVO.getGoodAmount());
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException se) {
				se.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
	}
	
	@Override
	public void insert(GoodsDetailsVO goodDetailsVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, goodDetailsVO.getGoodOrderId());
			pstmt.setString(2, goodDetailsVO.getGoodId());
			pstmt.setInt(3, goodDetailsVO.getGoodAmount());
			pstmt.setFloat(4, goodDetailsVO.getGoodScore());
			pstmt.setString(5, goodDetailsVO.getGoodRate());
			pstmt.executeUpdate();
			System.out.println("?��增�?筆�?��??");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
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
	public void update(GoodsDetailsVO goodDetailsVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(UPDATE_STMT);
			
			pstmt.setInt(1, goodDetailsVO.getGoodAmount());
			pstmt.setFloat(2, goodDetailsVO.getGoodScore());
			pstmt.setString(3, goodDetailsVO.getGoodRate());
			pstmt.setString(4, goodDetailsVO.getGoodOrderId());
			pstmt.setString(5, goodDetailsVO.getGoodId());
			pstmt.executeUpdate();
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
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
	public void delete(String goodOrderId, String goodId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(DELETE_STMT);
			pstmt.setString(1, goodOrderId);
			pstmt.setString(2, goodId);
			pstmt.executeUpdate();
			System.out.println("已刪?���?筆�?��??");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
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
	public GoodsDetailsVO findByPK(String goodOrderId, String goodId) {
		GoodsDetailsVO details = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_PK);
			pstmt.setString(1, goodOrderId);
			pstmt.setString(2, goodId);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				details = new GoodsDetailsVO();
				details.setGoodOrderId(rs.getString("GoodOrderId"));
				details.setGoodId(rs.getString("GoodId"));
				details.setGoodAmount(rs.getInt("GoodAmount"));
				details.setGoodScore(rs.getFloat("GoodScore"));
				details.setGoodRate(rs.getString("GoodRate"));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
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
		
		return details;
	}
	@Override
	public List<GoodsDetailsVO> getAll() {
		List<GoodsDetailsVO> detailsList = new ArrayList<GoodsDetailsVO>();
		GoodsDetailsVO details = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
			    details = new GoodsDetailsVO();
				details.setGoodOrderId(rs.getString("GOODORDERID"));
				details.setGoodId(rs.getString("GoodId"));
				details.setGoodAmount(rs.getInt("GoodAmount"));
				details.setGoodScore(rs.getFloat("GoodScore"));
				details.setGoodRate(rs.getString("GoodRate"));
				detailsList.add(details);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
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
		return detailsList;
	}
	
	public static void main(String[] args) {
	
		GoodsDetailsJDBCDAO dao = new GoodsDetailsJDBCDAO();
	
	//insert ??��?��?�主?��
	GoodsDetailsVO detail = new GoodsDetailsVO();
	detail.setGoodOrderId("GO00003");
	detail.setGoodId("GD00003");
	detail.setGoodAmount(66);
	detail.setGoodScore(new Float(2.3));
	detail.setGoodRate("?��西�?��??");
	dao.insert(detail);
	
	//update
//	GoodsDetailsVO detalis2 = new GoodsDetailsVO();
//	detalis2.setGoodAmount(66);
//	detalis2.setGoodScore(new Float(3.5));
//	detalis2.setGoodRate("評價");
//	detalis2.setGoodOrderId("GO00003");
//	detalis2.setGoodId("GD00005");
//	dao.update(detalis2);
	
	//delete
//	dao.delete("GO00004", "GD00001");
	
	//findByPK
//	GoodsDetailsVO details = dao.findByPK("GO00004", "GD00001");
//	System.out.print(details.getGoodOrderId()+ ",");
//	System.out.print(details.getGoodId()+ ",");
//	System.out.print(details.getGoodAmount()+ ",");
//	System.out.print(details.getGoodScore()+ ",");
//	System.out.print(details.getGoodRate());
//	System.out.println();
	
	//getAll
//	List<GoodsDetailsVO> list = dao.getAll();
//	for(GoodsDetailsVO details : list) {
//		System.out.print(details.getGoodOrderId()+ ",");
//		System.out.print(details.getGoodId()+ ",");
//		System.out.print(details.getGoodAmount()+",");
//		System.out.print(details.getGoodScore()+",");
//		System.out.print(details.getGoodRate());
//		System.out.println();
//	}
	}
	
	@Override
	public List<GoodsDetailsVO> findByOrderId(String goodOrderId) {
		List<GoodsDetailsVO> detailsList = new ArrayList<GoodsDetailsVO>();
		GoodsDetailsVO details = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_ORDERID);
			pstmt.setString(1, goodOrderId);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
			    details = new GoodsDetailsVO();
				details.setGoodOrderId(rs.getString("GOODORDERID"));
				details.setGoodId(rs.getString("GoodId"));
				details.setGoodAmount(rs.getInt("GoodAmount"));
				details.setGoodScore(rs.getFloat("GoodScore"));
				details.setGoodRate(rs.getString("GoodRate"));
				detailsList.add(details);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
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
		return detailsList;
	}

	@Override
	public List<GoodsDetailsVO> getByGoodId(String goodId) {
		List<GoodsDetailsVO> detailsList = new ArrayList<GoodsDetailsVO>();
		GoodsDetailsVO details = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_GOODID);
			pstmt.setString(1, goodId);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
			    details = new GoodsDetailsVO();
				details.setGoodOrderId(rs.getString("GOODORDERID"));
				details.setGoodId(rs.getString("GoodId"));
				details.setGoodAmount(rs.getInt("GoodAmount"));
				details.setGoodScore(rs.getFloat("GoodScore"));
				details.setGoodRate(rs.getString("GoodRate"));
				detailsList.add(details);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
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
		return detailsList;
	}
	
}
