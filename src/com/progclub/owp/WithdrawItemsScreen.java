package com.progclub.owp;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

/*
 *   This class is used to withdraw items from the inventory
 *   It also prints a receipt for the same.
 */
public class WithdrawItemsScreen extends JFrame implements ActionListener
{
	// This doesn't matter, placed here to avoid a warning in eclipse
	private static final long serialVersionUID = 8977765274843457737L;

	// Values of these fields will be used for withdrawing/printing
	JTextField[] txtNames;
	JSpinner[]   spRates;
	JSpinner[]   spQuantities;
	JLabel[]     lblPrices;

	// To check if user wants to print the receipt
	JCheckBox cbPrint;

	// No of items to be with drawn - max items printable on receipt
	private static final int NUM_ITEM_ROWS = 7;

	/**
	 * Create the frame.
	 */
	public WithdrawItemsScreen() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setTitle("Withdraw Items");

		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWeights = new double[]{0.2, 1, 1, 1, 1};
		gbl_contentPane.rowWeights = new double[]{1, 1, 1, 1, 1, 1, 1, 1, 1};
		contentPane.setLayout(gbl_contentPane);

		GridBagConstraints gbc_lblSNo = new GridBagConstraints();
		gbc_lblSNo.anchor = GridBagConstraints.WEST;
		gbc_lblSNo.gridx = 0;
		gbc_lblSNo.gridy = 0;
		JLabel lblSNo = new JLabel("S.No.");
		contentPane.add(lblSNo, gbc_lblSNo);

		GridBagConstraints gbc_lblItemName = new GridBagConstraints();
		gbc_lblItemName.anchor = GridBagConstraints.CENTER;
		gbc_lblItemName.gridx = 1;
		gbc_lblItemName.gridy = 0;
		JLabel lblItemName = new JLabel("Item Name");
		contentPane.add(lblItemName, gbc_lblItemName);

		GridBagConstraints gbc_lblRate = new GridBagConstraints();
		gbc_lblRate.anchor = GridBagConstraints.CENTER;
		gbc_lblRate.gridx = 2;
		gbc_lblRate.gridy = 0;
		JLabel lblRate = new JLabel("Rate");
		contentPane.add(lblRate, gbc_lblRate);

		GridBagConstraints gbc_lblQuantity = new GridBagConstraints();
		gbc_lblQuantity.anchor = GridBagConstraints.CENTER;
		gbc_lblQuantity.gridx = 3;
		gbc_lblQuantity.gridy = 0;
		JLabel lblQuantity = new JLabel("Quantity");
		contentPane.add(lblQuantity, gbc_lblQuantity);

		GridBagConstraints gbc_lblPrice = new GridBagConstraints();
		gbc_lblPrice.anchor = GridBagConstraints.CENTER;
		gbc_lblPrice.gridx = 4;
		gbc_lblPrice.gridy = 0;
		JLabel lblPrice = new JLabel("Price");
		contentPane.add(lblPrice, gbc_lblPrice);

		txtNames = new JTextField[NUM_ITEM_ROWS];
		spRates = new JSpinner[NUM_ITEM_ROWS];
		spQuantities = new JSpinner[NUM_ITEM_ROWS];
		lblPrices = new JLabel[NUM_ITEM_ROWS];
		for (int i=0; i<NUM_ITEM_ROWS; ++i)
		{
			GridBagConstraints gbc_lblSNo_i = new GridBagConstraints();
			gbc_lblSNo_i.anchor = GridBagConstraints.WEST;
			gbc_lblSNo_i.gridx = 0;
			gbc_lblSNo_i.gridy = i+1;
			JLabel lblSNo_i = new JLabel(Integer.toString(i+1));
			contentPane.add(lblSNo_i, gbc_lblSNo_i);

			GridBagConstraints gbc_txtName_i = new GridBagConstraints();
			gbc_txtName_i.anchor = GridBagConstraints.CENTER;
			gbc_txtName_i.gridx = 1;
			gbc_txtName_i.gridy = i+1;
			txtNames[i] = new JTextField();
			txtNames[i].setColumns(10);
			contentPane.add(txtNames[i], gbc_txtName_i);

			GridBagConstraints gbc_spRate_i = new GridBagConstraints();
			gbc_spRate_i.anchor = GridBagConstraints.CENTER;
			gbc_spRate_i.gridx = 2;
			gbc_spRate_i.gridy = i+1;
			spRates[i] = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 10000.0, 1.0));
			contentPane.add(spRates[i], gbc_spRate_i);

			GridBagConstraints gbc_spQuantity_i = new GridBagConstraints();
			gbc_spQuantity_i.anchor = GridBagConstraints.CENTER;
			gbc_spQuantity_i.gridx = 3;
			gbc_spQuantity_i.gridy = i+1;
			spQuantities[i] = new JSpinner(new SpinnerNumberModel(0, 0, 10000, 1));
			contentPane.add(spQuantities[i], gbc_spQuantity_i);

			GridBagConstraints gbc_lblPrice_i = new GridBagConstraints();
			gbc_lblPrice_i.anchor = GridBagConstraints.CENTER;
			gbc_lblPrice_i.gridx = 4;
			gbc_lblPrice_i.gridy = i+1;
			lblPrices[i] = new JLabel("");
			contentPane.add(lblPrices[i], gbc_lblPrice_i);
		}

		GridBagConstraints gbc_cbPrint = new GridBagConstraints();
		gbc_cbPrint.anchor = GridBagConstraints.CENTER;
		gbc_cbPrint.gridx = 1;
		gbc_cbPrint.gridy = 9;
		cbPrint = new JCheckBox("Print Receipt");
		cbPrint.setSelected(true);
		contentPane.add(cbPrint, gbc_cbPrint);

		GridBagConstraints gbc_btnWithdraw = new GridBagConstraints();
		gbc_btnWithdraw.anchor = GridBagConstraints.CENTER;
		gbc_btnWithdraw.gridx = 2;
		gbc_btnWithdraw.gridy = 9;
		JButton btnWithdraw = new JButton("Withdraw");
		contentPane.add(btnWithdraw, gbc_btnWithdraw);

		btnWithdraw.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		JOptionPane.showMessageDialog(null, "The code for withdraw will be added later!");
	}

}
