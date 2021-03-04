package com.bank.ui;

import java.sql.SQLException;
import java.time.LocalDate;
import org.apache.log4j.Logger;

import com.bank.exceptions.UserNotFoundException;
import com.bank.model.Account;
import com.bank.services.AccountService;
import com.bank.services.UserService;

public class CustomerMenu implements Menu {

	private static Logger log = Logger.getLogger(CustomerMenu.class);

	public UserService userService;
	public AccountService accountService;

	public CustomerMenu() {
		this.userService = new UserService();
		this.accountService = new AccountService();
	}

	public void display() {
		int choice = 0;
		int custId;
		int accNum;
		int secondAccNum;
		double amount;

		do {
			System.out.println("=== Customer MENU ===");
			System.out.println("Please select an option below: ");
			System.out.println("1.) Back");
			System.out.println("2.) Apply for a new bank account with a starting balance");
			System.out.println("3.) View the balance of a specific account");
			System.out.println("4.) Make a deposit to the specific account");
			System.out.println("5.) Make a withdrawal to the specific account");
			System.out.println("6.) Transfer the amount to an other account ");
			System.out.println("7.) Accept a money transfer from another account ");

			try {
				choice = Integer.parseInt(Menu.sc.nextLine());
			} catch (NumberFormatException e) {
			}

			switch (choice) {
			case 1:
				break;
			case 2:
				// Apply for new account
				custId = getCustomerIdInput();

				System.out.println("Enter the starting balance");
				amount = getAmountInput();

				LocalDate date = LocalDate.now();

				Account accountRequest = new Account(custId, amount, "Checking", date, "Pending", custId);
				try {
					int account = accountService.insertAccount(custId, accountRequest);
					System.out.println("Request with succes! "+ account);
					System.out.println("\n");
					System.out.println("Click enter to continue");
					Menu.sc.nextLine();
				log.info(UserLoginMenu.currentlyLoggedInUser.getUsername()+" Request a new account");
				} catch (SQLException | UserNotFoundException e) {
					log.error(UserLoginMenu.currentlyLoggedInUser.getUsername()+" tried to request a new account");
					System.out.println(e.getClass().getSimpleName() + " " + e.getMessage());
				}
				break;
			case 3:
				// View the balance of a specific account");
				custId = getCustomerIdInput();
				System.out.println("Enter Account Number: ");
				accNum = getAccountIdInput();
				try {
					Account account = accountService.getAccountByCustomerId(custId, accNum);
					System.out.println("Balance : " + account.getBalance());
					log.info(UserLoginMenu.currentlyLoggedInUser.getUsername()+" View the balance of a specific account");
					System.out.println("\n");
					System.out.println("Click enter to continue");
					Menu.sc.nextLine();
				} catch (SQLException | UserNotFoundException e) {
					log.error(UserLoginMenu.currentlyLoggedInUser.getUsername()+" Tried View the balance of a specific account");
					System.out.println(e.getClass().getSimpleName() + " " + e.getMessage());
				}

				break;
			case 4:
				// Deposit
				custId = getCustomerIdInput();
				System.out.println("Enter Account Number : ");
				accNum = getAccountIdInput();
				System.out.println("Enter Amount for deposit: ");
				amount = getAmountInput();
				try {
					Account account = accountService.deposit(custId, accNum, amount);
					System.out.println(account);
					log.info(UserLoginMenu.currentlyLoggedInUser.getUsername()+" did a doposit for specific account");
					System.out.println("\n");
					System.out.println("Click enter to continue");
					Menu.sc.nextLine();
				} catch (SQLException | UserNotFoundException e) {
					log.error(UserLoginMenu.currentlyLoggedInUser.getUsername()+" tried a doposit for specific account");
					System.out.println(e.getClass().getSimpleName() + " " + e.getMessage());
				}

				break;

			case 5:
				// Withdrawal
				custId = getCustomerIdInput();
				System.out.println("Enter Account Number : ");
				accNum = getAccountIdInput();
				System.out.println("Enter Amount for withdrawal: ");
				amount = getAmountInput();
				try {
					Account account = accountService.withDrawal(custId, accNum, amount);
					System.out.println(account);
					log.info(UserLoginMenu.currentlyLoggedInUser.getUsername()+" did a withdrawal for specific account");
					System.out.println("\n");
					System.out.println("Click enter to continue");
					Menu.sc.nextLine();
				} catch (SQLException | UserNotFoundException e) {
					log.error(UserLoginMenu.currentlyLoggedInUser.getUsername()+" did a withdrawal for specific account");
					System.out.println(e.getClass().getSimpleName() + " " + e.getMessage());
				}

				break;
			case 6:
				// Transfer the amount to an other account
				custId = getCustomerIdInput();
				System.out.println("From Account Number : ");
				accNum = getAccountIdInput();
				System.out.println("To Account Number : ");
				secondAccNum = getAccountIdInput();
				System.out.println("Enter Amount for the tranfer: ");
				amount = getAmountInput();

				try {

					if (accountService.transferAmount(custId, accNum, secondAccNum, amount)) {
						System.out.println("Tranfer amount  with succes");
						log.info(UserLoginMenu.currentlyLoggedInUser.getUsername()+" did Tranfer amount  with succes");
						System.out.println("\n");
						System.out.println("Click enter to continue");
						Menu.sc.nextLine();
					}
				} catch (SQLException | UserNotFoundException e) {
					log.error(UserLoginMenu.currentlyLoggedInUser.getUsername()+" failed to Tranfer amount  with succes");
					System.out.println(e.getClass().getSimpleName() + " " + e.getMessage());
				}

				break;

			case 7:
				// Accept transfert
				System.out.print("Enter the Customer Id: ");
				custId = getCustomerIdInput();
				System.out.print("Enter the account Number: ");
				accNum = getAccountIdInput();
				try {
					accountService.getAcceptTransfer(custId, accNum);
					log.info(UserLoginMenu.currentlyLoggedInUser.getUsername()+" received Tranfer amount  with succes");
					System.out.println("\n");
					System.out.println("Click enter to continue");
					Menu.sc.nextLine();
				} catch (SQLException | UserNotFoundException e) {
					log.info(UserLoginMenu.currentlyLoggedInUser.getUsername()+" failed to receive Tranfer amount!");
					e.printStackTrace();
				} 
				
                  
				break;

			default:
				System.out.println("No valid choice entered, please try again");
				System.out.println("\n");
				System.out.println("Click enter to continue");
				Menu.sc.nextLine();
			}

		} while (choice != 1);
	}

	public int getCustomerIdInput() {
		int customerId = 0;
		System.out.println("Enter a Customer's ID : ");
		try {
			customerId = Integer.parseInt(Menu.sc.nextLine());
		} catch (Exception e) {
			e.getMessage();
		}

		return customerId;
	}

	public int getAccountIdInput() {
		int accNum = 0;
		try {
			accNum = Integer.parseInt(Menu.sc.nextLine());
		} catch (Exception e) {
		}
		return accNum;
	}

	public double getAmountInput() {
		double amount = 0;

		try {
			amount = Menu.sc.nextDouble();
		} catch (Exception e) {
		}
		return amount;
	}

}
