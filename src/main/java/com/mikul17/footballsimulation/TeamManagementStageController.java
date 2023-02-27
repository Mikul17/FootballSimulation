package com.mikul17.footballsimulation;

import com.mikul17.footballsimulation.projekt.Player;
import com.mikul17.footballsimulation.projekt.Simulation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


public class TeamManagementStageController implements Initializable {

    Simulation simulation = MainApplication.getSimulation();
    @FXML
    private ListView<Player> activeEleven;
    @FXML
    private ListView<Player> bench;
    @FXML
    private Button swapPlayersButton;
    @FXML
    Label capitanLabel;
    @FXML
    Label freekickLabel;
    @FXML
    Label penaltyLabel;
    @FXML
    Button addPlayerButton;
    private String checker;


    public static class Cell extends ListCell<Player> {

        HBox hbox = new HBox();
        Label label = new Label("Default");
        Button detailed = new Button("View Details");
        Pane pane = new Pane();
        Player cellPlayer;

        public Cell (){
            super();
            hbox.getChildren().addAll(label, pane, detailed);
            HBox.setHgrow(pane, Priority.ALWAYS);
            detailed.setOnAction(event -> {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("playerDetails.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());
                    Stage stage = new Stage();
                    PlayerDetailsController controller = fxmlLoader.getController();
                    controller.setSelectedPlayer(cellPlayer);
                    controller.updateDetails();
                    stage.setTitle("Player Details");
                    stage.initStyle(StageStyle.UNDECORATED);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        @Override
        public void updateItem (Player player, boolean empty) {
            super.updateItem(player, empty);
            cellPlayer = player;
            setText(null);
            setGraphic(null);
            if (player != null) {
                label.setText(player.getPosition() + ": " + player.getName() + " " + player.getLastName() + " " + player.getOverall());
                setGraphic(hbox);
            }
        }

    }


    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {
        showActiveEleven();
        showBench();
        if(activeEleven.getItems().size() != 11){
            addPlayerButton.setVisible(true);
        }
    }

    public void checkIfSwappable () {
        if (activeEleven.getSelectionModel().getSelectedItem() != null && bench.getSelectionModel().getSelectedItem() != null) {
            swapPlayersButton.setDisable(!activeEleven.getSelectionModel().getSelectedItem().getPosition().equals(bench.getSelectionModel().getSelectedItem().getPosition()));
        }
    }

    public void goBack(ActionEvent event) throws IOException {
        MainApplication.changeScene("tableViewStage.fxml", Objects.requireNonNull(this.getClass().getResource("tableViewStage.css")).toExternalForm(), (Node) event.getSource());
    }

    private void showActiveEleven () {
        activeEleven.setCellFactory(param -> new Cell());
        for (int i = 0; i < MainApplication.getSimulation().getPlayerTeam().getPlayerList().size(); i++) {
            activeEleven.getItems().add(MainApplication.getSimulation().getPlayerTeam().getPlayerList().get(i));
        }
    }

    private void showBench () {
        bench.setCellFactory(param -> new Cell());
        for (int i = 0; i < MainApplication.getSimulation().getPlayerTeam().getSubstitutes().size(); i++) {
            bench.getItems().add(MainApplication.getSimulation().getPlayerTeam().getSubstitutes().get(i));
        }
    }

    public void checkPlayers(){
        int forwards=0;
        int midfielders=0;
        int defenders=0;
        Player selectedPlayerFromBench = bench.getSelectionModel().getSelectedItem();

        for(int i = 0; i < activeEleven.getItems().size(); i++){
            if(activeEleven.getItems().get(i).getPosition().equals("Forward")){
                forwards++;
            } else if(activeEleven.getItems().get(i).getPosition().equals("Midfielder")){
                midfielders++;
            } else if(activeEleven.getItems().get(i).getPosition().equals("Defender")){
                defenders++;
            }
        }

        if(forwards != 3){
            if(selectedPlayerFromBench.getPosition().equals("Forward")){
                checker="F";
                addPlayerButton.setDisable(false);
            }
        }else if (midfielders != 3){
            if(selectedPlayerFromBench.getPosition().equals("Midfielder")){
                checker="M";
                addPlayerButton.setDisable(false);
            }
        }else if(defenders != 4){
            if(selectedPlayerFromBench.getPosition().equals("Defender")){
               checker="D";
                addPlayerButton.setDisable(false);
            }
        }else{
            addPlayerButton.setDisable(true);
            addPlayerButton.setVisible(false);
        }

    }

    public void addPlayer() {
        Player selectedPlayerFromBench = bench.getSelectionModel().getSelectedItem();

        switch (checker) {
            case "F" -> {
               simulation.getPlayerTeam().getPlayerList().add(0, selectedPlayerFromBench);
               simulation.getPlayerTeam().getStartingField().add(0, selectedPlayerFromBench);
               simulation.getPlayerTeam().getSubstitutes().remove(selectedPlayerFromBench);
               activeEleven.getItems().add(0, selectedPlayerFromBench);
            }
            case "M" -> {
                simulation.getPlayerTeam().getPlayerList().add(3, selectedPlayerFromBench);
                simulation.getPlayerTeam().getStartingField().add(3, selectedPlayerFromBench);
                simulation.getPlayerTeam().getSubstitutes().remove(selectedPlayerFromBench);
                activeEleven.getItems().add(3, selectedPlayerFromBench);
            }
            case "D" -> {
                simulation.getPlayerTeam().getPlayerList().add(6, selectedPlayerFromBench);
                simulation.getPlayerTeam().getStartingField().add(6, selectedPlayerFromBench);
                simulation.getPlayerTeam().getSubstitutes().remove(selectedPlayerFromBench);
                activeEleven.getItems().add(6, selectedPlayerFromBench);
            }
        }
        addPlayerButton.setDisable(true);
        addPlayerButton.setVisible(false);
    }

    public void swapPlayers () {
        Player playerFromActiveEleven = activeEleven.getSelectionModel().getSelectedItem();
        int indexFromActiveEleven = activeEleven.getSelectionModel().getSelectedIndex();
        Player playerFromBench = bench.getSelectionModel().getSelectedItem();
        int indexFromBench = bench.getSelectionModel().getSelectedIndex();

        simulation.getPlayerTeam().getPlayerList().set(indexFromActiveEleven, playerFromBench);
        simulation.getPlayerTeam().getStartingField().set(indexFromActiveEleven, playerFromBench);
        simulation.getPlayerTeam().getSubstitutes().set(indexFromBench, playerFromActiveEleven);


        activeEleven.getItems().set(indexFromActiveEleven, playerFromBench);
        bench.getItems().set(indexFromBench, playerFromActiveEleven);



        activeEleven.getSelectionModel().clearSelection();
        bench.getSelectionModel().clearSelection();
    }

    public void updateLabel(){
        if(MainApplication.getSimulation().getPlayerTeam().getCapitan() != null){
            capitanLabel.setText("Capitan: "+MainApplication.getSimulation().getPlayerTeam().getCapitan().getLastName());
        }

        if(MainApplication.getSimulation().getPlayerTeam().getFreeKickTaker() != null){
            freekickLabel.setText("Freek kick taker: "+ MainApplication.getSimulation().getPlayerTeam().getFreeKickTaker().getLastName());
        }

        if(MainApplication.getSimulation().getPlayerTeam().getPenaltyTaker() != null){
            penaltyLabel.setText("Penalty taker: "+ MainApplication.getSimulation().getPlayerTeam().getPenaltyTaker().getLastName());
        }
    }
}


