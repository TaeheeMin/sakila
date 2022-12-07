package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import vo.*;

public class FilmDao {
	// 동적 쿼리 연습
	// release_year의 최소값
	public int selectMinReleaseYear() {
		// SELECT IFNULL(MIN(release_year), YEAR(CURDATE())) minYear FROM film;
		String sql = "SELECT MIN(release_year) y FROM film;";
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		int minYear = 0;
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/sakila", "root", "java1234");
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			if(rs.next()) {
				minYear = rs.getInt("y");
				//minYear = rs.getInt(1); 하나만 있으면 간단하게 표현 가능
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return minYear;
	}
	
	// rating : String[] 여러 개의 등급
	public ArrayList<Film> selectFilmList2(String col, String sort, String rentalRate, String releaseYear, String[] rating, int fromMinute, int toMinute, String searchTitle) {
		ArrayList<Film> list = new ArrayList<Film>();
		/*
			검색 : 등급, 제목, 상영시간(between and)
			1) rating == null || rating.length == 5
				SELECT * FROM film;
			2) rating.length == 4
				SELECT * FROM film WHERE rating IN(?, ?, ?, ?)
			3) searchTitle = ?
			4) BETWEEN fromMinute=? AND toMinute=?
			SELECT * FROM film WHERE title LIKE '%A%' AND rating IN('G', 'PG') AND length BETWEEN 40 AND 120;
		 */
		String sql = "SELECT * FROM film WHERE release_year like ? AND title like ? AND rental_rate LIKE ?";
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
	
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/sakila", "root", "java1234");
			if(fromMinute > toMinute) { // 상영시간 BETWEEN AND 필요
				if(rating == null || rating.length == 5) { // 등급 검색X
					sql += "ORDER BY "+ col + " " + sort;
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%" + releaseYear + "%");
					stmt.setString(2, "%" + searchTitle + "%");
					stmt.setString(3, "%" + rentalRate + "%");
					stmt.setInt(4, fromMinute);
					stmt.setInt(5, toMinute);
					
				} else if(rating.length == 4) {
					sql += " AND rating IN(?, ?, ?, ?)";
					sql += "ORDER BY "+ col + " " + sort;
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%" + releaseYear + "%");
					stmt.setString(2, "%" + searchTitle + "%");
					stmt.setString(3, "%" + rentalRate + "%");
					stmt.setInt(4, fromMinute);
					stmt.setInt(5, toMinute);
					stmt.setString(6, rating[0]);
					stmt.setString(7, rating[1]);
					stmt.setString(8, rating[2]);
					stmt.setString(9, rating[3]);
					
				} else if(rating.length == 3) {
					sql += " AND rating IN(?, ?, ?)";
					sql += "ORDER BY "+ col + " " + sort;
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%" + releaseYear + "%");
					stmt.setString(2, "%" + searchTitle + "%");
					stmt.setString(3, "%" + rentalRate + "%");
					stmt.setInt(4, fromMinute);
					stmt.setInt(5, toMinute);
					stmt.setString(6, rating[0]);
					stmt.setString(7, rating[1]);
					stmt.setString(8, rating[2]);
					
				} else if(rating.length == 2) {
					sql += " AND rating IN(?, ?)";
					sql += "ORDER BY "+ col + " " + sort;
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%" + releaseYear + "%");
					stmt.setString(2, "%" + searchTitle + "%");
					stmt.setString(3, "%" + rentalRate + "%");
					stmt.setInt(4, fromMinute);
					stmt.setInt(5, toMinute);
					stmt.setString(6, rating[0]);
					stmt.setString(7, rating[1]);
					
				} else if(rating.length == 1) {
					sql += " AND rating IN(?)";
					sql += "ORDER BY "+ col + " " + sort;
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%" + releaseYear + "%");
					stmt.setString(2, "%" + searchTitle + "%");
					stmt.setString(3, "%" + rentalRate + "%");
					stmt.setInt(4, fromMinute);
					stmt.setInt(5, toMinute);
					stmt.setString(6, rating[0]);
				}
				
			} else { // 상영시간 BETWEEN AND 필요X
				if(rating == null || rating.length == 5) { // 등급 검색X
					sql += "ORDER BY "+ col + " " + sort;
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%" + releaseYear + "%");
					stmt.setString(2, "%" + searchTitle + "%");
					stmt.setString(3, "%" + rentalRate + "%");
					
				} else if(rating.length == 4) {
					sql += " AND rating IN(?, ?, ?, ?)";
					sql += "ORDER BY "+ col + " " + sort;
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%" + releaseYear + "%");
					stmt.setString(2, "%" + searchTitle + "%");
					stmt.setString(3, "%" + rentalRate + "%");
					stmt.setString(4, rating[0]);
					stmt.setString(5, rating[1]);
					stmt.setString(6, rating[2]);
					stmt.setString(7, rating[3]);
					
				} else if(rating.length == 3) {
					sql += " AND rating IN(?, ?, ?)";
					sql += "ORDER BY "+ col + " " + sort;
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%" + releaseYear + "%");
					stmt.setString(2, "%" + searchTitle + "%");
					stmt.setString(3, "%" + rentalRate + "%");
					stmt.setString(4, rating[0]);
					stmt.setString(5, rating[1]);
					stmt.setString(6, rating[2]);
					
				} else if(rating.length == 2) {
					sql += " AND rating IN(?, ?)";
					sql += "ORDER BY "+ col + " " + sort;
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%" + releaseYear + "%");
					stmt.setString(2, "%" + searchTitle + "%");
					stmt.setString(3, "%" + rentalRate + "%");
					stmt.setString(4, rating[0]);
					stmt.setString(5, rating[1]);
					
				} else if(rating.length == 1) {
					sql += " AND rating IN(?)";
					sql += "ORDER BY "+ col + " " + sort;
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%" + releaseYear + "%");
					stmt.setString(2, "%" + searchTitle + "%");
					stmt.setString(3, "%" + rentalRate + "%");
					stmt.setString(4, rating[0]);
				}
			}
			
			rs = stmt.executeQuery();
			while(rs.next()) {
				Film f = new Film();
				f.setFilmId(rs.getInt("film_id"));
				f.setTitle(rs.getString("title"));
				f.setRating(rs.getString("rating"));
				f.setLength(rs.getInt("length"));
				f.setReleaseYear(rs.getString("release_year"));
				f.setRentalRate(rs.getDouble("rental_rate"));
				list.add(f);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}
	
	// sort : asc/desc
	// col : 컬럼명
	public ArrayList<Film> selectFilmListBySearch(String col, String sort, String searchCol, String searchWord) {
		ArrayList<Film> list = new ArrayList<Film>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/sakila", "root", "java1234");
			String sql = "";
			if(searchCol == null || searchWord == null) { // 초기(검색X)
				System.out.println("검색X");
				sql = "SELECT"
						+ " film_id filmId"
						+ ", title"
						+ ", description"
						+ ", release_year releaseYear"
						+ ", language_id languageId"
						+ ", original_language_id originalLanguageId"
						+ ", rental_duration rentalDuration"
						+ ", rental_rate rentalRate"
						+ ", length"
						+ ", replacement_cost replacementCost"
						+ ", rating"
						+ ", special_features specialFeatures"
						+ ", last_update lastUpdate"
						+ " FROM film"
						+ "	ORDER BY "+ col + " " + sort;
				stmt = conn.prepareStatement(sql);
			} else { // 검색O
				String whereCol = "";
				if(searchCol.equals("titleAndDescription")) {
					whereCol = "CONCAT(title, ' ', description)";
				} else {
					whereCol = searchCol;
				}
				System.out.println("검색O : "+searchCol + "/ 검색어 : " + searchWord);
				sql = "SELECT"
						+ " film_id filmId"
						+ ", title"
						+ ", description"
						+ ", release_year releaseYear"
						+ ", language_id languageId"
						+ ", original_language_id originalLanguageId"
						+ ", rental_duration rentalDuration"
						+ ", rental_rate rentalRate"
						+ ", length"
						+ ", replacement_cost replacementCost"
						+ ", rating"
						+ ", special_features specialFeatures"
						+ ", last_update lastUpdate"
						+ " FROM film"
						+ " WHERE "+ whereCol +" LIKE ?"
						+ "	ORDER BY "+ col + " " + sort;
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, "%" + searchWord + "%");
			}
			
			
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				Film f = new Film();
				f.setFilmId(rs.getInt("filmId"));
				f.setTitle(rs.getString("title"));
				f.setDescription(rs.getString("description"));
				f.setReleaseYear(rs.getString("releaseYear"));
				f.setLanguageId(rs.getInt("languageId"));
				f.setOriginalLanguageId(rs.getInt("originalLanguageId"));
				f.setRentalDuration(rs.getInt("rentalDuration"));
				f.setRentalRate(rs.getDouble("rentalRate"));
				f.setLength(rs.getInt("length"));
				f.setReplacementCost(rs.getDouble("replacementCost"));
				f.setRating(rs.getString("rating"));
				f.setSpecialFeatures(rs.getString("specialFeatures"));
				f.setLastUpdate(rs.getString("lastUpdate"));
				list.add(f);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}
}
