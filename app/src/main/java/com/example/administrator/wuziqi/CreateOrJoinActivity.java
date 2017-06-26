package com.example.administrator.wuziqi;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobRealTimeData;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.ValueEventListener;

public class CreateOrJoinActivity extends AppCompatActivity {
    private String ObjectId;

    private Button btn_createRoom;
    private Button btn_enterroom;

    private Wuziqi wuziqi;
    Match match = new Match();
    private int roomNumber;
    BmobRealTimeData rtd = new BmobRealTimeData();

//    private  BmobRealTimeData rtd = new BmobRealTimeData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_or_join);

        wuziqi = (Wuziqi) findViewById(R.id.online_wuziqi);

        btn_enterroom = (Button) findViewById(R.id.btn_jion_room);

        btn_createRoom = (Button) findViewById(R.id.btn_create_room);


        btn_createRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wuziqi.reatart();
                View view = LayoutInflater.from(CreateOrJoinActivity.this).inflate(R.layout.view, null, false);

                final EditText phone = (EditText) view.findViewById(R.id.edt_phone);
                final EditText roomNub = (EditText) view.findViewById(R.id.edt_roomname);
                final EditText username = (EditText) view.findViewById(R.id.edt_username);

                new AlertDialog.Builder(CreateOrJoinActivity.this).setView(view)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                String getphone = phone.getText().toString();
                                int getroomNub = Integer.parseInt(roomNub.getText().toString());
                                String getusernameA = username.getText().toString();

                                match.setResult("a");
                                match.setUserB("a");
                                match.setPhone(getphone);
                                match.setRoom(getroomNub);
                                match.setUserA(getusernameA);
                                wuziqi.initMatch(match);
                                match.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {
                                        if (e == null) {

                                            getObjectId();



                                        }


                                    }


                                });





//                               Log.e("NNN",wuziqi.queryId());
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                        .create().show();
            }
        });
        btn_enterroom.setOnClickListener(new View.OnClickListener() {


            public void onClick(View v) {
                query();
                getObjectId();

                View view = LayoutInflater.from(CreateOrJoinActivity.this).inflate(R.layout.room_view, null, false);
                final EditText roomNub = (EditText) view.findViewById(R.id.edt_room_view_roomname);
                final EditText username = (EditText) view.findViewById(R.id.edt_room_view_username);
                new AlertDialog.Builder(CreateOrJoinActivity.this).setView(view)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                int getroomNub = Integer.parseInt(roomNub.getText().toString());
                                String getusername = username.getText().toString();

                                if (getroomNub == roomNumber) {

                                    match.setRoom(getroomNub);
                                    match.setUserA(getusername);
                                    wuziqi.initMatch(match);
                                    match.update(ObjectId, new UpdateListener() {
                                        @Override
                                        public void done(BmobException e) {
                                            if (e == null) {
                                                wuziqi.initId(ObjectId);
                                                Ondatachange(CreateOrJoinActivity.this);
                                            }

                                        }
                                    });
                                } else {
                                    Toast.makeText(CreateOrJoinActivity.this, "此房间号不存在", Toast.LENGTH_LONG).show();
//                                    Toast.makeText(MainActivity.this,roomNumber, Toast.LENGTH_LONG).show();
//                                    Log.e("BBB",roomNumber);
                                }

                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                        .create().show();
            }
        });
    }


    public void query() {

        BmobQuery<Match> query = new BmobQuery<>();
        query.addQueryKeys("room");
        query.findObjects(new FindListener<Match>() {
            @Override
            public void done(List<Match> list, BmobException e) {
                if (e== null) {
                    int index = list.size()-1;
                    roomNumber = list.get(index).getRoom();
                }

            }
        });


    }



    //    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        wuziqi.unregisterLisenner();
//
//    }
    public void Ondatachange(final Context context) {

        rtd.start(new ValueEventListener() {
            @Override
            public void onConnectCompleted(Exception e) {
                if (rtd.isConnected() ) {

                    rtd.subRowUpdate("Match", ObjectId);
                    Log.e("CC", "connected success"+ ObjectId);

                }


            }
            @Override
            public void onDataChange(JSONObject jsonObject) {
//                Match match1;
//                Gson gson=new Gson();
//                match1=gson.fromJson(String.valueOf(jsonObject),Match.class);
//                wlite= (ArrayList<Point>) match1.getWhiteArray();
//                black= (ArrayList<Point>) match1.getBlackArray();
//                Log.d("VV",match1.getUserA());
//                invalidate();
//              Toast.makeText(context, match.getUserA(), Toast.LENGTH_LONG).show();

                wuziqi.tongbuliangbianqizi(context, ObjectId);

            }

        });
    }
    public void getObjectId(){


        BmobQuery<Match> query = new BmobQuery<>();
        query.addQueryKeys("objectId");
        query.findObjects(new FindListener<Match>() {
            @Override

            public void done(List<Match> list, BmobException e) {
                if (e== null) {

                    int index = list.size()-1;
                    ObjectId = list.get(index).getObjectId();

                    Log.e("NNN",ObjectId);
                    wuziqi.initId(ObjectId);
                    Ondatachange(CreateOrJoinActivity.this);
                }

            }
        });



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        rtd.unsubRowUpdate("Match",ObjectId);
    }
}
