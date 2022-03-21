package client.scenes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import com.google.inject.Inject;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import commons.Score;

import java.util.List;

public class EndScreenCtrl {


    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML
    private TableColumn<Score, String> usernames;

    @FXML
    private TableColumn<Score, Integer> score;

    @FXML
    private final TableColumn<Score, Long> id;

    @FXML
    private final TableColumn<Score, Integer> score;

    @Inject
    public EndScreenCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
        this.tableView = new TableView<>();
        this.usernames = new TableColumn<>("Usernames");
        this.score = new TableColumn<>("Scores");
        this.id = new TableColumn<>("Id");
        usernames.setCellValueFactory(new PropertyValueFactory<>("name"));
        score.setCellValueFactory(new PropertyValueFactory<>("score"));
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableView.getColumns().addAll(usernames, score, id);
        tableView.setItems(showLeaderboard());
    }

    @FXML
    void returnToHomePagePressed() {
        mainCtrl.showHomeScreen();
    }

    /**
     * Creates a list of all "Score" entities in the database, sorts them in descendin order.
     * @return an obeservable list of maximum 10 names and scores to be printed
     */
    ObservableList<Score> showLeaderboard(){
        List<Score> scores = server.getAllScores();
        ObservableList<Score> list = FXCollections.observableArrayList();
        scores.sort((x, y) -> Integer.compare(y.getScore(), x.getScore()));
        for(int i = 0; i < Math.min(10, scores.size()); ++i){
            list.add(scores.get(i));
        }
        return list;
    }

}
