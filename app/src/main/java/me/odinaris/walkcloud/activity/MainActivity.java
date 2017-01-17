package me.odinaris.walkcloud.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import java.util.ArrayList;

import me.odinaris.walkcloud.fragment.RobotFragment;
import me.odinaris.walkcloud.fragment.NewsFragment;
import me.odinaris.walkcloud.fragment.QuadrotorFragment;
import me.odinaris.walkcloud.R;
import me.odinaris.walkcloud.fragment.ResourceFragment;
import me.odinaris.walkcloud.fragment.UserFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationBar bottomNavigationBar;
    private ArrayList<Fragment> fragments;
    private ResourceFragment mResourceFragment = new ResourceFragment();
    private NewsFragment mNewsFragment = new NewsFragment();
    private QuadrotorFragment mProductFragment = new QuadrotorFragment();
    private UserFragment mUserFragment = new UserFragment();
    private RobotFragment mRobotFragment = new RobotFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏标题栏
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
                WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.act_main);
        initView();
        setDefaultFragment();

    }

    private void setDefaultFragment() {
        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction mFragmentTranscation = mFragmentManager.beginTransaction();
        mFragmentTranscation.replace(R.id.content, mRobotFragment);
        mFragmentTranscation.commit();
    }

    private void initView() {
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.resource, "博古"))
                .addItem(new BottomNavigationItem(R.drawable.news, "通今"))
                .addItem(new BottomNavigationItem(R.drawable.cloud,"云"))
                .setFirstSelectedPosition(2)
                .addItem(new BottomNavigationItem(R.drawable.product, "格物"))
                .addItem(new BottomNavigationItem(R.drawable.user, "慎独"))
                .initialise();
        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                // 当第position个tab被选中时，调用此方法
                // 这里可以完成对Fragment的切换
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                switch (position){
                    case 1:
                        if (mNewsFragment==null){
                            mNewsFragment = new NewsFragment();
                        }
                        transaction.replace(R.id.content,mNewsFragment);
                        break;
                    case 3:
                        if (mProductFragment==null){
                            mProductFragment = new QuadrotorFragment();
                        }
                        transaction.replace(R.id.content,mProductFragment);
                        break;
                    case 2:
                        if (mRobotFragment ==null){
                            mRobotFragment = new RobotFragment();
                        }
                        transaction.replace(R.id.content, mRobotFragment);
                        break;
                    case 0:
                        if (mResourceFragment==null){
                            mResourceFragment = new ResourceFragment();
                        }
                        transaction.replace(R.id.content,mResourceFragment);
                        break;
                    case 4:
                        if(mUserFragment==null){
                            mUserFragment = new UserFragment();
                        }
                        transaction.replace(R.id.content,mUserFragment);
                        break;
                }
                transaction.commit();//提交事务
            }

            @Override
            public void onTabUnselected(int position) {
                // 对未被选择的tab进行处理，其中pisition仍然是被选中的tab
            }

            @Override
            public void onTabReselected(int position) {
                // 当被选中的tab 再一次被点击时调用此方法
            }
        });
    }


}
