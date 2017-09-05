package com.gochyou.app.gochyou.adapters;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.gochyou.app.gochyou.R;
import com.gochyou.app.gochyou.activities.ReceivedMessageDetailActivity;
import com.gochyou.app.gochyou.models.Message;

import java.util.List;

public class ReceivedMessageAdapter extends RecyclerView.Adapter<ReceivedMessageAdapter.MessageViewHolder> {

    private List<Message> messageList;
    private Context frameContext;
    private ImageLoader mImageLoader;

    OnLoadMoreListener loadMoreListener;
    boolean isLoading = false;
    boolean isMoreDataAvailable = true;



    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }



    public ReceivedMessageAdapter(List<Message> messages, Context frameContext) {
        this.frameContext = frameContext;
        this.messageList = messages;
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView messageHolder;
        public TextView timestampHolder;
        public NetworkImageView thumbHolder;
        public MessageViewHolder(View view) {
            super(view);
            messageHolder = (TextView) view.findViewById(R.id.rvMessageText);
            timestampHolder =  (TextView) view.findViewById(R.id.rvMessageCreatedAt);
           // thumbHolder =  (NetworkImageView) view.findViewById(R.id.thumbnail);
            view.setOnClickListener(this);

        }


        @Override
        public void onClick(View view) {

            int itemPosition = getLayoutPosition();

            System.out.println("onClick" + itemPosition);

            //dialog = new Dialog(frameContext);

            //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

           // dialog.setContentView(R.layout.received_msg_view);

           // dialog.show();

            Intent intent = new Intent(frameContext, ReceivedMessageDetailActivity.class);
            frameContext.startActivity(intent);

        }


    }




    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {


        /*
        0if(position>=getItemCount()-1 && isMoreDataAvailable && !isLoading && loadMoreListener!=null){
            isLoading = true;
            loadMoreListener.onLoadMore();
        }

        if(getItemViewType(position)==TYPE_MOVIE){
            ((MovieHolder)holder).bindData(movies.get(position));
        }

        */

        if(position>=getItemCount()-1 && isMoreDataAvailable && !isLoading  && loadMoreListener!=null){
            System.out.println("====load more here===="+position);
            loadMoreListener.onLoadMore();
        }


        Message message = messageList.get(position);


        holder.messageHolder.setText(message.getMessage() + "---" +  message.getId());
        holder.timestampHolder.setText(message.getCreated_at());
        //holder.thumbHolder.setImageUrl(message.getImage(), mImageLoader );

    }


    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_message_row, parent, false);

       // itemView.setOnClickListener(mOnClickListener);


        return new MessageViewHolder(itemView);
    }



    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public void notifyDataChanged(){
        notifyDataSetChanged();
        isLoading = false;
    }

    public interface OnLoadMoreListener{
        void onLoadMore();
    }

    public void setLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }


}
