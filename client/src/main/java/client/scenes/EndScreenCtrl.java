package client.scenes;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import com.google.inject.Inject;
import client.utils.ServerUtils;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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

        usernames = new TableColumn<>("Username");
        score = new TableColumn<>("Scores");
        id = new TableColumn<>("Id");
        tableView = new TableView<>();

        usernames.setCellValueFactory(new PropertyValueFactory<>("name"));
        score.setCellValueFactory(new PropertyValueFactory<>("score"));
        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        tableView.getColumns().addAll(usernames, score, id);
        tableView.getItems().addAll(showLeaderboard());
    }

    @FXML
    void returnToHomePagePressed(ActionEvent event) {
        mainCtrl.showHomeScreen();
    }

    ObservableList<Score> showLeaderboard(){
        List<Score> scores = server.getAllScores();
        ObservableList<Score> list = FXCollections.observableArrayList();
        scores.sort((x, y) -> {
            if(x.getScore() > y.getScore()) return -1;
            return 1;
        });
        for(int i = 0; i < Math.min(10, scores.size()); ++i){
            list.add(scores.get(i));
        }
        return list;
    }

}
