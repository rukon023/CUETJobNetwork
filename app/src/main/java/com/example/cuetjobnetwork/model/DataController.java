package com.example.cuetjobnetwork.model;

public class DataController {
    JobPost currentJobpost;

    public DataController() {
    }

    public JobPost getCurrentJobpost() {
        return currentJobpost;
    }

    public void setCurrentJobpost(JobPost currentJobpost) {
        this.currentJobpost = currentJobpost;
    }

    public static DataController instance;

    public  static  DataController getInstance(){
        if(instance==null){
            instance=new DataController();
        }
        return  instance;
    }

}
