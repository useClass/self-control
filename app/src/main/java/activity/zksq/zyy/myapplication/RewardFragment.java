package activity.zksq.zyy.myapplication;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Reward;
import service.MyScoreService;
import service.RewardService;


public class RewardFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private ListView lv_reward;  //声明一个ListView对象
    private RewardService rewardService;
    private List<Reward> rewardList = new ArrayList<Reward>();  //声明一个list，动态存储要显示的信息
    private Button btn_addReward;

    public RewardFragment() {
    }

    public static RewardFragment newInstance(String param1, String param2) {
        RewardFragment fragment = new RewardFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reward, container, false);
        rewardService = new RewardService(getActivity());
        lv_reward = (ListView) view.findViewById(R.id.lv_reward);


        rewardService = new RewardService(getActivity());
        rewardList = rewardService. query();
        listViewShow(lv_reward,rewardList);

        btn_addReward = (Button)view.findViewById(R.id.btn_addReward);
        btn_addReward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater dialogInflater = getActivity().getLayoutInflater();
                View dialogView = dialogInflater.inflate(R.layout.dialog_reward_con, null);

                final View homeView = getActivity().getLayoutInflater().inflate(R.layout.fragment_home,null);

                final EditText et_rewardName = (EditText)dialogView.findViewById(R.id.et_rewardName);
                final EditText et_rewardScore = (EditText)dialogView.findViewById(R.id.et_rewardScore);

                new AlertDialog.Builder(getActivity()).setTitle("添加新任务").setView(dialogView)
                        .setPositiveButton("确定添加", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String rewardName = et_rewardName.getText().toString().trim();
                                String rewardScore = et_rewardScore.getText().toString().trim();
                                Reward reward = new Reward();
                                reward.setReward_name(rewardName);
                                reward.setReward_score(rewardScore);
                                rewardList.add(reward);
                                rewardService.add(reward);
                                listViewShow(lv_reward,rewardList);


                                //发送广播,便于其他fragment获取更新界面，reward更新
                                Intent intent = new Intent();
                                intent.setAction("ACTION_MYSCORE_CHANGE");
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("newReward",reward);
                                intent.putExtras(bundle);
                                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);

                                Toast.makeText(getActivity(),"添加成功",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("取消操作", null).show();
            }
        });

        //长按弹出操作列表


        lv_reward.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getActivity(),"这是长按弹出来的Toast",Toast.LENGTH_SHORT).show();
                final int listPosition = i;
                final String items_operate[] = {"删除","完成"};
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("操作");
                builder.setIcon(android.R.drawable.ic_dialog_info);
                builder.setItems(items_operate, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                       // Toast.makeText(getActivity(),items_operate[i], Toast.LENGTH_SHORT).show();
                        Reward slt_reward = rewardList.get(listPosition);
                        String slt_reward_id = slt_reward.getReward_id();

                        switch (i)
                        {
                            case 0:

                                rewardService.delete(slt_reward_id);
                                rewardList = rewardService. query();
                                listViewShow(lv_reward,rewardList);
                                break;
                            case 1:
                               // Toast.makeText(getActivity(),slt_reward_id,Toast.LENGTH_LONG).show();
                                break;

                        }
                    }
                });
                builder.create().show();
                return false;
            }
        });

        return view;

    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    //用于显示更新ListView显示
    private void listViewShow(ListView listView,List<Reward> list)
    {

        ArrayList<Map<String,String>> rewardData= new ArrayList<Map<String,String>>();;
        for (int i=0;i<list.size();i++)
        {
            Map<String,String> item = new HashMap<String,String>();
            System.out.println("--->>结果"+list.get(i).getReward_name()+">>"+list.get(i).getReward_score());
            item.put("reward_name","奖励名称："+list.get(i).getReward_name());
            item.put("reward_score","奖励分数："+list.get(i).getReward_score());
            rewardData.add(item);
        }
        SimpleAdapter rewardAdapter = new SimpleAdapter(getActivity(),rewardData,android.R.layout.simple_list_item_2,
                new String[]{"reward_name","reward_score"},new int[]{android.R.id.text1,android.R.id.text2});
        listView.setAdapter(rewardAdapter);

    }
}
