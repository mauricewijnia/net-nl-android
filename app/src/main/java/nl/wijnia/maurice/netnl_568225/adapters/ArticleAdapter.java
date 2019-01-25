package nl.wijnia.maurice.netnl_568225.adapters;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import nl.wijnia.maurice.netnl_568225.R;
import nl.wijnia.maurice.netnl_568225.activities.ArticleActivity;
import nl.wijnia.maurice.netnl_568225.models.Article;
import nl.wijnia.maurice.netnl_568225.models.Category;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {
    private List<Article> articles;

    public ArticleAdapter(List<Article> articles) {
        this.articles = articles;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.article_list_item, viewGroup, false);

        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder articleViewHolder, int position) {
        ImageView img = articleViewHolder.view.findViewById(R.id.img);
        TextView txtTitle = articleViewHolder.view.findViewById(R.id.txtTitle);
        TextView txtCategories = articleViewHolder.view.findViewById(R.id.txtCategories);
        //TextView txtSummary = articleViewHolder.view.findViewById(R.id.txtSummary);

        final Article article = articles.get(position);
        articleViewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ArticleActivity.class);
                intent.putExtra("article", article);
                v.getContext().startActivity(intent);
            }
        });
        String categories = "";
        int index = 0;
        for(Category category : article.categories) {
            index++;
            categories += " " + category.name;
            if (index < article.categories.length) {
                categories += ",";
            }
        }
        View liked = articleViewHolder.view.findViewById(R.id.imgLiked);
        if (article.isLiked) {
            liked.setVisibility(View.VISIBLE);
        } else {
            liked.setVisibility(View.GONE);
        }
        txtTitle.setText(article.title);
        txtCategories.setText(categories);
        //txtSummary.setText(article.summary);
        Glide.with(articleViewHolder.view)
                .load(article.imageUrl)
                .into(img);
    }

    @Override
    public int getItemCount() { return articles.size(); }

    public static class ArticleViewHolder extends RecyclerView.ViewHolder{
        public View view;

        public ArticleViewHolder(View view) {
            super(view);
            this.view = view;
        }
    }

}
