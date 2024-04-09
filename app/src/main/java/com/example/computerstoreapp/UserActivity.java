package com.example.computerstoreapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.computerstoreapp.database.Product;
import com.example.computerstoreapp.fragment.UserCartFragment;
import com.example.computerstoreapp.fragment.UserHistoryFragment;
import com.example.computerstoreapp.fragment.UserHomeFragment;
import com.example.computerstoreapp.fragment.UserProductDetailFragment;
import com.google.android.material.navigation.NavigationView;

public class UserActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, UserProductDetailFragment.OnAddToCartListener {

    private DrawerLayout user_mDrawerLayout;

    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_CART = 1;
    private static final int FRAGMENT_HISTORY = 2;
    private int mCurrentFragment = FRAGMENT_HOME;

    private UserHomeFragment userHomeFragment;
    private UserCartFragment userCartFragment;
    private UserHistoryFragment userHistoryFragment;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Toolbar toolbar = findViewById(R.id.user_toolbar);
        setSupportActionBar(toolbar);

        user_mDrawerLayout = findViewById(R.id.user_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, user_mDrawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        user_mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.user_navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        userHomeFragment = new UserHomeFragment();
        userCartFragment = new UserCartFragment();
        userHistoryFragment = new UserHistoryFragment();

        replaceFragment(userHomeFragment);
        navigationView.getMenu().findItem(R.id.user_nav_home).setChecked(true);

        View headerView = navigationView.getHeaderView(0);

        //set username
        TextView textViewUsername = headerView.findViewById(R.id.show_username);
        textViewUsername.setText(UserSessionManager.getInstance().getUsername());
        //set gmail
        TextView textViewUsergmail = headerView.findViewById(R.id.show_usergmail);
        textViewUsergmail.setText(UserSessionManager.getInstance().getUsergmail());

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.user_logout) {

            logout();//đăng xuất user

        } else if (id == R.id.user_nav_home) {

            if (mCurrentFragment != FRAGMENT_HOME) {
                replaceFragment(userHomeFragment);
                mCurrentFragment = FRAGMENT_HOME;
            }

        } else if (id == R.id.user_nav_cart) {

            if (mCurrentFragment != FRAGMENT_CART) {
                replaceFragment(userCartFragment);
                mCurrentFragment = FRAGMENT_CART;
            }

        } else if (id == R.id.user_nav_history) {

            if (mCurrentFragment != FRAGMENT_HISTORY) {
                replaceFragment(userHistoryFragment);
                mCurrentFragment = FRAGMENT_HISTORY;
            }

        }

        user_mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.user_content_frame, fragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (user_mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            user_mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void logout() {
        UserSessionManager sessionManager = UserSessionManager.getInstance();
        sessionManager.setLoggedIn(false);
        sessionManager.setUsername("");
        sessionManager.setUserid(-1);
        sessionManager.setUserrole(-1);

        // Chuyển hướng đến màn hình đăng nhập
        startActivity(new Intent(UserActivity.this, LoginActivity.class));
        Toast.makeText(this, "Logout successfully", Toast.LENGTH_SHORT).show();
        finish(); // Đóng activity hiện tại
    }

    @Override
    public void onAddToCart(Product product) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("product_add_to_cart", product);

        userCartFragment.setArguments(bundle);

        replaceFragment(userCartFragment);

        NavigationView navigationView = findViewById(R.id.user_navigation_view);
        navigationView.getMenu().findItem(R.id.user_nav_cart).setChecked(true);
        mCurrentFragment = FRAGMENT_CART;
    }
}