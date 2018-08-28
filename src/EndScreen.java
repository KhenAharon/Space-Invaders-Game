import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
/**
 * @author khen
 * end screen of winning or losing.
 */
public class EndScreen implements Animation {
    private KeyboardSensor keyboard;
    private boolean stop;
    private Counter score;
    private boolean isWinner;
    /**
     * @param k keyboard sensor.
     * @param score our score.
     * @param isWinner if we win.
     */
    public EndScreen(KeyboardSensor k, Counter score, boolean isWinner) {
        this.keyboard = k;
        this.stop = false;
        this.score = score;
        this.isWinner = isWinner;
    }
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        if (isWinner) {
            d.drawText(d.getWidth() / 4, d.getHeight() / 2, "You Win! Your score is " + score.getValue(), 32);
        } else {
            d.drawText(d.getWidth() / 4, d.getHeight() / 2, "Game Over. Your score is " + score.getValue(), 32);
        }
        if (this.keyboard.isPressed(KeyboardSensor.SPACE_KEY)) {
            this.stop = true;
        }
    }
    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}
