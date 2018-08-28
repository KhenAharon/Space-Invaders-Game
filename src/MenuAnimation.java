import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import java.awt.Color;
import java.util.List;
import java.util.ArrayList;
/**
 * class MenuAnimation<T> - a menu task animation.
 * display the menu
 *
 * @param <T> is a generic task
 */
public class MenuAnimation<T> implements Menu<T> {
    private static final int HEIGHT = 600, WIDTH = 800;
    private String title;
    private KeyboardSensor sensor;
    private T status = null;
    private boolean flag;
    private List<String> subMessageList;
    private List<Menu<T>> subMenus;
    private List<String> keysList;
    private List<String> messageList;
    private List<T> returnValList;
    private List<String> subKeysList;
    private boolean isAlreadyPressed;
    private Menu<T> subStatus = null;
    /**
     * ctor.
     * @param title to show
     * @param sensor to use
     * @param runner to run
     */
    public MenuAnimation(String title, KeyboardSensor sensor, AnimationRunner runner) {
        this.title = title;
        this.sensor = sensor;
        this.flag = false;
        this.isAlreadyPressed = true;
        this.keysList = new ArrayList<String>();
        this.messageList = new ArrayList<String>();
        this.returnValList = new ArrayList<T>();
        this.subKeysList = new ArrayList<String>();
        this.subMessageList = new ArrayList<String>();
        this.subMenus = new ArrayList<Menu<T>>();
    }
    /**
     * @return the keys list.
     */
    public List<String> getKeysList() {
        return this.keysList;
    }
    /**
     * @return the ValList list.
     */
    public List<T> getReturnValList() {
        return this.returnValList;
    }
    /**
     * @return the messages list.
     */
    public List<String> getMessageList() {
        return this.messageList;
    }
    /**
     * @param d is the surface to draw
     * @param dt is the dt
     */
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
            drawOn(d);
        for (int i = 0; i < this.keysList.size(); i++) {
            if (this.sensor.isPressed(this.keysList.get(i))) {
                if (!this.isAlreadyPressed) {
                    this.status = this.returnValList.get(i);
                    this.flag = true;
                    break;
                } else {
                    this.isAlreadyPressed = false;
                }
            }
        }
            for (int i = 0; i < this.subKeysList.size(); i++) {
                if (this.sensor.isPressed(this.subKeysList.get(i))) {
                    if (!this.isAlreadyPressed) {
                        this.subStatus = this.subMenus.get(i);
                        this.flag = true;
                        break;
                    } else {
                        this.isAlreadyPressed = false;
                    }
                }
            }
    }

    /**
     * the function draw a screen.
     * @param d is the surface
     */
    public void drawOn(DrawSurface d) {
        java.awt.Color color = new Color(132, 255, 132);
        Block background = new Block(new Point(0, 0), WIDTH,
                HEIGHT, color, -1);
        background.drawOn(d);
        d.setColor(Color.GRAY);
        d.drawText(200, 70, title, 50);
        int location = 120;
        d.setColor(Color.red);
        for (int i = 0; i < this.subKeysList.size(); i++) {
            d.drawText(120, location, "(" + this.subKeysList.get(i) + ")", 20);
            d.drawText(145, location, this.subMessageList.get(i), 20);
            location += 50;
        }
        for (int i = 0; i < this.keysList.size(); i++) {
            d.drawText(120, location, "(" + this.keysList.get(i) + ")", 20);
            d.drawText(145, location, this.messageList.get(i), 20);
            location += 50;
        }
    }
    /**
     * the function returns true if it should stop.
     * @return the function returns
     */
    @Override
    public boolean shouldStop() {
        return this.flag;
    }
    /**
     * this func will add selection to the menu.
     * @param k to be add
     * @param m to be add
     * @param returnVal task to add
     */
    @Override
    public void addSelection(String k, String m, T returnVal) {
        this.keysList.add(k);
        this.messageList.add(m);
        this.returnValList.add(returnVal);
    }
    /**
     * the function restart the stop parameter.
     */
    public void restart() {
        this.flag = false;
        this.status = null;
        this.subStatus = null;
    }
    /**
     * @return a task status
     */
    @Override
    public T getStatus() {
        return this.status;
    }
    /**
     * @return returns a menuTask status.
     */
    public Menu<T> getSubStatus()  {
        return this.subStatus;
    }
    /**
     * func to add a subMenu according to the given parameter.
     * @param key is the key to add
     * @param message is the message to add
     * @param subMenu is the menu task to add
     */
    @Override
    public void addSubMenu(String key, String message, Menu<T> subMenu) {
        this.subKeysList.add(key);
        this.subMessageList.add(message);
        this.subMenus.add(subMenu);
    }
}
