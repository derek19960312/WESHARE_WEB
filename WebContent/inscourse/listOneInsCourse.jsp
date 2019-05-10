<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="android.com.inscourse.model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 此頁暫練習採用 Script 的寫法取值 --%>

<%
  InsCourseVO insCourseVO = (InsCourseVO) request.getAttribute("insCourseVO"); //EmpServlet.java(Concroller), 存入req的empVO物件
%>
<jsp:useBean id="memSvc" scope="request" class="android.com.member.model.MemberService"/>
<jsp:useBean id="teacherSvc" scope="request" class="android.com.teacher.model.TeacherService"/>
<jsp:useBean id="courseSvc" scope="request" class="android.com.course.model.CourseService"/>
<html>
<head>
<title>員工資料 - listOneInsCourse.jsp</title>

<style>
  table#table-1 {
	background-color: #CCCCFF;
    border: 2px solid black;
    text-align: center;
  }
  table#table-1 h4 {
    color: red;
    display: block;
    margin-bottom: 1px;
  }
  h4 {
    color: blue;
    display: inline;
  }
</style>

<style>
  table {
	width: 100%;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 5px;
  }
  table, th, td {
    border: 1px solid #CCCCFF;
  }
  th, td {
    padding: 5px;
    text-align: center;
  }
</style>

</head>
<body bgcolor='white'>

<h4>此頁暫練習採用 Script 的寫法取值:</h4>
<table id="table-1">
	<tr><td>
		 <h3>員工資料 - ListOneInsCourse.jsp</h3>
		 <h4><a href="select_page.jsp"><img src="images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
	</td></tr>
</table>

<table>
	<tr>
		<th>課程編號</th>
		<th>老師</th>
		<th>課程種類</th>
		<th>可上課地點</th>
		<th>課程類型</th>
		<th>人數</th>
		<th>語言</th>
		<th>價錢</th>
		<th>課綱</th>
		<th>狀態</th>
	</tr>
	<tr>
		<c:if test="${insCourseVO.inscStatus == 0}">	
			<tr>
				<td>${insCourseVO.inscId}</td>
					<c:set var="teacherVO" value="${teacherSvc.findOneById(insCourseVO.teacherId)}"/>
					<c:set var="memberVO" value="${memSvc.getOneMember(teacherVO.memId)}" />
				<td>${memberVO.memName}</td>
					<c:set var="courseVO" value="${courseSvc.findOneById(insCourseVO.courseId)}" />			
				<td>${courseVO.courseName}</td>
				<td>${insCourseVO.inscLoc}</td>
					<c:choose>
						<c:when test="${insCourseVO.inscType == 0}">
							<td>個人課程</td>
						</c:when>
						<c:otherwise>
							<td>團體課程</td>
						</c:otherwise>
					</c:choose>
				<td>${insCourseVO.inscPeople} 人</td> 
				<td>${insCourseVO.inscLang}</td>
				<td>NT:${insCourseVO.inscPrice}</td>
				<td>${insCourseVO.inscCourser}</td>
				<td>
			  		<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/inscourse/InsCourse.do" style="margin-bottom: 0px;">
			    		<input type="submit" value="修改">
			    		<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>">
			     		<input type="hidden" name="inscId"  value="${insCourseVO.inscId}">
			    		<input type="hidden" name="action"	value="getOne_For_Update"></FORM>
				</td>
			</tr>
		</c:if>		
	</tr>
</table>

</body>
</html>