package robotrace;

import com.jogamp.opengl.util.gl2.GLUT;
import static javax.media.opengl.GL.GL_FRONT_AND_BACK;

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

    private static boolean stickFigure;

    private final double torsoHeight, torsoRadius,
            headHeight, headRadius,
            upperArmHeight, upperArmRadius,
            lowerArmHeight, lowerArmRadius,
            upperLegHeight, upperLegRadius,
            lowerLegHeight, lowerLegRadius,
            shoulderRadius, jointRadius;

    private final boolean glassesOn;

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
     * Constructs the robot with default parameters
     *
     * @param material
     * @param position
     */
    public Robot(Material material, Vector position) {
        this.material = material;
        this.position = position;

        //Default robot configurations
        this.torsoHeight = 1;
        this.torsoRadius = 0.26;
        this.headHeight = 0.34;
        this.headRadius = 0.26;
        this.upperArmHeight = 0.6;
        this.upperArmRadius = 0.13;
        this.lowerArmHeight = 0.4;
        this.lowerArmRadius = 0.1;
        this.upperLegHeight = 0.6;
        this.upperLegRadius = 0.13;
        this.lowerLegHeight = 0.6;
        this.lowerLegRadius = 0.1;
        this.shoulderRadius = 0.17;
        this.jointRadius = 0.17;
        this.glassesOn = false;

    }

    /**
     * Constructs robot with given parameters
     *
     * @param material either Gold, Silver, Wood or plastic orange
     * @param position initial position
     * @param torsoHeight in double
     * @param torsoRadius in double
     * @param headHeight in double
     * @param headRadius in double
     * @param upperArmHeight in double
     * @param upperArmRadius in double
     * @param lowerArmHeight in double
     * @param lowerArmRadius in double
     * @param upperLegHeight in double
     * @param upperLegRadius in double
     * @param lowerLegHeight in double
     * @param lowerLegRadius in double
     * @param shoulderRadius in double
     * @param jointRadius in double
     * @param glassesOn whether the robot should wear glasses
     */
    public Robot(Material material, Vector position, double torsoHeight, double torsoRadius, double headHeight, double headRadius,
            double upperArmHeight, double upperArmRadius, double lowerArmHeight, double lowerArmRadius,
            double upperLegHeight, double upperLegRadius, double lowerLegHeight, double lowerLegRadius,
            double shoulderRadius, double jointRadius, boolean glassesOn) {

        this.material = material;
        this.position = position;

        this.torsoHeight = torsoHeight;
        this.torsoRadius = torsoRadius;
        this.headHeight = headHeight;
        this.headRadius = headRadius;
        this.upperArmHeight = upperArmHeight;
        this.upperArmRadius = upperArmRadius;
        this.lowerArmHeight = lowerArmHeight;
        this.lowerArmRadius = lowerArmRadius;
        this.upperLegHeight = upperLegHeight;
        this.upperLegRadius = upperLegRadius;
        this.lowerLegHeight = lowerLegHeight;
        this.lowerLegRadius = lowerLegRadius;
        this.shoulderRadius = shoulderRadius;
        this.jointRadius = jointRadius;
        this.glassesOn = glassesOn;

    }

    /*
     Helpers methods
     */
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

    /**
     * Set up material color
     */
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
        // Current robot position, robot should be on the ground (above xy-plane)
        glTranslatef(position.x(), position.y(), upperLegHeight + lowerLegHeight);

        // Head
        glPushMatrix();
        glTranslatef(0.0, 0.0, torsoHeight + 0.6 * headHeight);
        glRotatef(-90, 1.0, 0.0, 0);                    // Robot face Y-axis
        glTranslatef(0.0, -0.6 * headHeight, 0.0);
        drawHead();
        glPopMatrix();
        
        drawTorso();
        drawTorsoTop();
        drawShoulderJoints();
        drawArms();
        drawLegs();
        drawLegJoints();
        glPopMatrix();

    }

    /**
     * Draw head
     */
    private void drawHead() {
        glPushMatrix();
        glTranslatef(0.0, 0.5 * headHeight, 0.0);
        glScalef(headRadius, headHeight, headRadius);
        gluSphere(glu.gluNewQuadric(), 1.0, 10, 10);
        glPopMatrix();

    }

    /**
     * Draw torso
     */
    private void drawTorso() {
        glPushMatrix();
        glRotatef(0, 1, 0, 0);
        if (!stickFigure) {
            gluCylinder(glu.gluNewQuadric(), torsoRadius, torsoRadius * 1.2, torsoHeight, 10, 10);	//(*obj, base, top, height, slices, stacks)
        } else {
            glPushMatrix();
            glColor3f(1.0f, 0, 0);
            glScalef(0.05f, 0.05f, (float) torsoHeight);
            glTranslatef(0, 0, 0.5f);
            glut.glutSolidCube((float) torsoHeight);
            glPopMatrix();
        }
        glPopMatrix();
    }

    /**
     * Draw torso upper disk (shoulder part)
     */
    private void drawTorsoTop() {
        //torso_disk
        glPushMatrix();
        glTranslatef(0.0, 0.0, torsoHeight);
        if (!stickFigure) {
            glRotatef(-90, 1, 0, 0);
            torsoTop();
        } else {
            glPushMatrix();
            glTranslatef(0, 0, -0.1 * torsoHeight);
            glRotatef(-90, 0, 1, 0);
            glColor3f(1.0f, 0, 0);
            glScalef(0.05f, 0.05f, 3 * torsoRadius);
            glut.glutSolidCube((float) torsoHeight);
            glPopMatrix();
        }

        glPopMatrix();
    }

    /**
     * Draw shoulder joints
     */
    private void drawShoulderJoints() {
        glPushMatrix();
        if (!stickFigure) {
            glTranslatef(1.5 * torsoRadius, 0.0, 0.9 * torsoHeight);
            shoulder_joints();
            glTranslatef(-3.0 * torsoRadius, 0.0, 0.0);
            shoulder_joints();
        } else {

        }
        glPopMatrix();
    }

    /**
     * Draw both arms
     */
    private void drawArms() {
        // right arm
        glPushMatrix();
        glTranslatef((torsoRadius + upperArmRadius), 0, 0.9 * torsoHeight);

        /*
         Rotating right upper arm
         Initially pointing upwards
         */
        glRotatef(-70, 1, 0, 0.0);

        if (stickFigure) {
            glPushMatrix();
            glTranslatef(0, 0, upperArmHeight / 2);
            glColor3f(1.0f, 0, 0);
            glScalef(0.05f, 0.05f, upperArmHeight);
            glut.glutSolidCube(1f);
            glPopMatrix();
        } else {
            upper_arm();
        }

        glTranslatef(0.0, 0.0, upperArmHeight);
        elbow_joints();

        /*
         Rotating right lower arm
         */
        glRotatef(0.0, 1.0, 0.0, 0.0);

        if (stickFigure) {
            glPushMatrix();
            glTranslatef(0, 0, lowerArmHeight / 2);
            glColor3f(1.0f, 0, 0);
            glScalef(0.05f, 0.05f, lowerArmHeight);
            glut.glutSolidCube(1f);
            glPopMatrix();
        } else {
            lower_arm();
        }

        // Left palm
        glTranslatef(0.0, 0.0, lowerArmHeight);
        palms(); //left hand
        glPopMatrix();

        // Left arm
        glPushMatrix();
        glTranslatef(-(torsoRadius + upperArmRadius), 0, 0.9 * torsoHeight);

        /*
         Rotating left upper arm
         Initially pointing upwards
         */
        glRotatef(-180, 1, 0.0, 0.0);
        if (stickFigure) {
            glPushMatrix();
            glTranslatef(0, 0, upperArmHeight / 2);
            glColor3f(1.0f, 0, 0);
            glScalef(0.05f, 0.05f, upperArmHeight);
            glut.glutSolidCube(1f);
            glPopMatrix();
        } else {
            upper_arm();
        }

        glTranslatef(0.0, 0.0, upperArmHeight);
        elbow_joints();

        /*
         Rotating Left lower arm
         */
        glRotatef(0.0, 1.0, 0.0, 0.0);
        if (stickFigure) {
            glPushMatrix();
            glTranslatef(0, 0, lowerArmHeight / 2);
            glColor3f(1.0f, 0, 0);
            glScalef(0.05f, 0.05f, lowerArmHeight);
            glut.glutSolidCube(1f);
            glPopMatrix();
        } else {
            lower_arm();
        }

        glTranslatef(0.0, 0.0, lowerArmHeight);
        palms(); //left hand
        glPopMatrix();
    }

    /**
     * Draw both legs
     */
    private void drawLegs() {
        //Left leg
        glPushMatrix();
        glTranslatef(-(torsoRadius - 0.07), 0.0, 0.1 * upperLegHeight);
        glRotatef(185, 1.0, 0.0, 0.0);                                          // Rotate left leg
        if (stickFigure) {
            glPushMatrix();
            glTranslatef(0, 0, upperLegHeight / 2);
            glColor3f(1.0f, 0, 0);
            glScalef(0.05f, 0.05f, upperLegHeight);
            glut.glutSolidCube(1f);
            glPopMatrix();
        } else {
            upper_leg();                                                       // Draw left leg
        }
        glTranslatef(0, 0.0, upperLegHeight);
        knee_joints();
        glRotatef(-10, 1.0, 0.0, 0.0);
        if (stickFigure) {
            glPushMatrix();
            glTranslatef(0, 0, lowerLegHeight / 2);
            glColor3f(1.0f, 0, 0);
            glScalef(0.05f, 0.05f, lowerLegHeight);
            glut.glutSolidCube(1f);
            glPopMatrix();
        } else {
            lower_leg();
        }
        glTranslatef(0, 0, lowerLegHeight);
        palms();
        glPopMatrix();

        //Right leg
        glPushMatrix();
        glTranslatef((torsoRadius - 0.07), 0.0, 0.1 * upperLegHeight);
        glRotatef(185, 1.0, 0.0, 0.0);
        if (stickFigure) {
            glPushMatrix();
            glTranslatef(0, 0, upperLegHeight / 2);
            glColor3f(1.0f, 0, 0);
            glScalef(0.05f, 0.05f, upperLegHeight);
            glut.glutSolidCube(1f);
            glPopMatrix();
        } else {
            upper_leg();
        }
        glTranslatef(0.0, 0, upperLegHeight);
        knee_joints();
        glRotatef(-10, 1.0, 0.0, 0.0);
        if (stickFigure) {
            glPushMatrix();
            glTranslatef(0, 0, lowerLegHeight / 2);
            glColor3f(1.0f, 0, 0);
            glScalef(0.05f, 0.05f, lowerLegHeight);
            glut.glutSolidCube(1f);
            glPopMatrix();
        } else {
            lower_leg();
        }
        glTranslatef(0, 0, lowerLegHeight);
        palms();
        glPopMatrix();

    }

    /**
     * *
     * Drawing helpers methods
     */
    void upper_arm() {
        glPushMatrix();
        gluCylinder(glu.gluNewQuadric(), upperArmRadius * 1.2, upperArmRadius, upperArmHeight, 10, 10);
        glPopMatrix();
    }

    void lower_arm() {
        glPushMatrix();
        gluCylinder(glu.gluNewQuadric(), lowerArmRadius * 1.1, lowerArmRadius, lowerArmHeight, 10, 10);
        glPopMatrix();
    }

    /*
     DRAWING LEGS:
     */
    void upper_leg() {
        glColor3f(1.0f, 0.0f, 1.0f);
        glPushMatrix();
        gluCylinder(glu.gluNewQuadric(), upperLegRadius * 1.2, upperLegRadius, upperLegHeight, 10, 10);
        glPopMatrix();
    }

    void lower_leg() {
        glColor3f(1.0, 0.0, 0.0);
        glPushMatrix();
        gluCylinder(glu.gluNewQuadric(), lowerLegRadius * 1.2, lowerLegRadius, lowerLegHeight, 10, 10);
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
        glScalef(shoulderRadius / 1.2, shoulderRadius / 1.2, shoulderRadius / 1.2);
        gluSphere(glu.gluNewQuadric(), 1.0, 10, 10);
        glPopMatrix();
    }

    void palms() {
        if (stickFigure) {
            return;
        }
        glPushMatrix();
        glScalef(shoulderRadius / 1.3, shoulderRadius / 1.3, shoulderRadius / 1.3);
        gluSphere(1, 1.0, 10, 10);
        glPopMatrix();
    }

    void leg_joints() {
        glPushMatrix();
        glScalef(jointRadius, jointRadius, jointRadius);
        gluSphere(1, 1.0, 10, 10);
        glPopMatrix();
    }

    void knee_joints() {
        if (stickFigure) {
            return;
        }
        glPushMatrix();
        glScalef(jointRadius, jointRadius, jointRadius);
        gluSphere(1, 1.0, 10, 10);
        glPopMatrix();
    }

    void torsoTop() {
        glPushMatrix();
        glScalef(1.2 * torsoRadius, 0.1, 1.2 * torsoRadius);
        gluSphere(1, 1.0, 10, 10);
        glPopMatrix();
    }

    void shoulder_joints() {
        glPushMatrix();
        glScalef(shoulderRadius, shoulderRadius, shoulderRadius);
        gluSphere(1, 1.0, 10, 10);
        glPopMatrix();
    }

    void drawLegJoints() {
        glPushMatrix();
        if (!stickFigure) {
            glTranslatef(torsoRadius - 0.07, 0.0, 0.0);
            leg_joints();
            glTranslatef(-2 * (torsoRadius - 0.07), 0.0, 0.0);
            leg_joints();
        } else {
            glPushMatrix();
            glRotatef(-90, 0, 1, 0);
            glColor3f(1.0f, 0, 0);
            glScalef(0.05f, 0.05f, 2 * (torsoRadius - 0.07));
            glut.glutSolidCube(1f);
            glPopMatrix();
        }
        glPopMatrix();
    }

}
