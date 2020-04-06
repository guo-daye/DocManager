package com.great.demo.entity;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component(value = "TB_ROLE")
@Scope("prototype")
public class TB_ROLE {
    private Integer role_id;
    private String role;

    public Integer getRole_id() {
        return role_id;
    }

    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
