/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package fuzzycmeanassignment.algorithm;

import fuzzycmeanassignment.jogl.JoglColor;
import java.util.Random;

/**
 *
 * @author kubin
 */
public class ExtendPoint extends Point {

    public JoglColor color;

    public ExtendPoint(boolean initRandColor) {
	super();
	if (initRandColor) {
	    color = GenerateColor.nextColor();
	} else {
	    color = new JoglColor();
	}
    }

    public ExtendPoint(double x, double y, double z, boolean initRandColor) {
	super(x, y, z);
	if (initRandColor) {
	    color = GenerateColor.nextColor();
	} else {
	    color = new JoglColor();
	}
    }

    public ExtendPoint(JoglColor color, double x, double y, double z) {
	super(x, y, z);
	this.color = color;
    }

    public Object[] toObject(int id) {
	Object[] result = new Object[5];
	result[0] = id;
	result[1] = x;
	result[2] = y;
	result[3] = z;
	result[4] = " ";

	return result;
    }
}
