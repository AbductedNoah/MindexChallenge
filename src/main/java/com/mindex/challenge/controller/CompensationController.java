package com.mindex.challenge.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;

@RestController
public class CompensationController {
	
	private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);
	
	private CompensationService compensationService;
	
	public CompensationController(CompensationService compensationService) {
		this.compensationService = compensationService;
	}
	
	/**
     * POST endpoint to create a new Compensation.
     * 
     * @param compensation The Compensation object containing details of the compensation to be created.
     * @return The created Compensation object.
     */
	@PostMapping("/compensation")
    public Compensation createCompensation(@RequestBody Compensation compensation) {
		LOG.debug("Request received to create Compensation with employeeId [{}]", compensation.getEmployee().getEmployeeId());
        return compensationService.createCompensation(compensation);
    }

	/**
     * GET endpoint to retrieve Compensation by employee ID.
     * 
     * @param employeeId The ID of the employee whose compensation details are being retrieved.
     * @return The Compensation object corresponding to the provided employee ID.
     */
    @GetMapping("/compensation/{employeeId}")
    public Compensation getCompensation(@PathVariable String employeeId) {
        LOG.debug("Request received to get Compensation by employee id [{}]", employeeId);
        return compensationService.getCompensation(employeeId);
    }
}
