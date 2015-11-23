package robotrace;

import com.jogamp.opengl.util.gl2.GLUT;
import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

/**
 * Represents a Robot, to be implemented according to the Assignments.
 */
class Robot {

    /**
     * The position of the robot.
     */
    public Vector position = new Vector(0, 0, 0);

    /**
     * The direction in which the robot is running.
     */
    public Vector direction = new Vector(1, 0, 0);

    /**
     * The material from which this robot is built.
     */
    private final Material material;

    /**
     * Constructs the robot with initial parameters.
     */
    public Robot(Material material
    /* add other parameters that characterize this robot */) {
        this.material = material;

        // code goes here ...
    }

    /**
     * Draws this robot (as a {@code stickfigure} if specified).
     */
    public void draw(GL2 gl, GLU glu, GLUT glut, boolean stickFigure, float tAnim) {
        // code goes here ...
//        gl.glShadeModel(GL2.GL_LIGHT_MODEL_LOCAL_VIEWER);
//        gl.glEnable(GL2.GL_LIGHTING);
//        gl.glEnable(GL2.GL_LIGHT0);
//        float lightPos[] = {2.0f, 0.0f, 3.0f, 0.0f};
//        float whiteColor[] = {1.0f, 1.0f, 1.0f, 1.0f};
//        float pinkColor[] = {1.0f, 0.5f, 0.5f, 1.0f};
//        float greyColor[] = {0.3f, 0.3f, 0.3f, 1.0f};
//
//        float n1[] = {2, 2, 2};
//        float v1[] = {1, 0, 0};
//        float n2[] = {2, 2, 2};
//        float v2[] = {0, 1, 0};
//        float n3[] = {2, 2, 2};
//        float v3[] = {0, 0, 1};
//
//        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, lightPos, 0);
//        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, whiteColor, 0);
//        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, pinkColor, 0);
//        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, greyColor, 0);
//        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, pinkColor, 0);
//        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, greyColor, 0);
//        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, whiteColor, 0);
//        gl.glMaterialf(GL2.GL_FRONT, GL2.GL_SHININESS, 25.0f);
//
//        gl.glBegin(GL2.GL_TRIANGLES);
//        gl.glNormal3fv(n1, 0);
//        gl.glVertex3fv(v1, 0); // draw triangle, give 
//        gl.glNormal3fv(n2, 0);
//        gl.glVertex3fv(v2, 0); // first normal, followed 
//        gl.glNormal3fv(n3, 0);
//        gl.glVertex3fv(v3, 0); // by vertex 
//        gl.glEnd();
//
//        gl.glDisable(GL2.GL_LIGHTING);

    }
}
