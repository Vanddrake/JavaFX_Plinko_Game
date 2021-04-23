import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import static java.lang.Integer.parseInt;

/**
 * A program that allows the user to play a game of Plinko, including features such as animation and score tracking.
 * July 19, 2020.
 *
 * @author Robert Zaranek
 */

public class MainView extends Application {

    /** a Plinko Board object **/
    private PlinkoBoard board;
    /** a Graphic Content object **/
    private GraphicsContext gc;
    /** a label displaying current gameplay data **/
    private Label scoreLabel;
    /** a label displaying relevant notifications for the user **/
    private Label notifLabel;
    /** the textfield where the user inputs their chosen location to drop the Plinko Puck **/
    private TextField boardPositionField;
    /** a button the user can press to begin the main gameplay loop **/
    private Button playButton;
    /** a button the user can press to reset gameplay data **/
    private Button resetButton;


    /**
     * An event handler that begins the main gameplay loop when the user presses the Play button
     *
     * @param e Button press event
     */
    private void playHandler(ActionEvent e) {
        int newX = parseInt(boardPositionField.getText());      // Stores and converts the input value into an integer
        // Checks for the correct integer to begin gameplay
        if(newX <= 9 && newX >= 1) {
            playButton.setDisable(true);        // Disables buttons while the animation plays
            resetButton.setDisable(true);
            Thread t = new Thread(() -> {       // New thread to play animations and the play method
                board.play(gc,newX);
                playButton.setDisable(false);   // Re-enable buttons on animation completion
                resetButton.setDisable(false);
                Platform.runLater(() -> {       // Updates labels on animation completion
                    notifLabel.setText("You won $" + board.score() + " dollars!");
                    scoreLabel.setText(String.valueOf(board));
                });
            });
            t.start();
        }
        // Displays an error message if the user inputs an out of range value
        else {
            notifLabel.setText("Choose 1 to 9!");
        }
    }


    /**
     * An event handler that sets total score and total games played to 0
     *
     * @param e Button press event
     */
    private void resetHandler(ActionEvent e) {
        board.resetScore();
        notifLabel.setText("Total score reset!");       // Notifies user of successful reset
        scoreLabel.setText(String.valueOf(board));
    }


    /**
     * The starting method for the program
     *
     * @param stage The main stage
     */
    @Override
    public void start(Stage stage) {
        Pane root = new Pane();
        Scene scene = new Scene(root, 540, 630); // set the size here
        stage.setTitle("Plinko!"); // set the window title here
        stage.setScene(scene);

        // Creates the Plinko Board model
        board = new PlinkoBoard();

        // Creates the GUI components
        Canvas canvas = new Canvas(540, 550);
        boardPositionField = new TextField("1");
        playButton = new Button("Play Plinko!");
        resetButton = new Button("Reset");
        scoreLabel = new Label(String.valueOf(board));
        notifLabel = new Label("Choose a starting point\n\t\tand press Play!");

        // Adds components to the root
        root.getChildren().addAll(canvas, boardPositionField, playButton, resetButton, scoreLabel, notifLabel);

        // Configures the component's fonts, size, and location
        boardPositionField.relocate(5, 560);
        playButton.relocate(160, 560);
        resetButton.relocate(245, 560);
        scoreLabel.relocate(10, 600);
        scoreLabel.setFont(new Font("System", 12));
        notifLabel.relocate(310, 558);
        notifLabel.setFont(new Font("System", 20));

        // Adds Listeners and does final setup
        playButton.setOnAction(this::playHandler);
        resetButton.setOnAction(this::resetHandler);
        gc = canvas.getGraphicsContext2D();
        board.drawBoard(gc);

        // Shows the stage
        stage.show();
    }


    /**
     * The main method
     *
     * @param args unused
     */
    public static void main(String[] args) {
        launch(args);
    }
}
