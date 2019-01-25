package nl.wijnia.maurice.netnl_568225;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import nl.wijnia.maurice.netnl_568225.fragments.FeedFragment;
import nl.wijnia.maurice.netnl_568225.fragments.HomeFragment;
import nl.wijnia.maurice.netnl_568225.fragments.LikedArticlesFragment;
import nl.wijnia.maurice.netnl_568225.fragments.LoginFragment;
import nl.wijnia.maurice.netnl_568225.fragments.RegisterFragment;
import nl.wijnia.maurice.netnl_568225.models.User;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    protected NavigationView navigationView;
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigateHome();
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkCurrentUser();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.detach(fragment);
            ft.attach(fragment);
            ft.commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        fragment = null;
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            fragment = new HomeFragment();
        } else if (id == R.id.nav_favorites) {
            fragment = new LikedArticlesFragment();
        } else if (id == R.id.menu_algemeen) {
            fragment = FeedFragment.newInstance(1, "Algemeen");
        } else if (id == R.id.menu_internet) {
            fragment = FeedFragment.newInstance(2, "Internet");
        } else if (id == R.id.menu_sport) {
            fragment = FeedFragment.newInstance(3, "Sport");
        } else if (id == R.id.menu_opmerkelijk) {
            fragment = FeedFragment.newInstance(4, "Opmerkelijk");
        } else if (id == R.id.menu_games) {
            fragment = FeedFragment.newInstance(5, "Games");
        } else if (id == R.id.menu_wetenschap) {
            fragment = FeedFragment.newInstance(6, "Wetenschap");
        } else if (id == R.id.menu_login) {
            fragment = LoginFragment.newInstance();
        } else if (id == R.id.menu_register) {
            fragment = RegisterFragment.newInstance();
        } else if (id == R.id.menu_logoff) {
            logOff();
            navigateHome();
        }

        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, fragment).commit();
            navigationView.getCheckedItem().setChecked(false);
            item.setChecked(true);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void navigateHome() {
        fragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, fragment).commit();
        navigationView.getMenu().getItem(0).setChecked(true);
    }

    public void checkCurrentUser() {
        User user = User.currentUser();
        View header = navigationView.getHeaderView(0);
        TextView txtUsername = header.findViewById(R.id.nav_username);
        MenuItem favorites = navigationView.getMenu().findItem(R.id.nav_favorites);
        MenuItem logoff = navigationView.getMenu().findItem(R.id.menu_logoff);
        MenuItem login = navigationView.getMenu().findItem(R.id.menu_login);
        MenuItem register = navigationView.getMenu().findItem(R.id.menu_register);

        if (user != null) {
            txtUsername.setText(user.username);
            favorites.setVisible(true);
            logoff.setVisible(true);
            login.setVisible(false);
            register.setVisible(false);
        } else {
            txtUsername.setText(R.string.unknown_user);
            favorites.setVisible(false);
            logoff.setVisible(false);
            login.setVisible(true);
            register.setVisible(true);
        }
    }

    public void logOff() {
        User.currentUser().logOff();
        checkCurrentUser();
    }
}
