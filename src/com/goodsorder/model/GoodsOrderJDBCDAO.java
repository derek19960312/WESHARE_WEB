package com.goodsorder.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.spi.DirStateFactory.Result;

import com.goods.model.GoodsVO;
import com.goodsdetails.model.GoodsDetailsJDBCDAO;
import com.goodsdetails.model.GoodsDetailsVO;

public class GoodsOrderJDBCDAO implements GoodsOrderDAO_interface {

	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "WESHARE";
	private static final String PASSWORD = "123456";

	private static final String INSERT_STMT =
//"INSERT INTO GOODSORDER(('GO'||LPAD(to_char(GoodsOrder_seq.NEXTVAL), 5, '0')),MEMID, GOODTOTALPRICE, GOODDATE, BUYERNAME, BUYERADDRESS, BUYERPHONE,GOODORDSTATUS) VALUES(?,?,?,?,?,?,?)";
			"INSERT INTO GoodsOrder VALUES (('GO'||LPAD(to_char(GoodsOrder_seq.NEXTVAL), 5, '0')),?,?,?,?,?,?,?)";

	private static final String UPDATE = 
			"UPDATE GOODSORDER SET memId=?, goodTotalPrice=?, goodDate=?, BUYERNAME=?, BUYERADDRESS=?, BUYERPHONE=?, goodOrdStatus=?  WHERE GOODORDERID = ?";
	private static final String UPDATE_BUYERDATA = "UPDATE GOODSORDER SET BUYERNAME=?, BUYERADDRESS=?, BUYERPHONE=? WHERE GOODORDERID = ?";
	private static final String DELETE_STMT = "DELETE FROM GOODSORDER WHERE GOODORDERID = ?";
	private static final String FIND_BY_MEMID = "SELECT * FROM GOODSORDER WHERE MEMID = ?";
	private static final String GET_ALL = "SELECT * FROM GOODSORDER";
	private static final String GET_GOOD_BY_MEMID = "SELECT * FROM GOODSORDER WHERE MEMID = ?";
	
	@Override
	public void insert(GoodsOrderVO goodOrderVO,Map<GoodsVO,Integer> myCart) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			con.setAutoCommit(false);
			
			int[] cols = {1};
			pstmt = con.prepareStatement(INSERT_STMT,cols);
			
			pstmt.setString(1, goodOrderVO.getMemId());
			pstmt.setInt(2, goodOrderVO.getGoodTotalPrice());
			pstmt.setTimestamp(3, goodOrderVO.getGoodDate());
			pstmt.setString(4, goodOrderVO.getBuyerName());
			pstmt.setString(5, goodOrderVO.getBuyerAddress());
			pstmt.setString(6, goodOrderVO.getBuyerPhone());
			pstmt.setInt(7, goodOrderVO.getGoodOrdStatus());
			pstmt.executeUpdate();
			
			
			rs = pstmt.getGeneratedKeys();
			rs.next();
			String goodOrderId = rs.getString(1);
			
			
			for(GoodsVO goodsVO : myCart.keySet()) {
				GoodsDetailsVO goodsDetailsVO = new GoodsDetailsVO();
				goodsDetailsVO.setGoodOrderId(goodOrderId);
				goodsDetailsVO.setGoodId(goodsVO.getGoodId());
				goodsDetailsVO.setGoodAmount(myCart.get(goodsVO));
				new GoodsDetailsJDBCDAO().insert_By_GoodsOrder(goodsDetailsVO, con);
			}
			
			con.commit();
			System.out.println("已新增");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
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
					se.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	public void updateAll(GoodsOrderVO goodOrderVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, goodOrderVO.getMemId());
			pstmt.setInt(2, goodOrderVO.getGoodTotalPrice());
			pstmt.setTimestamp(3, goodOrderVO.getGoodDate());
			pstmt.setString(4, goodOrderVO.getBuyerName());
			pstmt.setString(5, goodOrderVO.getBuyerAddress());
			pstmt.setString(6, goodOrderVO.getBuyerPhone());
			pstmt.setInt(7, goodOrderVO.getGoodOrdStatus());
			pstmt.setString(8, goodOrderVO.getGoodOrderId());
			pstmt.executeUpdate();
			System.out.println("已修改All");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}
	}

	@Override
	public void updateBuyerData(GoodsOrderVO goodOrderVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(UPDATE_BUYERDATA);

			pstmt.setString(1, goodOrderVO.getBuyerName());
			pstmt.setString(2, goodOrderVO.getBuyerAddress());
			pstmt.setString(3, goodOrderVO.getBuyerPhone());
			pstmt.setString(4, goodOrderVO.getGoodOrderId());
			pstmt.executeUpdate();
			System.out.println("已修改");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void delete(String goodOrderId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(DELETE_STMT);
			pstmt.setString(1, goodOrderId);
			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public GoodsOrderVO findByPk(String memId) {
		GoodsOrderVO order = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_MEMID);
			pstmt.setString(1, memId);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				order = new GoodsOrderVO();
				order.setGoodOrderId(rs.getString("GOODORDERID"));
				order.setMemId(rs.getString("MEMID"));
				order.setGoodTotalPrice(rs.getInt("GOODTOTALPRICE"));
				order.setGoodDate(rs.getTimestamp("GOODDATE"));
				order.setBuyerName(rs.getString("BUYERNAME"));
				order.setBuyerAddress(rs.getString("BUYERADDRESS"));
				order.setBuyerPhone(rs.getString("BUYERPHONE"));
				order.setGoodOrdStatus(rs.getInt("GOODORDSTATUS"));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
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
					se.printStackTrace();
				}
			}
			if (con == null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return order;
	}

	@Override
	public List<GoodsOrderVO> getAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		GoodsOrderVO order = null;
		List<GoodsOrderVO> orderList = new ArrayList<GoodsOrderVO>();

		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_GOOD_BY_MEMID);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				order = new GoodsOrderVO();
				order.setGoodOrderId(rs.getString("GoodOrderId"));
				order.setMemId(rs.getString("MemId"));
				order.setGoodTotalPrice(rs.getInt("GoodTotalPrice"));
				order.setGoodDate(rs.getTimestamp("GoodDate"));
				order.setBuyerName(rs.getString("buyerName"));
				order.setBuyerAddress(rs.getString("buyerAddress"));
				order.setBuyerPhone(rs.getString("buyerPhone"));
				order.setGoodOrdStatus(rs.getInt("goodOrdStatus"));
				orderList.add(order);
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
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
					se.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return orderList;

	}
	
	
	@Override
	public List<GoodsOrderVO> findGoodByMemId(String memId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		GoodsOrderVO order = null;
		List<GoodsOrderVO> orderList = new ArrayList<GoodsOrderVO>();

		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_GOOD_BY_MEMID);
			pstmt.setString(1, memId);
			rs = pstmt.executeQuery();


			while (rs.next()) {
				order = new GoodsOrderVO();
				order.setGoodOrderId(rs.getString("GoodOrderId"));
				order.setMemId(rs.getString("MemId"));
				order.setGoodTotalPrice(rs.getInt("GoodTotalPrice"));
				order.setGoodDate(rs.getTimestamp("GoodDate"));
				order.setBuyerName(rs.getString("buyerName"));
				order.setBuyerAddress(rs.getString("buyerAddress"));
				order.setBuyerPhone(rs.getString("buyerPhone"));
				order.setGoodOrdStatus(rs.getInt("goodOrdStatus"));
				orderList.add(order);
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
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
					se.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return orderList;
	}

	public static void main(String[] args) {

		GoodsOrderJDBCDAO dao = new GoodsOrderJDBCDAO();
		// insert
//		GoodsOrderVO order = new GoodsOrderVO();
//		order.setMemId("weshare04");
//		order.setGoodTotalPrice(9898);
//		order.setGoodDate(new Timestamp(new java.util.Date().getTime()));
//		order.setBuyerName("小叮噹");
//		order.setBuyerAddress("全家是你家");
//		order.setBuyerPhone("0982568789");
//		order.setGoodOrdStatus(1);
//		dao.insert(order);

//		 updateBuyerData
//		GoodsOrderVO order1	= new GoodsOrderVO();
//		order1.setBuyerName("ED");
//		order1.setBuyerAddress("九份999");
//		order1.setBuyerPhone("0910862121");
//		order1.setGoodOrderId("GO00004");
//		dao.updateBuyerData(order1);
		
//		 updateAll
//			GoodsOrderVO order3	= new GoodsOrderVO();
//			order3.setMemId("weshare02");
//			order3.setGoodTotalPrice(5858);
//			order3.setGoodDate(new Timestamp(new java.util.Date().getTime()));
//			order3.setBuyerName("ED");
//			order3.setBuyerAddress("九份999");
//			order3.setBuyerPhone("0910862121");
//			order3.setGoodOrderId("GO00004");
//			order3.setGoodOrdStatus(0);
//			dao.updateAll(order3);

		// delete
//		dao.delete("GO00005");

		// findByPk
//		GoodsOrderVO order2 = dao.findByPk("weshare02");
//		System.out.print(order2.getGoodOrderId()+ ",");
//		System.out.print(order2.getMemId()+ ",");
//		System.out.print(order2.getGoodTotalPrice()+ ",");
//		System.out.print(order2.getGoodDate()+ ",");
//		System.out.print(order2.getBuyerName()+ ",");
//		System.out.print(order2.getBuyerAddress()+ ",");
//		System.out.print(order2.getBuyerPhone()+ ",");
//		System.out.print(order2.getGoodOrdStatus());

		// getAll
		List<GoodsOrderVO> list = dao.getAll();
		for (GoodsOrderVO orders : list) {
			System.out.print(orders.getGoodOrderId() + ",");
			System.out.print(orders.getMemId() + ",");
			System.out.print(orders.getGoodTotalPrice() + ",");
			System.out.print(orders.getGoodDate() + ",");
			System.out.print(orders.getBuyerName() + ",");
			System.out.print(orders.getBuyerAddress() + ",");
			System.out.print(orders.getBuyerPhone() + ",");
			System.out.print(orders.getGoodOrdStatus());
			System.out.println();
		}

	}

	
}
