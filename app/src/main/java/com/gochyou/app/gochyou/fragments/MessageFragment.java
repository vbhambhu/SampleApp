package com.gochyou.app.gochyou.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.gochyou.app.gochyou.R;


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
