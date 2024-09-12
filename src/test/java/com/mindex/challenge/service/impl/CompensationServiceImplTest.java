package com.mindex.challenge.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {

    @Autowired
    private CompensationService compensationService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompensationRepository compensationRepository;

    @Before
    public void setup() {
        compensationRepository.deleteAll();
        employeeRepository.deleteAll();
    }

    @Test
    public void testCreateAndGetCompensation() {
        Employee testEmployee = new Employee();
        testEmployee.setEmployeeId("test-id");
        testEmployee.setFirstName("John");
        testEmployee.setLastName("Doe");
        testEmployee.setDepartment("Engineering");
        testEmployee.setPosition("Developer");
        employeeRepository.save(testEmployee);

        Compensation testCompensation = new Compensation();
        testCompensation.setEmployee(testEmployee);
        testCompensation.setSalary(new BigDecimal("75000.00"));
        testCompensation.setEffectiveDate(LocalDate.now());

        // Test create
        Compensation createdCompensation = compensationService.createCompensation(testCompensation);
        assertEquals(testEmployee.getEmployeeId(), createdCompensation.getEmployee().getEmployeeId());
        assertEquals(testCompensation.getSalary(), createdCompensation.getSalary());
        assertEquals(testCompensation.getEffectiveDate(), createdCompensation.getEffectiveDate());

        // Test get
        Compensation retrievedCompensation = compensationService.getCompensation(testEmployee.getEmployeeId());
        assertEquals(createdCompensation.getEmployee().getEmployeeId(), retrievedCompensation.getEmployee().getEmployeeId());
        assertEquals(createdCompensation.getSalary(), retrievedCompensation.getSalary());
        assertEquals(createdCompensation.getEffectiveDate(), retrievedCompensation.getEffectiveDate());
    }

    @Test(expected = RuntimeException.class)
    public void testCreateCompensation_EmployeeNotFound() {
        // Setup an employee that does not exist
        Employee nonExistentEmployee = new Employee();
        nonExistentEmployee.setEmployeeId("non-existent-id");

        // Create a compensation with the non-existent employee
        Compensation testCompensation = new Compensation();
        testCompensation.setEmployee(nonExistentEmployee);
        testCompensation.setSalary(new BigDecimal("75000.00"));
        testCompensation.setEffectiveDate(LocalDate.now());
        
        compensationService.createCompensation(testCompensation);
    }

    @Test(expected = RuntimeException.class)
    public void testGetCompensation_NotFound() {
        // Attempt to get compensation for a non-existent employee ID
        Compensation retrievedCompensation = compensationService.getCompensation("non-existent-id");

        // Assert that the retrieved compensation is null
        assertNull(retrievedCompensation);
    }
}
