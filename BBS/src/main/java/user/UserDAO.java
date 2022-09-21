package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bbs.Bbs;

public class UserDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public UserDAO() {
		try {
			String dbURL = "jdbc:mariadb://localhost:3306/BBS";
			String dbID = "root";
			String dbPassword = "root";
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public int login(String userID, String userPassword) {
		String SQL = "SELECT userPassword FROM user WHERE userID = ?";
		try {
			pstmt = conn.prepareStatement(SQL); // 보안을 위해 pstmt에 sql문을 옮겨놓음
			pstmt.setString(1, userID); // ?에 userID를 치환
			rs = pstmt.executeQuery();
			if(rs.next()) {
				if(rs.getString(1).equals(userPassword)) { // 실행된 쿼리문의 결과값을 getString이 문자열로 받아옴 
					return 1; // 로그인 성공
				}
				else
				{
					return 0;// 비밀번호 불일치
				}
			}
			return -1;// 아이디 없음
		}catch (Exception e) {
			e.printStackTrace();
		}
		return -2; // 데이터 베이스 오류
	}
	
	public int join(User user) {
		String SQL = "INSERT INTO USER VALUES (?, ?, ?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, user.getUserID());
			pstmt.setString(2, user.getUserPassword());
			pstmt.setString(3, user.getUserName());
			pstmt.setString(4, user.getUserGender());
			pstmt.setString(5, user.getUserEmail());
			return pstmt.executeUpdate(); // SELECT문을 제외한 쿼리문 수행시 executeUpdate 사용
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
}
