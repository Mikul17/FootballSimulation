package com.mikul17.footballsimulation;

import com.mikul17.footballsimulation.projekt.Simulation;
import com.mikul17.footballsimulation.projekt.Team;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class SelectYourTeamStageController implements Initializable {

    Simulation simulation = MainApplication.getSimulation();
    @FXML
    private ListView<Team> teamsList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showTeams();
    }

    private void showTeams(){
        for(int i=0; i< MainApplication.getSimulation().getTeams().size(); i++){
            teamsList.getItems().add(MainApplication.getSimulation().getTeams().get(i));
        }
    }

    public void selectTeam() {
        Button nextButton = (Button) MainApplication.getScene().lookup("#acceptButton");
        Team selectedTeam = teamsList.getSelectionModel().getSelectedItem();
        simulation.setPlayerTeam(selectedTeam);
        nextButton.setDisable(false);
    }

    public void nextStage(ActionEvent event) throws Exception {
        MainApplication.changeScene("tableViewStage.fxml", Objects.requireNonNull(this.getClass().getResource("tableViewStage.css")).toExternalForm(), (Node) event.getSource());
    }
}
