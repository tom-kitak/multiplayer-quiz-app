package client.scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import com.google.inject.Inject;
import client.utils.ServerUtils;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import server.Score;

import java.util.List;

public class EndScreenCtrl {


    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML
    private final TableView<Score> tableView;

    @FXML
    private final TableColumn<Score, String> usernames;

    @FXML
    private final TableColumn<Score, Integer> score;

    @Inject
    public EndScreenCtrl(ServerUtils server, MainCtrl mainCtrl, TableView<Score> tableView) {
        this.mainCtrl = mainCtrl;
        this.server = server;
        this.tableView = tableView;
        this.usernames = new TableColumn<>("Usernames");
        this.score = new TableColumn<>("Scores");
        tableView.getColumns().addAll(usernames, score);
    }

    @FXML
    void returnToHomePagePressed(ActionEvent event) {
        mainCtrl.showHomeScreen();
    }

    @FXML
    void showLeaderboard(){
        List<Score> list = server.getAllScores();
        list.sort((x, y) -> {
            if(x.getScore() > y.getScore()) return -1;
            if(x.getScore() == y.getScore()) return 0;
            return 1;
        });
        for(int i = 0; i < Math.min(10, list.size()); ++i){
            tableView.getItems().add(list.get(i));
        }
    }

}
