package com.sql.demo;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.Driver;

public class DBDemo {
	public static String jdbcURL = "jdbc:mysql://localhost:3306/payroll_service?useSSL=false";
	public static String userName = "root";
	public static String password = "password";

	public static void main(String[] args) {

		Connection con;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver loaded!");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("Cannot find the driver in the classpath!", e);
		}
		listDrivers();
		try {
			System.out.println("Connecting to database:" + jdbcURL);
			con = DriverManager.getConnection(jdbcURL, userName, password);
			System.out.println("Connection is success" + con);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<EmployeePayrollData> empList = new ArrayList();
		empList = readData();
		for (EmployeePayrollData i : empList) {
			System.out.println(i);
		}
	}

	private static void listDrivers() {
		Enumeration<Driver> driverList = DriverManager.getDrivers();
		while (driverList.hasMoreElements()) {
			Driver driverClass = (Driver) driverList.nextElement();
			System.out.println(" " + driverClass.getClass().getName());
		}

	}

	public static List<EmployeePayrollData> readData() {
		String sql = "SELECT * FROM employee_payroll;";
		List<EmployeePayrollData> employeePayrollList = new ArrayList();
		try {
			Connection connection = DriverManager.getConnection(jdbcURL, userName, password);
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			while (result.next()) {
				int id = result.getInt("id");
				String name = result.getString("name");
				double basic_pay = result.getDouble("basic_pay");
				LocalDate startDate = result.getDate("start").toLocalDate();
				employeePayrollList.add(new EmployeePayrollData(id, name, basic_pay, startDate));
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employeePayrollList;

	}
}
