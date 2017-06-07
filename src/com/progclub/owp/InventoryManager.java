package com.progclub.owp;

public class InventoryManager
{
	public static void main(String[] args)
	{
		// Create a new Login prompt and display it
		LoginPrompt frame = new LoginPrompt();
		frame.setVisible(true);
	}

	public static void loginSuccessful(LoginPrompt lp)
	{
		// Show the Home Window
		HomeWindow hw = new HomeWindow();
		hw.setVisible(true);
		// Remove the login prompt
		lp.dispose();
	}
}
