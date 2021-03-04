package com.bank.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import com.bank.model.Account;
import com.bank.model.FundsTransfer;

public class AccountDAOImpl implements AccountDAO {

	@Override
	public ArrayList<Account> getAccountsByCustomerId(int id, Connection con) throws SQLException {
		String status = "Active";
		ArrayList<Account> accountList = new ArrayList<Account>();
		String sql = "SELECT * FROM sql_bank.account WHERE status =? AND customer_id=? ";
		PreparedStatement pstmt = con.prepareStatement(sql);
		
		pstmt.setString(1, status);
		pstmt.setInt(2, id);

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {

			int accNum = rs.getInt("acc_num");
			double bal = rs.getDouble("balance");
			String accType = rs.getString("acctype");
			LocalDate date = rs.getDate("accdate").toLocalDate();
			status = rs.getString("status");
			int customerId = rs.getInt("customer_id");

			Account account = new Account(accNum, bal, accType, date, status, customerId);

			accountList.add(account);
		}

		return accountList;
	}

	@Override
	public boolean getAccountUpdate(Account account, Connection con) throws SQLException {
		boolean on = false;
		String sql = "UPDATE sql_bank.account SET balance=? WHERE acc_num =?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		
		pstmt.setDouble(1, account.getBalance());
		pstmt.setInt(2, account.getNum());

		int count = pstmt.executeUpdate();

		if (count != 1) {
			throw new SQLException("Did not successfully update account " + account.getNum());
		}
		on = true;

		return on;
	}

	@Override
	public Account getAccountForCustomerId(int custId, int accountNum, Connection con) throws SQLException {

		String sql = "SELECT * FROM sql_bank.account WHERE acc_num=? AND customer_id=? ";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, accountNum);
		pstmt.setInt(2, custId);

		ResultSet rs = pstmt.executeQuery();
		Account account = null;
		if (rs.next()) {

			int accNum = rs.getInt("acc_num");
			double bal = rs.getDouble("balance");
			String accType = rs.getString("acctype");
			LocalDate date = rs.getDate("accdate").toLocalDate();
			String status = rs.getString("status");
			int customerId = rs.getInt("customer_id");

			 account = new Account(accNum, bal, accType, date, status, customerId);
		}

		return account;
	}

	@Override
	public int createAccount(int custId, Account account, Connection con) throws SQLException {
		String sql = "INSERT INTO sql_bank.account (balance,acctype,accdate,status,customer_id ) VALUES (?, ?,?,?,?)";
		PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		pstmt.setDouble(1, account.getBalance());
		pstmt.setString(2, account.getAccountType());
		pstmt.setDate(3, Date.valueOf(account.getAccdate()));
		pstmt.setString(4, account.getStatus());
		pstmt.setInt(5, custId);

		int count = pstmt.executeUpdate();

		if (count != 1) {
			throw new SQLException("Did not successfully demand " + account);
		}

		// Retrieve auto generated ID, and set it on our Post object
		ResultSet rs = pstmt.getGeneratedKeys();
		if (rs.next()) {
			int num = rs.getInt(1);
			account.setNum(num);
		}
		return account.getNum();
	}


	@Override
	public ArrayList<Account> getPendingAccounts(Connection con) throws SQLException {
		ArrayList<Account> accountList = new ArrayList<Account>();
		String sql = "SELECT * FROM sql_bank.account WHERE status =? ";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, "Pending");

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {

			int accNum = rs.getInt("acc_num");
			double bal = rs.getDouble("balance");
			String accType = rs.getString("acctype");
			int customerId = rs.getInt("customer_id");

			Account account = new Account(accNum, bal, accType, customerId);

			accountList.add(account);
		}
		if (accountList.isEmpty()) {
			throw new SQLException("Did not successfully update account ");
		}
		return accountList;

	}

	@Override
	public int getAccountUpdateStatus(int id, String rep, Connection con) throws SQLException {

		String sql = "UPDATE sql_bank.account SET status=? WHERE acc_num =?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, rep);
		pstmt.setInt(2, id);

		int count = pstmt.executeUpdate();

		if (count != 1) {
			throw new SQLException("Did not successfully update account ");
		}

		return count;
	}

	@Override
	public boolean insertFundsTransfer(int accountIdFrom, int accountIdTo, double amount, Connection con)
			throws SQLException {
		Boolean on = false;
		LocalDate dates = LocalDate.now();
		String status = "Pending";
		
		String sql = "INSERT INTO sql_bank.fundstransfer (Tdate,amount,status,accountfrom,accountto) VALUES(?,?,?,?,?)";
		PreparedStatement pstmt = con.prepareStatement(sql);

		pstmt.setDate(1, Date.valueOf(dates));
		pstmt.setDouble(2, amount);
		pstmt.setString(3, status);
		pstmt.setInt(4, accountIdFrom);
		pstmt.setInt(5, accountIdTo);

		int count = pstmt.executeUpdate();

		if (count != 1) {
			throw new SQLException("Did not successfully update transfer! ");
		}
		on = true;
		return on;
	}

	@Override
	public boolean getAcceptTransfer(int accountId, Connection con) throws SQLException {
		boolean on = false;
		String resp = "Accept";
		String sql = "UPDATE sql_bank.fundstransfer SET status=? WHERE acc_num =?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		
		pstmt.setString(1, resp);
		pstmt.setInt(2, accountId);

		int count = pstmt.executeUpdate();

		if (count != 1) {
			throw new SQLException("Did not successfully update account");
		}
		on = true;

		return on;
	}

	@Override
	public FundsTransfer getTransferInfo(int accNum, Connection con) throws SQLException {
		String stat = "Pending";
		String sql = "SELECT * FROM sql_bank.fundstransfer WHERE accountto=? and status =? ";
		PreparedStatement pstmt = con.prepareStatement(sql);
		
		pstmt.setInt(1, accNum);
		pstmt.setString(2, stat);

		ResultSet rs = pstmt.executeQuery();

		FundsTransfer fundsTransfer = null;
		if (rs.next()) {
			LocalDate dates = rs.getDate("Tdate").toLocalDate();
			double amount = rs.getDouble("amount");
			String status = rs.getString("status");
			int accountFrom = rs.getInt("accountfrom");
			int accountTo = rs.getInt("accountto");

			fundsTransfer = new FundsTransfer(accountFrom, accountTo, dates, amount, status);
		}

		return fundsTransfer;

	}

	@Override
	public Account getAccountById(int secondAccNum, Connection con) throws SQLException {
		String status = "Active";
		Account account = null;
		
		String sql = "SELECT * FROM sql_bank.account WHERE status =? AND customer_id=? ";
		PreparedStatement pstmt = con.prepareStatement(sql);

		pstmt.setString(1, status);
		pstmt.setInt(2, secondAccNum);

		ResultSet rs = pstmt.executeQuery();

		if (rs.next()) {

			int accNum = rs.getInt("acc_num");
			double bal = rs.getDouble("balance");
			String accType = rs.getString("acctype");
			LocalDate date = rs.getDate("accdate").toLocalDate();
			status = rs.getString("status");
			int customerId = rs.getInt("customer_id");

		   account = new Account(accNum, bal, accType, date, status, customerId);
			
		}

		return account;
	}

}
