package nl.wijnia.maurice.netnl_568225.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import nl.wijnia.maurice.netnl_568225.R;
import nl.wijnia.maurice.netnl_568225.models.Article;
import nl.wijnia.maurice.netnl_568225.models.Repository;
import nl.wijnia.maurice.netnl_568225.models.User;

public class ArticleActivity extends AppCompatActivity {

    Article article;
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Article");
        repository = new Repository(this);
        initArticle();
        setupButton();
        setupLike();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Do your actions here
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }

        return true;
    }

    public void initArticle() {
        article = (Article) getIntent().getParcelableExtra("article");
        TextView txtTitle = findViewById(R.id.article_title);
        TextView txtArticle = findViewById(R.id.article_text);
        ImageView imgView = findViewById(R.id.article_image);
        txtTitle.setText(article.title);
        txtArticle.setText(article.summary);
        Glide.with(this)
                .load(article.imageUrl)
                .into(imgView);

    }

    public void setupButton() {
        Button btnReadMore = findViewById(R.id.read_more);
        btnReadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(article.url));
                startActivity(browserIntent);
            }
        });
    }

    public void setupLike() {
        User user = User.currentUser();
        final ArticleActivity context = this;
        if (user != null) {
            Button btnLike = findViewById(R.id.btn_like);
            btnLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    repository.likeArticle(context, article);
                }
            });

            Button btnUnlike = findViewById(R.id.btn_unlike);
            btnUnlike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    repository.unlikeArticle(context, article);
                }
            });

            if (article.isLiked) {
                btnLike.setVisibility(View.GONE);
                btnUnlike.setVisibility(View.VISIBLE);
            } else {
                btnLike.setVisibility(View.VISIBLE);
                btnUnlike.setVisibility(View.GONE);
            }
        }
    }

    public void onLike() {
        article.isLiked = true;
        Toast.makeText(this, String.format("Article added to favorites") , Toast.LENGTH_LONG).show();
        setupLike();
    }

    public void onUnlike() {
        article.isLiked = false;
        Toast.makeText(this, String.format("Article removed from favorites") , Toast.LENGTH_LONG).show();
        setupLike();
    }

    public void onFail() {
        Toast.makeText(this, String.format("Something went wrong, please try again") , Toast.LENGTH_LONG).show();

    }
}