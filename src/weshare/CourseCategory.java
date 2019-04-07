package weshare;

import java.io.*;
import java.sql.*;
import java.util.*;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.coursetype.model.CourseTypeVO;

@WebServlet("/CourseCategory")
public class CourseCategory extends HttpServlet {
	private static final long serialVersionUID = 1L;
    final String GET_COURSETYPE = "SELECT * FROM COURSETYPE";
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		Context ctx;
		DataSource ds;
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		List<CourseTypeVO> courseTypes = new ArrayList<>();

		
		try {
			ctx = new javax.naming.InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
			con = ds.getConnection();
			
			pstmt = con.prepareStatement(GET_COURSETYPE);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				CourseTypeVO courseType = new CourseTypeVO();
				courseType.setCourseTypeName(rs.getString("coursetypename"));
				courseTypes.add(courseType);
//				JSONObject jsonObj = new JSONObject(courseType);
//				jsonArr.put(courseType);
			}
			
			JSONArray jsonArr = new JSONArray(courseTypes);
	
			out.println(jsonArr);
			
			try {
				out.println(jsonArr.get(0));
			} catch (JSONException e) {
				e.printStackTrace();
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
