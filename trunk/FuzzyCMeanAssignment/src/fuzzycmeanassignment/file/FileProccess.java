/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package fuzzycmeanassignment.file;

import fuzzycmeanassignment.algorithm.ExtendPoint;
import fuzzycmeanassignment.algorithm.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

/**
 *
 * @author kubin
 */
public class FileProccess {

    private ExtendPoint[] data;
    private long lastModified = 0;
    private String fileName = "";
    public static Point pMax, pMin;

    public FileProccess() {
	lastModified = -1;
	fileName = "";
	pMin = new Point(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);
	pMax = new Point(Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE);

    }

    public ExtendPoint[] proccess(File file) {
	if (fileName.equals(file.getAbsolutePath()) && lastModified == file.lastModified()) {
	} else {
	    fileName = file.getAbsolutePath();
	    lastModified = file.lastModified();
	    pMin = new Point(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);
	    pMax = new Point(Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE);
	    data = readFile(file);
	}
	return data;
    }

    public static ExtendPoint[] readFile(File file) {

	BufferedReader reader = null;

	ArrayList<ExtendPoint> data = null;
	file.lastModified();
	try {
	    reader = new BufferedReader(new FileReader(file));
	    String line = reader.readLine();
	    while (line != null & !line.trim().equals("@data")) {
		line = reader.readLine();
	    }
	    line = reader.readLine();
	    data = new ArrayList<ExtendPoint>(1000);
	    while (line.length() <= 0) {
		line = reader.readLine();
	    }
	    
	    ExtendPoint p = convertToFloat(line);
	    pMin.x = pMax.x = p.x;
	    pMin.y = pMax.y = p.y;
	    pMin.z = pMax.z = p.z;
	    data.add(p);
	    line = reader.readLine();

	    while (line != null) {
//		System.out.println("line = " + line);
		p = convertToFloat(line);
		data.add(p);
		findMinMax(p);
		

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
	ExtendPoint[] result = new ExtendPoint[data.size()];
	data.toArray(result);
	return result;
    }

    private static void findMinMax(Point p) {
	if (p.x > pMax.x) {
	    pMax.x = p.x;
	} else if (p.x < pMin.x) {
	    pMin.x = p.x;
	}
	if (p.y > pMax.y) {
	    pMax.y = p.y;
	} else if (p.y < pMin.y) {
	    pMin.y = p.y;
	}
	if (p.z > pMax.z) {
	    pMax.z = p.z;
	} else if (p.z < pMin.z) {
	    pMin.z = p.z;
	}
    }

    private static ExtendPoint convertToFloat(String str) {
	String[] arr = str.split(",");
	double[] tmp = new double[3];
	for (int i = 0; i < 3; i++) {
	    tmp[i] = Double.parseDouble(arr[i]);
	}

	return new ExtendPoint(tmp[0], tmp[1], tmp[2], false);
    }

    public Point getMin() {
	return pMin;
    }

    public Point getMax() {
	return pMax;
    }

    public static void main(String[] args) {
	JFileChooser chooser = new JFileChooser();
	chooser.showOpenDialog(null);
	File file = chooser.getSelectedFile();



    }
}
