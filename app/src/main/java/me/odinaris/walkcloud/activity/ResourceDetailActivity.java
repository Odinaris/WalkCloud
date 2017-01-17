package me.odinaris.walkcloud.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.odinaris.walkcloud.R;
import me.odinaris.walkcloud.model.Quadrotor;
import me.odinaris.walkcloud.utils.StatusbarUtils;

/**
 * Created by Odinaris on 2016/10/30.
 */

public class ResourceDetailActivity extends AppCompatActivity {

    @BindView(R.id.rescource_detail_toolbar)Toolbar toolbar;
    @BindView(R.id.rescource_detail_article)WebView webView;
    @BindView(R.id.rescource_detail_loading)ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //隐藏标题栏
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        StatusbarUtils.from(this)
                //沉浸状态栏
                .setTransparentStatusbar(true)
                //设置toolbar,actionbar等view
                .setActionbarView(toolbar)
                .process();
        setContentView(R.layout.resource_detail);
        ButterKnife.bind(this);
        String url = getIntent().getStringExtra("url");
        webView.loadUrl(url);


    }

    private class ConnectInternet extends AsyncTask<String,Void,Void>{



        @Override
        protected Void doInBackground(String... strings) {

            return null;
        }
    }
}
