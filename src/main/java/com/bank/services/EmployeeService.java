package com.bank.services;

import java.sql.Connection;
import java.sql.SQLException;

import com.bank.DAO.UserDAO;
import com.bank.DAO.UserDAOImpl;
import com.bank.exceptions.UserNotFoundException;
import com.bank.model.Users;
import com.bank.util.ConnectionUtil;

public class EmployeeService {
	public UserDAO userDAO;
	
	public EmployeeService() {
		this.userDAO = new UserDAOImpl();
	}
	
	public Users getEmployeeVerification(String username, String password) throws SQLException, UserNotFoundException {
        Users employee;
		
		Connection con = ConnectionUtil.getConnection();
		employee = userDAO.getUserNamePassword(username, password, con);
		
		if (employee == null) {
			
			throw new UserNotFoundException("User with username : " + username + " was not found!");
		}
		
		con.close();
		return employee;
	}
   
	}
		
	

