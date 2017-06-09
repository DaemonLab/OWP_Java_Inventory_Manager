package com.progclub.owp;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

/**
 *   This class does the actual printing of the receipt
 *   It based on example printing code from java docs at 
 *       https://docs.oracle.com/javase/tutorial/2d/printing/examples/HelloWorldPrinter.java
 */
public class ReceiptPrinter implements Printable
{
	private String[][] strMat;
	private String strTotal;
	
	// Just the position of columns
	private static final int[] xPos = {10, 50, 150, 200, 260};

	public ReceiptPrinter(String[][] strMat, String strTotal)
	{
		this.strMat = strMat;
		this.strTotal = strTotal;
	}

	// Need to override the following function to implement Printable
	// It will be called for printing
	@Override
    public int print(Graphics g, PageFormat pf, int page) throws PrinterException
	{
        if (page > 0) { /* We have only one page, and 'page' is zero-based */
            return NO_SUCH_PAGE;
        }

        /* User (0,0) is typically outside the imageable area, so we must
         * translate by the X and Y values in the PageFormat to avoid clipping
         */
        Graphics2D g2d = (Graphics2D)g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());

        /** Now we perform our rendering **/
        // Title of the receipt
        g.drawString("Receipt/Invoice of your withdrawl", 40, 20);
        // Rectangle around the table
        g.drawRect(xPos[0]-5, 25, 300, 235);
        // Column Headers
        g.drawString("S.No", xPos[0], 40);
        g.drawString("Name", xPos[1], 40);
        g.drawString("Rate", xPos[2], 40);
        g.drawString("Quantity", xPos[3], 40);
        g.drawString("Price", xPos[4], 40);
        // Each row of the items list
        for(int i=0;i<strMat.length; ++i)
        {
        	for(int j=0; j<=strMat[i].length; ++j)
        	{
        		if(j==0)
        			g.drawString("" + (i+1), xPos[j], i*30 + 70);	// S.No.
        		else
        			g.drawString(strMat[i][j-1], xPos[j], i*30 + 70);
        	}
        }
        // Final total value
        g.drawString("Total Price", xPos[2], 280);
        g.drawString(strTotal, xPos[4], 280);

        /* tell the caller that this page is part of the printed document */
        return PAGE_EXISTS;
    }
}
