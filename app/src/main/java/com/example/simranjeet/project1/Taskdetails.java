package com.example.simranjeet.project1;

/**
 * Created by simranjeet on 1/8/2018.
 */

public class Taskdetails {
    String user_id;
    String taskid;
    String taskplace;
    String task;
    String lati;
    String longi;
    public Taskdetails(){

    }

    public Taskdetails(String user_id, String taskid, String taskplace, String task, String lati, String longi) {
        this.user_id = user_id;
        this.taskid = taskid;
        this.taskplace = taskplace;
        this.task = task;
        this.lati = lati;
        this.longi = longi;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getTaskid() {
        return taskid;
    }

    public String getTaskplace() {
        return taskplace;
    }

    public String getTask() {
        return task;
    }

    public String getLati() {
        return lati;
    }

    public String getLongi() {
        return longi;
    }
}
