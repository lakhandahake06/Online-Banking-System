package com.company.main;

import java.util.Scanner;

import com.company.service.AdminService;
import com.company.service.AdminServiceImpl;
import com.company.service.CustomerService;
import com.company.service.CustomerServiceImpl;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		AdminService adminService = new AdminServiceImpl();
		CustomerService customerService = new CustomerServiceImpl();
		while (true) {
			System.out.println("--------------------------------------------------");
			System.out.println("-------- WELCOME TO ONLINE BANKING SYSTEM --------");
			System.out.println("--------------------------------------------------");
			System.out.println("1. Admin Login Portal");
			System.out.println("2. Customer Login Portal");
			System.out.println("3. Exit");
			System.out.println("Choose your option : ");
			int choice = scanner.nextInt();
			scanner.nextLine();
			switch (choice) {
			case 1:
				System.out.println("--------------------------------------------------");
				System.out.println("--------------- Admin Login Portal ---------------");
				System.out.println("--------------------------------------------------");
				System.out.println("Enter your username : ");
				String adminUsername = scanner.nextLine();
				System.out.println("Enter your password : ");
				String adminPassword = scanner.nextLine();
				boolean admin = adminService.adminLogin(adminUsername, adminPassword);
				if (admin) {
					adminDashboard(scanner, adminService);
				}
				break;
			case 2:
				System.out.println("--------------------------------------------------");
				System.out.println("------------- Customer Login Portal --------------");
				System.out.println("--------------------------------------------------");
				System.out.println("Enter your email : ");
				String customerEmail = scanner.nextLine();
				System.out.println("Enter your password : ");
				String customerPassword = scanner.nextLine();
				boolean customer = customerService.customerLogin(customerEmail, customerPassword);
				if (customer) {
					long accountNumber = customerService.getCurrentCustomer().getAccountNumber();
					customerDashboard(scanner, customerService, accountNumber);
				}
				break;
			case 3:
				System.out.println("Thank you for using the online banking system...");
				scanner.close();
				System.exit(0);
				break;
			default:
				System.out.println("Invalid choice...");
			}
		}
	}

	public static void adminDashboard(Scanner scanner, AdminService adminService) {
		while (true) {
			System.out.println("--------------------------------------------------");
			System.out.println("----------------- Admin Dashboard ----------------");
			System.out.println("--------------------------------------------------");
			System.out.println("1. Add Customer Account");
			System.out.println("2. Update Customer Account");
			System.out.println("3. Remove Customer Account");
			System.out.println("4. View Customer Account");
			System.out.println("5. View All Customer Accounts");
			System.out.println("6. Logout");
			System.out.println("Choose your option : ");
			int choice = scanner.nextInt();
			scanner.nextLine();
			switch (choice) {
			case 1:
				System.out.println("--------------------------------------------------");
				System.out.println("-------------- Add Customer Account --------------");
				System.out.println("--------------------------------------------------");
				System.out.println("Enter customer name : ");
				String customerName = scanner.nextLine();
				System.out.println("Enter customer age : ");
				int customerAge = scanner.nextInt();
				scanner.nextLine();
				System.out.println("Enter customer phone : ");
				String customerPhone = scanner.nextLine();
				System.out.println("Enter customer address : ");
				String customerAddress = scanner.nextLine();
				System.out.println("Enter customer email : ");
				String customerEmail = scanner.nextLine();
				System.out.println("Enter customer password : ");
				String customerPassword = scanner.nextLine();
				System.out.println("Enter customer balance : ");
				Double customerBalance = scanner.nextDouble();
				adminService.addCustomerAccount(customerName, customerAge, customerPhone, customerAddress,
						customerEmail, customerPassword, customerBalance);
				break;
			case 2:
				System.out.println("--------------------------------------------------");
				System.out.println("------------- Update Customer Account ------------");
				System.out.println("--------------------------------------------------");
				System.out.println("Enter account number to update customer details : ");
				String accountNumber = scanner.nextLine();
				System.out.println("Enter to update customer name : ");
				String name = scanner.nextLine();
				System.out.println("Enter to update customer age : ");
				int age = scanner.nextInt();
				scanner.nextLine();
				System.out.println("Enter to update customer phone : ");
				String phone = scanner.nextLine();
				System.out.println("Enter to update customer address : ");
				String address = scanner.nextLine();
				System.out.println("Enter to update customer email : ");
				String email = scanner.nextLine();
				adminService.updateCustomerAccount(name, age, phone, address, email, accountNumber);
				break;
			case 3:
				System.out.println("--------------------------------------------------");
				System.out.println("------------- Remove Customer Account ------------");
				System.out.println("--------------------------------------------------");
				System.out.println("Enter account number to remove customer account : ");
				long accountNumber_01 = scanner.nextLong();
				adminService.removeCustomerAccount(accountNumber_01);
				break;
			case 4:
				System.out.println("--------------------------------------------------");
				System.out.println("------------- View Customer Account --------------");
				System.out.println("--------------------------------------------------");
				System.out.println("Enter account number to view customer account : ");
				long accountNumber_02 = scanner.nextLong();
				adminService.viewCustomerAccount(accountNumber_02);
				break;
			case 5:
				System.out.println("--------------------------------------------------");
				System.out.println("------------ View All Customer Accounts ----------");
				System.out.println("--------------------------------------------------");
				adminService.viewAllCustomerAccounts();
				break;
			case 6:
				adminService.adminLogout();
				return;
			default:
				System.out.println("Invalid choice...");
			}
		}
	}

	public static void customerDashboard(Scanner scanner, CustomerService customerService, long accountNumber) {
		while (true) {
			System.out.println("--------------------------------------------------");
			System.out.println("--------------- Customer Dashboard ---------------");
			System.out.println("--------------------------------------------------");
			System.out.println("1. View Balance");
			System.out.println("2. Deposit Money");
			System.out.println("3. Withdraw Money");
			System.out.println("4. Transfer Money");
			System.out.println("5. View Transaction History");
			System.out.println("6. Logout");
			System.out.println("Choose your option : ");
			int choice = scanner.nextInt();
			scanner.nextLine();
			switch (choice) {
			case 1:
				System.out.println("--------------------------------------------------");
				System.out.println("-------------- View Account Balance --------------");
				System.out.println("--------------------------------------------------");
				customerService.viewBalance();
				break;
			case 2:
				System.out.println("--------------------------------------------------");
				System.out.println("----------------- Deposit Money ------------------");
				System.out.println("--------------------------------------------------");
				System.out.println("Enter amount to deposit into your account : ");
				double depositAmount = scanner.nextDouble();
				scanner.nextLine();
				if (depositAmount <= 0) {
					System.out.println("Enter a valid amount greater than zero...");
				} else {
					customerService.depositMoney(depositAmount);
				}
				break;
			case 3:
				System.out.println("--------------------------------------------------");
				System.out.println("---------------- Withdraw Money ------------------");
				System.out.println("--------------------------------------------------");
				System.out.println("Enter amount to withdraw from your account : ");
				double withdrawAmount = scanner.nextDouble();
				scanner.nextLine();
				customerService.withdrawMoney(withdrawAmount);
				break;
			case 4:
				System.out.println("--------------------------------------------------");
				System.out.println("---------------- Transfer Money ------------------");
				System.out.println("--------------------------------------------------");
				System.out.println("Enter the recipient account number : ");
				long toAccount = scanner.nextLong();
				System.out.println("Enter the amount to transfer money : ");
				double transferAmount = scanner.nextDouble();
				scanner.nextLine();
				customerService.transferMoney(toAccount, transferAmount);
				break;
			case 5:
				System.out.println("--------------------------------------------------");
				System.out.println("------------ View Transaction History ------------");
				System.out.println("--------------------------------------------------");
				customerService.viewTransactionHistory();
				break;
			case 6:
				customerService.customerLogout();
				return;
			default:
				System.out.println("Invalid choice...");
			}
		}
	}
}