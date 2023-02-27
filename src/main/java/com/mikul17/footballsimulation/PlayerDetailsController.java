package com.mikul17.footballsimulation;

import com.mikul17.footballsimulation.projekt.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;


public class PlayerDetailsController{
    @FXML
    private ListView<String> detailView;
    private final Simulation simulation = MainApplication.getSimulation();
    private Player selectedPlayer;
    @FXML
    private Button exitButton;
    @FXML
    private Button freekickButton;
    @FXML
    private Button penaltyButton;
    @FXML
    private Button capitanButton;



    public void updateDetails (){
        disableButtons();

        detailView.getItems().add("Name: " + selectedPlayer.getName() +" "+ selectedPlayer.getLastName());
        detailView.getItems().add("Position: "+ selectedPlayer.getPosition());
        detailView.getItems().add("Nationality: " + selectedPlayer.getNationality());
        detailView.getItems().add("Age: " + selectedPlayer.getAge());
        detailView.getItems().add("Height: " + selectedPlayer.getHeight());
        detailView.getItems().add("Overall: " + selectedPlayer.getOverall());
        detailView.getItems().add("Injury: "+ (selectedPlayer.getIsInjured()? selectedPlayer.getInjuryTime() : "Not injured"));
        detailView.getItems().add("Is red carded: "+ (selectedPlayer.isRedCard()? selectedPlayer.getRedCardTime() : "Not red carded"));
        detailView.getItems().add("Red cards: "+ selectedPlayer.getRedCardCounter());
        detailView.getItems().add("Yellow cards: "+ selectedPlayer.getYellowCardCounter());
        detailView.getItems().add("Goals: "+ selectedPlayer.getGoalCounter());
        detailView.getItems().add("Assists: "+ selectedPlayer.getAssistCounter());
        switch (selectedPlayer.getPosition()) {
            case "Goalkeeper" -> {
                Goalkeeper goalkeeper = (Goalkeeper) selectedPlayer;
                detailView.getItems().add("Short pass: " + goalkeeper.getShortPasses());
                detailView.getItems().add("Long pass: " + goalkeeper.getLongPasses());
                detailView.getItems().add("Reflex: " + goalkeeper.getReflexes());
                detailView.getItems().add("Diving: " + goalkeeper.getDiving());
                detailView.getItems().add("Long shot defense: " + goalkeeper.getLongShotDefending());
                detailView.getItems().add("Close range defense: " + goalkeeper.getCloseRangeDefending());
                detailView.getItems().add("Penalty saving: " + goalkeeper.getPenaltySaving());
            }
            case "Defender" -> {
                Defender defender = (Defender) selectedPlayer;
                detailView.getItems().add("Position: Forward");
                detailView.getItems().add("Pace: " + defender.getPace());
                detailView.getItems().add("Close range shooting: " + defender.getCloseRangeShot());
                detailView.getItems().add("Heading: " + defender.getHeader());
                detailView.getItems().add("Short pass: " + defender.getShortPass());
                detailView.getItems().add("Long pass: " + defender.getLongPass());
                detailView.getItems().add("Interceptions: " + defender.getInterception());
                detailView.getItems().add("Strength: " + defender.getStrength());
                detailView.getItems().add("Blocking: " + defender.getBlock());
                detailView.getItems().add("Header interception: " + defender.getHeaderFight());
                detailView.getItems().add("Positioning: " + defender.getPositioning());
            }
            case "Midfielder" -> {
                Midfielder midfielder = (Midfielder) selectedPlayer;
                detailView.getItems().add("Pace: " + midfielder.getPace());
                detailView.getItems().add("Close range shooting: " + midfielder.getCloseRangeShot());
                detailView.getItems().add("Long range shooting: " + midfielder.getLongShot());
                detailView.getItems().add("Heading: " + midfielder.getHeader());
                detailView.getItems().add("Dribbling: " + midfielder.getDribbling());
                detailView.getItems().add("Short pass: " + midfielder.getShortPass());
                detailView.getItems().add("Long pass: " + midfielder.getLongPass());
                detailView.getItems().add("Crossing: " + midfielder.getCrossPass());
                detailView.getItems().add("Vision: " + midfielder.getVision());
                detailView.getItems().add("Penalties: " + midfielder.getPenalties());
                detailView.getItems().add("Free kicks: " + midfielder.getFreekicks());
                detailView.getItems().add("Interceptions: " + midfielder.getInterception());
            }
            case "Forward" -> {
                Forward forward = (Forward) selectedPlayer;
                detailView.getItems().add("Pace: " + forward.getPace());
                detailView.getItems().add("Close range shooting: " + forward.getCloseRangeShot());
                detailView.getItems().add("Long range shooting: " + forward.getLongShot());
                detailView.getItems().add("Heading: " + forward.getHeader());
                detailView.getItems().add("Dribbling: " + forward.getDribbling());
                detailView.getItems().add("Short pass: " + forward.getShortPass());
                detailView.getItems().add("Long pass: " + forward.getLongPass());
                detailView.getItems().add("Penalties: " + forward.getPenalties());
                detailView.getItems().add("Free kicks: " + forward.getFreekicks());
                detailView.getItems().add("Interceptions: " + forward.getInterception());
            }
        }
    }

    public void exitButtonClicked(){
        detailView.getItems().clear();
        exitButton.getScene().getWindow().hide();
    }

    public void freekickButtonClicked(){
        simulation.getPlayerTeam().setFreeKickTaker(selectedPlayer);
    }

    public void penaltyButtonClicked(){
        simulation.getPlayerTeam().setPenaltyTaker(selectedPlayer);
    }

    public void capitanButtonClicked(){
        simulation.getPlayerTeam().setCapitan(selectedPlayer);
    }


    public void setSelectedPlayer (Player selectedPlayer) {
        this.selectedPlayer = selectedPlayer;
    }

    public void disableButtons () {
        if (selectedPlayer.getPosition().equals("Forward") || selectedPlayer.getPosition().equals("Midfielder")) {
            capitanButton.setDisable(false);
            penaltyButton.setDisable(false);
            freekickButton.setDisable(false);
        } else if (selectedPlayer.getPosition().equals("Defender")) {
            capitanButton.setDisable(false);
            penaltyButton.setDisable(true);
            freekickButton.setDisable(true);
        }
    }
}
