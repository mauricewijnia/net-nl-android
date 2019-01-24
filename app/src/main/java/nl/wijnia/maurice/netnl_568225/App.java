package nl.wijnia.maurice.netnl_568225;

import android.app.Application;
import android.content.Context;

public class App extends Application {
    static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getContext() {
        return context;
    }
}
