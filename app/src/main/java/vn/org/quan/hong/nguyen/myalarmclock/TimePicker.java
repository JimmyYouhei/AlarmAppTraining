package vn.org.quan.hong.nguyen.myalarmclock;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Calendar;

public class TimePicker extends AppCompatActivity {
    private TextView title;
    private ImageButton btnBack;
    private Button btnAdd;
    Intent fromMain;
    private android.widget.TimePicker timePicker;
    public static final String HOUR_KEY ="hour , key";
    public static final String MINUTE_KEY = "minute , key";
    public static final String TIME_IN_MILIS_KEY = "time , milis";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_picker);

        title = findViewById(R.id.txtTimeTitle);
        btnBack = findViewById(R.id.btnBack);
        btnAdd = findViewById(R.id.btnAdd);
        timePicker = findViewById(R.id.timePicker);

        fromMain = getIntent();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        if (fromMain.getAction().equals(Intent.ACTION_INSERT)){
            title.setText("Add");
            btnAdd.setText("ADD");

            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent addResult = new Intent();
                    if (Build.VERSION.SDK_INT >= 23){
                        addResult.putExtra(HOUR_KEY , timePicker.getHour());
                        addResult.putExtra(MINUTE_KEY , timePicker.getMinute());
                    } else {
                        addResult.putExtra(HOUR_KEY, timePicker.getCurrentHour());
                        addResult.putExtra(MINUTE_KEY , timePicker.getCurrentMinute());
                    }

                    setResult(RESULT_OK , addResult);
                    finish();

                }
            });

        } else if (fromMain.getAction().equals(Intent.ACTION_EDIT)) {
            title.setText("Edit");
            btnAdd.setText("EDIT");

            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent editAlarm = new Intent();
                    editAlarm.putExtra(AlarmAdapter.ViewHolder.OLD_HH_MM_STRING_KEY ,
                            fromMain.getStringExtra(AlarmAdapter.ViewHolder.OLD_HH_MM_STRING_KEY));
                    editAlarm.putExtra(AlarmAdapter.ViewHolder.AM_PM_KEY,
                            fromMain.getStringExtra(AlarmAdapter.ViewHolder.AM_PM_KEY));

                    if (Build.VERSION.SDK_INT >= 23){
                        editAlarm.putExtra(HOUR_KEY , timePicker.getHour());
                        editAlarm.putExtra(MINUTE_KEY , timePicker.getMinute());
                    } else {
                        editAlarm.putExtra(HOUR_KEY, timePicker.getCurrentHour());
                        editAlarm.putExtra(MINUTE_KEY , timePicker.getCurrentMinute());
                    }

                    setResult(RESULT_OK , editAlarm);
                    finish();

                }
            });
        } else if (fromMain.getAction().equals(Intent.ACTION_DELETE)){
            setResult(RESULT_OK , fromMain);
            finish();
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_CANCELED);
        finish();
    }

}
