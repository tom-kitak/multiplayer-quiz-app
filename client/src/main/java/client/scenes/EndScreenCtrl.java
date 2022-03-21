package client.scenes;


import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import server.Score;

import java.util.List;

public class EndScreenCtrl {


    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML
    private TableColumn<Score, String> usernames;

    @FXML
    private TableColumn<Score, Integer> score;

    @FXML
    private TableColumn<Score, Long> id;

    @FXML
    private TableView<Score> tableView;


    @Inject
    public EndScreenCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    @FXML
    void returnToHomePagePressed() {
        mainCtrl.showHomeScreen();
    }

    /**
     * Supposed to update the leaderboard with new information, does not work yet.
     */
    public void showLeaderboard(){
        List<Score> scores = server.getAllScores();
        ObservableList<Score> list = FXCollections.observableArrayList();
        scores.sort((x, y) -> Integer.compare(y.getScore(), x.getScore()));
        for(int i = 0; i < Math.min(10, scores.size()); ++i){
            list.add(scores.get(i));
        }
        tableView.setItems(list);
    }

}
