package com.example.administrator.wuziqi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class NativeWuZiQiActivity extends AppCompatActivity {




    private Button btn_LWnewgame;
    private Wuziqi_native wuziqi;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_wu_zi_qi);

        wuziqi = (Wuziqi_native) findViewById(R.id.native_wuziqi);

        btn_LWnewgame = (Button) findViewById(R.id.btn_again);
        btn_LWnewgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wuziqi.reatart();

            }
        });
    }
}
