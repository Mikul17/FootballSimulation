package com.mikul17.footballsimulation;


import com.mikul17.footballsimulation.projekt.*;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MatchStageController implements Initializable {

    private final Simulation simulation = MainApplication.getSimulation();
    private Label activePlayerLabel;
    private TextArea matchReport;
    @FXML
    private Label scoreLabel;
    private Team homeTeam;
    private Team awayTeam;
    private Button nextButton;

    @FXML
    private ListView<Player> homeTeamPlayersList;
    @FXML
    private ListView<Player> awayTeamPlayersList;

    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {
        prepareHomeAndAwayTeam();
        showTeamPlayers();
    }
    public void prepareScene () {
        matchReport = (TextArea) MainApplication.getScene().lookup("#matchReport");
        activePlayerLabel = (Label) MainApplication.getScene().lookup("#activePlayerLabel");
        Label homeTeamNameLabel = (Label) MainApplication.getScene().lookup("#homeTeamNameLabel");
        Label awayTeamNameLabel = (Label) MainApplication.getScene().lookup("#awayTeamNameLabel");
        Button simulateButton = (Button) MainApplication.getScene().lookup("#simulateButton");
        VBox matchReportVBox = (VBox) MainApplication.getScene().lookup("#matchReportVBox");
        Label matchDetailsLabel = (Label) MainApplication.getScene().lookup("#matchDetails");
        nextButton = (Button) MainApplication.getScene().lookup("#nextButton");
        homeTeamNameLabel.setText(homeTeam.getName());
        awayTeamNameLabel.setText(awayTeam.getName());
        simulateButton.setVisible(false);
        matchDetailsLabel.setVisible(true);
        homeTeamNameLabel.setVisible(true);
        awayTeamNameLabel.setVisible(true);
        matchReport.setVisible(true);
        matchReportVBox.setVisible(true);
        scoreLabel.setVisible(true);
        //nextButton.setVisible(true);
        nextButton.setDisable(true);

        if(simulation.getPlayerTeam().isHomeTeam()) {
            awayTeamPlayersList.setDisable(true);
            awayTeamPlayersList.setOpacity(1);
        }else{
            homeTeamPlayersList.setDisable(true);
            homeTeamPlayersList.setOpacity(1);
        }

        matchReport.textProperty().addListener((observable, oldValue, newValue) -> matchReport.setScrollTop(Double.MAX_VALUE));
    }
    public void showTeamPlayers(){
        if(simulation.getPlayerTeam().isHomeTeam()) {
            for (int i = 0; i < simulation.getPlayerTeam().getPlayerList().size(); i++) {
                homeTeamPlayersList.getItems().add(homeTeam.getPlayerList().get(i));
            }

            for (int i = 0; i < simulation.getPlayerOpponentTeam().getPlayerList().size(); i++) {
                awayTeamPlayersList.getItems().add(awayTeam.getPlayerList().get(i));
            }
        }else{
            for (int i = 0; i < simulation.getPlayerOpponentTeam().getPlayerList().size(); i++) {
                homeTeamPlayersList.getItems().add(homeTeam.getPlayerList().get(i));
            }

            for (int i = 0; i < simulation.getPlayerTeam().getPlayerList().size(); i++) {
                awayTeamPlayersList.getItems().add(awayTeam.getPlayerList().get(i));
            }
        }
    }
    private void prepareHomeAndAwayTeam(){
        if(simulation.getPlayerTeam().isHomeTeam()){
            homeTeam = simulation.getPlayerTeam();
            awayTeam = simulation.getPlayerOpponentTeam();
        }else{
            homeTeam = simulation.getPlayerOpponentTeam();
            awayTeam = simulation.getPlayerTeam();
        }
    }

    public void startMatch(){
        Match match = new Match(homeTeam, awayTeam);
        prepareScene();

        Task<Void> task = new Task<>() {
            @Override
            public Void call () throws Exception {
                simulateFirstHalf(match);
                simulateSecondHalf(match);
                return null;
            }
        };
        new Thread(task).start();
        simulateMatchesInBackground(simulation.pairs, simulation);
        nextButton.setVisible(true);
    }
    private void simulateFirstHalf(Match match) throws Exception {
        Random random = new Random();
        for (int i = 1; i <= 45; i++) {
            matchEventUpdate(match, i);
        }
        int extraIterations = random.nextInt(6);
        if (extraIterations > 0) {
            final String addedTimeReference = matchReport.getText() + "Added time: " + extraIterations + " minutes\n";
            Platform.runLater(() -> {
                matchReport.setText(addedTimeReference);
                matchReport.setScrollTop(Double.MAX_VALUE);
            });
            for (int j = 0; j <= extraIterations; j++) {
                TimeUnit.MILLISECONDS.sleep(5);
                final String extraMessage = match.simulateMatch(45 + j, extraIterations,false);
                Platform.runLater(() -> {
                    matchReport.setText(matchReport.getText() + extraMessage);
                    matchReport.setScrollTop(Double.MAX_VALUE);
                    activePlayerLabel.setText("Active player: " +match.getActivePlayer().getName());
                    updateScore(match);
                });
            }
        }

   
        final String firstHalfOverMessage = "First half is over\n";
        Platform.runLater(() -> {
            matchReport.setText(matchReport.getText() + firstHalfOverMessage);
            matchReport.setScrollTop(Double.MAX_VALUE);
        });
       // TimeUnit.SECONDS.sleep(2);
    }
    private void simulateSecondHalf(Match match) throws Exception {
        Random random = new Random();

   
        for (int i = 45; i <= 90; i++) {
            matchEventUpdate(match, i);
        }


        int extraIterations = random.nextInt(6);
        if (extraIterations > 0) {
            final String addedTimeReference = matchReport.getText() + "Added time: " + extraIterations + " minutes\n";
            Platform.runLater(() -> {
                matchReport.setText(addedTimeReference);
                matchReport.setScrollTop(Double.MAX_VALUE);
            });
            for (int j = 0; j <= extraIterations; j++) {
                TimeUnit.MILLISECONDS.sleep(5);
                final String extraMessage = match.simulateMatch(90 + j, extraIterations,false);
                Platform.runLater(() -> {
                    matchReport.setText(matchReport.getText() + extraMessage);
                    matchReport.setScrollTop(Double.MAX_VALUE);
                    activePlayerLabel.setText("Active player: " +match.getActivePlayer().getName());
                    updateScore(match);
                });
            }
        }

        final String firstHalfOverMessage = "Match is over\n Final score is: "+match.getHomeTeam().getName() +
                " "+match.getHomeTeamGoals() + " - " + match.getAwayTeamGoals()+" "+match.getAwayTeam().getName();
        Platform.runLater(() -> {
            matchReport.setText(matchReport.getText() + firstHalfOverMessage);
            matchReport.setScrollTop(Double.MAX_VALUE);
        });
        //TimeUnit.SECONDS.sleep(2);
        match.setHomeTeamPossession((match.getHomeTeamPossession()*100)/180);
        match.setAwayTeamPossession(100-match.getHomeTeamPossession());
        insertMatchInformation(simulation, match);
        match.updateTeamStats();
        nextButton.setDisable(false);
    }
    private void matchEventUpdate (Match match, int i) throws Exception {
        TimeUnit.MILLISECONDS.sleep(5);
        final String message = match.simulateMatch(i, 0,true);
        final Player activePlayer = match.getActivePlayer();
        Platform.runLater(() -> {
            matchReport.setText(matchReport.getText() + message);
            matchReport.setScrollTop(Double.MAX_VALUE);
            activePlayerLabel.setText("Active player: " + activePlayer.getName());
            updateScore(match);
        });
    }
    public static void simulateMatchesInBackground(List<Team> pairs, Simulation simulation) {

            ExecutorService executor = Executors.newFixedThreadPool(4);
            for (int i = 0; i < pairs.size(); i+=2) {
                final int index = i;
                Runnable task = () -> {
                    Match match = new Match(pairs.get(index), pairs.get(index+1));
                    try {
                        match.simulateMatch();
                        insertMatchInformation(simulation, match);
                        match.updateTeamStats();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                };
                executor.submit(task);
            }
            executor.shutdown();
        }


    private static void insertMatchInformation (Simulation simulation, Match match) {
        DBmanager.insertMatch(simulation.getCurrentWeek(),match.getHomeTeamGoals(),match.getAwayTeamGoals(),match.getHomeTeam().getName(), match.getAwayTeam().getName());
        DBmanager.insertMatchStats(
                match.getHomeTeam().getName(),
                match.getAwayTeam().getName(),
                match.getHomeTeam().getName(),
                match.getHomeTeamShots(),
                match.getHomeTeamShotsOnTarget(),
                match.getHomeTeamPossession(),
                match.getHomeTeamCorners(),
                match.getHomeTeamOffsides(),
                match.getHomeTeamFouls(),
                match.getHomeTeamFreekicksAmount(),
                match.getHomeTeamPenaltiesAmount(),
                match.getHomeTeamYellowCards(),
                match.getHomeTeamRedCards(),
                match.getHomeTeamSaves(),
                match.getHomeTeamInterceptions(),
                match.getHomeTeamBlocks(),
                match.getHomeTeamPasses(),
                match.getHomeTeamPassesAccurate(),
                match.getHomeTeamDribbles(),
                match.getHomeTeamDribblesWon()
        );
        DBmanager.insertMatchStats(
                match.getHomeTeam().getName(),
                match.getAwayTeam().getName(),
                match.getAwayTeam().getName(),
                match.getAwayTeamShots(),
                match.getAwayTeamShotsOnTarget(),
                match.getAwayTeamPossession(),
                match.getAwayTeamCorners(),
                match.getAwayTeamOffsides(),
                match.getAwayTeamFouls(),
                match.getAwayTeamFreekicksAmount(),
                match.getAwayTeamPenaltiesAmount(),
                match.getAwayTeamYellowCards(),
                match.getAwayTeamRedCards(),
                match.getAwayTeamSaves(),
                match.getAwayTeamInterceptions(),
                match.getAwayTeamBlocks(),
                match.getAwayTeamPasses(),
                match.getAwayTeamPassesAccurate(),
                match.getAwayTeamDribbles(),
                match.getAwayTeamDribblesWon()
        );
    }
    private void updateScore(Match match){
        scoreLabel.setText(match.getHomeTeamGoals() + " - " + match.getAwayTeamGoals());
    }

    public void changeScene(ActionEvent event) throws IOException {
        MainApplication.changeScene("matchResultStage.fxml", Objects.requireNonNull(this.getClass().getResource("matchResultStage.css")).toExternalForm(), (Node) event.getSource());
    }

}
