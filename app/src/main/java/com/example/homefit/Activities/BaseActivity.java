package com.example.homefit.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.homefit.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;


import de.hdodenhof.circleimageview.CircleImageView;

public class BaseActivity extends AppCompatActivity implements MenuItem.OnMenuItemClickListener{

    public static final String TAG = "Nope";
    public Dialog aboutDialog;
    private GoogleSignInClient googleSignInClient;
    public FirebaseAuth mAuth;
    private FrameLayout view_stub;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base);
        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);
        view_stub = findViewById(R.id.view_stub);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        drawerLayout = findViewById(R.id.drawerlayout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, 0, 0);
        drawerLayout.addDrawerListener(drawerToggle);
        //set our new burger action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        Menu drawerMenu = navigationView.getMenu();
        for(int i=0 ; i < drawerMenu.size(); i++){
            drawerMenu.getItem(i).setOnMenuItemClickListener(this);
        }

        View header = navigationView.getHeaderView(0);
        TextView name = header.findViewById(R.id.username_tv);
        TextView email = header.findViewById(R.id.useremail_tv);
        CircleImageView userImg = header.findViewById(R.id.user_image);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        for (UserInfo userInfo : user.getProviderData()) {
            String providerId = userInfo.getProviderId();
            if (providerId.equals("google.com")) {
                email.setText(user.getEmail());
                name.setText(user.getDisplayName());
                String photo = user.getPhotoUrl().toString();
                Glide.with(this).load(photo).into(userImg);

            } else {
                String defEmail = mAuth.getCurrentUser().getEmail();
                String defName = defEmail.substring(0, defEmail.indexOf("@"));
                name.setText(defName);
                email.setText(mAuth.getCurrentUser().getEmail());
            }
        }

        //set about dialog from drawer
        aboutDialog = new Dialog(this);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void setContentView(int layoutResID) {
        if (view_stub != null) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            View stubView = inflater.inflate(layoutResID, view_stub, false);
            view_stub.addView(stubView, lp);
        }
    }

    @Override
    public void setContentView(View view) {
        if (view_stub != null) {
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            view_stub.addView(view, lp);
        }
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        if (view_stub != null) {
            view_stub.addView(view, params);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                if (!this.getClass().getName().equals(MainActivity.class.getName())){
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
                drawerLayout.closeDrawers();
                break;
            case R.id.workout:
                Toast.makeText(this, ""+item.getTitle(), Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(BaseActivity.this, BuilderActivity.class);
                startActivity(intent1);
                drawerLayout.closeDrawers();
                break;
            case R.id.nutrition:
                Toast.makeText(this, ""+item.getTitle(), Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(BaseActivity.this, NutriActivity.class);
                startActivity(intent2);
                drawerLayout.closeDrawers();
                break;
            case R.id.progress:
                Toast.makeText(this, ""+item.getTitle(), Toast.LENGTH_SHORT).show();
                Intent intent3 = new Intent(BaseActivity.this, ProgressActivity.class);
                startActivity(intent3);
                drawerLayout.closeDrawers();
                break;
            case R.id.alarm:
                Toast.makeText(this, ""+item.getTitle(), Toast.LENGTH_SHORT).show();
                Intent intent4 = new Intent(BaseActivity.this, AlarmActivity.class);
                startActivity(intent4);
                drawerLayout.closeDrawers();
                break;
            case R.id.about:
                aboutDialog.setContentView(R.layout.about);
                aboutDialog.show();
                drawerLayout.closeDrawers();
                break;
            case R.id.signout:
                signOut();
                finish();

        }
        return false;
    }

    private void signOut() {
        // Firebase sign out
        mAuth.signOut();
        //  Google sign out
        googleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
                        Toast.makeText(BaseActivity.this, "Logged out", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }
                });
    }
}
