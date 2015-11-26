package robotrace;

import com.jogamp.opengl.util.gl2.GLUT;
import static javax.media.opengl.GL.GL_LINES;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

/**
 * Represents a Robot, to be implemented according to the Assignments.
 */
class Robot {

    private static GL2 gl;
    private static GLU glu;
    private static GLUT glut;

    private static final double TORSO_HEIGHT = 1;
    private static final double TORSO_RADIUS = 0.26;

    private static final double HEAD_HEIGHT = 0.34;
    private static final double HEAD_RADIUS = 0.26;

    private static final double UPPER_ARM_HEIGHT = 0.6;
    private static final double LOWER_ARM_HEIGHT = 0.4;

    private static final double UPPER_ARM_RADIUS = 0.13;
    private static final double LOWER_ARM_RADIUS = 0.1;

    private static final double UPPER_LEG_HEIGHT = 0.6;
    private static final double LOWER_LEG_HEIGHT = 0.6;

    private static final double UPPER_LEG_RADIUS = 0.13;
    private static final double LOWER_LEG_RADIUS = 0.1;

    private static final double SHOULDER_RADIUS = 0.17;
    private static final double JOINT_RADIUS = 0.17;

    private static boolean stickFigure;

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

    private void glPushMatrix() {
        gl.glPushMatrix();
    }

    private void glPopMatrix() {
        gl.glPopMatrix();
    }

    private void glRotatef(double a, double b, double c, double d) {
        gl.glRotatef((float) a, (float) b, (float) c, (float) d);
    }

    private void glTranslatef(double d, double d0, double d1) {
        gl.glTranslatef((float) d, (float) d0, (float) d1);
    }

    private void glScalef(double f, double f1, double f2) {
        gl.glScalef((float) f, (float) f1, (float) f2);
    }

    private void gluCylinder(Object a, double b, double c, double d, int e, int f) {
        glu.gluCylinder(glu.gluNewQuadric(), b, c, d, e, f);
    }

    private void gluSphere(Object gluNewQuadric, double d, int i, int i0) {
        glu.gluSphere(glu.gluNewQuadric(), d, i, i0);
    }

    private void glColor3f(double d, double d0, double d1) {
        gl.glColor3f((float) d, (float) d0, (float) d1);
    }

    /**
     * Draws this robot (as a {@code stickfigure} if specified).
     */
    public void draw(GL2 gl, GLU glu, GLUT glut, boolean stickFigure, float tAnim) {
        // code goes here ...
        Robot.gl = gl;
        Robot.glut = glut;
        Robot.glu = glu;
        Robot.stickFigure = stickFigure;

        glPushMatrix();
//        glRotatef(-90, 0, 0, 1);      //Rotate robot around Z-axis

        // Torso
        drawTorso();

        // Head
        glPushMatrix();
        glTranslatef(0.0, 0.0, TORSO_HEIGHT + 0.6 * HEAD_HEIGHT);
        glRotatef(-90, 1.0, 0.0, 0);                    // Robot face Y-axis
        glTranslatef(0.0, -0.6 * HEAD_HEIGHT, 0.0);
        drawHead();
        glColor3f(1, 0, 0);
        drawGlasses();     //glasses
        glColor3f(0, 0, 0);
        glPopMatrix();

        drawArms();

        drawLegs();

        drawTorsoTop();

        drawShoulder();

        drawLegJoints();

        glPopMatrix();

    }

    private void drawTorso() {
        glPushMatrix();
        if (stickFigure) {
            gl.glBegin(GL_LINES);
            gl.glVertex3f(0, 0, 0); // Base of the torso.
            gl.glVertex3d(0, 0, TORSO_HEIGHT); // End of the leg.
            gl.glEnd();
        } else {
            glRotatef(0, 1, 0, 0);
            gluCylinder(glu.gluNewQuadric(), TORSO_RADIUS, TORSO_RADIUS * 1.2, TORSO_HEIGHT, 10, 10);	//(*obj, base, top, height, slices, stacks)
        }
        glPopMatrix();
    }

    private void drawHead() {
        glPushMatrix();
        glTranslatef(0.0, 0.5 * HEAD_HEIGHT, 0.0);
        glScalef(HEAD_RADIUS, HEAD_HEIGHT, HEAD_RADIUS);
        gluSphere(glu.gluNewQuadric(), 1.0, 10, 10);
        glPopMatrix();

    }

    private void drawTorsoTop() {
        //torso_disk
        glPushMatrix();
        glRotatef(90, 1, 0, 0);
        glTranslatef(0.0, TORSO_HEIGHT, 0.0);
        torsoTop();
        glPopMatrix();
    }

    private void drawShoulder() {
        //shoulder_joints
        glPushMatrix();
        glTranslatef(1.5 * TORSO_RADIUS, 0.0, 0.9 * TORSO_HEIGHT);
        if (stickFigure) {
            glTranslatef(0, -0.1*TORSO_HEIGHT, -TORSO_HEIGHT);
            gl.glBegin(GL_LINES);
            gl.glVertex3f(0, 0, 0); // Base of the torso.
            gl.glVertex3d(-3 * TORSO_RADIUS, 0, 0); // End of the leg.
            gl.glEnd();
        } else {
            shoulder_joints();
        }
        glTranslatef(-3.0 * TORSO_RADIUS, 0.0, 0.0);
        if (!stickFigure) {
            shoulder_joints();
        }
        glPopMatrix();
    }

    private void drawArms() {
        // Left arm
        glPushMatrix();
        glTranslatef((TORSO_RADIUS + UPPER_ARM_RADIUS), 0, 0.9 * TORSO_HEIGHT);
        glRotatef(-90, 1, 0, 0.0);              //Arm to front
        left_upper_arm();

        glTranslatef(0.0, 0.0, UPPER_ARM_HEIGHT);
        elbow_joints();
        glRotatef(0.0, 1.0, 0.0, 0.0);
        left_lower_arm();

        // Left palm
        glTranslatef(0.0, 0.0, LOWER_ARM_HEIGHT);
        palms(); //left hand
        glPopMatrix();

        // Right arm
        glPushMatrix();
        glTranslatef(-(TORSO_RADIUS + UPPER_ARM_RADIUS), 0, 0.9 * TORSO_HEIGHT);
        glRotatef(-90, 1, 0.0, 0.0);
        right_upper_arm();

        glTranslatef(0.0, 0.0, UPPER_ARM_HEIGHT);
        elbow_joints();
        glRotatef(0.0, 1.0, 0.0, 0.0);
        right_lower_arm();

        glTranslatef(0.0, 0.0, LOWER_ARM_HEIGHT);
        palms(); //left hand
        glPopMatrix();
    }

    private void drawLegs() {
        //Left leg
        glPushMatrix();
        glTranslatef(-(TORSO_RADIUS), 0.0, 0.1 * UPPER_LEG_HEIGHT);
        glRotatef(185, 1.0, 0.0, 0.0);                                          // Rotate left leg
        left_upper_leg();                                                       // Draw left leg
        glTranslatef(0, 0.0, UPPER_LEG_HEIGHT);
        knee_joints();
        glRotatef(-10, 1.0, 0.0, 0.0);
        left_lower_leg();
        glTranslatef(0, 0, LOWER_LEG_HEIGHT);
        palms();
        glPopMatrix();

        //Right leg
        glPushMatrix();
        glTranslatef((TORSO_RADIUS), 0.0, 0.1 * UPPER_LEG_HEIGHT);
        glRotatef(185, 1.0, 0.0, 0.0);
        right_upper_leg();
        glTranslatef(0.0, 0, UPPER_LEG_HEIGHT);
        knee_joints();
        glRotatef(-10, 1.0, 0.0, 0.0);
        right_lower_leg();
        glTranslatef(0, 0, LOWER_LEG_HEIGHT);
        palms();
        glPopMatrix();

    }

    // Methods
    /*
     DRAW GLASSES:
     */
    private void drawGlasses() {
        if (stickFigure) {
            return;
        }
        glPushMatrix();
        glTranslatef(0.0, 0.35 * HEAD_HEIGHT, 0.2 * HEAD_HEIGHT);
        glRotatef(-90.0, 1.0, 0.0, 0.0);
        gluCylinder(glu.gluNewQuadric(), HEAD_RADIUS, HEAD_RADIUS, HEAD_HEIGHT / 2, 10, 10);
        glPopMatrix();
    }

    /*
     DRAWING ARMS:
     */
    void left_upper_arm() {
        glPushMatrix();
        if (stickFigure) {
            // Draw legs
            gl.glBegin(GL_LINES);
            gl.glVertex3f(0, 0, 0); // Base of the torso.
            gl.glVertex3d(0, 0, UPPER_ARM_HEIGHT); // End of the leg.
            gl.glEnd();
        } else {
            gluCylinder(glu.gluNewQuadric(), UPPER_ARM_RADIUS * 1.2, UPPER_ARM_RADIUS, UPPER_ARM_HEIGHT, 10, 10);
        }
        glPopMatrix();
    }

    void left_lower_arm() {
        glPushMatrix();
        if (stickFigure) {
            // Draw legs
            gl.glBegin(GL_LINES);
            gl.glVertex3f(0, 0, 0); // Base of the torso.
            gl.glVertex3d(0, 0, LOWER_ARM_HEIGHT); // End of the leg.
            gl.glEnd();
        } else {
            gluCylinder(glu.gluNewQuadric(), LOWER_ARM_RADIUS * 1.1, LOWER_ARM_RADIUS, LOWER_ARM_HEIGHT, 10, 10);
        }
        glPopMatrix();
    }

    void right_upper_arm() {
        glPushMatrix();
        if (stickFigure) {
            // Draw legs
            gl.glBegin(GL_LINES);
            gl.glVertex3f(0, 0, 0); // Base of the torso.
            gl.glVertex3d(0, 0, UPPER_ARM_HEIGHT); // End of the leg.
            gl.glEnd();
        } else {
            gluCylinder(glu.gluNewQuadric(), UPPER_ARM_RADIUS * 1.2, UPPER_ARM_RADIUS, UPPER_ARM_HEIGHT, 10, 10);
        }
        glPopMatrix();
    }

    void right_lower_arm() {
        glPushMatrix();
        if (stickFigure) {
            // Draw legs
            gl.glBegin(GL_LINES);
            gl.glVertex3f(0, 0, 0); // Base of the torso.
            gl.glVertex3d(0, 0, LOWER_ARM_HEIGHT); // End of the leg.
            gl.glEnd();
        } else {
            gluCylinder(glu.gluNewQuadric(), LOWER_ARM_RADIUS * 1.1, LOWER_ARM_RADIUS, LOWER_ARM_HEIGHT, 10, 10);
        }
        glPopMatrix();
    }

    /*
     DRAWING LEGS:
     */
    void left_upper_leg() {
        glColor3f(1.0, 0.0, 1.0);

        glPushMatrix();
        if (stickFigure) {
            // Draw legs
            gl.glBegin(GL_LINES);
            gl.glVertex3f(0, 0, 0); // Base of the torso.
            gl.glVertex3d(0, 0, UPPER_LEG_HEIGHT); // End of the leg.
            gl.glEnd();
        } else {
            gluCylinder(glu.gluNewQuadric(), UPPER_LEG_RADIUS * 1.2, UPPER_LEG_RADIUS, UPPER_LEG_HEIGHT, 10, 10);
        }
        glPopMatrix();
    }

    void left_lower_leg() {
        glColor3f(1.0, 0.0, 0.0);
        glPushMatrix();
        if (stickFigure) {
            // Draw legs
            gl.glBegin(GL_LINES);
            gl.glVertex3f(0, 0, 0); // Base of the torso.
            gl.glVertex3d(0, 0, LOWER_LEG_HEIGHT); // End of the leg.
            gl.glEnd();
        } else {
            gluCylinder(glu.gluNewQuadric(), LOWER_LEG_RADIUS * 1.2, LOWER_LEG_RADIUS, LOWER_LEG_HEIGHT, 10, 10);
        }
        glPopMatrix();
    }

    void right_upper_leg() {
        glColor3f(1.0f, 0.0f, 1.0f);
        glPushMatrix();
        if (stickFigure) {
            // Draw legs
            gl.glBegin(GL_LINES);
            gl.glVertex3f(0, 0, 0); // Base of the torso.
            gl.glVertex3d(0, 0, UPPER_LEG_HEIGHT); // End of the leg.
            gl.glEnd();
        } else {
            gluCylinder(glu.gluNewQuadric(), UPPER_LEG_RADIUS * 1.2, UPPER_LEG_RADIUS, UPPER_LEG_HEIGHT, 10, 10);
        }
        glPopMatrix();
    }

    void right_lower_leg() {
        glColor3f(1.0, 0.0, 0.0);
        glPushMatrix();
        if (stickFigure) {
            // Draw legs
            gl.glBegin(GL_LINES);
            gl.glVertex3f(0, 0, 0); // Base of the torso.
            gl.glVertex3d(0, 0, LOWER_LEG_HEIGHT); // End of the leg.
            gl.glEnd();
        } else {
            gluCylinder(glu.gluNewQuadric(), LOWER_LEG_RADIUS * 1.2, LOWER_LEG_RADIUS, LOWER_LEG_HEIGHT, 10, 10);
        }
        glPopMatrix();
    }

    /*
     DRAWING JOINTS
     */
    void elbow_joints() {
        glPushMatrix();
        glScalef(SHOULDER_RADIUS / 1.2, SHOULDER_RADIUS / 1.2, SHOULDER_RADIUS / 1.2);
        gluSphere(glu.gluNewQuadric(), 1.0, 10, 10);
        glPopMatrix();
    }

    void palms() {
        glPushMatrix();
        glScalef(SHOULDER_RADIUS / 1.3, SHOULDER_RADIUS / 1.3, SHOULDER_RADIUS / 1.3);
        gluSphere(1, 1.0, 10, 10);
        glPopMatrix();
    }

    void leg_joints() {
        glPushMatrix();
        glScalef(JOINT_RADIUS, JOINT_RADIUS, JOINT_RADIUS);
        gluSphere(1, 1.0, 10, 10);
        glPopMatrix();
    }

    void knee_joints() {
        glPushMatrix();
        glScalef(JOINT_RADIUS, JOINT_RADIUS, JOINT_RADIUS);
        gluSphere(1, 1.0, 10, 10);
        glPopMatrix();
    }

    void torsoTop() {
        glPushMatrix();
        if (stickFigure) {
            return;
        } else {
            glScalef(1.2 * TORSO_RADIUS, 0.1, 1.2 * TORSO_RADIUS);
            gluSphere(1, 1.0, 10, 10);
        }
        glPopMatrix();
    }

    void shoulder_joints() {
        glPushMatrix();
        glScalef(SHOULDER_RADIUS, SHOULDER_RADIUS, SHOULDER_RADIUS);
        gluSphere(1, 1.0, 10, 10);
        glPopMatrix();
    }

    private void drawLegJoints() {
        //leg_joints
        glPushMatrix();
        if (stickFigure) {
//            // Draw hips
//            glTranslatef(1.1 * TORSO_RADIUS, 0.0, 0.0);
//            gl.glBegin(GL_LINES);
//            gl.glVertex3f(0, 0, 0); // Base of the torso.
//            gl.glVertex3d(-2.2 * TORSO_RADIUS, 0, 0); // End of the leg.
//            gl.glEnd();
        } else {

            glTranslatef(1.1 * TORSO_RADIUS, 0.0, 0.0);
            leg_joints();
            glTranslatef(-2.2 * TORSO_RADIUS, 0.0, 0.0);
            leg_joints();

        }
        glPopMatrix();
    }

}
