package com.mikul17.footballsimulation;

import com.mikul17.footballsimulation.projekt.Simulation;
import com.mikul17.footballsimulation.projekt.Team;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;


public class TableViewStageController implements Initializable {

    @FXML
    private TableView<TeamModel> scoreboard ;
    @FXML
    private TableColumn<TeamModel, Integer> position;
    @FXML
    private TableColumn<TeamModel, String> teamName;
    @FXML
    private TableColumn<TeamModel, Integer> wins;
    @FXML
    private TableColumn<TeamModel, Integer> draws;
    @FXML
    private TableColumn<TeamModel, Integer> loses;
    @FXML
    private TableColumn<TeamModel, Integer> goalsScored;
    @FXML
    private TableColumn<TeamModel, Integer> goalsConceded;
    @FXML
    private TableColumn<TeamModel, Integer> goalsDifference;
    @FXML
    private TableColumn<TeamModel, Integer> points;
    @FXML
    private Label currentWeekLabel;

    public static ObservableList<TeamModel> teamsList = FXCollections.observableArrayList();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Simulation simulation = MainApplication.getSimulation();
        List<Team> sortedTeams = new ArrayList<>(simulation.getTeams());

        if(teamsList != null){
            teamsList.clear();
        }

        sortedTeams.sort((o1, o2) -> o2.getPoints() - o1.getPoints());


       for(int i=0; i<sortedTeams.size(); i++){
           teamsList.add(new TeamModel(i+1, sortedTeams.get(i).getName(),
                   sortedTeams.get(i).getWins(),
                   sortedTeams.get(i).getDraws(),
                   sortedTeams.get(i).getLosses(),
                   sortedTeams.get(i).getGoalsScored(),
                   sortedTeams.get(i).getGoalsConceded(),
                   sortedTeams.get(i).getPoints()));
       }


        position.setCellValueFactory(new PropertyValueFactory<>("position"));
        teamName.setCellValueFactory(new PropertyValueFactory<>("teamName"));
        wins.setCellValueFactory(new PropertyValueFactory<>("wins"));
        draws.setCellValueFactory(new PropertyValueFactory<>("draws"));
        loses.setCellValueFactory(new PropertyValueFactory<>("losses"));
        goalsScored.setCellValueFactory(new PropertyValueFactory<>("goalsScored"));
        goalsConceded.setCellValueFactory(new PropertyValueFactory<>("goalsConceded"));
        goalsDifference.setCellValueFactory(new PropertyValueFactory<>("goalDifference"));
        points.setCellValueFactory(new PropertyValueFactory<>("points"));



        scoreboard.setItems(teamsList);
        scoreboard.setFixedCellSize(55);
    }


    @FXML
    private void upcomingMatches(ActionEvent event) throws IOException {
        if(MainApplication.getSimulation().getPlayerTeam().getCapitan()==null || MainApplication.getSimulation().getPlayerTeam().getFreeKickTaker()==null || MainApplication.getSimulation().getPlayerTeam().getPenaltyTaker()==null){
            displayPopUp();
        }else{
            MainApplication.changeScene("NextMatchStage.fxml", Objects.requireNonNull(this.getClass().getResource("tableViewStage.css")).toExternalForm(), (Node) event.getSource());
        }
    }


    @FXML
    private void teamManagement(ActionEvent event) throws IOException {
       MainApplication.changeScene("TeamManagementStage.fxml", Objects.requireNonNull(this.getClass().getResource("tableViewStage.css")).toExternalForm(), (Node) event.getSource());
    }

    public void exit() throws SQLException {
       MainApplication.exit(MainApplication.getStage());
    }

    private static void displayPopUp () {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(250);
        window.setResizable(false);
        window.getIcons().add(new Image(Objects.requireNonNull(NextMatchStageController.class.getResource("football.png")).toExternalForm()));

        Label label = new Label();
        label.setText("You must first select your team capitan, free kick taker and penalty taker!");
        label.styleProperty().setValue("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: \"black\"; -fx-alignment: center;-fx-font-family: \"Gill Sans MT\";");

        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> window.close());


        VBox layout = new VBox(20);
        layout.paddingProperty().setValue(new Insets(20));
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    public void updateCurrentWeekLabel(){
        currentWeekLabel.setText("Current week: " + MainApplication.getSimulation().getCurrentWeek());
    }


}
