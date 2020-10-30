package com.sql.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class EmployeePayrollDBService {

	private PreparedStatement preparedStatement;
	private static EmployeePayrollDBService dbService;

	public static EmployeePayrollDBService getInstance() {
		if (dbService == null)
			dbService = new EmployeePayrollDBService();
		return dbService;
	}

	public List<EmployeePayrollData> readData() {
		String sql = "select * from employee_payroll;";
		List<EmployeePayrollData> employeePayrollList = new ArrayList<>();
		try (Connection connection = this.getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			employeePayrollList = this.getEmployeePayrollData(result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employeePayrollList;
	}

	private Connection getConnection() throws SQLException {
		String jdbcURL = "jdbc:mysql://localhost:3306/payroll_service?useSSL=false";
		String userName = "root";
		String password = "password";
		Connection connection = null;
		System.out.println("Connecting to database:" + jdbcURL);
		connection = DriverManager.getConnection(jdbcURL, userName, password);
		System.out.println("Connection is success" + connection);
		return connection;
	}

	public int updateEmployeeData(String name, double salary) {
		return this.updateEmployeeDataUsingStatement(name, salary);
	}

	private int updateEmployeeDataUsingStatement(String name, double salary) {
		String sql = String.format("update employee_payroll set basic_pay= %.2f where name = '%s';", salary, name);
		try (Connection connection = this.getConnection()) {
			Statement statement = connection.createStatement();
			return statement.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int updateEmployeeDataUsingPreparedStatement(String name, double salary) {
		return this.updateEmployeeDataUsingPrepareStatement(name, salary);
	}

	private int updateEmployeeDataUsingPrepareStatement(String name, double salary) {
		String sql = String.format("update employee_payroll set basic_pay= %.2f where name = '%s';", salary, name);
		try (Connection connection = this.getConnection()) {
			PreparedStatement statment = connection.prepareStatement(sql);
			return statment.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public List<EmployeePayrollData> getEmployeePayrollData(String name) {
		List<EmployeePayrollData> employeePayrollData = null;
		if (this.preparedStatement == null)
			this.preparedStatementForEmployeeData();
		try {
			preparedStatement.setString(1, name);
			ResultSet resultSet = preparedStatement.executeQuery();
			employeePayrollData = this.getEmployeePayrollData(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employeePayrollData;
	}

	private List<EmployeePayrollData> getEmployeePayrollData(ResultSet result) {
		List<EmployeePayrollData> employeePayrollList = new ArrayList<>();
		try {
			while (result.next()) {
				int id = result.getInt("id");
				String name = result.getString("name");
				double salary = result.getDouble("basic_pay");
				LocalDate startDate = result.getDate("start").toLocalDate();
				employeePayrollList.add(new EmployeePayrollData(id, name, salary, startDate));
			}
			System.out.println(employeePayrollList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employeePayrollList;
	}

	private void preparedStatementForEmployeeData() {
		try {
			Connection con = this.getConnection();
			String sql = "select * from employee_payroll where name = ?";
			preparedStatement = con.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
