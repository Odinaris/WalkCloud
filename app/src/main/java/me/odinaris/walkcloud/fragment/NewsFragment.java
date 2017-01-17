package me.odinaris.walkcloud.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.ChangeBounds;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.kogitune.activity_transition.ActivityTransitionLauncher;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.odinaris.walkcloud.R;
import me.odinaris.walkcloud.activity.NewsDetailActivity;
import me.odinaris.walkcloud.adapter.NewsAdapter;
import me.odinaris.walkcloud.model.News;
import me.odinaris.walkcloud.ui.GlideCircleTransform;


/**
 * Created by Odinaris on 2016/10/25.
 */

public class NewsFragment  extends Fragment {

    private Handler handler = new Handler();

    ArrayList<News> mNewsList;
    NewsAdapter mNewsAdapter;
    @BindView(R.id.rvNewsList)
    RecyclerView mRecyclerView;
    @BindView(R.id.news_banner)
    ImageView banner;
/*    @BindView(R.id.tvest)
    TextView test;*/
    @BindView(R.id.progressBarNews)
    ProgressBar mProgressBar;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    boolean isLoading;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news,container,false);
        ButterKnife.bind(this,view);
        Bitmap bmp = readBitMap(R.drawable.news_banner);
        banner.setImageBitmap(bmp);
        mNewsList = new ArrayList<News>();
        mNewsAdapter = new NewsAdapter(getContext(),mNewsList);
        mRecyclerView.setAdapter(mNewsAdapter);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        initView();

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mNewsList.clear();
                        getData();
                    }
                }, 2000);
            }
        });


        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });


        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                if (lastVisibleItemPosition + 1 == mNewsAdapter.getItemCount()) {

                    boolean isRefreshing = mSwipeRefreshLayout.isRefreshing();
                    if (isRefreshing) {
                        mNewsAdapter.notifyItemRemoved(mNewsAdapter.getItemCount());
                        return;
                    }
                    if (!isLoading) {
                        isLoading = true;
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                getData();
                                isLoading = false;
                            }
                        }, 1000);
                    }
                }
            }
        });

        //添加点击事件
        mNewsAdapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                final Intent mIntent = new Intent(getActivity(),NewsDetailActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("current_news",mNewsList.get(position));
                mIntent.putExtras(mBundle);
                /*ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),view.findViewById(R.id.news_image),"transitionImg");
                ActivityCompat.startActivity(getActivity(),mIntent,options.toBundle());*/
               // final Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
                //ActivityTransitionLauncher.with(getActivity()).from(view.findViewById(R.id.news_image)).launch(mIntent);
                startActivity(mIntent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });


        return view;

    }

    private void getMoreData() {
    }

    private void initView() {
        getData();
    }


    private void getData(){
        parseArticle pp = new parseArticle();
        pp.execute();
    }


    //爬取http://zhidx.com/上关于无人机的资讯
    private class parseArticle extends AsyncTask<Void,Void,Void>{

        Document doc;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                int page_num=mNewsList.size()/20+1;
                if(mNewsList.size()<20){
                    doc =  Jsoup.connect("http://zhidx.com/?s=%E6%97%A0%E4%BA%BA%E6%9C%BA&urlTit=%E6%99%BA%E4%B8%9C%E8%A5%BF").
                            userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31").get();
                }else {
                    doc =  Jsoup.connect("http://zhidx.com/page/"+page_num+"?s=%E6%97%A0%E4%BA%BA%E6%9C%BA&urlTit=%E6%99%BA%E4%B8%9C%E8%A5%BF").
                            userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31").get();
                }

                Elements title = doc.select("ul>li>p.name>a");
                Elements imageurls = doc.select("ul>li>a.img>img");

                for(int i=0;i<title.size();i++){
                    News news = new News();
                    String url = title.get(i).attr("href");
                    String imageurl = imageurls.get(i).attr("src");
                    news.setTitle(title.get(i).text());
                    news.setUrl(url);
                    news.setImageUrl(imageurl);
                    mNewsList.add(news);
                }
            }catch (IOException i){}
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mNewsAdapter.notifyDataSetChanged();
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    private Bitmap readBitMap(int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        InputStream is = getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }
}
