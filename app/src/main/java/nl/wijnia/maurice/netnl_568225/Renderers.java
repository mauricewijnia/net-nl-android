package nl.wijnia.maurice.netnl_568225;

import java.util.List;

import nl.wijnia.maurice.netnl_568225.models.Article;

public class Renderers {
    public interface Articles {
        void render(List<Article> articles);
        void onFetchError();

        int getNextArticleId();
        void setNextArticleId(int nextArticleId);
    }
}
