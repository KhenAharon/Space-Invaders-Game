import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

/**
 * @author khen runner of the animation
 */
public class AnimationRunner {
    private GUI gui;
    private int framesPerSecond;
    private double secs;
    /**
     * @param framesPerSecond will be this number of frames every second.
     * @param gui our gui of animation.
     * @param dt - frames changer.
     */
    public AnimationRunner(int framesPerSecond, GUI gui, double dt) {
        this.framesPerSecond = framesPerSecond;
        this.gui = gui;
        this.secs = dt;
    }
    /**
     * @param animation to run.
     */
    public void run(Animation animation) {
       Sleeper sleeper = new biuoop.Sleeper();
       int millisecondsPerFrame = 1000 / framesPerSecond;
       while (!animation.shouldStop()) {
          long startTime = System.currentTimeMillis(); // timing
          DrawSurface d = gui.getDrawSurface();
          animation.doOneFrame(d, secs);
          gui.show(d);
          long usedTime = System.currentTimeMillis() - startTime;
          long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
          if (milliSecondLeftToSleep > 0) {
              sleeper.sleepFor(millisecondsPerFrame);
          }
       }
    }
    /**
     * @param animation
     *            to run but only one frame.
     */
    public void runOneFrame(Animation animation) {
        DrawSurface d = gui.getDrawSurface();
        animation.doOneFrame(d, secs);
        gui.show(d);
    }
    /**
     * @return dt of frames.
     */
    public double getSecs() {
        return this.secs;
    }

    /**
     * @return the gui.
     */
    public GUI getGui() {
        return this.gui;
    }
}
