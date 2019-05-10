<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="android.com.inscourse.model.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
    InsCourseService insCourseSvc = new InsCourseService();
    List<InsCourseVO> list = insCourseSvc.getAll();
    pageContext.setAttribute("list",list);
%>

<jsp:useBean id="memSvc" scope="request" class="android.com.member.model.MemberService"/>
<jsp:useBean id="teacherSvc" scope="request" class="android.com.teacher.model.TeacherService"/>
<jsp:useBean id="courseSvc" scope="request" class="android.com.course.model.CourseService"/>
<html>
<head>
<title>所有課程資料 - listAllInsCourse.jsp</title>

<style>
	div{
	margin: 5px auto;
	}
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
<div>

<h4>此頁練習採用 EL 的寫法取值:</h4>
<table id="table-1">
	<tr><td>
		 <h3>所有員工資料 - listAllInsCourse.jsp</h3>
		 <h4><a href="select_page.jsp"><img src="images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
	</td></tr>
</table>

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>



<table>
	<tr>
		<th>課程編號</th>
		<th>老師</th>
		<th>課程種類</th>
		<th>可上課地點</th>
		<th>課程類型</th>
		<th>人數</th>
		<th>上課語言</th>
		<th>價錢</th>
		<th>課綱</th>
		<th>修改</th>
	</tr>
	<%@ include file="page1.file" %> 
	<c:forEach var="InsCourseVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
		<c:if test="${InsCourseVO.inscStatus == 0}">	
			<tr>
				<td>${InsCourseVO.inscId}</td>
					<c:set var="teacherVO" value="${teacherSvc.findOneById(InsCourseVO.teacherId)}"/>
					<c:set var="memberVO" value="${memSvc.getOneMember(teacherVO.memId)}" />
				<td>${memberVO.memName}</td>
					<c:set var="courseVO" value="${courseSvc.findOneById(InsCourseVO.courseId)}" />			
				<td>${courseVO.courseName}</td>
				<td>${InsCourseVO.inscLoc}</td>
					<c:choose>
						<c:when test="${InsCourseVO.inscType == 0}">
							<td>個人課程</td>
						</c:when>
						<c:otherwise>
							<td>團體課程</td>
						</c:otherwise>
					</c:choose>
				<td>${InsCourseVO.inscPeople} 人</td> 
				<td>${InsCourseVO.inscLang}</td>
				<td>NT:${InsCourseVO.inscPrice}</td>
				<td>${InsCourseVO.inscCourser}</td>
				<td>
			  		<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/inscourse/InsCourse.do" style="margin-bottom: 0px;">
			    		<input type="submit" value="修改">
			    		<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>">
			     		<input type="hidden" name="inscId"  value="${InsCourseVO.inscId}">
			    		<input type="hidden" name="action"	value="getOne_For_Update"></FORM>
				</td>
			</tr>
		</c:if>		
	</c:forEach>
</table>
<%@ include file="page2.file" %>
</div>
</body>
</html>