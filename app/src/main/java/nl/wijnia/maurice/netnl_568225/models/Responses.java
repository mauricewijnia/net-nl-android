package nl.wijnia.maurice.netnl_568225.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Responses {
    public class Articles {
        @SerializedName("Results")
        List<Article> articles;

        @SerializedName("NextId")
        int nextId;

        public Articles(List<Article> results) {
            this.articles = results;
        }
    }

    public class Login {
        @SerializedName("AuthToken")
        String authToken;
    }

    public class Register {
        @SerializedName("Success")
        boolean success;
        @SerializedName("Message")
        String message;
    }
}
