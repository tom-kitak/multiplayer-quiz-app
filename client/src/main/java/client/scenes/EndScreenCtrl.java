package client.scenes;

import commons.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import com.google.inject.Inject;
import client.utils.ServerUtils;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import commons.Score;

import java.util.ArrayList;
import java.util.List;

public class EndScreenCtrl {


    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML
    private TableView<Score> tableView;

    @FXML
    private TableColumn<Score, String> usernames;

    @FXML
    private TableColumn<Score, Long> id;

    @FXML
    private TableColumn<Score, Integer> score;

    @Inject
    public EndScreenCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
        this.tableView = new TableView<>();
        this.usernames = new TableColumn<>("Usernames");
        this.score = new TableColumn<>("Scores");
        this.id = new TableColumn<>("Id");
    }

    @FXML
    void returnToHomePagePressed(ActionEvent event) {
        mainCtrl.showHomeScreen();
    }

    /**
     * Creates the columns of the leaderboard.
     * Sets the columns to use the attributes of Score class and adds the players from the databse to the columns.
     */
    void initialize(boolean partyLeaderboard, List<Player> players){
        usernames.setCellValueFactory(new PropertyValueFactory<>("name"));
        score.setCellValueFactory(new PropertyValueFactory<>("score"));
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableView.getColumns().clear();
        tableView.getColumns().add(usernames);
        tableView.getColumns().add(score);
        tableView.getColumns().add(id);
        tableView.setItems(showLeaderboard(partyLeaderboard, players));
        ///System.out.println(tableView.getColumns().get(0).getCellData(0));
    }

    /**
     * Creates a list of all "Score" entities in the database, sorts them in descendin order.
     * @return an obeservable list of maximum 10 names and scores to be printed
     */
    ObservableList<Score> showLeaderboard(boolean partyLeaderboard, List<Player> players){
        List<Score> scores = new ArrayList<>();
        if(!partyLeaderboard) scores =  server.getAllScores();
        else {
            for (Player player : players) {
                scores.add(new Score(player.getScore(), player.getUsername()));
            }
        }
        ObservableList<Score> list = FXCollections.observableArrayList();
        scores.sort((x, y) -> Integer.compare(y.getScore(), x.getScore()));
        for (int i = 0; i < Math.min(10, scores.size()); ++i) {
            list.add(scores.get(i));
        }
        return list;
    }

}
