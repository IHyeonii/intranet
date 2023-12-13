package entity;

public class Employee {
	int empId = 0; //사번 = 로그인ID
	String empPw = "";
	String name = "";
	
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public String getEmpPw() {
		return empPw;
	}
	public void setEmpPw(String empPw) {
		this.empPw = empPw;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Employee [empId=" + empId + ", empPw=" + empPw + ", name=" + name + "]";
	}
}
