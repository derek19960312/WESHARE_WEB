<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="android.com.inscourse.model.*"%>

<%
InsCourseVO insCourseVO = (InsCourseVO) request.getAttribute("insCourseVO"); 
%>
<jsp:useBean id="memSvc" scope="page" class="android.com.member.model.MemberService"/>
<jsp:useBean id="insCourseSvc" scope="page" class="android.com.inscourse.model.InsCourseService"/>
<jsp:useBean id="teacherSvc" scope="page" class="android.com.teacher.model.TeacherService"/>
<jsp:useBean id="courseSvc" scope="page" class="android.com.course.model.CourseService"/>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>課程資料新增 - addEmp.jsp</title>

<style>
  table#table-1 {
	background-color: #CCCCFF;
    border: 2px solid black;
    text-align: center;
    width: 100%;
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
	form{
	margin: 1px auto;
	}
  table {
	width: 450px;
	background-color: white;
	margin: 1px auto;
  }
  table, th, td {
    border: 0px solid #CCCCFF;
  }
  th, td {
    padding: 1px;
  }
</style>

</head>
<body bgcolor='white'>

<table id="table-1">
	<tr><td>
		 <h3>課程資料新增 - addEmp.jsp</h3></td><td>
		 <h4><a href="select_page.jsp"><img src="images/tomcat.png" width="100" height="100" border="0">回首頁</a></h4>
	</td></tr>
</table>

<h3 align="center">資料新增:</h3>

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<FORM METHOD="post" ACTION="InsCourse.do" name="form1">
<table>
	<tr>
		<td>老師:</td>
		<c:set var="teacherVO" value="${teacherSvc.findOneById(insCourseVO.teacherId)}"/>
		<c:set var="memberVO" value="${memSvc.getOneMember(teacherVO.memId)}" />
		<td><input type="TEXT" name="nameforread" size="10" value="黃鼎鈞"/>
			<input type="hidden" name="teacherId" size="1" value="TC00001"/></td>
		
	</tr>
	<tr>
		<td>課程種類:</td>	
		<td>
			<select size="1" name="courseId">
		         <c:forEach var="courseVO" items="${courseSvc.all}" >
		         	  <c:choose>
		         		<c:when test="${courseVO.courseId == insCourseVO.courseId}">
		         			<option value="${courseVO.courseId}" selected>${courseVO.courseName}</option>
		         		</c:when>
		         		<c:otherwise>
							<option value="${courseVO.courseId}">${courseVO.courseName}</option>
						</c:otherwise>
			          </c:choose>
		         </c:forEach>   
	       </select>
		</td>
	</tr>
	<tr>
		<td>可上課地點:</td>
		<td><input type="TEXT" name="inscLoc" size="45"	value="桃園市桃園區" /></td>
	</tr>
	<tr>
		<td>課程類型:</td>
		<td>
			<select size="1" name="inscType">
		         <c:choose>
		         	<c:when test="${(insCourseVO.inscType == 0)||(insCourseVO.inscType == null)}">
		         		<option value="${insCourseVO.inscType}" selected>個人</option>
		         		<option value="1" >團體</option>
		         	</c:when>
		         	<c:otherwise>
						<option value="${insCourseVO.inscType}" selected>團體</option>
						<option value="0" >個人</option>
					</c:otherwise>
			        </c:choose>
	       </select>	
		</td>
	</tr>
	<tr>
		<td>人數:</td>
		<td><input type="TEXT" name="inscPeople" size="1" style="text-align: right" value="10" />人</td>
	</tr>
	<tr>
		<td>語言:</td>
		<td><input type="TEXT" name="inscLang" size="5" value="越南文" /></td>
	</tr>
	<tr>
		<td>價錢:</td>
		<td>NT:<input type="TEXT" name="inscPrice" size="3" value="10000" /></td>
	</tr>
	<tr>
		<td>課綱:</td>
		<td><textarea name="inscCourser" rows="5" cols="30">JAVA變數三部曲，宣告取值拿來用</textarea></td>
	</tr>
	<tr>
		<td>狀態:</td>
		<td>
			<select size="1" name="inscStatus">
		         <c:choose>
		         	<c:when test="${(insCourseVO.inscStatus == 0)||(insCourseVO.inscStatus == null)}">
		         		<option value="${insCourseVO.inscStatus}" selected>上架</option>
		         		<option value="1" >下架</option>
		         	</c:when>
		         	<c:otherwise>
						<option value="${insCourseVO.inscStatus}" selected>下架</option>
						<option value="0" >上架</option>
					</c:otherwise>
			        </c:choose>
	       </select>
		</td>


</table>
	
<br>
<input type="hidden" name="action" value="insert">
<input type="submit" value="送出新增" ></FORM>
</body>
</html>