package ck.edu.com.soccerproject;

//model for one game
public class Game {
    public Game() {
    }

    public Game(String opponent, String score, String date, String location) {
        this.opponent = opponent;
        this.score = score;
        this.date = date;
        this.location = location;
    }

    private String opponent;
    private String score;
    private String date;
    private String location;

    public String getOpponent() {
        return opponent;
    }

    public void setOpponent(String opponent) {
        this.opponent = opponent;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
