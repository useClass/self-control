package service;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import model.Reward;
import util.DatabaseHelper;

/**
 * Created by Administrator on 2016/8/22 0022.
 */
public class RewardService {
    private DatabaseHelper dbHelper;

    public RewardService(Context context) {
        dbHelper = new DatabaseHelper(context);
    }


    //添加任务
    public void add(Reward reward){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "insert into reward(reward_name,reward_score) values('"+reward.getReward_name()+"',"+reward.getReward_score()+");";
        db.execSQL(sql);
    }

    public List<Reward> query(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM reward " ,new String[]{});
        List<Reward> rewardList = new ArrayList<Reward>();

        while(cursor.moveToNext()){
            String reward_name = cursor.getString(cursor.getColumnIndex("reward_name"));
            String reward_score = cursor.getString(cursor.getColumnIndex("reward_score"));
            Reward reward = new Reward();
            reward.setReward_name(reward_name);
            reward.setReward_score(reward_score);
            rewardList.add(reward);

        }

        cursor.close();
        db.close();
        return rewardList;
    }
}
