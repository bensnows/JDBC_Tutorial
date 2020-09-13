package jdbc.step1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestConnection {

	private final static String USER = "JDB1C";
	private final static String PASSWORD = "JDBC";
	private final static String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private final static String DRIVER = "oracle.jdbc.driver.OracleDriver";

	static {
		//static 區塊，代表此片段在建構 class前載入，若是常使用而重複、不改變的資源，可放於此處
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println("啟動 Driver 錯誤，請確認Driver是否正確被引用");
			//請設定並讓專案引用 item/ojebc6.jar (oracle jdbc) 
		}
	}

	public static void main(String[] args) {
		Connection con = null;

		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);

			if (con != null) {
				System.out.println("資料庫連到 User: " + USER + "成功");
			}
		} catch (SQLException e) {
			System.out.println("SQL 錯誤，原因: "+ e.getMessage());
		} finally {
			try {
				if (con != null) {
//					JDK7 開始，若類別有實作 AutoCloseeable的介面，則在 try{} 結束時會自動關閉"
//					但避免遇到 JDK7 以前的版本，習慣在 finally 寫入 con.close()是好習慣"
					con.close();
					System.out.println("使用完畢後關閉連線");
				}
			} catch (SQLException e1) {
				System.out.println("關閉連線錯誤");
			}

		}

	}

}
