package com.mikul17.footballsimulation.projekt;


import java.util.ArrayList;
import java.util.List;

public class Team {

    //Information about team
    private final String name;
    private int points = 0;
    private int goalsScored = 0;
    private int goalsConceded = 0;
    private int goalsDifference = 0;
    private int wins = 0;
    private int draws = 0;
    private int losses = 0;
    private boolean isHomeTeam = false;

    //Players
    private Player capitan;
    private Player freeKickTaker;
    private Player penaltyTaker;

    private final ArrayList<Player> players = new ArrayList<>();
    private final ArrayList<Forward> forwards = new ArrayList<>();
    private final ArrayList<Midfielder> midfielders = new ArrayList<>();
    private final ArrayList<Defender> defenders = new ArrayList<>();
    private final ArrayList<Player> substitutes = new ArrayList<>();
    private final ArrayList<Player> startingField = new ArrayList<>();



    public Team (String name) {
        this.name = name;
    }

    public Team (String name, int wins, int draws, int loses, int goalsScored, int goalsConceded, int points) {
        this.name = name;
        this.wins = wins;
        this.draws = draws;
        this.losses = loses;
        this.goalsScored = goalsScored;
        this.goalsConceded = goalsConceded;
        this.points = points;
    }

    //Setters
    public void setCapitan (Player capitan) {
        this.capitan = capitan;
    }
    public void setFreeKickTaker (Player freeKickTaker) {
        this.freeKickTaker = freeKickTaker;
    }
    public void setPenaltyTaker (Player penaltyTaker) {
        this.penaltyTaker = penaltyTaker;
    }
    public void setHomeTeamForPlayers (boolean homeTeam) {
        for (Player player : players) {
            player.setHomeTeam(homeTeam);
        }
        for (Player player : substitutes) {
            player.setHomeTeam(homeTeam);
        }
    }
    public void setHomeTeam (boolean homeTeam) {
        this.isHomeTeam = homeTeam;
    }
    public void setPoints (int points) {
        this.points = points;
    }
    public void setGoalsScored (int goalsScored) {
        this.goalsScored = goalsScored;
    }
    public void setGoalsConceded (int goalsConceded) {
        this.goalsConceded = goalsConceded;
    }
    public void setGoalsDifference (int goalsDifference) {
        this.goalsDifference = goalsDifference;
    }
    public void setWins (int wins) {
        this.wins = wins;
    }
    public void setDraws (int draws) {
        this.draws = draws;
    }
    public void setLosses (int losses) {
        this.losses = losses;
    }

    //Getters
    public int getGoalsConceded () {
        return goalsConceded;
    }
    public int getPoints () {
        return points;
    }
    public int getGoalsScored () {
        return goalsScored;
    }
    public int getWins () {
        return wins;
    }
    public int getDraws () {
        return draws;
    }
    public int getLosses () {
        return losses;
    }

    public String getName () {
        return name;
    }
    public ArrayList<Player> getPlayerList () {
        return players;
    }
    public ArrayList<Player> getSubstitutes () {
        return substitutes;
    }
    public ArrayList<Player> getStartingField () {
        return startingField;
    }
    public ArrayList<Forward> getAttackers () {
        return forwards;
    }
    public ArrayList<Midfielder> getMidfielders () {
        return midfielders;
    }
    public ArrayList<Defender> getDefenders () {
        return defenders;
    }
    public Player getActiveGoalkeeper () {
        for (Player player : players) {
            if (player instanceof Goalkeeper) {
                return player;
            }
        }
        return null;
    }
    public Player getFreeKickTaker () {
        return freeKickTaker;
    }
    public Player getPenaltyTaker () {
        return penaltyTaker;
    }
    public Player getCapitan () {
        return capitan;
    }
    public void getPlayerFromBench(String position){
        for(Player player : substitutes){
            if(player.getPosition().equals(position) && player.getInjuryTime() == 0 && player.getRedCardTime() == 0){
                if (player instanceof Forward) {
                    players.add(0,player);
                    startingField.add(0,player);
                    forwards.add(0,(Forward) player);
                } else if (player instanceof Midfielder) {
                    startingField.add(3,player);
                    players.add(3,player);
                    midfielders.add(0,(Midfielder) player);
                }else if (player instanceof Defender) {
                    startingField.add(6,player);
                    players.add(6,player);
                    defenders.add(0,(Defender) player);
                }
                substitutes.remove(player);
                break;
            }
        }
    }
    public int getGoalsDifference () {
        return goalsDifference;
    }

    //Queries
    public void queryForwards (int teamID) {
        for (int j = 1; j <= 6; j++) {
            Player queriedPlayer = DBmanager.queryPlayer(j + (teamID * 6));
            if (j % 2 != 0 && queriedPlayer instanceof Forward) {
                //startingEleven.add(queriedPlayer);
                players.add(queriedPlayer);
                startingField.add(queriedPlayer);
                forwards.add((Forward) queriedPlayer);
            } else if (j % 2 == 0 && queriedPlayer instanceof Forward) {
                substitutes.add(DBmanager.queryPlayer(j + (teamID * 6)));
            } else {
                throw new IllegalArgumentException("Wrong player type");
            }
        }
    }
    public void queryMidfielders (int teamID) {
        for (int j = 1; j <= 6; j++) {
            Player queriedPlayer = DBmanager.queryPlayer(j + 60 + (teamID * 6));
            if (j % 2 != 0 && queriedPlayer instanceof Midfielder) {
                //startingEleven.add(queriedPlayer);
                players.add(queriedPlayer);
                startingField.add(queriedPlayer);
                midfielders.add((Midfielder) queriedPlayer);
            } else if (j % 2 == 0 && queriedPlayer instanceof Midfielder) {
                substitutes.add(DBmanager.queryPlayer(j + 60 + (teamID * 6)));
            } else {
                throw new IllegalArgumentException("Wrong player type");
            }
        }
    }
    public void queryDefenders (int teamID) {
        for (int j = 1; j <= 6; j++) {
            Player queriedPlayer = DBmanager.queryPlayer(j + 120 + (teamID * 6));
            if (j <= 4 && queriedPlayer instanceof Defender) {
                //startingEleven.add(queriedPlayer);
                players.add(queriedPlayer);
                startingField.add(queriedPlayer);
                defenders.add((Defender) queriedPlayer);
            } else if (j > 4 && queriedPlayer instanceof Defender) {
                substitutes.add(DBmanager.queryPlayer(j + 120 + (teamID * 6)));
            } else {
                throw new IllegalArgumentException("Wrong player type");
            }
        }
    }
    public void queryGoalkeepers (int teamID) {
        for (int j = 1; j <= 2; j++) {
            Player queriedPlayer = DBmanager.queryPlayer(j + 180 + (teamID * 2));
            if (j == 1 && queriedPlayer instanceof Goalkeeper) {
                //startingEleven.add(queriedPlayer);
                players.add(queriedPlayer);
            } else if (j == 2 && queriedPlayer instanceof Goalkeeper) {
                substitutes.add(DBmanager.queryPlayer(j + 180 + (teamID * 2)));
            } else {
                throw new IllegalArgumentException("Wrong player type");
            }
        }
    }

    //Updating stats
    public void updateGoalsScored (int goalsScored) {
        this.goalsScored += goalsScored;
    }

    public void updateGoalsConceded (int goalsConceded) {
        this.goalsConceded += goalsConceded;
    }

    public void updateGoalsDifference () {
        this.goalsDifference = this.goalsScored - this.goalsConceded;
    }

    public void updatePoints (int points) {
        this.points += points;
    }

    public void updateWins (int wins) {
        this.wins += wins;
    }

    public void updateDraws (int draws) {
        this.draws += draws;
    }

    public void updateLosses (int losses) {
        this.losses += losses;
    }

    //Player swapping
    public void transferCaptainOrPenaltyOrFreekick(Player leavin, Player comingIn) {
        if (capitan == leavin) {
            capitan = comingIn;
        }
        if (penaltyTaker == leavin) {
            penaltyTaker = comingIn;
        }
        if (freeKickTaker == leavin) {
            freeKickTaker = comingIn;
        }
    }
    public void transferCaptainOrPenaltyOrFreekick(Player leaving){
        List<Player> playerListWithoutLeaving = new ArrayList<>(players);
        playerListWithoutLeaving.remove(leaving);
        if(capitan == leaving){
            capitan = playerListWithoutLeaving.get(0);
        }
        if(penaltyTaker == leaving){
            penaltyTaker = playerListWithoutLeaving.get(0);
        }
        if(freeKickTaker == leaving){
            freeKickTaker = playerListWithoutLeaving.get(0);
        }
    }
    public void swapPlayerWithBench (Player player) {
        int indexInPlayers = players.indexOf(player);
        int indexInStartingField = startingField.indexOf(player);
        List<Player> possibleReplacements = new ArrayList<>();

        if (player instanceof Forward) {
            startingField.remove(player);
            forwards.remove(player);
            players.remove(player);
            for (Player replacement : substitutes) {
                if (replacement instanceof Forward && replacement.getInjuryTime() == 0 && replacement.getRedCardTime() == 0) {
                    possibleReplacements.add(replacement);
                }
            }
        } else if (player instanceof Midfielder) {
            startingField.remove(player);
            midfielders.remove(player);
            players.remove(player);
            for (Player replacement : substitutes) {
                if (replacement instanceof Midfielder && replacement.getInjuryTime() == 0 && replacement.getRedCardTime() == 0) {
                    possibleReplacements.add(replacement);
                }
            }
        }else if (player instanceof Defender) {
            startingField.remove(player);
            defenders.remove(player);
            players.remove(player);
            for (Player replacement : substitutes) {
                if (replacement instanceof Defender && replacement.getInjuryTime() == 0 && replacement.getRedCardTime() == 0) {
                    possibleReplacements.add(replacement);
                }
            }
        }

        if(possibleReplacements.size()!=0){
            transferCaptainOrPenaltyOrFreekick(player, possibleReplacements.get(0));
            startingField.add(indexInStartingField,possibleReplacements.get(0));
            players.add(indexInPlayers,possibleReplacements.get(0));
            substitutes.remove(possibleReplacements.get(0));
            switch (possibleReplacements.get(0).getPosition()) {
                case "Forward" -> forwards.add((Forward) possibleReplacements.get(0));
                case "Midfielder" -> midfielders.add((Midfielder) possibleReplacements.get(0));
                case "Defender" -> defenders.add((Defender) possibleReplacements.get(0));
            }
        }
        substitutes.add(player);
    }
    public void moveToBench(Player player){
        if (player instanceof Forward) {
            transferCaptainOrPenaltyOrFreekick(player);
            startingField.remove(player);
            forwards.remove(player);
            players.remove(player);
        } else if (player instanceof Midfielder) {
            transferCaptainOrPenaltyOrFreekick(player);
            startingField.remove(player);
            midfielders.remove(player);
            players.remove(player);
        }else if (player instanceof Defender) {
            transferCaptainOrPenaltyOrFreekick(player);
            startingField.remove(player);
            defenders.remove(player);
            players.remove(player);
        }
        substitutes.add(player);
    }

    //Other
    public void assignStatsToPlayers () {
        for (Player player : players) {
            if (player instanceof Forward) {
                DBmanager.setPlayerStats((Forward) player);
            } else if (player instanceof Midfielder) {
                DBmanager.setPlayerStats((Midfielder) player);
            } else if (player instanceof Defender) {
                DBmanager.setPlayerStats((Defender) player);
            } else if (player instanceof Goalkeeper) {
                DBmanager.setPlayerStats((Goalkeeper) player);
            }
        }
        for(Player player : substitutes){
            if (player instanceof Forward) {
                DBmanager.setPlayerStats((Forward) player);
            } else if (player instanceof Midfielder) {
                DBmanager.setPlayerStats((Midfielder) player);
            } else if (player instanceof Defender) {
                DBmanager.setPlayerStats((Defender) player);
            } else if (player instanceof Goalkeeper) {
                DBmanager.setPlayerStats((Goalkeeper) player);
            }
        }

    }
    public boolean isHomeTeam () {
        return isHomeTeam;
    }
    @Override
    public String toString () {
        return getName();
    }
}
