package dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import activity.zksq.zyy.myapplication.R;

public class TaskDialog extends Dialog {

    private EditText et_taskName;
    private EditText et_taskScore;
    private Button btn_okaTaskAdd;
    private Button btn_cancelTaskAdd;

    private String name;

    protected TaskDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
     //   View view = LayoutInflater.from(context).inflate(R.layout.dialog_task,null);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_task);
        setTitle(name);
        et_taskName = (EditText)findViewById(R.id.ed_taskName);
        et_taskScore = (EditText) findViewById(R.id.etn_taskScore);
        btn_okaTaskAdd = (Button)findViewById(R.id.btn_okaTaskAdd);
        btn_cancelTaskAdd = (Button)findViewById(R.id.btn_cancelTaskAdd);

      //  clickBtn.setOnClickListener(clickListener);
    }

    private class clickListerner implements View.OnClickListener
    {

        @Override
        public void onClick(View view) {
            int id = view.getId();

        }
    }


}
