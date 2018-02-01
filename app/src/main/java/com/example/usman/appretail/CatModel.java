package com.example.usman.appretail;

/**
 * Created by Usman on 2/1/2018.
 */

class CatModel {
    private int id;
    private  String name;

    public CatModel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
