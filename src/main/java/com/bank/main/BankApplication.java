
package com.bank.main;

import com.bank.ui.MainMenu;
import com.bank.ui.Menu;

public class BankApplication {

	public static void main(String[] args) {
		
		Menu mainMenu = new MainMenu(); // shortcut for importing is ctrl + shift + o
		mainMenu.display();
		
		// This is the end of the application
		// So we can close our scanner
		Menu.sc.close();
		System.out.println("Application closing!");
		
	}

}
