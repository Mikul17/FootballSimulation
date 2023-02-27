package com.mikul17.footballsimulation;

import com.mikul17.footballsimulation.projekt.DBmanager;
import com.mikul17.footballsimulation.projekt.Simulation;
import com.mikul17.footballsimulation.projekt.Team;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WinnerStageController {
    private final Simulation simulation = MainApplication.getSimulation();
    private final List<Team> scoreboard = new ArrayList<>(MainApplication.getSimulation().getTeams());

    @FXML
    private Label winnerTeamLabel;
    @FXML
    private Button showWinner;
    @FXML
    private Button nextSeason;
    @FXML
    private Button exitButton;
    @FXML
    private Text text;
    @FXML
    private ImageView trophyImage;


    public void changeScene(ActionEvent event) throws IOException {
        DBmanager.createNextSeasonTables();
        for(Team team : simulation.getTeams()){
            DBmanager.updateNextSeasonTeamValues(team.getName());
        }
        simulation.resetTeamScore();
        updateAge();
        simulation.addSeasonsPlayed();
        MainApplication.changeScene("tableViewStage.fxml", Objects.requireNonNull(this.getClass().getResource("tableViewStage.css")).toExternalForm(), (Node) event.getSource());
    }

    private void updateAge(){
        for(Team team : simulation.getTeams()){
            for(int i = 0; i < team.getPlayerList().size(); i++){
                team.getPlayerList().get(i).incrementAge();
            }
            for(int i=0; i<team.getSubstitutes().size();i++){
                team.getSubstitutes().get(i).incrementAge();
            }
        }
    }
    public void handleCloseButtonAction() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
    public void showWinner() {
        showWinner.setDisable(true);
        showWinner.setVisible(false);
        nextSeason.setDisable(false);
        nextSeason.setVisible(true);
        text.setVisible(true);
        trophyImage.setVisible(true);
        Team winner = getWinner();


        winnerTeamLabel.setText(winner.getName());
        if(Objects.equals(winner.getName(), simulation.getPlayerTeam().getName())){
            simulation.setSeasonsWon(simulation.getSeasonsWon()+1);
        }
    }

    private Team getWinner(){
        scoreboard.sort((o1, o2) -> o2.getPoints() - o1.getPoints());
        List<Team> mostPoints = new ArrayList<>();
        for(Team team : scoreboard){
            if(team.getPoints() == scoreboard.get(0).getPoints()){
                mostPoints.add(team);
            }
        }
        mostPoints.sort((o1, o2) -> o2.getGoalsDifference() - o1.getGoalsDifference());
        return  mostPoints.get(0);
    }

}
