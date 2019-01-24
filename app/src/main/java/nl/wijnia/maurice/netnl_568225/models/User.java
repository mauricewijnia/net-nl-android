package nl.wijnia.maurice.netnl_568225.models;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.annotations.SerializedName;

import nl.wijnia.maurice.netnl_568225.App;

public class User {
    public String username;
    @SerializedName("AuthToken")
    public String authToken;

    public User(String username, String authToken) {
        this.username = username;
        this.authToken = authToken;
    }

    public void logIn() {
        SharedPreferences settings = App.getContext().getSharedPreferences("USER", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("userName", username);
        editor.putString("authToken", authToken);
        editor.commit();
    }

    public void logOff() {
        SharedPreferences settings = App.getContext().getSharedPreferences("USER", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove("userName");
        editor.remove("authToken");
        editor.commit();
    }

    public static User currentUser() {
        SharedPreferences settings = App.getContext().getSharedPreferences("USER", 0);
        String username = settings.getString("userName", "");
        String authToken = settings.getString("authToken", "");

        if (username == "" || authToken == "") {
            return null;
        }

        return new User(username, authToken);
    }
}
