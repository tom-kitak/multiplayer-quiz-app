package client.scenes;

import client.utils.ServerUtils;
import commons.Player;
import commons.MultiGame;
import commons.SingleGame;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import com.google.inject.Inject;
import commons.Question;


import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.ArrayList;


public class HomeScreenCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;


    @FXML
    private TextField nameField;

    @FXML
    private Label labelForUniqueUsername;

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
        if (server.checkActivities()) {
            String name;
            if (nameField.getText().length() == 0) {
                name = generateRandomString();
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
     * The client has the option of accessing the leaderboard without playing a game, from the home screen.
     */
    @FXML
    public void showLeaderBoard(){
        mainCtrl.showEndScreen(false, new ArrayList<>(){});
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

        if (!server.checkActivities()) {
            throwError("There are not enough activities on this server to start a game!");
        } else {

            String name;
            if (nameField.getText().length() == 0) {
                name = generateRandomString();
            } else {
                name = nameField.getText();
            }
            Player player = new Player(name);
            MultiGame multiGame = server.getLobby();
            boolean unique = checkForUnique(name, multiGame.getPlayers());
            if (unique) {
                mainCtrl.showWaitingRoom(player);
            } else {
                labelForUniqueUsername.setText("Choose a different username and" +
                        " click again");
            }
        }
    }

    public boolean checkForUnique(String name, ArrayList<Player> players2) {
        for(Player player: players2){
            if(player.getUsername().equals(name)){
                return false;
            }
        }
        return true;
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
