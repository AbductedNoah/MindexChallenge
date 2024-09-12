package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private EmployeeRepository employeeRepository;
    
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
    	this.employeeRepository = employeeRepository;
    }

    /**
     * Creates a new employee record.
     * Generates a unique employee ID and processes the employee's direct reports before saving.
     * 
     * @param employee The Employee object to be created.
     * @return The created Employee object.
     */
    @Override
    public Employee create(Employee employee) {
        LOG.debug("Creating employee [{}]", employee);

        employee.setEmployeeId(UUID.randomUUID().toString());
        employee.setDirectReports(fillDirectReports(employee.getDirectReports()));
        employeeRepository.insert(employee);

        return employee;
    }

    /**
     * Fills the direct reports for an employee by retrieving the full employee records for each report.
	 *
     * @param directReports The list of employees who are direct reports.
     * @return A list of fully populated Employee objects for the direct reports.
     */
    private List<Employee> fillDirectReports(List<Employee> directReports) {
        if (directReports == null || directReports.isEmpty()) {
            return Collections.emptyList();
        }

        List<Employee> filledReportsEmployees = new ArrayList<>();
        for (Employee employee : directReports) {
            filledReportsEmployees.add(read(employee.getEmployeeId()));
        }
        return filledReportsEmployees;
    }

    /**
     * Retrieves an employee record by employee ID.
     * 
     * @param id The employee ID to retrieve.
     * @return The Employee object with the given ID.
     * @throws RuntimeException If no employee is found with the given ID.
     */
	@Override
    public Employee read(String id) {
        LOG.debug("Finding employee with id [{}]", id);

        Employee employee = employeeRepository.findByEmployeeId(id);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        return employee;
    }

	/**
     * Updates an existing employee record.
     * 
     * @param employee The Employee object containing updated information.
     * @return The updated Employee object.
     */
    @Override
    public Employee update(Employee employee) {
        LOG.debug("Updating employee [{}]", employee);

        return employeeRepository.save(employee);
    }
}
