/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package fuzzycmeanassignment.file;

import fuzzycmeanassignment.algorithm.Point;
import java.io.*;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

/**
 *
 * @author kubin
 */
public class FileProccess {

    private Point[] data;
    private long lastModified = 0;
    private String fileName = "";

    public FileProccess() {
	lastModified = -1;
	fileName = "";
    }

    public Point[] proccess(File file) {
	if (fileName.equals(file.getAbsolutePath()) && lastModified == file.lastModified()) {
	} else {
	    fileName = file.getAbsolutePath();
	    lastModified = file.lastModified();

	    data = readFile(file);
	}
	return data;
    }

    public static Point[] readFile(File file) {

	BufferedReader reader = null;

	ArrayList<Point> data = null;
	file.lastModified();
	try {
	    reader = new BufferedReader(new FileReader(file));
	    String line = reader.readLine();
	    while (line != null & !line.trim().equals("@data")) {
		line = reader.readLine();
	    }
	    line = reader.readLine();
	    data = new ArrayList<Point>(1000);
	    while (line.length() <= 0) {
		line = reader.readLine();
	    }

	    while (line != null) {
//		System.out.println("line = " + line);
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
	Point[]result = new Point[data.size()];
	data.toArray(result);
	return result;
    }

    private static Point convertToFloat(String str) {
	String[] arr = str.split(",");
	float[] tmp = new float[3];
	for (int i = 0; i < 3; i++) {
	    tmp[i] = Float.parseFloat(arr[i]);
	}

	return new Point(tmp[0], tmp[1], tmp[2]);
    }

    public static void main(String[] args) {
	JFileChooser chooser = new JFileChooser();
	chooser.showOpenDialog(null);
	File file = chooser.getSelectedFile();



    }
}
