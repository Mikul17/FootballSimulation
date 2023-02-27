package com.mikul17.footballsimulation;

import com.mikul17.footballsimulation.projekt.DBmanager;
import com.mikul17.footballsimulation.projekt.Simulation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;

import java.io.IOException;
import java.util.Objects;

public class StartingStageController {

    @FXML
    public void newSimulation(ActionEvent event) throws IOException {

        DBmanager.dropDatabase();
        DBmanager.prepareDatabase();
        Simulation simulation = MainApplication.getSimulation();
        simulation.createTeams();
        simulation.createStartingElevensAndBenches();
        simulation.assignStatsToPlayers();
        simulation.setCaptains();
        simulation.calculateOverallForPlayers();

        MainApplication.changeScene("selectYourTeamStage.fxml", Objects.requireNonNull(this.getClass().getResource("selectYourTeamStage.css")).toExternalForm(), (Node) event.getSource());
    }

    public void continueSimulation(ActionEvent event) throws IOException {
        Simulation simulation = MainApplication.getSimulation();
        simulation.createTeams();
        simulation.createStartingElevensAndBenches();
        simulation.assignStatsToPlayers();
        simulation.setCaptains();
        simulation.assignStatusToPlayers();
        simulation.prepareSimulation();
        MainApplication.changeScene("tableViewStage.fxml", Objects.requireNonNull(this.getClass().getResource("tableViewStage.css")).toExternalForm(), (Node) event.getSource());
    }
}
