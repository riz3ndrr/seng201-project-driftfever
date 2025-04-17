package seng201.team0.models;


public class GameStats {
    private static final GameStats instance = new GameStats();

    private int raceCount = 3;
    private String userName;
    private String raceDifficulty;

    public static GameStats getInstance() {
        return instance;
    }

    public void setRaceCount(int raceCount) {
        this.raceCount = raceCount;
    }
    public int getRaceCount() {
        return raceCount;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserName() {
        return userName;
    }

    public void setRaceDifficulty(String raceDifficulty) {
        this.raceDifficulty = raceDifficulty;
    }
    public String getRaceDifficulty() {
        return raceDifficulty;
    }

}
