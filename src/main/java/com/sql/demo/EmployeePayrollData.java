package com.sql.demo;

import java.time.LocalDate;

public class EmployeePayrollData {
	public int id;
	public String name;
	public double basic_pay;
	public LocalDate start;
	public String gender;
	public String department;
	public String phone;
	public String address;
	
	
	public EmployeePayrollData(int id, String name, double basic_pay) {
		this.id = id;
		this.name = name;
		this.basic_pay = basic_pay;
	}

	public EmployeePayrollData(int id, String name, double basic_pay, LocalDate start) {
		this.id = id;
		this.name = name;
		this.basic_pay = basic_pay;
		this.start = start;
	}
	
	public EmployeePayrollData(int id, String name, double salary, LocalDate startDate,String gender,String department) {
		this(id, name, salary);
		this.start = startDate;
		this.gender=gender;
		this.department=department;
	}
	
	public EmployeePayrollData(int id, String name, double salary, LocalDate startDate,String gender,String department, String phone, String address) {
		this(id, name, salary);
		this.start = startDate;
		this.gender=gender;
		this.department=department;
		this.address=address;
		this.phone=phone;
	}

	@Override
	public String toString() {
		return "id=" + id + ", name=" + name + ", salary=" + basic_pay+"; ";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		EmployeePayrollData that = (EmployeePayrollData) o;
		return id == that.id && Double.compare(that.basic_pay, basic_pay) == 0 && name.equals(that.name);
	}
	
	

}