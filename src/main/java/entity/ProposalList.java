package entity;

import java.sql.Timestamp;

public class ProposalList { //전자결재 테이블
	int appId = 0;
	int empId = 0;
	String empNm = "";
	String title = "";
	Timestamp date;
	int checkUp = 0;
	int appStatus = -1;
	
	public int getAppId() {
		return appId;
	}
	public void setAppId(int appId) {
		this.appId = appId;
	}
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public String getEmpNm() {
		return empNm;
	}
	public void setEmpNm(String empNm) {
		this.empNm = empNm;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp time) {
		this.date = time;
	}
	public int getCheckUp() {
		return checkUp;
	}
	public void setCheckUp(int checkUp) {
		this.checkUp = checkUp;
	}
	public int getAppStatus() {
		return appStatus;
	}
	public void setAppStatus(int appStatus) {
		this.appStatus = appStatus;
	}
	
	@Override
	public String toString() {
		return "ReviewList [appId=" + appId + ", empId=" + empId + ", empNm=" + empNm + ", title=" + title + ", date="
				+ date + ", checkUp=" + checkUp + ", appStatus=" + appStatus + "]";
	}
}
