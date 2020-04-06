package com.great.demo.entity;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Component(value = "TB_USER")
@Scope("prototype")
public class TB_USER {
    private Integer row_id;
    @Size(min = 3,max = 16,message = "用户名长度必须在3~16位")
    private String user_id;
    @NotBlank(message = "密码不能为空")
    private String user_pwd;
    @NotEmpty
    private String user_sex;
    @Size(min = 5,max = 15,message = "学位必须在5~15位")
    private String education;
    @NotEmpty
    private String profession;
    @Pattern(regexp="[1][3|4|5|7|8][0-9]{9}",message="手机号码格式不正确")
    private String user_tel;
    @NotEmpty
    private String email;
    private Integer integral;
    private String rtime;
    private Integer state;

    public Integer getRow_id() {
        return row_id;
    }

    public void setRow_id(Integer row_id) {
        this.row_id = row_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_pwd() {
        return user_pwd;
    }

    public void setUser_pwd(String user_pwd) {
        this.user_pwd = user_pwd;
    }

    public String getUser_sex() {
        return user_sex;
    }

    public void setUser_sex(String user_sex) {
        this.user_sex = user_sex;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getUser_tel() {
        return user_tel;
    }

    public void setUser_tel(String user_tel) {
        this.user_tel = user_tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public String getRtime() {
        return rtime;
    }

    public void setRtime(String rtime) {
        this.rtime = rtime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

}
