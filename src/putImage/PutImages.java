package putImage;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PutImages {
	
	static String driver = "oracle.jdbc.driver.OracleDriver";
	static String url = "jdbc:oracle:thin:@localhost:1521:XE";
	static String userid = "WESHARE";
	static String passwd = "123456";
	
	static String formName;
	public static String UPDATE;
	

	
	private static final String UPDATE_MEMPIC = 
			"UPDATE Member set memImage=? where  memId =? ";
	private static final String UPDATE_GOODSPIC = 
			"UPDATE GOODS set goodImg=? where  goodId =? ";
	
	private static String GET_ALL ;
			
	
	
	public static void main(String args[]) {


		File file;
		BufferedInputStream bis = null;
		try {
			
			UPDATE = UPDATE_MEMPIC; 
			GET_ALL = 
					"SELECT * FROM MEMBER";
			List<String> memList = getAll();
			for (int i = 1; i <= memList.size(); i++) {
				file = new File("WebContent/images/Teacher/Teacher" + i + ".jpg");
				bis = new BufferedInputStream(new FileInputStream(file));
				byte[] b = new byte[(int)bis.available()];
				bis.read(b);
				String memId = memList.get(i - 1);
				update(memId,b);
			}
			
			
			UPDATE = UPDATE_GOODSPIC;
			GET_ALL = 
					"SELECT * FROM GOODS";
			List<String> goodsList = getAll();
			for (int i = 1; i <= goodsList.size(); i++) {
				file = new File("WebContent/images/goods/mtri" + i + ".jpg");
				bis = new BufferedInputStream(new FileInputStream(file));
				byte[] b = new byte[(int)bis.available()];
				bis.read(b);
				String goodId = goodsList.get(i - 1);
				update(goodId,b);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(bis != null) {
					bis.close();
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	
	
	public static void update(String xxxId, byte[] images) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setBytes(1,images);
			pstmt.setString(2, xxxId);
			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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
	
	
	
	
	
	public static List<String> getAll() {
		List<String> list = new ArrayList<>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				list.add(rs.getString(1)); // Store the row in the list
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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
		return list;
	}
	
	
	
	
	
	
}
