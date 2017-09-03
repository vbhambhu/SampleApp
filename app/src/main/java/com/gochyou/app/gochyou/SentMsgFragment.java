package com.gochyou.app.gochyou;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
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


public class SentMsgFragment extends Fragment {

    private Context frameContext;
    private RecyclerView recyclerView;
    private List<Message> messageList = new ArrayList<Message>();
    private RequestQueue requestQueue;
    private Gson gson;
    private ProgressBar spinner;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.frameContext=context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get data from remote
        requestQueue = Volley.newRequestQueue(this.frameContext);

        //Initilaize GsonBuilder
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        //Now fetch messages remotely
        fetchMessages();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_sent_msg, container, false);
        recyclerView = view.findViewById(R.id.rvMessages);
        recyclerView.setLayoutManager(new LinearLayoutManager(frameContext));


        spinner=(ProgressBar)view.findViewById(R.id.progress_bar);
        spinner.setVisibility(View.VISIBLE);

        return view;
    }


    //custom methods
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
            ReceivedMessageAdapter adapter = new ReceivedMessageAdapter(messageList, frameContext);
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



}
