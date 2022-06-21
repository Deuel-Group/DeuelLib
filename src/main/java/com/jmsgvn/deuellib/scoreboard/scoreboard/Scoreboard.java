package com.jmsgvn.deuellib.scoreboard.scoreboard;

public class Scoreboard {

    private String objectiveName;
    private String objectiveValue;
    private int slot;

    public Scoreboard(String objectiveName, String objectiveValue, int slot) {
        this.objectiveName = objectiveName;
        this.objectiveValue = objectiveValue;
        this.slot = slot;
    }

    public String getObjectiveName() {
        return objectiveName;
    }

    public void setObjectiveName(String objectiveName) {
        this.objectiveName = objectiveName;
    }

    public String getObjectiveValue() {
        return objectiveValue;
    }

    public void setObjectiveValue(String objectiveValue) {
        this.objectiveValue = objectiveValue;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }
}
