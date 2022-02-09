package com.example.cuetjobnetwork.model;

public class User {
    private String fName, lName, cuetId, cuetDept, address, email;
    public User(){

    }
    public User(String f_name, String l_name, String cuet_id, String cuet_dept,
                String address, String email){
        this.fName = f_name;
        this.lName = l_name;
        this.cuetId = cuet_id;
        this.cuetDept = cuet_dept;
        this.address = address;
        this.email = email;

    }

    public String getfName() {
        return fName;
    }
    public String getlName() {
        return lName;
    }

    public String getCuetId() {
        return cuetId;
    }

    public String getCuetDept() {
        return cuetDept;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public void setCuetId(String cuetId) {
        this.cuetId = cuetId;
    }

    public void setCuetDept(String cuetDept) {
        this.cuetDept = cuetDept;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
