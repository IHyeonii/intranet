package entity;

import java.time.LocalDateTime;

public class Approval { //전자결재 테이블
	int appId = 0; // 결재ID
	String title = "";
	String contents = "";
	Employee requester; //요청자
	LocalDateTime date;
	int checkUp = 0; // 검토 여부
	int appStatus = -1; // 결재 상테
	SubmissionStatus submit; //제출여부
}
