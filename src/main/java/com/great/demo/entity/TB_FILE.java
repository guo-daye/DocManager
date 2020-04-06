package com.great.demo.entity;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component(value = "TB_FILE")
@Scope("prototype")
public class TB_FILE {
    private Integer file_id;
    private String file_url;
    private String file_title;
    private String user_id;
    private String upload_time;
    private Integer integral;
    private String type;
    private Integer frequency;
    private Integer state;

    public Integer getFile_id() {
        return file_id;
    }

    public void setFile_id(Integer file_id) {
        this.file_id = file_id;
    }

    public String getFile_url() {
        return file_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }

    public String getFile_title() {
        return file_title;
    }

    public void setFile_title(String file_title) {
        this.file_title = file_title;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUpload_time() {
        return upload_time;
    }

    public void setUpload_time(String upload_time) {
        this.upload_time = upload_time;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "TB_FILE{" +
                "file_id=" + file_id +
                ", file_url='" + file_url + '\'' +
                ", file_title='" + file_title + '\'' +
                ", user_id='" + user_id + '\'' +
                ", upload_time='" + upload_time + '\'' +
                ", integral=" + integral +
                ", type='" + type + '\'' +
                ", frequency=" + frequency +
                ", state=" + state +
                '}';
    }
}
