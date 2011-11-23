/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package fuzzycmeanassignment.algorithm;

import java.util.ArrayList;

/**
 *
 * @author kubin
 */
public class Point {

    public double x, y, z;

    public Point() {
	init(0, 0, 0);
    }

    public Point(double x, double y, double z) {
	init(x, y, z);
    }

    private void init(double x, double y, double z) {
	this.x = x;
	this.y = y;
	this.z = z;

    }

    public void update(double x, double y, double z) {
	init(x, y, z);
    }

    public double distance(Point p) {
	double dx = x - p.x;
	double dy = y - p.y;
	double dz = z - p.z;
	return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public void print() {
	System.out.println("[" + x + ",\t" + y + ",\t" + z + "]");
    }

    public void copyTo(Point p) {
	if (p != null) {
	    p.x = x;
	    p.y = y;
	    p.z = z;
	}
    }

    public void copyFrom(Point p) {
	if (p != null) {
	    x = p.x;
	    y = p.y;
	    z = p.z;
	}
    }
}
