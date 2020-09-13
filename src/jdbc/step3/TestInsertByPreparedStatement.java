package jdbc.step3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestInsertByPreparedStatement {

	private final static String USER = "JDBC";
	private final static String PASSWORD = "JDBC";
	private final static String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private final static String DRIVER = "oracle.jdbc.driver.OracleDriver";

	static {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println("啟動 Driver 錯誤，請確認Driver是否正確被引用");
			// 請設定並讓專案引用 item/ojebc6.jar (oracle jdbc)
		}
	}

	public static void main(String[] args) {
		Connection con = null;

		final String SQL = "INSERT INTO EMPLOYEE(EMPNO, ENAME, JOB, HIREDATE, SAL, COMM, DEPTNO)"
				+ "VALUES(?, ?, ?, ?, ?, ?, ?)";

		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			ResultSet rs = null;

			//test1
			PreparedStatement pstmt = con.prepareStatement(SQL);
			pstmt.setInt(1, 7015);
			pstmt.setString(2, "DAVID");
			pstmt.setString(3, "MANAGER");
			pstmt.setDate(4, java.sql.Date.valueOf("2016-01-01"));
			pstmt.setDouble(5, 2500);
			pstmt.setDouble(6, 0.0);
			pstmt.setInt(7, 40);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				callResult(rs);
			}
		} catch (SQLException e) {
			System.out.println("SQL 錯誤，原因: " + e.getMessage());
		} finally {
			try {
				if (con != null) {
					con.close();
					System.out.println("使用完畢後關閉連線");
				}
			} catch (SQLException e1) {
				System.out.println("關閉連線錯誤");
			}
		}
	}
	
	private static void callResult (ResultSet rs) throws SQLException {
		String id = rs.getString("id");
		System.out.print("編號: "+id+"\t");
		String userName = rs.getString("NAME");
		System.out.print("使用者帳號: "+userName+"\t");
		String userPWD = rs.getString("PASSWORD");
		System.out.println("使用者密碼: "+userPWD);
	}
}
