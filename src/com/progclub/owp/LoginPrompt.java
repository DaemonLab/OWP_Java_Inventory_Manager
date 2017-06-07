package com.progclub.owp;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridBagConstraints;

/*
 *  Our Login page which will direct the users to main window 
 *  It extends JFrame as it is derived from it since we need to show a window
 *  It implements ActionListener as we need to catch the click on Login button
 */
public class LoginPrompt extends JFrame implements ActionListener
{
	// This doesn't matter, placed here to avoid a warning in eclipse
	private static final long serialVersionUID = -4323982503807550496L;
	
	// Need this fields in other functions hence declared outside constructor
	JTextField txtUsername, txtPassword;

	/**
	 * Create the frame in the constructor.
	 */
	public LoginPrompt()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		// When the cross is clicked, exit application
		// Set the top left corner of window on desktop and the width,height of the window
		// (0,0) is the top left corner of desktop and bottom right corner (maxx, maxy) 
		setBounds(100, 100, 450, 300);
		setTitle("Login");	// Set the text in the title bar
		setResizable(false);	// Don't let users resize the window

		// Create the main panel where all the other widgets will be placed
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));	// Set 5 pixel border on each side
		setContentPane(contentPane);
		// There are many different types of layout in Java, we are using
		// GridBagLayout as it provides precise control on placing elements in a grid
		// Feel free to google about other Layouts and learn about them
		GridBagLayout gbl_contentPane = new GridBagLayout();
		// Grid will have 5 columns (0-4). The 0th and 4th columns will be there
		// just for padding. And central (2nd) column will be small. The weights
		// don't predict strict sizes instead give a suggestion on what should be the sizes
		gbl_contentPane.columnWeights = new double[]{0.5, 1, 0.2, 1, 0.5};
		// The grid will have 4 rows (0-3), the 0th row will be for padding 
		gbl_contentPane.rowWeights = new double[]{0.5, 1, 1, 1};
		contentPane.setLayout(gbl_contentPane);
		
		GridBagConstraints gbc_lblUsername = new GridBagConstraints();
		gbc_lblUsername.anchor = GridBagConstraints.EAST;	// EAST means the element will be right aligned in the grid cell
		gbc_lblUsername.gridx = 1;	// 2nd column
		gbc_lblUsername.gridy = 1;	// 2nd row
		JLabel lblUsername = new JLabel("Username");	// A label
		contentPane.add(lblUsername, gbc_lblUsername);	// Add the label to the grid at the given position
		
		GridBagConstraints gbc_txtUsername = new GridBagConstraints();
		gbc_txtUsername.anchor = GridBagConstraints.WEST;	// WEST means the element will be left aligned in the grid cell
		gbc_txtUsername.gridx = 3;
		gbc_txtUsername.gridy = 1;
		txtUsername = new JTextField();	// An editable text field
		txtUsername.setColumns(10);			// Increase this to increase size of the text field
		contentPane.add(txtUsername, gbc_txtUsername);
		
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.anchor = GridBagConstraints.EAST;
		gbc_lblPassword.gridx = 1;
		gbc_lblPassword.gridy = 2;
		JLabel lblPassword = new JLabel("Password");
		contentPane.add(lblPassword, gbc_lblPassword);
		
		GridBagConstraints gbc_txtPassword = new GridBagConstraints();
		gbc_txtPassword.anchor = GridBagConstraints.WEST;
		gbc_txtPassword.gridx = 3;
		gbc_txtPassword.gridy = 2;
		txtPassword = new JTextField();
		txtPassword.setColumns(10);
		contentPane.add(txtPassword, gbc_txtPassword);
		
		GridBagConstraints gbc_btnLogin = new GridBagConstraints();
		gbc_btnLogin.anchor = GridBagConstraints.WEST;
		gbc_btnLogin.gridx = 2;
		gbc_btnLogin.gridy = 3;
		JButton btnLogin = new JButton("Login");
		contentPane.add(btnLogin, gbc_btnLogin);
		
		// Set this class as action listener for the button
		// so that proper function is called when it is clicked
		btnLogin.addActionListener(this);
	}
	
	// We need to override the following function for implementing ActionListener
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		// Login Button Clicked. For now, we will just show messages
		// Later on we will redirect the user to another window
		if (DatabaseHelper.getConnection(txtUsername.getText(), txtPassword.getText()))
			InventoryManager.loginSuccessful(this);
		else
			JOptionPane.showMessageDialog(null, "Login Failed! Check your credentials!!");
	}
}
