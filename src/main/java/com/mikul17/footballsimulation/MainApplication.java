package com.mikul17.footballsimulation;

import com.mikul17.footballsimulation.projekt.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;


public class MainApplication extends Application {

    public static Simulation simulation = new Simulation();
    private static Scene scene;
    private static Parent root;

    public static void main(String[] args) {
        launch();
    }

    public static Simulation getSimulation(){
        return simulation;
    }

    public static Scene getScene() {
        return scene;
    }
    public static void setScene(Scene scene) {
        MainApplication.scene = scene;
    }

    public static void changeScene(String fxml,String css, Node node) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(MainApplication.class.getResource(fxml)));
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.getScene().getStylesheets().add(css);
        setScene(stage.getScene());
    }

    public static Stage getStage () {
        return (Stage) scene.getWindow();
    }


    @Override
    public void start(Stage stage) throws Exception {

        DBmanager.openConnection();
        DBmanager.checkIfDatabaseExists();

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("startingStage.fxml")));
        scene = new Scene(root);
        stage.setScene(scene);
        Button continueButton = (Button) scene.lookup("#continueButton");
        continueButton.setDisable(!DBmanager.databaseExists);
        stage.setResizable(false);
        String css = Objects.requireNonNull(this.getClass().getResource("startingStage.css")).toExternalForm();
        scene.getStylesheets().add(css);
        stage.setTitle("Football Simulation");
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("football.png")).toExternalForm()));
        stage.show();

        stage.setOnCloseRequest(event -> {
            event.consume();
            try {
                exit(stage);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static void exit (Stage stage) throws SQLException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to save simulation progress?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.setTitle("Exit");

        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES){
            stage.close();
            DBmanager.saveSimulation(
                    simulation.getPlayerTeam()==null?null:simulation.getPlayerTeam().getName(),
                    simulation.getCurrentWeek(),
                    simulation.getSeasonsPlayed(),
                    simulation.getSeasonsWon(),
                    simulation.getPlayerTeam().getCapitan()==null?null:simulation.getPlayerTeam().getCapitan().getName(),
                    simulation.getPlayerTeam().getFreeKickTaker()==null?null:simulation.getPlayerTeam().getCapitan().getName(),
                    simulation.getPlayerTeam().getPenaltyTaker()==null?null:simulation.getPlayerTeam().getCapitan().getName()
            );
            for(Team team : simulation.getTeams()){
                DBmanager.insertTeamStats(team);
                for(int i=0; i<team.getPlayerList().size();i++){
                    DBmanager.updatePlayerInfo(team.getPlayerList().get(i),team.getName());
                }
                for(int i=0; i<team.getSubstitutes().size();i++){
                    DBmanager.updatePlayerInfo(team.getSubstitutes().get(i),team.getName());
                }
            }
        }else if  (alert.getResult() == ButtonType.NO){
            DBmanager.dropDatabase();
            DBmanager.closeConnection();
            stage.close();
        }else if (alert.getResult() == ButtonType.CANCEL)
            alert.close();
        }
}