import java.util.*;
public class PlayerRecord implements Comparable<PlayerRecord> {
    private String name;
    private int score;

    public PlayerRecord(String _name, int _score){
        name = _name;
        score = _score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return name + "\t" + score;
    }

    @Override
    public int compareTo(PlayerRecord that) {
        return Integer.compare(this.score,that.score);
    }
}
