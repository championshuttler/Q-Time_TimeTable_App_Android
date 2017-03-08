package in.thegeekybaniya.q_time;

/**
 * Created by Kabir on 29/01/2017.
 */

public class Lectures {

    String subject, teacher, startTime, endTime, roomno;
    int period;

    public String getTeacher() {

        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getRoomno() {
        return roomno;
    }

    public void setRoomno(String roomno) {
        this.roomno = roomno;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public String getSubject() {

        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Lectures(String subject, String teacher, String startTime, String endTime, String roomno, int period) {

        this.subject = subject;
        this.teacher = teacher;
        this.startTime = startTime;
        this.endTime = endTime;
        this.roomno = roomno;
        this.period = period;
    }

    public Lectures(){};
}
