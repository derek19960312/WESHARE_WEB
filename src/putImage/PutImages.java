package putImage;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
		File file;
		
		try {
			ctx = new javax.naming.InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
			con = ds.getConnection();
			
			rs = con.createStatement().executeQuery("select memId from Member");
			
			for(int i=0; i<10; i++) {
				file = new File("images/teacher"+i+".jpg");
				BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
				pstmt = con.prepareStatement("update member set memImage=? where memId=?");
				rs.next();
				pstmt.setString(1,rs.getString("memId"));
				pstmt.executeUpdate();
			}
			
			
			

		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e){
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
