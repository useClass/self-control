package util;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

/**
 * Created by Administrator on 2016/8/21 0021.
 */
public class DatabaseHelper extends SQLiteOpenHelper {


    private static final String DB_NAME = "task.db"; //数据库名称
    private static final int version = 1; //数据库版本

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql_task = "create table task(task_id integer primary key autoincrement,task_name varchar(50),task_score integer )";
        String sql_reward = "create table reward(reward_id integer primary key autoincrement,reward_name varchar(50),reward_score integer )";
        String sql_performance = "create table performance(performance_id integer primary key autoincrement,task_id integer,tak_name varchar(50),date date  )";
        String sql_myscore = "create table myscore(score_id integer primary key autoincrement,score integer)";
        String sql_scoreInit = "insert into myscore(score_id,score) values('1','0')";

        db.execSQL(sql_task);
        db.execSQL(sql_reward);
        db.execSQL(sql_performance);
        db.execSQL(sql_myscore);
        db.execSQL(sql_scoreInit);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /* 当version=2时会执行 */

    }
}
