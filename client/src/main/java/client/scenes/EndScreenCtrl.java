package client.scenes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import com.google.inject.Inject;
import client.utils.ServerUtils;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import commons.Score;
import java.util.List;

public class EndScreenCtrl {


    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private XYChart.Series series;
    private List<Score> scores;

    @FXML
    private TableView<Score> tableView;

    @FXML
    private TableColumn<Score, String> usernames;

    @FXML
    private TableColumn<Score, Long> id;

    @FXML
    private TableColumn<Score, Integer> score;

    @FXML
    private BarChart<String, Number> barChart;

    @Inject
    public EndScreenCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
        this.tableView = new TableView<>();
        this.usernames = new TableColumn<>("Usernames");
        this.score = new TableColumn<>("Scores");
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Players");
        yAxis.setLabel("Scores");
        this.barChart = new BarChart<String, Number>(xAxis, yAxis);
        xAxis.setAutoRanging(true);
        this.series = new XYChart.Series();
    }

    @FXML
    void returnToHomePagePressed(ActionEvent event) {
        mainCtrl.showHomeScreen();
    }

    /**
     * Creates the columns of the leaderboard.
     * Sets the columns to use the attributes of Score class and adds the players from the databse to the columns.
     */
    void initialize(boolean partyLeaderboard, List<Score> players){
        usernames.setCellValueFactory(new PropertyValueFactory<>("name"));
        score.setCellValueFactory(new PropertyValueFactory<>("score"));
        tableView.getColumns().clear();
        tableView.getColumns().add(usernames);
        tableView.getColumns().add(score);
        tableView.setItems(showLeaderboard(partyLeaderboard, players));
        barChart.setLegendVisible(false);
        barChart.getData().clear();
        series.getData().clear();
        for(Score x : scores) {
            series.getData().add(new XYChart.Data(x.getName(), x.getScore()));
        }
        barChart.getData().add(series);
    }

    /**
     * Creates a list of all "Score" entities in the database, sorts them in descendin order.
     * @return an obeservable list of maximum 10 names and scores to be printed
     */
    ObservableList<Score> showLeaderboard(boolean partyLeaderboard, List<Score> scores){
        if(!partyLeaderboard) scores =  server.getTopScores();
        this.scores = scores;
        ObservableList<Score> list = FXCollections.observableArrayList();
        scores.sort((x, y) -> Integer.compare(y.getScore(), x.getScore()));
        for (int i = 0; i < scores.size(); ++i) {
            list.add(scores.get(i));
        }
        return list;
    }

}
