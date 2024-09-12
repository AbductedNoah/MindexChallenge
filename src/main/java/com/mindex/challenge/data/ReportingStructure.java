package com.mindex.challenge.data;


public class ReportingStructure {
	
	private Employee employee; 
	private int numberOfReports;
	
	public ReportingStructure(Employee employee, int numberOfReports) {
		super();
		this.employee = employee;
		this.numberOfReports = numberOfReports;
	}
	
	/**
	 * @return the employee
	 */
	public Employee getEmployee() {
		return employee;
	}
	/**
	 * @param employee the employee to set
	 */
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	/**
	 * @return the numberOfReports
	 */
	public int getNumberOfReports() {
		return numberOfReports;
	}
	/**
	 * @param numberOfReports the numberOfReports to set
	 */
	public void setNumberOfReports(int numberOfReports) {
		this.numberOfReports = numberOfReports;
	}
}
