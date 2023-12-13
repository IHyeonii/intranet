package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import entity.ProposalList;

public class ReviewDoc {
	public static void reviewProposal(Connection conn, Scanner sc, int id) throws SQLException {
		// 2번 선택하면, DB에서 검토전인 제안서 목록이 출력
		// select -> submit == S && check_up = 0 인 제안서의 타이틀, 요청자, 일자가 리스트 형식으로
		// 일자 기준으로 오름차순 정렬
		String sql = "SELECT app_id, emp_id, emp_nm, title, date, check_up, app_status FROM approval "
				+ "where submit = 'S' and check_up = 0 order by date asc";

		// DB로 해당 sql문 날리고
		PreparedStatement psmt = conn.prepareStatement(sql);

		// sql 결과 담을 ResultSet 객체 생성
		ResultSet rs = psmt.executeQuery();

		System.out.println("제안서 목록");

		Map<Integer, ProposalList> proList = new HashMap<>();

		// 반복문 -> ResultSet에 남은 값 없을때까지 데이터 추출
		while (rs.next()) {
			ProposalList list = new ProposalList();
			list.setAppId(rs.getInt(1));
			list.setEmpId(rs.getInt(2));
			list.setEmpNm(rs.getString(3));
			list.setTitle(rs.getString(4));
			list.setDate(rs.getTimestamp(5));
			list.setCheckUp(rs.getInt(6));
			list.setAppStatus(rs.getInt(7));

			proList.put(list.getAppId(), list);
		}

		StringBuilder sb = new StringBuilder();

		// title
		sb.append("ID | 사원번호 | 사원명 | 문서명 | 일자 | 검토여부 | 결재상태\n");

		for (Map.Entry<Integer, ProposalList> pro : proList.entrySet()) {
			int appId = pro.getKey();
			ProposalList list = pro.getValue();

			if (list.getEmpId() == id) {
				System.out.println("검토할 제안서가 없습니다.");
				break;
			}

			sb.append(list.getAppId()).append("|");
			sb.append(list.getEmpId()).append("|");
			sb.append(list.getEmpNm()).append("|");
			sb.append(list.getTitle()).append("|");
			sb.append(list.getDate()).append("|");
			sb.append(list.getCheckUp()).append("|");
			sb.append(list.getAppStatus()).append("\n");
		}
		System.out.println(sb);

		// 제안서 update
		updateProposal(conn, sc, proList);
	}

	public static void updateProposal(Connection conn, Scanner sc, Map<Integer, ProposalList> proList)
			throws SQLException {
		// update 준비
		conn.setAutoCommit(false);
		String sql = "UPDATE approval SET check_up = ?, app_status = ? WHERE app_id = ?";
		PreparedStatement psmt = conn.prepareStatement(sql);

		// 검토여부 선택
		System.out.print("검토할 제안서  ID를 선택하세요.		>");
		int selectId = sc.nextInt();

		System.out.print("제안서 승인여부 [반려: 0, 승인:1]	>");
		int select = sc.nextInt();

		for (Map.Entry<Integer, ProposalList> pro : proList.entrySet()) {
			int appId = pro.getKey();

			if (appId == selectId) {
				psmt.setInt(3, selectId);
				psmt.setInt(1, 1); // check_up = 1 -> 검토

				if (select == 0) { // 반려
					psmt.setInt(2, 0);
				} else if (select == 1) {
					psmt.setInt(2, 1);
				}
				
				psmt.executeUpdate();
			}
		}
		conn.commit();
	}

	public static void insertApprover(Connection conn) throws SQLException {
		String selectSql = "SELECT app_id, emp_id FROM approval";
		PreparedStatement psmt1 = conn.prepareStatement(selectSql);
		ResultSet rs = psmt1.executeQuery(); 

		conn.setAutoCommit(false);
		String inSql = "INSERT INTO approver VALUES(?, ?)";
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
