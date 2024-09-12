package com.mindex.challenge.controller;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmployeeController {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);

    private EmployeeService employeeService;
    
    public EmployeeController(EmployeeService employeeService) {
    	this.employeeService = employeeService;
    }
    
    /**
     * POST endpoint to create a new employee.
     * 
     * @param employee The Employee object containing details of the new employee to be created.
     * @return The created Employee object.
     */
    @PostMapping("/employee")
    public Employee create(@RequestBody Employee employee) {
        LOG.debug("Received employee create request for [{}]", employee);

        return employeeService.create(employee);
    }

    /**
     * GET endpoint to retrieve an employee by their ID.
     * 
     * @param id The ID of the employee to be retrieved.
     * @return The Employee object corresponding to the provided ID.
     */
    @GetMapping("/employee/{id}")
    public Employee read(@PathVariable String id) {
        LOG.debug("Received employee read request for id [{}]", id);

        return employeeService.read(id);
    }

    /**
     * PUT endpoint to update an existing employee's details.
     * 
     * @param id The ID of the employee to be updated.
     * @param employee The updated Employee object containing new details.
     * @return The updated Employee object.
     */
    @PutMapping("/employee/{id}")
    public Employee update(@PathVariable String id, @RequestBody Employee employee) {
        LOG.debug("Received employee create request for id [{}] and employee [{}]", id, employee);

        employee.setEmployeeId(id);
        return employeeService.update(employee);
    }
}
