package com.bank.DAO;

import java.sql.Connection;
import java.sql.SQLException;

import com.bank.model.Users;

public interface UserDAO {

	Users getCustomerById(int id, Connection con) throws SQLException;
	Users getUserNamePassword(String username, String password, Connection con) throws SQLException;
	Users createCustomer(Users customer, Connection con) throws SQLException;
}
