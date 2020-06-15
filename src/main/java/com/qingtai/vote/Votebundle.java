package com.qingtai.vote;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Votebundle {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer vid;
    private String vname;
    private String vdescrip;
    private String vcreator;
    private String[] vuser;

    @Override
    public String toString()
    {
        return "{" + "vid:" + getVid() + "creator:" + getVcreator() + "user:" + getVuser() + "}";
    }

    public Integer getVid() {
        return this.vid;
    }

    public void setVid(Integer vid) {
        this.vid = vid;
    }

    public String getVname() {
        return this.vname;
    }

    public void setVname(String vname) {
        this.vname = vname;
    }

    public String getVdescrip() {
        return this.vdescrip;
    }

    public void setVdescrip(String vdescrip) {
        this.vdescrip = vdescrip;
    }

    public String getVcreator() {
        return this.vcreator;
    }

    public void setVcreator(String user) {
        this.vcreator = user;
    }

    public String[] getVuser() {
        return this.vuser;
    }

    public void setVuser(String[] vuser) {
        this.vuser = vuser;
    }

}