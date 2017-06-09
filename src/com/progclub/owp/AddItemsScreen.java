package com.progclub.owp;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

public class AddItemsScreen extends JFrame implements ActionListener
{
	// This doesn't matter, placed here to avoid a warning in eclipse
	private static final long serialVersionUID = 6844570596338448619L;

	// Need these later
	JTextField txtName;
	JSpinner spPrice, spQuantity;	// Used to select numbers

	/**
	 * Create the Frame.
	 */
	public AddItemsScreen() {
		// Do NOT exit the program when user clicks X button on title bar
		// Instead just remove this window
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setTitle("Add Item");
		
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWeights = new double[]{0.5, 1, 0.2, 1, 0.5};
		gbl_contentPane.rowWeights = new double[]{0.5, 1, 1, 1, 1, 0.5};
		contentPane.setLayout(gbl_contentPane);
		
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.anchor = GridBagConstraints.WEST;
		gbc_lblName.gridx = 1;
		gbc_lblName.gridy = 1;
		JLabel lblName = new JLabel("Name");
		contentPane.add(lblName, gbc_lblName);
		
		GridBagConstraints gbc_txtName = new GridBagConstraints();
		gbc_txtName.anchor = GridBagConstraints.WEST;
		gbc_txtName.gridx = 3;
		gbc_txtName.gridy = 1;
		txtName = new JTextField();
		txtName.setColumns(10);
		contentPane.add(txtName, gbc_txtName);

		GridBagConstraints gbc_lblPrice = new GridBagConstraints();
		gbc_lblPrice.anchor = GridBagConstraints.WEST;
		gbc_lblPrice.gridx = 1;
		gbc_lblPrice.gridy = 2;
		JLabel lblPrice = new JLabel("Price per piece");
		contentPane.add(lblPrice, gbc_lblPrice);
		
		GridBagConstraints gbc_spPrice = new GridBagConstraints();
		gbc_spPrice.anchor = GridBagConstraints.WEST;
		gbc_spPrice.gridx = 3;
		gbc_spPrice.gridy = 2;
		spPrice = new JSpinner();
		// Setting a model will set initial_value, min_value, max_value, step_size
		// Users can type in any number but if they click on the arrows in the spinner
		// the number changes by step_size amount. And all the four numbers given are
		// double (decimal numbers) so the spinner will take decimal numbers
		spPrice.setModel(new SpinnerNumberModel(0.0, 0.0, 10000.0, 1.0));
		contentPane.add(spPrice, gbc_spPrice);

		GridBagConstraints gbc_lblQuantity = new GridBagConstraints();
		gbc_lblQuantity.anchor = GridBagConstraints.WEST;
		gbc_lblQuantity.gridx = 1;
		gbc_lblQuantity.gridy = 3;
		JLabel lblQuantity = new JLabel("Quantity");
		contentPane.add(lblQuantity, gbc_lblQuantity);
		
		GridBagConstraints gbc_spQuantity = new GridBagConstraints();
		gbc_spQuantity.anchor = GridBagConstraints.WEST;
		gbc_spQuantity.gridx = 3;
		gbc_spQuantity.gridy = 3;
		spQuantity = new JSpinner();
		// Here all the 4 numbers are integer and hence it will only take integer input
		spQuantity.setModel(new SpinnerNumberModel(0, 0, 10000, 1));
		contentPane.add(spQuantity, gbc_spQuantity);
		
		GridBagConstraints gbc_btnAdd = new GridBagConstraints();
		gbc_btnAdd.anchor = GridBagConstraints.WEST;
		gbc_btnAdd.gridx = 2;
		gbc_btnAdd.gridy = 4;
		JButton btnAdd = new JButton("Add");
		contentPane.add(btnAdd, gbc_btnAdd);
		
		btnAdd.addActionListener(this);
	}
	
	// We need to override the following function for implementing ActionListener
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		// Add Button Clicked
		DatabaseHelper.addEntry(txtName.getText(), (double) spPrice.getValue(), (int) spQuantity.getValue());
		JOptionPane.showMessageDialog(null, "Entry Added!!");
	}
}
