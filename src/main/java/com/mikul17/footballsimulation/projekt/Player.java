package com.mikul17.footballsimulation.projekt;

public abstract class Player {
    private final String name;
    private final String lastName;
    private final String nationality;
    private int age;
    private final int height;
    private int overall;
    private boolean isInjured = false;
    private int injuryTime = 0;
    private boolean redCard = false;
    private boolean yellowCard = false;
    private int redCardTime = 0;
    private boolean isHomeTeam = false;
    private int goalCounter = 0;
    private int assistCounter = 0;
    private int redCardCounter = 0;
    private int yellowCardCounter = 0;

    public Player(int age, int height, String name, String lastName, String nationality) {
        this.age = age;
        this.height = height;
        this.name = name;
        this.lastName = lastName;
        this.nationality = nationality;
    }

    //Getters

    public int getAge() {
        return age;
    }
    public String getName() {
        return name;
    }
    public String getLastName() {
        return lastName;
    }
    public String getNationality() {
        return nationality;
    }
    public int getOverall() {
        return overall;
    }
    public int getGoalCounter() {
        return goalCounter;
    }
    public int getAssistCounter() {
        return assistCounter;
    }
    public int getHeight() {
        return height;
    }
    public boolean getIsInjured() {
        return isInjured;
    }
    public int getInjuryTime() {
        return injuryTime;
    }
    public int getRedCardTime () {
        return redCardTime;
    }
    public int getRedCardCounter () {
        return redCardCounter;
    }
    public int getYellowCardCounter () {
        return yellowCardCounter;
    }

    public String getPosition(){
        return this.getClass().getSimpleName();
    }

    //Setters
    public void setOverall(int overall) {
        this.overall = overall;
    }
    public void setHomeTeam(boolean homeTeam) {
        isHomeTeam = homeTeam;
    }
    public boolean setYellowCard() {
        if(this.yellowCard) {
            this.yellowCard = false;
            this.redCard = true;
            yellowCardCounter++;
            redCardCounter++;
            System.out.println(name+" It's his second yellow card so he will be sent off!");
            return false;
        }else{
            this.yellowCard = true;
            yellowCardCounter++;
            return true;
        }
    }
    public void setRedCard(){
        if(!this.redCard){
            this.redCard = true;
            this.redCardTime = 1;
            redCardCounter++;
        }else{
            System.out.println("Player already has red card");
        }
    }
    public void setRedCard(Integer redCard) {
        this.redCard = redCard != 0;
        this.redCardTime = 1;
    }
    public void setInjury(int injuryTime){
        if(!this.isInjured){
            this.injuryTime = injuryTime;
            this.isInjured = true;
        }else{
            System.out.println("Player already injured");
        }
    }
    public void setInjuryTime(int injuryTime){
        this.injuryTime = injuryTime;
    }
    public void setIsInjured(Integer isInjured) {
        this.isInjured = isInjured != 0;
    }
    public void setAge (int i) {
        this.age = i;
    }
    public void setRedCardsCounter (Integer redCardsCounter) {
        this.redCardCounter = redCardsCounter;
    }
    public void setYellowCardsCounter (Integer yellowCards) {
        this.yellowCardCounter = yellowCards;
    }
    public void setGoalCounter (Integer goals) {
        this.goalCounter = goals;
    }
    public void setAssistCounter (Integer assists) {
        this.assistCounter = assists;
    }

    //Overriden methods
    @Override
    public String toString() {
        return getPosition() + " | " + name + " " + lastName + " | " +getOverall()+" | ";
    }
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Player player) {
            return this.name.equals(player.name) && this.lastName.equals(player.lastName);
        }
        return false;
    }

    //Other methods
    public void score() {
        this.goalCounter++;
    }
    public void assist() {
        this.assistCounter++;
    }
    public boolean isHomeTeam() {
        return isHomeTeam;
    }
    public void calculateInjuryTime(){
        if(injuryTime <= 0){
            this.isInjured = false;
        }else{
            this.injuryTime --;
            if(injuryTime == 0){
                this.isInjured = false;
            }
        }
    }
    public boolean isRedCard () {
        return redCard;
    }
    public void decreaseRedCardTime () {
        if(this.isRedCard()){
            this.redCardTime--;
            if(this.redCardTime <=0){
                this.redCard = false;
                this.redCardTime=0;
            }
        }
    }
    public void resetYellowCards () {
        if(this.yellowCard){
            this.yellowCard = false;
        }
    }
    public void incrementAge () {
        this.age++;
    }

}