package com.example.administrator.wuziqi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button native_game;
    private Button online_game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        native_game = (Button) findViewById(R.id.btn_native);
        online_game = (Button) findViewById(R.id.btn_online);

        native_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NativeWuZiQiActivity.class));
            }
        });
        online_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CreateOrJoinActivity.class));
            }
        });
    }
}
