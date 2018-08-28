/**
 * interface Menu<T> - responsible of displaying menu task animation.
 *
 * @param <T> - is a generic task
 */
public interface Menu<T> extends Animation {
    /**
     * the function add a selection.
     * @param key is the key to add
     * @param message is the message to add
     * @param returnVal is the task to add
     */
   void addSelection(String key, String message, T returnVal);
   /**
    * the function returns a current task.
    * @return a current task.
    */
   T getStatus();
   /**
    * the function adds a Menu task.
    * @param key is the key to add
    * @param message is the message to add
    * @param subMenu is the menu task to add
    */
   void addSubMenu(String key, String message, Menu<T> subMenu);
   /**
    * the function restart an object to it's primary values.
    */
   void restart();
   /**
    * the function returns a current MENU task.
    * @return a current MENU task.
    */
   Menu<T> getSubStatus();
}
