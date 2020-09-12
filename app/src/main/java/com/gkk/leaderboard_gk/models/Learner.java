package com.gkk.leaderboard_gk.models;

import java.util.Comparator;

public class Learner {

    public static String NAME="name";
    public static String HOURS="hours";
    public static String SCORE="score";
    public static String COUNTRY="country";
    public static String BADGEURL="badgeUrl";

    private String name;
    private int hours;
    private int score;
    private String country;
    private String badgeUrl;

    public Learner() {
    }

    public Learner(String name, int hours, int score, String country, String badgeUrl) {
        this.name = name;
        this.hours = hours;
        this.score = score;
        this.country = country;
        this.badgeUrl = badgeUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBadgeUrl() {
        return badgeUrl;
    }

    public void setBadgeUrl(String badgeUrl) {
        this.badgeUrl = badgeUrl;
    }

    public static class LearnersHoursComparator implements Comparator<Learner> {
        @Override
        public int compare(Learner learner, Learner t1) {
            return Integer.compare (t1.getHours(), learner.getHours());
        }
    }
    public static class LearnersScoreComparator implements Comparator<Learner> {
        @Override
        public int compare(Learner learner, Learner t1) {
            return Integer.compare (t1.getScore(), learner.getScore());
        }
    }
}
