package com.progclub.owp;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultFormatter;

import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.swing.AutoCompleteSupport;

/*
 *   This class is used to withdraw items from the inventory
 *   It also prints a receipt for the same.
 */
public class WithdrawItemsScreen extends JFrame implements ActionListener, ChangeListener, Runnable
{
	// This doesn't matter, placed here to avoid a warning in eclipse
	private static final long serialVersionUID = 8977765274843457737L;

	private static final String COMBO_ACTION_CMD_PREFIX = "COMBO_ACTION_";
	private static final String WITHDRAW_ACTION_CMD = "WITHDRAW_ACTION";

	// Just a class to represent data of the Item
	public class InventoryItem
	{
		public int id;
		public double price;
		public int quantity;

		public InventoryItem(int i, double p, int q)
		{
			id = i;
			price = p;
			quantity = q;
		}
	}
	// This will store all the elements of database
	Map<String, InventoryItem> map_items;

	// Values of these fields will be used for withdrawing/printing
	@SuppressWarnings("rawtypes")	// Avoids a warning in eclipse
	JComboBox[] cbNames;		// Changed from JTextField to JComboBox as it is better to provide auto-complete

	JSpinner[]   spRates;
	JSpinner[]   spQuantities;
	JLabel[]     lblPrices;
	JLabel lblTotalValue;

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

		cbNames = new JComboBox[NUM_ITEM_ROWS];
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

			GridBagConstraints gbc_cbName_i = new GridBagConstraints();
			gbc_cbName_i.anchor = GridBagConstraints.CENTER;
			gbc_cbName_i.gridx = 1;
			gbc_cbName_i.gridy = i+1;
			cbNames[i] = new JComboBox<String>();
			// Setting the action command will let us identify it. We are starting all the
			// Combo boxes with same string and adding their index so a combo box event can
			// be identified from a button press event and also we will know which combo box
			// has triggered the event
			cbNames[i].setActionCommand(COMBO_ACTION_CMD_PREFIX + i);
			cbNames[i].addActionListener(this);
			contentPane.add(cbNames[i], gbc_cbName_i);

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
			// This is required to add a listener to the spinner so that whenever its value changes,
			// it calls the 'stateChanged' function of our class. We will force the
			// formatter of TextField of the ComboBox to always fire events (or commit)
			// whenever a valid number is input into the spinner. We will also set the name
			// of the spinner so it can be identified later, as all the spinners will call the same
			// 'stateChanged' function. The name has to be of String type.
			JComponent comp = spQuantities[i].getEditor();
			JFormattedTextField field = (JFormattedTextField) comp.getComponent(0);
			DefaultFormatter formatter = (DefaultFormatter) field.getFormatter();
			formatter.setCommitsOnValidEdit(true);
			spQuantities[i].setName("" + i);		// Set the name to be the index so it's easy to identify
			spQuantities[i].addChangeListener(this);

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

		GridBagConstraints gbc_lblTotalPrice = new GridBagConstraints();
		gbc_lblTotalPrice.anchor = GridBagConstraints.WEST;
		gbc_lblTotalPrice.gridx = 3;
		gbc_lblTotalPrice.gridy = 9;
		JLabel lblTotalPrice = new JLabel("Total Price");
		contentPane.add(lblTotalPrice, gbc_lblTotalPrice);

		GridBagConstraints gbc_lblTotalValue = new GridBagConstraints();
		gbc_lblTotalValue.anchor = GridBagConstraints.WEST;
		gbc_lblTotalValue.gridx = 4;
		gbc_lblTotalValue.gridy = 9;
		lblTotalValue = new JLabel("0.0");
		contentPane.add(lblTotalValue, gbc_lblTotalValue);

		btnWithdraw.setActionCommand(WITHDRAW_ACTION_CMD);
		btnWithdraw.addActionListener(this);

		// Since the database cam have a lot of entries, load all of them
		// in a separate thread
		Thread th = new Thread(this);
		th.start();		// Calls the 'run' method
	}

	// Need to override actionPerformed to implement ActionListener
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		String actionCmd = arg0.getActionCommand();
		if(actionCmd.startsWith(COMBO_ACTION_CMD_PREFIX))
		{
			// Combo box event
			int cbNum = actionCmd.charAt(actionCmd.length()-1) - '0';
			String selection = (String) cbNames[cbNum].getSelectedItem();
			if(map_items.containsKey(selection))
			{
				InventoryItem item = map_items.get(selection);
				spRates[cbNum].setValue(item.price);
				spQuantities[cbNum].setValue(1);
				lblPrices[cbNum].setText(Double.toString(item.price));

				recalculateTotalValue();	// recalculate the total price of items
			}
		}
		else if(actionCmd == WITHDRAW_ACTION_CMD)
		{
			// Withdraw Button Pressed. Update the Database
			for (int i=0; i< NUM_ITEM_ROWS; ++i)
			{
				String curItemName = (String) cbNames[i].getSelectedItem();
				if(map_items.containsKey(curItemName))
				{
					InventoryItem item = map_items.get(curItemName);
					int quantityWithdrawn = (int) spQuantities[i].getValue();
					int quantityLeft = item.quantity - quantityWithdrawn;

					// Update the database
					DatabaseHelper.updateEntry(item.id, curItemName, item.price, quantityLeft);

					// Update the map too
					item.quantity = quantityLeft;
					map_items.put(curItemName, item);
				}
			}
			if (cbPrint.isSelected())
			{
				// Print the receipt
				// First get all the values to String matrix: 7 rows, 4 columns
				String[][] strMat = new String[NUM_ITEM_ROWS][];
				for(int i=0;i<NUM_ITEM_ROWS;++i)
				{
					strMat[i] = new String[4];
					strMat[i][0] = (String) cbNames[i].getSelectedItem();
					strMat[i][0] = strMat[i][0] == null ? "" : strMat[i][0];
					if(!strMat[i][0].isEmpty())
					{
						// If the name is not empty then only populate other things
						strMat[i][1] = InventoryManager.formatDouble((double) spRates[i].getValue());
						strMat[i][2] = Integer.toString((int) spQuantities[i].getValue());
						strMat[i][3] = lblPrices[i].getText();
					}
					else
					{
						// Populate other fields with empty strings (not null)
						strMat[i][1] = "";
						strMat[i][2] = "";
						strMat[i][3] = "";
					}
				}
				// Call our utility function to do the job
				InventoryManager.printReceipt(strMat, lblTotalValue.getText());
			}
		}
	}

	private void recalculateTotalValue()
	{
		// A function to calculate the total price of all the goods
		double netValue = 0;
		for(int i=0; i<NUM_ITEM_ROWS; ++i)
		{
			try
			{
				// Add value to netValue only if the value is valid. Else it will throw Exception
				double curVal = Double.parseDouble(lblPrices[i].getText());
				netValue += curVal;
			}
			catch (Exception e) {}
		}
		// Display total price upto 2 decimal places
		lblTotalValue.setText(InventoryManager.formatDouble(netValue));
	}

	// Need to override this to implement Runnable
	@Override
	public void run()
	{
		ResultSet rs = DatabaseHelper.getAllEntries();
		try
		{
			map_items  = new HashMap<String, InventoryItem>();
			while(rs.next())
			{
				// Result set is like linked list of rows with each element being a Map
				// so to get the values of a column, we use the column name as key
				// the type of value returned depends on the function used (getInt, getString ...)
				int id = rs.getInt(DatabaseHelper.COLUMN_ID);
				String name = rs.getString(DatabaseHelper.COLUMN_NAME);
				double price = rs.getDouble(DatabaseHelper.COLUMN_PRICE);
				int quantity = rs.getInt(DatabaseHelper.COLUMN_QUANTITY);

				map_items.put(name, new InventoryItem(id, price, quantity));
			}
			// To allow auto complete support we need to call AutoCompleteSupport.install
			// from GlazedLists library. But it demands that is should be called on the 
			// GUI thread of swing. Hence we request Swing to run the following code on
			// its own thread.
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					// Allow auto-complete for Names of items
					Object[] all_names = map_items.keySet().toArray();
					for(int i=0; i< NUM_ITEM_ROWS; ++i)
						AutoCompleteSupport.install(cbNames[i], GlazedLists.eventListOf(all_names));
				}
			});
		}
		catch (SQLException e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	// Need to override this for implementing ChangeListener
	@Override
	public void stateChanged(ChangeEvent arg0) {
		// This is called whenever user changes quantity of an item
		JSpinner spCur = (JSpinner) arg0.getSource();
		// First character in the Spinner's name will give its index
		int spNum = spCur.getName().charAt(0) - '0';
		double quantity = (int) spCur.getValue();
		double rate = (double) spRates[spNum].getValue();
		// Use String.format to get the double rounded upto 2 decimal places
		// This avoids wastage for some numbers showing 10 decimal places
		lblPrices[spNum].setText(InventoryManager.formatDouble(quantity * rate));

		recalculateTotalValue();	// Recalculate the total prices
	}

}
