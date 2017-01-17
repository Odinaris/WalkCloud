package me.odinaris.walkcloud.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.odinaris.walkcloud.R;
import me.odinaris.walkcloud.activity.ResourceDetailActivity;

/**
 * Created by Odinaris on 2016/10/25.
 */

public class ResourceFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.resource_question)LinearLayout question;
    @BindView(R.id.rescource_terminology)LinearLayout terminology;
    @BindView(R.id.rescource_principle)LinearLayout principle;
    @BindView(R.id.rescource_paddle)LinearLayout paddle;
    @BindView(R.id.rescource_machine)LinearLayout machine;
    @BindView(R.id.rescource_mcu)LinearLayout mcu;
    @BindView(R.id.rescource_remote)LinearLayout remote;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resource,container,false);

        ButterKnife.bind(this,view);
        question.setOnClickListener(this);
        terminology.setOnClickListener(this);
        principle.setOnClickListener(this);
        paddle.setOnClickListener(this);
        machine.setOnClickListener(this);
        mcu.setOnClickListener(this);
        remote.setOnClickListener(this);

        return view;
    }

    public void onClick(View view) {
        Intent intent = new Intent(getActivity(), ResourceDetailActivity.class);
        switch (view.getId()){
            case R.id.resource_question:
                intent.putExtra("url","http://www.crazepony.com/wiki/faq.html");
                startActivity(intent);
                break;
            case R.id.rescource_terminology:
				intent.putExtra("url","http://www.crazepony.com/wiki/copter-terminology.html");
                startActivity(intent);
                break;
            case R.id.rescource_principle:

                intent.putExtra("url","http://www.crazepony.com/wiki/quadcopter-aerodynamic.html");
				startActivity(intent);
                break;
            case R.id.rescource_paddle:

                intent.putExtra("url","http://www.crazepony.com/wiki/propeller.html");
				startActivity(intent);
                break;
            case R.id.rescource_machine:
                intent.putExtra("url","http://www.crazepony.com/wiki/motor-aircraft-model.html");
				startActivity(intent);
                break;
            case R.id.rescource_mcu:
                intent.putExtra("url","http://www.crazepony.com/wiki/main-controller-mcu.html");
				startActivity(intent);
                break;
            case R.id.rescource_remote:
                intent.putExtra("url","http://www.crazepony.com/wiki/remote-controller-2-4.html");
				startActivity(intent);
                break;
            default:
                break;
        }
    }

}
