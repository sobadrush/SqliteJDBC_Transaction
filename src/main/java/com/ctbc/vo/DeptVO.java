package com.ctbc.vo;

public class DeptVO {
	
	private Integer deptNo;
	private String deptName;
	private String deptLoc;

	public DeptVO() {
		
	}

	public DeptVO(String deptName, String deptLoc) {
		this.deptName = deptName;
		this.deptLoc = deptLoc;
	}

	public Integer getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(Integer deptNo) {
		this.deptNo = deptNo;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptLoc() {
		return deptLoc;
	}

	public void setDeptLoc(String deptLoc) {
		this.deptLoc = deptLoc;
	}

	@Override
	public String toString() {
		return "DeptVO [deptNo=" + deptNo + ", deptName=" + deptName + ", deptLoc=" + deptLoc + "]";
	}
	
}
