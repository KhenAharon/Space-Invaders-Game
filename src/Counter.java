/**
 * a simple counting class.
 */
public class Counter {
    private int counter;
    /**
     * ctor.
     * @param count is the count of something.
     */
    public Counter(int count) {
        this.counter = count;
    }
    /**
     * fun to increase value.
     * @param number to add.
     */
    public void increase(int number) {
        this.counter += number;
    }
    /**
     * fun to increase value.
     * @param number to subtract.
     */
    public void decrease(int number) {
        this.counter -= number;
    }
    /**
     * get value func.
     * @return current value.
     */
    public int getValue() {
        return this.counter;
    }
}