package com.abdmmar;

import com.sun.opengl.util.Animator;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.VK_A;
import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_L;
import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_RIGHT;
import static java.awt.event.KeyEvent.VK_S;
import static java.awt.event.KeyEvent.VK_SHIFT;
import static java.awt.event.KeyEvent.VK_UP;
import static java.awt.event.KeyEvent.VK_W;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

public class MonasIn3D extends GLCanvas implements  GLEventListener, KeyListener {
    private static float angleX = 0.0f; // rotational angle for x-axis in degree
    private static float angleY = 0.0f; // rotational angle for y-axis in degree
    private static float angleZ = 0.0f;
    private static float rotateSpeedX = 0.0f; // rotational speed for x-axis
    private static float rotateSpeedY = 0.0f; // rotational speed for y-axis
    private static float rotateSpeedZ = 0.0f; // rotational speed for y-axis
    private static float rotateSpeedXIncrement = 0.05f; // adjusting x rotational speed
    private static float rotateSpeedYIncrement = 0.05f; // adjusting y rotational speed
    private static float rotateSpeedZIncrement = 0.05f; // adjusting y rotational speed

    public static void main(String[] args) {
        Frame frame = new Frame("Monas 3D");
        GLCanvas canvas = new MonasIn3D();

        canvas.addGLEventListener(new MonasIn3D());
        frame.add(canvas);
        frame.setSize(1080, 1080);
        final Animator animator = new Animator(canvas);
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                // Run this on another thread than the AWT event queue to
                // make sure the call to Animator.stop() completes before
                // exiting
                new Thread(new Runnable() {

                    public void run() {
                        animator.stop();
                        System.exit(0);
                    }
                }).start();
            }
        });
        // Center frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        animator.start();
    }

    public MonasIn3D() {
        this.addGLEventListener(this);
        // For handling KeyEvents
        this.addKeyListener(this);
        this.setFocusable(true);
        this.requestFocus();
    }

    public void init(GLAutoDrawable drawable) {
        // Use debug pipeline
        // drawable.setGL(new DebugGL(drawable.getGL()));

        GL gl = drawable.getGL();
        System.err.println("INIT GL IS: " + gl.getClass().getName());

        // Enable VSync
        gl.setSwapInterval(1);

        // Setup the drawing area and shading mode
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glShadeModel(GL.GL_SMOOTH); // try setting this to GL_FLAT and see what happens.
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL gl = drawable.getGL();
        GLU glu = new GLU();

        if (height <= 0) { // avoid a divide by zero error!
        
            height = 1;
        }
        final float h = (float) width / (float) height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0f, h, 1.0, 20.0);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();

        // Clear the drawing area
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        // Reset the current matrix to the "identity"
        gl.glLoadIdentity();
        
        gl.glShadeModel( GL.GL_SMOOTH );
        gl.glClearColor( 0f, 0f, 0f, 0f );
        gl.glClearDepth( 1.0f );
        gl.glEnable( GL.GL_DEPTH_TEST );
        gl.glDepthFunc( GL.GL_LEQUAL );
        gl.glHint( GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST );
        // Clear The Screen And The Depth Buffer
        gl.glClear( GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT );     
        gl.glLoadIdentity();  // Reset The View

        // Move the "drawing cursor" around
        gl.glTranslatef( 0f, 0f, -5.0f );
        gl.glRotatef( angleX, 1.0f, 0.0f, 0.0f );  
        gl.glRotatef( angleY, 0.0f, 1.0f, 0.0f );  
        gl.glRotatef( angleZ, 0.0f, 0.0f, 1.0f );  
        gl.glBegin( GL.GL_QUADS );
        
        base(gl);
        baseTopBottom(gl);
        baseTopTop(gl);
        
        body(gl);
        bodyTop(gl);
        
        head(gl);
        headTop(gl);
        
        goldBase(gl);
        gold(gl);
        
        
        gl.glEnd();
        
        angleX += rotateSpeedX;
        angleY += rotateSpeedY;
        angleZ += rotateSpeedZ;

        // Flush all drawing operations to the graphics card
        gl.glFlush();
    }
    
    public void orangeColor(GL gl) {
        //BACK
        gl.glColor3f(1.f, 0.8f, 0.0f); // orange
    }
    
    public void violetColor(GL gl) {
        //LEFT
        gl.glColor3f(0.5f, 0.0f, 1.0f); // violet
    }

    public void blueColor(GL gl) {
        //RIGHT
        gl.glColor3f(0.0f, 0.0f, 0.8f); // blue
    }

    public void greenColor(GL gl) {
        //FRONT
        gl.glColor3f(0.0f, 0.9f, 0.0f); // green
    }
    
    public void redColor(GL gl) {
        //TOP
        gl.glColor3f(0.5f, 0.0f, 1.0f); // red
    }
    
    public void whiteColor(GL gl){
        gl.glColor3f(1.0f, 1.0f, 0.9f); // white
    }
    
    public void creamColor(GL gl){
        gl.glColor3f(1.0f, 1.0f, 0.7f); // white
    }
    
    public void yellowColor(GL gl) {
        //BOTTOM
        gl.glColor3f(1.0f, 1.0f, 0.0f); // yellow
    }
    
    public void gold(GL gl){
        // FRONT
        orangeColor(gl);
        gl.glVertex3f(-0.04f, 1.2f, 0.04f);  
        gl.glVertex3f(0.04f, 1.2f, 0.04f);  
        gl.glVertex3f(0.03f, 1.12f, 0.03f);   
        gl.glVertex3f(-0.03f, 1.12f, 0.03f);  
        
        gl.glVertex3f(-0.04f, 1.2f, 0.04f);  
        gl.glVertex3f(0.04f, 1.2f, 0.04f);  
        gl.glVertex3f(0.0f, 1.35f, 0.0f);   
        gl.glVertex3f(0.0f, 1.35f, 0.0f);  
        
        orangeColor(gl);
        gl.glVertex3f(-0.04f, 1.2f, -0.04f);  
        gl.glVertex3f(0.04f, 1.2f, -0.04f);  
        gl.glVertex3f(0.03f, 1.12f, -0.03f);   
        gl.glVertex3f(-0.03f, 1.12f, -0.03f);  
        
        gl.glVertex3f(-0.04f, 1.2f, -0.04f);  
        gl.glVertex3f(0.04f, 1.2f, -0.04f);  
        gl.glVertex3f(0.0f, 1.35f, -0.0f);   
        gl.glVertex3f(0.0f, 1.35f, -0.0f);  
        
        //RIGHT
        orangeColor(gl);
        gl.glVertex3f(0.04f, 1.2f, -0.04f);  
        gl.glVertex3f(0.04f, 1.2f, 0.04f);  
        gl.glVertex3f(0.03f, 1.12f, 0.03f);   
        gl.glVertex3f(0.03f, 1.12f, -0.03f);  
        
        gl.glVertex3f(0.04f, 1.2f, -0.04f);  
        gl.glVertex3f(0.04f, 1.2f, 0.04f);  
        gl.glVertex3f(0.0f, 1.35f, 0.0f);   
        gl.glVertex3f(0.0f, 1.35f, -0.0f);  
        
        //LEFT
        orangeColor(gl);
        gl.glVertex3f(-0.04f, 1.2f, -0.04f);  
        gl.glVertex3f(-0.04f, 1.2f, 0.04f);  
        gl.glVertex3f(-0.03f, 1.12f, 0.03f);   
        gl.glVertex3f(-0.03f, 1.12f, -0.03f);  
        
        gl.glVertex3f(-0.04f, 1.2f, -0.04f);  
        gl.glVertex3f(-0.04f, 1.2f, 0.04f);  
        gl.glVertex3f(-0.0f, 1.35f, 0.0f);   
        gl.glVertex3f(-0.0f, 1.35f, -0.0f);  

    }
    
    public void goldBase(GL gl){
        whiteColor(gl);
        gl.glVertex3f(-0.1f, 1.1f, 0.05f);  
        gl.glVertex3f(0.1f, 1.1f, 0.05f);  
        gl.glVertex3f(0.12f, 1.12f, 0.08f);   
        gl.glVertex3f(-0.12f, 1.12f, 0.08f);  
        
        //BACK
        whiteColor(gl);
        gl.glVertex3f(-0.1f, 1.1f, -0.05f);  
        gl.glVertex3f(0.1f, 1.1f, -0.05f);  
        gl.glVertex3f(0.12f, 1.12f, -0.08f);   
        gl.glVertex3f(-0.12f, 1.12f, -0.08f);  
        
        whiteColor(gl);
        gl.glVertex3f(0.1f, 1.1f, -0.05f);  
        gl.glVertex3f(0.1f, 1.1f, 0.05f);  
        gl.glVertex3f(0.12f, 1.12f, 0.08f);   
        gl.glVertex3f(0.12f, 1.12f, -0.08f);  
        
        whiteColor(gl);
        gl.glVertex3f(-0.1f, 1.1f, -0.05f);  
        gl.glVertex3f(-0.1f, 1.1f, 0.05f);  
        gl.glVertex3f(-0.12f, 1.12f, 0.08f);   
        gl.glVertex3f(-0.12f, 1.12f, -0.08f);  
        
        //TOP
        whiteColor(gl);
        gl.glVertex3f(-0.12f, 1.12f, -0.08f);  
        gl.glVertex3f(-0.12f, 1.12f, 0.08f);  
        gl.glVertex3f(0.12f, 1.12f, 0.08f);   
        gl.glVertex3f(0.12f, 1.12f, -0.08f);  
        
        
    }
    
    public void headTop(GL gl){
        //FRONT
        creamColor(gl);
        gl.glVertex3f(0.12f, 1.05f, 0.12f);   
        gl.glVertex3f(-0.12f, 1.05f, 0.12f);  
        gl.glVertex3f(-0.1f, 1.1f, 0.05f);  
        gl.glVertex3f(0.1f, 1.1f, 0.05f);  
        
        //BACK
        creamColor(gl);
        gl.glVertex3f(0.12f, 1.05f, -0.12f);   
        gl.glVertex3f(-0.12f, 1.05f, -0.12f);  
        gl.glVertex3f(-0.1f, 1.1f, -0.05f);  
        gl.glVertex3f(0.1f, 1.1f, -0.05f);  
        
        //RIGHT
        creamColor(gl);
        gl.glVertex3f(0.12f, 1.05f, -0.12f);   
        gl.glVertex3f(0.12f, 1.05f, 0.12f);  
        gl.glVertex3f(0.1f, 1.1f, 0.05f);  
        gl.glVertex3f(0.1f, 1.1f, -0.05f);  
        
        //LEFT
        creamColor(gl);
        gl.glVertex3f(-0.12f, 1.05f, -0.12f);   
        gl.glVertex3f(-0.12f, 1.05f, 0.12f);  
        gl.glVertex3f(-0.1f, 1.1f, 0.05f);  
        gl.glVertex3f(-0.1f, 1.1f, -0.05f);  

    }
    
    public void head(GL gl){
        //FRONT
        whiteColor(gl);
        gl.glVertex3f(-0.1f, 1.03f, 0.1f);  
        gl.glVertex3f(0.1f, 1.03f, 0.1f);  
        gl.glVertex3f(0.12f, 1.05f, 0.12f);   
        gl.glVertex3f(-0.12f, 1.05f, 0.12f);  
        
        //BACK
        gl.glVertex3f(-0.1f, 1.03f, -0.1f);  
        gl.glVertex3f(0.1f, 1.03f, -0.1f);  
        gl.glVertex3f(0.12f, 1.05f, -0.12f);   
        gl.glVertex3f(-0.12f, 1.05f, -0.12f);  
        
        //RIGHT
        gl.glVertex3f(0.1f, 1.03f, -0.1f);  
        gl.glVertex3f(0.1f, 1.03f, 0.1f);  
        gl.glVertex3f(0.12f, 1.05f, 0.12f);   
        gl.glVertex3f(0.12f, 1.05f, -0.12f);  
        
        //LEFT
        gl.glVertex3f(-0.1f, 1.03f, 0.1f);  
        gl.glVertex3f(-0.1f, 1.03f, -0.1f);  
        gl.glVertex3f(-0.12f, 1.05f, -0.12f);   
        gl.glVertex3f(-0.12f, 1.05f, 0.12f);  
        
    }
    
    private void bodyTop(GL gl) {
        // FRONT
        orangeColor(gl);
        gl.glVertex3f(-0.05f, 1f, 0.05f);  
        gl.glVertex3f(0.05f, 1f, 0.05f);  
        gl.glVertex3f(0.1f, 1.03f, 0.1f);   
        gl.glVertex3f(-0.1f, 1.03f, 0.1f);  
        
        //BACK
        
        gl.glVertex3f(-0.05f, 1f, -0.05f);  
        gl.glVertex3f(0.05f, 1f, -0.05f);  
        gl.glVertex3f(0.1f, 1.03f, -0.1f);   
        gl.glVertex3f(-0.1f, 1.03f, -0.1f);  
        
        //RIGHT
        
        gl.glVertex3f(0.05f, 1f, -0.05f);  
        gl.glVertex3f(0.05f, 1f, 0.05f);  
        gl.glVertex3f(0.1f, 1.03f, 0.1f);   
        gl.glVertex3f(0.1f, 1.03f, -0.1f);  
        
        //LEFT
        
        gl.glVertex3f(-0.05f, 1f, -0.05f);  
        gl.glVertex3f(-0.05f, 1f, 0.05f);  
        gl.glVertex3f(-0.1f, 1.03f, 0.1f);   
        gl.glVertex3f(-0.1f, 1.03f, -0.1f);  
        
    }
    
    public void body(GL gl){
        // FRONT
        whiteColor(gl);
        gl.glVertex3f(-0.1f, -0.7f, 0.1f);  
        gl.glVertex3f(0.1f, -0.7f, 0.1f);  
        gl.glVertex3f(0.05f, 1f, 0.05f);   
        gl.glVertex3f(-0.05f, 1f, 0.05f);  
        
        //BACK
        whiteColor(gl);
        gl.glVertex3f(-0.1f, -0.7f, -0.1f);  
        gl.glVertex3f(0.1f, -0.7f, -0.1f);  
        gl.glVertex3f(0.05f, 1f, -0.05f);   
        gl.glVertex3f(-0.05f, 1f, -0.05f);  
        
        //TOP
        whiteColor(gl);
        gl.glVertex3f(-0.05f, 1f, -0.05f);  
        gl.glVertex3f(-0.05f, 1f, 0.05f);  
        gl.glVertex3f(0.05f, 1f, 0.05f);   
        gl.glVertex3f(0.05f, 1f, -0.05f);  
        
        //BOTTOM
        whiteColor(gl);
        gl.glVertex3f(-0.1f, -0.7f, -0.1f);  
        gl.glVertex3f(0.1f, -0.7f, -0.1f);  
        gl.glVertex3f(0.1f, -0.7f, 0.1f);   
        gl.glVertex3f(-0.1f, -0.7f, 0.1f);  
        
        //RIGHT
        whiteColor(gl);
        gl.glVertex3f(0.1f, -0.7f, -0.1f);  
        gl.glVertex3f(0.1f, -0.7f, 0.1f);  
        gl.glVertex3f(0.05f, 1f, 0.05f);   
        gl.glVertex3f(0.05f, 1f, -0.05f);  
        
        //LEFT
        whiteColor(gl);
        gl.glVertex3f(-0.1f, -0.7f, 0.1f);  
        gl.glVertex3f(-0.1f, -0.7f, -0.1f);  
        gl.glVertex3f(-0.05f, 1f, -0.05f);   
        gl.glVertex3f(-0.05f, 1f, 0.05f);  
        
    }
    
    public void base(GL gl){
        //BASE
        // FRONT
        orangeColor(gl);
        gl.glVertex3f(-0.2f, -1.0f, 0.2f);  
        gl.glVertex3f(0.2f, -1.0f, 0.2f);  
        gl.glVertex3f(0.4f, -0.8f, 0.4f);   
        gl.glVertex3f(-0.4f, -0.8f, 0.4f);  
        
        // BACK
        orangeColor(gl);
        gl.glVertex3f(-0.2f, -1.0f, -0.2f);  
        gl.glVertex3f(0.2f, -1.0f, -0.2f);  
        gl.glVertex3f(0.4f, -0.8f, -0.4f);   
        gl.glVertex3f(-0.4f, -0.8f, -0.4f);  
        
        // TOP
        orangeColor(gl);
        gl.glVertex3f(-0.4f, -0.8f, -0.4f);
        gl.glVertex3f(-0.4f, -0.8f, 0.4f);
        gl.glVertex3f(0.4f, -0.8f, 0.4f);
        gl.glVertex3f(0.4f, -0.8f, -0.4f);
        
        orangeColor(gl);
        gl.glVertex3f(-0.2f, -1.0f, -0.2f);
        gl.glVertex3f(0.2f, -1.0f, -0.2f);
        gl.glVertex3f(0.2f, -1.0f, 0.2f);
        gl.glVertex3f(-0.2f, -1.0f, 0.2f);
        
        // RIGHT
        orangeColor(gl);
        gl.glVertex3f(0.2f, -1.0f, -0.2f);
        gl.glVertex3f(0.2f, -1.0f, 0.2f);
        gl.glVertex3f(0.4f, -0.8f, 0.4f);
        gl.glVertex3f(0.4f, -0.8f, -0.4f);
        
        // LEFT
        orangeColor(gl);
        gl.glVertex3f(-0.2f, -1.0f, 0.2f);
        gl.glVertex3f(-0.2f, -1.0f, -0.2f);
        gl.glVertex3f(-0.4f, -0.8f, -0.4f);
        gl.glVertex3f(-0.4f, -0.8f, 0.4f);
      
        //BASE - END
    }
    
    public void baseTopBottom(GL gl){
        //BASETOP BOTTOM SECTION
        //FRONT
        creamColor(gl);
        gl.glVertex3f(0.4f, -0.8f, 0.4f);   
        gl.glVertex3f(-0.4f, -0.8f, 0.4f);
        gl.glVertex3f(-0.5f, -0.75f, 0.45f);
        gl.glVertex3f(0.5f, -0.75f, 0.45f);
        
        //BACK
        creamColor(gl);
        gl.glVertex3f(0.4f, -0.8f, -0.4f);   
        gl.glVertex3f(-0.4f, -0.8f, -0.4f);  
        gl.glVertex3f(-0.5f, -0.75f, -0.45f);
        gl.glVertex3f(0.5f, -0.75f, -0.45f);
        
        //BOTTOM
        creamColor(gl);
        gl.glVertex3f(-0.4f, -0.8f, -0.4f);
        gl.glVertex3f(-0.4f, -0.8f, 0.4f);
        gl.glVertex3f(0.4f, -0.8f, 0.4f);
        gl.glVertex3f(0.4f, -0.8f, -0.4f);
        
        // RIGHT
        creamColor(gl);
        gl.glVertex3f(0.4f, -0.8f, -0.4f);
        gl.glVertex3f(0.4f, -0.8f, 0.4f);
        gl.glVertex3f(0.5f, -0.75f, 0.45f);
        gl.glVertex3f(0.5f, -0.75f, -0.45f);
        
        // LEFT
        creamColor(gl);
        gl.glVertex3f(-0.4f, -0.8f, 0.4f);
        gl.glVertex3f(-0.4f, -0.8f, -0.4f);
        gl.glVertex3f(-0.5f, -0.75f, -0.45f);
        gl.glVertex3f(-0.5f, -0.75f, 0.45f);
    }
    
    public void baseTopTop(GL gl){
        //BASETOP TOP SECTION
        //FRONT
        creamColor(gl);
        gl.glVertex3f(0.4f, -0.7f, 0.4f);
        gl.glVertex3f(-0.4f, -0.7f, 0.4f);
        gl.glVertex3f(-0.5f, -0.75f, 0.45f);
        gl.glVertex3f(0.5f, -0.75f, 0.45f);   
        
        //BACK
        creamColor(gl);
        gl.glVertex3f(0.4f, -0.7f, -0.4f);
        gl.glVertex3f(-0.4f, -0.7f, -0.4f);
        gl.glVertex3f(-0.5f, -0.75f, -0.45f);
        gl.glVertex3f(0.5f, -0.75f, -0.45f);
        
        // RIGHT
        creamColor(gl);
        gl.glVertex3f(0.5f, -0.75f, -0.45f);
        gl.glVertex3f(0.5f, -0.75f, 0.45f);
        gl.glVertex3f(0.4f, -0.7f, 0.4f);
        gl.glVertex3f(0.4f, -0.7f, -0.4f);
        
        // LEFT
        creamColor(gl);
        gl.glVertex3f(-0.5f, -0.75f, 0.45f);
        gl.glVertex3f(-0.5f, -0.75f, -0.45f);
        gl.glVertex3f(-0.4f, -0.7f, -0.4f);
        gl.glVertex3f(-0.4f, -0.7f, 0.4f);
        
        //TOP
        whiteColor(gl);
        gl.glVertex3f(0.4f, -0.7f, -0.4f);
        gl.glVertex3f(-0.4f, -0.7f, -0.4f);
        gl.glVertex3f(-0.4f, -0.7f, 0.4f);
        gl.glVertex3f(0.4f, -0.7f, 0.4f);
        
    }
    
    @Override
    public void keyPressed(KeyEvent ke) {
        int keyCode = ke.getKeyCode();
        switch (keyCode) {
            case VK_UP:   // decrease rotational speed in x
                rotateSpeedX -= rotateSpeedXIncrement;
                break;
            case VK_DOWN: // increase rotational speed in x
                rotateSpeedX += rotateSpeedXIncrement;
                break;
            case VK_LEFT:  // decrease rotational speed in y
                rotateSpeedY -= rotateSpeedYIncrement;
                break;
            case VK_RIGHT: // increase rotational speed in y
                rotateSpeedY += rotateSpeedYIncrement;
                break;
            case VK_W:
                rotateSpeedZ -= rotateSpeedZIncrement;
                break;
            case VK_S:
                rotateSpeedZ += rotateSpeedZIncrement;
                break;
            case VK_SHIFT : // increase rotational speed in y
                rotateSpeedY = 0;
                rotateSpeedX = 0;
                rotateSpeedZ = 0;
                break;

        }
    }
    
    @Override
    public void keyTyped(KeyEvent ke) {
    }
    
    @Override
    public void keyReleased(KeyEvent ke) {
    }
    
    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }
}

