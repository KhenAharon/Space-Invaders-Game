import java.awt.Color;
import java.util.Iterator;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * @author Khen Aharon
 */
public class GameLevel implements Animation {
    /**
     * class Game will create a new game with balls, blocks and paddle, and
     * activate the whole games's functions.
     */

    private LevelInformation info;
    private Counter score = new Counter(0);
    private Counter blockCount = new Counter(0);
    private Counter numberOfLives = new Counter(0);
    private ScoreTrackingListener tracker;
    private DeathListener deathListener;
    private BlockRemover blockRem;
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private java.util.List<Block> blocks;
    private Paddle paddle;
    public static final int BOARD_WIDTH = 800;
    public static final int BOARD_HEIGHT = 600;
    public static final int BALLS_NO = 2;
    public static final int DEGREES = 360;
    public static final int WALLS_NO = 3;
    private AnimationRunner runner;
    private boolean running;
    private KeyboardSensor keyboard;
    //for timing
    private int flag;
    private long startMiliSeconds;
    private long elapsedTimeMillis;
    private float elapsedTimeSec;

    /**
     * @param info
     *            our level info
     * @param r
     *            animation runner to run animation.
     * @param k
     *            keyboard sensor.
     * @param lives
     *            no of lives.
     * @param blockcount
     *            no of blocks.
     * @param scores
     *            the score.
     */
    public GameLevel(LevelInformation info, AnimationRunner r, KeyboardSensor k, Counter lives, Counter blockcount,
            Counter scores) {
        this.score = scores;
        this.blockCount = blockcount;
        this.numberOfLives = lives;
        this.keyboard = k;
        this.runner = r;
        this.info = info;
    }

    /**
     * @param c
     *            add this Collidable.
     */
    public void addCollidable(Collidable c) {
        environment.addCollidable(c);
    }

    /**
     * @param c
     *            collidable to remove.
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }

    /**
     * @param s
     *            add this sprite.
     */
    public void addSprite(Sprite s) {
        sprites.addSprite(s);
    }

    /**
     * @param s
     *            sprite to remove.
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }

    /**
     * Create ball, blocks, paddle...the whole game environment.
     */
    public void initialize() {
        this.blockCount = new Counter(info.numberOfBlocksToRemove());
        this.blockRem = new BlockRemover(this, this.blockCount);
        this.tracker = new ScoreTrackingListener(this.score);
        this.deathListener = new DeathListener();
        sprites = new SpriteCollection();
        environment = Ball.getGameEvironment();

        ScoreIndicator scoreIndi = new ScoreIndicator(new Rectangle(new Point(0, 0),
                                                      BOARD_WIDTH, 25), this.score);
        LevelNameIndicator levelName = new LevelNameIndicator(new Rectangle(new Point(0, 0), BOARD_WIDTH, 25),
                this.info.levelName());
        LivesIndicator liveIndi = new LivesIndicator(new Rectangle(new Point(0, 0), BOARD_WIDTH, 25),
                this.numberOfLives);

        scoreIndi.addToGame(this);
        liveIndi.addToGame(this);
        levelName.addToGame(this);
        blocks = this.info.blocks();
        Iterator<Block> iter1 = blocks.iterator();
        while (iter1.hasNext()) {
            iter1.next().addToGame(this);
        }
        Iterator<Block> iter2 = blocks.iterator();
        while (iter2.hasNext()) {
            iter2.next().addHitListener(blockRem);
        }
        Iterator<Block> iter3 = blocks.iterator();
        while (iter3.hasNext()) {
            iter3.next().addHitListener(tracker);
        }
        Iterator<Block> iter4 = blocks.iterator();
        while (iter4.hasNext()) {
            iter4.next().addDeathListener(deathListener);
        }
    }

    /**
     * remove all elements from level.
     */
    public void removeElements() {
        this.environment.deplete();
        this.sprites.deplete();
    }

    /**
     * create balls on top of the paddle and the paddle.
     */
    private void createBallsOnTopOfPaddle() {
        paddle = new Paddle(new Point((BOARD_WIDTH - info.paddleWidth()) / 2, BOARD_HEIGHT - 20 - 25),
                info.paddleWidth(), 20, Color.ORANGE, this.keyboard);
        paddle.setSpeed(info.paddleSpeed());
        paddle.addToGame(this);
    }

    /**
     * if to stop the animation.
     * @return if the animation should stop.
     */
    @Override
    public boolean shouldStop() {
        return !running;
    }

    /**
     * Run the game.
     */
    public void playOneTurn() {
        this.createBallsOnTopOfPaddle();
        // Count Down 3 2 1 Go (during 2 seconds).
        this.runner.run(new CountdownAnimation(2, 3, this.sprites, info));
        running = true;
        deathListener.resetDeath();
        // the loop that runs the game
        this.runner.run(this);
    }

    /**
     * implement of the animation interface.
     * @param dt
     *            frames change.
     * @param d
     *            surface to draw.
     */
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        if (blockCount.getValue() == 0 || deathListener.death() || this.paddle.getHit()) {
            running = false;
        }
        info.drawBackground(d);
        this.sprites.drawAllOn(d);
        this.sprites.notifyAllTimePassed(dt);
        if (this.keyboard.isPressed("p")) {
            this.runner.run(new PauseScreen(this.keyboard));
        }
        if (this.flag == 0) {
            this.startMiliSeconds = System.currentTimeMillis();
            this.flag++;
        }
        this.elapsedTimeMillis = System.currentTimeMillis() - startMiliSeconds;
        this.elapsedTimeSec = elapsedTimeMillis / 1000F;

        if (elapsedTimeSec > 0.5) {
            Collidable alien = this.environment.getAnyLowestAlien();
            if (alien != null) {
                Rectangle alienRect = alien.getCollisionRectangle();
                if (alienRect != null) {
                    Point alienP = alienRect.getUpperLeft();
                    Point shootP = new Point(alienP.getX() + 20, alienP.getY() + 45);
                    Ball alienShootBall = new Ball(shootP, 3, Color.red);
                    alienShootBall.setVelocity(new Velocity(0, 5));
                    alienShootBall.addToGame(this);
                    this.startMiliSeconds = System.currentTimeMillis();
                }
            }
        }
    }
    /**
     * run the level.
     */
    public void run() {
        while (numberOfLives.getValue() > 0) {
            playOneTurn();
            if (blockCount.getValue() == 0) {
                runner.runOneFrame(this);
                removeElements();
                //upper the aliens
                Iterator<Block> iter1 = blocks.iterator();
                while (iter1.hasNext()) {
                    iter1.next().resetRect();
                }
                return;
            }
            this.paddle.removeFromGame(this);
            numberOfLives.decrease(1);
            //upper the aliens
            Iterator<Block> iter1 = blocks.iterator();
            while (iter1.hasNext()) {
                iter1.next().resetRect();
            }
        }
        removeElements();
        return;
    }

    /**
     * get lives func.
     * @return number of lives
     */
    public int getNumberOfLives() {
        return numberOfLives.getValue();
    }

    /**
     * get blocks number.
     * @return number of blocks.
     */
    public int getBlockCount() {
        return blockCount.getValue();
    }
}
