package weshare;

import java.io.*;
import java.sql.*;
import javax.naming.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;

@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();

		String memId = request.getParameter("memId").trim();
		String memPsw = request.getParameter("memPsw").trim();
		
		

		try {
			Context ctx = new javax.naming.InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
			Connection con = ds.getConnection();

			PreparedStatement pstmt = con.prepareStatement("select memPsw from member where memId=?");
			pstmt.setString(1, memId);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				if (rs.getString(1).equals(memPsw)) {
					out.println("成功");
				} else {
					out.print("密碼錯誤");
				}
			} else {
				out.println("查無帳號");
			}
			rs.close();
			pstmt.close();
			con.close();

		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
