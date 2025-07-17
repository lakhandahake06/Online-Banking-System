# Online-Banking-System
An Online Banking System developed using core java and MySQL. The system allows administrators to manage customer accounts and enables customers to perform basic banking  operations such as balance inquiry, money transfer, deposits, and withdrawals.

This project consists of two main modules :
1. Admin Module :
1.  Admin Login
2.  Add Customer Account
3. Update Customer Account
4. Remove Customer Account
5. View Customer Account
6. View All Customer Accounts
7.  Logout

2. Customer Module :
1 Customer Login
2. View Balance
3. Deposit Money
4. Withdraw Money
5. Transfer Money
6. View Transaction History
7.  Logout

Technologies Used :
- Core Java (OOP'S Concept)
- MySQL (Database Management)
- JDBC (for Java-Database connectivity)

Database Schema
- Admin Table - Stores admin credentials.
- Customer Table - Stores customer account details.
- Transaction Table - Stores transaction records (deposits, withdrawals, transfers).

MySQL Query :
-- Create the bankwise database for managing banking records and operations.
create database bankwise;
use bankwise;

-- Create the admin table to store admin user credentials and information.
create table admin (
    id int auto_increment primary key,
    username varchar(255) not null unique,
    password varchar(255) not null
);

-- Insert admin user details into the admin table for authentication.
insert into admin (username, password) values ("Admin", "123");

-- Retrieve administrator credentials and details from the admin table.
select * from admin;

-- Create the customer table to store customer information including personal details and login credentials.
create table customer (
    id int auto_increment primary key,
    name varchar(255) not null,
    age int not null,
    phone varchar(10) not null,
    address varchar(255),
    email varchar(100) unique not null,
    password varchar(100) not null,
    accountNumber bigint unique not null,
    balance double default 0.0
);

-- Retrieve all customer records from the customer table.
select * from customer;
-- Create table transaction to store transaction history
create table transaction (
    id int auto_increment primary key,
    accountNumber bigint not null,
     amount double not null,
       type varchar(50) not null,
    date timestamp default current_timestamp,
    foreign key (accountNumber) references customer(accountNumber)
);

-- Retrieve all transaction records from the transaction table.
select * from transaction;
