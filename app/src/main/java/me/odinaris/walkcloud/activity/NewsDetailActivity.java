package me.odinaris.walkcloud.activity;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kogitune.activity_transition.ActivityTransition;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.odinaris.walkcloud.R;
import me.odinaris.walkcloud.model.News;
import me.odinaris.walkcloud.utils.StatusbarUtils;

import static android.R.attr.height;

/**
 * Created by Odinaris on 2016/10/27.
 */

public class NewsDetailActivity extends AppCompatActivity {

    private News news;


    @BindView(R.id.stoolbar)
    Toolbar toolbar;
    @BindView(R.id.news_detail_image)
    ImageView imageView;
    @BindView(R.id.news_detail_article)
    WebView webView;
    @BindView(R.id.news_detail_loading)
    ProgressBar progressBar;
    @BindView(R.id.news_detail_title)
    TextView title;
    @BindView(R.id.news_detail_back)
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //隐藏标题栏
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setStatusBarColor(Color.BLACK);

        StatusbarUtils.from(this)
                //沉浸状态栏
                .setTransparentStatusbar(true)
                //设置toolbar,actionbar等view
                .setActionbarView(toolbar)
                .process();


        setContentView(R.layout.act_newsdetail);
        ButterKnife.bind(this);
        news = (News)getIntent().getSerializableExtra("current_news");

        setSupportActionBar(toolbar);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        title.setText(news.getTitle());



        Glide.with(NewsDetailActivity.this).load(news.getImageUrl()).centerCrop().into(imageView);
        GetDetailNews getDetailNews=new GetDetailNews();
        getDetailNews.execute();
        webView.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                //设置点击网页里面的链接还是在当前的webview里跳转
                view.loadUrl(url);
                return true;
            }
        });
    }


    class GetDetailNews extends AsyncTask<Void,Void,Void>{

        String content;

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Document doc = Jsoup.connect(news.getUrl()).
                        userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537" +
                                ".31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31").get();
                Elements article= doc.select("div.finCnt");
                content="<html >"
                        +"<style type=\"text/css\">"
                        +"h1,h2,h3,strong{ font-size:20px; color:#000; line-height: 38px; font-style:bold;}"
                        +"p{ line-height:1.7; font-size:18px; color:#6E6B6B;}"
                        +"img{width:100%;height:auto}"
                        +"body{padding:10px;background：#000}"
                        +"</style>"
                        +"<body>"
                        +article.html()
                        +"</body>"
                        +"</html>";
            }catch (IOException e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            webView.loadDataWithBaseURL(null, content, "text/html", "utf-8", null);
            progressBar.setVisibility(View.GONE);
            webView.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }
    }
}
