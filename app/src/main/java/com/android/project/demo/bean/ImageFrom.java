package com.android.project.demo.bean;

import java.io.Serializable;
import java.util.List;

public class ImageFrom extends Object implements Serializable {
    private String name;
    private String herf;

    private List<ImageFrom> list;

    public void setName(String name) {
        this.name = name;
    }

    public void setHerf(String herf) {
        this.herf = herf;
    }

    public String getHerf() {
        return herf;
    }

    public String getName() {
        return name;
    }

    public void setList(List<ImageFrom> list) {
        this.list = list;
    }

    public List<ImageFrom> getList() {
        return list;
    }
}
