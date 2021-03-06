package com.littlecorgi.leave.teacher;

public class RecyclerItem {

    private String type;
    private String pass;
    private String student;
    private String time;
    private String reason;

    public RecyclerItem(String type, String pass, String time, String reason, String student) {
        this.type = type;
        this.pass = pass;
        this.time = time;
        this.reason = reason;
        this.student = student;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
