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
import commons.Score;

import javafx.scene.input.KeyEvent;


public class HomeScreenCtrl {

    private ServerUtils server;
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
    Score getNewScore(int points){
        Score score = new Score(points, nameField.getText());
        return score;
    }

    void cancelEvent(){
        nameField.clear();
    }

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

    @FXML
    void adminToolsPressed(ActionEvent event) {
        mainCtrl.showAdministratorInterface();
    }

    @FXML
    void playMultiPlayerButtonPressed(ActionEvent event) {
        String name;
        if (nameField.getText().length() == 0){
            name = generateRandomString();
        } else {
            name = nameField.getText();
        }
        Player player = new Player(name);

        mainCtrl.showWaitingRoom(player);
    }

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
