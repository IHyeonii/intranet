package service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import dbConnection.MySqlConnection;
import entity.Employee;

public class SignIn {
	public static void main(String[] args) {

		Connection conn = null; // DB연결

		try {
			conn = MySqlConnection.getConnection();

			// 1. DB에서 사원 정보 가져오기
			ArrayList<Employee> emp = new ArrayList<>();
			getInfo(conn, emp);

			Scanner sc = new Scanner(System.in);
			System.out.println("사원번호와 비밀번호 입력하세요.");

			System.out.print("사원번호: ");
			int id = sc.nextInt();
			sc.nextLine(); // 왜 이게 있어야 엔터쳐서 입력할 수 있지 ??
			System.out.print("비밀번호: ");
			String pw = sc.nextLine();

			for (Employee e : emp) {
				int empId = e.getEmpId();
				String empPw = e.getEmpPw();

				if (id == empId) {
					if (pw.equals(empPw)) {
						System.out.println("로그인 성공");
						System.out.println("업무를 선택하세요.");
						System.out.print("1. 제안서 작성	2. 제안서 검토	>");

						int pick = sc.nextInt();

						if (pick == 1) {
							CreateNewDoc.writeProposal(conn, sc, e);
							CreateNewDoc.insertRequester(conn);
						}

						if (pick == 2) {
							ReviewDoc.reviewProposal(conn, sc, empId);
							ReviewDoc.insertApprover(conn);
						}
						
						break;
					} else {
						System.out.println("로그인 실패, 비밀번호를 확인해주세요.");
					}
				} else if (id != empId) {
					System.out.println("로그인 실패, 정보를 확인해주세요.");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.setAutoCommit(true);
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e2) {
			}
		}
	}

	// 1. DB에서 사원 정보를 가져와서 -> 입력받은 정보와 비교
	public static void getInfo(Connection conn, ArrayList<Employee> empList) throws SQLException {
		// Staement -> SQL문을 데이터베이스에 보내기위한 객체
		Statement stmt = conn.createStatement();

		String sql = "SELECT * FROM employee";
		ResultSet rs = stmt.executeQuery(sql);

		while (rs.next()) {
			Employee emp = new Employee();
			emp.setEmpId(rs.getInt(1));
			emp.setEmpPw(rs.getString(2));
			emp.setName(rs.getString(3));

			empList.add(emp);
		}
	}
}
