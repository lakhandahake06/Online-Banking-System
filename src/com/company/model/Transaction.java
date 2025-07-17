package com.company.model;

import java.sql.Timestamp;

public class Transaction {
	private int id;
	private long accountNumber;
	private double amount;
	private String type;
	private Timestamp date;

	public Transaction() {
		super();
	}

	public Transaction(int id, long accountNumber, double amount, String type, Timestamp date) {
		this.id = id;
		this.accountNumber = accountNumber;
		this.amount = amount;
		this.type = type;
		this.date = date;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public long getAccountNumber() {
		return accountNumber;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getAmount() {
		return amount;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public Timestamp getDate() {
		return date;
	}

	@Override
	public String toString() {
		return "Transaction [id=" + id + ", accountNumber=" + accountNumber + ", amount=" + amount + ", type=" + type
				+ ", date=" + date + "]";
	}
}