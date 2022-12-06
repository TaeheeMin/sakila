<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "vo.*" %>
<%@ page import = "dao.*" %>
<%@ page import = "java.util.*" %>
<%
	// 1. Controller(요청처리 + 모델호출(dao))
	// 요청처리(정렬)
	String col = "film_id";
	String sort = "ASC";
	
	if(request.getParameter("col") != null) {
		col = request.getParameter("col");
	}
	if(request.getParameter("sort") != null) {
		sort = request.getParameter("sort");
	}
	
	// 제목 클릭시 넘겨질 sort값(sort의 반대값)
	String paramSort = "ASC";
	if(sort.equals("ASC")) { 
		paramSort = "DESC";
	}
	
	// 요청처리(검색)
	String searchCol = request.getParameter("searchCol"); // title, description, titleAndDescription
	String searchWord = request.getParameter("searchWord"); // 입력한 검색어
	
	// 모델 호풀
	FilmDao filmDao = new FilmDao();
	ArrayList<Film> list = filmDao.selectFilmListBySearch(col, sort, searchCol, searchWord);
	
	// 2. View
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Film List</title>
	</head>
	
	<body>
		<h1>필름 목록(검색 : 동적쿼리)</h1>
		<hr>
		
		<!-- 필름 검색 Start -->
		<h3>검색</h3>
		<!-- 검색어는 보이는게 좋으니까 get방식 사용 -->
		<div>
			<form method="get" action="<%=request.getContextPath()%>/filmListAction.jsp">
				<table border="1">
					<tr>
						<th>검색</th>
						<td>
							<select name="searchCol">
								<option value="title">title</option>
								<option value="description">description</option>
								<option value="titleAndDescription">title + description</option>
							</select>
							<input type="text" name="searchWord" placeholder="검색어">
						</td>
					</tr>
					
					<tr>
						<th>대여료</th>
						<td>
							<input type="radio" name="rentalRate" value="0.99">0.99
							<input type="radio" name="rentalRate" value="2.99">2.99
							<input type="radio" name="rentalRate" value="4.99">4.99
						</td>
					</tr>
					<tr>
						<th>상영시간</th>
						<td>
							<select name="length">
								<option value="">~1시간</option>
								<option value="">1시간~2시간</option>
								<option value="">2시간~</option>
							</select>
						</td>
					</tr>
					<tr>
						<th>등급</th>
						<td>
							<select name="rating">
								<option value="">G</option>
								<option value="">PG</option>
								<option value="">PG-13</option>
								<option value="">R</option>
								<option value="">NC-17</option>
							</select>
						</td>
					</tr>
				</table>
				<button type="submit">검색</button>
			</form>
		</div>
		<!-- 필름 검색 End -->
		
		<!-- 필름 목록 Start -->
		<table border="1">
			<tr>
				<th>
					<a href="<%=request.getContextPath()%>/filmListAction.jsp?col=film_id&sort=<%=paramSort%>">
						Film
					</a>
				</th>
				<th>
					<a href="<%=request.getContextPath()%>/filmListAction.jsp?col=title&sort=<%=paramSort%>">
						Title
					</a>
				</th>
				<th>
					<a href="<%=request.getContextPath()%>/filmListAction.jsp?col=description&sort=<%=paramSort%>">
						Description
					</a>
				</th>
				<th>
					<a href="<%=request.getContextPath()%>/filmListAction.jsp?col=release_year&sort=<%=paramSort%>">
						Release year
					</a>
				</th>
				<th>
					<a href="<%=request.getContextPath()%>/filmListAction.jsp?col=language_id&sort=<%=paramSort%>">
						Language
					</a>
				</th>
				<th>
					<a href="<%=request.getContextPath()%>/filmListAction.jsp?col=original_language_id&sort=<%=paramSort%>">
						Original language
					</a>
				</th>
				<th>
					<a href="<%=request.getContextPath()%>/filmListAction.jsp?col=rental_duration&sort=<%=paramSort%>">
						Rental duration
					</a>
				</th>
				<th>
					<a href="<%=request.getContextPath()%>/filmListAction.jsp?col=rental_rate&sort=<%=paramSort%>">
						Rental rate
					</a>
				</th>
				<th>
					<a href="<%=request.getContextPath()%>/filmListAction.jsp?col=length&sort=<%=paramSort%>">
						Length
					</a>
				</th>
				<th>
					<a href="<%=request.getContextPath()%>/filmListAction.jsp?col=replacement_cost&sort=<%=paramSort%>">
						Replacement cost
					</a>
				</th>
				<th>
					<a href="<%=request.getContextPath()%>/filmListAction.jsp?col=rating&sort=<%=paramSort%>">
						Rating
					</a>
				</th>
				<th>
					<a href="<%=request.getContextPath()%>/filmListAction.jsp?col=special_features&sort=<%=paramSort%>">
						Special features
					</a>
				</th>
				<th>
					<a href="<%=request.getContextPath()%>/filmListAction.jsp?col=last_update&sort=<%=paramSort%>">
						Last update
					</a>
				</th>
			</tr>
			<%
				for(Film f : list) {
			%>
					<tr>
						<td><%=f.getFilmId() %></td>
						<td><%=f.getTitle() %></td>
						<td><%=f.getDescription() %></td>
						<td><%=f.getReleaseYear() %></td>
						<td><%=f.getLanguageId() %></td>
						<td><%=f.getOriginalLanguageId() %></td>
						<td><%=f.getRentalDuration() %></td>
						<td><%=f.getRentalRate() %></td>
						<td><%=f.getLength() %></td>
						<td><%=f.getReplacementCost() %></td>
						<td><%=f.getRating() %></td>
						<td><%=f.getSpecialFeatures() %></td>
						<td><%=f.getLastUpdate() %></td>
					</tr>
			<%
				}
			%>
		</table>
		<!-- 필름 목록 End -->
		
	</body>
</html>