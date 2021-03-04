package com.bank.DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import com.bank.model.Account;
import com.bank.model.FundsTransfer;

public interface AccountDAO {
    
	public ArrayList<Account> getAccountsByCustomerId(int Id, Connection con) throws SQLException;
	public Account  getAccountForCustomerId(int customerId, int accountNum, Connection con) throws SQLException;
	public int createAccount(int id, Account account, Connection con) throws SQLException;
	public boolean getAccountUpdate(Account account, Connection con)throws SQLException;
	public ArrayList<Account> getPendingAccounts(Connection con) throws  SQLException ;
	public int getAccountUpdateStatus(int id, String rep, Connection con) throws SQLException;
	public boolean insertFundsTransfer(int accountIdFrom, int accountIdTo, double amount,Connection con) throws SQLException;
	public boolean getAcceptTransfer(int accountId, Connection con ) throws SQLException;
	public FundsTransfer getTransferInfo(int accNum, Connection con) throws SQLException;
	public Account getAccountById(int secondAccNum, Connection con) throws SQLException;
}
