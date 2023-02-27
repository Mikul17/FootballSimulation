package com.mikul17.footballsimulation.projekt;

public class Defender extends Player{
    private int pace;
    //defending stats
    private int defending;
        private int headerFight;
        private int block;
        private int interception;
        private int strength;
    //passing stats
    private int passing;
        private int shortPass;
        private int longPass;
    //shooting stats
    private int shooting;
        private int closeRangeShot;
        private int header;
        private int positioning;

    public Defender(int age, int height, String name, String lastName, String nationality) {
        super(age,height, name, lastName, nationality);
    }

    //Getters
    public int getPace() {
        return pace;
    }
    public int getDefending() {
        return defending;
    }
    public int getHeaderFight() {
        return headerFight;
    }
    public int getBlock() {
        return block;
    }
    public int getStrength() {
        return strength;
    }
    public int getInterception() {
        return interception;
    }
    public int getLongPass() {
        return longPass;
    }
    public int getShortPass() {
        return shortPass;
    }
    public int getCloseRangeShot() {
        return closeRangeShot;
    }
    public int getHeader() {
        return header;
    }
    public int getPositioning() {
        return positioning;
    }

    //Setters
    public void setPace(int pace) {
        this.pace = pace;
    }
    public void setHeaderFight(int headerFight) {
        this.headerFight = headerFight;
    }
    public void setBlock(int block) {
        this.block = block;
    }
    public void setInterception(int interception) {
        this.interception = interception;
    }
    public void setStrength(int strength) {
        this.strength = strength;
    }
    public void setShortPass(int shortPass) {
        this.shortPass = shortPass;
    }
    public void setLongPass(int longPass) {
        this.longPass = longPass;
    }
    public void setCloseRangeShot(int closeRangeShot) {
        this.closeRangeShot = closeRangeShot;
    }
    public void setHeader(int header) {
        this.header = header;
    }
    public void setPositioning(int positioning) {
        this.positioning = positioning;
    }

    //Stats calculation
    public void calculateDefending() {
        this.defending = (this.headerFight+this.block+this.interception+this.strength + this.positioning)/5;
    }
    public void calculateShooting(){
        this.shooting= (this.closeRangeShot+ this.header)/2;
    }
    public void calculatePassing(){
        this.passing= (this.shortPass+ this.longPass)/2;
    }
    public void calculateOverall(){
        calculateShooting();
        calculatePassing();
        calculateDefending();
        int overall = (this.shooting+this.passing+ this.pace+ this.defending)/4;
        this.setOverall(overall);
    }
}