package jelly;


import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * MainWindow class
 */
public class MainWindow extends AnchorPane {

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Jelly jelly;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/Jelly_user.png"));
    private Image jellyImage = new Image(this.getClass().getResourceAsStream("/images/Jelly_icon.png"));
    private Image jellyExcited = new Image(this.getClass().getResourceAsStream("/images/Jelly_excited.png"));
    private Image jellyHardwork = new Image(this.getClass().getResourceAsStream("/images/Jelly_hardworking.png"));
    private Image jellySad = new Image(this.getClass().getResourceAsStream("/images/Jelly_sad.png"));
    private Image jellyConfused = new Image(this.getClass().getResourceAsStream("/images/Jelly_confused.png"));

    /**
     * Initializes Window
     */
    @FXML
    public void initialize() {

        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());

        DialogBox initialDialog = DialogBox.getJellyDialog("Hello! I'm Jelly\nWhat can I do for you?", jellyImage);

        scaleAnimation(1000, 0, 0, 0, initialDialog);
        scaleAnimation(100, 0.5, 1.1, 1000, initialDialog);
        scaleAnimation(100, 1.1, 1, 1100, initialDialog);

        dialogContainer.getChildren().addAll(initialDialog);
    }

    public void scaleAnimation(double duration, double from, double to, double delay, DialogBox dialog) {

        ScaleTransition r = new ScaleTransition();

        r.setDuration(Duration.millis(duration));
        r.setDelay(Duration.millis(delay));
        r.setFromX(from);
        r.setFromY(from);
        r.setToX(to);
        r.setToY(to);

        r.setNode(dialog);
        r.play();
    }

    public void setJelly(Jelly j) {

        jelly = j;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {

        String input = userInput.getText();

        String line = jelly.getResponse(input);

        String[] lines = line.split(" ", 2);

        String emotion = lines[0];
        String response = lines[1];

        Image mood;

        switch(emotion.toLowerCase()) {

        case "normal":
            mood = jellyImage;
            break;
        case "sad":
            mood = jellySad;
            break;
        case "confused":
            mood = jellyConfused;
            break;
        case "hardworking":
            mood = jellyHardwork;
            break;
        case "excited":
            mood = jellyExcited;
            break;
        default:
            mood = jellyImage;
            response = emotion + " " + response;
        }

        System.out.println(emotion);

        if (response.equals("bye")) {

            jelly.saveStorage();
            response = jelly.getFarewell();
        }

        DialogBox userDialog = DialogBox.getUserDialog(input, userImage);

        scaleAnimation(100, 0.5, 1.1, 0, userDialog);
        scaleAnimation(100, 1.1, 1, 100, userDialog);

        dialogContainer.getChildren().addAll(userDialog);

        DialogBox jellyDialog = DialogBox.getJellyDialog(response, mood);

        scaleAnimation(200, 0, 0, 0, jellyDialog);
        scaleAnimation(100, 0.5, 1.1, 200, jellyDialog);
        scaleAnimation(100, 1.1, 1, 300, jellyDialog);

        dialogContainer.getChildren().addAll(jellyDialog);

        userInput.clear();
    }
}
