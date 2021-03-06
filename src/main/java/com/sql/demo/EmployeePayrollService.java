package com.sql.demo;

import java.time.LocalDate;
import java.util.*;

public class EmployeePayrollService {
	public enum IOService {
		CONSOLE_IO, FILE_IO, DB_IO, REST_IO
	}

	private static List<EmployeePayrollData> empList;
	private EmployeePayrollDBService employeePayrollDBService;

	public EmployeePayrollService() {
		employeePayrollDBService = EmployeePayrollDBService.getInstance();
	}

	public EmployeePayrollService(List<EmployeePayrollData> empList) {
		this.empList = new ArrayList<>(empList);
	}

	private void readEmployeePayrollData(Scanner s) {
		System.out.println("Enter ID");
		int id = s.nextInt();
		System.out.println("Enter Name");
		String name = s.next();
		System.out.println("Enter salary");
		Double salary = s.nextDouble();
		empList.add(new EmployeePayrollData(id, name, salary));
	}

	void writeEmployeePayrollData(IOService ioService) {
		if (ioService.equals(IOService.CONSOLE_IO))
			System.out.println("\nWriting Payroll to Console\n" + empList);
		else if (ioService.equals(IOService.FILE_IO))
			new EmployeePayrollFileIOService().writeData(empList);

	}

	public static void main(String[] args) {
		ArrayList<EmployeePayrollData> empList = new ArrayList<>();
		EmployeePayrollService empService = new EmployeePayrollService(empList);
		Scanner consoleInputReader = new Scanner(System.in);
		empService.readEmployeePayrollData(consoleInputReader);
		empService.writeEmployeePayrollData(IOService.CONSOLE_IO);

	}

	public static long countEntries() {
		
			return empList.size();
		
	}

	public void printData(IOService ioService) {
		if (ioService.equals(IOService.FILE_IO))
			new EmployeePayrollFileIOService().printData();
	}

	public List<EmployeePayrollData> readEmployeePayrollData(IOService dbIo) {
		if (dbIo.equals(IOService.DB_IO)) {
			this.empList = new EmployeePayrollDBService().readData();
		}
		return this.empList;
	}

	public void updateEmployeeSalary(String name, double salary) {
		int result = new EmployeePayrollDBService().updateEmployeeData(name, salary);
		if (result == 0)
			return;
		EmployeePayrollData employeePayrollData = this.getEmployeePayrollData(name);
		if (employeePayrollData != null)
			employeePayrollData.basic_pay = salary;
	}

	EmployeePayrollData getEmployeePayrollData(String name) {
		for (EmployeePayrollData data : empList) {
			if (data.name.equals(name)) {
				return data;
			}
		}
		return null;
	}

	public boolean checkEmployeePayrollInSyncWithDB(String name, double salary) {
		for (EmployeePayrollData data : empList) {
			if (data.name.equals(name)) {
				if (Double.compare(data.basic_pay, salary) == 0) {
					return true;
				}
			}
		}
		return false;
	}

	public void updateEmployeeSalaryUsingPrepareStatement(String name, double salary) {
		int result = employeePayrollDBService.updateEmployeeDataUsingPreparedStatement(name, salary);
		if (result == 0)
			return;
		EmployeePayrollData employeePayrollData = this.getEmployeePayrollData(name);
		if (employeePayrollData != null)
			employeePayrollData.basic_pay = salary;
	}

	public List<EmployeePayrollData> readEmployeeDetailsForDateRange(IOService dbIo, LocalDate startDate,
			LocalDate endDate) {
		if (dbIo.equals(IOService.DB_IO)) {
			return employeePayrollDBService.getEmployeeDetailsForGivenDateRange(startDate, endDate);
		}
		return null;
	}

	public Map<String, Double> readAverageSalaryByGender(IOService dbIo) {
		if (dbIo.equals(IOService.DB_IO)) {
			return employeePayrollDBService.getAverageSalaryByGender();
		}
		return null;
	}

	public void addEmployeeToPayroll(int id, String name, double salary, LocalDate startDate, String gender,
			String department) {
		EmployeePayrollData employeePayrollData = employeePayrollDBService.addEmployee(id, name, salary, startDate,
				gender, department);
		empList.add(employeePayrollData);
		System.out.println(empList);
	}

	public void addEmployeeToPayrollERDiagram(int id, String name, double salary, LocalDate startDate, String gender,
			String department, String phone, String address) {
		EmployeePayrollData employeePayrollData = employeePayrollDBService.addEmployeeToPayrollERDiagram(id, name,
				salary, startDate, gender, department, phone, address);
		empList.add(employeePayrollData);
	}
	
	public void removeEmployee(String name) {
		employeePayrollDBService.removeEmployee(name);
	}
	
	public void addEmployeeToPayrollWithoutThreads(List<EmployeePayrollData> employeePayrollList) {
		employeePayrollList.forEach(employeePayrollData -> {
			System.out.println("Employee being added : " + employeePayrollData.name);
			this.addEmployeeToPayroll(employeePayrollData.id,employeePayrollData.name, employeePayrollData.basic_pay, employeePayrollData.start, employeePayrollData.gender,employeePayrollData.department);
			System.out.println("Employee added : " + employeePayrollData.name);
		});
	}
	
	public void addEmployeeToPayrollWithThreads(List<EmployeePayrollData> employeePayrollList) {
		Map<Integer, Boolean> empAdditionStatus = new HashMap<Integer, Boolean>();
		employeePayrollList.forEach(employeePayrollData -> {
			Runnable task = () -> {
				empAdditionStatus.put(employeePayrollData.hashCode(), false);
				
				this.addEmployeeToPayroll(employeePayrollData.id,employeePayrollData.name, employeePayrollData.basic_pay, employeePayrollData.start, employeePayrollData.gender,employeePayrollData.department);
				empAdditionStatus.put(employeePayrollData.hashCode(), true);
			};
			Thread thread = new Thread(task, employeePayrollData.name);
			thread.start();
		});
		while(empAdditionStatus.containsValue(false)) {
			try {
				Thread.sleep(10);
			}
			catch(InterruptedException e) {}
		}
	}
	
	public void addEmployeesToPayrollWithThreads(List<EmployeePayrollData> employeePayrollDataList) {
		Map<Integer, Boolean> employeeAdditionStatus = new HashMap<Integer, Boolean>();
		employeePayrollDataList.forEach(employeePayrollData -> {
			Runnable task = () -> {
				employeeAdditionStatus.put(employeePayrollData.hashCode(), false);
				System.out.println("Employee Being Added: " + Thread.currentThread().getName());
				this.addEmployeeToPayroll(employeePayrollData.id, employeePayrollData.name, employeePayrollData.basic_pay,
						employeePayrollData.start, employeePayrollData.gender, employeePayrollData.department);
				employeeAdditionStatus.put(employeePayrollData.hashCode(), true);
				System.out.println("Employee Added: " + Thread.currentThread().getName());
			};
			Thread thread = new Thread(task, employeePayrollData.name);
			thread.start();
		});
		while (employeeAdditionStatus.containsValue(false)) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
		}
		System.out.println(employeePayrollDataList);
	}
	
	public void printEmployeeData() {

		System.out.println(empList);
	}

	public void addEmployeesToPayrollToDBWithERWithThreads(List<EmployeePayrollData>  employeePayrollDataList) {
		Map<Integer, Boolean> employeeAdditionStatus = new HashMap<Integer, Boolean>();
		 employeePayrollDataList.forEach(employeePayrollData -> {
			Runnable task = () -> {
				employeeAdditionStatus.put(employeePayrollData.hashCode(), false);
				System.out.println("Employee Being Added: " + Thread.currentThread().getName());
				this.addEmployeeToPayrollERDiagram(employeePayrollData.id, employeePayrollData.name, employeePayrollData.basic_pay,
						employeePayrollData.start, employeePayrollData.gender, employeePayrollData.department,employeePayrollData.phone,employeePayrollData.address);
				System.out.println("Employee Added: " + Thread.currentThread().getName());
				employeeAdditionStatus.put(employeePayrollData.hashCode(), true);
			};
			Thread thread = new Thread(task, employeePayrollData.name);
			thread.start();
		});
		while (employeeAdditionStatus.containsValue(false)) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
		}
		
	}
	

	//REST API
	
	public void addEmployeeToPayroll(EmployeePayrollData employeePayrollData, IOService ioService) {
		
		empList.add(employeePayrollData);
	}
	
	public void updateEmployeeSalary(String name, double salary, IOService ioService) {
		if (ioService.equals(IOService.DB_IO)) {
			int result = employeePayrollDBService.updateEmployeeData(name, salary);
			if (result == 0)
				return;
		}
		EmployeePayrollData employeePayrollData = this.getEmployeePayrollData(name);
		if (employeePayrollData != null)
			employeePayrollData.basic_pay = salary;
	}
	
	public void deleteEmployeePayroll(String name, IOService ioService) {
		if (ioService.equals(IOService.REST_IO)) {
			EmployeePayrollData employeePayrollData = this.getEmployeePayrollData(name);
			empList.remove(employeePayrollData);
		}
	}

}
