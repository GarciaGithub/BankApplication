package com.bank.ui;

import java.sql.SQLException;

import com.bank.exceptions.UserNotFoundException;

import com.bank.model.Users;
import com.bank.services.UserService;
import org.apache.log4j.Logger;



public class UserLoginMenu implements Menu{
   private static Logger log = Logger.getLogger(UserLoginMenu.class);
	
	public static Users currentlyLoggedInUser;
	public UserService userService;
	public UserLoginMenu() {
		this.userService = new UserService();
	}
	public void display() {
		
		System.out.println("User LOGIN MENU");
			System.out.println("============================");
			System.out.println("============================");
			System.out.println("Enter User Name: ");
			String username = Menu.sc.nextLine();
			System.out.println("Enter PassWord: ");
			String password = Menu.sc.nextLine();
			try {
				Users users = userService.getUserVerification(username, password);
				System.out.println("======================================================");
				System.out.println("Connection with success, User: " + users.getFirstName());
				System.out.println("======================================================");
				System.out.println("\n");
				currentlyLoggedInUser= new Users(username,password);
				if (users.getType().equals("Customer")) {
					
					Menu customerMenu = new CustomerMenu();
					log.info(currentlyLoggedInUser.getFirstName()+"login with succes");
					customerMenu.display();
				}else if (users.getType().equals("Employee")) {
				Menu employeeMenu = new EmployeeMenu();
				//log.info("User login with succes : " +  UserLoginMenu.currentlyLoggedInUser.getUsername());
				employeeMenu.display();}
				} catch (SQLException | UserNotFoundException e) {
				log.error(currentlyLoggedInUser.getUsername()+"tried to loging");
				System.out.println(e.getClass().getSimpleName() + " " + e.getMessage());
			}
			
			
	
	}
}
