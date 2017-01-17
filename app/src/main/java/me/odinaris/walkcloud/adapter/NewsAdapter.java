package me.odinaris.walkcloud.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kogitune.activity_transition.ActivityTransitionLauncher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import me.odinaris.walkcloud.activity.MainActivity;
import me.odinaris.walkcloud.activity.NewsDetailActivity;
import me.odinaris.walkcloud.imageLoader.ImageFileCache;
import me.odinaris.walkcloud.imageLoader.ImageGetFormHttp;
import me.odinaris.walkcloud.imageLoader.ImageMemoryCache;
import me.odinaris.walkcloud.model.News;
import me.odinaris.walkcloud.R;
import me.odinaris.walkcloud.ui.GlideCircleTransform;

import static android.R.attr.animation;

/**
 * Created by Odinaris on 2016/10/25.
 */

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<News> mNewsList;
    private LayoutInflater mLayoutInflater;
    private OnItemClickListener onItemClickListener;
    private Animation animation;
    private Map<Integer, Boolean> isFrist;

    public NewsAdapter(Context context, ArrayList<News> newsList) {
        this.mContext = context;
        this.mNewsList = newsList;
        this.mLayoutInflater = LayoutInflater.from(mContext);
        animation = AnimationUtils.loadAnimation(context, R.anim.news_list_layout);
        isFrist = new HashMap<Integer, Boolean>();
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);

        NewsAdapter.NewsViewHolder newsViewHolder = new NewsAdapter.NewsViewHolder(view);
        return newsViewHolder;
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
        ((NewsViewHolder)holder).textView.setText(mNewsList.get(position).getTitle());

        Glide.with(mContext)
                .load(mNewsList.get(position).getImageUrl())
                .crossFade()
                .into(((NewsViewHolder)holder).imageView);

       /* // 如果是第一次加载该view，则使用动画
        if (isFrist.get(position) == null || isFrist.get(position)) {
            holder.itemView.startAnimation(animation);
            isFrist.put(position, false);
        }*/
        holder.itemView.setTag(position);


    }



    @Override
    public int getItemCount() {
        return mNewsList!=null?mNewsList.size():0;
    }


    private static class NewsViewHolder extends RecyclerView.ViewHolder{

        private TextView textView;
        private ImageView imageView;

        public NewsViewHolder(View view){
            super(view);
            textView = (TextView)view.findViewById(R.id.news_title);
            imageView = (ImageView)view.findViewById(R.id.news_image);
        }
    }

    /*** 获得一张图片,从三个地方获取,首先是内存缓存,然后是文件缓存,最后从网络获取 ***/
    private  Bitmap getBitmap(String url) {

        ImageMemoryCache imageMemoryCache = new ImageMemoryCache(mContext);
        ImageFileCache imageFileCache = new ImageFileCache();
        // 从内存缓存中获取图片
        Bitmap result;
        result = imageMemoryCache.getBitmapFromCache(url);
        if (result == null) {
            // 文件缓存中获取
            result = imageFileCache.getImage(url);
            if (result == null) {
                // 从网络获取
                result = ImageGetFormHttp.downloadBitmap(url);
                if (result != null) {
                    imageFileCache.saveBitmap(result, url);
                    imageMemoryCache.addBitmapToCache(url, result);
                }
            } else {
                // 添加到内存缓存
                imageMemoryCache.addBitmapToCache(url, result);
            }
        }
        return result;
    }

    private class ImageLoaderTask extends AsyncTask<String,Void,Void>{

        public Bitmap bitmap;

        public Bitmap getImage(){
            return bitmap;
        }

        @Override
        protected Void doInBackground(String... strings) {
            bitmap = getBitmap(strings[0]);
            return null;
        }
    }
}