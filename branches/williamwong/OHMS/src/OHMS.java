//
//  OHMS.java
//  OHMS
//
//  Created by Kevin Ridsdale on 09-10-14.
//  Copyright (c) 2009 Home. All rights reserved.
//
//  A simple signed Java applet
// THIS IS A TEST!

import java.awt.*;
import java.applet.*;
import javax.swing.*;

public class OHMS extends JApplet {
	
    static final String message = "Hello World!";
    private Font font = new Font("serif", Font.ITALIC + Font.BOLD, 36);
	
    public void init() {
        // set the default look and feel
        String laf = UIManager.getSystemLookAndFeelClassName();
        try {
            UIManager.setLookAndFeel(laf);
        } catch (UnsupportedLookAndFeelException exc) {
            System.err.println ("Warning: UnsupportedLookAndFeel: " + laf);
        } catch (Exception exc) {
            System.err.println ("Error loading " + laf + ": " + exc);
        }
		getContentPane().setLayout (null);
    }
	
    public void paint (Graphics g) {
        super.paint(g);
        g.setColor(Color.blue);
        g.setFont(font);
        g.drawString(message, 40, 80);
    }
}

// testing subversion William Wong
