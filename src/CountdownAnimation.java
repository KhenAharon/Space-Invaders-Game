import java.awt.Color;

import biuoop.DrawSurface;
/**
 * @author khen
 * count down from X to zero (in our example from 3 to 0).
 */
public class CountdownAnimation implements Animation {
    private double numOfSeconds;
    private int countFrom;
    private SpriteCollection gameScreen;
    private boolean stop;
    private long beginTime;
    private long relativeTime;
    private long beginRelativeTime;
    private LevelInformation levelInfo;
    /**
     * Ctor for countdown.
     * @param numOfSeconds seconds.
     * @param countFrom count from this to zero.
     * @param gameScreen where to display.
     * @param levelInfo info of level.
     */
    public CountdownAnimation(double numOfSeconds, int countFrom,
                              SpriteCollection gameScreen, LevelInformation levelInfo) {
        this.numOfSeconds = numOfSeconds;
        this.countFrom = countFrom;
        this.gameScreen = gameScreen;
        stop = false;
        beginTime = System.currentTimeMillis();
        beginRelativeTime = (long) (1000 * this.numOfSeconds / this.countFrom);
        relativeTime = beginRelativeTime;
        this.levelInfo = levelInfo;
    }
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        levelInfo.drawBackground(d);
        this.gameScreen.drawAllOn(d);
        d.setColor(Color.red);
        d.drawText(d.getWidth() / 2, d.getHeight() / 5, countFrom + "", 100);
        long passedTime = System.currentTimeMillis() - beginTime;
        if (passedTime > relativeTime) {
            countFrom--;
            relativeTime += beginRelativeTime;
        }
        if (countFrom == 0) {
            stop = true;
        }
    }
    @Override
    public boolean shouldStop() {
        return stop;
    }
    /**
     * reset the count down.
     */
    public void setAsNewCountdown() {
        this.relativeTime = beginRelativeTime;
        this.beginTime = System.currentTimeMillis();
    }
}
