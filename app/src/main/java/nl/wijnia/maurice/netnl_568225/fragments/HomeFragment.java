package nl.wijnia.maurice.netnl_568225.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import nl.wijnia.maurice.netnl_568225.R;
import nl.wijnia.maurice.netnl_568225.Renderers;
import nl.wijnia.maurice.netnl_568225.models.Article;
import nl.wijnia.maurice.netnl_568225.models.Repository;

public class HomeFragment extends Fragment implements ArticlesFragment.ArticlesListener {

    private List<Article> articles;
    private ArticlesFragment articlesFragment;
    private Repository repository;

    public HomeFragment() {
        // Required empty public constructor
        articles = new ArrayList<>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repository = new Repository(this.getContext());
        setupArticlesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle(R.string.app_name);
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    public void setupArticlesFragment() {
        articlesFragment = ArticlesFragment.createInstance(this);
        getChildFragmentManager().beginTransaction().replace(R.id.home_fragment_container, articlesFragment).commit();
    }

    @Override
    public void getArticles() {
        repository.getArticles(articlesFragment, 20);
    }

    @Override
    public void getMore() {
        repository.getArticles(articlesFragment, 20, articlesFragment.getNextArticleId());
    }

    @Override
    public void updateArticles(List<Article> articles) {
        this.articles = articles;
    }
}
