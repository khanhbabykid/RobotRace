package robotrace;

import static java.lang.Math.atan2;
import static java.lang.Math.toDegrees;

import static javax.media.opengl.GL2.*;
import static javax.media.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;
import static javax.media.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;

/**
 * Handles all of the RobotRace graphics functionality, which should be extended
 * per the assignment.
 *
 * OpenGL functionality: - Basic commands are called via the gl object; -
 * Utility commands are called via the glu and glut objects;
 *
 * GlobalState: The gs object contains the GlobalState as described in the
 * assignment: - The camera viewpoint angles, phi and theta, are changed
 * interactively by holding the left mouse button and dragging; - The camera
 * view width, vWidth, is changed interactively by holding the right mouse
 * button and dragging upwards or downwards; - The center point can be moved up
 * and down by pressing the 'q' and 'z' keys, forwards and backwards with the
 * 'w' and 's' keys, and left and right with the 'a' and 'd' keys; - Other
 * settings are changed via the menus at the top of the screen.
 *
 * Textures: Place your "track.jpg", "brick.jpg", "head.jpg", and "torso.jpg"
 * files in the same folder as this file. These will then be loaded as the
 * texture objects track, bricks, head, and torso respectively. Be aware, these
 * objects are already defined and cannot be used for other purposes. The
 * texture objects can be used as follows:
 *
 * gl.glColor3f(1f, 1f, 1f); track.bind(gl); gl.glBegin(GL_QUADS);
 * gl.glTexCoord2d(0, 0); gl.glVertex3d(0, 0, 0); gl.glTexCoord2d(1, 0);
 * gl.glVertex3d(1, 0, 0); gl.glTexCoord2d(1, 1); gl.glVertex3d(1, 1, 0);
 * gl.glTexCoord2d(0, 1); gl.glVertex3d(0, 1, 0); gl.glEnd();
 *
 * Note that it is hard or impossible to texture objects drawn with GLUT. Either
 * define the primitives of the object yourself (as seen above) or add
 * additional textured primitives to the GLUT object.
 */
public class RobotRace extends Base {

    /**
     * Array of the four robots.
     */
    private final Robot[] robots;

    /**
     * Instance of the camera.
     */
    private final Camera camera;

    /**
     * Instance of the race track.
     */
    private final RaceTrack[] raceTracks;

    /**
     * Instance of the terrain.
     */
    private final Terrain terrain;

    /**
     * Constructs this robot race by initializing robots, camera, track, and
     * terrain.
     */
    public RobotRace() {

        // Create a new array of four robots
        robots = new Robot[4];

        // Initialize robot 0
        robots[0] = new Robot(Material.GOLD, Vector.O);

        // Initialize robot 1
        robots[1] = new Robot(Material.SILVER, new Vector(1.2, 0, 0));

        // Initialize robot 2
        robots[2] = new Robot(Material.WOOD, new Vector(2.4, 0, 0));

        // Initialize robot 3
        robots[3] = new Robot(Material.ORANGE, new Vector(3.6, 0, 0));

        // Initialize the camera
        camera = new Camera();

        // Initialize the race tracks
        raceTracks = new RaceTrack[5];

        // Test track
        raceTracks[0] = new RaceTrack();

        // O-track
        raceTracks[1] = new RaceTrack(new Vector[]{ /* add control points like:
         new Vector(10, 0, 1), new Vector(10, 5, 1), new Vector(5, 10, 1),
         new Vector(..., ..., ...), ...
         */});

        // L-track
        raceTracks[2] = new RaceTrack(new Vector[]{ /* add control points */});

        // C-track
        raceTracks[3] = new RaceTrack(new Vector[]{ /* add control points */});

        // Custom track
        raceTracks[4] = new RaceTrack(new Vector[]{ /* add control points */});

        // Initialize the terrain
        terrain = new Terrain();
    }

    /**
     * Called upon the start of the application. Primarily used to configure
     * OpenGL.
     */
    @Override
    public void initialize() {

        // Enable blending.
        gl.glEnable(GL_BLEND);
        gl.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        // Enable depth testing.
        gl.glEnable(GL_DEPTH_TEST);
        gl.glDepthFunc(GL_LESS);

        // Normalize normals.
        gl.glEnable(GL_NORMALIZE);

        // Enable textures. 
        gl.glEnable(GL_TEXTURE_2D);
        gl.glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
        gl.glBindTexture(GL_TEXTURE_2D, 0);

        // Enable light 0 and lighting
        gl.glEnable(GL_LIGHT0);
        gl.glEnable(GL_LIGHTING);
        
        // Try to load four textures, add more if you like.
        track = loadTexture("track.jpg");
        brick = loadTexture("brick.jpg");
        head = loadTexture("head.jpg");
        torso = loadTexture("torso.jpg");
    }

    /**
     * Configures the viewing transform.
     */
    @Override
    public void setView() {
        // Select part of window.
        gl.glViewport(0, 0, gs.w, gs.h);

        // Set projection matrix.
        gl.glMatrixMode(GL_PROJECTION);
        gl.glLoadIdentity();

        //Set the perspective.
        //The angle is calculated using the distance between the eye point and the center point
        //and the width given by the assignment
        double fovy = toDegrees(atan2(gs.vWidth / 2, gs.vDist)) * 2;
        glu.gluPerspective((int) fovy, (float) gs.w / (float) gs.h, 0.1 * gs.vDist, 10.0 * gs.vDist);

        // Set camera.
        gl.glMatrixMode(GL_MODELVIEW);
        gl.glLoadIdentity();

        // Update the view according to the camera mode and robot of interest.
        // For camera modes 1 to 4, determine which robot to focus on.
        camera.update(gs, robots[0]);
        glu.gluLookAt(camera.eye.x(), camera.eye.y(), camera.eye.z(),
                camera.center.x(), camera.center.y(), camera.center.z(),
                camera.up.x(), camera.up.y(), camera.up.z());
    }

    /**
     * Draws the entire scene.
     */
    @Override
    public void drawScene() {
        // Background color.
        gl.glClearColor(1f, 1f, 1f, 0f);

        // Clear background.
        gl.glClear(GL_COLOR_BUFFER_BIT);

        // Clear depth buffer.
        gl.glClear(GL_DEPTH_BUFFER_BIT);

        // Set color to black.
        gl.glColor3f(0f, 0f, 0f);

        gl.glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);

        // Draw the axis frame.
        if (gs.showAxes) {
            gl.glEnable(GL_COLOR_MATERIAL); //Enable color for axis frame
            drawAxisFrame();
            gl.glDisable(GL_COLOR_MATERIAL); //Disable color after drawing
        }

        // Get the position and direction of the first robot.
        robots[0].position = raceTracks[gs.trackNr].getLanePoint(0, 0);
        robots[0].direction = raceTracks[gs.trackNr].getLaneTangent(0, 0);

        // Draw the first robot.
        robots[0].draw(gl, glu, glut, gs.showStick, gs.tAnim);
        robots[1].draw(gl, glu, glut, gs.showStick, gs.tAnim);
        robots[2].draw(gl, glu, glut, gs.showStick, gs.tAnim);
        robots[3].draw(gl, glu, glut, gs.showStick, gs.tAnim);

        // Draw the race track.
        raceTracks[gs.trackNr].draw(gl, glu, glut);

        // Draw the terrain.
        terrain.draw(gl, glu, glut);

    }

    /**
     * Draws the x-axis (red), y-axis (green), z-axis (blue), and origin
     * (yellow).
     */
    public void drawAxisFrame() {

        int axisLength = 1;
        // Draw axis by separate lines
        gl.glBegin(GL_LINES);
        // draw X
        gl.glColor3f(1, 0, 0);
        gl.glVertex3f(0, 0, 0);
        gl.glVertex3f(axisLength, 0, 0);

        // draw Y
        gl.glColor3f(0, 1, 0);
        gl.glVertex3f(0, 0, 0);
        gl.glVertex3f(0, axisLength, 0);

        // draw Z
        gl.glColor3f(0, 0, 1);
        gl.glVertex3f(0, 0, 0);
        gl.glVertex3f(0, 0, axisLength);

        gl.glEnd();

        // draw cone X
        gl.glPushMatrix();                          // Save previous state
        gl.glColor3f(1, 0, 0);
        gl.glTranslatef(axisLength, 0, 0);
        gl.glRotatef(90, 0, 1, 0);                  // Rotate around y-axis
        glut.glutSolidCone(0.1, 0.2, 16, 16);
        gl.glPopMatrix();                           // restore after drawing cone

        // draw cone Y
        gl.glPushMatrix();
        gl.glColor3f(0, 1, 0);
        gl.glTranslatef(0, axisLength, 0);
        gl.glRotatef(270, 1, 0, 0);                 // Rotate around x-axis
        glut.glutSolidCone(0.1, 0.2, 16, 16);
        gl.glPopMatrix();

        // draw cone Z, rotate around x-axis and point upwards, hence no rotation needed
        gl.glPushMatrix();
        gl.glColor3f(0, 0, 1);
        gl.glTranslatef(0, 0, axisLength);
        glut.glutSolidCone(0.1, 0.2, 16, 16);
        gl.glPopMatrix();

        // draw origin
        gl.glColor3f(1, 1, 0);
        glut.glutSolidSphere(0.05, 10, 10);

        // reset color
        gl.glColor3f(0f, 0f, 0f);

    }

    /**
     * Main program execution body, delegates to an instance of the RobotRace
     * implementation.
     */
    public static void main(String args[]) {
        RobotRace robotRace = new RobotRace();
        robotRace.run();
    }
}
