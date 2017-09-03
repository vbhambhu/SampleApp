package com.gochyou.app.gochyou;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.gochyou.app.gochyou.helpers.SessionManager;


public class AccountFragment extends Fragment {

    SessionManager session;
    Context frameContext;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.frameContext=context;

         // Session Manager
        session = new SessionManager(frameContext);

    }

    public static AccountFragment newInstance() {
        AccountFragment fragment = new AccountFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_account, container, false);

        final Button button = view.findViewById(R.id.logout_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                session.logout();

                System.out.println("logout");
            }
        });

        return view;
    }




}
