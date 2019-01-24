package nl.wijnia.maurice.netnl_568225.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import nl.wijnia.maurice.netnl_568225.R;
import nl.wijnia.maurice.netnl_568225.Renderers;
import nl.wijnia.maurice.netnl_568225.adapters.ArticleAdapter;
import nl.wijnia.maurice.netnl_568225.models.Article;

public class ArticlesFragment extends Fragment implements Renderers.Articles {

    private List<Article> articles;
    private ArticlesListener listener;
    private Boolean loading;
    private Boolean loadMoreOnScroll = true;
    private Integer nextArticleId;

    private ProgressBar loader;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public ArticlesFragment() {
        loading = false;
    }

    public static ArticlesFragment createInstance(ArticlesListener listener) {
        ArticlesFragment articlesFragment = new ArticlesFragment();
        articlesFragment.listener = listener;
        return articlesFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loader = getView().findViewById(R.id.main_loader);
        initRecyclerView();
        initArticles();
        getArticles();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_articles, container, false);
    }

    private void initArticles() {
        articles = new ArrayList<>();
        adapter = new ArticleAdapter(articles);
        recyclerView.setAdapter(adapter);
    }

    public void initRecyclerView() {
        recyclerView = getView().findViewById(R.id.articles_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);

        initScrollListener();
    }

    public void initScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (loadMoreOnScroll) {
                    int maxScroll = recyclerView.computeVerticalScrollRange();
                    int currentScroll = recyclerView.computeVerticalScrollOffset() + recyclerView.computeVerticalScrollExtent();
                    if ((!loading) && ((maxScroll - 500) < currentScroll)) {
                        getMore();
                    }
                }
            }
        });
    }

    public void disableScrollListener() {
        loadMoreOnScroll = false;
    }


    public void getArticles() {
        startLoader();
        listener.getArticles();
    }

    public void getMore() {
        startLoader();
        listener.getMore();
    }

    public void startLoader() {
        loading = true;
        loader.setVisibility(View.VISIBLE);
    }

    public void stopLoader() {
        loading = false;
        loader.setVisibility(View.GONE);
    }

    @Override
    public void render(List<Article> articles) {
        this.articles.addAll(articles);
        Log.d("DEBUG", String.format("%d", this.articles.size()));
        listener.updateArticles(this.articles);
        adapter.notifyDataSetChanged();
        stopLoader();
    }

    @Override
    public void onFetchError() {
        Toast.makeText(getContext(), R.string.fetch_error, Toast.LENGTH_LONG).show();
        stopLoader();
    }

    @Override
    public int getNextArticleId() {
        if (this.nextArticleId != null) {
            return nextArticleId;
        } else {
            return 0;
        }
    }

    @Override
    public void setNextArticleId(int nextArticleId) {
        this.nextArticleId = nextArticleId;
    }

    public interface ArticlesListener {
        void getArticles();
        void getMore();
        void updateArticles(List<Article> articles);
    }
}
