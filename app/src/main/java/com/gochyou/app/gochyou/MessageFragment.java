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
    private RecyclerView recyclerView;
    private List<Message> messageList = new ArrayList<Message>();
    private RequestQueue requestQueue;
    private Gson gson;

    private ProgressBar spinner;


    private TabLayout tabLayout;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.frameContext=context;
    }


    public static MessageFragment newInstance() {
        MessageFragment fragment = new MessageFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        //Get data from remote
        requestQueue = Volley.newRequestQueue(this.frameContext);

        //Initilaize GsonBuilder
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();

        //Now fetch messages remotely



        fetchMessages();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_message, container, false);
        recyclerView = view.findViewById(R.id.rvMessages);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.frameContext)); //set layoutManger

        //recyclerView.setVisibility(View.GONE);
        spinner=(ProgressBar)view.findViewById(R.id.progress_bar);
        spinner.setVisibility(View.VISIBLE);


//        Toolbar toolbar = (Toolbar) view.findViewById(R.id.someid);
//        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        tabLayout = (TabLayout) view.findViewById(R.id.tabs);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                Fragment selectedFragment = null;


                if(tab.getPosition() == 0){
                    selectedFragment = ReceivedMsgFragment.newInstance();
                } else{
                    selectedFragment = SentMsgFragment.newInstance();
                }

                FragmentTransaction transaction =  getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.MsgFramesHolder, selectedFragment);
                transaction.commit();
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





    private void fetchMessages() {
        System.out.println("Now in fetchMessages");

        String url = "http://10.0.2.2/guesswho/api/get_messages";

        StringRequest request = new StringRequest(Request.Method.GET, url, onMessagesLoaded, onMessagesError);
        requestQueue.add(request);
    }

    private final Response.Listener<String> onMessagesLoaded = new Response.Listener<String>() {

        @Override
        public void onResponse(String response) {


            System.out.println(response);
            //deserilize response and add to messages list.
            messageList = Arrays.asList(gson.fromJson(response, Message[].class));

            System.out.println(messageList);
            //create an adapter
            ReceivedMessageAdapter adapter = new ReceivedMessageAdapter(messageList);
            //finally set adapter
            recyclerView.setAdapter(adapter);

            spinner.setVisibility(View.GONE);
        }
    };

    private final Response.ErrorListener onMessagesError = new Response.ErrorListener() {

        @Override
        public void onErrorResponse(VolleyError error) {
            System.out.println("Messages load fail");
            Log.e("PostActivity", error.toString());
        }
    };


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.messages_nav, menu);  // Use filter.xml from step 1

    }




}
