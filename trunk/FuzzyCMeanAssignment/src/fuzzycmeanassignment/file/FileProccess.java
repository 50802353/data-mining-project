/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package fuzzycmeanassignment.file;

import java.io.*;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

/**
 *
 * @author kubin
 */
public class FileProccess {
    
    public static Vector<float[]> readFile(File file){
	BufferedReader reader = null;
	Vector<float[]> data = null; 
	try {
	    reader = new BufferedReader(new FileReader(file));
	    String line = reader.readLine();
	    while(line != null & !line.trim().equals("@data")){
		line = reader.readLine();
	    }
	    line = reader.readLine();
	    data = new Vector<float[]>(1000);
	    while(line.length() <= 0){
		line = reader.readLine();
	    }
	    
	    while(line != null){
		System.out.println("line = " + line);
		data.add(convertToFloat(line));
		line = reader.readLine();
	    }
	} catch (Exception ex) {
	    Logger.getLogger(FileProccess.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
	    try {
		reader.close();
	    } catch (IOException ex) {
		Logger.getLogger(FileProccess.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
	return data;
    }
    private Vector<float[]> data; 
    
    public FileProccess(File file){
	BufferedReader reader = null;
	try {
	    reader = new BufferedReader(new FileReader(file));
	    String line = reader.readLine();
	    while(line != null & !line.trim().equals("@data")){
		line = reader.readLine();
	    }
	    line = reader.readLine();
	    data = new Vector<float[]>(1000);
	    while(line.length() <= 0){
		line = reader.readLine();
	    }
	    
	    while(line != null){
		System.out.println("line = " + line);
		data.add(convertToFloat(line));
		line = reader.readLine();
	    }
	    
	} catch (Exception ex) {
	    Logger.getLogger(FileProccess.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
	    try {
		reader.close();
	    } catch (IOException ex) {
		Logger.getLogger(FileProccess.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
	
    }
    
    private static float[] convertToFloat(String str){
	String []arr = str.split(",");
	float[] result = new float[3];
	for(int i = 0; i < 3; i++){
	    result[i] = Float.parseFloat(arr[i]);
	}
	
	return result;
    }
    
    public static void main(String []args){
	JFileChooser chooser = new JFileChooser();
	chooser.showOpenDialog(null);
	File file = chooser.getSelectedFile();
	
	
	
    }
}
