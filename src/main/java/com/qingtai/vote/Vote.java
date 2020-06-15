package com.qingtai.vote;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Vote {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer iid;
    private Integer vid;
    private String item;
    private int count;

    public Integer getIid()
    {
        return iid;
    }

    public void setIid(Integer iid)
    {
        this.iid = iid;
    }

    public Integer getVid()
    {
        return vid;
    }

    public void setVid(Integer vid)
    {
        this.vid = vid;
    }

    public String getItem()
    {
        return item;
    }

    public void setItem(String item)
    {
        this.item = item;
    }

    public int getCount()
    {
        return count;
    }

    public void setCount(int count)
    {
        this.count = count;
    }
}