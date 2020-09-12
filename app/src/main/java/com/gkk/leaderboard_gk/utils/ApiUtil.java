package com.gkk.leaderboard_gk.utils;

import android.util.Log;

import com.gkk.leaderboard_gk.models.Learner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class ApiUtil {
    public static final String BASE_API_URL = "https://gadsapi.herokuapp.com";
    public static final String LEARNING_LEADERS_PATH = "/api/hours";
    public static final String SKILL_IQ_LEADERS_PATH = "/api/skilliq";

    public static URL buildURL(String branch){
        URL url = null;
        try {
            url = new URL(BASE_API_URL+branch);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getJSon(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        InputStream stream = connection.getInputStream();
        Scanner scanner = new Scanner(stream);
        scanner.useDelimiter("\\A");

        try {
            if(scanner.hasNext()){
                return scanner.next();
            }
        }catch (Exception e){
            throw e;
        }finally {
            connection.disconnect();
        }
        return null;
    }

    public static ArrayList<Learner> getLearnersFromJson(String jsonResult){
        ArrayList<Learner> learners = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(jsonResult);
            Learner learner = null;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                learner = new Learner();
                learner.setName(jsonObject.getString(Learner.NAME));
                learner.setHours(jsonObject.isNull(Learner.HOURS)?0:jsonObject.getInt(Learner.HOURS));
                learner.setScore(jsonObject.isNull(Learner.SCORE)?0:jsonObject.getInt(Learner.SCORE));
                learner.setCountry(jsonObject.getString(Learner.COUNTRY));
                learner.setBadgeUrl(jsonObject.getString(Learner.BADGEURL));

                learners.add(learner);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return learners;
    }
}
