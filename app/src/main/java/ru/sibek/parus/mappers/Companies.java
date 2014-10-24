package ru.sibek.parus.mappers;

/**
 * Created by Developer on 24.10.2014.
 */

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class Companies {

    @Expose
    private List<ItemCompany> items = new ArrayList<ItemCompany>();

    public List<ItemCompany> getItems() {
        return items;
    }

    public void setItems(List<ItemCompany> items) {
        this.items = items;
    }

public class ItemCompany {

    @Expose
    private Integer rn;
    @Expose
    private String name;
    @Expose
    private String fullname;
    @Expose
    private Integer agent;

    public Integer getRn() {
        return rn;
    }

    public void setRn(Integer rn) {
        this.rn = rn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Integer getAgent() {
        return agent;
    }

    public void setAgent(Integer agent) {
        this.agent = agent;
    }

}}