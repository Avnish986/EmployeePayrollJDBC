package com.sql.demo;

import java.time.LocalDate;

public class EmployeePayrollData {
	public int id;
	public String name;
	public double basic_pay;
	public LocalDate start;

	public EmployeePayrollData(int id, String name, double basic_pay, LocalDate start) {
		this.id = id;
		this.name = name;
		this.basic_pay = basic_pay;
		this.start = start;
	}

	@Override
	public String toString() {
		return " id: " + id + " name: " + name + " basic_pay: " + basic_pay + " startdate: " + start;
	}

}