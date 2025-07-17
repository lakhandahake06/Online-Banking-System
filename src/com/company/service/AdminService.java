package com.company.service;

import java.util.List;

import com.company.model.Customer;

public interface AdminService {
	public boolean adminLogin(String username, String password);

	public void addCustomerAccount(String name, int age, String phone, String address, String email, String password,
			double balance);

	public void updateCustomerAccount(String name, int age, String phone, String address, String email,
			String accountNumber);

	public void removeCustomerAccount(long accountNumber);

	public Customer viewCustomerAccount(long accountNumber);

	public List<Customer> viewAllCustomerAccounts();

	public void adminLogout();
}