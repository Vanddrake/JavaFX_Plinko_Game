import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * A class to create a single Plinko Puck object
 *
 * @author Robert Zaranek (001161598)
 */

public class PlinkoPuck {

    /** The horizontal position of the Plinko Puck **/
    private int x;
    /** The vertical position of the Plinko Puck **/
    private int y;


    /**
     * Constructor class to create a Plinko Puck object, given an x and y coordinate
     *
     * @param x The puck's horizontal position
     * @param y The puck's vertical position
     */
    public PlinkoPuck(int x, int y) {
        this.x = x;
        this.y = y;
    }


    /**
     * Setter class to change the position of the Plinko Puck object
     *
     * @param x The puck's horizontal position
     * @param y The puck's vertical position
     */
    public void setXandY(int x, int y) {
        this.x = x;
        this.y = y;
    }


    /**
     * Draws a Plinko Puck on a GraphicsContext object
     *
     * @param gc The graphics context to draw on
     */
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.CRIMSON);
        gc.fillOval(x, y, 30, 30);
        gc.setStroke(Color.WHITESMOKE);
        gc.setFont(Font.font("System", 20));
        gc.setLineWidth(2);
        gc.strokeText("P", x+10, y+22);

    }


    /**
     * Modifies the position of the Plinko Puck object by 1 pixel down and 1 pixel left
     */
    public void moveLeft() {
        x--;
        y++;
    }


    /**
     * Modifies the position of the Plinko Puck object by 1 pixel down and 1 pixel right
     */
    public void moveRight() {
        x++;
        y++;
    }
}


