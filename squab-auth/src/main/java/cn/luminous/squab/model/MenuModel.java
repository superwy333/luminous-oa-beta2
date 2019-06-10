package cn.luminous.squab.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MenuModel {


    private String name;


    private List<Map<String,String>> child = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Map<String, String>> getChild() {
        return child;
    }

    public void setChild(List<Map<String, String>> child) {
        this.child = child;
    }
}
