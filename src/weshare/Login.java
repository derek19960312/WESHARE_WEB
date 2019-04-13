package weshare;

import java.io.*;
import java.sql.*;
import javax.naming.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;

import com.google.gson.Gson;

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
		Context ctx;
		DataSource ds;
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Gson gson = new Gson();
		try {
			ctx = new javax.naming.InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
			con = ds.getConnection();

			pstmt = con.prepareStatement("select memPsw from member where memId=?");
			pstmt.setString(1, memId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				if (rs.getString(1).equals(memPsw)) {
					out.println(gson.toJson("0"));
				} else {
					out.print("密碼錯誤");
				}
			} else {
				out.println("查無帳號");
			}
			

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
