/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package fuzzycmeanassignment.algorithm;

import fuzzycmeanassignment.jogl.JoglColor;
import java.awt.Color;
import javax.swing.JPanel;

/**
 *
 * @author kubin
 */
public class ExtendPoint extends Point{
    
    public JoglColor color;

    public ExtendPoint() {
	super();
	color = new JoglColor(Math.random(), Math.random(), Math.random());
    }

    public ExtendPoint(double x, double y, double z) {
	super(x, y, z);
	color = new JoglColor(Math.random(), Math.random(), Math.random());
    }

    public ExtendPoint(JoglColor color, double x, double y, double z) {
	super(x, y, z);
	this.color = color;
    }
    
    
    
    
    
    
    public Object []toObject(int id){
	Object []result = new Object[5];
	result[0] = id;
	result[1] = x;
	result[2] = y;
	result[3] = z; 
//	System.out.println("id = " + id);
//	System.out.println("["+x + ", " + y +", "+ z +"]");
	result[4] = " ";
	
	return result;
    }
}
