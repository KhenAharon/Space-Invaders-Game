import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
/**
 * KeyPressStoppableAnimation implements Animation - responsible of all the
 * KeyPressStoppable animation and some operations.
 *
 */
public class KeyPressStoppableAnimation implements Animation {
   private KeyboardSensor sensor;
   private String key;
   private Animation animation;
   private boolean flag;
   private boolean isAlreadyPressed;
   /**
    * construct a KeyPressStoppableAnimation according to given parameters.
    * @param sensor is the sensor
    * @param key is the key
    * @param animation is the animation to run
    */
   public KeyPressStoppableAnimation(KeyboardSensor sensor, String key, Animation animation) {
       this.sensor = sensor;
       this.key = key;
       this.animation = animation;
       this.flag = false;
       this.isAlreadyPressed = true;
   }
   /**
    * the function perform one frame of the game.
    * @param d is the surface to draw
    * @param dt is the speed indicator
    */
   @Override
    public void doOneFrame(DrawSurface d, double dt) {
        this.animation.doOneFrame(d, dt);
        if (this.sensor.isPressed(this.key)) {
            if (!this.isAlreadyPressed) {
                this.flag = true;
            }
       } else {
           this.isAlreadyPressed = false;
       }
   }
   /**
    * the function returns true if it should stop.
    * @return the function returns.
    */
    @Override
    public boolean shouldStop() {
        return flag;
    }
    /**
     * method to release stop flag.
     */
    public void unstop() {
        this.flag = false;
    }
}
