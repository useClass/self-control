package activity.zksq.zyy.myapplication;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import model.Reward;
import service.MyScoreService;
import service.RewardService;
import service.TaskService;


public class HomeFragment extends Fragment {
    private Button btn_updateNowScore;
    private TextView tv_totalScore;
    private TextView tv_myScore ;
    private TextView tv_rewardName;
    private String myScore;
    private MyScoreService myScoreService;
    private RewardService rewardService;
    private String nextRewardScore;

    public HomeFragment() {

    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.fragment_home, container, false);
        tv_myScore = (TextView) view.findViewById(R.id.tv_myScore);
        tv_totalScore = (TextView) view.findViewById(R.id.tv_totalScore);
        tv_rewardName = (TextView) view.findViewById(R.id.tv_rewardName);

        myScoreService = new MyScoreService(getActivity());
        rewardService = new RewardService(getActivity());


//查询数据库显示现在总分数
        myScore = myScoreService.query();
        if(myScore != null)
            tv_myScore.setText(myScore);
        else
        {
            myScore = "0";
            tv_myScore.setText(myScore);
        }

        showRewardScore();

        btn_updateNowScore = (Button)view.findViewById(R.id.btn_updateNowScore);
        btn_updateNowScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(getActivity(),"试一试",Toast.LENGTH_SHORT).show();
                final View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_scoreset_con,null);
               final EditText et_score = (EditText)dialogView.findViewById(R.id.et_setScore);
                AlertDialog.Builder socreBuilder = new AlertDialog.Builder(getActivity());
                socreBuilder.setIcon(android.R.drawable.ic_dialog_info);
                socreBuilder.setTitle("设置分数");
                socreBuilder.setView(dialogView);
                socreBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       String setScore = et_score.getText().toString();
                        tv_myScore.setText(setScore);
                        myScoreService.update(setScore);

                    }
                });
                socreBuilder.setNegativeButton("取消",null);
                socreBuilder.show();

            }
        });


        //接收广播,奖励列表中添加新奖励
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("ACTION_MYSCORE_CHANGE");

        BroadcastReceiver addRewardReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                showRewardScore();
//                Reward newReward = (Reward)intent.getSerializableExtra("newReward");
//                String newScore = newReward.getReward_score();
//                String newName = newReward.getReward_name();
//
//                if(nextRewardScore == null)
//                {
//                    tv_totalScore.setText(newScore);
//                    tv_rewardName.setText(newName);
//                }
//               else if(Integer.parseInt(newScore) >Integer.parseInt(myScore) && Integer.parseInt(newScore) < Integer.parseInt(nextRewardScore))
//                {
//                    tv_totalScore.setText(newScore);
//                    tv_rewardName.setText(newName);
//                }

            }
        };

        //接受广播，奖励列表中删除奖励
        IntentFilter delRewardFilter = new IntentFilter();
        delRewardFilter.addAction("ACTION_REWARD_DELETE");
        BroadcastReceiver delRewardReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                showRewardScore();
            }
        };


        broadcastManager.registerReceiver(addRewardReceiver,intentFilter);
        broadcastManager.registerReceiver(delRewardReceiver,delRewardFilter);
        return view;
    }


    private void showRewardScore()
    {



        List<Reward> allReward = rewardService.query();
        if(allReward == null || allReward.size() == 0)
        {
            tv_totalScore.setText("无奖励");
        }
        else
        {
            Collections.sort(allReward,new Comparator<Reward>() {
                @Override
                public int compare(Reward t1, Reward t2) {
                    if (Integer.parseInt(t1.getReward_score())>Integer.parseInt(t2.getReward_score()))
                    {
                        return 1;
                    }
                    else if(Integer.parseInt(t1.getReward_score())<Integer.parseInt(t2.getReward_score()))
                    {
                        return -1;
                    }
                    else
                    {
                        return 0;
                    }
                }
            });
            for (Reward r:allReward)
            {
                if(Integer.parseInt(myScore)<Integer.parseInt(r.getReward_score()))
                {
                    nextRewardScore = r.getReward_score();
                    String nextRewardName = r.getReward_name();
                    tv_totalScore.setText(nextRewardScore);
                    tv_rewardName.setText(nextRewardName);
                    break;
                }

            }
        }
    }

}
