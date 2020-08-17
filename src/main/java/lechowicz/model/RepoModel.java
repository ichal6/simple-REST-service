package lechowicz.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.Date;

public class RepoModel {
    String name;
    String description;
    String url;
    int followers;
    @JsonFormat(pattern="yyyy-MM-dd")
    Date date;

    public RepoModel(String name, String description, String url, int followers, Date date) {
        this.name = name;
        this.description = description;
        this.url = url;
        this.followers = followers;
        this.date = date;
    }

    @JsonGetter("fullName")
    public String getName() {
        return name;
    }

    @JsonGetter("description")
    public String getDescription() {
        return description;
    }

    @JsonGetter("cloneURL")
    public String getUrl() {
        return url;
    }

    @JsonGetter("stars")
    public int getFollowers() {
        return followers;
    }

    @JsonGetter("createdAt")
    public Date getDate() {
        return date;
    }
}
