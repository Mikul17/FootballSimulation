package com.mikul17.footballsimulation;

import com.mikul17.footballsimulation.projekt.Player;
import com.mikul17.footballsimulation.projekt.Simulation;
import com.mikul17.footballsimulation.projekt.Team;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NextMatchStageController {

    private final Simulation simulation = MainApplication.getSimulation();
    @FXML
    private VBox vsBox;
    private final List<Label> labels = new ArrayList<>();


    private void changeVisibility () {
        Button generateButton = (Button) MainApplication.getScene().lookup("#generateRivalsButton");
        Button nextMatchButton = (Button) MainApplication.getScene().lookup("#simulateButton");
        VBox homeTeamBox = (VBox) MainApplication.getScene().lookup("#homeTeamsBox");
        VBox awayTeamBox = (VBox) MainApplication.getScene().lookup("#awayTeamsBox");
        vsBox = (VBox) MainApplication.getScene().lookup("#vsBox");
        generateButton.setVisible(false);
        generateButton.setDisable(true);
        nextMatchButton.setDisable(false);
        homeTeamBox.setVisible(true);
        awayTeamBox.setVisible(true);
        vsBox.setVisible(true);
    }

    private void setLabels () {
        for (int i = 1; i <= 10; i++) {
            Label label = (Label) MainApplication.getScene().lookup("#team" + i);
            labels.add(label);
        }
    }

    @FXML
    private void playMatch (ActionEvent event) throws IOException {
        if (checkIfEnoughPlayers()) {
            if(checkPlayerTeam()){
                simulation.calculateNewInjuryTime();
                simulation.resetRedAndYellowCards();
                MainApplication.changeScene("matchStage.fxml", Objects.requireNonNull(this.getClass().getResource("matchStage.css")).toExternalForm(), (Node) event.getSource());
            }else {
                displayPopup();
            }
        }else{
            throw new IOException("Not enough players");
        }
    }

    public void backToTableView (ActionEvent event) throws IOException {
        MainApplication.changeScene("tableViewStage.fxml", Objects.requireNonNull(this.getClass().getResource("tableViewStage.css")).toExternalForm(), (Node) event.getSource());
    }

    public void loadTeamsToLabels () {
        changeVisibility();
        setLabels();
        List<Team> preparedPairs = simulation.generatePairs(simulation.getCurrentWeek());
        for (int i = 0; i < preparedPairs.size(); i++) {
            labels.get(i).setText(preparedPairs.get(i).getName());
        }
        simulation.removePlayerTeamFromPairs();
    }

    private boolean checkIfEnoughPlayers () {
        List<Team> allTeams = new ArrayList<>(simulation.getTeams());
        allTeams.removeIf(team -> team.getName().equals(simulation.getPlayerTeam().getName()));

        for (Team team : allTeams) {

            int forwards = 0;
            int midfielders = 0;
            int defenders = 0;
            for (int i = 0; i < team.getStartingField().size(); i++) {
                Player checkedPlayer = team.getStartingField().get(i);
                if (checkedPlayer.getPosition().equals("Forward") && !checkedPlayer.getIsInjured() && !checkedPlayer.isRedCard()) {
                    forwards++;
                } else if (checkedPlayer.getPosition().equals("Midfielder") && !checkedPlayer.getIsInjured() && !checkedPlayer.isRedCard()) {
                    midfielders++;
                } else if (checkedPlayer.getPosition().equals("Defender") && !checkedPlayer.getIsInjured() && !checkedPlayer.isRedCard()) {
                    defenders++;
                }
            }


            if (forwards != 3 || midfielders != 3 || defenders != 4) {
                if (forwards != 3) {
                    for (int j = 0; j < 3 - forwards; j++) {
                        team.getPlayerFromBench("Forward");
                    }
                } else if (midfielders != 3) {
                    for (int j = 0; j < 3 - midfielders; j++) {
                        team.getPlayerFromBench("Midfielder");
                    }
                } else {
                    for (int j = 0; j < 4 - defenders; j++) {
                        team.getPlayerFromBench("Defender");
                    }
                }
            }
        }
        return true;
    }

    private boolean checkPlayerTeam(){
        for(Player player : simulation.getPlayerTeam().getPlayerList()){
            if(player.getIsInjured() || player.isRedCard() || simulation.getPlayerTeam().getPlayerList().size() != 11){
                return false;
            }
        }
        return true;
    }

    private static void displayPopup () {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(250);
        window.setResizable(false);
        window.getIcons().add(new Image(Objects.requireNonNull(NextMatchStageController.class.getResource("football.png")).toExternalForm()));

        Label label = new Label();
        label.setText("Not all of your players are able to play");
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
}

