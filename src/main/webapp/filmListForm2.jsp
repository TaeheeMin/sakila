<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="dao.*" %>
<%@ page import="java.util.*" %>
<%
	FilmDao filmDao = new FilmDao();
	int minYear = filmDao.selectMinReleaseYear();
	
	// 오늘날짜
	Calendar today = Calendar.getInstance();
	// 추상클래스라 생성자(new Calendar)로 호출 못함
	int todayYear = today.get(Calendar.YEAR); // 오늘날짜의 년도만 구하기
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Film List Form</title>
	</head>
	
	<body>
	<h1>Film List Form</h1>
		<form action="<%=request.getContextPath()%>/filmListAction2.jsp" method="get">
			<table border="1">
				<tr>
					<th>등급</th>
					<td>
						<input type="checkbox" name="rating" value="G">G
						<input type="checkbox" name="rating" value="PG">PG
						<input type="checkbox" name="rating" value="PG-13">PG-13
						<input type="checkbox" name="rating" value="R">R
						<input type="checkbox" name="rating" value="NC-17">NC-17
					</td>
				</tr>
				
				<tr>
					<th>상영시간</th>
					<td>
						<input type="number" name="fromMinute">
						~
						<input type="number" name="toMinute">
					</td>
				</tr>
				
				<tr>
					<th>제목</th>
					<td>
						<input type="text" name="searchTitle">
					</td>
				</tr>
				
				<tr>
					<th>출시년도</th>
					<td>
						<select name="releaseYear">
							<option value="">선택하세요</option>
							<%
								for(int i=minYear; i<=todayYear; i++) {
									%>
										<option value="<%=i%>"><%=i%>년</option>
									<%
								}
							%>
						</select>
					</td>
				</tr>
				
				<tr>
					<th>대여료</th>
					<td>
						<input type="radio" name="rentalRate" value="" checked="checked">모두
						<input type="radio" name="rentalRate" value="0.99">0.99
						<input type="radio" name="rentalRate" value="2.99">2.99
						<input type="radio" name="rentalRate" value="4.99">4.99
					</td>
				</tr>
				
			</table>
			<button type="submit">검색</button>
		</form>
	</body>
</html>