package me.odinaris.walkcloud.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import me.odinaris.walkcloud.R;
import me.odinaris.walkcloud.bean.ChatMessage;

/**
 * Created by Odinaris on 2016/10/29.
 */

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private Context mContext;
    private ArrayList<ChatMessage> mDatas;
    private LayoutInflater mLayoutInflater;
    private QuadrotorAdapter.OnItemClickListener onItemClickListener;

    public MessageAdapter(Context context, ArrayList<ChatMessage> newsList) {
        this.mContext = context;
        this.mDatas = newsList;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }


    public void setOnItemClickListener(QuadrotorAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(viewType==0){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_from, parent, false);
            MessageAdapter.MessageFromViewHolder messageFromViewHolder = new MessageAdapter.MessageFromViewHolder(view);
            return messageFromViewHolder;
        }else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_to, parent, false);
            MessageAdapter.MessageToViewHolder messageToViewHolder = new MessageAdapter.MessageToViewHolder(view);
            return messageToViewHolder;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(  new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onItemClickListener!=null){
                    onItemClickListener.onItemClick(holder.itemView,position);
                }
            }
        });
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(getItemViewType(position)==0){
           // ((MessageFromViewHolder)holder).mMsg.setText(mDatas.get(position).getMsg());
            ((MessageFromViewHolder)holder).mDate.setText(df.format(mDatas.get(position).getDate()));
        }else {
           // ((MessageToViewHolder)holder).mMsg.setText(mDatas.get(position).getMsg());
            ((MessageToViewHolder)holder).mDate.setText(df.format(mDatas.get(position).getDate()));
        }




    }



    @Override
    public int getItemCount() {
        return mDatas!=null?mDatas.size():0;
    }

    public Object getItem(int position){
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }


    @Override
    public int getItemViewType(int position){
        ChatMessage chatMessage = mDatas.get(position);
        if(chatMessage.getType()== ChatMessage.Type.INCOMING){
            return 0;
        }
        return 1;
    }

    public int getViewTypeCount(){
        return 2;
    }


    private static class MessageToViewHolder extends RecyclerView.ViewHolder{

        private TextView mDate,mMsg;

        public MessageToViewHolder (View view){
            super(view);
            mDate = (TextView)view.findViewById(R.id.id_to_msg_date);
            mMsg = (TextView)view.findViewById(R.id.id_to_msg_info);
        }
    }

    private static class MessageFromViewHolder extends RecyclerView.ViewHolder{

        private TextView mDate,mMsg;

        public MessageFromViewHolder (View view){
            super(view);
            mDate = (TextView)view.findViewById(R.id.id_from_msg_date);
            mMsg = (TextView)view.findViewById(R.id.id_from_msg_info);
        }
    }
}
