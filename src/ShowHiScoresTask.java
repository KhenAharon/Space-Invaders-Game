/**
 * @author khen
 */
public class ShowHiScoresTask implements Task<Void> {
    private AnimationRunner runner;
    private Animation highScoresAnimation;
    /**
     * @param runner the anim runner.
     * @param highScoresAnimation anim of high score.
     */
    public ShowHiScoresTask(AnimationRunner runner, Animation highScoresAnimation) {
        this.runner = runner;
        this.highScoresAnimation = highScoresAnimation;
    }
    /**
     * running anim.
     * @return void.
     */
    public Void run() {
            this.runner.run(this.highScoresAnimation);
        return null;
    }
}
