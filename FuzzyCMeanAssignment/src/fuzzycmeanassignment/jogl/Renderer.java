/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package fuzzycmeanassignment.jogl;

import fuzzycmeanassignment.algorithm.ExtendPoint;
import fuzzycmeanassignment.algorithm.Point;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;

/**
 *
 * @author kubin
 */
public class Renderer implements GLEventListener, KeyListener, MouseMotionListener, MouseListener {

    private GLU glu = new GLU();
    private ExtendPoint[] points;
    private ExtendPoint[] clusters;
    private Point pMin, pMax;
    private GLCanvas canvas;
    private int width, height;
    private int angle0, angle1;
    private double DEG2RAD = Math.PI / 180.0;
    private Point cCenter;
    private Point cPos;
    private int mouseX, mouseY;
    

    public Renderer() {
    }

    public Renderer(GLCanvas canvas) {
	this.canvas = canvas;
	cCenter = new Point();
	cPos = new Point();
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
	final GLU glu = new GLU();
	if (cCenter == null) {
	    cCenter = new Point();
	    cPos = new Point();
	}
	gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);


	if (points != null) {
	    gl.glMatrixMode(GL2.GL_PROJECTION);
	    gl.glLoadIdentity();
	    double min, max;
	    min = (pMin.x > pMin.y ? pMin.y : pMin.x);
	    max = (pMax.x > pMax.y ? pMax.x : pMax.y);

	    cCenter.x = (pMin.x + pMax.x) / 2.0;
	    cCenter.y = (pMin.y + pMax.y) / 2.0;
	    cCenter.z = (pMin.z + pMax.z) / 2.0;
	    double r = pMin.distance(pMax) / 2.0;
	    cPos.x = cCenter.x + r * Math.sin(angle0 * DEG2RAD) * Math.cos(angle1 * DEG2RAD);
	    cPos.y = cCenter.y + r * Math.sin(angle0 * DEG2RAD) * Math.sin(angle1 * DEG2RAD);
	    cPos.z = cCenter.z + r * Math.cos(angle0 * DEG2RAD);

	    gl.glOrtho(min, max, min, max, -100000, 100000);
	    //glu.gluLookAt(cPos.x, cPos.y, cPos.z, cCenter.x, cCenter.y, cCenter.z, 0, 1, 0);

	    gl.glPushMatrix();

	    gl.glTranslated(cCenter.x, cCenter.y, cCenter.z);
	    gl.glRotatef((float) (angle0 * DEG2RAD), 1, 0, 0);
	    gl.glRotatef((float) (angle1 * DEG2RAD), 0, 1, 0);
	    gl.glTranslated(-cCenter.x, -cCenter.y, -cCenter.z);

	    drawClusters(gl, max, min);
	    drawPoints1(gl);
	    gl.glPopMatrix();
	    gl.glFlush();
	}

    }

    private void drawClusters(final GL2 gl, double max, double min) {
	double size = 3.0 / width * (max - min);
	System.out.println("min = " + min + ",    max = " + max);
	System.out.println("size = " + size);
	for (ExtendPoint p : clusters) {
	    gl.glColor3d(p.color.r, p.color.g, p.color.b);
	    gl.glBegin(GL2.GL_POLYGON);

	    gl.glVertex3d(p.x - size, p.y - size, p.z);
	    gl.glVertex3d(p.x + size, p.y - size, p.z);
	    gl.glVertex3d(p.x + size, p.y + size, p.z);
	    gl.glVertex3d(p.x - size, p.y + size, p.z);
	    gl.glEnd();
	}
    }

    private void drawPoints1(final GL2 gl) {
	gl.glBegin(GL2.GL_POINTS);
	for (ExtendPoint p : points) {
	    drawPoint(gl, p);
	}
	gl.glEnd();
    }

    private void drawPointsN(final GL2 gl) {
	int numberOfThread = 10;
	int start = 0, end = -1;
	int length = points.length / numberOfThread;
	Thread[] thr = new Thread[numberOfThread];

	for (int i = 0; i < numberOfThread; i++) {
	    start = end + 1;
	    end = start + length;
	    if (end >= points.length) {
		end = points.length - 1;
	    }
	    DrawRunable dr = new DrawRunable(gl, points, start, end);
	    thr[i] = new Thread(dr);
	    thr[i].start();
	}

	for (Thread t : thr) {
	    try {
		t.join();
	    } catch (InterruptedException ex) {
		Logger.getLogger(Renderer.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
	gl.glFlush();
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
	gl.glEnable(GL2.GL_DEPTH_TEST);


    }
    
    public void reset(){
	angle0 = 0;
	angle1 = 0;
	canvas.display();
    }
    
    @Override
    public void reshape(GLAutoDrawable gLDrawable, int x, int y, int width, int height) {
	System.out.println("reshape() called: x = " + x + ", y = " + y + ", width = " + width + ", height = " + height);
	final GL2 gl = gLDrawable.getGL().getGL2();
	this.width = width;
	this.height = height;
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

    @Override
    public void keyTyped(KeyEvent e) {
	//throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void keyPressed(KeyEvent e) {
	//throw new UnsupportedOperationException("Not supported yet.");
	System.out.println("keyPress");
	switch (e.getKeyCode()) {
	    case KeyEvent.VK_UP:
		System.out.println("UP");
		angle0 += 60;
		break;
	    case KeyEvent.VK_DOWN:
		System.out.println("DOWN");
		angle0 -= 60;
		break;
	    case KeyEvent.VK_LEFT:
		System.out.println("LEFT");
		angle1 += 60;
		break;
	    case KeyEvent.VK_RIGHT:
		System.out.println("RIGHT");
		angle1 -= 60;
		break;
	    default:
		System.out.println("NONE");
	}
	canvas.display();
    }

    @Override
    public void keyReleased(KeyEvent e) {
	//throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseDragged(java.awt.event.MouseEvent e) {
	int x = e.getX();
	int y = e.getY();
	System.out.println("Mouse Drag");
	angle0 += x - mouseX;
	angle1 += y - mouseY;
	canvas.display();
    }

    @Override
    public void mouseMoved(java.awt.event.MouseEvent e) {
	//throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseClicked(MouseEvent e) {
	//throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mousePressed(MouseEvent e) {
	System.out.println("Mouse Press");
	mouseX = e.getX();
	mouseY = e.getY();
	
    }

    @Override
    public void mouseReleased(MouseEvent e) {
	//throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
	//throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseExited(MouseEvent e) {
	//throw new UnsupportedOperationException("Not supported yet.");
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
	    //System.out.println("run: " + start + ", " + end);
	    gl.glBegin(GL2.GL_POINTS);
	    for (int i = start; i < end; i++) {
		//System.out.println("point " + i + " = " + points[i].x);
		drawPoint(gl, points[i]);
	    }
	    gl.glEnd();

	}
    }
}
