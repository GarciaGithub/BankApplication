package com.bank.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.bank.model.Users;

public class UserDAOImpl implements UserDAO {
	@Override
	public Users getUserNamePassword(String username, String password, Connection con) throws SQLException {
		Users user1= null;
		String sql = "SELECT * FROM sql_bank.users WHERE username = ? AND userpassword =?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1,username );
		pstmt.setString(2, password);
        
		ResultSet rs = pstmt.executeQuery();
		
		if (rs.next()) {
			int id = rs.getInt(("id"));
			String firstName = rs.getString("firstname");
			String lastName = rs.getString("lastname");
			String usertype = rs.getString("usertype");
			
			user1 = new Users(id,firstName,lastName, usertype);
		}   
		
	    return user1;	
	}

	@Override
	public Users getCustomerById(int id, Connection con) throws SQLException {
		Users customer = null;
		
		String sql = "SELECT * FROM sql_bank.users WHERE id = ? and usertype=? ";
		PreparedStatement pstmt = con.prepareStatement(sql);
		
		pstmt.setInt(1, id);
		pstmt.setString(2, "Customer");
		ResultSet rs = pstmt.executeQuery();
		
		if (rs.next()) {
			int custid = rs.getInt(("id"));
			String firstName = rs.getString("firstname");
			String lastName = rs.getString("lastname");
			String usertype = rs.getString("usertype");
			
			customer = new Users(custid,firstName,lastName, usertype);
		}   
		
		return customer;
	}
	

	@Override
	public Users createCustomer(Users customer, Connection con ) throws SQLException{
		Users customer1=null;
		
		String sql = "INSERT INTO sql_bank.users (id,firstname,lastname,username,userpassword,usertype) VALUES (?, ?,?,?,?,?)";
		PreparedStatement pstmt = con.prepareStatement(sql);
		
		pstmt.setInt(1, customer.getId());
		pstmt.setString(2, customer.getFirstName());
		pstmt.setString(3, customer.getLastName());
		pstmt.setString(4, customer.getUsername());
		pstmt.setString(5, customer.getPassword());
		pstmt.setString(6, customer.getType());
		
		ResultSet rs = pstmt.executeQuery();
		
		if (rs.next()) {
			int custid = rs.getInt(("id"));
			String firstName = rs.getString("firstname");
			String lastName = rs.getString("lastname");
			String userName = rs.getString("username");
			String passWord = rs.getString("userpassword");
			String userType = rs.getString("usertype");
			
					
			customer1 = new Users(custid,firstName,lastName,userName, passWord, userType);
		}
		
		return customer1;
	}


}
