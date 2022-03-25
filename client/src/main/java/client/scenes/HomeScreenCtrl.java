package client.scenes;

import client.utils.ServerUtils;
import commons.Player;
import commons.SingleGame;
import jakarta.ws.rs.WebApplicationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import com.google.inject.Inject;
import commons.Question;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import server.Score;

import javafx.scene.input.KeyEvent;


public class HomeScreenCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML
    private TextField nameField;

    /**Creates a new HomeScreenCtrl Object.
     * @param server the server we want to use
     * @param mainCtrl the MainController
     */
    @Inject
    public HomeScreenCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**The reaction when the play Single Player is pressed.
     * @param event the event we react on
     */
    @FXML
    void playSinglePlayerButtonPressed(ActionEvent event) {
        if (server.checkConnection()) {
            String name;
            if (nameField.getText().length() == 0) {
                name = "anonymous user";
            } else {
                name = nameField.getText();
            }
            Player player = new Player(name);


            Question question = server.getQuestion();

            SingleGame game = new SingleGame(player, question);

            mainCtrl.showQuizScreen(game);
        } else {
            throwError("There are not enough activities on this server to start a game!");
        }
    }

    /**
     * The method for when the exit button is pressed.
     * @param event The event which caused this method call
     */
    @FXML
    void exitButtonPressed(ActionEvent event){
        mainCtrl.showServerAddress();
    }

    /**
     * The method for when the how to play button is pressed.
     */
    @FXML
    void howToPlay(){
        mainCtrl.showHowToPlay();
    }

    /**
     * The method to handle key presses.
     * @param e The key event that caused this methods call.
     */
    @FXML
    public void keyPressed(KeyEvent e){
        switch (e.getCode()) {
            case ENTER -> addNameAndScore();
            case ESCAPE -> cancelEvent();
            default -> {
            }
        }
    }

    /**
     * Method to get a new score object with the specified amount of points.
     * @param points The points to assign to the score object
     * @return A new score object with the specified points
     */
    Score getNewScore(int points){
        return new Score(nameField.getText(), points);
    }

    /**
     * Method to call when the escape button is hit.
     */
    void cancelEvent(){
        nameField.clear();
    }

    /**
     * The method to add name and score to the server.
     */
    void addNameAndScore(){
        try{
            server.addScore(getNewScore(0));
        } catch (WebApplicationException e){

            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }
        cancelEvent();
        mainCtrl.showHomeScreen();
    }

    /**
     * Method to call when admin button is pressed.
     * @param event The event which caused this method call.
     */
    @FXML
    void adminToolsPressed(ActionEvent event) {
        mainCtrl.showAdministratorInterface();
    }

    /**
     * The method to call when multiplayer button is pressed.
     * @param event The event which caused this method call.
     */
    @FXML
    void playMultiPlayerButtonPressed(ActionEvent event) {
        if (server.checkConnection()) {
            String name;
            if (nameField.getText().length() == 0) {
                name = generateRandomString();
            } else {
                name = nameField.getText();
            }
            Player player = new Player(name);

            mainCtrl.showWaitingRoom(player);
        } else {
            throwError("There are not enough activities on this server to start a game!");
        }
    }

    /**
     * Method to generate a random string.
     * @return The randomly generated string
     */
    public String generateRandomString(){
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder s = new StringBuilder();
        for(int i = 0; i<=12; i++){
            int index = generateIndex(51);
            char a = letters.charAt(index);
            s.append(a);
        }
        return s.toString();

    }

    /**
     * @param max the max value of index we want to obtain.
     * @return a randomly generated Index
     */
    public int generateIndex(int max){
        double factor = Math.random();
        return (int) (Math.round(factor * max));
    }

    /**
     * Method to show an error prompt with the specified message.
     * @param errorMessage The error message to display
     */
    private void throwError(String errorMessage) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setContentText(errorMessage);
        a.show();
    }


}
