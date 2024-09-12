package com.mindex.challenge.service.impl;

import org.springframework.stereotype.Service;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportingStructureService;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService{
	
	private final EmployeeService employeeService;
	
	public ReportingStructureServiceImpl(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	/**
     * Retrieves the reporting structure for a given employee by their ID.
     * 
     * @param id The employee ID whose reporting structure is being requested.
     * @return A ReportingStructure object containing the employee and their total number of reports.
     * @throws RuntimeException If no employee is found for the given ID.
     */
	@Override
	public ReportingStructure reportStructure(String id) {
		Employee headEmployee = employeeService.read(id);
		if(headEmployee == null) {
			throw new RuntimeException("Invalid reporting structure employeeId: " + id);
		}
		int count = countReports(headEmployee);
		return new ReportingStructure(headEmployee, count);
	}

	 /**
     * Recursively counts the number of direct and indirect reports for a given employee.
     * 
     * @param headEmployee The employee whose reports are being counted.
     * @return The total number of direct and indirect reports.
     */
	private int countReports(Employee headEmployee) {
		int count = 0;
        if (headEmployee.getDirectReports() != null) {
            for (Employee directReport : headEmployee.getDirectReports()) {
                Employee fullDirectReport = employeeService.read(directReport.getEmployeeId());
                count += 1 + countReports(fullDirectReport);
            }
        }
        return count;
    }
}
