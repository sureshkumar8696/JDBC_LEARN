package com.jdbc.student;

import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StudentDatabase {

	private static Connection connection = null;
	private static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {

		StudentDatabase studentDatabase = new StudentDatabase();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/jdbcdb";
			String username = "root";
			String password = "Suresht@k8696";
			connection = DriverManager.getConnection(url, username, password);
			System.out.println("Enter Choice");
			System.out.println("1.Insert record");
			
			System.out.println("2.Select record");
			System.out.println("3.Update record");
			System.out.println("4.Delete record");
			System.out.println("5.Transaction record");
			
			int choice = Integer.parseInt(scanner.nextLine());
			switch (choice) {
			case 1:
				studentDatabase.insertRecord();
				break;
			case 2:
				studentDatabase.selectRecord();
				break;
			case 3:
				studentDatabase.updateRecord();
			case 4:
				studentDatabase.deleteRecord();
			case 5:
				studentDatabase.transaction();
			default:
				break;

			}

		} catch (Exception e) {
			throw new RuntimeException("Something went wrong");
		}
	}

	private void insertRecord() throws SQLException {
		System.out.println("inside insert record()");
		String sql = "insert into student(name,percentage,address) values (?,?,?)";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		System.out.println("Enter Name: ");
		preparedStatement.setString(1, scanner.nextLine());

		System.out.println("Enter Precentage: ");
		preparedStatement.setDouble(2, Double.parseDouble(scanner.nextLine()));

		System.out.println("Enter Address: ");
		preparedStatement.setString(3, scanner.nextLine());

		int row = preparedStatement.executeUpdate();

		if (row > 0) {
			System.out.println("Record inserted successfully");
		}
	}
	
	public void selectRecord() throws SQLException{
		
		System.out.println("Enter roll number to find result");
		int number = Integer.parseInt(scanner.nextLine());
		String sql = "select * from student where roll_number = "+ number;
		Statement statement =  connection.createStatement();
		
		ResultSet result = statement.executeQuery(sql);
		
		if(result.next()) {
				int rollNumber = result.getInt("roll_number");
				String name = result.getString("name");
				double percentage = result.getDouble("percentage");
				String address = result.getString("address"); 
				System.out.println("Roll number is "+ rollNumber);
				System.out.println("percentage is "+ percentage);
				System.out.println("address is "+ address);
		}
		else 
		{
			System.out.println("No records found...");
		}
		
	}
	private void updateRecord() throws SQLException{
		
		System.out.println("Enter the roll number to update record");
		int roll =Integer.parseInt(scanner.nextLine());
		String sql = "select * from student where roll_number = " + roll;
		Statement statement =  connection.createStatement();
		
		ResultSet result = statement.executeQuery(sql);
		
		if(result.next()) {
			int rollNumber = result.getInt("roll_number");
			String name = result.getString("name");
			double percentage = result.getDouble("percentage");
			String address = result.getString("address");
			System.out.println("Roll number is "+ rollNumber);
			System.out.println("Name is "+ name);
			System.out.println("percentage is "+ percentage);
			System.out.println("address is "+ address);
			
			System.out.println("What do you want to update?");
			System.out.println("1. Name");
			System.out.println("2. Percenatge");
			System.out.println("3. Address");
			
			int choice = Integer.parseInt(scanner.nextLine());
			String sqlQuery = "update student set";
			switch(choice) {
		
			case 1:
			    System.out.println("Enter new Name =");
			    String newName = scanner.nextLine();
			    sqlQuery = sqlQuery + " name = ? where roll_number = " + rollNumber;
			    PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
			    preparedStatement.setString(1, newName);
			    int rows = preparedStatement.executeUpdate();
			    if (rows > 0) {
			        System.out.println("Record updated successfully.");
			    }
			    break;

			case 2:
				System.out.println("Enter new Percentage =");
			    double newPercenatge = scanner.nextDouble();
			    sqlQuery = sqlQuery + " percenatge = ? where roll_number = " + rollNumber;
			    PreparedStatement preparedStatement1 = connection.prepareStatement(sqlQuery);
			    preparedStatement1.setDouble(1, newPercenatge);
			    int rows1 = preparedStatement1.executeUpdate();
			    if (rows1 > 0) {
			        System.out.println("Record updated successfully.");
			    }
			    break;
			case 3:
				System.out.println("Enter new Address =");
			    String newAddress = scanner.nextLine();
			    sqlQuery = sqlQuery + " Address = ? where roll_number = " + rollNumber;
			    PreparedStatement preparedStatement2 = connection.prepareStatement(sqlQuery);
			    preparedStatement2.setString(1, newAddress);
			    int rows2 = preparedStatement2.executeUpdate();
			    if (rows2 > 0) {
			        System.out.println("Record updated successfully.");
			    }
			    break;
			}
		}else {
			System.out.println("Records not found...");
		}
	}
	public void deleteRecord() throws SQLException {
		System.out.println("Enter roll number to delete.");
		int rollNumber = Integer.parseInt(scanner.nextLine());
		String sql = "delete from student where roll_number = " + rollNumber;
		Statement statement = connection.createStatement();
		int rows = statement.executeUpdate(sql);
		
		if(rows>0) {
			System.out.println("Record is deleted successfully...");
		}else {
			System.out.println("Record not found...");
		}
	}
	
	public void transaction() throws SQLException {
		
		connection.setAutoCommit(false);
		
		String sql1 = "Insert into student(name ,percentage, address) values('Aakash',73.2,'Banglore');";
		String sql2 = "Insert into student(name ,percentage, address) values('Harnish',90.31,'Gujarat');;";
		
		PreparedStatement preparedStatement = connection.prepareStatement(sql1);
		int row1 = preparedStatement.executeUpdate();
		
		PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
		int row2 = preparedStatement2.executeUpdate();
		
		if(row1>0 && row2>0) {
			connection.commit();
		}else {
			connection.rollback();
		}
		
	}

}
