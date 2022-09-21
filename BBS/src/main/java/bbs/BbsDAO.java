package bbs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class BbsDAO {
	private Connection conn;
//	private PreparedStatement pstmt;  -->> bbsDAO�� ���� �Լ��� ����ϱ� ������ �����Լ����� �����ͺ��̽� ���ٿ� �־ �浹�� �����ϱ����� ���������� ���������ʴ´�.
	private ResultSet rs;
	
	public BbsDAO() {
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
	
	// ���� ��¥ ���� �������� �Լ�
	public String getDate() {
		String SQL = "SELECT NOW()";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getString(1);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return ""; // �����ͺ��̽� ����
	}
	
	// �Խñ� ��ȣ�� 1������ �ϳ��� �þ�� �Ǳ⶧���� �������� ���α��� �������� 1�� ���Ѱ��� �������� ��ȣ�� �ǰ��ϱ�����
	public int getNext() {
		String SQL = "SELECT bbsID FROM BBS ORDER BY bbsID DESC";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getInt(1) + 1;
			}
			return 1; // ù �Խù��� ���
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1; // �����ͺ��̽� ����
	}
	
	// �� �ۼ� �Լ�
	public int write(String bbsTitle, String userID, String bbsContent) {
		String SQL = "INSERT INTO BBS VALUES (?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext()); // ���� �Խñ� ��ȣ
			pstmt.setString(2, bbsTitle); // ���� �Խñ� ����
			pstmt.setString(3, userID); // ���� �Խñ� ���� id
			pstmt.setString(4, getDate()); // ���� �Խñ� �ð�
			pstmt.setString(5, bbsContent); // ���� �Խñ� 
			pstmt.setInt(6, 1); // ó�� �� �ۼ��� ������.Available = �� ���� ����  (1 = ����x)
			return pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1; // �����ͺ��̽� ����
	}
	
	// �� ��������
	public ArrayList<Bbs> getList(int pageNumber) { // pageNumber => ���� ������
		String SQL = "SELECT * FROM BBS WHERE bbsID < ? AND bbsAvailable = 1 ORDER BY bbsID DESC LIMIT 10";
		ArrayList<Bbs> list = new ArrayList<Bbs>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext() - (pageNumber - 1) * 10); // �������� ��ȣ - (���������� - 1) * 10 => limit�� 10�̹Ƿ� 10�������� �۵��� ��� 1�������� ǥ��.
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Bbs bbs = new Bbs();
				bbs.setBbsID(rs.getInt(1));
				bbs.setBbsTitle(rs.getString(2));
				bbs.setUserID(rs.getString(3));
				bbs.setBbsDate(rs.getString(4));
				bbs.setBbsContent(rs.getString(5));
				bbs.setBbsAvailable(rs.getInt(6));
				list.add(bbs); // �޾ƿ� ��������� bbs�� ����
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	// ����¡ ó��
	public boolean nextPage(int pageNumber) {
		String SQL = "SELECT * FROM BBS WHERE bbsID < ? AND bbsAvailable = 1 ORDER BY bbsID DESC LIMIT 10";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext() - (pageNumber - 1) * 10); // �������� ��ȣ - (���������� - 1) * 10 => limit�� 10�̹Ƿ� 10�������� �۵��� ��� 1�������� ǥ��.
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	// �Խñ� ��������
	public Bbs getBbs(int bbsID) {
		String SQL = "SELECT * FROM BBS WHERE bbsID = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, bbsID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				Bbs bbs = new Bbs();
				bbs.setBbsID(rs.getInt(1));
				bbs.setBbsTitle(rs.getString(2));
				bbs.setUserID(rs.getString(3));
				bbs.setBbsDate(rs.getString(4));
				bbs.setBbsContent(rs.getString(5));
				bbs.setBbsAvailable(rs.getInt(6));
				return bbs;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int update(int bbsID, String bbsTitle, String bbsContent) {
		String SQL = "UPDATE BBS SET bbsTitle = ?, bbsContent = ? WHERE bbsID = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, bbsTitle); 
			pstmt.setString(2, bbsContent); 
			pstmt.setInt(3, bbsID);
			return pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1; // �����ͺ��̽� ����
	}
	
	public int delete(int bbsID) {
		String SQL = "UPDATE BBS SET bbsAvailable = 0 WHERE bbsID = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, bbsID);
			return pstmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
}