package com.mikul17.footballsimulation;

public class TableData {

    private String name;
    private String homeValue;
    private String awayValue;

    public TableData(String name, String homeValue, String awayValue) {
        this.name = name;
        this.homeValue = homeValue;
        this.awayValue = awayValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHomeValue() {
        return homeValue;
    }

    public void setHomeValue(String homeValue) {
        this.homeValue = homeValue;
    }

    public String getAwayValue() {
        return awayValue;
    }

    public void setAwayValue(String awayValue) {
        this.awayValue = awayValue;
    }
}