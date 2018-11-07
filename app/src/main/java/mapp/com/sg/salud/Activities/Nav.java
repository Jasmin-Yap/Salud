package mapp.com.sg.salud.Activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import mapp.com.sg.salud.Fragments.Beer;
import mapp.com.sg.salud.Fragments.cart;
import mapp.com.sg.salud.Fragments.favourites;
import mapp.com.sg.salud.R;
import mapp.com.sg.salud.Fragments.categories;
import mapp.com.sg.salud.Fragments.profile;
import mapp.com.sg.salud.Fragments.wine;

public class Nav extends AppCompatActivity
        implements profile.OnFragmentInteractionListener,
        categories.OnFragmentInteractionListener,
        Beer.OnFragmentInteractionListener,
        wine.OnFragmentInteractionListener,
        cart.OnFragmentInteractionListener,
        favourites.OnFragmentInteractionListener,
        NavigationView.OnNavigationItemSelectedListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
        onNavigationItemSelected(navigationView.getMenu().getItem(0));
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
        getMenuInflater().inflate(R.menu.nav, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_cat) {
            setTitle("Catergories");
            categories frag = new categories();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame, frag, "fragment_cat");
            fragmentTransaction.commitAllowingStateLoss();
        } else if (id == R.id.nav_prof) {
            setTitle("Profile");
            profile frag = new profile();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame, frag, "fragment_profile");
            fragmentTransaction.commitAllowingStateLoss();
        } else if (id == R.id.nav_favs) {
            setTitle("Favourites");
            favourites frag = new favourites();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame, frag, "fragment_profile");
            fragmentTransaction.commitAllowingStateLoss();
        } else if (id == R.id.nav_orders) {
            Toast.makeText(getBaseContext(), "Function currently unavailable", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_signout) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
