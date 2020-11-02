package com.sql.demo;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.sql.demo.EmployeePayrollService.IOService;

public class EmployeePayrollTest {
//	@Test
//	public void givenDatabase_ShouldReturn_SizeOfEmplList() {
//		EmployeePayrollService service = new EmployeePayrollService();
//		List<EmployeePayrollData> empList = service.readEmployeePayrollData(IOService.DB_IO);
//		System.out.println(empList);
//		Assert.assertEquals(5, empList.size());
//	}
//
//	@Test
//	public void givenUpdatedSalaryForEmployee_WhenUpdated_ShouldMatchWithDB() {
//		EmployeePayrollService service = new EmployeePayrollService();
//		List<EmployeePayrollData> employeePayrollData = service.readEmployeePayrollData(IOService.DB_IO);
//		service.updateEmployeeSalary("Terisa", 3000000.00);
//		boolean result = service.checkEmployeePayrollInSyncWithDB("Terisa", 3000000.00);
//		Assert.assertTrue(result);
//	}
//
//	@Test
//	public void givenUpdatedSalaryForEmployee_WhenUpdatedUsingPreparedStatement_ShouldSyncWithDB() {
//		EmployeePayrollService service = new EmployeePayrollService();
//		List<EmployeePayrollData> employeePayrollData = service.readEmployeePayrollData(IOService.DB_IO);
//		service.updateEmployeeSalaryUsingPrepareStatement("Terisa", 2000000.00);
//		boolean result = service.checkEmployeePayrollInSyncWithDB("Terisa", 2000000.00);
//		Assert.assertTrue(result);
//	}
//
//	@Test
//	public void givenSpecifiesDateRange_WhenDataRetrieved_ShouldMatchEmployeeCount() {
//		EmployeePayrollService service = new EmployeePayrollService();
//		service.readEmployeePayrollData(IOService.DB_IO);
//		LocalDate startDate = LocalDate.of(2018, 01, 01);
//		LocalDate endDate = LocalDate.now();
//		List<EmployeePayrollData> employeePayrollData = service.readEmployeeDetailsForDateRange(IOService.DB_IO,
//				startDate, endDate);
//		Assert.assertEquals(5, employeePayrollData.size());
//	}
//
//	@Test
//	public void givenEmployeePayrollData_WhenRetrievedAvg_SalaryByGender_ShouldReturnValuesAsExpected() {
//		EmployeePayrollService service = new EmployeePayrollService();
//		service.readEmployeePayrollData(IOService.DB_IO);
//		Map<String, Double> averageSalaryByGender = service.readAverageSalaryByGender(IOService.DB_IO);
//		Assert.assertTrue(
//				averageSalaryByGender.get("M").equals(200000.0) && averageSalaryByGender.get("F").equals(2000000.0));
//	}

//	@Test
//	public void givenNewEmployee_WhenAdded_ShouldSyncWityhDB() {
//		EmployeePayrollService service = new EmployeePayrollService();
//		service.readEmployeePayrollData(IOService.DB_IO);
//		service.addEmployeeToPayroll(30,"Mark", 5000000.00, LocalDate.now(), "M", "Sales");
//		boolean result = service.checkEmployeePayrollInSyncWithDB("Mark", 5000000.00);
//		Assert.assertTrue(result);
//	}
	
//	@Test
//	public void givenNewEmployee_WhenAddedToPayroll_ShouldSyncWityhDB() {
//		EmployeePayrollService service = new EmployeePayrollService();
//		service.readEmployeePayrollData(IOService.DB_IO);
//		service.addEmployeeToPayrollERDiagram(36,"Glen", 5000000.00, LocalDate.now(), "M","Sales","9888888888","50,Model Town");
//		boolean result = service.checkEmployeePayrollInSyncWithDB("Glen", 5000000.00);
//		Assert.assertTrue(result);
//	}
	
//	@Test
//	public void givenEmployee_WhenRemovedFromPayroll_ShouldSyncWityhDB() {
//		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
//		employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
//		employeePayrollService.removeEmployee("Glen");
//		boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("Glen", 5000000.00);
//		Assert.assertTrue(result);
//	}

//	@Test 
//    public void given3Employees_WhenAdded_ShouldMatchEmpCount() {
//    	EmployeePayrollData[] empPayrollData = {
//    			new EmployeePayrollData(50, "Jeff", 60000.0, LocalDate.now(),"M","sales"),
//    			new EmployeePayrollData(51, "Elon", 70000.0, LocalDate.now(),"M","maketing"),
//    			new EmployeePayrollData(52, "Tim", 50000.0, LocalDate.now(),"M","HR")
//    	};
//    	EmployeePayrollService empPayrollService = new EmployeePayrollService();
//    	empPayrollService.readEmployeePayrollData(IOService.DB_IO);
//    	Instant start = Instant.now();
//    	empPayrollService.addEmployeeToPayrollWithoutThreads(Arrays.asList(empPayrollData));
//    	Instant end = Instant.now();
//    	System.out.println("Duration without thread : " + Duration.between(start, end));
//    	List<EmployeePayrollData> employeePayrollData = empPayrollService.readEmployeePayrollData(IOService.DB_IO);
//    	Assert.assertEquals(11, employeePayrollData.size());
//    }
	
	@Test 
    public void given3Employees_WhenAdded_ShouldMatchEmpCountthreads() {
    	EmployeePayrollData[] empPayrollData = {
    			new EmployeePayrollData(53, "Keff", 60000.0, LocalDate.now(),"M","sales"),
    			new EmployeePayrollData(54, "Slon", 70000.0, LocalDate.now(),"M","maketing"),
    			new EmployeePayrollData(55, "Kim", 50000.0, LocalDate.now(),"M","HR")
    	};
    	EmployeePayrollService empPayrollService = new EmployeePayrollService();
    	empPayrollService.readEmployeePayrollData(IOService.DB_IO);
    	Instant start = Instant.now();
    	empPayrollService.addEmployeeToPayrollWithThreads(Arrays.asList(empPayrollData));
    	Instant end = Instant.now();
    	System.out.println("Duration without thread : " + Duration.between(start, end));
    	List<EmployeePayrollData> employeePayrollData = empPayrollService.readEmployeePayrollData(IOService.DB_IO);
    	Assert.assertEquals(14, employeePayrollData.size());
    }

	

}
