package com.gkk.leaderboard_gk.models;

public class Post  {
    private String firstName;
    private String lastName;
    private String email;
    private String githublink;

    public Post() {
    }
    public Post(String firstName, String lastName, String email, String githublink) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.githublink = githublink;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGithublink() {
        return githublink;
    }

    public void setGithublink(String githublink) {
        this.githublink = githublink;
    }
}
