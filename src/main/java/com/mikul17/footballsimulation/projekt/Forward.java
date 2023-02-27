package com.mikul17.footballsimulation.projekt;

public class Forward extends Player{
    private int pace;

    //Shooting stats
    private int shooting;
        private int longShot;
        private int closeRangeShot;
        private int header;
        private int freekicks;
        private int penalties;

    //Passing stats
    private int passing;
        private int shortPass;
        private int longPass;

    private int interception;
    private int dribbling;

    public Forward(int age,int height, String name, String lastName, String nationality) {
        super(age,height, name, lastName, nationality);
    }

    //Getters
    public int getPace() {
        return pace;
    }
    public int getLongShot() {
        return longShot;
    }
    public int getCloseRangeShot() {
        return closeRangeShot;
    }
    public int getHeader() {
        return header;
    }
    public int getInterception() {
        return interception;
    }
    public int getFreekicks() {
        return freekicks;
    }
    public int getPenalties() {
        return penalties;
    }
    public int getDribbling() {
        return dribbling;
    }
    public int getShortPass() {
        return shortPass;
    }
    public int getLongPass() {
        return longPass;
    }

    //Setters
    public void setPace(int pace) {
        this.pace = pace;
    }
    public void setLongShot(int longShot) {
        this.longShot = longShot;
    }
    public void setCloseRangeShot(int closeRangeShot) {
        this.closeRangeShot = closeRangeShot;
    }
    public void setHeader(int header) {
        this.header = header;
    }
    public void setInterception(int interception) {
        this.interception = interception;
    }
    public void setFreekicks(int freekicks) {
        this.freekicks = freekicks;
    }
    public void setPenalties(int penalties) {
        this.penalties = penalties;
    }
    public void setDribbling(int dribbling) {
        this.dribbling = dribbling;
    }
    public void setShortPass(int shortPass) {
        this.shortPass = shortPass;
    }
    public void setLongPass(int longPass) {
        this.longPass = longPass;
    }

    //Stats calculations
    public void calculateShooting(){
        this.shooting= (this.longShot+this.closeRangeShot+ this.header + this.freekicks + this.penalties)/5;
    }
    public void calculatePassing(){
        this.passing= (this.shortPass+ this.longPass)/2;
    }
    public void calculateOverall(){
        calculateShooting();
        calculatePassing();
        int overall = (this.shooting+this.passing+this.dribbling+this.pace+ this.interception)/5;
        this.setOverall(overall);
    }
}
