package com.example.administrator.wuziqi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button come_again;
    private Wuziqi wuziqi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        come_again= (Button) findViewById(R.id.btn_again);
        wuziqi= (Wuziqi) findViewById(R.id.wuziqi);
        come_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wuziqi.reatart();
            }
        });
    }
}
