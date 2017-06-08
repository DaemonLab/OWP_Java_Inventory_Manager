package com.progclub.owp;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class HomeWindow extends JFrame implements ActionListener
{
	// This doesn't matter, placed here to avoid a warning in eclipse
	private static final long serialVersionUID = -5562972360891866009L;
	
	// Just some constants used and explained later
	private static final String VIEW_ACTION_CMD = "VIEW_EDIT_ACTION";
	private static final String ADD_ACTION_CMD = "ADD_ACTION";
	private static final String WITHDRAW_ACTION_CMD = "WITHDRAW_ACTION";

	/**
	 * Create the Window.
	 */
	public HomeWindow()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWeights = new double[]{0.2, 1, 0.2};
		gbl_contentPane.rowWeights = new double[]{0.5, 1, 1, 1, 0.5};
		contentPane.setLayout(gbl_contentPane);
		
		GridBagConstraints gbc_btnView = new GridBagConstraints();
		gbc_btnView.anchor = GridBagConstraints.CENTER;
		gbc_btnView.gridx = 1;
		gbc_btnView.gridy = 1;
		JButton btnView = new JButton("View/Edit Inventory");
		contentPane.add(btnView, gbc_btnView);
		
		GridBagConstraints gbc_btnAdd = new GridBagConstraints();
		gbc_btnAdd.anchor = GridBagConstraints.CENTER;
		gbc_btnAdd.gridx = 1;
		gbc_btnAdd.gridy = 2;
		JButton btnAdd = new JButton("Add Items");
		contentPane.add(btnAdd, gbc_btnAdd);
		
		GridBagConstraints gbc_btnPrint = new GridBagConstraints();
		gbc_btnPrint.anchor = GridBagConstraints.CENTER;
		gbc_btnPrint.gridx = 1;
		gbc_btnPrint.gridy = 3;
		JButton btnPrint = new JButton("Withdraw Items");
		contentPane.add(btnPrint, gbc_btnPrint);
		
		// Setting the action command will let us identify which button
		// was pressed in out actionPerformed function
		btnView.setActionCommand(VIEW_ACTION_CMD);
		btnAdd.setActionCommand(ADD_ACTION_CMD);
		btnPrint.setActionCommand(WITHDRAW_ACTION_CMD);
		
		// All of the buttons will call the same actionPerformed listener
		btnView.addActionListener(this);
		btnAdd.addActionListener(this);
		btnPrint.addActionListener(this);
	}
	
	// We need to override the following function for implementing ActionListener
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		// Get the action command to identify the button pressed
		String actionCommand = arg0.getActionCommand();
		switch(actionCommand)
		{
		case VIEW_ACTION_CMD:
			ViewEditScreen viewScreen = new ViewEditScreen();
			viewScreen.setVisible(true);
			break;
		case ADD_ACTION_CMD:
			AddItemsScreen addScreen = new AddItemsScreen();
			addScreen.setVisible(true);
			break;
		case WITHDRAW_ACTION_CMD:
			WithdrawItemsScreen withdrawScreen = new WithdrawItemsScreen();
			withdrawScreen.setVisible(true);
			break;
		}
	}
}
