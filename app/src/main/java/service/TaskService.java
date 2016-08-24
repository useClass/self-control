package service;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import model.Task;
import util.DatabaseHelper;

/**
 * Created by Administrator on 2016/8/21 0021.
 */
public class TaskService {
    private DatabaseHelper dbHelper;

    public TaskService(Context context) {


        dbHelper = new DatabaseHelper(context);
    }


    //添加任务
    public void add(Task task){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "insert into task(task_name,task_score) values('"+task.getTaskName()+"',"+task.getTaskScore()+");";
        db.execSQL(sql);
    }

    public List<Task> query(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM task " ,new String[]{});
        List<Task> taskList = new ArrayList<Task>();

        while(cursor.moveToNext()){

            String task_id = cursor.getString(cursor.getColumnIndex("task_id"));
            String task_name = cursor.getString(cursor.getColumnIndex("task_name"));
            String task_score = cursor.getString(cursor.getColumnIndex("task_score"));
            Task task = new Task();
            task.setTask_id(task_id);
            task.setTaskName(task_name);
            task.setTaskScore(task_score);
            taskList.add(task);

          //  System.out.println("查询结果---->>"+str+"---"+str2);
        }

        cursor.close();
        db.close();
        return taskList;
    }

    public void delete(String id)
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "delete from task where task_id ="+id;
        db.execSQL(sql);

    }

    public void update(String id,String task_name,String task_score)
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "update task set task_name="+task_name+ ",task_score="+task_score+"where id"+id;
        db.execSQL(sql);

    }

}
