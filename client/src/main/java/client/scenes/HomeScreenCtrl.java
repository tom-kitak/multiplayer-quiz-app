package client.scenes;

import client.utils.ServerUtils;
import commons.Player;
import commons.MultiGame;
import commons.SingleGame;
import commons.Question;
import jakarta.ws.rs.WebApplicationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import com.google.inject.Inject;
import commons.Question;


import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import server.Score;

import javafx.scene.input.KeyEvent;


public class HomeScreenCtrl {

    private ServerUtils server;
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
        String name;
        if (nameField.getText().length() == 0){
            name = "anonymous user";
        } else {
            name = nameField.getText();
        }
        Player player = new Player(name);


        Question question = server.getQuestion();

        SingleGame game = new SingleGame(player, question);

        mainCtrl.showQuizScreen(game);
    }

    @FXML
    void exitButtonPressed(ActionEvent event){
        mainCtrl.showServerAddress();
    }

    @FXML
    void howToPlay(){
        mainCtrl.showHowToPlay();
    }

    @FXML
    public void keyPressed(KeyEvent e){
        switch (e.getCode()){
            case ENTER:
                addNameAndScore();
                break;
            case ESCAPE:
                cancelEvent();
                break;
            default: break;
        }
    }

    /**
     * Method to get a new score object with the specified amount of points.
     * @param points The points to assign to the score object
     * @return A new score object with the specified points
     */
    Score getNewScore(int points, String name){
        Score score = new Score(points,name);
        return score;
    }

    void cancelEvent(){
        nameField.clear();
    }

    void addNameAndScore(){
        try{
            server.addScore(getNewScore(0, nameField.getText()));
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

    @FXML
    void adminToolsPressed(ActionEvent event) {
        mainCtrl.showAdministratorInterface();
    }

    @FXML
    void playMultiPlayerButtonPressed(ActionEvent event) {

        if (!server.checkConnection()) {
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
        String s = "";
        for(int i = 0; i<=12; i++){
            int index = generateIndex(51);
            char a = letters.charAt(index);
            s = s +a;
        }
        return s;

    }

    /**
     * @param max the max value of index we want to obtain.
     * @return a randomly generated Index
     */
    public int generateIndex(int max){
        double factor = Math.random();
        int result = (int) (Math.round(factor * max));
        return result;
    }

}
