/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package fuzzycmeanassignment.test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kubin
 */
public class TestCreate {
    public static void main(String []args){
	try {
	    BufferedWriter writer = new BufferedWriter(new FileWriter("D:/fuzzy_testcase.txt"));
	    float x = 0, y = 0, z = 0;
	    writer.write("@relation weka.datagenerators.clusterers.BIRCHCluster-S_1_-a_3_-k_5_-N_20000..20000_-R_5000..5000_-G_-D_4.0_-O_-P_0.1\r\n\r\n");
	    writer.write("@attribute X0 numeric\r\n@attribute X1 numeric\r\n@attribute X2 numeric\r\n\r\n@data\r\n\r\n");
		    
	    for(int i = 0; i < 100000; i++){
		x = (float) (Math.random() * 45000 + 5000);
		y = (float) ( 25000);
		z = (float) (0);
		
		writer.write(x + "," + y +"," +z +"\r\n");
	    }
	    //writer.write(x + "," + y +"," +z +"\r\n");
	    writer.close();
	} catch (IOException ex) {
	    Logger.getLogger(TestCreate.class.getName()).log(Level.SEVERE, null, ex);
	}
	
    }
}
