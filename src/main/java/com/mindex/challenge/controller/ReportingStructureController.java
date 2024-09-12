package com.mindex.challenge.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;

@RestController
public class ReportingStructureController {

	private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureController.class);

    private ReportingStructureService reportingService;
    
    public ReportingStructureController(ReportingStructureService reportingService) {
    	this.reportingService = reportingService;
    }

    /**
     * Get endpoint for retrieving the reporting structure of a given employeeId. 
     * 
     * @param employeeId the employeeId
     * @return The ReportingStructure
     */
    @GetMapping("/reportingStructure/{employeeId}")
    public ReportingStructure getStructure(@PathVariable String employeeId) {
        LOG.debug("Received employee structure request for employeeId=[{}]", employeeId);

        return reportingService.reportStructure(employeeId);
    }
}
