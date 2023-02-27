package com.mikul17.footballsimulation.projekt;

public class Goalkeeper extends Player{
    //Goalkeeping stats
    private int goalKeeping;
        private int longShotDefending;
        private int closeRangeDefending;
        private int diving;
        private int reflexes;
        private int penaltySaving;
    //Passing stats
    private int passing;
        private int shortPasses;
        private int longPasses;
    public Goalkeeper(int age, int height, String name, String lastName, String nationality) {
        super(age, height, name, lastName, nationality);
    }

    //Getters
    public int getLongShotDefending() {
        return longShotDefending;
    }
    public int getDiving() {
        return diving;
    }
    public int getReflexes() {
        return reflexes;
    }
    public int getPenaltySaving() {
        return penaltySaving;
    }
    public int getShortPasses() {
        return shortPasses;
    }
    public int getLongPasses() {
        return longPasses;
    }
    public int getCloseRangeDefending() {
        return closeRangeDefending;
    }

    public void setLongShotDefending(int longShotDefending) {
        this.longShotDefending = longShotDefending;
    }
    public void setDiving(int diving) {
        this.diving = diving;
    }
    public void setReflexes(int reflexes) {
        this.reflexes = reflexes;
    }
    public void setPenaltySaving(int penaltySaving) {
        this.penaltySaving = penaltySaving;
    }
    public void setShortPasses(int shortPasses) {
        this.shortPasses = shortPasses;
    }
    public void setLongPasses(int longPasses) {
        this.longPasses = longPasses;
    }
    public void setCloseRangeDefending(int closeRangeDefending) {
        this.closeRangeDefending = closeRangeDefending;
    }

    //Stats calculation
    public void calculateGoalKeeping(){
        this.goalKeeping=(longShotDefending+closeRangeDefending+diving+reflexes+penaltySaving)/5;
    }
    public void calculatePassing(){
        this.passing=(shortPasses+longPasses)/2;
    }
    public void calculateOverall(){
        calculateGoalKeeping();
        calculatePassing();
        int overall = (this.goalKeeping+this.passing)/2;
        this.setOverall(overall);
    }
}
