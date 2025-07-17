package com.company.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.company.exception.MinimumBalanceException;
import com.company.model.Customer;
import com.company.util.DatabaseConnection;

public class AdminServiceImpl implements AdminService {
	@Override
	public boolean adminLogin(String username, String password) {
		String query = "select * from admin where username = ? and password = ?";
		try (Connection connection = DatabaseConnection.getConnection()) {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				System.out.println("Admin login successfully...");
				return true;
			} else {
				System.out.println("Invalid username or password...");
				return false;
			}
		} catch (SQLException e) {
			System.out.println("An exception occurred while processing the admin login request...");
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void addCustomerAccount(String name, int age, String phone, String address, String email, String password,
			double balance) {
		if (balance < 10000) {
			throw new MinimumBalanceException("Initial balance must be at least 10,000...");
		}
		long accountNumber = createAccountNumber();
		String query = "insert into customer (name, age, phone, address, email, password, accountNumber, balance) values (?, ?, ?, ?, ?, ?, ?, ?)";
		try (Connection connection = DatabaseConnection.getConnection()) {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, name);
			ps.setInt(2, age);
			ps.setString(3, phone);
			ps.setString(4, address);
			ps.setString(5, email);
			ps.setString(6, password);
			ps.setLong(7, accountNumber);
			ps.setDouble(8, balance);
			int rows = ps.executeUpdate();
			if (rows > 0) {
				System.out.println("Customer account added successfully...");
				System.out.println("Customer Account Number : " + accountNumber);
			} else {
				System.out.println("Failed to add customer account...");
			}
		} catch (SQLException e) {
			System.out.println("An exception occurred while adding the customer account...");
			e.printStackTrace();
		}
	}

	@Override
	public void updateCustomerAccount(String name, int age, String phone, String address, String email,
			String accountNumber) {
		String query = "update customer set name = ?, age = ?, phone = ?, address = ?, email = ? where accountNumber = ?";
		try (Connection connection = DatabaseConnection.getConnection()) {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, name);
			ps.setInt(2, age);
			ps.setString(3, phone);
			ps.setString(4, address);
			ps.setString(5, email);
			ps.setString(6, accountNumber);
			int rows = ps.executeUpdate();
			if (rows > 0) {
				System.out.println("Customer account updated successfully...");
			} else {
				System.out.println("No customer found with the provided account number...");
			}
		} catch (SQLException e) {
			System.out.println("An exception occurred while updating the customer account...");
			e.printStackTrace();
		}
	}

	@Override
	public void removeCustomerAccount(long accountNumber) {
		String query = "delete from customer where accountNumber = ?";
		try (Connection connection = DatabaseConnection.getConnection()) {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setLong(1, accountNumber);
			int rows = ps.executeUpdate();
			if (rows > 0) {
				System.out.println("Customer account removed successfully...");
			} else {
				System.out.println("No customer found with the provided account number...");
			}
		} catch (SQLException e) {
			System.out.println("An exception occurred while removing the customer account...");
			e.printStackTrace();
		}
	}

	@Override
	public Customer viewCustomerAccount(long accountNumber) {
		Customer customer = null;
		String query = "select * from customer where accountNumber = ?";
		try (Connection connection = DatabaseConnection.getConnection()) {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setLong(1, accountNumber);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				customer = new Customer();
				customer.setId(rs.getInt("id"));
				customer.setName(rs.getString("name"));
				customer.setAge(rs.getInt("age"));
				customer.setPhone(rs.getString("phone"));
				customer.setAddress(rs.getString("address"));
				customer.setEmail(rs.getString("email"));
				customer.setAccountNumber(rs.getLong("accountNumber"));
				customer.setBalance(rs.getDouble("balance"));
				System.out.println("--------------------------------------------------");
				System.out.println("---------------- Customer Details ----------------");
				System.out.println("--------------------------------------------------");
				System.out.println("Account Number : " + customer.getAccountNumber());
				System.out.println("Name           : " + customer.getName());
				System.out.println("Age            : " + customer.getAge());
				System.out.println("Phone          : " + customer.getPhone());
				System.out.println("Address        : " + customer.getAddress());
				System.out.println("Email          : " + customer.getEmail());
				System.out.println("Balance        : " + customer.getBalance());
			} else {
				System.out.println("No customer found with the provided account number...");
			}
		} catch (SQLException e) {
			System.out.println("An exception occurred while retrieving the customer account...");
			e.printStackTrace();
		}
		return customer;
	}

	@Override
	public List<Customer> viewAllCustomerAccounts() {
		List<Customer> customers = new ArrayList<Customer>();
		String query = "select * from customer";
		try (Connection connection = DatabaseConnection.getConnection()) {
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Customer customer = new Customer();
				customer.setId(rs.getInt("id"));
				customer.setName(rs.getString("name"));
				customer.setAge(rs.getInt("age"));
				customer.setPhone(rs.getString("phone"));
				customer.setAddress(rs.getString("address"));
				customer.setEmail(rs.getString("email"));
				customer.setAccountNumber(rs.getLong("accountNumber"));
				customer.setBalance(rs.getDouble("balance"));
				customers.add(customer);
			}
			if (customers.isEmpty()) {
				System.out.println("No customer accounts found in the database...");
			} else {
				for (Customer customer : customers) {
					System.out.println();
					System.out.println("Account Number : " + customer.getAccountNumber());
					System.out.println("Name           : " + customer.getName());
					System.out.println("Age            : " + customer.getAge());
					System.out.println("Phone          : " + customer.getPhone());
					System.out.println("Address        : " + customer.getAddress());
					System.out.println("Email          : " + customer.getEmail());
					System.out.println("Balance        : " + customer.getBalance());
					System.out.println();
				}
			}
		} catch (SQLException e) {
			System.out.println("An exception occurred while retrieving all customer accounts...");
			e.printStackTrace();
		}
		return customers;
	}

	@Override
	public void adminLogout() {
		System.out.println("Admin has been logged out successfully...");
	}

	public static long createAccountNumber() {
		long accountNumber = 11001;
		String query = "select max(accountNumber) from customer";
		try (Connection connection = DatabaseConnection.getConnection()) {
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				long lastAccountNumber = rs.getLong(1);
				if (lastAccountNumber > 0) {
					accountNumber = lastAccountNumber + 1;
				}
			} else {
				System.out.println("Account number could not be generated...");
			}
		} catch (SQLException e) {
			System.out.println("An exception occurred while generating the account number...");
			e.printStackTrace();
		}
		return accountNumber;
	}
}