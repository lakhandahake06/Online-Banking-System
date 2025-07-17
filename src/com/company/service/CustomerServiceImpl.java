package com.company.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.company.exception.InsufficientFundException;
import com.company.model.Customer;
import com.company.util.DatabaseConnection;

public class CustomerServiceImpl implements CustomerService {
	private Customer currentCustomer = null;

	@Override
	public boolean customerLogin(String email, String password) {
		String query = "select * from customer where email = ? and password = ?";
		try (Connection connection = DatabaseConnection.getConnection()) {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, email);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				currentCustomer = new Customer();
				currentCustomer.setId(rs.getInt("id"));
				currentCustomer.setName(rs.getString("name"));
				currentCustomer.setAge(rs.getInt("age"));
				currentCustomer.setPhone(rs.getString("phone"));
				currentCustomer.setAddress(rs.getString("address"));
				currentCustomer.setEmail(rs.getString("email"));
				currentCustomer.setPassword(rs.getString("password"));
				currentCustomer.setAccountNumber(rs.getLong("accountNumber"));
				currentCustomer.setBalance(rs.getDouble("balance"));
				System.out.println("Customer login successfully...");
				return true;
			} else {
				System.out.println("Invalid email or password...");
				return false;
			}
		} catch (SQLException e) {
			System.out.println("An exception occurred while processing the customer login request...");
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public double viewBalance() {
		if (currentCustomer == null) {
			System.out.println("Please login to view your account balance...");
			return 0;
		}
		String query = "select balance from customer where accountNumber = ?";
		try (Connection connection = DatabaseConnection.getConnection()) {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setLong(1, currentCustomer.getAccountNumber());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				double balance = rs.getDouble("balance");
				currentCustomer.setBalance(balance);
				System.out.println("Your current balance is : " + balance);
				return balance;
			} else {
				System.out.println("Customer Account not found...");
			}
		} catch (SQLException e) {
			System.out.println("An exception occurred while retrieving the balance...");
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public void depositMoney(double amount) {
		if (currentCustomer == null) {
			System.out.println("Please login to deposit money into your account...");
			return;
		}
		double newBalance = currentCustomer.getBalance() + amount;
		String updateQuery = "update customer set balance = ? where accountNumber = ?";
		String insertTransaction = "insert into transaction (accountNumber, amount, type) values (?, ?, 'Deposit')";
		try (Connection connection = DatabaseConnection.getConnection()) {
			connection.setAutoCommit(false);
			PreparedStatement psUpdate = connection.prepareStatement(updateQuery);
			psUpdate.setDouble(1, newBalance);
			psUpdate.setLong(2, currentCustomer.getAccountNumber());

			PreparedStatement psTransaction = connection.prepareStatement(insertTransaction);
			psTransaction.setLong(1, currentCustomer.getAccountNumber());
			psTransaction.setDouble(2, amount);
			int updateStatus = psUpdate.executeUpdate();
			int transactionStatus = psTransaction.executeUpdate();
			if (updateStatus > 0 && transactionStatus > 0) {
				connection.commit();
				currentCustomer.setBalance(newBalance);
				System.out.println("Amount deposited successfully...");
				System.out.println("Your current balance is : " + newBalance);
			} else {
				connection.rollback();
				System.out.println("Customer Account not found...");
			}
		} catch (SQLException e) {
			System.out.println("An exception occurred while depositing the amount into your account...");
			e.printStackTrace();
		}
	}

	@Override
	public void withdrawMoney(double amount) throws InsufficientFundException {
		if (currentCustomer == null) {
			System.out.println("Please login to withdraw money into your account...");
			return;
		}
		if (currentCustomer.getBalance() < amount) {
			throw new InsufficientFundException(
					"You have insufficient funds in your account to complete this withdrawal...");
		}
		double newBalance = currentCustomer.getBalance() - amount;
		String updateQuery = "update customer set balance = ? where accountNumber = ?";
		String insertTransaction = "insert into transaction (accountNumber, amount, type) values (?, ?, 'Withdraw')";
		try (Connection connection = DatabaseConnection.getConnection()) {
			connection.setAutoCommit(false);
			PreparedStatement psUpdate = connection.prepareStatement(updateQuery);
			psUpdate.setDouble(1, newBalance);
			psUpdate.setLong(2, currentCustomer.getAccountNumber());

			PreparedStatement psTransaction = connection.prepareStatement(insertTransaction);
			psTransaction.setLong(1, currentCustomer.getAccountNumber());
			psTransaction.setDouble(2, amount);

			int updateStatus = psUpdate.executeUpdate();
			int transactionStatus = psTransaction.executeUpdate();

			if (updateStatus > 0 && transactionStatus > 0) {
				connection.commit();
				currentCustomer.setBalance(newBalance);
				System.out.println("Amount withdrawn successfully...");
				System.out.println("Your current balance is : " + newBalance);
			} else {
				connection.rollback();
				System.out.println("Customer Account not found...");
			}
		} catch (SQLException e) {
			System.out.println("An exception occurred while withdrawing the amount from your account...");
			e.printStackTrace();
		}
	}

	@Override
	public void transferMoney(long toAccount, double amount) throws InsufficientFundException {
		if (currentCustomer == null) {
			System.out.println("Please login to transfer money from your account...");
			return;
		}
		if (currentCustomer.getBalance() < amount) {
			throw new InsufficientFundException(
					"You have insufficient funds in your account to complete the transfer money...");
		}
		String withdrawQuery = "update customer set balance = balance - ? where accountNumber = ?";
		String depositQuery = "update customer set balance = balance + ? where accountNumber = ?";
		String senderTransactionQuery = "insert into transaction (accountNumber, amount, type) values (?, ?, 'Transfer-Out')";
		String receiverTransactionQuery = "insert into transaction (accountNumber, amount, type) VALUES (?, ?, 'Transfer-In')";
		try (Connection connection = DatabaseConnection.getConnection()) {
			connection.setAutoCommit(false);

			PreparedStatement psWithdraw = connection.prepareStatement(withdrawQuery);
			psWithdraw.setDouble(1, amount);
			psWithdraw.setLong(2, currentCustomer.getAccountNumber());

			PreparedStatement psDeposit = connection.prepareStatement(depositQuery);
			psDeposit.setDouble(1, amount);
			psDeposit.setLong(2, toAccount);

			PreparedStatement psSenderTxn = connection.prepareStatement(senderTransactionQuery);
			psSenderTxn.setLong(1, currentCustomer.getAccountNumber());
			psSenderTxn.setDouble(2, amount);

			PreparedStatement psReceiverTxn = connection.prepareStatement(receiverTransactionQuery);
			psReceiverTxn.setLong(1, toAccount);
			psReceiverTxn.setDouble(2, amount);

			int withdrawStatus = psWithdraw.executeUpdate();
			int depositStatus = psDeposit.executeUpdate();
			int stStatus = psSenderTxn.executeUpdate();
			int rtStatus = psReceiverTxn.executeUpdate();

			if (withdrawStatus > 0 && depositStatus > 0 && stStatus > 0 && rtStatus > 0) {
				connection.commit();
				currentCustomer.setBalance(currentCustomer.getBalance() - amount);
				System.out.println("Amount transferred successfully...");
				System.out.println("Your current balance is: " + currentCustomer.getBalance());
			} else {
				connection.rollback();
				System.out.println("Please check the account number and try again...");
			}
		} catch (SQLException e) {
			System.out.println("An exception occurred while transferring the amount from your account...");
			e.printStackTrace();
		}
	}

	public void viewTransactionHistory() {
		if (currentCustomer == null) {
			System.out.println("Please login to view the transaction history of your account...");
			return;
		}
		String query = "select * from transaction where accountNumber = ? order by date desc";
		try (Connection connection = DatabaseConnection.getConnection()) {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setLong(1, currentCustomer.getAccountNumber());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				System.out.println();
				System.out.println("Id             : " + rs.getInt("id"));
				System.out.println("Account Number : " + rs.getLong("accountNumber"));
				System.out.println("Amount         : " + rs.getDouble("amount"));
				System.out.println("Type           : " + rs.getString("type"));
				System.out.println("Date           : " + rs.getTimestamp("date"));
				System.out.println();
			}
		} catch (SQLException e) {
			System.out.println("An exception occurred while retrieving the transaction history of your account...");
			e.printStackTrace();
		}
	}

	@Override
	public void customerLogout() {
		currentCustomer = null;
		System.out.println("Customer has been logged out successfully...");
	}

	@Override
	public Customer getCurrentCustomer() {
		return currentCustomer;
	}
}