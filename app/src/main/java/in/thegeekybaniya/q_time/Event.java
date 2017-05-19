package in.thegeekybaniya.q_time;

import java.util.ArrayList;



public class Event {
    int likes, dislikes;
    String name, key;
    ArrayList<String> likeList, dislikeList;


    public Event(String name) {
        this.name = name;
        this.likes = 0;
        this.dislikes =(0);


        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("abcd");

        this.likeList = arrayList;
        this.dislikeList = arrayList;


    }


    public Event(int likes, int dislikes, String name, String key, ArrayList<String> likeList, ArrayList<String> dislikeList, int report) {
        this.likes = likes;
        this.dislikes = dislikes;
        this.name = name;
        this.key = key;
        this.likeList = likeList;
        this.dislikeList = dislikeList;
    }

    public Event() {

    }

    public void dislikePressedFirst() {
        this.dislikes += 1;
    }

    public void likePressed() {
        this.likes += 1;
        this.dislikes -= 1;

    }

    public void dislikePressed() {
        this.dislikes += 1;
        this.likes -= 1;
    }

    public void likePressedFirst() {
        this.likes += 1;

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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ArrayList<String> getLikeList() {
        return likeList;
    }

    public void setLikeList(ArrayList<String> likeList) {
        this.likeList = likeList;
    }

    @Override
    public String toString() {
        return "Event{" +
                "likes=" + likes +
                ", dislikes=" + dislikes +
                ", name='" + name + '\'' +
                ", key='" + key + '\'' +
                ", likeList=" + likeList +
                ", dislikeList=" + dislikeList +
                ", }";
    }

    public ArrayList<String> getDislikeList() {
        return dislikeList;
    }

    public void setDislikeList(ArrayList<String> dislikeList) {
        this.dislikeList = dislikeList;
    }


}
