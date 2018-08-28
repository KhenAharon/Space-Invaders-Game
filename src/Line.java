import java.util.List;
/**
 * <p>Line class<p>.
 */
public class Line {

    private Point start;
    private Point end;

    /**
    * public Line.
    * <p>
    * This method difine line by poind end and point start
    * <p>
    * @param start - start point of line
    * @param end - end point of line
    */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }
   /**
   * @param x1 x1 location first P
   * @param y1 y1 location first P
   * @param x2 x2 location sec P
   * @param y2 y2 location sec P
   */
    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }
    /**
    * public double length().
    * <p>
    * This method calculate the length of the line using start and end points.
    * <p>
    * @return the length of the line.
    */
    public double length() {
        double dx = start.getX() - end.getX();
        double dy = start.getY() - end.getY();
        return Math.sqrt((dx * dx) + (dy * dy));
    }
    /**
    * public Point middle().
    * <p>
    * This method find the mid point of the line by its start and end point.
    * <p>
    * @return the middle point of the line.
    */
    public Point middle() {
        double mx = (start.getX() + end.getX()) / 2;
        double my = (start.getY() + end.getY()) / 2;
        return new Point(mx, my);
    }
    /**
    * public Point start().
    * <p>
    * This method find the start point of the line.
    * <p>
    * @return the start point of the line.
    */
    public Point start() {
        return this.start;
    }
    /**
    * public Point end().
    * <p>
    * This method find the end point of the line.
    * <p>
    * @return the end point of the line.
    */
    public Point end() {
        return this.end;
    }
    /**
    * private Line generateRandomLine().
    * <p>
    * This method calculate the slope between two points.
    * <p>
    * @return the slope calculated by the 2 points.
    */
    protected double findSlope() {
        double x1 = start.getX();
        double x2 = end.getX();
        double y1 = start.getY();
        double y2 = end.getY();
        double slope = (y2 - y1) / (x2 - x1);
        return slope;
    }
    /**
    * protected int getRelativeLocation.
    * <p>
    * This method check where the point is locatade relative to the line.
    * <p>
    * @param x1 - first point x
    * @param y1 - first point y
    * @param x2 - second point x
    * @param y2 - second point y
    * @param compX - comparable point x
    * @param compY - comparable point y
    * @return relative poisition of the comparable point. for example: 1 is under line, -1 is above line, 0 is on line.
    */
    protected int getRelativeLocation(double x1, double y1, double x2, double y2, double compX, double compY) {
        double relativeLoc;
        //this var will contain a number that represent a relative location of the point compare to the line
        x2 -= x1;
        y2 -= y1;
        compX -= x1;
        compY -= y1;
        relativeLoc = compX * y2 - compY * x2; //get relative location
        if (relativeLoc == 0.0) { //calc the location on the line, which side the point falls.
            relativeLoc = compX * x2 + compY * y2; //reverse value
            if (relativeLoc > 0.0) { //reverse projection of the point and check again
                compX -= x2;
                compY -= y2;
                relativeLoc = compX * x2 + compY * y2;
                if (relativeLoc < 0.0) { //in the middle
                relativeLoc = 0.0;
                }
            }
        }
        if (relativeLoc < 0) { //point is above line
            return -1;
        } else if (relativeLoc > 0) { //point is under line
            return 1;
        } else {
            return 0; //point is on line
        }
    }
    /**
    * public boolean isIntersecting.
    * <p>
    * This method check if two lines are Intersecting.
    * <p>
    * @param other - another line to check if intersected with current line.
    * @return true if they're intersected, false othrewize.
    */
    public boolean isIntersecting(Line other) {
        //relative location of current line and start point of second line
        int mult1 = getRelativeLocation(this.start.getX(), this.start.getY(),
                this.end.getX(), this.end.getY(), other.start.getX(), other.start.getY());
        //relative location of current line and end point of second line
        int mult2 = getRelativeLocation(this.start.getX(), this.start.getY(),
                this.end.getX(), this.end.getY(), other.end.getX(), other.end.getY());
        //relative location of current start point and second line
        int mult3 = getRelativeLocation(other.start.getX(), other.start.getY(),
                other.end.getX(), other.end.getY(), this.start.getX(), this.start.getY());
        //relative location of current end point and second line
        int mult4 = getRelativeLocation(other.start.getX(), other.start.getY(),
                other.end.getX(), other.end.getY(), this.end.getX(), this.end.getY());
        //if true, calculated by relative positions of the lines' points, then the lines are intersected.
        if ((mult1 * mult2 <= 0) && (mult3 * mult4 <= 0)) {
            return true;
        }
        return false;
    }
    /**
    * public Point intersectionWith.
    * <p>
    * This method check if two lines are Intersecting.
    * <p>
    * @param other - another line to check if intersected with current line.
    * @return the intersection point if the lines intersect,and null otherwise.
    */

public Point intersectionWith(Line other) {
        double a = this.end.getY() - this.start.getY();
        double b = this.start.getX() - this.end.getX();
        double a2 = other.end.getY() - other.start.getY();
        double b2 = other.start.getX() - other.end.getX();
        double c = a * this.start.getX() + b * this.start.getY();
        double c2 = a2 * other.start.getX() + b2 * other.start.getY();
        double det = a * b2 - a2 * b;
        double x = (b2 * c - b * c2) / det;
        double y = (a * c2 - a2 * c) / det;
        Point intersection = new Point(x, y);
        return intersection;
    }
    /**
    * public boolean equals.
    * <p>
    * this method check if two lines are equal return true if equal and false otherwise.
    * <p>
    * @param other - another line to check if two lines are equal.
    * @return true is the lines are equal, false otherwise.
    */
    public boolean equals(Line other) {
        if ((other.start.getX() == this.start.getX()
                && other.start.getY() == this.start.getY())
                && (other.end.getX() == this.end.getX()
                && other.end.getY() == this.end.getY())) {
            return true;
        }
        return false;
    }

    /**
     * Method will let know the collision point(obviously closest point).
     * @param rec collision rectangle.
     * @return closest collision point.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rec) {
        List<Point> points = rec.intersectionPoints(this);

        if (points.isEmpty()) {
            return null;
        }

        double minDist = this.start.distance(points.get(0));
        int closestIntersection = 0;

        for (int i = 1; i < points.size(); i++) {
            double tempDist = this.start.distance(points.get(i));
            if (tempDist < minDist) {
                minDist = tempDist;
                closestIntersection = i;
            }
        }

        return points.get(closestIntersection);
    }
    /**
     * Method will let know if a point is on the line.
     * @param p point.
     * @return true or false.
     */
    public boolean isPointOnTheLine(Point p) {
        if (p.distance(start) + p.distance(end) == start.distance(end)) {
            return true;
        }
        return false;
    }

}