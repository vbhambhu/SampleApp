package com.gochyou.app.gochyou;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;



import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gochyou.app.gochyou.adapters.ReceivedMessageAdapter;
import com.gochyou.app.gochyou.models.Message;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MessageFragment extends Fragment {

    private Context frameContext;
    private TabLayout tabLayout;
    private Fragment childFragment;


    public static MessageFragment newInstance() {
        MessageFragment fragment = new MessageFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_message, container, false);

        tabLayout = (TabLayout) view.findViewById(R.id.tabs);







        //tabLayout.getTabAt(1).select();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                childFragment = null;

                if(tab.getPosition() == 0){
                    childFragment = new ReceivedMsgFragment();

                } else{
                    childFragment = new SentMsgFragment();

                }


                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.MsgFramesHolder, childFragment).commit();


//                FragmentTransaction transaction =  getActivity().getSupportFragmentManager().beginTransaction();
//                transaction.replace(R.id.MsgFramesHolder, childFragment);
//                transaction.commit();
                //viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



        return view;


    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        childFragment = new ReceivedMsgFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.MsgFramesHolder, childFragment).commit();
    }

}
