package service;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import model.Task;
import util.DatabaseHelper;

/**
 * Created by zyy on 2016/8/24.
 */
public class MyScoreService {
    private DatabaseHelper dbHelper;

    public MyScoreService(Context context) {
        dbHelper = new DatabaseHelper(context);
    }
    public String query(){
        String myscore = "0";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM myscore " ,new String[]{});

        while(cursor.moveToNext()){

            myscore = cursor.getString(cursor.getColumnIndex("score"));

        }

        cursor.close();
        db.close();
        return myscore;
    }
    public void update(String score)
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "update myscore set score="+score+" where score_id=1";
        db.execSQL(sql);

    }
}
