package com.gochyou.app.gochyou.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gochyou.app.gochyou.R;
import com.gochyou.app.gochyou.models.Message;

import java.util.List;

public class ReceivedMessageAdapter extends RecyclerView.Adapter<ReceivedMessageAdapter.MessageViewHolder> {

    private List<Message> messageList;

    public ReceivedMessageAdapter(List<Message> messages) {
        this.messageList = messages;
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {
        public TextView messageHolder;
        public TextView timestampHolder;
        public MessageViewHolder(View view) {
            super(view);
            messageHolder = (TextView) view.findViewById(R.id.rvMessageText);
            timestampHolder =  (TextView) view.findViewById(R.id.rvMessageCreatedAt);

        }
    }


    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        Message message = messageList.get(position);
        holder.messageHolder.setText(message.getMessage());
        holder.timestampHolder.setText(message.getCreated_at());
    }


    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_message_row, parent, false);
        return new MessageViewHolder(itemView);
    }



    @Override
    public int getItemCount() {
        return messageList.size();
    }



}
