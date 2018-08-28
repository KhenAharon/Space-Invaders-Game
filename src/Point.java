/**
 * @author Khen Aharon
 */
public class Point {
    /**
     * <p>
     * This class define point gets its x and y values, find the distance
     * between 2 points.
     * <p>
     */
    private double x;
    private double y;

    /**
     * <p>
     * This method define point gets its x and y values of each point.
     * <p>
     *
     * @param x
     *            locationx
     * @param y
     *            locationy
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * double distance.
     * <p>
     * This method getting 2 points and calculate the distance between them.
     * <p>
     *
     * @param other
     *            comparable point
     * @return the distance of this point to the other point.
     */
    double distance(Point other) {
        double dx = this.x - other.getX();
        double dy = this.y - other.getY();
        return Math.sqrt((dx * dx) + (dy * dy));
    }

    /**
     * public double getX().
     * <p>
     * This method get the x value of this point.
     * <p>
     *
     * @return the x value of this point.
     */
    public double getX() {
        return this.x;
    }

    /**
     * public double getY().
     * <p>
     * This method get the y value of this point.
     * <p>
     *
     * @return the y value of this point.
     */
    public double getY() {
        return this.y;
    }

    /**
     * public boolean equals.
     * <p>
     * This method check it two points are equal or not.
     * <p>
     *
     * @param other
     *            comparable point
     * @return true is the points are equal, false otherwise.
     */
    public boolean equals(Point other) {
        if (other.getX() == this.x && other.getY() == this.y) {
            return true;
        }
        return false;
    }
    /**
     * Change x.
     * @param mx x axis
     */
    public void setX(int mx) {
        this.x = mx;
    }
    /**
     * Change y.
     * @param my y axis
     */
    public void setY(int my) {
        this.y = my;
    }
}
