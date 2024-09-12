package com.mindex.challenge.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;

@Service
public class CompensationServiceImpl implements CompensationService {
	
	private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);
	
	private CompensationRepository compensationRepository;
	private EmployeeRepository employeeRepository;
	
	public CompensationServiceImpl(CompensationRepository compensationRepository, EmployeeRepository employeeRepository) {
		this.compensationRepository = compensationRepository;
		this.employeeRepository = employeeRepository;
	}

	/**
     * Creates a new compensation entry for an employee.
     * 
     * @param compensation The Compensation object to be created.
     * @return The saved Compensation object after creation.
     * @throws RuntimeException If the employee associated with the compensation is not found.
     */
	@Override
    public Compensation createCompensation(Compensation compensation) {
        Employee employee = employeeRepository.findByEmployeeId(compensation.getEmployee().getEmployeeId());
        if (employee == null) {
            LOG.debug("No employee found");
            throw new RuntimeException("No employee found");
        }
        compensation.setEmployee(employee);
        return compensationRepository.save(compensation);
    }

	/**
     * Retrieves the compensation details for a specific employee by employee ID.
     * 
     * @param employeeId The employee ID for which compensation is being requested.
     * @return The Compensation object associated with the given employee ID.
     * @throws RuntimeException If no compensation is found for the given employee ID.
     */
    @Override
    public Compensation getCompensation(String employeeId) {
        Compensation compensation = compensationRepository.findByEmployeeEmployeeId(employeeId);
        if (compensation == null) {
            LOG.debug("Compensation not found for employee ID: [{}]", employeeId);
            throw new RuntimeException("Compensation not found for employeeId: " + employeeId);
        }
        return compensation;
    }

}
