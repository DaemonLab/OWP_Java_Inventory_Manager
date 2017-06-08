package com.progclub.owp;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.progclub.utilities.TableCellListener;

import javax.swing.JTable;

public class ViewEditScreen extends JFrame implements ActionListener, Runnable
{
	// This doesn't matter, placed here to avoid a warning in eclipse
	private static final long serialVersionUID = 245020422665595721L;

	private JTable table;

	/**
	 * Create the frame.
	 */
	public ViewEditScreen() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setTitle("View/Edit Inventory");

		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout());

		// Setting the custom table model which doesn't allow editing of first column (id)
		// The rest of the cells can be edited in place and update can be performed
		// The first Object[] argument lists the headers of columns. The next argument (integer)
		// gives the number of rows present when it is created. Since we will populate
		// the table later on, we keep it to zero (0)
		table = new JTable(new CustomTableModel(new Object[] {"ID", "Name", "Price/piece", "Quantity"}, 0));
		// Putting a table in a JScrollPane makes the table header visible. Also if the table has
		// many rows then it facilitates scrolling the contents
		JScrollPane scroller = new JScrollPane(table);
		scroller.setPreferredSize(getMaximumSize());	// Set the size to full size of the window
		contentPane.add(scroller);
		
		// This helps us catch edits in any cell, it attaches a TableCellListener
		// to table which calls 'actionPerformed' whenever a cell is edited hence allows 
		// us to update the database when required
		new TableCellListener(table, this);
		
		// Since the database cam have a lot of entries, load all of them
		// in a separate thread
		Thread th = new Thread(this);
		th.start();		// Calls the 'run' method
	}
	
	// The following function must be overriden to implement Runnable and allow running the thread
	@Override
	public void run()
	{
		ResultSet rs = DatabaseHelper.getAllEntries();
		try
		{
			while(rs.next())
			{
				// Result set is like linked list of rows with each element being a Map
				// so to get the values of a column, we use the column name as key
				// the type of value returned depends on the function used (getInt, getString ...)
				int id = rs.getInt(DatabaseHelper.COLUMN_ID);
				String name = rs.getString(DatabaseHelper.COLUMN_NAME);
				double price = rs.getDouble(DatabaseHelper.COLUMN_PRICE);
				int quantity = rs.getInt(DatabaseHelper.COLUMN_QUANTITY);
				
				// Add this data to the table. All values have to be String
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.addRow(new Object[] {Integer.toString(id), name, Double.toString(price), Integer.toString(quantity)});
			}
		}
		catch (SQLException e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	private class CustomTableModel extends DefaultTableModel
	{
		// This doesn't matter, placed here to avoid a warning in eclipse
		private static final long serialVersionUID = -7908536523358403447L;

		public CustomTableModel(Object[] objs, int numRows)
		{
			// Just call the constructor of DefaultTableModel -- super class of this class
			super(objs, numRows);
		}
		
		@Override
		public boolean isCellEditable(int row, int column)
		{
			// Don't allow editing of first (0th) column
			return column == 0 ? false : true;
		}
	}
	
	// We need to override the following function for implementing ActionListener
	@Override
	public void actionPerformed(ActionEvent e)
	{
		// A table cell was edited
		TableCellListener tcl = (TableCellListener) e.getSource();
		int r = tcl.getRow();
		// table stores all values as Strings and hence the parsing is needed
		int id = Integer.parseInt((String) table.getValueAt(r,  0));
		String name = (String) table.getValueAt(r,  1);
		double price = Double.parseDouble((String) table.getValueAt(r,  2));
		int quantity = Integer.parseInt((String) table.getValueAt(r,  3));
		
		DatabaseHelper.updateEntry(id, name, price, quantity);	// update database

		JOptionPane.showMessageDialog(null, "Value in row: " + r + " Changed to: (" + id + ", " + name + ", "
				 					+ price + ", " + quantity + ")");
	}
}
