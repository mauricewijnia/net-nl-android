package nl.wijnia.maurice.netnl_568225.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import nl.wijnia.maurice.netnl_568225.R;
import nl.wijnia.maurice.netnl_568225.models.Article;

public class ArticleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Article");
        initArticle();
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
        Article article = (Article) getIntent().getParcelableExtra("article");
        TextView txtTitle = findViewById(R.id.article_title);
        TextView txtArticle = findViewById(R.id.article_text);
        ImageView imgView = findViewById(R.id.article_image);
        txtTitle.setText(article.title);
        txtArticle.setText(article.summary);
        Glide.with(this)
                .load(article.imageUrl)
                .into(imgView);

    }
}