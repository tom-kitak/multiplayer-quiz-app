package client.scenes;

public class Player {

    private int points;

    public Player(){
        this.points = 0;
    }

    public Player(int points) {
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void increasePoints(int x){
        points += x;
    }
}
