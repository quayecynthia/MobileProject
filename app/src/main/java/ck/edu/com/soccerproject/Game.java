package ck.edu.com.soccerproject;

//Game class
public class Game {

    private String first_team;
    private String second_team;
    private String score;
    private String date;
    private String location;
    private byte[] image;
    public Game() {
    }

    public Game(String first_team, String second_team, String score, String date, String location, byte[] image) {
        this.first_team = first_team;
        this.second_team = second_team;
        this.score = score;
        this.date = date;
        this.location = location;
        this.image = image;
    }


    //Accessors
    public String getFirst_team() {
        return first_team;
    }

    public void setFirst_team(String first_team) {
        this.first_team = first_team;
    }

    public String getSecond_team() {
        return second_team;
    }

    public void setSecond_team(String second_team) {
        this.second_team = second_team;
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

    public byte[] getImage() { return image;}

    public void setImage(byte[] image) {this.image = image;}

}
