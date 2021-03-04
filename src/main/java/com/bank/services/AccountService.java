package com.bank.services;

import java.util.ArrayList;

//import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

import com.bank.DAO.*;
import com.bank.exceptions.UserNotFoundException;
import com.bank.model.Account;
import com.bank.model.FundsTransfer;
import com.bank.model.Users;
import com.bank.util.ConnectionUtil;

public class AccountService {
	//private static Logger log = Logger.getLogger(AccountService.class);
	public AccountDAO accountDAO;
	public UserDAO userDAO;

	public AccountService() {
		this.accountDAO = new AccountDAOImpl();
		this.userDAO = new UserDAOImpl();
	}

	public Account deposit(int custId, int accountNum, double amount) throws SQLException, UserNotFoundException {
		Account account;
		
		try (Connection con = ConnectionUtil.getConnection()) {
			con.setAutoCommit(false); // By default, autocommit is on.

			account = accountDAO.getAccountForCustomerId(custId, accountNum, con);

			if (account == null) {
				throw new UserNotFoundException("Account does not exist!!!");
			}

			System.out.println("Previous balance: " + account.getBalance());

			if (amount <= 0) {
				throw new UserNotFoundException("Your amount is incorrect!!!");
			}
			// Make a deposit to the account object
			account.setDeposit(amount);
             boolean on = accountDAO.getAccountUpdate(account, con);
			// Update the account balance inside the database
			if (on==false) {
				account.setWithDrawal(amount);
				throw new UserNotFoundException("Deposit failed!!!");
			}
			System.out.println("Actuel balance" + account.getBalance());
			con.commit();
			
		}

		return account;
	}

	public Account withDrawal(int custId, int accountNum, double amount) throws SQLException, UserNotFoundException {
		Account account = null;
		boolean on;
		try (Connection con = ConnectionUtil.getConnection()) {
			con.setAutoCommit(false); // By default, autocommit is on.

			account = accountDAO.getAccountForCustomerId(custId, accountNum, con);

			if (account == null) {
				throw new UserNotFoundException("Account does not exist!!!");
			}

			System.out.println("Previous balance: " + account.getBalance());
			if (amount <= 0) {
				throw new UserNotFoundException("Your amount is incorrect!!!");
			}
			// Make a withdrawal to the account object

			account.setWithDrawal(amount);

			// Update the account balance inside the database

			on = accountDAO.getAccountUpdate(account, con);

			if (on == false) {
				/*log.error("Withdrawal transaction fail: "
						+ com.bank.ui.UserLoginMenu.currentlyLoggedInUser.getFirstName());*/
				throw new UserNotFoundException("Withdrawal transaction fail");
			}
			con.commit();

			System.out.println("Actuel balance 2" + account.getBalance());
		}

		return account;
	}

	public Account getAccountByCustomerId(int custId, int accountNum) throws SQLException, UserNotFoundException {

		try (Connection con = ConnectionUtil.getConnection()) {
			con.setAutoCommit(false); // By default, autocommit is on

			Account accountForCustomerId = null;
			accountForCustomerId = accountDAO.getAccountForCustomerId(custId, accountNum, con);
			if (accountForCustomerId == null) {
				throw new UserNotFoundException(
						"Account:  '" + accountNum + "For customer id:" + custId + "' have not account!");
			}

			return accountForCustomerId;
		}
	}

	public ArrayList<Account> getAccountsByCustomerId(int custId) throws SQLException, UserNotFoundException {

		try (Connection con = ConnectionUtil.getConnection()) {
			con.setAutoCommit(false); // By default, autocommit is on.

			Users customer = userDAO.getCustomerById(custId, con);

			if (customer == null) {
				throw new UserNotFoundException("Customer id:  '" + custId + "' was not found!");
			}

			ArrayList<Account> accountsByCustomerId = new ArrayList<Account>();
			accountsByCustomerId = accountDAO.getAccountsByCustomerId(custId, con);
			if (accountsByCustomerId == null) {
				throw new UserNotFoundException("Customer Id:  '" + custId + "' have not account!");
			}

			return accountsByCustomerId;
		}
	}

	public boolean transferAmount(int custId, int accNum, int secondAccNum, double amount)
			throws SQLException, UserNotFoundException {
		boolean on = false;
		try (Connection con = ConnectionUtil.getConnection()) {
			con.setAutoCommit(false); // By default, autocommit is on.
			if (amount <= 0) {
				throw new UserNotFoundException(
						"Transfer amount: " + amount + "is not allowed! Enter amount greater than zero");
			}

			// Account from
			Account accountFrom = accountDAO.getAccountForCustomerId(custId, accNum, con);
			if (accountFrom == null) {
				throw new UserNotFoundException("Account from: " + accNum + "was not found!");
			}
			// Account to
			Account accountTo = accountDAO.getAccountById(secondAccNum, con);
			if (accountTo == null) {
				throw new UserNotFoundException("Account to : " + accNum + "was not found!");
			}
             
             
			if (accountFrom.getBalance() < amount) {
				throw new UserNotFoundException("The account did not have sufficient funds to complete the transfer");
			}
			// transfer begin here

			accountFrom.setWithDrawal(amount);
			// Update the database
			boolean onAccountFrom=accountDAO.getAccountUpdate(accountFrom, con);
			boolean onAccountTo=accountDAO.insertFundsTransfer( accNum, secondAccNum, amount, con);
				
			if ( onAccountFrom == false || onAccountTo == false) {
				accountFrom.setDeposit(amount);
				throw new UserNotFoundException("Transfert amount was not performed! Transfer did not updated ");
			}

			con.commit();

			//log.info("Tranfer amount with succes: " + com.bank.ui.UserLoginMenu.currentlyLoggedInUser.getFirstName());
			// Transaction end here
			on = true;

		}
		return on;
	}

	public int insertAccount(int custId, Account account) throws SQLException, UserNotFoundException {

		try (Connection con = ConnectionUtil.getConnection()) {
			con.setAutoCommit(false); // By default, autocommit is on.

			Users customer = userDAO.getCustomerById(custId, con);

			if (customer == null) {
				throw new UserNotFoundException("Customer id:  '" + custId + "' was not found!");
			}
			// Enter Account informations

			int accountInsert = accountDAO.createAccount(custId, account, con);
			if (accountInsert == 0) {
				throw new UserNotFoundException("Transaction for new account was failed!!!");
			}
			con.commit();

			return accountInsert;
		}

	}

	public ArrayList<Account> getPendingAccount() throws SQLException, UserNotFoundException {
		ArrayList<Account> accounts;
		try (Connection con = ConnectionUtil.getConnection()) {
			con.setAutoCommit(false); // By default, autocommit is on.

			accounts = accountDAO.getPendingAccounts(con);

		}
		return accounts;

	}

	public int getAccountResponse(int id, String rep) throws SQLException, UserNotFoundException {

		try (Connection con = ConnectionUtil.getConnection()) {
			con.setAutoCommit(false); // By default, autocommit is on.

			int status = accountDAO.getAccountUpdateStatus(id, rep, con);

			if (status == 0) {
				throw new UserNotFoundException("Account does not exist!!!");
			}
			System.out.println(rep + "was performed");
			return status;
		}
	}

	public boolean getAcceptTransfer(int custId, int accNum) throws SQLException, UserNotFoundException {
		boolean on = false;
		try (Connection con = ConnectionUtil.getConnection()) {
			con.setAutoCommit(false); // By default, autocommit is on.

			Account accountTo = getAccountByCustomerId(custId, accNum);
			if (accountTo == null) {
				throw new UserNotFoundException("Account does not exist!!!");
			}
			FundsTransfer fundsTransfer = accountDAO.getTransferInfo(accNum, con);
			if (fundsTransfer == null) {
				throw new UserNotFoundException("Transfert does not exist on this account: " + accNum);
			}

			accountTo.setDeposit(fundsTransfer.getAmount());
			on = accountDAO.getAccountUpdate(accountTo, con);
			if (on == false) {
				accountTo.setWithDrawal(fundsTransfer.getAmount());
				throw new UserNotFoundException("Transfer transaction failed! account: " + accNum);
			}
			con.commit();
			System.out.println("Transaction with succes! ");
			on = true;
		} catch (Exception e) {
			throw new UserNotFoundException("connection failed! account: " + accNum);
		}
		return on;
	}
}
