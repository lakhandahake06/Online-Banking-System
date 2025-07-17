package com.company.model;

public class Customer {
	private int id;
	private String name;
	private int age;
	private String phone;
	private String address;
	private String email;
	private String password;
	private long accountNumber;
	private double balance;

	public Customer() {
		super();
	}

	public Customer(int id, String name, int age, String phone, String address, String email, String password,
			long accountNumber, double balance) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.phone = phone;
		this.address = address;
		this.email = email;
		this.password = password;
		this.accountNumber = accountNumber;
		this.balance = balance;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getAge() {
		return age;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhone() {
		return phone;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return address;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public long getAccountNumber() {
		return accountNumber;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public double getBalance() {
		return balance;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", age=" + age + ", phone=" + phone + ", address=" + address
				+ ", email=" + email + ", password=" + password + ", accountNumber=" + accountNumber + ", balance="
				+ balance + "]";
	}
}