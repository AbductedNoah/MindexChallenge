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

	@Override
	public ReportingStructure reportStructure(String id) {
		Employee headEmployee = employeeService.read(id);
		int count = countReports(headEmployee);
		return new ReportingStructure(headEmployee, count);
	}

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
