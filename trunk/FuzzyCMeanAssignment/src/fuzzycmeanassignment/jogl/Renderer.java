/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package fuzzycmeanassignment.jogl;

import fuzzycmeanassignment.algorithm.ExtendPoint;
import fuzzycmeanassignment.algorithm.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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

    public Renderer() {
    }

    public Renderer(GLCanvas canvas) {
	this.canvas = canvas;
    }

    public void reset(ExtendPoint[] ps, ExtendPoint[] cs, Point min, Point max) {
	System.out.println("reset : point length = " + ps.length);
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
//
//	gl.glColor3d(1.0, 1.0, 1.0);
//	gl.glBegin(GL2.GL_POLYGON);
//	gl.glVertex3d(100, 100, 0.0);
//	gl.glVertex3d(150, 100, 0.0);
//	gl.glVertex3d(150, 150, 0.0);
//	gl.glVertex3d(100, 150, 0.0);
//	gl.glEnd();


//	gl.glLineWidth(3);
	if (points != null) {
	    System.out.println("display with points");
	    gl.glOrtho(pMin.x, pMax.x, pMin.y, pMax.y, -100, 100);
	    System.out.println(pMin.x + ", " + pMax.x + ", " + pMin.y + ", " + pMax.y + ", " + pMin.z + ", " + pMax.z);
	    gl.glBegin(GL2.GL_POINTS);
	    for (ExtendPoint p : points) {
		drawPoint(gl, p);
	    }
	    gl.glEnd();
	    gl.glFlush();
	}


//	gl.glBegin(GL2.GL_POINTS);
//	gl.glVertex3d(150, 150, 0);
//	gl.glVertex3d(160, 150, 0);
//	gl.glEnd();


//	gl.glFlush();
    }

    private void drawPoint(GL2 gl, ExtendPoint p) {
	System.out.println("drawPoint " + p.x + ", " + p.y + ", " + p.z);
	gl.glColor3d(p.color.r, p.color.g, p.color.b);
	gl.glVertex3d(p.x, p.y, 0);
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
	gl.glPointSize(2);
	//gl.glOrtho(100, 200, 100, 200, 100, 200);


    }

    @Override
    public void reshape(GLAutoDrawable gLDrawable, int x, int y, int width, int height) {
	System.out.println("reshape() called: x = " + x + ", y = " + y + ", width = " + width + ", height = " + height);
	final GL2 gl = gLDrawable.getGL().getGL2();

	gl.glViewport(0, 0, width, height);
	gl.glMatrixMode(GL2.GL_PROJECTION);
	gl.glLoadIdentity();
	gl.glOrtho(100, 200, 100, 200, -100, 100);

	gl.glMatrixMode(GL2.GL_MODELVIEW);
	gl.glLoadIdentity();

    }

    @Override
    public void dispose(GLAutoDrawable arg0) {
	System.out.println("dispose() called");
    }

    public static void main(String[] args) {
	// setup OpenGL Version 2
	GLProfile profile = GLProfile.get(GLProfile.GL2);
	GLCapabilities capabilities = new GLCapabilities(profile);

	// The canvas is the widget that's drawn in the JFrame
	GLCanvas glcanvas = new GLCanvas(capabilities);
	glcanvas.addGLEventListener(new Renderer() {
	});
	glcanvas.setSize(300, 300);

	JFrame frame = new JFrame("Hello World");
	frame.getContentPane().add(glcanvas);

	// shutdown the program on windows close event
	frame.addWindowListener(new WindowAdapter() {

	    @Override
	    public void windowClosing(WindowEvent ev) {
		System.exit(0);
	    }
	});

	frame.setSize(frame.getContentPane().getPreferredSize());
	frame.setVisible(true);
    }
}
