package jdbc.step2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestQueryAllByPreparedStatement {

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
		final String SELECT_All_FROM_MEMBER = "Select * from MEMBER";

		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			ResultSet rs = null;

			PreparedStatement pstmt = con.prepareStatement(SELECT_All_FROM_MEMBER);
			rs = pstmt.executeQuery();
			
			int i = 0;
			
			while (rs.next()) {
				System.out.print("第"+ ++i+"筆資料");
				String userName = rs.getString("NAME");
				System.out.print("使用者帳號: "+userName+"\t");
				String userPWD = rs.getString("PASSWORD");
				System.out.println("使用者密碼: "+userPWD);
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

}
