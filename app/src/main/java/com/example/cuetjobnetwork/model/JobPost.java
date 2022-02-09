package com.example.cuetjobnetwork.model;

public class JobPost
{
    private String company, date, jobDescrip, jobLoc, jobSkill, jobTitle, postedBy, salary;
    public JobPost()
    {

    }
    public JobPost(String company, String date, String jobDescrip, String jobLoc, String jobSkill,
                   String jobTitle, String postedBy, String salary) {
        this.company = company;
        this.date = date;
        this.jobDescrip = jobDescrip;
        this.jobLoc = jobLoc;
        this.jobSkill = jobSkill;
        this.jobTitle = jobTitle;
        this.postedBy = postedBy;
        this.salary = salary;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getJobDescrip() {
        return jobDescrip;
    }

    public void setJobDescrip(String jobDescrip) {
        this.jobDescrip = jobDescrip;
    }

    public String getJobLoc() {
        return jobLoc;
    }

    public void setJobLoc(String jobLoc) {
        this.jobLoc = jobLoc;
    }

    public String getJobSkill() {
        return jobSkill;
    }

    public void setJobSkill(String jobSkill) {
        this.jobSkill = jobSkill;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }
}
