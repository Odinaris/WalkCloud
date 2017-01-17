package me.odinaris.walkcloud.fragment;

import android.content.Intent;

import android.os.Bundle;

import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.odinaris.walkcloud.R;
import me.odinaris.walkcloud.activity.RobotActivity;

/**
 * Created by Odinaris on 2016/10/25.
 */

public class RobotFragment extends Fragment {

    @BindView(R.id.robot) ImageView robot;
    @BindView(R.id.robot_linearlayout)LinearLayout linearLayout;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_robot, container, false);
        ButterKnife.bind(this, view);

        robot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(getActivity(), RobotActivity.class);
                startActivity(mIntent);
            }
        });
        return view;

    }

}
