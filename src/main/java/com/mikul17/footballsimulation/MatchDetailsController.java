package com.mikul17.footballsimulation;

import com.mikul17.footballsimulation.projekt.DBmanager;
import com.mikul17.footballsimulation.projekt.Simulation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class MatchDetailsController implements Initializable {

    private final Simulation simulation = MainApplication.getSimulation();
    private final ObservableList<TableData> tableData = FXCollections.observableArrayList();
    @FXML
    private Button closeButton;
    @FXML
    private TableView<TableData> detailsTable;
    @FXML
    private TableColumn<TableData, String> teamName;
    @FXML
    private TableColumn<TableData, String> homeTeamColumn;
    @FXML
    private TableColumn<TableData, String> awayTeamColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourcebundle){
        teamName.setCellValueFactory(new PropertyValueFactory<>("name"));
        homeTeamColumn.setCellValueFactory(new PropertyValueFactory<>("homeValue"));
        awayTeamColumn.setCellValueFactory(new PropertyValueFactory<>("awayValue"));
    }

    public void updateTable (String homeTeam, String awayTeam) {
        List<HashMap<String, Integer>> matchDetails = DBmanager.queryMatchDetails(simulation.getCurrentWeek(), homeTeam, awayTeam);
        tableData.add(new TableData("Shots", matchDetails.get(0).get("homeTeamShots").toString(), matchDetails.get(1).get("awayTeamShots").toString()));
        tableData.add(new TableData("Shots on target", matchDetails.get(0).get("homeTeamShotsOnTarget").toString(), matchDetails.get(1).get("awayTeamShotsOnTarget").toString()));
        tableData.add(new TableData("Possession", matchDetails.get(0).get("homeTeamPossession").toString(), matchDetails.get(1).get("awayTeamPossession").toString()));
        tableData.add(new TableData("Corner kicks", matchDetails.get(0).get("homeTeamCorners").toString(), matchDetails.get(1).get("awayTeamCorners").toString()));
        tableData.add(new TableData("Offsides", matchDetails.get(0).get("homeTeamOffsides").toString(), matchDetails.get(1).get("awayTeamOffsides").toString()));
        tableData.add(new TableData("Fouls", matchDetails.get(0).get("homeTeamOffsides").toString(), matchDetails.get(1).get("awayTeamOffsides").toString()));
        tableData.add(new TableData("Free kicks", matchDetails.get(0).get("homeTeamFreekicks").toString(), matchDetails.get(1).get("awayTeamFreekicks").toString()));
        tableData.add(new TableData("Penalties", matchDetails.get(0).get("homeTeamPenalties").toString(), matchDetails.get(1).get("awayTeamPenalties").toString()));
        tableData.add(new TableData("Yellow cards", matchDetails.get(0).get("homeTeamYellowCards").toString(), matchDetails.get(1).get("awayTeamYellowCards").toString()));
        tableData.add(new TableData("Red cards", matchDetails.get(0).get("homeTeamRedCards").toString(), matchDetails.get(1).get("awayTeamRedCards").toString()));
        tableData.add(new TableData("Saves", matchDetails.get(0).get("homeTeamSaves").toString(), matchDetails.get(1).get("awayTeamSaves").toString()));
        tableData.add(new TableData("Interceptions", matchDetails.get(0).get("homeTeamInterceptions").toString(), matchDetails.get(1).get("awayTeamInterceptions").toString()));
        tableData.add(new TableData("Blocks", matchDetails.get(0).get("homeTeamBlocks").toString(), matchDetails.get(1).get("awayTeamBlocks").toString()));
        tableData.add(new TableData("Passes", matchDetails.get(0).get("homeTeamPasses").toString(), matchDetails.get(1).get("awayTeamPasses").toString()));
        tableData.add(new TableData("Passes accuracy", matchDetails.get(0).get("homeTeamPassesAccuracy").toString(), matchDetails.get(1).get("awayTeamPassesAccuracy").toString()));
        tableData.add(new TableData("Dribbles", matchDetails.get(0).get("homeTeamDribbles").toString(), matchDetails.get(1).get("awayTeamDribbles").toString()));
        tableData.add(new TableData("Dribbles won", matchDetails.get(0).get("homeTeamDribblesWon").toString(), matchDetails.get(1).get("awayTeamDribblesWon").toString()));

        homeTeamColumn.setText(homeTeam);
        awayTeamColumn.setText(awayTeam);

        detailsTable.setItems(tableData);
    }

    public void closeWindow(){
        closeButton.getScene().getWindow().hide();
    }
}

