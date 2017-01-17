package me.odinaris.walkcloud.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import me.odinaris.walkcloud.R;
import me.odinaris.walkcloud.model.Quadrotor;
import me.odinaris.walkcloud.ui.GlideCircleTransform;

/**
 * Created by Odinaris on 2016/10/25.
 */

public class QuadrotorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<Quadrotor> mQuadrotorList;
    private LayoutInflater mLayoutInflater;
    private OnItemClickListener onItemClickListener;

    public QuadrotorAdapter(Context context, ArrayList<Quadrotor> newsList) {
        this.mContext = context;
        this.mQuadrotorList = newsList;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quadrotor_item, parent, false);

        QuadrotorAdapter.QuadrotorViewHolder quadrotorViewHolder = new QuadrotorAdapter.QuadrotorViewHolder(view);
        return quadrotorViewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onItemClickListener!=null){
                    onItemClickListener.onItemClick(holder.itemView,position);
                }
            }
        });
        ((QuadrotorViewHolder)holder).textView.setText(mQuadrotorList.get(position).getTitle());

        Glide.with(mContext)
                .load(mQuadrotorList.get(position).getImageUrl())
                .crossFade()
                .transform(new GlideCircleTransform(mContext))
                .into(((QuadrotorViewHolder)holder).imageView);
        holder.itemView.setTag(position);

    }



    @Override
    public int getItemCount() {
        return mQuadrotorList!=null?mQuadrotorList.size():0;
    }


    private static class QuadrotorViewHolder extends RecyclerView.ViewHolder{

        private TextView textView;
        private ImageView imageView;

        public QuadrotorViewHolder(View view){
            super(view);
            textView = (TextView)view.findViewById(R.id.quadrotor_title);
            imageView = (ImageView)view.findViewById(R.id.quadrotor_img);
        }
    }
}