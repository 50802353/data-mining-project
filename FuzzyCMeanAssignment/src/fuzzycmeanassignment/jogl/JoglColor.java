/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package fuzzycmeanassignment.jogl;

import java.awt.Color;

/**
 *
 * @author kubin
 */
public class JoglColor {
    public double a,r,g,b;

    public JoglColor() {
	a = r = g = b = 0;
    }

    public JoglColor(double a, double r, double g, double b) {
	this.a = a;
	this.r = r;
	this.g = g;
	this.b = b;
    }
    
    public JoglColor(double r, double g, double b){
	this.r = r;
	this.g = g;
	this.b = b;
	this.a = 1;
    }
    
    public Color toColor(){
	return new Color((float)r,(float) g,(float) b,(float) a);
    }
}
