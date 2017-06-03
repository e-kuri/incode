package com.incode.incode.model;

import java.util.Date;

/**
 * Created by kuri on 6/2/17.
 */

public class Image {
    private String title;
    private Date publishedAt;
    private String photo;
    private int id;
    private String comment;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getPhotoURL() {
        return photo;
    }

    public void setPhotoURL(String photoURL) {
        this.photo = photoURL;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
