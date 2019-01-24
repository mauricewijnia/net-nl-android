package nl.wijnia.maurice.netnl_568225.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import nl.wijnia.maurice.netnl_568225.R;
import nl.wijnia.maurice.netnl_568225.models.Article;
import nl.wijnia.maurice.netnl_568225.models.Repository;

public class FeedFragment extends Fragment implements ArticlesFragment.ArticlesListener{
    private static final String ARG_FEED_ID = "feedId";
    private static final String ARG_FEED_NAME = "feedName";

    private List<Article> articles;
    private ArticlesFragment articlesFragment;
    private Repository repository;
    private int feedId;
    private String feedName;

    public FeedFragment() {
        // Required empty public constructor
        articles = new ArrayList<>();
    }

    public static FeedFragment newInstance(int feedId, String feedName) {
        FeedFragment fragment = new FeedFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_FEED_ID, feedId);
        args.putString(ARG_FEED_NAME, feedName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            feedId = getArguments().getInt(ARG_FEED_ID);
            feedName = getArguments().getString(ARG_FEED_NAME);
        }
        repository = new Repository(this.getContext());
        setupArticlesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle(feedName);
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

    public void setupArticlesFragment() {
        articlesFragment = ArticlesFragment.createInstance(this);
        getChildFragmentManager().beginTransaction().replace(R.id.feed_fragment_container, articlesFragment).commit();
    }

    @Override
    public void getArticles() {
        repository.getFeedArticles(articlesFragment, feedId);
    }

    @Override
    public void getMore() {
        repository.getFeedArticles(articlesFragment, feedId, articlesFragment.getNextArticleId());
    }

    @Override
    public void updateArticles(List<Article> articles) {
        this.articles = articles;
    }
}
