package com.mindex.challenge.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;

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
import com.mindex.challenge.service.ReportingStructureService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureServiceImplTest {
	private String reportingStructureUrl;

    @Autowired
    private ReportingStructureService reportingStructureService;

    @Autowired
    private EmployeeService employeeService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        reportingStructureUrl = "http://localhost:" + port + "/reportingStructure/{id}";
    }

    @Test
    public void testReportingStructure() {
        // Create test employees
        Employee johnLennon = createTestEmployee("John", "Lennon", "Music", "Lead");
        Employee paulMcCartney = createTestEmployee("Paul", "McCartney", "Music", "Rhythm");
        Employee ringoStarr = createTestEmployee("Ringo", "Starr", "Music", "Drums");
        Employee peteBest = createTestEmployee("Pete", "Best", "Music", "Drums");
        Employee georgeHarrison = createTestEmployee("George", "Harrison", "Music", "Lead");

        // Set up reporting structure
        johnLennon.setDirectReports(Arrays.asList(paulMcCartney, ringoStarr));
        ringoStarr.setDirectReports(Arrays.asList(peteBest, georgeHarrison));

        // Save the structure
        employeeService.create(johnLennon);
        employeeService.create(paulMcCartney);
        employeeService.create(ringoStarr);
        employeeService.create(peteBest);
        employeeService.create(georgeHarrison);

        // Test the reporting structure
        ReportingStructure reportingStructure = restTemplate.getForEntity(reportingStructureUrl, 
                                                ReportingStructure.class, 
                                                johnLennon.getEmployeeId()).getBody();

        assertNotNull(reportingStructure);
        assertEquals(johnLennon.getEmployeeId(), reportingStructure.getEmployee().getEmployeeId());
        assertEquals(4, reportingStructure.getNumberOfReports());
    }

    @Test
    public void testReportingStructureWithNoReports() {
        Employee employee = createTestEmployee("Solo", "Artist", "Music", "Singer");
        employeeService.create(employee);

        ReportingStructure reportingStructure = restTemplate.getForEntity(reportingStructureUrl, 
                                                ReportingStructure.class, 
                                                employee.getEmployeeId()).getBody();

        assertNotNull(reportingStructure);
        assertEquals(employee.getEmployeeId(), reportingStructure.getEmployee().getEmployeeId());
        assertEquals(0, reportingStructure.getNumberOfReports());
    }

    private Employee createTestEmployee(String firstName, String lastName, String department, String position) {
        Employee employee = new Employee();
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setDepartment(department);
        employee.setPosition(position);
        return employee;
    }
}
