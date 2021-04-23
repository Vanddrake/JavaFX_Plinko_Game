import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import java.util.Random;

/**
 * A class to create a Plinko Board object and track play data
 *
 * @author Robert Zaranek
 */

public class PlinkoBoard {

    /** A Plinko Puck object **/
    private final PlinkoPuck puck;
    /** Tracking of the current total score **/
    private int totalScore = 0;
    /** Tracking of the current horizontal Plinko Puck position on the Plinko Board grid **/
    private int xPuckPosition = 0;
    /** Tracking of the current amount of games played **/
    private int totalPlays = 0;

    /**
     * Constructor class that initializes a new Plinko Puck in a position on the Plinko Board grid
     */
    public PlinkoBoard() {
        puck = new PlinkoPuck((xPuckPosition*60)-45,50);
    }


    /**
     * Draws a Plinko Board on a GraphicsContext object
     *
     * @param gc The graphics context to draw on
     */
    public void drawBoard(GraphicsContext gc) {
        // Background color
        gc.setFill(Color.BLANCHEDALMOND);
        gc.fillRect(0, 0, 540, 550);
        // Goes down the rows of the Plinko Board grid
        for(int row=1;row<7;row++) {
            //Draws eight pegs on the first row and every alternating row thereafter
            for(int evenRow=1;evenRow<9; evenRow++) {
                gc.setFill(Color.CADETBLUE);
                gc.fillOval((60*evenRow)-5, 60*row, 10, 10);
            }
            //Draws seven pegs on the second row and every alternating row thereafter
            for(int oddRow=1;oddRow<8; oddRow++) {
                gc.setFill(Color.CADETBLUE);
                gc.fillOval((60*oddRow)+25, 30+(60*row), 10, 10);
            }
        }
        // Draws eight dividers along the bottom of the Plinko Board
        for(int divider=1;divider<9; divider++) {
            gc.setFill(Color.CADETBLUE);
            gc.fillRect(60*divider, 420, 2,30);
        }
        // Draws six "bumpers" along the left side of the Plinko Board
        for(int leftTri=1;leftTri<7; leftTri++) {
            gc.setFill(Color.CADETBLUE);
            gc.fillPolygon(new double[]{0, 30, 0},
                    new double[]{60*leftTri, 30+(60*leftTri), 60+(60*leftTri)}, 3);
        }
        // Draws six "bumpers" along the right side of the Plinko Board
        for(int rightTri=1;rightTri<7; rightTri++) {
            gc.setFill(Color.CADETBLUE);
            gc.fillPolygon(new double[]{540, 510, 540},
                    new double[]{60*rightTri, 30+(60*rightTri), 60+(60*rightTri)}, 3);
        }
        // Draws numbers for all the possible dropping points along the top of the Plinko Board
        for(int topNum=1;topNum<10; topNum++) {
            gc.setStroke(Color.INDIANRED);
            gc.setFont(Font.font("System", 15));
            gc.setLineWidth(2);
            gc.strokeText(String.valueOf(topNum), (topNum*60)-35, 40);
        }
        // Draws numbers for the possible amounts of money to win along the bottom of the Plinko Board
        gc.setStroke(Color.INDIANRED);
        gc.setFont(Font.font("System", 15));
        gc.setLineWidth(2);
        gc.strokeText("1\n0\n0", 25, 460);
        gc.strokeText("5\n0\n0", 85, 460);
        gc.strokeText("1\n0\n0\n0", 145, 460);
        gc.strokeText("0", 205, 460);
        gc.strokeText("10\n0\n0\n0", 265, 460);
        gc.strokeText("0", 325, 460);
        gc.strokeText("1\n0\n0\n0", 385, 460);
        gc.strokeText("5\n0\n0", 445, 460);
        gc.strokeText("1\n0\n0", 505, 460);
    }


    /**
     * Resets the current score and total games played back to 0
     */
    public void resetScore() {
        totalScore = 0;
        totalPlays = 0;
    }


    /**
     * Calculates the current average amount of winnings per game played, preventing the case of dividing by 0
     *
     * @return The average amount of winnings per game played
     */
    public int average() {
        if(totalPlays == 0) {
            return 0;
        }
        else {
            return totalScore/totalPlays;
        }
    }


    /**
     * Uses the current Plinko Board grid position of the Plinko Puck to return an integer of the current amount
     * of prize money won in this session and adds it to the total score
     *
     * @return Integer of the current amount of prize money won in this session
     */
    public int score() {
        if(xPuckPosition == 1 || xPuckPosition == 17) {
            totalScore += 100;
            return 100;
        }
        else if(xPuckPosition == 3 || xPuckPosition == 15) {
            totalScore += 500;
            return 500;
        }
        else if(xPuckPosition == 5 || xPuckPosition == 13) {
            totalScore += 1000;
            return 1000;
        }
        else if(xPuckPosition == 7 || xPuckPosition == 11) {
            return 0;
        }
        else {
            totalScore += 10000;
            return 10000;
        }
    }


    /**
     * The main gameplay loop method. It animates and moves the Plinko Puck object downwards along the
     * Plinko Board grid, on completion, it updates the horizontal Plinko Puck position
     * and adds one to the total games played
     *
     * @param gc The graphics context to draw on
     * @param puckPosition A horizontal position along the top of the Plinko Board grid
     */
    public void play(GraphicsContext gc, int puckPosition) {
        puck.setXandY((puckPosition*60)-45, 50);        // sets the starting point for the Plinko Puck
        puckPosition = (puckPosition*2)-1;          // adjusts the input position to fit on the Plinko Board grid
        Random random = new Random();
        // Waits a short time at the top of the Plinko Board
        Platform.runLater(() -> {
            drawBoard(gc);
            puck.draw(gc);
        });
        pause(750);
        // Moves the Plinko Puck vertically down the Plinko Board grid
        for(int moveVert=0; moveVert<12; moveVert++) {
            boolean randomMove = random.nextBoolean();      // Chooses a random direction for the Plinko Puck
            // Moves and animates the Plinko Puck to the right if it hits the left wall
            if(puckPosition == 1) {
                puckPosition++;
                for (int move=0; move<30; move++) {
                    Platform.runLater(() -> {
                        drawBoard(gc);
                        puck.draw(gc);
                    });
                    puck.moveRight();
                    pause(1000 / 60);
                }
            }
            // Moves and animates the Plinko Puck to the left if it hits the right wall
            else if(puckPosition == 17) {
                puckPosition--;
                for (int move=0; move<30; move++) {
                    Platform.runLater(() -> {
                        drawBoard(gc);
                        puck.draw(gc);
                    });
                    puck.moveLeft();
                    pause(1000 / 60);
                }
            }
            // Moves and animates the Plinko Puck to the right if randomMove is true
            else if(randomMove){
                puckPosition++;
                for (int move=0; move<30; move++) {
                    Platform.runLater(() -> {
                        drawBoard(gc);
                        puck.draw(gc);
                    });
                    puck.moveRight();
                    pause(1000 / 60);
                }
            }
            // Moves and animates the Plinko Puck to the left if randomMove is false
            else {
                puckPosition--;
                for (int move=0; move<30; move++) {
                    Platform.runLater(() -> {
                        drawBoard(gc);
                        puck.draw(gc);
                    });
                    puck.moveLeft();
                    pause(1000 / 60);
                }
            }
        }
        xPuckPosition = puckPosition;       // Updates the Plinko Puck's horizontal position on the grid
        totalPlays++;
    }


    /**
     * Use this method instead of Thread.sleep(). It handles the possible
     * exception by catching it, because re-throwing it is not an option in this
     * case.
     *
     * @param duration Pause time in milliseconds.
     */
    public static void pause(int duration) {
        try {
            Thread.sleep(duration);
        } catch (InterruptedException ex) {
        }
    }


    /**
     * toString method that returns the current total plays and the current total score
     *
     * @return The current total plays and the current total score
     */
    @Override
    public String toString() {
        return "Total Plays: " + totalPlays + "  |  Total Winnings: $" + totalScore +
                "  |  Avg. Winnings/Play: $" + average();
    }
}
