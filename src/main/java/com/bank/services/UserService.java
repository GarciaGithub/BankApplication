package com.bank.services;
import java.sql.Connection;
import java.sql.SQLException;

import com.bank.DAO.AccountDAO;
import com.bank.DAO.AccountDAOImpl;
import com.bank.DAO.UserDAO;
import com.bank.DAO.UserDAOImpl;
import com.bank.exceptions.UserNotFoundException;
import com.bank.model.Users;
import com.bank.util.ConnectionUtil;

public class UserService {

	public UserDAO userDAO; 
	public AccountDAO accountDAO;
	
	public UserService() {
		this.userDAO = new UserDAOImpl();
		this.accountDAO = new AccountDAOImpl();
	}
	public Users getUserVerification(String username, String password) throws UserNotFoundException, SQLException {
		Users users;
		
		Connection con = ConnectionUtil.getConnection();
		users = userDAO.getUserNamePassword(username, password, con);
		
		if (users == null) {
			throw new UserNotFoundException("User with username : " + username + " was not found!");
		}
		
		//List<Account> userPosts = accountDAO.getCustomerById(Id, con);
		
		//customer.setPosts(userPosts);
		
		con.close();
		return users;
	}

	
	public Users getCustomerById(int Id) throws UserNotFoundException, SQLException {
		Users customer;
		
		Connection con = ConnectionUtil.getConnection();
		customer = userDAO.getCustomerById(Id, con);
		
		if (customer == null) {
			throw new UserNotFoundException("User with username '" + Id + "' was not found!");
		}
		
		//List<Account> userPosts = accountDAO.getCustomerById(Id, con);
		
		//customer.setPosts(userPosts);
		
		con.close();
		return customer;
	}
	
}
