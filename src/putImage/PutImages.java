package putImage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class PutImages {
	public static void main(String args[]) {
		Context ctx;
		DataSource ds;
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			ctx = new javax.naming.InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
			con = ds.getConnection();
			
			
			
			pstmt = con.prepareStatement("update member set memImage=? where memId=?");
			
			pstmt.executeUpdate();
			
			

		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				pstmt.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
		
		
		}
	}
}
