package com.gochyou.app.gochyou.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import com.gochyou.app.gochyou.R;
import com.gochyou.app.gochyou.adapters.ReceivedMessageAdapter;
import com.gochyou.app.gochyou.helpers.SessionManager;
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
    private int userId;
    SessionManager session;

    boolean loadmore = true;
    int offset = 0;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.frameContext=context;
        //Get userid
        session = new SessionManager(context);
        userId = session.getUserId();
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
        fetchMessages(0);




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_message, container, false);
        recyclerView = view.findViewById(R.id.rvMessages);
        recyclerView.setLayoutManager(new LinearLayoutManager(frameContext));

        spinner=(ProgressBar)view.findViewById(R.id.progress_bar);
        spinner.setVisibility(View.VISIBLE);

        //create an adapter


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


                  int visibleItemCount = recyclerView.getChildCount();




                //
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
               // int firstVisibleItem = manager.findFirstVisibleItemPosition();


                int lastVisiblePosition = layoutManager.findLastVisibleItemPosition();


               // System.out.println(" System.out.println(lastVisiblePosition);" +lastVisiblePosition);
               // System.out.println("messageList.size()" +messageList.size() );
                if ((messageList.size() -1) == lastVisiblePosition) {



                    if (loadmore) {
                        loadmore = false;
                        System.out.println("==================================");

                        fetchMessages(offset);

                    }
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });





        return view;
    }


    //custom methods
    private void fetchMessages(int pos) {




       // System.out.println("Now in fetchMessages");
        String url = "http://10.0.2.2/gochyou/api/message/conversations?uid="+ userId+"&offset="+offset;
        System.out.println(url);
        StringRequest request = new StringRequest(Request.Method.GET, url, onMessagesLoaded, onMessagesError);
        requestQueue.add(request);
    }

    private final Response.Listener<String> onMessagesLoaded = new Response.Listener<String>() {

        @Override
        public void onResponse(String response) {

           // System.out.println(response);
            //deserilize response and add to messages list.
            List<Message> remoteMsgs = Arrays.asList(gson.fromJson(response, Message[].class));
            messageList.addAll(remoteMsgs);



           // System.out.println(messageList);
            //create an adapter
            ReceivedMessageAdapter adapter = new ReceivedMessageAdapter(messageList, frameContext);


//            adapter.setLoadMoreListener(new ReceivedMessageAdapter.OnLoadMoreListener() {
//                @Override
//                public void onLoadMore() {
//
//                    recyclerView.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            int offset = messageList.size();
//
//                            System.out.println("load more" + offset);
//                            fetchMessages(offset);// a method which requests remote data
//                        }
//                    });
//                    //Calling loadMore function in Runnable to fix the
//                    // java.lang.IllegalStateException: Cannot call this method while RecyclerView is computing a layout or scrolling error
//                }
//            });

            //finally set adapter
            recyclerView.setAdapter(adapter);

            adapter.notifyDataChanged();

            offset  = messageList.size();
            System.out.println("**************setting ooffset value" + offset);
            loadmore = true;

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


    private void loadMore(int offset){

        System.out.println("load more message");
        String url = "http://10.0.2.2/gochyou/api/message/conversations?uid="+ userId;


        System.out.println(url);
        StringRequest request = new StringRequest(Request.Method.GET, url, onMessagesLoaded, onMessagesError);
        requestQueue.add(request);




        /*add loading progress view
        movies.add(new MovieModel("load"));
        adapter.notifyItemInserted(movies.size()-1);

        Call<List<MovieModel>> call = api.getMovies(index);
        call.enqueue(new Callback<List<MovieModel>>() {
            @Override
            public void onResponse(Call<List<MovieModel>> call, Response<List<MovieModel>> response) {
                if(response.isSuccessful()){

                    //remove loading view
                    movies.remove(movies.size()-1);

                    List<MovieModel> result = response.body();
                    if(result.size()>0){
                        //add loaded data
                        movies.addAll(result);
                    }else{//result size 0 means there is no more data available at server
                        adapter.setMoreDataAvailable(false);
                        //telling adapter to stop calling load more as no more server data available
                        Toast.makeText(context,"No More Data Available",Toast.LENGTH_LONG).show();
                    }
                    adapter.notifyDataChanged();
                    //should call the custom method adapter.notifyDataChanged here to get the correct loading status
                }else{
                    Log.e(TAG," Load More Response Error "+String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<MovieModel>> call, Throwable t) {
                Log.e(TAG," Load More Response Error "+t.getMessage());
            }
        });

        */
    }


}
