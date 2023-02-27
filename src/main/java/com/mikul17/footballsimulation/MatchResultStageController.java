package com.mikul17.footballsimulation;

import com.mikul17.footballsimulation.projekt.DBmanager;
import com.mikul17.footballsimulation.projekt.Simulation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MatchResultStageController{

    private final Simulation simulation = MainApplication.getSimulation();
    @FXML
    private VBox resultsBox;
    private final List<Label> labelsScores = new ArrayList<>();

    @FXML
    private Button showButton;
    private final List<Button> buttons = new ArrayList<>();


    private void prepareLabelsAndButtons (){
        for(int i=1; i<=5; i++){
            Label label = (Label) MainApplication.getScene().lookup("#score"+i);
            labelsScores.add(label);
        }

        for(int i=1; i<=5; i++){
            Button button = (Button) MainApplication.getScene().lookup("#details"+i);
            buttons.add(button);
        }
    }

    public void loadTeamsToLabels(){
        changeVisibility();
        prepareLabelsAndButtons();

        List<Object[]> homes = DBmanager.queryMatchResultHome(simulation.getCurrentWeek());
        List<Object[]> away = DBmanager.queryMatchResultAway(simulation.getCurrentWeek());

        for(int i=0; i<5; i++){
            labelsScores.get(i).setText(homes.get(i)[0] + " " + homes.get(i)[1] + " - " + away.get(i)[1] + " " + away.get(i)[0]);
        }

        for(int i=0; i<5; i++){
            int finalI = i;
            buttons.get(i).setOnAction(event -> showDetails(homes.get(finalI)[0].toString(),  away.get(finalI)[0].toString()));
        }
    }
    private void changeVisibility(){
        resultsBox.setVisible(true);
        showButton.setVisible(false);
        showButton.setDisable(true);
    }

    public void changeStage(ActionEvent event) throws IOException {
        simulation.nextWeek();
        if(simulation.getCurrentWeek() == 1){
            MainApplication.changeScene("winnerStage.fxml", Objects.requireNonNull(this.getClass().getResource("winnerStage.css")).toExternalForm(), (Node) event.getSource());
            simulation.nextSeason();
        }else{
            MainApplication.changeScene("tableViewStage.fxml", Objects.requireNonNull(this.getClass().getResource("tableViewStage.css")).toExternalForm(), (Node) event.getSource());
        }
    }

    public void showDetails(String home, String away){
        try{
            FXMLLoader fxmlloader = new FXMLLoader();
            fxmlloader.setLocation(getClass().getResource("matchDetails.fxml"));
            Scene scene = new Scene(fxmlloader.load());
            Stage stage = new Stage();
            MatchDetailsController controller = fxmlloader.getController();
           // controller.updateList(home, away);
            controller.updateTable(home, away);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
