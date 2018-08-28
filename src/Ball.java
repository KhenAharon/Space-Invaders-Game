import java.awt.Color;
import biuoop.DrawSurface;
/**
 * @author Khen Aharon
 */
public class Ball implements Sprite {
    /**
     * this class represent a Ball.
     */
    public static final int DEFAULT_VELOCITY = 2; // default velocity for a ball
    private int radius; // ball qualities
    private Color color;
    private Point point;
    private Velocity v;
    private Velocity tempV;
    private GameLevel g;
    private static GameEnvironment gameEnvironment = null;
    private static final int ALMOST_COLLISION = 1;
    /**
     * creates a ball.
     * @param center
     *            center point of ball
     * @param r
     *            radius
     * @param color
     *            color of ball
     */
    public Ball(Point center, int r, java.awt.Color color) {
        point = center;
        radius = r;
        this.color = color;
        this.v = new Velocity(DEFAULT_VELOCITY, DEFAULT_VELOCITY);
    }

    /**
     * creates a ball.
     * @param center
     *            - center point
     * @param r
     *            - radius
     * @param boardWidth
     *            - width of board
     * @param boardHeight
     *            -height of board
     * @param color
     *            - ball color
     */
    public Ball(Point center, int r, int boardWidth, int boardHeight, java.awt.Color color) {
        point = center;
        radius = r;
        this.color = color;
        this.v = new Velocity(DEFAULT_VELOCITY, DEFAULT_VELOCITY);
    }

    /**
     * @param x
     *            x for location point of ball
     * @param y
     *            y for location point of ball
     * @param r
     *            radius
     * @param color
     *            color of ball
     */
    public Ball(int x, int y, int r, java.awt.Color color) {
        point = new Point(x, y);
        radius = r;
        this.color = color;
        this.v = new Velocity(DEFAULT_VELOCITY, DEFAULT_VELOCITY);
    }

    /**
     * creates a ball.
     * @param x
     *            x loc. of ball
     * @param y
     *            y loc. of ball
     * @param r
     *            radius
     * @param boardWidth
     *            width of board of ball
     * @param boardHeight
     *            height of board
     * @param color
     *            ball color
     */
    public Ball(int x, int y, int r, int boardWidth, int boardHeight, java.awt.Color color) {
        point = new Point(x, y);
        radius = r;
        this.color = color;
        this.v = new Velocity(DEFAULT_VELOCITY, DEFAULT_VELOCITY);
    }

    /**
     * creates a ball.
     * @param center
     *            - the center of ball
     * @param r
     *            radius
     * @param color
     *            color of ball
     * @param v
     *            speed
     */
    public Ball(Point center, int r, java.awt.Color color, Velocity v) {
        point = center;
        radius = r;
        this.color = color;
        this.v = v;
    }

    /**
     * creates a ball.
     * @param x
     *            - the center of ball by x
     * @param y
     *            - the center of ball by y
     * @param r
     *            radius
     * @param color
     *            color of ball
     * @param v
     *            speed
     */
    public Ball(int x, int y, int r, java.awt.Color color, Velocity v) {
        point = new Point(x, y);
        radius = r;
        this.color = color;
        this.v = v;
    }

    /**
     * This method get the x value of the point.
     * @return x location in x coordinate
     */
    public int getX() {
        return (int) point.getX();
    }
    /**
     * simply return y.
     * @return the y value of the point.
     */
    public int getY() {
        return (int) point.getY();
    }
    /**
     * return the ball radius.
     * @return radius.
     */
    public int getSize() {
        return this.radius;
    }
    /**
     * return color of the ball.
     * @return color of ball.
     */
    public java.awt.Color getColor() {
        return this.color;
    }
    /**
     * draw ball on surface.
     * @param surface
     *            to draw on
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(color);
        surface.fillCircle((int) point.getX(), (int) point.getY(), radius);
    }
    /**
     * setting velocity of ball.
     * @param velo speed.
     * @param dt frames change.
     */
    public void setVelocity(Velocity velo, double dt) {
        this.v = new Velocity(velo.getDx() * dt, velo.getDy() * dt);
    }

    /**
     * This method sets velocity by x and y and return the velocity created.
     * @param dx
     *            difference in x in point
     * @param dt
     *            frames
     * @param dy
     *            difference in y in point
     */
    public void setVelocity(double dx, double dy, double dt) {
        this.v = new Velocity(dx * dt, dy * dt);
    }

    /**
     * public Velocity getVelocity(). This method get the Velocity.
     * @return velcoity (speed)
     */
    public Velocity getVelocity() {
        return this.v;
    }

    /**
     * step of the ball. changes are to be checked
     * @param dt frames change.
     */
    public void moveOneStep(double dt) {
        tempV = this.v.setVelocity(1);
        int nextX, nextY;
        nextX = (int) tempV.applyToPoint(this.point).getX();
        nextY = (int) tempV.applyToPoint(this.point).getY();
        for (int i = 0; i < ALMOST_COLLISION; i++) {
            nextX = (int) tempV.applyToPoint(new Point(nextX, nextY)).getX();
            nextY = (int) tempV.applyToPoint(new Point(nextX, nextY)).getY();
        }
        Line trajectory = new Line(this.point, new Point(nextX, nextY));
        CollisionInfo collided = Ball.gameEnvironment.getClosestCollision(trajectory);
        if (collided != null) { // collision!
            this.setVelocity(collided.getCollisionObject().hit(this, collided.getCollisionPoint(), tempV));
            /*
             * performs almost like the cmd:
             * this.getVelocity().applyToPoint(collided.getCollisionPoint()) but
             * without a bug
             */
            moveOneStepWithoutAlmostCollision();
            this.removeFromGame(g);
        } else {
            // move the point one step.
            this.point = this.getVelocity().applyToPoint(this.point);
            this.tempV = this.v.setVelocity(1);
        }
    }

    /**
     * similar to the above method, but here we don't calculate the future
     * appliance because we may have, two collisions consequently.
     */
    public void moveOneStepWithoutAlmostCollision() {
        this.tempV = this.v.setVelocity(1);
        this.point = this.getVelocity().applyToPoint(this.point);
        int nextX, nextY;
        nextX = (int) this.getVelocity().applyToPoint(this.point).getX();
        nextY = (int) this.getVelocity().applyToPoint(this.point).getY();
        Line trajectory = new Line(this.point, new Point(nextX, nextY));
        CollisionInfo collided = Ball.gameEnvironment.getClosestCollision(trajectory);
        if (collided != null) { // collision!
               this.setVelocity(collided.getCollisionObject().
                       hit(this, collided.getCollisionPoint(), this.v));
                this.tempV  = this.v.setVelocity(1);
                this.removeFromGame(g);
        } else {
            // move the point one step.
            this.point = this.getVelocity().applyToPoint(this.point);
        }
    }
    /**
     * @param vel velocity to set.
     */
    void setVelocity(Velocity vel) {
        this.v = vel;
    }
    /**
     * if environment wasn't created then create and return it, otherwise just
     * return it.
     * @return ball environment
     */
    public static GameEnvironment getGameEvironment() {
        if (gameEnvironment == null) {
            gameEnvironment = new GameEnvironment();
        }
        return Ball.gameEnvironment;
    }

    /**
     * add ball to game.
     * @param game
     *            is our game
     */
    public void addToGame(GameLevel game) {
        game.addSprite(this);
        this.g = game;
    }

    /**
     * what happens when times passes?- takes a step.
     * @param dt frames change.
     */
    @Override
    public void timePassed(double dt) {
        moveOneStep(dt);
    }
    /**
     * remove from game this ball.
     * @param ga
     *            our game.
     */
    public void removeFromGame(GameLevel ga) {
        ga.removeSprite(this);
    }
}
