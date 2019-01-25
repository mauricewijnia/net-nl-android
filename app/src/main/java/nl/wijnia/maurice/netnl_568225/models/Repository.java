package nl.wijnia.maurice.netnl_568225.models;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import nl.wijnia.maurice.netnl_568225.Renderers;
import nl.wijnia.maurice.netnl_568225.activities.ArticleActivity;
import nl.wijnia.maurice.netnl_568225.fragments.LoginFragment;
import nl.wijnia.maurice.netnl_568225.fragments.RegisterFragment;

public class Repository {
    RequestQueue queue;
    String base_url;
    Gson gson;

    public Repository(Context context){
        this.queue = Volley.newRequestQueue(context);
        this.base_url = "https://inhollandbackend.azurewebsites.net/";
        this.gson = new Gson();
    }


    public void getArticles(final Renderers.Articles context, final int amount) {
        String url = base_url + String.format("api/Articles?count=%d", amount);
        getArticles(context, url);
    }

    public void getArticles(final Renderers.Articles context, final int amount, final int nextId) {
        String url = base_url + String.format("api/Articles/%d?count=%d", nextId, amount);
        getArticles(context, url);
    }


    public void getFeedArticles(final Renderers.Articles context, final int feedId) {
        String url = base_url + String.format("api/Articles?feed=%d", feedId);
        getArticles(context, url);
    }

    public void getFeedArticles(final Renderers.Articles context, final int feedId, final int nextId) {
        String url = base_url + String.format("api/Articles/%d?feed=%d&count=20", nextId, feedId);
        getArticles(context, url);
    }

    public void getLikedArticles(final Renderers.Articles context) {
        String url = base_url + String.format("api/Articles/liked");
        getArticles(context, url);
    }

    public void getArticles(final Renderers.Articles context, final String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Success", response);
                Responses.Articles articlesResponse = gson.fromJson(response, Responses.Articles.class);
                context.setNextArticleId(articlesResponse.nextId);
                context.render(articlesResponse.articles);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.getMessage() != null) {
                    Log.d("Fail", error.getMessage());
                } else {
                    Log.d("Fail", "Error thrown without message");
                }
                context.onFetchError();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                User user = User.currentUser();
                if (user != null) {
                    params.put("x-authtoken", user.authToken);
                }
                return params;
            }
        };
        Log.d("REQUEST", url);
        queue.add(stringRequest);
    }

    public void postLogin(final LoginFragment context, final String username, final String password) {
        String url = base_url + String.format("api/Users/login");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Success", response);
                Responses.Login login = gson.fromJson(response, Responses.Login.class);
                User user = new User(username, login.authToken);
                context.onLoginSuccess(user);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Login", ""+error.networkResponse.statusCode);
                context.onLoginFail("Login failed.");
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };
        queue.add(stringRequest);
    }

    public void postRegister(final RegisterFragment context, final String username, final String password) {
        String url = base_url + String.format("api/Users/register?username=%s&password=%s", username, password);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Success", response);
                Responses.Register register = gson.fromJson(response, Responses.Register.class);
                if (register.success) {
                    context.onRegisterSuccess();
                }
                else {
                    context.onRegisterFail(register.message);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Registration", ""+error.networkResponse.statusCode);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };
        queue.add(stringRequest);
    }

    public void likeArticle(final ArticleActivity context, final Article article) {
        String url = base_url + String.format("api/Articles/%d/like", article.id);
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Success", response);
                context.onLike();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Registration", ""+error.networkResponse.statusCode);
                context.onFail();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                User user = User.currentUser();
                if (user != null) {
                    params.put("x-authtoken", user.authToken);
                }
                return params;
            }
        };

        queue.add(stringRequest);
    }

    public void unlikeArticle(final ArticleActivity context, final Article article) {
        String url = base_url + String.format("api/Articles/%d/like", article.id);
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Success", response);
                context.onUnlike();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Registration", ""+error.networkResponse.statusCode);
                context.onFail();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                User user = User.currentUser();
                if (user != null) {
                    params.put("x-authtoken", user.authToken);
                }
                return params;
            }
        };

        queue.add(stringRequest);
    }

}
