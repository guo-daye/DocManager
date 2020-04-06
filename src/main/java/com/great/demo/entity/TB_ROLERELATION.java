package com.great.demo.entity;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component(value = "TB_ROLERELATION")
@Scope("prototype")
public class TB_ROLERELATION {
    private Integer rr_id;
    private Integer row_id;
    private Integer role_id;
    private Integer state;
    private TB_ROLE tb_role;

    public Integer getRr_id() {
        return rr_id;
    }

    public void setRr_id(Integer rr_id) {
        this.rr_id = rr_id;
    }

    public Integer getRow_id() {
        return row_id;
    }

    public void setRow_id(Integer row_id) {
        this.row_id = row_id;
    }

    public Integer getRole_id() {
        return role_id;
    }

    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
    }

    public TB_ROLE getTb_role() {
        return tb_role;
    }

    public void setTb_role(TB_ROLE tb_role) {
        this.tb_role = tb_role;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

}
