package com.great.demo.entity;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "TB_MENU")
@Scope("prototype")
public class TB_MENU {
    private Integer menu_id;
    private String menu_name;
    private String menu_url;
    private Integer parent_id;
    private Integer state;
    private List<TB_MENU> menuList;

    public Integer getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(Integer menu_id) {
        this.menu_id = menu_id;
    }

    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }

    public String getMenu_url() {
        return menu_url;
    }

    public void setMenu_url(String menu_url) {
        this.menu_url = menu_url;
    }

    public Integer getParent_id() {
        return parent_id;
    }

    public void setParent_id(Integer parent_id) {
        this.parent_id = parent_id;
    }

    public List<TB_MENU> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<TB_MENU> menuList) {
        this.menuList = menuList;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "TB_MENU{" +
                "menu_id=" + menu_id +
                ", menu_name='" + menu_name + '\'' +
                ", menu_url='" + menu_url + '\'' +
                ", parent_id=" + parent_id +
                ", state=" + state +
                ", menuList=" + menuList +
                '}';
    }
}
