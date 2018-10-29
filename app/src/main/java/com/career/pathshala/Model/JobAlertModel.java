package com.career.pathshala.Model;

/**
 * Created by rupesh.m on 3/10/2017.
 */

public class JobAlertModel {
    private String id;
    private String content;
    private String url;
    private String image;
    private String examtypeid;
    private String is_deleted;
    private String modified_on;
    private String created_on;
    private String examtype;
    private String is_seen;
    private String type;
    private String msg;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getExamtypeid() {
        return examtypeid;
    }

    public void setExamtypeid(String examtypeid) {
        this.examtypeid = examtypeid;
    }


    public String getExamtype() {
        return examtype;
    }

    public void setExamtype(String examtype) {
        this.examtype = examtype;
    }

    public String getIs_seen() {
        return is_seen;
    }

    public void setIs_seen(String is_seen) {
        this.is_seen = is_seen;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModified_on() {
        return modified_on;
    }

    public void setModified_on(String modified_on) {
        this.modified_on = modified_on;
    }

    public String getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(String is_deleted) {
        this.is_deleted = is_deleted;
    }

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


}

