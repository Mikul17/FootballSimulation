package com.mikul17.footballsimulation.projekt;


import java.util.ArrayList;
import java.util.Objects;


public class Match {

    private final Team homeTeam;
    private final Team awayTeam;

    private Player activePlayer;
    private Player passingPlayer;
    private StringBuilder matchReport = new StringBuilder();

    public Player getActivePlayer() {
        return activePlayer;
    }

    //Match variables
    private boolean isOut = false;
    private boolean isCorner = false;
    private boolean isCornerShot = false;
    private boolean hadDribbled = false;
    private boolean successDribble = false;
    private boolean insideBoxShot = false;
    private boolean gameResume = true;
    boolean goalKick = false;

    //Statistics
    private int homeTeamGoals;
    private int awayTeamGoals;

    private int homeTeamShots;
    private int awayTeamShots;

    private int homeTeamShotsOnTarget;
    private int awayTeamShotsOnTarget;

    private int homeTeamPossession;
    private int awayTeamPossession;

    private int homeTeamCorners;
    private int awayTeamCorners;

    private int homeTeamFouls;
    private int awayTeamFouls;

    private int homeTeamOffsides;
    private int awayTeamOffsides;

    private int homeTeamYellowCards;
    private int awayTeamYellowCards;
    private int homeTeamRedCards;
    private int awayTeamRedCards;

    private int homeTeamSaves;
    private int awayTeamSaves;

    private int homeTeamInterceptions;
    private int awayTeamInterceptions;
    private int homeTeamBlocks;
    private int awayTeamBlocks;

    private int homeTeamPasses;
    private int awayTeamPasses;
    private int homeTeamPassesAccurate;
    private int awayTeamPassesAccurate;

    private int homeTeamDribbles;
    private int awayTeamDribbles;
    private int homeTeamDribblesWon;
    private int awayTeamDribblesWon;

    private int homeTeamFreekicksAmount;
    private int awayTeamFreekicksAmount;

    private int homeTeamPenaltiesAmount;
    private int awayTeamPenaltiesAmount;

    public Match(Team homeTeam, Team awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeTeam.setHomeTeamForPlayers(true);
        this.awayTeam.setHomeTeamForPlayers(false);
        this.awayTeam.setHomeTeam(false);
    }

    @Override
    public String toString() {
        return homeTeam.getName() + " vs " + awayTeam.getName();
    }

    //Getters
    public Team getHomeTeam() {
        return homeTeam;
    }
    public Team getAwayTeam() {
        return awayTeam;
    }
    public int getHomeTeamGoals() {
        return homeTeamGoals;
    }
    public int getAwayTeamGoals(){
        return awayTeamGoals;
    }
    public int getHomeTeamShots () {
        return homeTeamShots;
    }
    public int getAwayTeamShots () {
        return awayTeamShots;
    }
    public int getHomeTeamShotsOnTarget () {
        return homeTeamShotsOnTarget;
    }
    public int getAwayTeamShotsOnTarget () {
        return awayTeamShotsOnTarget;
    }
    public int getHomeTeamPossession () {
        return homeTeamPossession;
    }
    public int getHomeTeamCorners () {
        return homeTeamCorners;
    }
    public int getAwayTeamPossession () {
        return awayTeamPossession;
    }
    public int getAwayTeamCorners () {
        return awayTeamCorners;
    }

    public int getHomeTeamFouls () {
        return homeTeamFouls;
    }

    public int getAwayTeamFouls () {
        return awayTeamFouls;
    }

    public int getHomeTeamOffsides () {
        return homeTeamOffsides;
    }
    public int getAwayTeamOffsides () {
        return awayTeamOffsides;
    }

    public int getHomeTeamYellowCards () {
        return homeTeamYellowCards;
    }

    public int getAwayTeamYellowCards () {
        return awayTeamYellowCards;
    }

    public int getHomeTeamRedCards () {
        return homeTeamRedCards;
    }

    public int getAwayTeamRedCards () {
        return awayTeamRedCards;
    }

    public int getHomeTeamSaves () {
        return homeTeamSaves;
    }

    public int getAwayTeamSaves () {
        return awayTeamSaves;
    }

    public int getHomeTeamInterceptions () {
        return homeTeamInterceptions;
    }

    public int getAwayTeamInterceptions () {
        return awayTeamInterceptions;
    }

    public int getHomeTeamBlocks () {
        return homeTeamBlocks;
    }

    public int getAwayTeamBlocks () {
        return awayTeamBlocks;
    }

    public int getHomeTeamPasses () {
        return homeTeamPasses;
    }

    public int getAwayTeamPasses () {
        return awayTeamPasses;
    }

    public int getHomeTeamPassesAccurate () {
        return homeTeamPassesAccurate;
    }

    public int getAwayTeamPassesAccurate () {
        return awayTeamPassesAccurate;
    }

    public int getHomeTeamDribbles () {
        return homeTeamDribbles;
    }

    public int getAwayTeamDribbles () {
        return awayTeamDribbles;
    }

    public int getHomeTeamDribblesWon () {
        return homeTeamDribblesWon;
    }

    public int getAwayTeamDribblesWon () {
        return awayTeamDribblesWon;
    }

    public int getHomeTeamFreekicksAmount () {
        return homeTeamFreekicksAmount;
    }

    public int getAwayTeamFreekicksAmount () {
        return awayTeamFreekicksAmount;
    }

    public int getHomeTeamPenaltiesAmount () {
        return homeTeamPenaltiesAmount;
    }

    public int getAwayTeamPenaltiesAmount () {
        return awayTeamPenaltiesAmount;
    }

    //Setters
    public void setHomeTeamPossession(int homeTeamPossession){
        this.homeTeamPossession = homeTeamPossession;
    }
    public void setAwayTeamPossession(int awayTeamPossession){
        this.awayTeamPossession = awayTeamPossession;
    }

    //Incrementation
    private void incrementPasses(Player player, boolean isAccurate) {
        if (player.isHomeTeam()) {
            homeTeamPasses++;
            if (isAccurate) {
                homeTeamPassesAccurate++;
            }
        } else {
            awayTeamPasses++;
            if (isAccurate) {
                awayTeamPassesAccurate++;
            }
        }
    }
    private void incrementShots(Player player, boolean isOnTarget) {
        if (player.isHomeTeam()) {
            homeTeamShots++;
            if (isOnTarget) {
                homeTeamShotsOnTarget++;
            }
        } else {
            awayTeamShots++;
            if (isOnTarget) {
                awayTeamShotsOnTarget++;
            }
        }
    }
    private void incrementDribbles(Player player, boolean isWon) {
        if (player.isHomeTeam()) {
            homeTeamDribbles++;
            if (isWon) {
                homeTeamDribblesWon++;
            }
        } else {
            awayTeamDribbles++;
            if (isWon) {
                awayTeamDribblesWon++;
            }
        }
    }
    private void incrementFouls(Player player){
        if(player.isHomeTeam()){
            homeTeamFouls++;
        }else{
            awayTeamFouls++;
        }
    }
    private void incrementCards(Player player, boolean isYellow){
        if(player.isHomeTeam()){
            if(isYellow){
                homeTeamYellowCards++;
            }else{
                homeTeamRedCards++;
            }
        }else{
            if(isYellow){
                awayTeamYellowCards++;
            }else{
                awayTeamRedCards++;
            }
        }
    }
    private void incrementInterceptions(Player player) {
        if (player.isHomeTeam()) {
            homeTeamInterceptions++;
        } else {
            awayTeamInterceptions++;
        }
    }
    private void incrementBlocks(Player player) {
        if (player.isHomeTeam()) {
            homeTeamBlocks++;
        } else {
            awayTeamBlocks++;
        }
    }
    private void incrementOffsides(Player player){
        if(player.isHomeTeam()){
            homeTeamOffsides++;
        }else{
            awayTeamOffsides++;
        }
    }
    private void incrementSaves(Player player) {
        if (player.isHomeTeam()) {
            homeTeamSaves++;
        } else {
            awayTeamSaves++;
        }
    }
    private void incrementCorner(Player player) {
        if (player.isHomeTeam()) {
            homeTeamCorners++;
        } else {
            awayTeamCorners++;
        }
    }
    private void incrementGoals(Player player) {
        activePlayer.score();
        if (player.isHomeTeam()) {
            homeTeamGoals++;
        } else {
            awayTeamGoals++;
        }
        gameResume=true;
    }
    private void incrementFreekicks(Player player) {
        if (player.isHomeTeam()) {
            homeTeamFreekicksAmount++;
        } else {
            awayTeamFreekicksAmount++;
        }
    }
    private void incrementPenalties(Player player) {
        if (player.isHomeTeam()) {
            homeTeamPenaltiesAmount++;
        } else {
            awayTeamPenaltiesAmount++;
        }
    }

    //Percentage calculation
    private boolean calculateChance(int chance){
        if(chance>=100){
            chance=95;
        }

        if(chance<=0){
            chance=5;
        }

        int random = (int) (Math.random() * 100 + 1);
        return random <= chance;
    }
    private boolean calculateActionChance (int chance){
        int random = (int) (Math.random() * 100 + 1);
        if(chance==0){
            return false;
        } else if (chance == 100){
            return true;
        } else {
            return random <= chance;
        }
    }
    private boolean calculateCard(Player player, int gameTime) {
        if (calculateChance(20)) {
            if (calculateChance(30)) {
                matchReport.append(player.getLastName()).append(" got a red card\n");
                calculateInjury(this.activePlayer, gameTime, 2);
                player.setRedCard();
                if (player.isHomeTeam()) {
                    homeTeam.moveToBench(player);
                } else {
                    awayTeam.moveToBench(player);
                }
                incrementCards(player,false);

            }else{
                matchReport.append(player.getLastName()).append(" got a yellow card\n");
                calculateInjury(this.activePlayer, gameTime, 1);
                incrementCards(player,true);
                if(!player.setYellowCard()){
                    incrementCards(player,false);
                    if(player.isHomeTeam()){
                        homeTeam.moveToBench(player);
                    }else{
                        awayTeam.moveToBench(player);
                    }
                }
            }
            return true;
        }
        return false;
    }
    private void calculateInjury(Player player, int gameTime, int injurySeverity) {
        if (calculateChance(5+injurySeverity*3)) {
            player.setInjury(calculateInjuryTime());
            matchReport.append(gameTime).append(": ").append(player.getLastName()).append(": got injured and will be out for ").append(player.getInjuryTime()).append(" games\n");
            if(player.isHomeTeam()){
                homeTeam.swapPlayerWithBench(player);
            }else{
                awayTeam.swapPlayerWithBench(player);
            }
        }else{
            matchReport.append(gameTime).append(": ").append(player.getLastName()).append(": got injured but he will walk it off\n");
        }
    }
    private int calculateInjuryTime(){
        return (int) (Math.random() * 2);
    }

    //Checkers
    private boolean checkIfEnoughPlayers(){
        if(homeTeamRedCards >=3 || awayTeamRedCards >=3){
            matchReport.append("Match has been stopped due to red cards!\n");
            if(homeTeamRedCards >=3){
                homeTeamGoals=0;
                awayTeamGoals = 3;
                matchReport.append("Home team has been disqualified and lost the match 3-0!\n");
            }
            else{
                matchReport.append("Away team has been disqualified and lost the match 3-0!\n");
                homeTeamGoals =3;
                awayTeamGoals = 0;
            }
            return true;
        }
        return false;
    }
    private void checkPassStatus(Player passingPlayer, Player opponent,Player teammate, int gameTime) {
        int interceptionValue;
        if(opponent instanceof Midfielder){
            interceptionValue = ((Midfielder) opponent).getInterception();
        }else if(opponent instanceof Defender){
            interceptionValue = ((Defender) opponent).getInterception();
        }else{
            interceptionValue = ((Forward) opponent).getInterception();
        }
        if (calculateChance(interceptionValue)) {
            matchReport.append(passingPlayer.getLastName()).append(" failed to pass the ball. ").append(opponent.getLastName()).append(" intercepted the ball\n");
            incrementInterceptions(opponent);
            if(foul(teammate, opponent, gameTime)) {
                freeKick("long",gameTime, teammate.isHomeTeam());
                return;
            }
        } else {
            matchReport.append(passingPlayer.getLastName()).append(" failed to pass the ball and the ball went off the pitch\n");
            matchReport.append(gameTime).append(": ").append("Now ").append(opponent.getLastName()).append(" will start the game from throw in!\n");
            isOut = true;
        }
        this.passingPlayer = null;
        activePlayer= opponent;
    }
    private boolean checkForOffside(int gameTime, Player teammate, Player opponent){
        if(gameResume){
            gameResume=false;
            return false;
        }
        if(teammate instanceof Forward){
            if(calculateChance(25+((Defender)opponent).getPositioning())){
                incrementOffsides(teammate);
                matchReport.append(gameTime).append(teammate.getLastName()).append(" was offside\n");
                this.activePlayer = opponent;
                gameResume=true;
                return true;
            }
        }
        return false;
    }

    //Players access
    private Player getRandomMidfielder(boolean isHomeTeam){
        Player returned;
        if(isHomeTeam) {
            do{
                returned = this.homeTeam.getMidfielders().get((int) (Math.random() * this.homeTeam.getMidfielders().size()));
            }while(returned == this.activePlayer);
        } else {
            do{
                returned = this.awayTeam.getMidfielders().get((int) (Math.random() * this.awayTeam.getMidfielders().size()));
            }while(returned == this.activePlayer);
        }
        return returned;
    }
    private Player getEnemyGoalkeeper(){
        if(this.activePlayer.isHomeTeam()){
            return this.awayTeam.getActiveGoalkeeper();
        }else{
            return this.homeTeam.getActiveGoalkeeper();
        }
    }
    private Player getRandomDefender(boolean isHomeTeam){
        Player returned;
        if(isHomeTeam) {
            do{
                returned = this.homeTeam.getDefenders().get((int) (Math.random() * this.homeTeam.getDefenders().size()));
            }while(returned == this.activePlayer);
        } else {
            do{
                returned = this.awayTeam.getDefenders().get((int) (Math.random() * this.awayTeam.getDefenders().size()));
            }while(returned == this.activePlayer);
        }
        return returned;
    }
    private Player getRandomForward(boolean isHomeTeam){
        Player returned;
        if(isHomeTeam){
            do{
                returned = this.homeTeam.getAttackers().get((int) (Math.random() * this.homeTeam.getAttackers().size()));
            }while(returned == this.activePlayer);
        } else {
            do{
                returned = this.awayTeam.getAttackers().get((int) (Math.random() * this.awayTeam.getAttackers().size()));
            }while(returned == this.activePlayer);
        }
        return returned;
    }
    private ArrayList<Player> createPossiblePlayers(Player without) {
        ArrayList<Player> possiblePlayers = new ArrayList<>();
        if (without.isHomeTeam()) {
            possiblePlayers.addAll(this.homeTeam.getAttackers());
            possiblePlayers.addAll(this.homeTeam.getMidfielders());
        }else{
            possiblePlayers.addAll(this.awayTeam.getAttackers());
            possiblePlayers.addAll(this.awayTeam.getMidfielders());
        }
        possiblePlayers.remove(without);
        return possiblePlayers;
    }

    //Match event methods
    private void corner(int gameTime){

        Team ourTeam;
        Team enemyTeam;

        if (this.activePlayer.isHomeTeam()) {
            ourTeam = homeTeam;
            enemyTeam = awayTeam;
        } else {
            ourTeam = awayTeam;
            enemyTeam = homeTeam;
        }


        incrementCorner(activePlayer);
        isCorner = false;
        if (calculateChance(50 + ((Midfielder) this.activePlayer).getCrossPass())) {
            matchReport.append(this.activePlayer.getLastName()).append(" passed the ball to the box\n");
            Player luckyPlayer;
            do{
                luckyPlayer = ourTeam.getStartingField().get((int) (Math.random() * (ourTeam.getStartingField().size())));
            }while(luckyPlayer.equals(this.activePlayer));
            if(foul(luckyPlayer,getRandomMidfielder(!luckyPlayer.isHomeTeam()), gameTime)){
                penalty(gameTime,luckyPlayer.isHomeTeam());
                return;
            }
            this.passingPlayer=this.activePlayer;
            this.activePlayer=luckyPlayer;
            matchReport.append(this.activePlayer.getLastName()).append(" receives the ball\n");
            incrementPasses(activePlayer, true);
            isCornerShot = true;
        } else {
            incrementPasses(activePlayer, false);
            if (calculateChance(30)) {
                isCorner = true;
                incrementCorner(activePlayer);
                matchReport.append(this.activePlayer.getLastName()).append(" passed the ball to the box but ").append(enemyTeam.getActiveGoalkeeper().getLastName()).append(" sent the ball off the pitch\n");
            } else {
                matchReport.append(gameTime).append(":").append("Corner failed and goalkeeper(").append(enemyTeam.getActiveGoalkeeper().getLastName()).append(") caught the ball\n");
                incrementSaves(enemyTeam.getActiveGoalkeeper());
                activePlayer =  enemyTeam.getActiveGoalkeeper();
            }
        }
    }
    private boolean foul(Player teammate, Player opponent, int gameTime){
        if(calculateChance(10)){
            incrementFouls(opponent);
            matchReport.append(opponent.getLastName()).append(" committed a foul\n");
            if(!calculateCard(opponent,gameTime)){
                calculateInjury(teammate,gameTime,0);
                this.activePlayer = getRandomMidfielder(teammate.isHomeTeam());
                return true;
            }
        }
        return false;
    }
    private boolean block(int hitChance, Defender opponent, int gameTime){
        if(gameResume){
            gameResume=false;
            return false;
        }
        if(calculateChance(30+(hitChance-opponent.getBlock()))){
            incrementBlocks(opponent);
            matchReport.append(gameTime).append(": ").append(opponent.getLastName()).append(" blocked the shot\n");
            this.activePlayer = opponent;
            return true;
        }
        return false;
    }
    private void freeKick(String type, int gameTime, boolean isHomeTeam) {
        int freekickValue;
        ArrayList<Player> possiblePlayers = createPossiblePlayers(activePlayer);
        Defender defender = (Defender) getRandomDefender(!isHomeTeam);


        if (isHomeTeam) {
            this.activePlayer = homeTeam.getFreeKickTaker() != null ? homeTeam.getFreeKickTaker() : getRandomMidfielder(true);
        } else {
            this.activePlayer = awayTeam.getFreeKickTaker() != null ? awayTeam.getFreeKickTaker() : getRandomMidfielder(false);
        }
        Goalkeeper enemyTeamGoalkeeper = (Goalkeeper) getEnemyGoalkeeper();

        if (this.activePlayer instanceof Midfielder) {
            freekickValue = ((Midfielder) this.activePlayer).getFreekicks();
        } else {
            freekickValue = ((Forward) this.activePlayer).getFreekicks();
        }
        incrementFreekicks(this.activePlayer);

        if (type.equals("long")) {
            if (calculateChance(freekickValue)) {
                matchReport.append(this.activePlayer.getLastName()).append(" is trying to pass the ball to the box!\n");
                incrementPasses(activePlayer, true);
                activePlayer = possiblePlayers.get((int) (Math.random() * (possiblePlayers.size())));
                isCornerShot = true;
            } else {
                if (calculateChance(defender.getInterception())) {
                    matchReport.append(this.activePlayer.getLastName()).append(" failed to pass the ball and ").append(defender.getLastName()).append(" intercepted the ball\n");
                    incrementInterceptions(defender);
                    incrementPasses(activePlayer, false);
                    if (foul(activePlayer, defender, gameTime)) {
                        freeKick("long", gameTime, activePlayer.isHomeTeam());
                    }
                } else {
                    incrementPasses(activePlayer, false);
                    matchReport.append(this.activePlayer.getLastName()).append(" failed to pass the ball and the ball went off the pitch\n");
                    matchReport.append("Now ").append(defender.getLastName()).append(" will start the game from throw in!\n");
                    isOut = true;
                }
                this.activePlayer=defender;
            }
        } else if (type.equals("close")) {
            if (calculateChance(freekickValue-enemyTeamGoalkeeper.getLongShotDefending())) {
                matchReport.append(this.activePlayer.getLastName()).append(" is trying to shoot from a free kick!\n");
                matchReport.append(gameTime).append(": AND HE MADE IT! ").append(this.activePlayer.getLastName()).append(" scored a goal!\n");
                incrementShots(activePlayer, true);
                incrementGoals(this.activePlayer);
                this.activePlayer = getRandomMidfielder(!activePlayer.isHomeTeam());
                gameResume=true;
            } else {
                if (calculateChance(freekickValue)) {
                    matchReport.append(gameTime).append(": but").append(enemyTeamGoalkeeper.getLastName()).append(" is up to the task and saved the ball!\n");
                    incrementShots(activePlayer, false);
                    incrementSaves(enemyTeamGoalkeeper);
                } else {
                    matchReport.append(this.activePlayer.getLastName()).append(" failed to shoot from a free kick and the ball went off the pitch\n");
                    matchReport.append("Now ").append(enemyTeamGoalkeeper.getLastName()).append(" will start the game from kick off!\n");
                    incrementShots(activePlayer, false);
                }
                this.activePlayer = enemyTeamGoalkeeper;
            }
        }
    }
    private void penalty(int gameTime , boolean isHomeTeam){
        int penaltyValue;

        if (isHomeTeam) {
            this.activePlayer = homeTeam.getPenaltyTaker() != null ? homeTeam.getPenaltyTaker() : getRandomForward(true);
        } else {
            this.activePlayer = awayTeam.getPenaltyTaker() != null ? awayTeam.getPenaltyTaker() : getRandomForward(false);
        }

        if (this.activePlayer instanceof Midfielder) {
            penaltyValue = ((Midfielder) this.activePlayer).getPenalties();
        } else {
            penaltyValue = ((Forward) this.activePlayer).getPenalties();
        }
        incrementPenalties(this.activePlayer);
        Goalkeeper enemyTeamGoalkeeper = (Goalkeeper) getEnemyGoalkeeper();


        if (calculateChance(60+penaltyValue-(enemyTeamGoalkeeper.getPenaltySaving()+enemyTeamGoalkeeper.getReflexes()/10))) {
            matchReport.append(this.activePlayer.getLastName()).append(" is trying to shoot from a penalty!\n");
            matchReport.append(gameTime).append(": AND HE MADE IT! ").append(this.activePlayer.getLastName()).append(" scored a goal!\n");
            incrementShots(activePlayer, true);
            incrementGoals(this.activePlayer);
            this.activePlayer = getRandomMidfielder(!activePlayer.isHomeTeam());
        } else {
            if (calculateChance(penaltyValue)) {
                matchReport.append(gameTime).append(": but").append(enemyTeamGoalkeeper.getLastName()).append(" is up to the task and saved the ball!\n");
                incrementShots(activePlayer, false);
                incrementSaves(enemyTeamGoalkeeper);
            } else {
                matchReport.append(this.activePlayer.getLastName()).append(" failed to shoot from a penalty and the ball went off the pitch\n");
                matchReport.append("Now ").append(enemyTeamGoalkeeper.getLastName()).append(" will start the game from kick off!\n");
                incrementShots(activePlayer, false);
            }
            this.activePlayer = enemyTeamGoalkeeper;
        }
    }
    private void pass(String passType, int gameTime) {


        Player ourForward = getRandomForward(this.activePlayer.isHomeTeam());
        Player ourMidfielder = getRandomMidfielder(this.activePlayer.isHomeTeam());
        Player ourDefender = getRandomDefender(this.activePlayer.isHomeTeam());
        Player enemyForward = getRandomForward(!this.activePlayer.isHomeTeam());
        Player enemyMidfielder = getRandomMidfielder(!this.activePlayer.isHomeTeam());
        Player enemyDefender = getRandomDefender(!this.activePlayer.isHomeTeam());


        int shortPassChance ;
        int longPassChance ;
        int forwardPassChance = 0;
        Player closeTeammate;
        Player longTeammate;
        Player closeOpponent;
        Player longOpponent;
        Player longOpponent2 = null;
        Player longTeammate2 = null;



        if(this.activePlayer instanceof Midfielder){
            forwardPassChance = 50+ ((Midfielder) this.activePlayer).getVision();
            shortPassChance = 50+((Midfielder) this.activePlayer).getShortPass()-((Midfielder)enemyMidfielder).getInterception();
            longPassChance = 50+((Midfielder) this.activePlayer).getLongPass();
            closeTeammate = ourMidfielder;
            closeOpponent = enemyMidfielder;
            longTeammate = ourForward;
            longOpponent = enemyDefender;
            longTeammate2 = ourDefender;
            longOpponent2 = enemyForward;

        }else if (this.activePlayer instanceof Forward){
            shortPassChance = 50+((Forward) this.activePlayer).getShortPass()-((Defender)enemyDefender).getInterception();
            longPassChance = 50+((Forward) this.activePlayer).getLongPass()-((Midfielder)enemyMidfielder).getInterception();
            closeTeammate = ourForward;
            closeOpponent = enemyDefender;
            longTeammate = ourMidfielder;
            longOpponent = enemyMidfielder;
        }else if(this.activePlayer instanceof Defender){
            shortPassChance = ((Defender) this.activePlayer).getShortPass();
            longPassChance = ((Defender) this.activePlayer).getLongPass();
            closeTeammate = ourDefender;
            closeOpponent = enemyForward;
            longTeammate= ourMidfielder;
            longOpponent = enemyMidfielder;
        }else{
            forwardPassChance = 50;
            shortPassChance = ((Goalkeeper) this.activePlayer).getShortPasses();
            longPassChance = 50+((Goalkeeper) this.activePlayer).getLongPasses();
            closeTeammate = ourDefender;
            closeOpponent = enemyForward;
            longTeammate = ourForward;
            longOpponent = enemyDefender;
            longTeammate2 = ourMidfielder;
            longOpponent2 = enemyMidfielder;

        }


        if (isOut) {
            isOut = false;
            if (calculateChance(60)) {
                matchReport.append(activePlayer.getLastName()).append(" passed the ball to ").append(ourMidfielder.getLastName()).append("\n");
                this.passingPlayer = this.activePlayer;
                this.activePlayer=ourMidfielder;
                incrementPasses(activePlayer, true);
            } else {
                matchReport.append(activePlayer.getLastName()).append(" passed the ball to ").append(ourForward.getLastName()).append("\n");
                this.passingPlayer= activePlayer;
                this.activePlayer=ourForward;
                incrementPasses(activePlayer, true);
            }
            return;
        }

        if (isCorner) {
            corner(gameTime);
            return;
        }

        if(gameResume){
            gameResume=false;
            matchReport.append(gameTime).append(": ").append(this.activePlayer.getLastName()).append(" passed the ball to ").append(this.passingPlayer.getLastName()).append("\n");
            this.activePlayer=closeTeammate;
            return;
        }

        if (passType.equals("short")) {
            if (calculateChance((50 + shortPassChance))) {
                incrementPasses(activePlayer, true);
                matchReport.append(this.activePlayer.getLastName()).append(" passed the ball to ").append(closeTeammate.getLastName()).append("\n");
                if(checkForOffside(gameTime,closeTeammate,closeOpponent)){
                    return;
                }
                this.passingPlayer= this.activePlayer;
                this.activePlayer = closeTeammate;
            } else {
                incrementPasses(activePlayer, false);
                checkPassStatus(this.activePlayer, closeOpponent,closeTeammate, gameTime);
            }
        }
        else if(passType.equals("long") && !(this.activePlayer instanceof Goalkeeper || this.activePlayer instanceof Midfielder)){
            if(calculateChance((50 + longPassChance))){
                incrementPasses(activePlayer, true);
                if(checkForOffside(gameTime,longTeammate,longOpponent)){
                    return;
                }
                matchReport.append(this.activePlayer.getLastName()).append(" passed the ball to ").append(longTeammate.getLastName()).append("\n");
                this.passingPlayer= this.activePlayer;
                this.activePlayer = longTeammate;
            }else {
                incrementPasses(activePlayer, false);
                checkPassStatus(this.activePlayer, longOpponent,longTeammate, gameTime);
            }
        }else{
            if(calculateChance(forwardPassChance)){
                if(calculateChance(longPassChance - ((Defender)longOpponent).getInterception())) {
                    incrementPasses(activePlayer, true);
                    matchReport.append(this.activePlayer.getLastName()).append(" passed the ball to ").append(longTeammate.getLastName()).append("\n");
                    this.passingPlayer= this.activePlayer;
                    this.activePlayer = longTeammate;
                }else{
                    incrementPasses(activePlayer, false);
                    checkPassStatus(this.activePlayer, longOpponent,longTeammate, gameTime);
                }
            }else {
                longPassChance = (this.activePlayer instanceof Midfielder) ? 60 + (((Midfielder) this.activePlayer).getLongPass() - ((Defender) enemyDefender).getInterception())/2 : 50 + ((Goalkeeper) this.activePlayer).getLongPasses() - ((Midfielder) enemyMidfielder).getInterception();
                if (calculateChance(longPassChance)) {
                    incrementPasses(activePlayer, true);
                    if(checkForOffside(gameTime,longTeammate,longOpponent)){
                        return;
                    }
                    matchReport.append(this.activePlayer.getLastName()).append(" passed the ball to ").append(Objects.requireNonNull(longTeammate2, "Wrong player type").getLastName()).append("\n");
                    this.passingPlayer= this.activePlayer;
                    this.activePlayer = longTeammate2;
                } else {
                    incrementPasses(activePlayer, false);
                    checkPassStatus(this.activePlayer, longOpponent2,longTeammate2, gameTime);
                }
            }
        }
    }
    private void dribble(int gameTime) {

        int dribblingChance;
        int pace;



        if(this.activePlayer instanceof Midfielder){
            dribblingChance = 50+ ((Midfielder) this.activePlayer).getDribbling();
            pace = ((Midfielder) this.activePlayer).getPace();
        }else if(this.activePlayer instanceof Forward){
            dribblingChance = 50 + ((Forward) this.activePlayer).getDribbling();
            pace= ((Forward) this.activePlayer).getPace();
        }else{
            throw new IllegalArgumentException("Dribbling can be done only by Midfielder or Forward");
        }

        Player enemyDefender = getRandomDefender(!this.activePlayer.isHomeTeam());
        Player enemyMidfielder = getRandomMidfielder(!this.activePlayer.isHomeTeam());

        if (!hadDribbled) {
            if(foul(this.activePlayer, enemyMidfielder, gameTime)){
                freeKick("long",gameTime, this.activePlayer.isHomeTeam());
                return;
            }
            if (calculateChance((50 + (dribblingChance- ((Midfielder) enemyMidfielder).getInterception())))) {
                matchReport.append(this.activePlayer.getLastName()).append(" dribbled over ").append(enemyMidfielder.getLastName()).append("\n");
                incrementDribbles(activePlayer, true);
                hadDribbled = true;
            } else {
                matchReport.append(this.activePlayer.getLastName()).append(" failed to dribble over ").append(enemyMidfielder.getLastName()).append("\n");
                hadDribbled = false;
                incrementDribbles(activePlayer, false);
                this.activePlayer = enemyMidfielder;
            }
        } else {
            hadDribbled=false;
            if (calculateChance((50 + (dribblingChance - ((Defender) enemyDefender).getDefending())))) {
                matchReport.append(this.activePlayer.getLastName()).append(" dribbled over ").append(enemyDefender.getLastName()).append(" and is heading towards the goal!").append("\n");
                successDribble = true;
                incrementDribbles(activePlayer, true);
                if(foul(this.activePlayer, enemyMidfielder, gameTime)){
                    freeKick("close",gameTime, this.activePlayer.isHomeTeam());
                    return;
                }
                if (pace> ((Defender) enemyDefender).getPace()) {
                    matchReport.append(this.activePlayer.getLastName()).append(" is faster than ").append(enemyDefender.getLastName()).append(" and have made it inside the box!\n");
                    insideBoxShot = true;
                    successDribble = false;
                    if(foul(this.activePlayer, enemyDefender, gameTime)){
                        penalty(gameTime,this.activePlayer.isHomeTeam());
                    }
                }
            } else {
                matchReport.append(this.activePlayer.getLastName()).append(" failed to dribble over ").append(enemyDefender.getLastName()).append("\n");
                incrementDribbles(activePlayer, false);
                successDribble = false;
                this.activePlayer = enemyDefender;
            }
        }
    }
    private void shoot(String shootType,int gameTime) {


        Player enemyGoalkeeper = getEnemyGoalkeeper();
        Player enemyDefender = getRandomDefender(false);


        int closeShot = 0;
        int longShot = 0;
        int header = 0;

        if (this.activePlayer instanceof Midfielder) {
            closeShot = ((Midfielder) this.activePlayer).getCloseRangeShot();
            longShot = ((Midfielder) this.activePlayer).getLongShot();
            header = ((Midfielder) this.activePlayer).getHeader();
        } else if (this.activePlayer instanceof Forward) {
            closeShot = ((Forward) this.activePlayer).getCloseRangeShot();
            longShot = ((Forward) this.activePlayer).getLongShot();
            header = ((Forward) this.activePlayer).getHeader();
        } else if (this.activePlayer instanceof Defender) {
            header = ((Defender) this.activePlayer).getHeader();
            closeShot = ((Defender) this.activePlayer).getCloseRangeShot();
        }


        switch (shootType) {
            case "long" -> {
                int successPercent = (longShot -15 - ((Goalkeeper) enemyGoalkeeper).getLongShotDefending());
                if (calculateChance(successPercent)) {
                    incrementShots(this.activePlayer, true);
                    incrementGoals(this.activePlayer);
                    gameResume=true;
                    if(this.passingPlayer != null){
                        this.passingPlayer.assist();
                        matchReport.append(this.activePlayer.getLastName()).append(" shot the ball from long distance and it's a goal! (assisted by ").append(this.passingPlayer.getLastName()).append(")\n");
                    }else{
                        matchReport.append(this.activePlayer.getLastName()).append(" shot the ball from long distance and it's a goal!\n");
                    }
                    this.activePlayer = getRandomMidfielder(false);
                    matchReport.append(gameTime).append(": ").append(this.activePlayer.getLastName()).append(" will start the game from back from middle\n");
                } else {
                    if(calculateChance(longShot)){
                        if (calculateChance(((Goalkeeper) enemyGoalkeeper).getDiving())) {
                            matchReport.append(this.activePlayer.getLastName()).append(" shot the ball from long distance but ").append(enemyGoalkeeper.getLastName()).append(" saved it!\n");
                            this.activePlayer = enemyGoalkeeper;
                        }else{
                            matchReport.append(this.activePlayer.getLastName()).append(" shot the ball from long distance but ").append(enemyGoalkeeper.getLastName()).append(" parades the ball out of bounds! And we gonna have a corner!\n");
                            isCorner = true;
                            this.activePlayer = getRandomMidfielder(this.activePlayer.isHomeTeam());
                        }
                        incrementSaves(enemyGoalkeeper);
                        incrementShots(activePlayer, true);

                    }
                    else {
                        matchReport.append(this.activePlayer.getLastName()).append(" shot the ball from long distance and completely missed!\n");
                        incrementShots(activePlayer, false);
                        this.activePlayer = enemyGoalkeeper;
                        matchReport.append(this.activePlayer.getLastName()).append(" will start the game from a goal kick!\n");
                        goalKick=true;
                    }
                }
            }
            case "short" -> {
                int reflexBonus = ((Goalkeeper) enemyGoalkeeper).getReflexes()/10;
                int successPercent = ((closeShot - ((Goalkeeper) enemyGoalkeeper).getCloseRangeDefending())-reflexBonus);
                if (calculateChance(successPercent)) {
                    if(block(closeShot, (Defender) enemyDefender,gameTime)){
                        return;
                    }
                    //matchReport.append(this.activePlayer.getLastName()).append(" finds his space inside the box and HE SCORES !! (assisted by ").append(this.passingPlayer.getLastName()).append(")\n");
                    incrementShots(activePlayer, true);
                    incrementGoals(activePlayer);
                    gameResume=true;
                    if(this.passingPlayer != null){
                        this.passingPlayer.assist();
                        matchReport.append(this.activePlayer.getLastName()).append(" finds his space inside the box and HE SCORES !! (assisted by ").append(this.passingPlayer.getLastName()).append(")\n");
                    }else{
                        matchReport.append(this.activePlayer.getLastName()).append(" finds his space inside the box and HE SCORES !!\n");
                    }
                    this.activePlayer = getRandomMidfielder(false);
                    matchReport.append(gameTime).append(": ").append(this.activePlayer.getLastName()).append(" will start the game from back from middle\n");
                } else {
                    if (calculateChance(closeShot)) {
                        matchReport.append(this.activePlayer.getLastName()).append(" shot the ball from long distance but ").append(enemyGoalkeeper.getLastName()).append(" saved it!\n");
                        incrementShots(activePlayer, true);
                        incrementSaves(enemyGoalkeeper);
                        this.activePlayer = enemyGoalkeeper;
                    } else {
                        matchReport.append(this.activePlayer.getLastName()).append(" shot the ball from long distance and completely missed!\n");
                        incrementShots(activePlayer, false);
                        this.activePlayer = enemyGoalkeeper;
                        goalKick=true;
                        matchReport.append(this.activePlayer.getLastName()).append(" will start the game from a goal kick!\n");
                    }
                }
            }
            case "header" -> {
                isCornerShot = false;
                int heightBonus = activePlayer.getHeight() > enemyDefender.getHeight() ? activePlayer.getHeight() - enemyDefender.getHeight() : -10;
                int successPercent = heightBonus + (header - ((Defender) enemyDefender).getHeaderFight());
                if (calculateChance(successPercent)) {
                    incrementShots(activePlayer, true);
                    matchReport.append(this.activePlayer.getLastName()).append("jumps for a header!\n");
                    int goalPercent = 30 + (header - ((Goalkeeper) enemyGoalkeeper).getReflexes());
                    if (calculateChance(goalPercent)) {
                        incrementGoals(activePlayer);
                        if(this.passingPlayer != null){
                            this.passingPlayer.assist();
                            matchReport.append(gameTime).append(": and he scores !!!").append(activePlayer.getLastName()).append(" scored a goal! assisted by ").append(this.passingPlayer.getLastName()).append("\n");
                        }else{
                            matchReport.append(gameTime).append(": and he scores !!!").append(activePlayer.getLastName()).append(" scored a goal!\n");
                        }
                        gameResume=true;
                        this.activePlayer = getRandomMidfielder(false);
                        matchReport.append(gameTime).append(": ").append(this.activePlayer.getLastName()).append(" will start the game from back from middle\n");
                    } else {
                        incrementSaves(enemyGoalkeeper);
                        this.activePlayer = enemyGoalkeeper;
                        goalKick = true;
                        matchReport.append(gameTime).append(": and ").append(enemyGoalkeeper.getLastName()).append(" saves it!\n");
                    }
                } else {
                    matchReport.append(this.activePlayer.getLastName()).append(" shot the ball from short distance and it's a miss!\n");
                    incrementShots(activePlayer, false);
                    this.activePlayer = enemyGoalkeeper;
                }
            }
        }
    }

    //Main method for the game
    private Player action(int gameTime) throws Exception {
        int passChance;
        int longPassChance =0;
        int dribbleChance=0;
        int longShotChance=0;

        if(this.activePlayer instanceof Forward) {
            passChance = 50;
            dribbleChance = 70;
            longShotChance=40-(hadDribbled?40:0);
        }
        else if(this.activePlayer instanceof Midfielder) {
            passChance = 60-(hadDribbled?10:0)-(successDribble?20:0);
            dribbleChance = successDribble?0:(50-(hadDribbled?10:0));
            longShotChance = 30-(hadDribbled?20:0);
            longPassChance = successDribble?0:(40-(hadDribbled?30:0));
        }
        else if(this.activePlayer instanceof Defender) {
            passChance = 100;
            longPassChance = 50;
        }
        else if(this.activePlayer instanceof Goalkeeper) {
            passChance = 100;
            longPassChance = 50;
        }else{
            throw new Exception("Player type not found");
        }

        if(goalKick){
            goalKick=false;
            passChance = 100;
        }

        if(insideBoxShot){
            shoot("short",gameTime);
            insideBoxShot=false;
            return this.activePlayer;
        }

        if(isCorner){
            passChance = 100;
        }

        if(isOut){
            passChance=100;
        }

        if(isCornerShot){
            shoot("header",gameTime);
        }

        if(gameResume){
            gameResume=false;
            passChance=100;
        }

        if(calculateActionChance(passChance)){
            if(calculateActionChance(longPassChance)){
                pass("long",gameTime);
            }
            else{
                pass("short",gameTime);
            }
            return this.activePlayer;
        }else if(calculateActionChance(dribbleChance) && !(this.activePlayer instanceof Goalkeeper)){
            dribble(gameTime);
        }else{
            if(calculateActionChance(longShotChance) && !(this.activePlayer instanceof Goalkeeper)){
                shoot("long",gameTime);
            }
            else if (!(this.activePlayer instanceof Goalkeeper)){
                shoot("short",gameTime);
            }else{
                if(calculateActionChance(longPassChance)){
                    pass("long",gameTime);
                }
                else{
                    pass("short",gameTime);
                }
            }
        }

        return this.activePlayer;
    }
    public void simulateMatch() throws Exception {
        activePlayer = homeTeam.getCapitan();

        int gameStart = 1;
        while(gameStart <= 90){
            for(int i=0; i<2; i++){
                if(checkIfEnoughPlayers()){
                    return;
                }
                activePlayer = action(gameStart);
                if(activePlayer.isHomeTeam()){
                    homeTeamPossession++;
                }
            }
            gameStart++;
        }

        homeTeamPossession = (100*homeTeamPossession) /180;
        awayTeamPossession = 100 - homeTeamPossession;
    }
    public String simulateMatch(int minute,int totalExtraTime,boolean isStart) throws Exception {
        if (minute == 1 && isStart) {
            activePlayer = homeTeam.getCapitan();
        } else if (minute == 45 && isStart) {
            activePlayer = awayTeam.getCapitan();
        }

        matchReport = new StringBuilder();


        if (checkIfEnoughPlayers()) {
            throw new Exception("Not enough players");
        }
        for(int i=0; i<2; i++){
            activePlayer = action(minute);
            if (activePlayer.isHomeTeam()) {
                homeTeamPossession++;
            }
        }


        if (minute > 45 && totalExtraTime != 0) {
            return (minute - (minute % 45)) + "+" + minute % 45 + " of extra time: " + matchReport.toString();
        } else {
            return minute + ": " + matchReport.toString();
        }

    }
    public void updateTeamStats(){
        homeTeam.updateGoalsScored(homeTeamGoals);
        homeTeam.updateGoalsConceded(awayTeamGoals);
        homeTeam.updateGoalsDifference();

        awayTeam.updateGoalsScored(awayTeamGoals);
        awayTeam.updateGoalsConceded(homeTeamGoals);
        awayTeam.updateGoalsDifference();

        if(homeTeamGoals>awayTeamGoals){
            homeTeam.updateWins(1);
            awayTeam.updateLosses(1);
            homeTeam.updatePoints(3);
        } else if (homeTeamGoals<awayTeamGoals){
            homeTeam.updateLosses(1);
            awayTeam.updateWins(1);
            awayTeam.updatePoints(3);
        } else {
            homeTeam.updateDraws(1);
            awayTeam.updateDraws(1);
            homeTeam.updatePoints(1);
            awayTeam.updatePoints(1);
        }

    }
}