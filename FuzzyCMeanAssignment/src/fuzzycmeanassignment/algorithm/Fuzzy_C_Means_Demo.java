/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fuzzycmeanassignment.algorithm;

import fuzzycmeanassignment.file.FileProccess;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JFileChooser;

/**
 *
 * @author kubin
 */
public class Fuzzy_C_Means_Demo {

    /**
     * @param args the command line arguments
     */
    
    private double [][]mU;
    private Point []mC;
    private Point []xs;
    private double m;
    
    public Fuzzy_C_Means_Demo(Point []xs, int numberOfCluster, double m){
        int leng = xs.length;
        
        //init U(0)
        mU = new double[leng][];
	
        for(int i = 0; i < leng; i++){
            mU[i] = new double[numberOfCluster];
            for(int j = 0; j < numberOfCluster; j++){
                mU[i][j] = (Math.random() * 23434 % 1000 > 500? 1 : 0);
//                System.out.print(mU[i][j] + "\t");
            }
//            System.out.println("");
        }
        
        //init C
        mC = new Point[numberOfCluster];
	
	for(int i = 0; i < mC.length; i++){
	    mC[i] = new Point();
	    mC[i].copyFrom(xs[i]);
//	    System.out.println("mC[" + i + "] = " + mC[i].x);
	}
	
        //System.arraycopy(xs, 0, mC, 0, numberOfCluster);
        
        this.xs = xs;
        this.m = m;
    }
    
    private void updateC(){
	double cx, cy, cz, mau, uijm;
	cx = cy = cz = mau = 0;
	int i, j;
	int cLeng = mC.length;
	int xLeng = xs.length;
	Point p;
        for(j = 0; j < cLeng; j++){
//            mC[k] = calSumUmX(k)/calSumUm(k);
	    for(i = 0; i < xLeng; i++){
		p = xs[i];
		uijm = Math.pow(mU[i][j], m);
		cx += uijm * p.x;
		cy += uijm * p.y;
		cz += uijm * p.z;
		mau += uijm;
	    }
	    
	    mC[j].x = cx / mau;
	    mC[j].y = cy / mau;
	    mC[j].z = cz / mau;
	    
        }
    }
    
    private double calUij(int i, int j){
        double result = 0;
        
        double tu = Math.abs(xs[i].distance(mC[j]));
        double mau = 0;
        for(int k = 0; k < mC.length; k++){
            
            mau = Math.abs(xs[i].distance(mC[k]));
            result += Math.pow(tu / mau, 2 / (m - 1));
        }
        
        return 1 / result;
    }
    
    private double updateU(){
        
        double max = Float.MIN_VALUE;
        
        for(int i = 0; i < xs.length; i++){
            for(int j = 0; j < mC.length; j++){
                double nextUij = calUij(i, j);
                double evalue = Math.abs(mU[i][j] - nextUij);
                mU[i][j] = nextUij;
                if(max < evalue){
                    max = evalue;
                }
            }
        }
        
        return max;
    }
    
    public void run(float epsilon){
        double max = Double.MAX_VALUE;
        int count = 0;
        do{
            updateC();
            max = updateU();
//            System.out.println("count = " + count + "\t max = " + max);
            count++;
            
        }while (max > epsilon);
	System.out.println("count = " + count + "\t max = " + max);
          
    }
    
    public void print(){
        System.out.println("C :");
        for(int i = 0 ; i < mC.length; i++){
            mC[i].print();
        }
        System.out.println("");
        System.out.println("U");
        for(int i = 0; i < xs.length; i++){
            for(int j = 0; j < mC.length; j++){
                System.out.print(mU[i][j] + ",\t");
            }
            
            System.out.println("");
        }
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
	
	File file = new File("D:/sample.txt");
	Point[] xs = FileProccess.readFile(file);
	System.out.println("xs[0] = " + xs[0].x);
        Fuzzy_C_Means_Demo fcm = new Fuzzy_C_Means_Demo(xs, 3, 2.0);
        fcm.run(0.00001f);
        fcm.print();
        
        
    }
}
