package me.odinaris.walkcloud.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.odinaris.walkcloud.R;
import me.odinaris.walkcloud.model.Quadrotor;
import me.odinaris.walkcloud.utils.StatusbarUtils;

/**
 * Created by Odinaris on 2016/10/30.
 */

public class QuadrotorDetailActivity extends AppCompatActivity {

    @BindView(R.id.quadrotor_toolbar) Toolbar toolbar;
    @BindView(R.id.quadrotor_name) TextView name;
    @BindView(R.id.quadrotor_price)TextView tPrice;
    @BindView(R.id.quadrotor_heavy)TextView tHeavy;
    @BindView(R.id.quadrotor_fly_time)TextView tFly_time;
    @BindView(R.id.quadrotor_remote_distance)TextView tRemote_distance;
    @BindView(R.id.quadrotor_wheelbase)TextView tWheelbase;
    @BindView(R.id.quadrotor_image_transmission)TextView tImage_transmission;
    @BindView(R.id.quadrotor_indoor_hover)TextView tIndoor_hover;
    @BindView(R.id.quadrotor_progressbar)ProgressBar progressbar;
    @BindView(R.id.quadrotor_info)CardView cardview;

    private Quadrotor quadrotor;

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
        setContentView(R.layout.act_quadrotor_detail);
        ButterKnife.bind(this);

        quadrotor = (Quadrotor)getIntent().getSerializableExtra("current_quadrotor");
        name.setText(quadrotor.getTitle());

        getData();

    }

    private void getData() {
        InfoLoader infoLoader = new InfoLoader();
        infoLoader.execute(quadrotor.getUrl());
    }

    private class InfoLoader extends AsyncTask<String,Void,Void>{

        String price,heavy,fly_time,remote_distance,
                wheelbase,image_transmission,indoor_hover;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            cardview.setVisibility(View.GONE);
            progressbar.setVisibility(View.VISIBLE);

        }

        @Override
        protected Void doInBackground(String... strings) {
            try{
                Document doc = Jsoup.connect(strings[0]).get();
                Elements key_info = doc.select("div.KeyIndexBox>dl>dd");
                price = key_info.get(0).text();
                heavy = key_info.get(1).text();
                fly_time = key_info.get(2).text();
                remote_distance = key_info.get(3).text();
                wheelbase = key_info.get(4).text();
                image_transmission = key_info.get(5).text();
                indoor_hover = key_info.get(6).text();

            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressbar.setVisibility(View.GONE);
            cardview.setVisibility(View.VISIBLE);
            tPrice.setText(price);
            tHeavy.setText(heavy);
            tFly_time.setText(fly_time);
            tImage_transmission.setText(image_transmission);
            tRemote_distance.setText(remote_distance);
            tIndoor_hover.setText(indoor_hover);
            tWheelbase.setText(wheelbase);
        }
    }

}
