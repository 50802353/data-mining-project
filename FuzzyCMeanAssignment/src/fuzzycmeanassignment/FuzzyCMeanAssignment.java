/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package fuzzycmeanassignment;

import javax.swing.UIManager;

/**
 *
 * @author kubin
 */
public class FuzzyCMeanAssignment {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
	try {
	    UIManager.setLookAndFeel(
		    UIManager.getSystemLookAndFeelClassName());
	} catch (Exception e) {
	}
	FuzzyCMeanFrame fcmFrame = new FuzzyCMeanFrame();
	fcmFrame.setVisible(true);
    }
}
