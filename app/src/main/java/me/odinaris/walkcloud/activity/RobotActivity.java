package me.odinaris.walkcloud.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.odinaris.walkcloud.R;
import me.odinaris.walkcloud.adapter.ChatMessageAdapter;
import me.odinaris.walkcloud.bean.ChatMessage;
import me.odinaris.walkcloud.utils.HttpUtils;

/**
 * Created by Odinaris on 2016/10/29.
 */

public class RobotActivity extends AppCompatActivity {


    @BindView(R.id.id_input_msg)
    EditText mInputMsg;
    @BindView(R.id.id_send_msg)
    Button mSendMsg;
    @BindView(R.id.id_listview_msgs)
    ListView mMsgs;

    private ChatMessageAdapter mAdapter;
    private ArrayList<ChatMessage> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //隐藏标题栏
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.act_robot);
        ButterKnife.bind(this);
        initData();
        initListener();


    }

    private void initData() {
        mDatas = new ArrayList<ChatMessage>();
        mDatas.add(new ChatMessage("你好，欢迎使用行云", ChatMessage.Type.INCOMING, new Date()));
        mAdapter = new ChatMessageAdapter(this, mDatas);
        mMsgs.setAdapter(mAdapter);
    }

    private void initListener() {
        mSendMsg.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final String toMsg = mInputMsg.getText().toString();
                if (TextUtils.isEmpty(toMsg))
                {
                    Toast.makeText(getApplication(), "发送消息不能为空！",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                mInputMsg.setText("");
                ChatMessage toMessage = new ChatMessage();
                toMessage.setDate(new Date());
                toMessage.setMsg(toMsg);
                toMessage.setType(ChatMessage.Type.OUTCOMING);
                mDatas.add(toMessage);
                mAdapter.notifyDataSetChanged();

                ChatRobot chatRobot = new ChatRobot();
                chatRobot.execute(toMsg);


            }
        });

    }

    private class ChatRobot extends AsyncTask<String,Void,Void>{
        ChatMessage fromMessage= new ChatMessage();
        String answer;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            fromMessage.setMsg("小云正在思考该如何回复您...");
        }

        @Override
        protected Void doInBackground(String... strings) {
            answer = HttpUtils.doGet(strings[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            fromMessage.setMsg(answer);
            fromMessage.setDate(new Date());
            fromMessage.setType(ChatMessage.Type.INCOMING);
            mDatas.add(fromMessage);
            mAdapter.notifyDataSetChanged();

        }
    }
}
