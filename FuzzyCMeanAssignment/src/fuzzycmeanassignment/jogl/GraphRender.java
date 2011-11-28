/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package fuzzycmeanassignment.jogl;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

/**
 *
 * @author kubin
 */
public class GraphRender implements GLEventListener{
     private GLU glu = new GLU();
    @Override
    public void init(GLAutoDrawable gLDrawable) {
	System.out.println("init() called");
        GL2 gl = gLDrawable.getGL().getGL2();
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glShadeModel(GL2.GL_FLAT);
    }

    @Override
    public void dispose(GLAutoDrawable glad) {
	GL2 gl2 = glad.getGL().getGL2();
	//throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void display(GLAutoDrawable gLDrawable) {
	final GL2 gl = gLDrawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        gl.glTranslatef(-1.5f, 0.0f, -6.0f);
        gl.glBegin(GL2.GL_TRIANGLES);		
        gl.glVertex3f(0.0f, 1.0f, 0.0f);	
        gl.glVertex3f(-1.0f, -1.0f, 0.0f);	
        gl.glVertex3f(1.0f, -1.0f, 0.0f);	
        gl.glEnd();				
        gl.glTranslatef(3.0f, 0.0f, 0.0f);
        gl.glBegin(GL2.GL_QUADS);           	
        gl.glVertex3f(-1.0f, 1.0f, 0.0f);	
        gl.glVertex3f(1.0f, 1.0f, 0.0f);	
        gl.glVertex3f(1.0f, -1.0f, 0.0f);	
        gl.glVertex3f(-1.0f, -1.0f, 0.0f);	
        gl.glEnd();				
        gl.glFlush();
    }

    @Override
    public void reshape(GLAutoDrawable gLDrawable, int x, int y, int width, int height) {
	System.out.println("reshape() called: x = "+x+", y = "+y+", width = "+width+", height = "+height);
        final GL2 gl = gLDrawable.getGL().getGL2();
 
        if (height <= 0) // avoid a divide by zero error!
        {
            height = 1;
        }
 
        final float h = (float) width / (float) height;
 
        gl.glViewport(0, 0, width, height);
	
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0f, h, 1.0, 20.0);
	
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }
    
}
