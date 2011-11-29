/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package fuzzycmeanassignment.jogl;

import fuzzycmeanassignment.algorithm.ExtendPoint;
import fuzzycmeanassignment.algorithm.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;
import javax.swing.JFrame;

/**
 *
 * @author kubin
 */
public class Renderer implements GLEventListener {

    private GLU glu = new GLU();
    private ExtendPoint[] points;
    private ExtendPoint[] clusters;
    private Point pMin, pMax;
    private GLCanvas canvas;
    private int width, height;

    public Renderer() {
    }

    public Renderer(GLCanvas canvas) {
	this.canvas = canvas;
    }

    public void reset(ExtendPoint[] ps, ExtendPoint[] cs, Point min, Point max) {
	points = ps;
	clusters = cs;
	pMin = min;
	pMax = max;
	canvas.display();
    }

    @Override
    public void display(GLAutoDrawable gLDrawable) {
	final GL2 gl = gLDrawable.getGL().getGL2();
	gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
	if (points != null) {
	    gl.glMatrixMode(GL2.GL_PROJECTION);
	    gl.glLoadIdentity();
	    double min, max;
	    min = (pMin.x > pMin.y ? pMin.y : pMin.x);
	    max = (pMax.x > pMax.y ? pMax.x : pMax.y);
	    gl.glOrtho(min, max, min, max, -100000, 100000);
 
	    for (ExtendPoint p : clusters) {
		gl.glColor3d(p.color.r, p.color.g, p.color.b);
		gl.glBegin(GL2.GL_POLYGON);
		gl.glVertex3d(p.x - 80, p.y - 80, 0);
		gl.glVertex3d(p.x + 80, p.y - 80, 0);
		gl.glVertex3d(p.x + 80, p.y + 80, 0);
		gl.glVertex3d(p.x - 80, p.y + 80, 0);
		gl.glEnd();
	    }
	    
	    int numberOfThread = 10;
	    int start = 0, end = -1;
	    int length = points.length/numberOfThread;
	    Thread []thr = new Thread[numberOfThread];
	    
	    for(int i = 0; i < numberOfThread; i++){
		start = end + 1;
		end = start + length;
		if(end >= points.length)
		    end = points.length - 1;
		DrawRunable dr = new DrawRunable(gl, points, start, end);
		thr[i] = new Thread(dr);
		thr[i].start();
	    }
	    
	    for(Thread t:thr){
		try {
		    t.join();
		} catch (InterruptedException ex) {
		    Logger.getLogger(Renderer.class.getName()).log(Level.SEVERE, null, ex);
		}
	    }
	    
	    

	    gl.glFlush();
	}

    }

    private void drawPoint(GL2 gl, ExtendPoint p) {
	gl.glColor3d(p.color.r, p.color.g, p.color.b);
	gl.glVertex3d(p.x, p.y, p.z);
    }

    public void displayChanged(GLAutoDrawable gLDrawable, boolean modeChanged, boolean deviceChanged) {
	System.out.println("displayChanged called");
    }

    @Override
    public void init(GLAutoDrawable gLDrawable) {
	System.out.println("init() called");
	GL2 gl = gLDrawable.getGL().getGL2();
	gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
	gl.glMatrixMode(GL2.GL_PROJECTION);
	//gl.glShadeModel(GL2.GL_FLAT);
	gl.glLoadIdentity();
	//gl.glPointSize(2);
	gl.glOrtho(100, 200, 100, 200, 100, 200);


    }

    @Override
    public void reshape(GLAutoDrawable gLDrawable, int x, int y, int width, int height) {
	System.out.println("reshape() called: x = " + x + ", y = " + y + ", width = " + width + ", height = " + height);
	final GL2 gl = gLDrawable.getGL().getGL2();

	gl.glViewport(0, 0, width, height);
	gl.glMatrixMode(GL2.GL_PROJECTION);
	gl.glLoadIdentity();
	gl.glOrtho(100, 200, 100, 200, -100, 200);
//	gl.glOrtho(5000, 50000, 5000, 50000, 5000, 50000);

	gl.glMatrixMode(GL2.GL_MODELVIEW);
	gl.glLoadIdentity();

    }

    @Override
    public void dispose(GLAutoDrawable arg0) {
	System.out.println("dispose() called");
    }

    class DrawRunable implements Runnable {

	private ExtendPoint[] points;
	private int start, end;
	private GL2 gl;

	public DrawRunable(GL2 gl, ExtendPoint[] points, int start, int end) {
	    this.gl = gl;
	    this.points = points;
	    this.start = start;
	    this.end = end;
	}

	@Override
	public void run() {
	    System.out.println("run: " + start + ", " + end);
	    gl.glBegin(GL2.GL_POINTS);
	    for (int i = start; i < end; i++) {
		System.out.println("point " + i + " = " + points[i].x);
		drawPoint(gl, points[i]);
	    }
	    gl.glEnd();
	   
	}
    }

}
