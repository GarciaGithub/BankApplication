package com.bank.ui;

public class MainMenu implements Menu {

	public void display() {
		System.out.println("Welcome to the application!");
		
		int choice = 0;
		
		do {
			System.out.println("===================== MAIN MENU =====================");
			System.out.println("|                                                    |");
			System.out.println("|           Please select an option below:           |");
			System.out.println("|             1.) Exit Application                   |");
			System.out.println("|             2.)  User login                        |");
			System.out.println("|             3.)  Sign Up                           |");
			System.out.println("|                                                    |");
			System.out.println("======================================================");
			
			try {
				choice = Integer.parseInt(Menu.sc.nextLine());
			} catch (NumberFormatException e) {
			}
			
			switch (choice) {
				case 1:
					break;
				case 2:
					
					Menu usersLogin= new UserLoginMenu();
					usersLogin.display();
					break;
				case 3:
					
					break;
				default:
					System.out.println("No valid choice entered, please try again");
			}
			
		} while(choice != 1);
		
	}

}
