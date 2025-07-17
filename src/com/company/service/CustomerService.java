package com.company.service;

import com.company.exception.InsufficientFundException;
import com.company.model.Customer;

public interface CustomerService {
	public boolean customerLogin(String email, String password);

	public double viewBalance();

	public void depositMoney(double amount);

	public void withdrawMoney(double amount) throws InsufficientFundException;

	public void transferMoney(long toAccount, double amount) throws InsufficientFundException;

	public void viewTransactionHistory();

	public void customerLogout();

	public Customer getCurrentCustomer();
}