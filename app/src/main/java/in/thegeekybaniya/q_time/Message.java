package in.thegeekybaniya.q_time;

import java.util.Map;

/**
 * Created by Kabir on 11/03/2017.
 */

public class Message {

    String name,enEmail, message;

    Map<String,String> time;

    public Message(String name, String enEmail, String message, Map<String, String> time) {
        this.name = name;
        this.enEmail = enEmail;
        this.message = message;
        this.time = time;
    }

    public Message() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnEmail() {
        return enEmail;
    }

    public void setEnEmail(String enEmail) {
        this.enEmail = enEmail;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, String> getTime() {
        return time;
    }

    public void setTime(Map<String, String> time) {
        this.time = time;
    }
}
