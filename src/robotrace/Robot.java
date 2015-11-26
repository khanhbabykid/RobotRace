package robotrace;

import com.jogamp.opengl.util.gl2.GLUT;

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
        if (stickFigure) {

        }

        //TORSO
        glTranslatef(0.0, -0.3, 0.0);
        //glRotatef(0.0, 0.0, 1.0, 0.0);
        torso();

        //HEAD
        glPushMatrix();
        glTranslatef(0.0, 0.0, TORSO_HEIGHT + 0.6 * HEAD_HEIGHT);
        glRotatef(-90, 1.0, 0.0, 0.0);
        //glRotatef(0.0, 0.0, 1.0, 0.0);
        glTranslatef(0.0, -0.6 * HEAD_HEIGHT, 0.0);
        head();
        glColor3f(1, 0, 0);
        drawGlasses();     //glasses
        glPopMatrix();

        double rot_x = 1.0,
                rot_y = 0.0;
        //Left upper arm
        glPushMatrix();
        glTranslatef(0.0, -(TORSO_RADIUS + UPPER_ARM_RADIUS), 0.9 * TORSO_HEIGHT);
        glRotatef(0.0, rot_x, rot_y, 0.0);
        left_upper_arm();

        glTranslatef(0.0, 0.0, UPPER_ARM_HEIGHT);
        elbow_joints();
        glRotatef(0.0, 1.0, 0.0, 0.0);
        left_lower_arm();

        //Palm
        glTranslatef(0.0, 0.0, LOWER_ARM_HEIGHT);
        palms(); //left hand

        glPopMatrix();

//        gl.glPointSize(1);
////        gl.glColor3ub((byte) 255, 0, 0);  // Color Red
//        gl.glBegin(GL_POINTS);
//        for (float x = (float) -1.139; x <= 1.139; x += 0.001) {
//            float delta = (float) (cbrt(x * x) * cbrt(x * x) - 4 * x * x + 4);
//            float y1 = (float) ((cbrt(x * x) + sqrt(delta)) / 2);
//            float y2 = (float) ((cbrt(x * x) - sqrt(delta)) / 2);
//            gl.glVertex2f(x, y1);
//            gl.glVertex2f(x, y2);
//        }
//        gl.glEnd();
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

    private void torso() {
        glPushMatrix();
        glRotatef(0, 1, 0, 0);
        gluCylinder(glu.gluNewQuadric(), TORSO_RADIUS, TORSO_RADIUS * 1.2, TORSO_HEIGHT, 10, 10);	//(*obj, base, top, height, slices, stacks)
        glPopMatrix();
    }

    private void head() {
        glPushMatrix();
        glTranslatef(0.0, 0.5 * HEAD_HEIGHT, 0.0);
        glScalef(HEAD_RADIUS, HEAD_HEIGHT, HEAD_RADIUS);
        gluSphere(glu.gluNewQuadric(), 1.0, 10, 10);
        glPopMatrix();

    }

    private void drawGlasses() {
        glPushMatrix();
        glTranslatef(0.0, 0.5 * HEAD_HEIGHT, 0.075);
        glRotatef(-90.0, 1.0, 0.0, 0.0);
        gluCylinder(glu.gluNewQuadric(), HEAD_RADIUS, HEAD_RADIUS, HEAD_HEIGHT / 2, 10, 10);
        glPopMatrix();
    }

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

        glRotatef(-90.0, 1.0, 0.0, 0.0);
        gluCylinder(glu.gluNewQuadric(), UPPER_ARM_RADIUS * 1.2, UPPER_ARM_RADIUS, UPPER_ARM_HEIGHT, 10, 10);
        glPopMatrix();
    }

    void right_lower_arm() {
        glPushMatrix();
        glRotatef(-90.0, 1.0, 0.0, 0.0);
        gluCylinder(glu.gluNewQuadric(), LOWER_ARM_RADIUS * 1.1, LOWER_ARM_RADIUS, LOWER_ARM_HEIGHT, 10, 10);
        glPopMatrix();
    }

    void left_upper_leg() {
        glColor3f(1.0, 0.0, 1.0);
        glPushMatrix();
        glRotatef(-120.0, 1.0, 0.0, 0.0);
        gluCylinder(glu.gluNewQuadric(), UPPER_LEG_RADIUS * 1.2, UPPER_LEG_RADIUS, UPPER_LEG_HEIGHT, 10, 10);
        glPopMatrix();
    }

    void left_lower_leg() {
        glColor3f(1.0, 0.0, 0.0);
        glPushMatrix();
        glTranslatef(0.0, -0.25, -UPPER_LEG_HEIGHT / 2);
        glRotatef(-70.0, 1.0, 0.0, 0.0);
        gluCylinder(glu.gluNewQuadric(), LOWER_LEG_RADIUS, LOWER_LEG_RADIUS * 1.5, LOWER_LEG_HEIGHT, 10, 10);
        glPopMatrix();
    }

    void right_upper_leg() {
        glColor3f(1.0f, 0.0f, 1.0f);
        glPushMatrix();
        glRotatef(-120.0, 1.0, 0.0, 0.0);
        gluCylinder(glu.gluNewQuadric(), UPPER_LEG_RADIUS * 1.2, UPPER_LEG_RADIUS, UPPER_LEG_HEIGHT, 10, 10);
        glPopMatrix();
    }

    void right_lower_leg() {
        glColor3f(1.0, 0.0, 0.0);
        glPushMatrix();
        glTranslatef(0.0, -0.25, -UPPER_LEG_HEIGHT / 2);
        glRotatef(-70.0, 1.0, 0.0, 0.0);
        gluCylinder(glu.gluNewQuadric(), LOWER_LEG_RADIUS, LOWER_LEG_RADIUS * 1.5, LOWER_LEG_HEIGHT, 10, 10);
        glPopMatrix();
    }

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

}
