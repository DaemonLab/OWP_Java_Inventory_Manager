package com.progclub.owp;

import java.math.RoundingMode;
import java.text.DecimalFormat;

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

	public static String formatDouble(double d)
	{
		// Uses a formatter to format the number to have max 2 decimal places
		// it rounds the numbers so 0.005 to 0.01 will be 0.01 (HALF_UP)
		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.HALF_UP);
		return df.format(d);
	}
}
