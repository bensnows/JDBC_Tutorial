package jdbc.step4;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestReturnData {
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

		final String userAc = "KING";
		final String SELECT_All_FROM_MEMBER_ByName = "Select * from Employee where ename = ?";

		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			ResultSet rs = null;

			PreparedStatement pstmt = con.prepareStatement(SELECT_All_FROM_MEMBER_ByName);
			pstmt.setString(1, userAc);

			rs = pstmt.executeQuery();

			while (rs.next()) {
//				callResult(rs);
				callResultByColumnIndex(rs);
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

	private static void callResult(ResultSet rs) throws SQLException {
		String empNo = rs.getString("empno");
		System.out.print("編號: " + empNo + "\t");
		
		String eName = rs.getString("eName");
		System.out.print("員工編號: " + eName + "\t");
		
		String job = rs.getString("job");
		System.out.print("職稱: " + job + "\t");
		
		Date hireDate = rs.getDate("hireDate");
		System.out.print("日期:  " + hireDate + "\t");
		
		double salary = rs.getDouble("sal");
		System.out.print("薪資: " + salary + "\t");
		
		int comm = rs.getInt("comm");
		System.out.print("獎金: " + comm + "\t");

		int deptNo = rs.getInt("deptno");
		System.out.print("部門編號: "+ deptNo);

		System.out.println();
	}
	private static void callResultByColumnIndex(ResultSet rs) throws SQLException {
		int empNo = rs.getInt(1);
		System.out.print("編號: " + empNo + "\t");
		
		String eName = rs.getString(2);
		System.out.print("員工編號: " + eName + "\t");
		
		String job = rs.getString(3);
		System.out.print("職稱: " + job + "\t");
		
//		Date hireDate = rs.getDate(1);
//		System.out.print("日期:  " + hireDate + "\t");
//		
		double salary = rs.getDouble(1);
		System.out.print("薪資: " + salary + "\t");
		
		double comm = rs.getDouble(2);
		System.out.print("獎金: " + comm + "\t");

		int deptNo = rs.getInt(2);
		System.out.print("部門編號: "+ deptNo);

		System.out.println();
	}
	
	
}
