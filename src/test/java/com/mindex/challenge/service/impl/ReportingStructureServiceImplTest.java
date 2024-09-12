package com.mindex.challenge.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureServiceImplTest {

    private String employeeUrl;
    private String reportingStructureUrl;

    @Autowired
    private TestRestTemplate restTemplate;
    
    @Autowired
    private EmployeeService employeeService;
    
    @Autowired
    private ReportingStructureServiceImpl reportingStructureServiceImpl;

    @LocalServerPort
    private int port;

    @Before
    public void setup() {
        employeeUrl = "http://localhost:" + port + "/employee";
        reportingStructureUrl = "http://localhost:" + port + "/reportingStructure/{employeeId}";
    }

    @Test
    public void testReportStructure() {
    	
    	Employee emp1 = new Employee();
        emp1.setFirstName("John");
        emp1.setLastName("Doe");
        emp1.setDepartment("Engineering");
        emp1.setPosition("Developer");
        emp1.setDirectReports(Collections.emptyList());

        Employee emp2 = new Employee();
        emp2.setFirstName("Jane");
        emp2.setLastName("Doe");
        emp2.setDepartment("Engineering");
        emp2.setPosition("Senior Developer");
        emp2.setDirectReports(Collections.emptyList());

        Employee manager = new Employee();
        manager.setFirstName("Alice");
        manager.setLastName("Smith");
        manager.setDepartment("Engineering");
        manager.setPosition("Manager");
        
        emp1 = restTemplate.postForEntity(employeeUrl, emp1, Employee.class).getBody();
        emp2 = restTemplate.postForEntity(employeeUrl, emp2, Employee.class).getBody();
        
        manager.setDirectReports(Arrays.asList(emp1, emp2));
        
        manager = restTemplate.postForEntity(employeeUrl, manager, Employee.class).getBody();
        
        
        // Test valid reporting structure
        ReportingStructure report = restTemplate.getForEntity(reportingStructureUrl, ReportingStructure.class, manager.getEmployeeId()).getBody();

        assertNotNull(report);
        assertEquals(manager.getEmployeeId(), report.getEmployee().getEmployeeId());
        assertEquals(2, report.getNumberOfReports());
    }
    
    @Test
    public void testInvalidEmployee() {
    	ReportingStructure reportingStructure = restTemplate.getForEntity(reportingStructureUrl, ReportingStructure.class, "test").getBody();
    	assertNull(reportingStructure.getEmployee());
    	assertEquals(0, reportingStructure.getNumberOfReports());
    }
}
