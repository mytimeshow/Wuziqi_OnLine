package com.example.administrator.wuziqi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

public class OnLineWuZiQiActivity extends AppCompatActivity {

    private Button btn_createRoom;
    private Button btn_enterroom;
    private Wuziqi wuziqi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_line_wu_zi_qi);

        wuziqi = (Wuziqi) findViewById(R.id.online_wuziqi);


    }
}
