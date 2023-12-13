package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Scanner;

import entity.Employee;

public class CreateNewDoc {
	public static void writeProposal(Connection conn, Scanner sc, Employee emp) throws SQLException {
		conn.setAutoCommit(false);
		String sql = " INSERT INTO approval(emp_id, emp_nm, title, contents, date, submit, check_up, app_status) VALUES(?, ?, ?, ?, ?, ?, ?, ?) ";
		PreparedStatement psmt = conn.prepareStatement(sql);

		// 여기서 작성한게 DB에 insert 되야 함
		System.out.println("제안서 작성");
		sc.nextLine(); // 숫자 -> 문자로 넘어오면서 개행문자 문제때문이었어
		System.out.print("제목: ");
		String title = sc.nextLine();

		System.out.print("본문: ");
		String contents = sc.nextLine();

		System.out.print("제출 버튼 [임시저장: T, 제출: S, 취소: C]		>");
		String pick = sc.nextLine();

		if (pick.equals("T") || pick.equals("S")) {
			// insert
			int id = emp.getEmpId();
			String name = emp.getName();

			psmt.setInt(1, id);
			psmt.setString(2, name);
			psmt.setString(3, title);
			psmt.setString(4, contents);
			psmt.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
			psmt.setString(6, pick);
			psmt.setInt(7, 0);
			psmt.setInt(8, -1);

			psmt.executeUpdate();
		}
		conn.commit();

		// 취소버튼 -> Scanner 종료
		if (pick.equals("C")) {
			System.exit(0);
		}
	}

	public static void insertRequester(Connection conn) throws SQLException {
		String selectSql = "SELECT app_id, emp_id FROM approval";
		PreparedStatement psmt1 = conn.prepareStatement(selectSql);
		ResultSet rs = psmt1.executeQuery(); // 여기에 Select한거 담아

		conn.setAutoCommit(false);
		String inSql = "INSERT INTO requester VALUES(?, ?)";
		PreparedStatement psmt2 = conn.prepareStatement(inSql);

		while (rs.next()) {
			psmt2.setInt(1, rs.getInt(1));
			psmt2.setInt(2, rs.getInt(2));
			psmt2.executeUpdate();
		}
		rs.close();
		conn.commit();
	}
}
