package client.scenes;


import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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
        initialize();
    }

    @FXML
    void returnToHomePagePressed() {
        mainCtrl.showHomeScreen();
    }

    /**
     * Sets up the columns and gives each of them an attribute
     * of the Score class to take care of.
     */
    void initialize(){
        ///setting up the columns and the table
        usernames = new TableColumn<>("Username");
        score = new TableColumn<>("Scores");
        id = new TableColumn<>("Id");
        tableView = new TableView<>();

        ///giving each table column an attribute of score in order to use getters and setters
        ///from that declared field in the score class
        usernames.setCellValueFactory(new PropertyValueFactory<>("name"));
        score.setCellValueFactory(new PropertyValueFactory<>("score"));
        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        tableView.getColumns().add(usernames);
        tableView.getColumns().add(score);
        tableView.getColumns().add(id);

        tableView.setItems(showLeaderboard());

        ///the table receives the elements but does not print them on the screen
        System.out.println(tableView.getColumns().get(0).getCellData(0));
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
