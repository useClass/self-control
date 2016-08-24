package activity.zksq.zyy.myapplication;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener
{
    // 定位四个Fragment
    private Fragment homeFragment = new HomeFragment();
    private Fragment taskFragment = new TaskFragment();
    private Fragment rewardFragment = new RewardFragment();
    private Fragment meFragment = new MeFragment();


    // tab中的四个帧布局
    private LinearLayout homeFrameLayout, taskFrameLayout, rewardFrameLayout,meFrameLayout;

    // tab中的四个帧布局中的四个图片组件
    private ImageView homeImageView, taskImageView, rewardImageView, meImageView;

    // tab中的四个帧布局中的四个图片对应文字
    private TextView homeTextView, taskTextView,rewardTextView, meTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化组件
        initView();

        // 初始化按钮单击事件
        initClickEvent();

        // 初始化所有fragment
        initFragment();

    }

    /**
     * 初始化所有fragment
     */
    private void initFragment() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        if (!homeFragment.isAdded()) {
            fragmentTransaction.add(R.id.content, homeFragment);
            fragmentTransaction.hide(homeFragment);
        }
        if (!taskFragment.isAdded()) {
            fragmentTransaction.add(R.id.content, taskFragment);
            fragmentTransaction.hide(taskFragment);
        }
        if (!rewardFragment.isAdded()) {
            fragmentTransaction.add(R.id.content, rewardFragment);
            fragmentTransaction.hide(rewardFragment);
        }
        if (!meFragment.isAdded()) {
            fragmentTransaction.add(R.id.content, meFragment);
            fragmentTransaction.hide(meFragment);
        }
        hideAllFragment(fragmentTransaction);
        // 默认显示第一个fragment
        fragmentTransaction.show(homeFragment);
        fragmentTransaction.commit();
    }

    /**
     * 隐藏所有fragment
     *
     * @param fragmentTransaction
     */
    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        fragmentTransaction.hide(homeFragment);
        fragmentTransaction.hide(taskFragment);
        fragmentTransaction.hide(rewardFragment);
        fragmentTransaction.hide(meFragment);
    }

    /**
     * 初始化按钮单击事件
     */
    private void initClickEvent() {
        homeFrameLayout.setOnClickListener(this);
        taskFrameLayout.setOnClickListener(this);
        rewardFrameLayout.setOnClickListener(this);
        meFrameLayout.setOnClickListener(this);

    }

    /**
     * 初始化组件
     */
    private void initView() {
        homeFrameLayout = (LinearLayout) findViewById(R.id.homeLayout);
        taskFrameLayout = (LinearLayout) findViewById(R.id.taskLayout);
        rewardFrameLayout = (LinearLayout) findViewById(R.id.rewardLayout);
        meFrameLayout = (LinearLayout) findViewById(R.id.meLayout);


        homeImageView = (ImageView) findViewById(R.id.homeImageView);
        taskImageView = (ImageView) findViewById(R.id.taskImageView);
        rewardImageView = (ImageView) findViewById(R.id.rewardImageView);
        meImageView = (ImageView) findViewById(R.id.meImageView);


        homeTextView = (TextView) findViewById(R.id.homeTextView);
        taskTextView = (TextView) findViewById(R.id.taskTextView);
        rewardTextView = (TextView) findViewById(R.id.rewardTextView);
        meTextView = (TextView) findViewById(R.id.meTextView);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.homeLayout:
                // 点击微信tab
                clickTab(homeFragment);
                break;

            case R.id.taskLayout:
                // 点击联系人tab
                clickTab(taskFragment);
                break;

            case R.id.rewardLayout:
                // 点击发现tab
                clickTab(rewardFragment);
                break;

            case R.id.meLayout:
                // 点击我tab
                clickTab(meFragment);
                break;

            default:
                break;
        }
    }

    /**
     * 点击下面的Tab按钮
     *
     * @param tabFragment
     */
    private void clickTab(Fragment tabFragment) {

        // 清除上次选中状态
        clearSeleted();

        FragmentTransaction fragmentTransaction = getFragmentManager() .beginTransaction();

        // 隐藏所有fragment
        hideAllFragment(fragmentTransaction);

        // 显示该Fragment
        fragmentTransaction.show(tabFragment);

        // 提交事务
        fragmentTransaction.commit();

        // 改变tab的样式,设置为选中状态
        changeTabStyle(tabFragment);

    }

    /**
     * 清除上次选中状态
     */
    private void clearSeleted() {
        if (!homeFragment.isHidden()) {
           // homeImageView.setImageResource(R.drawable.weixin_grey);
            homeTextView.setTextColor(Color.parseColor("#999999"));
        }

        if (!taskFragment.isHidden()) {
           // taskImageView.setImageResource(R.drawable.contract_grey);
            taskTextView.setTextColor(Color.parseColor("#999999"));
        }

        if (!rewardFragment.isHidden()) {
          //  rewardImageView.setImageResource(R.drawable.find_grey);
            rewardTextView.setTextColor(Color.parseColor("#999999"));
        }

        if (!meFragment.isHidden()) {
          //  meImageView.setImageResource(R.drawable.me_grey);
            meTextView.setTextColor(Color.parseColor("#999999"));
        }
    }

    /**
     * 根据Fragment的状态改变样式
     */
    private void changeTabStyle(Fragment tabFragment) {
        if (tabFragment instanceof HomeFragment) {
            homeImageView.setImageResource(R.drawable.home);
            homeTextView.setTextColor(Color.parseColor("#45C01A"));
        }

        if (tabFragment instanceof TaskFragment) {
            taskImageView.setImageResource(R.drawable.task);
            taskTextView.setTextColor(Color.parseColor("#45C01A"));
        }

        if (tabFragment instanceof RewardFragment) {
            rewardImageView.setImageResource(R.drawable.reward);
            rewardTextView.setTextColor(Color.parseColor("#45C01A"));
        }

        if (tabFragment instanceof MeFragment) {
            meImageView.setImageResource(R.drawable.me);
            meTextView.setTextColor(Color.parseColor("#45C01A"));
        }
    }
}
