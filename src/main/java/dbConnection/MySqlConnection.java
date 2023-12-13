package dbConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlConnection {
	public static Connection getConnection() throws Exception {
		// DB Driver
		String dbDriver = "com.mysql.cj.jdbc.Driver";

		// DB URL, ID, PW
		String dbUrl = "jdbc:mysql://주소/DB명?serverTimezone=UTC";
		String dbUser = "";
		String dbPassword = "";

		Connection conn = null;

		try {
			Class.forName(dbDriver);
			System.out.println("Driver Loading Success");

			conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			System.out.println("DB Connection 성공");

		} catch (SQLException e) {
			System.out.println("DB Connection 실패");
			e.printStackTrace();
		}
		return conn;
	}
}
