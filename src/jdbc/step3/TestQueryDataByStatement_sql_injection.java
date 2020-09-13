package jdbc.step3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestQueryDataByStatement_sql_injection {

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

		//select by name
//		final String userAc = "123@abc.com";
//		final String SELECT_All_FROM_MEMBER_ByName = "Select * from MEMBER where name = '"+userAc+"'";
		
		//select by injection
		final String incorrectString = "1' OR '1'='1";		
		final String SELECT_All_FROM_MEMBER_ByNameWithInjection = "Select * from MEMBER where name = '"+incorrectString+"'";
		
		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			ResultSet rs = null;

			Statement stmt = con.createStatement();
//			rs = stmt.executeQuery(SELECT_All_FROM_MEMBER_ByName);
			System.out.println(SELECT_All_FROM_MEMBER_ByNameWithInjection);
			rs = stmt.executeQuery(SELECT_All_FROM_MEMBER_ByNameWithInjection);

			
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
