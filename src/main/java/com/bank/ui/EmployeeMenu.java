package com.bank.ui;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import com.bank.exceptions.UserNotFoundException;
import com.bank.model.Account;
import com.bank.model.Transactions;
import com.bank.services.AccountService;
import com.bank.services.TransactionService;

public class EmployeeMenu implements Menu {
public AccountService accountService;
public TransactionService transactionService;
private static Logger log = Logger.getLogger(EmployeeMenu.class);
	
	public EmployeeMenu() {
		this.accountService = new AccountService();
		
	}

	public void display() {
		int choice = 0;
		int id;
		double amount;
		String rep;
				
		do {
			System.out.println("=== Employee MENU ===");
			System.out.println("Please select an option below: ");
			System.out.println("1.) Back");
			System.out.println("2.) View a customer's bank accounts");
			System.out.println("3.) Register for a customer's bank account");
			System.out.println("4.) Approve / Reject an account.");
			System.out.println("5.) View a log of all transactions");
			
			try {
				choice = Integer.parseInt(Menu.sc.nextLine());
			} catch (NumberFormatException e) {
			}
			
			switch (choice) {
				case 1:
					break;
				case 2:
					System.out.println("Enter a Customer's ID : ");
					id = getIdInput();
					try {
						ArrayList<Account> accounts = new ArrayList<Account>(accountService.getAccountsByCustomerId(id));
						System.out.println(accounts);
						log.info(UserLoginMenu.currentlyLoggedInUser.getUsername()+" consulted accounts for a customer id:"+ id);
						
					} catch (SQLException | UserNotFoundException e) {
						log.error(UserLoginMenu.currentlyLoggedInUser.getUsername()+" failed to consult accounts for a customer id:"+ id);
						System.out.println(e.getClass().getSimpleName() + " " + e.getMessage());
					}
					break;
				case 3:
					//Register for a customer's bank accounts
					System.out.println("Enter a Customer's ID : ");
					id = getIdInput();
					
					amount = getAmountInput();
					String type="Checking";
					LocalDate date = LocalDate.now();
					String status ="Actve";
					
					Account regAccount= new Account(amount,type,date,status);
					try {
					int newAccount=accountService.insertAccount(id, regAccount);
					System.out.println(newAccount);
					log.info(UserLoginMenu.currentlyLoggedInUser.getUsername()+" did register a new accounts for a customer id:"+ id);
					} catch (SQLException | UserNotFoundException e) {
						log.info(UserLoginMenu.currentlyLoggedInUser.getUsername()+"failed to register a new accounts for a customer id:"+ id);
						System.out.println(e.getClass().getSimpleName() + " " + e.getMessage());
					}
					break;
				case 4:
					//Approve / Reject an account.
					
					System.out.println("                     List of pending Account                            ");
					System.out.println("*********************************************************************");
				ArrayList<Account> accounts=null;
				try {
					accounts = accountService.getPendingAccount();
					System.out.println(accounts);
					log.info(UserLoginMenu.currentlyLoggedInUser.getUsername()+" consulted a list of accounts");
				} catch (SQLException | UserNotFoundException e) {
					log.error(UserLoginMenu.currentlyLoggedInUser.getUsername()+" failed to consult a list of accounts");
					e.printStackTrace();
				}
					
					System.out.println("Enter an account ID : ");
					id = getIdInput();
					rep = getResponseInput();
					try {
						int account =accountService.getAccountResponse(id,rep);
						System.out.println(account);
						
					} catch (SQLException | UserNotFoundException e) {
						System.out.println(e.getClass().getSimpleName() + " " + e.getMessage());
					}
					
					
					break;
					   
					
				case 5:
					    // View a log of all transactions
					break;
				default:
					System.out.println("No valid choice entered, please try again");
			}
			
		} while(choice != 1);
	}
	
	private String getResponseInput() {
		System.out.println("         Select Option      ");
		System.out.println("..................................");
		System.out.println("1) Approve");
		System.out.println("2) Reject");
		int resp= Menu.sc.nextInt();
		if(resp==1) {
			return "Approve";	
		}else if(resp==1) {
			return "Reject";	
		}else {
			return "Wrong value";
		}
		
		
		
	}

	private double getAmountInput() {
		double amounts;
		System.out.println("Enter Amount : ");
		
		amounts = Menu.sc.nextDouble();
		return amounts;
	}

	public int getIdInput() {
		int customerId=0;
		
		try {
		customerId = Integer.parseInt(Menu.sc.nextLine());
		}
		catch(Exception e){}
		return customerId;
	}

}
