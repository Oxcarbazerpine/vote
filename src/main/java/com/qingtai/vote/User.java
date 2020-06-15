package com.qingtai.vote;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
    /**
     *
     */

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer uid;
    // @Id
    private String username;
    private String password;
    private String role;

    @Override
    public String toString()
    {
        return "{" + "id:" + getUid() + "username:" + getUsername() + "password:" + getPassword() + "}";
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUid() {
        return this.uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}