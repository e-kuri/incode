package com.incode.incode.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;
import java.util.Date;

/**
 * Created by kuri on 6/2/17.
 */

public class Image implements Parcelable {
    private String title;
    private Date publishedAt;
    private String photo;
    Uri photoUri;
    private String id;
    private String comment;
    private boolean isLocal = false;
    public Image(){}

    public Image(String id, String photo, Uri uri, String comment){
        this.id = id;
        this.photo = photo;
        this.comment = comment;
        this.photoUri = uri;
    }

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

    public Uri getPhotoURI() {
        if(photoUri == null && photo != null){
            photoUri = Uri.parse(photo);
        }
        return photoUri;
    }

    public void setPhotoURI(Uri photoURI) {
        this.photoUri = photoURI;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(photo);
        dest.writeString(comment);
    }

    public void setLocal(boolean isLocal){
        this.isLocal = isLocal;
    }

    public boolean isLocal(){
        return this.isLocal;
    }

}
