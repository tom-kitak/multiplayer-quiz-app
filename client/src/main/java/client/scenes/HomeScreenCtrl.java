package client.scenes;

import client.ConfirmBoxCtrl;
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
        boolean answer = ConfirmBoxCtrl.display("Alert", "Are you sure you want to exit?");
        if(answer) System.exit(0);
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
    Score getNewScore(){
        Score score = new Score(0, nameField.getText());
        return score;
    }

    void cancelEvent(){
        nameField.clear();
    }

    void addNameAndScore(){
        try{
            server.addScore(getNewScore());
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
            name = "anonymous user";
        } else {
            name = nameField.getText();
        }
        Player player = new Player(name);

        mainCtrl.showWaitingRoom();
    }

}
