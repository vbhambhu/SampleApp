package com.gochyou.app.gochyou;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.gochyou.app.gochyou.helpers.SessionManager;

public class HomeActivity extends AppCompatActivity {

    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        // Session Manager
        session = new SessionManager(getApplicationContext());

        //session.checkLogin();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ActionBar actionbar = getSupportActionBar();
        actionbar.hide();
        //getActionBar().setDisplayHomeAsUpEnabled(true);

        //Get bottom nav by id
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.HomeBottomNav);

        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.navBtnMsg:
                                selectedFragment = MessageFragment.newInstance();
                                break;
                            case R.id.navBtnFrds:
                                selectedFragment = FriendFragment.newInstance();
                                break;
                            case R.id.navBtnAcnt:
                                selectedFragment = AccountFragment.newInstance();
                                break;
                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.HomeFramesHolder, selectedFragment);
                        transaction.commit();
                        return true;
                    }
                });

        //Manually displaying the first fragment - one time only
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.HomeFramesHolder, MessageFragment.newInstance());
        transaction.commit();


    }



}
