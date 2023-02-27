package com.mikul17.footballsimulation.projekt;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Simulation {

    private int currentWeek=1;
    private int seasonsPlayed=0;
    private  int seasonsWon=0;
    private Team playerTeam = null;
    private Team playerOpponentTeam = null;
    public  List<Team> teams = new ArrayList<>();
    public List<Team> pairs = new ArrayList<>();

    //Setters
    public void setCaptains (){
        for (Team team : teams) {
            team.setCapitan(team.getPlayerList().get(0));
        }
    }
    private void setPairs (List<Team> pairs) {
        this.pairs = pairs;
    }
    public void setPlayerTeam(Team selectedTeam) {
        playerTeam = selectedTeam;
    }
    private void setSeasonsPlayed (int querySeasonsPlayed) {
        this.seasonsPlayed = querySeasonsPlayed;
    }
    public void setCurrentWeek (int currentWeek) {
        this.currentWeek = currentWeek;
    }
    public void setSeasonsWon (int seasonsWon) {
        this.seasonsWon = seasonsWon;
    }

    //Getters
    public int getCurrentWeek() {
        return currentWeek;
    }
    public Team getTeamByName(String name){
        for (Team team : teams) {
            if (team.getName().equals(name)) {
                return team;
            }
        }
        return null;
    }
    public List<Team> getTeams() {
        return teams;
    }
    public int getPlayerTeamIndexFromPairs() {
        for(Team team : pairs){
            if(team.getName().equals(playerTeam.getName())){
                return pairs.indexOf(team);
            }
        }
        return -1;
    }
    public Team getPlayerTeam() {
        return playerTeam;
    }
    public Team getPlayerOpponentTeam() {
        return playerOpponentTeam;
    }
    public int getSeasonsPlayed () {
        return seasonsPlayed;
    }
    public int getSeasonsWon () {
        return seasonsWon;
    }


    //Generating match schedule
    public List<Team> generatePairs(int weekNumber){
        List<Team> pairs = new ArrayList<>();
        boolean reverseTeamOrder = false;
        try {
            File file = new File("src/main/resources/com/mikul17/footballsimulation/round_robin.txt");
            Scanner reader = new Scanner(file);
            if(weekNumber>9){
                weekNumber=weekNumber-9;
                reverseTeamOrder=true;
            }
            for(int i=0; i<weekNumber; i++){
                String data = reader.nextLine();
                if(i==weekNumber-1){
                    String[] indexes = data.split(" ");
                    for(int j=0; j<teams.size(); j+=2){
                        if(reverseTeamOrder) {
                            pairs.add(getTeamByName(String.valueOf(teams.get(Integer.parseInt(indexes[j + 1])))));
                            pairs.add(getTeamByName(String.valueOf(teams.get(Integer.parseInt(indexes[j])))));
                        }else{
                            pairs.add(getTeamByName(String.valueOf(teams.get(Integer.parseInt(indexes[j])))));
                            pairs.add(getTeamByName(String.valueOf(teams.get(Integer.parseInt(indexes[j + 1])))));
                        }
                    }
                    break;
                }
            }
        }catch (Exception e){
            System.out.println("Error: " + e);
        }
        setPairs(pairs);
        return this.pairs;
    }
    public void removePlayerTeamFromPairs() {
        if (getPlayerTeamIndexFromPairs() != -1) {
            if (getPlayerTeamIndexFromPairs() % 2 == 0) {
                playerOpponentTeam=pairs.get(getPlayerTeamIndexFromPairs()+1);
                pairs.remove(getPlayerTeamIndexFromPairs() + 1);
                pairs.remove(getPlayerTeamIndexFromPairs());
                playerTeam.setHomeTeam(true);
                playerOpponentTeam.setHomeTeam(false);
            } else if(getPlayerTeamIndexFromPairs() % 2 != 0){
                playerOpponentTeam=pairs.get(getPlayerTeamIndexFromPairs()-1);
                pairs.remove(getPlayerTeamIndexFromPairs() - 1);
                pairs.remove(getPlayerTeamIndexFromPairs());
                playerOpponentTeam.setHomeTeam(true);
                playerTeam.setHomeTeam(false);
            }
        }
    }


    //Preparing simulation
    public void prepareSimulation (){
        this.setPlayerTeam(this.getTeamByName(DBmanager.queryPlayerSelectedTeam()));
        this.setCurrentWeek(DBmanager.queryCurrentWeek());
        this.setSeasonsPlayed(DBmanager.querySeasonsPlayed());
        this.setSeasonsWon(DBmanager.querySeasonsWon());
        this.getPlayerTeam().setCapitan(DBmanager.queryCapitan());
        this.getPlayerTeam().setPenaltyTaker(DBmanager.queryPenaltyTaker());
        this.getPlayerTeam().setFreeKickTaker(DBmanager.queryFreekickTaker());
    }
    public void createTeams(){
        for(int i=1; i<=10;i++){
            teams.add(DBmanager.queryTeam(i));
        }
    }
    public void createStartingElevensAndBenches(){
        for(int i=0; i<teams.size(); i++){
            teams.get(i).queryForwards(i);
            teams.get(i).queryMidfielders(i);
            teams.get(i).queryDefenders(i);
            teams.get(i).queryGoalkeepers(i);
        }
    }
    public void assignStatsToPlayers(){
        for (Team team : teams) {
            team.assignStatsToPlayers();
        }
    }
    public void calculateOverallForPlayers(){
        for(Team team : teams){
            for(Player player : team.getPlayerList()){
                switch (player.getPosition()) {
                    case "Goalkeeper" -> ((Goalkeeper) player).calculateOverall();
                    case "Defender" -> ((Defender) player).calculateOverall();
                    case "Midfielder" -> ((Midfielder) player).calculateOverall();
                    case "Forward" -> ((Forward) player).calculateOverall();
                }
            }
            for(Player player : team.getSubstitutes()){
                switch (player.getPosition()) {
                    case "Goalkeeper" -> ((Goalkeeper) player).calculateOverall();
                    case "Defender" -> ((Defender) player).calculateOverall();
                    case "Midfielder" -> ((Midfielder) player).calculateOverall();
                    case "Forward" -> ((Forward) player).calculateOverall();
                }
            }
        }
    }
    private void assignPlayerStatusToPlayerFromTeam (Team team, Player player) {
        HashMap<String, Integer> playerStatus = DBmanager.queryPlayerStatusInformation(player.getName(),player.getLastName(),team.getName());
        player.setAge(playerStatus.get("playerAge"));
        player.setOverall(playerStatus.get("playerOverall"));
        player.setIsInjured(playerStatus.get("playerIsInjured"));
        player.setInjuryTime(playerStatus.get("playerInjuryTime"));
        player.setRedCard(playerStatus.get("playerIsRedCarded"));
        player.setRedCardsCounter(playerStatus.get("playerRedCards"));
        player.setYellowCardsCounter(playerStatus.get("playerYellowCards"));
        player.setGoalCounter(playerStatus.get("playerGoals"));
        player.setAssistCounter(playerStatus.get("playerAssists"));
    }

    //Other methods
    public void nextWeek(){
        if(currentWeek>=17){
            currentWeek=1;
        }else{
            currentWeek++;
        }
    }
    public void nextSeason() {
        this.seasonsPlayed++;
    }
    public void resetTeamScore(){
        for (Team team : teams) {
            team.setPoints(0);
            team.setGoalsScored(0);
            team.setGoalsConceded(0);
            team.setGoalsDifference(0);
            team.setWins(0);
            team.setDraws(0);
            team.setLosses(0);
        }
    }
    public void addSeasonsPlayed () {
        this.seasonsPlayed ++;
    }
    public void calculateNewInjuryTime(){
        for (Team team : teams) {
            for(Player player : team.getPlayerList()){
                if(player.getInjuryTime()>=0){
                    player.calculateInjuryTime();
                }
            }
            for(Player player : team.getSubstitutes()){
                if(player.getInjuryTime()>=0){
                    player.calculateInjuryTime();
                }
            }
        }
    }
    public void resetRedAndYellowCards(){
        for(Team team : teams){
            for(Player player : team.getPlayerList()){
                player.decreaseRedCardTime();
                player.resetYellowCards();
            }
            for(Player player : team.getSubstitutes()){
                player.decreaseRedCardTime();
                player.resetYellowCards();
            }
        }
    }
    public void assignStatusToPlayers(){
        for(Team team : teams){
            for(Player player : team.getPlayerList()){
                assignPlayerStatusToPlayerFromTeam(team, player);
            }
            for(Player player : team.getSubstitutes()){
                assignPlayerStatusToPlayerFromTeam(team, player);
            }
        }
    }



}
