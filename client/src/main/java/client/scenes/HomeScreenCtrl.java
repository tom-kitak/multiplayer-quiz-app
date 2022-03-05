package client.scenes;

import client.ConfirmBoxCtrl;
import client.utils.ServerUtils;
import commons.Player;
import commons.Question;
import commons.SingleGame;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import com.google.inject.Inject;

import javafx.scene.control.TextField;


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

        //Question question = server.getQuestion();
        Question question = new Question("descr", 999);
        SingleGame game = new SingleGame(player, question);

        mainCtrl.showQuizScreen(game);
    }

    @FXML
    void exitButtonPressed(ActionEvent event){
        boolean answer = ConfirmBoxCtrl.display("Alert", "Are you sure you want to exit?");
        if(answer) System.exit(0);
    }

}
