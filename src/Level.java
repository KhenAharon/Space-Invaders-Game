import java.awt.Color;
import java.awt.Image;
import java.util.LinkedList;
import java.util.List;
import biuoop.DrawSurface;
/**
 * class Level implements LevelInformation - responsible of all the set levels,
 * their information, and operations of them.
 *
 */
public class Level implements LevelInformation {
    private int numberOfBalls;
    private Integer paddleSpeed;
    private Integer paddleWidth;
    private Integer blocksNumber;
    private String name;
    private String ballVelocities;
    private String backgroundColor;
    private List<Velocity> velocityBallsList;
    private List<Block> blocksList;
    private java.awt.Color color;
    private Image img;
    /**
     * Constructor of level according to given parameters.
     * @param paddleSpeed is the paddle speed
     * @param paddleWidth is the paddle's width
     * @param numberOfBlocks is the number of blocks
     * @param levelName is the level name
     * @param ballVelocities is the ball velocity
     * @param backgroundColor is the back ground level
     * @param blocksList is the blocklist
     */
    public Level(Integer paddleSpeed, Integer paddleWidth, Integer numberOfBlocks, String levelName,
                 String ballVelocities, String backgroundColor, List<Block> blocksList) {
        this.name = levelName;
        this.blocksNumber = numberOfBlocks;
        this.ballVelocities = ballVelocities;
        this.numberOfBalls = 0;
        this.paddleSpeed = paddleSpeed;
        this.paddleWidth = paddleWidth;
        this.backgroundColor = backgroundColor;
        this.blocksList = blocksList;
        String[] type = this.backgroundColor.split("\\(");
        ColorsParser c = new ColorsParser();
        Color colorC = null;
        if (type[0].equals("color")) {
            colorC = c.colorFromString(type);
            this.color = colorC;
            this.img = null;
        } else {
            if (type[0].equals("image")) {
                Image image = c.imageFromString(type);
                this.img = image;
                this.color = null;
            }
        }
    }
    /**
     * the function return number of balls at this level.
     * @return number of balls at this level
     */
    public int numberOfBalls() {
        return this.numberOfBalls;
    }
    /**
     * The function initial velocity of each ball.
     * @return new list with the velocity of each ball
     */
    public java.util.List<Velocity> initialBallVelocities() {
        return this.velocityBallsList;
    }
    /**
     * the function returns a list of velocities.
     * @return  a list of velocities.
     */
    public java.util.List<Velocity> initialVelocities() {
        List<Velocity> velocityBalls = new LinkedList<Velocity>();
        String[] strVelocity = this.ballVelocities.split(" ");
        for (int i = 0; i < strVelocity.length; i++) {
            String[] strVelocity2 = strVelocity[i].split(",");
            if ((strVelocity2.length == 2) && (!strVelocity2[0].isEmpty())) {
                velocityBalls.add(Velocity.fromAngleAndSpeed(
                        Integer.valueOf(strVelocity2[0]),
                        Integer.valueOf(strVelocity2[1])));
                this.numberOfBalls++;
            } else {
                return null;
            }

        }
        return velocityBalls;
    }
    /**
     * the function return the paddle speed.
     * @return the paddle speed
     */
    public int paddleSpeed() {
        return  this.paddleSpeed;
    }
    /**
     * the function return the paddle width.
     * @return the paddle width
     */
    public int paddleWidth() {
        return this.paddleWidth;
    }
    /**
     * the function return the level name that be displayed at the
     *  top of the screen.
     * @return the level name.
     */
    public String levelName() {
        return this.name;
    }
    /**
     * the function returns a sprite with the background of the level.
     * @return background of the level
     */
    public Sprite getBackground() {
        Background background = new Background(this);
        return background;
    }
    /**
     * the function draw the sprite to the screen.
     * @param d is the surface to draw on it
     */

    public void drawBackground(DrawSurface d) {
        if (this.color != null) {
            Block background = new Block(new Point(0, 0), 800,
                    600, this.color, -1);
            background.drawOn(d);
        } else {
            d.drawImage(0, 0, this.img);
        }
    }
    /**
     * the function check if all the members are fine- update the level.
     * @return true if it's fine
     */
    public boolean initLevel() {
        if (this.name == null) {
            return false;
        }
        if (this.ballVelocities == null) {
            return false;
        } else {
            this.velocityBallsList = new LinkedList<Velocity>();
            this.velocityBallsList = initialVelocities();
            if (this.velocityBallsList == null) {
                return false;
            }
        }
        if (this.paddleSpeed == null) {
            return false;
        }
        if (this.paddleWidth == null) {
            return false;
        }
        if (this.blocksNumber == null) {
            return false;
        }
        if (this.blocksList.isEmpty()) {
            return false;
        }
        return true;
    }
    /**
     * The function return a list of the Blocks that make up this level,
     * each block contains its size, color and location.
     * @return a list of the Blocks that make up this level
     */
    public java.util.List<Block> blocks() {
        return this.blocksList;

    }

    /**
     * the function return number of blocks that should be removed at this
     * level before the level is considered to be "cleared".
     * @return number of blocks at this level that should be removed at this
     * level before the level
     */
    public int numberOfBlocksToRemove() {
        return this.blocksNumber;
    }

}