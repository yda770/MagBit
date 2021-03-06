package com.example.yehuda_da.magbit;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.yehuda_da.magbit.dummy.DummyContent;
import com.example.yehuda_da.magbit.models.Magbit;
import com.example.yehuda_da.magbit.models.charity_item_Fragment;
import com.example.yehuda_da.magbit.models.magbit_charity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Login_fragment.LoginFragmentListener, MaibItitemFragment.OnListFragmentInteractionListener, charity_item_Fragment.OnListFragmentInteractionListener, CharityFragment.OnFragmentInteractionListener{
    private Menu navMenu;
    RecyclerView RecyclerViewMagbir;
    MyMaibItitemRecyclerViewAdapter MagbitAdapter;

    public void onListFragmentInteraction(Magbit item)
    {
        final charity_item_Fragment fragment = new charity_item_Fragment();
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, charity_item_Fragment.newInstance(item));
        transaction.addToBackStack(null);
        transaction.commit();

    }
   public void onListFragmentInteraction(com.example.yehuda_da.magbit.models.dummy.DummyContent.DummyItem item){

   }

    public void onFragmentInteraction(Uri uri)
    {

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }
        catch (Exception e)
        {

        }



        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navMenu = navigationView.getMenu();

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content, Login_fragment.newInstance())
                    .commit();
            navMenu
                    .findItem(R.id.logout)
                    .setVisible(false);
        } else {

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content, MaibItitemFragment.newInstance())
                    .commit();
            showUser(FirebaseAuth.getInstance().getCurrentUser());
            navMenu
                    .findItem(R.id.logout)
                    .setVisible(true);

        }
    }

    private void startMaigbitList() {

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
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {

        if (id == R.id.logout) {
            FirebaseAuth.getInstance().signOut();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content, Login_fragment.newInstance())
                    .commit();
            navMenu
            .findItem(R.id.logout)
            .setVisible(false);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onLoginSuccess() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, MaibItitemFragment.newInstance())
                .commit();
        showUser(FirebaseAuth.getInstance().getCurrentUser());
        navMenu
                .findItem(R.id.logout)
                .setVisible(true);
    }


    private void showUser(FirebaseUser currentUser) {

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View v = navigationView.getHeaderView(0);
        TextView userName = v.findViewById(R.id.user_name);
        userName.setText(currentUser.getDisplayName());
        TextView userEmail = v.findViewById(R.id.user_email);
        userEmail.setText(currentUser.getEmail());

        ImageView userImage = v.findViewById(R.id.user_image);
        Glide.with(this)
                .load(currentUser.getPhotoUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(userImage);
    }

    @Override
    public void onListFragmentInteraction(magbit_charity item) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}