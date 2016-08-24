package activity.zksq.zyy.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Task;
import service.TaskService;
import util.DatabaseHelper;


public class TaskFragment extends Fragment {


    private ListView lv_task;  //声明一个ListView对象
    private Button btn_addtask;
    private List<Task> taskList = new ArrayList<Task>();  //声明一个list，动态存储要显示的信息
    private TaskService taskService;

    private OnFragmentInteractionListener mListener;

    public TaskFragment() {
        // Required empty public constructor
    }

    public static TaskFragment newInstance(String param1, String param2) {
        TaskFragment fragment = new TaskFragment();
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

        View view = inflater.inflate(R.layout.fragment_task, container, false);
        lv_task = (ListView)view.findViewById(R.id.lv_task);

        taskService = new TaskService(getActivity());
        taskList = taskService. query();
        listViewShow(lv_task,taskList);

        btn_addtask = (Button)view.findViewById(R.id.bt_addTack);
        btn_addtask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Toast.makeText(getActivity(),"添加任务按钮被触发！",Toast.LENGTH_SHORT).show();
                LayoutInflater dialogInflater = getActivity().getLayoutInflater();
                View dialogView = dialogInflater.inflate(R.layout.dialog_task_con, null);

                final EditText et_taskName = (EditText)dialogView.findViewById(R.id.et_taskName);
                final EditText et_taskScore = (EditText)dialogView.findViewById(R.id.et_taskScore);

                new AlertDialog.Builder(getActivity()).setTitle("添加新任务").setView(dialogView)
                        .setPositiveButton("确定添加", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String taskName = et_taskName.getText().toString().trim();
                                String taskScore = et_taskScore.getText().toString().trim();
                                Task task = new Task();
                                task.setTaskName(taskName);
                                task.setTaskScore(taskScore);
                                taskList.add(task);
                                taskService.add(task);
                                taskList = taskService. query();
                                listViewShow(lv_task,taskList);

                                Toast.makeText(getActivity(),"添加成功",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("取消操作", null).show();
            }
        });

//单击每条
      //list调用menu，不是每条的
        lv_task.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int task_position = i;
              //  Toast.makeText(getActivity(),"位置是："+ taskList.get(i).getTaskName().toString(),
                        //Toast.LENGTH_SHORT).show();
                LayoutInflater taskMenuInflater = getActivity().getLayoutInflater();
                View dialogMenuView = taskMenuInflater.inflate(R.layout.dialog_task_menu,null);
                final ListView lv_task_menu = (ListView)dialogMenuView.findViewById(R.id.lv_task_menu);

                final String[] listCtrl = new String[] {"修改", "删除"};
                lv_task_menu.setAdapter(new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_list_item_1, listCtrl));

                final AlertDialog.Builder buildermenu = new  AlertDialog.Builder(getActivity());
                buildermenu.setTitle("操作");
                buildermenu.setView(dialogMenuView);
                buildermenu.show();

                lv_task_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        //Toast.makeText(getActivity(),"这个是第"+i+"个"+listCtrl[i],Toast.LENGTH_SHORT).show();
                        switch (i)
                        {
                            case 0:
                             //   Task updateTask = new Task();
                             //   updateTask.setTask_id(taskList.get(task_position).getTask_id());
                             //   updateTask.setTaskName(taskList.get(task_position).getTaskName());
                             //   updateTask.setTaskScore(taskList.get(task_position).getTaskScore());
                             //   taskService.update(updateTask);
                             //   taskList = taskService. query();
                             //   listViewShow(lv_task,taskList);
                                break;
                            case 1:
                               taskService.delete(taskList.get(task_position).getTask_id());
                                System.out.println(taskList.get(task_position).getTask_id());
                                taskList = taskService. query();
                                listViewShow(lv_task,taskList);

                                break;
                        }

                    }
                });
                return false;
            }
        });
        //  lv_task.setOnCreateContextMenuListener(this);
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
    private void listViewShow(ListView listView,List<Task> list)
    {
        ArrayList<Map<String,String>> taskData= new ArrayList<Map<String,String>>();;
        for (int i=0;i<list.size();i++)
        {
            Map<String,String> item = new HashMap<String,String>();
            System.out.println("--->>结果"+list.get(i).getTaskName()+">>"+list.get(i).getTaskScore());
            item.put("task_name","任务名称："+list.get(i).getTaskName());
            item.put("task_score","任务分数："+list.get(i).getTaskScore());
            taskData.add(item);
        }
        SimpleAdapter taskAdapter = new SimpleAdapter(getActivity(),taskData,android.R.layout.simple_list_item_2,
                new String[]{"task_name","task_score"},new int[]{android.R.id.text1,android.R.id.text2});
        listView.setAdapter(taskAdapter);

    }
    //系统的menu，
//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenu.ContextMenuInfo mi) {
//        menu.setHeaderTitle("操作");
//        //配置上下文菜单选项
//        menu.add(0, Menu.FIRST +1, 1, "修改");
//        menu.add(0, Menu.FIRST +2, 2, "删除");
//        //super.onCreateContextMenu(menu, v, mi);
//    }
//
//    @Override
//    public boolean onContextItemSelected(MenuItem item) {
//        if(item.getItemId() == Menu.FIRST + 1){
//            Toast.makeText(getActivity(), "修改！", Toast.LENGTH_SHORT).show();
//        }
//        else if(item.getItemId() == Menu.FIRST +2){
//            Toast.makeText(getActivity(), "删除！", Toast.LENGTH_SHORT).show();
//        }
//        return super.onContextItemSelected(item);
//    }

}
