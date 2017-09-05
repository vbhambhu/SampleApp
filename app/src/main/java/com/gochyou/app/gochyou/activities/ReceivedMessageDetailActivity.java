package com.gochyou.app.gochyou.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.CallbackManager;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.gochyou.app.gochyou.R;
import com.gochyou.app.gochyou.adapters.ReceivedMessageAdapter;
import com.gochyou.app.gochyou.models.Message;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReceivedMessageDetailActivity extends AppCompatActivity {


    private Context frameContext;
    private RecyclerView recyclerView;
    private List<Message> messageList = new ArrayList<Message>();
    private RequestQueue requestQueue;
    private Gson gson;
    private ProgressBar spinner;

    CallbackManager callbackManager;
    ShareDialog shareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received_message_detail);


        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);



        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        shareDialog = new ShareDialog(this);
        callbackManager = CallbackManager.Factory.create();

        Button but1=(Button) findViewById(R.id.sharefb);

        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ShareLinkContent content = new ShareLinkContent.Builder()
                        .setContentUrl(Uri.parse("https://gochyou.com"))
                        .build();


                shareDialog.show(content);


            }
        });

        //setupActionBar();
        //Get data from remote
        requestQueue = Volley.newRequestQueue(this);
        //Initilaize GsonBuilder
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        //Now fetch messages remotely
        //getMessageDetail();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return false;

    }


    //custom methods
    private void getMessageDetail() {

        String url = "http://10.0.2.2/guesswho/api/get_message_detail?id=1";
        StringRequest request = new StringRequest(Request.Method.GET, url, onMessageLoaded, onMessageError);
        requestQueue.add(request);
    }

    private final Response.Listener<String> onMessageLoaded = new Response.Listener<String>() {

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

    private final Response.ErrorListener onMessageError = new Response.ErrorListener() {

        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("PostActivity", error.toString());
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }




}
