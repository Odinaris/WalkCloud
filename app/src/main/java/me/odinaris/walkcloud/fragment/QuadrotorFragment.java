package me.odinaris.walkcloud.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.BinderThread;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.odinaris.walkcloud.R;
import me.odinaris.walkcloud.activity.QuadrotorDetailActivity;
import me.odinaris.walkcloud.adapter.QuadrotorAdapter;
import me.odinaris.walkcloud.model.Quadrotor;
import me.odinaris.walkcloud.model.QuadrotorData;

/**
 * Created by Odinaris on 2016/10/25.
 */

public class QuadrotorFragment extends Fragment {


    @BindView(R.id.quadrotor_recryclerview)
    RecyclerView recyclerView;
    @BindView(R.id.quadrotor_loading)
    ProgressBar progressbar;
    ArrayList<Quadrotor> mQuadrotorList = new ArrayList<Quadrotor>();
    QuadrotorData quadrotorData = new QuadrotorData();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product,container,false);
        ButterKnife.bind(this,view);
        initData();
        initView();

        return view;
    }

    private void initData() {
        //添加机型数据列表
        for(int i=0;i<quadrotorData.size();i++){
            Quadrotor quadrotor = new Quadrotor();
            quadrotor.setTitle(quadrotorData.getTitle()[i]);
            quadrotor.setImageUrl(quadrotorData.getImgUrls()[i]);
            quadrotor.setUrl(quadrotorData.getUrls()[i]);
            mQuadrotorList.add(quadrotor);
        }
    }

    private void initView() {
        QuadrotorAdapter quadrotorAdapter = new QuadrotorAdapter(getContext(), mQuadrotorList);
        recyclerView.setAdapter(quadrotorAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        new LinearSnapHelper().attachToRecyclerView(recyclerView);
        quadrotorAdapter.setOnItemClickListener(new QuadrotorAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                final Intent mIntent = new Intent(getActivity(), QuadrotorDetailActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("current_quadrotor",mQuadrotorList.get(position));
                mIntent.putExtras(mBundle);
                startActivity(mIntent);

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }


    class LoadData extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... voids) {


            return null;
        }
    }
}
