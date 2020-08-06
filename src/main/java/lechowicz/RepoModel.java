package lechowicz;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonGetter;

public class RepoModel {
    String name;
    String description;
    String url;
    int followers;
    String date;

    public RepoModel(String name, String description, String url, int followers, String date) {
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
    public String getDate() {
        return date;
    }
}
