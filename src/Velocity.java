/**
 * Velocity of the ball with it's x and y axises.
 */
public class Velocity {
   /**
   * Velocity (dy, dx) represents changes in x and y.
   */
    private double dx;
    private double dy;
    /**
    * @param dx difference in x
    * @param dy difference in y
    */
   public Velocity(double dx, double dy) {
      this.dx = dx;
      this.dy = dy;
   }
   /**
   * public static Velocity fromAngleAndSpeed.
   * <p>
   * This method define the Velocity of the ball creating it by it's angle and speed.
   * <p>
   * @param angle the angle to which the ball will move
   * @param speed of ball
   * @return Velocity(dx, dy).
   */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double myAngle = (angle - 90) * Math.PI / 180;
        double dx = speed * Math.cos(myAngle);
        double dy = speed * Math.sin(myAngle);
        return new Velocity(dx, dy);
        }
    /**
    * This method getting dx from the point.
    * @return dx difference in x
    */
    public double getDx() {
        return dx;
        }
    /**
    * Getting dy from the point.
    * @return dy difference by y
    */
    public double getDy() {
        return dy;
        }
    /**
     * change x movement.
     * @param x movemen
     */
     public void setDx(double x) {
         this.dx = x;
         }
     /**
     * change y movement.
     * @param y movemen
     */
     public void setDy(double y) {
         this.dy = y;
         }
    /**
    * Take a point with position (x,y) and return a new point with position (x+dx, y+dy).
    * @param p a point to apply
    * @return point new after apply
    */
    public Point applyToPoint(Point p) {
        return new Point(p.getX() + dx, p.getY() + dy);
   }
    /**
     * Changes velocity of y by 180 degrees.
     */
    public void changeDyDirection() {
        this.dy = dy * (-1);
    }
    /**
     * Changes velocity of x by 180 degrees.
     */
    public void changeDxDirection() {
        this.dx = dx * (-1);
    }
    /**
     * The function update the velocity with dt.
     * @param dt is the dt
     * @return an update velocity
     */
    public Velocity setVelocity(double dt) {
        Velocity newVel = new Velocity(this.dx * dt, this.dy * dt);
        return newVel;
    }
}