package com.sql.demo;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.sql.demo.EmployeePayrollService.IOService;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class EmpPayrollRestAPITest {
	@Before
	public void Setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 3000;
	}

//	@Test
//	public void givenEmployeeInJSONServer_whenRetrieved_ShouldMatchTheCount() {
//		EmployeePayrollData[] arrayOfEmps = getEmployeeList();
//		EmployeePayrollService employeePayrollService = new EmployeePayrollService(Arrays.asList(arrayOfEmps));
//		long entries = employeePayrollService.countEntries();
//		Assert.assertEquals(3, entries);
//	}
//
	private EmployeePayrollData[] getEmployeeList() {
		Response response = RestAssured.get("/employees");
		System.out.println("EMPLOYEE PAYROLL ENTRIES IN JSONServer:\n" + response.asString());
		EmployeePayrollData[] arrayOfEmps = new Gson().fromJson(response.asString(), EmployeePayrollData[].class);
		return arrayOfEmps;
	}
	
//	@Test
//	public void givenNewEmployee_WhenAdded_ShouldMatch201ResponseAndCount() {
//		EmployeePayrollService employeePayrollService;
//		EmployeePayrollData[] ArrayOfEmps = getEmployeeList();
//		employeePayrollService = new EmployeePayrollService(Arrays.asList(ArrayOfEmps));
//		EmployeePayrollData employeePayrollData = new EmployeePayrollData(0, "Mark Zukerberg", "M", 300000,
//				LocalDate.now());
//		Response response = addEmployeeToJsonServer(employeePayrollData);
//		int statusCode = response.getStatusCode();
//		Assert.assertEquals(201, statusCode);
//		employeePayrollData = new Gson().fromJson(response.asString(), EmployeePayrollData.class);
//		employeePayrollService.addEmployeeToPayroll(employeePayrollData, IOService.REST_IO);
//		long entries = employeePayrollService.countEntries();
//		Assert.assertEquals(4, entries);
//	}

	private Response addEmployeeToJsonServer(EmployeePayrollData employeePayrollData) {
		String empJson = new Gson().toJson(employeePayrollData);
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.body(empJson);
		return request.post("/employees");
	}
	
	@Test
	public void givenListOfNewEmployee_whenAdded_ShouldMatch201ResponseAndCount() {
		EmployeePayrollService employeePayrollService;
		EmployeePayrollData[] ArrayOfEmps = getEmployeeList();
		employeePayrollService = new EmployeePayrollService(Arrays.asList(ArrayOfEmps));
		EmployeePayrollData[] arrayOfNewEmps = { new EmployeePayrollData(0, "Sunder", "M", 600000.0, LocalDate.now()),
				new EmployeePayrollData(0, "Mukesh", "M", 100000.0, LocalDate.now()),
				new EmployeePayrollData(0, "Anil", "M", 200000.0, LocalDate.now()) };
		for (EmployeePayrollData employeePayrollData : Arrays.asList(arrayOfNewEmps)) {
			Response response = addEmployeeToJsonServer(employeePayrollData);
			int statusCode = response.getStatusCode();
			Assert.assertEquals(201, statusCode);
			employeePayrollData = new Gson().fromJson(response.asString(), EmployeePayrollData.class);
			employeePayrollService.addEmployeeToPayroll(employeePayrollData, IOService.REST_IO);
		}
		long entries = employeePayrollService.countEntries();
		Assert.assertEquals(7, entries);
	}
}
