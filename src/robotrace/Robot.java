package robotrace;

import com.jogamp.opengl.util.gl2.GLUT;
import static javax.media.opengl.GL.GL_FRONT_AND_BACK;
import static javax.media.opengl.GL.GL_LINES;
import static javax.media.opengl.GL.GL_LINE_LOOP;

import javax.media.opengl.GL2;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_DIFFUSE;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_SHININESS;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_SPECULAR;
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
    public Robot(Material material, Vector position) {
        this.material = material;
        this.position = position;

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

    private void gluSphere(Object a, double d, int i, int i0) {
        glu.gluSphere(glu.gluNewQuadric(), d, i, i0);
    }

    private void glColor3f(double d, double d0, double d1) {
        gl.glColor3f((float) d, (float) d0, (float) d1);
    }

    private void addMaterialColor() {
        gl.glMaterialfv(GL_FRONT_AND_BACK, GL_DIFFUSE, material.diffuse, 0);
        gl.glMaterialfv(GL_FRONT_AND_BACK, GL_SPECULAR, material.specular, 0);
        gl.glMaterialf(GL_FRONT_AND_BACK, GL_SHININESS, material.shininess);
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

        addMaterialColor();

        glPushMatrix();

        // Robot position
        glTranslatef(position.x(), position.y(), UPPER_LEG_HEIGHT + LOWER_LEG_HEIGHT);

        // Torso
        drawTorso();

        // Head
        glPushMatrix();
        glTranslatef(0.0, 0.0, TORSO_HEIGHT + 0.6 * HEAD_HEIGHT);
        glRotatef(-90, 1.0, 0.0, 0);                    // Robot face Y-axis
        glTranslatef(0.0, -0.6 * HEAD_HEIGHT, 0.0);
        drawHead();
        glPopMatrix();

        drawTorsoTop();
        drawShoulder();
        drawArms();
        drawLegs();
        drawLegJoints();

        glPopMatrix();

    }

    private void drawTorso() {
        glPushMatrix();
        glRotatef(0, 1, 0, 0);
        if (!stickFigure) {
            gluCylinder(glu.gluNewQuadric(), TORSO_RADIUS, TORSO_RADIUS * 1.2, TORSO_HEIGHT, 10, 10);	//(*obj, base, top, height, slices, stacks)
        } else {
            glPushMatrix();
            glColor3f(1.0f, 0, 0);
            glScalef(0.05f, 0.05f, (float) TORSO_HEIGHT);
            glTranslatef(0, 0, 0.5f);
            glut.glutSolidCube((float) TORSO_HEIGHT);
            glPopMatrix();
        }
        glPopMatrix();
    }

    private void drawHead() {
        glPushMatrix();
        glTranslatef(0.0, 0.5 * HEAD_HEIGHT, 0.0);
//        if (!stickFigure) {
        glScalef(HEAD_RADIUS, HEAD_HEIGHT, HEAD_RADIUS);
        gluSphere(glu.gluNewQuadric(), 1.0, 10, 10);
//        } else {
//
//        }
        glPopMatrix();

    }

    private void drawTorsoTop() {
        //torso_disk
        glPushMatrix();
        glTranslatef(0.0, 0.0, TORSO_HEIGHT);
        if (!stickFigure) {
            glRotatef(-90, 1, 0, 0);
            torsoTop();
        } else {
            glPushMatrix();
            glTranslatef(0, 0, -0.1 * TORSO_HEIGHT);
            glRotatef(-90, 0, 1, 0);
            glColor3f(1.0f, 0, 0);
            glScalef(0.05f, 0.05f, 3 * TORSO_RADIUS);
            glut.glutSolidCube((float) TORSO_HEIGHT);
            glPopMatrix();
        }

        glPopMatrix();
    }

    private void drawShoulder() {
        //shoulder_joints
        glPushMatrix();
        if (!stickFigure) {
            glTranslatef(1.5 * TORSO_RADIUS, 0.0, 0.9 * TORSO_HEIGHT);
            shoulder_joints();
            glTranslatef(-3.0 * TORSO_RADIUS, 0.0, 0.0);
            shoulder_joints();
        } else {

        }
        glPopMatrix();
    }

    private void drawArms() {
        // Left arm
        glPushMatrix();
        glTranslatef((TORSO_RADIUS + UPPER_ARM_RADIUS), 0, 0.9 * TORSO_HEIGHT);
        glRotatef(-90, 1, 0, 0.0);              //Arm to front

        if (stickFigure) {
            glPushMatrix();
            glTranslatef(0, 0, UPPER_ARM_HEIGHT / 2);
            glColor3f(1.0f, 0, 0);
            glScalef(0.05f, 0.05f, UPPER_ARM_HEIGHT);
            glut.glutSolidCube(1f);
            glPopMatrix();
        } else {
            left_upper_arm();
        }

        glTranslatef(0.0, 0.0, UPPER_ARM_HEIGHT);
        elbow_joints();
        glRotatef(0.0, 1.0, 0.0, 0.0);
        if (stickFigure) {
            glPushMatrix();
            glTranslatef(0, 0, LOWER_ARM_HEIGHT / 2);
            glColor3f(1.0f, 0, 0);
            glScalef(0.05f, 0.05f, LOWER_ARM_HEIGHT);
            glut.glutSolidCube(1f);
            glPopMatrix();
        } else {
            left_lower_arm();
        }

        // Left palm
        glTranslatef(0.0, 0.0, LOWER_ARM_HEIGHT);
        palms(); //left hand
        glPopMatrix();

        // Right arm
        glPushMatrix();
        glTranslatef(-(TORSO_RADIUS + UPPER_ARM_RADIUS), 0, 0.9 * TORSO_HEIGHT);
        glRotatef(-90, 1, 0.0, 0.0);
        if (stickFigure) {
            glPushMatrix();
            glTranslatef(0, 0, UPPER_ARM_HEIGHT / 2);
            glColor3f(1.0f, 0, 0);
            glScalef(0.05f, 0.05f, UPPER_ARM_HEIGHT);
            glut.glutSolidCube(1f);
            glPopMatrix();
        } else {
            right_upper_arm();
        }

        glTranslatef(0.0, 0.0, UPPER_ARM_HEIGHT);
        elbow_joints();
        glRotatef(0.0, 1.0, 0.0, 0.0);
        if (stickFigure) {
            glPushMatrix();
            glTranslatef(0, 0, LOWER_ARM_HEIGHT / 2);
            glColor3f(1.0f, 0, 0);
            glScalef(0.05f, 0.05f, LOWER_ARM_HEIGHT);
            glut.glutSolidCube(1f);
            glPopMatrix();
        } else {
            right_lower_arm();
        }

        glTranslatef(0.0, 0.0, LOWER_ARM_HEIGHT);
        palms(); //left hand
        glPopMatrix();
    }

    private void drawLegs() {
        //Left leg
        glPushMatrix();
        glTranslatef(-(TORSO_RADIUS - 0.07), 0.0, 0.1 * UPPER_LEG_HEIGHT);
        glRotatef(185, 1.0, 0.0, 0.0);                                          // Rotate left leg
        if (stickFigure) {
            glPushMatrix();
            glTranslatef(0, 0, UPPER_LEG_HEIGHT / 2);
            glColor3f(1.0f, 0, 0);
            glScalef(0.05f, 0.05f, UPPER_LEG_HEIGHT);
            glut.glutSolidCube(1f);
            glPopMatrix();
        } else {
            left_upper_leg();                                                       // Draw left leg
        }
        glTranslatef(0, 0.0, UPPER_LEG_HEIGHT);
        knee_joints();
        glRotatef(-10, 1.0, 0.0, 0.0);
        if (stickFigure) {
            glPushMatrix();
            glTranslatef(0, 0, LOWER_LEG_HEIGHT / 2);
            glColor3f(1.0f, 0, 0);
            glScalef(0.05f, 0.05f, LOWER_LEG_HEIGHT);
            glut.glutSolidCube(1f);
            glPopMatrix();
        } else {
            left_lower_leg();
        }
        glTranslatef(0, 0, LOWER_LEG_HEIGHT);
        palms();
        glPopMatrix();

        //Right leg
        glPushMatrix();
        glTranslatef((TORSO_RADIUS - 0.07), 0.0, 0.1 * UPPER_LEG_HEIGHT);
        glRotatef(185, 1.0, 0.0, 0.0);
        if (stickFigure) {
            glPushMatrix();
            glTranslatef(0, 0, UPPER_LEG_HEIGHT / 2);
            glColor3f(1.0f, 0, 0);
            glScalef(0.05f, 0.05f, UPPER_LEG_HEIGHT);
            glut.glutSolidCube(1f);
            glPopMatrix();
        } else {
            right_upper_leg();
        }
        glTranslatef(0.0, 0, UPPER_LEG_HEIGHT);
        knee_joints();
        glRotatef(-10, 1.0, 0.0, 0.0);
        if (stickFigure) {
            glPushMatrix();
            glTranslatef(0, 0, LOWER_LEG_HEIGHT / 2);
            glColor3f(1.0f, 0, 0);
            glScalef(0.05f, 0.05f, LOWER_LEG_HEIGHT);
            glut.glutSolidCube(1f);
            glPopMatrix();
        } else {
            right_lower_leg();
        }
        glTranslatef(0, 0, LOWER_LEG_HEIGHT);
        palms();
        glPopMatrix();

    }

    // Utils methods

    /*
     DRAWING ARMS:
     */
    void left_upper_arm() {
        glPushMatrix();
        gluCylinder(glu.gluNewQuadric(), UPPER_ARM_RADIUS * 1.2, UPPER_ARM_RADIUS, UPPER_ARM_HEIGHT, 10, 10);
        glPopMatrix();
    }

    void left_lower_arm() {
        glPushMatrix();
        gluCylinder(glu.gluNewQuadric(), LOWER_ARM_RADIUS * 1.1, LOWER_ARM_RADIUS, LOWER_ARM_HEIGHT, 10, 10);
        glPopMatrix();
    }

    void right_upper_arm() {
        glPushMatrix();
        gluCylinder(glu.gluNewQuadric(), UPPER_ARM_RADIUS * 1.2, UPPER_ARM_RADIUS, UPPER_ARM_HEIGHT, 10, 10);
        glPopMatrix();
    }

    void right_lower_arm() {
        glPushMatrix();
        gluCylinder(glu.gluNewQuadric(), LOWER_ARM_RADIUS * 1.1, LOWER_ARM_RADIUS, LOWER_ARM_HEIGHT, 10, 10);
        glPopMatrix();
    }

    /*
     DRAWING LEGS:
     */
    void left_upper_leg() {
        glColor3f(1.0, 0.0, 1.0);
        glPushMatrix();
        gluCylinder(glu.gluNewQuadric(), UPPER_LEG_RADIUS * 1.2, UPPER_LEG_RADIUS, UPPER_LEG_HEIGHT, 10, 10);
        glPopMatrix();
    }

    void left_lower_leg() {
        glColor3f(1.0, 0.0, 0.0);
        glPushMatrix();
        gluCylinder(glu.gluNewQuadric(), LOWER_LEG_RADIUS * 1.2, LOWER_LEG_RADIUS, LOWER_LEG_HEIGHT, 10, 10);
        glPopMatrix();
    }

    void right_upper_leg() {
        glColor3f(1.0f, 0.0f, 1.0f);
        glPushMatrix();

        gluCylinder(glu.gluNewQuadric(), UPPER_LEG_RADIUS * 1.2, UPPER_LEG_RADIUS, UPPER_LEG_HEIGHT, 10, 10);

        glPopMatrix();
    }

    void right_lower_leg() {
        glColor3f(1.0, 0.0, 0.0);
        glPushMatrix();

        gluCylinder(glu.gluNewQuadric(), LOWER_LEG_RADIUS * 1.2, LOWER_LEG_RADIUS, LOWER_LEG_HEIGHT, 10, 10);

        glPopMatrix();
    }

    /*
     DRAWING JOINTS
     */
    void elbow_joints() {
        if (stickFigure) {
            return;
        }
        glPushMatrix();
        glScalef(SHOULDER_RADIUS / 1.2, SHOULDER_RADIUS / 1.2, SHOULDER_RADIUS / 1.2);
        gluSphere(glu.gluNewQuadric(), 1.0, 10, 10);
        glPopMatrix();
    }

    void palms() {
        if (stickFigure) {
            return;
        }
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
        if (stickFigure) {
            return;
        }
        glPushMatrix();
        glScalef(JOINT_RADIUS, JOINT_RADIUS, JOINT_RADIUS);
        gluSphere(1, 1.0, 10, 10);
        glPopMatrix();
    }

    void torsoTop() {
        glPushMatrix();
        glScalef(1.2 * TORSO_RADIUS, 0.1, 1.2 * TORSO_RADIUS);
        gluSphere(1, 1.0, 10, 10);
        glPopMatrix();
    }

    void shoulder_joints() {
        glPushMatrix();
        glScalef(SHOULDER_RADIUS, SHOULDER_RADIUS, SHOULDER_RADIUS);
        gluSphere(1, 1.0, 10, 10);
        glPopMatrix();
    }

    private void drawLegJoints() {
        glPushMatrix();
        if (!stickFigure) {
            glTranslatef(TORSO_RADIUS - 0.07, 0.0, 0.0);
            leg_joints();
            glTranslatef(-2 * (TORSO_RADIUS - 0.07), 0.0, 0.0);
            leg_joints();
        } else {
            glPushMatrix();
            glRotatef(-90, 0, 1, 0);
            glColor3f(1.0f, 0, 0);
            glScalef(0.05f, 0.05f, 2 * (TORSO_RADIUS - 0.07));
            glut.glutSolidCube(1f);
            glPopMatrix();
        }
        glPopMatrix();
    }

}
