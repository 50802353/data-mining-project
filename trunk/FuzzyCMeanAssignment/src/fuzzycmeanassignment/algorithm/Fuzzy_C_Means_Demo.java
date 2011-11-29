/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package fuzzycmeanassignment.algorithm;

import fuzzycmeanassignment.file.FileProccess;
import fuzzycmeanassignment.jogl.JoglColor;
import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFileChooser;

/**
 *
 * @author kubin
 */
public class Fuzzy_C_Means_Demo {

    /**
     * @param args the command line arguments
     */
    private double[][] arrUDegrees;
    private ExtendPoint[] clusters;
    private ExtendPoint[] points;
    private double m;
    private int seed;

    public Fuzzy_C_Means_Demo() {
    }

    public Fuzzy_C_Means_Demo(ExtendPoint[] points, int numberOfCluster, double m, int seed) {
	init(points, numberOfCluster, m, seed);
    }

    public void reset(ExtendPoint[] points, int numberOfCluster, double m, int seed) {
	init(points, numberOfCluster, m, seed);
    }

    private void init(ExtendPoint[] points, int numberOfCluster, double m, int seed) {
	int leng = points.length;

	//init U(0)
	arrUDegrees = new double[leng][];
	Random random = new Random(seed);
	
	for (int i = 0; i < leng; i++) {
	    arrUDegrees[i] = new double[numberOfCluster];
	    for (int j = 0; j < numberOfCluster; j++) {
		arrUDegrees[i][j] = random.nextDouble();
//                System.out.print(mU[i][j] + "\t");
	    }
//            System.out.println("");
	}

	//init C
	clusters = new ExtendPoint[numberOfCluster];

	for (int i = 0; i < numberOfCluster; i++) {
	    clusters[i] = new ExtendPoint(true);
	    clusters[i].copyFrom(points[i]);
//	    System.out.println("mC[" + i + "] = " + mC[i].x);
	}

	//System.arraycopy(xs, 0, mC, 0, numberOfCluster);

	this.points = points;
	this.m = m;
    }

    private void updateC() {
	double cx, cy, cz, mau, uijm;
	cx = cy = cz = mau = 0;
	int i, j;
	int cLeng = clusters.length;
	int xLeng = points.length;
	Point p;
	for (j = 0; j < cLeng; j++) {
//            mC[k] = calSumUmX(k)/calSumUm(k);
	    for (i = 0; i < xLeng; i++) {
		p = points[i];
		uijm = Math.pow(arrUDegrees[i][j], m);
		cx += uijm * p.x;
		cy += uijm * p.y;
		cz += uijm * p.z;
		mau += uijm;
	    }
	    clusters[j].x = cx / mau;
	    clusters[j].y = cy / mau;
	    clusters[j].z = cz / mau;

	    cx = cy = cz = mau = 0;
	}
    }

    private double calUij(int i, int j) {
	double result = 0;

	double tu = Math.abs(points[i].distance(clusters[j]));
	double mau = 0;
	for (int k = 0; k < clusters.length; k++) {

	    mau = Math.abs(points[i].distance(clusters[k]));
	    result += Math.pow(tu / mau, 2 / (m - 1));
	}

	return 1 / result;
    }

    private double updateU() {

	double max = Float.MIN_VALUE;

	for (int i = 0; i < points.length; i++) {
	    for (int j = 0; j < clusters.length; j++) {
		double nextUij = calUij(i, j);
		double evalue = Math.abs(arrUDegrees[i][j] - nextUij);
		arrUDegrees[i][j] = nextUij;
		if (max < evalue) {
		    max = evalue;
		}
	    }
	}

	return max;
    }

    public void run(double epsilon) {
	double max = Double.MAX_VALUE;
	int count = 0;
	do {
	    updateC();
	    max = updateU();
	    count++;

	} while (max > epsilon);
	System.out.println("count = " + count + "\t max = " + max);

	int leng = points.length;
	for(int i = 0; i < leng; i++){
	    calColor(i);
	}
    }

    private void calColor(int index) {
	int leng = clusters.length;
	int count = 0;
	double[] u = arrUDegrees[index]; // mức độ thành viên của 1 điểm vào điểm trung tâm
	JoglColor p = points[index].color;
	p.a = p.b = p.g = p.r = 0;
	JoglColor pc;
	for (int i = 0; i < leng; i++) {
	    if (u[i] > 0) {
		pc = clusters[i].color;
		p.a += pc.a * u[i];
		p.b += pc.b * u[i];
		p.g += pc.g * u[i];
		p.r += pc.r * u[i];
		count++;
	    }
	}
    }

    public double getM() {
	return m;
    }

    public ExtendPoint[] getClusters() {
	return clusters;
    }

    public double[][] getmU() {
	return arrUDegrees;
    }

    public Point[] getXs() {
	return points;
    }

    public void print() {
	System.out.println("C :");
	for (int i = 0; i < clusters.length; i++) {
	    clusters[i].print();
	}
	System.out.println("");
	System.out.println("U");
	for (int i = 0; i < points.length; i++) {
	    for (int j = 0; j < clusters.length; j++) {
		System.out.print(arrUDegrees[i][j] + ",\t");
	    }

	    System.out.println("");
	}
    }

    public static void main(String[] args) {
	// TODO code application logic here

	File file = new File("D:/sample.txt");
	ExtendPoint[] xs = FileProccess.readFile(file);
	System.out.println("xs[0] = " + xs[0].x);
	Fuzzy_C_Means_Demo fcm = new Fuzzy_C_Means_Demo(xs, 5, 2.0, 21);
	fcm.run(0.00001f);
	fcm.print();


    }
}
