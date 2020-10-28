package com.sql.demo;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;


public class EmployeePayrollTest {
	@Test
	public void givenDatabase_ShouldReturn_SizeOfEmplList() {
		DBDemo db = new DBDemo();
		List<EmployeePayrollData> empList = db.readData();
		Assert.assertEquals(4, empList.size());
	}
}
