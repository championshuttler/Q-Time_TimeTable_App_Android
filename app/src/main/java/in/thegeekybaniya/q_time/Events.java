package in.thegeekybaniya.q_time;

/**
 * Created by Kabir on 28/01/2017.
 */

public class Events {
    int likes, dislikes;
    String name;

    public Events() {
    }

    public int getLikes() {

        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Events(int likes, int dislikes, String name) {

        this.likes = likes;
        this.dislikes = dislikes;
        this.name = name;
    }
}
