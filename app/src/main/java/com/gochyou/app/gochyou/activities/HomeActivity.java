package com.gochyou.app.gochyou.activities;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.gochyou.app.gochyou.fragments.AccountFragment;
import com.gochyou.app.gochyou.fragments.FriendFragment;
import com.gochyou.app.gochyou.fragments.MessageFragment;
import com.gochyou.app.gochyou.R;
import com.gochyou.app.gochyou.helpers.SessionManager;

public class HomeActivity extends AppCompatActivity {

    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Session Manager
        session = new SessionManager(getApplicationContext());

        if(!session.isLoggedIn()){
            session.createLoginSession(1, "4322432", "Vinod Kumar", "py photo");
        }


        System.out.println("==============" +session.getUserId());

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
