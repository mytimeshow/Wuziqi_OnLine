package com.example.administrator.wuziqi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Administrator on 2017/6/8 0008.
 */

public class Wuziqi extends View {
    Match match;

    private int lineWidth;
    private float lineHeight;
    private int MAX_LINE = 10;
    private Paint mpaint;
    Bitmap a;
    Bitmap b;
    private Bitmap wlitePiece;
    private Bitmap balckPiece;
    private float radioPieceOfLineHeight = 3 * 1.0f / 4;
    private boolean IsWilte = true;
    private ArrayList<Point> wlite = new ArrayList<>();
    private ArrayList<Point> black = new ArrayList<>();
    private boolean IsgameOver;
    private boolean Iswhitewinner;
    private int MAX_IN_LINE = 5;
    private String objectId;
    private Canvas canve=new Canvas();
    private Handler handle;
//    private BmobRealTimeData rtd = new BmobRealTimeData();


    public Wuziqi(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBackgroundColor(0x44ff0000);
        initPaint();
    }

    public void initMatch(Match a) {
        match = a;
    }

    private void initPaint() {
        mpaint = new Paint();
        mpaint.setColor(Color.BLUE);
        mpaint.setAntiAlias(true);
        mpaint.setDither(true);
        mpaint.setStyle(Paint.Style.STROKE);
        a = BitmapFactory.decodeResource(getResources(), R.drawable.white);
        b = BitmapFactory.decodeResource(getResources(), R.drawable.black);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthModel = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightModel = MeasureSpec.getMode(heightMeasureSpec);

        int width = Math.min(widthSize, heightSize);


        if (widthModel == MeasureSpec.UNSPECIFIED) {
            width = heightSize;
        } else if (heightModel == MeasureSpec.UNSPECIFIED) {
            width = widthSize;
        }
        setMeasuredDimension(width, width);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        lineWidth = w;
        lineHeight = lineWidth * 1.0f / MAX_LINE;
        int PieceWidth = (int) (lineHeight * radioPieceOfLineHeight);
        wlitePiece = Bitmap.createScaledBitmap(a, PieceWidth, PieceWidth, false);
        balckPiece = Bitmap.createScaledBitmap(b, PieceWidth, PieceWidth, false);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (IsgameOver) {
            return false;
        }
        int action = event.getAction();
        if (action == MotionEvent.ACTION_UP) {

            int x = (int) event.getX();
            int y = (int) event.getY();
            Point p = getPoint(x, y);
            if (wlite.contains(p) || black.contains(p)) {
                return false;
            }
            if (IsWilte) {
//                initWhite();
                wlite.add(p);
                if(match==null){
                    return false;
                }

                match.setWhiteArray(wlite);
                match.update(objectId, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e != null) {
                            Log.e("AAA", e.toString());
                        } else {
                            Log.e("AAA", "update success");
                        }
                    }
                });

            } else {
//                initblack();
                black.add(p);
                if(match==null){
                    return false;
                }
                match.setBlackArray(black);
                match.update(objectId, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {

                    }
                });
            }
            invalidate();
            IsWilte = !IsWilte;
        }
        return true;
    }

    private void initblack() {
        BmobQuery<Match> query = new BmobQuery<>();
        query.addQueryKeys("blackArray");
        query.findObjects(new FindListener<Match>() {
            @Override
            public void done(List<Match> list, BmobException e) {
                if (e== null) {
                    int index = list.size() - 1;
                    black = (ArrayList<Point>) list.get(index).getBlackArray();

                }


            }
        });
    }

    private void initWhite() {
        BmobQuery<Match> query = new BmobQuery<>();
        query.addQueryKeys("whiteArray");
        query.findObjects(new FindListener<Match>() {
            @Override
            public void done(List<Match> list, BmobException e) {
                if (e== null) {
                    int index = list.size() - 1;
                    wlite = (ArrayList<Point>) list.get(index).getWhiteArray();

                }
            }
        });
    }

//    public void queryId() {
//        BmobQuery<Match> query = new BmobQuery<>();
//        query.addQueryKeys("objectId");
//        query.findObjects(new FindListener<Match>() {
//            @Override
//
//            public void done(List<Match> list, BmobException e) {
//                if (e== null) {
//                    int index = list.size() - 1;
//                    objectId = list.get(index).getObjectId();
//                }
//
//            }
//        });
//
//
//    }


//    public void unregisterLisenner(){
//        rtd.unsubRowUpdate("Match", objectId);
//    }

  public  void tongbuliangbianqizi(final Context context,String id) {
        BmobQuery<Match> query = new BmobQuery<>();
        query.addWhereEqualTo("objectId",id);
        query.findObjects(new FindListener<Match>() {
            @Override
            public void done(List<Match> list, BmobException e) {
                if (e== null) {

                    int index = list.size() - 1;
                    wlite.clear();
                    black.clear();
                    wlite.addAll(list.get(index).getWhiteArray());
                    black.addAll(list.get(index).getBlackArray());
                    invalidate();
                    Toast.makeText(context, "datachange", Toast.LENGTH_LONG).show();
                }
            }
        });




    }
    public void blacklisenner() {
        BmobQuery<Match> query = new BmobQuery<>();
        query.addQueryKeys("blackArray");
        query.findObjects(new FindListener<Match>() {
            @Override
            public void done(List<Match> list, BmobException e) {
                if (e== null) {
                    int index = list.size() - 1;
                    black = (ArrayList<Point>) list.get(index).getBlackArray();

                }


            }
        });
    }

    private Point getPoint(int x, int y) {

//        int b= (int) (x/lineHeight);
//        String a=String.valueOf(b);
//        Toast.makeText(getContext(),a,Toast.LENGTH_LONG).show();
        return new Point((int) (x / lineHeight), (int) (y / lineHeight));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canve = canvas;
        drawBoard(canvas);
        drawPiece(canvas);
        checkGameOver();
    }

    private void checkGameOver() {
        boolean whileWin = checkfiveInLine(wlite);
        boolean blackWin = checkfiveInLine(black);
        if (whileWin || blackWin) {
            IsgameOver = true;
            Iswhitewinner = whileWin;
            String text = Iswhitewinner ? "白棋胜利" : "黑棋胜利";
            Toast.makeText(getContext(), text, Toast.LENGTH_LONG).show();

        }

    }

    private boolean checkfiveInLine(List<Point> point) {
        for (Point p : point) {
            int x = p.x;
            int y = p.y;
            boolean win = checkHorizontal(x, y, point);
            if (win) {
                return true;
            }
            win = checkVertical(x, y, point);
            if (win) {
                return true;
            }
            win = checkXiexian(x, y, point);
            if (win) {
                return true;
            }
            win = checkRightXiexian(x, y, point);
            if (win) {
                return true;
            }
        }


        return false;

    }

    private boolean checkHorizontal(int x, int y, List<Point> point) {
        int count = 1;
        for (int i = 1; i < MAX_IN_LINE; i++) {
            if (point.contains(new Point(x - i, y))) {
                count++;
            } else {
                break;
            }
        }
        if (count == MAX_IN_LINE) {
            return true;
        }
        for (int i = 1; i < MAX_IN_LINE; i++) {
            if (point.contains(new Point(x + i, y))) {
                count++;
            } else {
                break;
            }
        }
        if (count == MAX_IN_LINE) {
            return true;
        }


        return false;
    }

    private boolean checkVertical(int x, int y, List<Point> point) {
        int count = 1;
        for (int i = 1; i < MAX_IN_LINE; i++) {
            if (point.contains(new Point(x, y - i))) {
                count++;
            } else {
                break;
            }
        }
        if (count == MAX_IN_LINE) {
            return true;
        }
        for (int i = 1; i < MAX_IN_LINE; i++) {
            if (point.contains(new Point(x, y + i))) {
                count++;
            } else {
                break;
            }
        }
        if (count == MAX_IN_LINE) {
            return true;
        }


        return false;
    }

    private boolean checkXiexian(int x, int y, List<Point> point) {
        int count = 1;
        for (int i = 1; i < MAX_IN_LINE; i++) {
            if (point.contains(new Point(x - i, y - i))) {
                count++;
            } else {
                break;
            }
        }
        if (count == MAX_IN_LINE) {
            return true;
        }
        for (int i = 1; i < MAX_IN_LINE; i++) {
            if (point.contains(new Point(x + i, y + i))) {
                count++;
            } else {
                break;
            }
        }
        if (count == MAX_IN_LINE) {
            return true;
        }


        return false;
    }

    private boolean checkRightXiexian(int x, int y, List<Point> point) {
        int count = 1;
        for (int i = 1; i < MAX_IN_LINE; i++) {
            if (point.contains(new Point(x + i, y - i))) {
                count++;
            } else {
                break;
            }
        }
        if (count == MAX_IN_LINE) {
            return true;
        }
        for (int i = 1; i < MAX_IN_LINE; i++) {
            if (point.contains(new Point(x - i, y + i))) {
                count++;
            } else {
                break;
            }
        }
        if (count == MAX_IN_LINE) {
            return true;
        }


        return false;
    }


    private void drawPiece(Canvas canvas) {
        for (int i = 0; i < wlite.size(); i++) {
            Point p = wlite.get(i);

            canvas.drawBitmap(wlitePiece, (p.x + (1 - radioPieceOfLineHeight) / 2) * lineHeight,
                    (p.y + (1 - radioPieceOfLineHeight) / 2) * lineHeight,
                    null);
        }
        for (int i = 0; i < black.size(); i++) {
            Point p = black.get(i);

            canvas.drawBitmap(balckPiece, (p.x + (1 - radioPieceOfLineHeight) / 2) * lineHeight,
                    (p.y + (1 - radioPieceOfLineHeight) / 2) * lineHeight,
                    null);
        }
    }


    private void drawBoard(Canvas canvas) {
        canve = canvas;
        int w = lineWidth;
        float h = lineHeight;
        for (int i = 0; i < MAX_LINE; i++) {
            int startX = (int) (h / 2);
            int endX = (int) (w - h / 2);
            int y = (int) ((0.5 + i) * h);
            canvas.drawLine(startX, y, endX, y, mpaint);
            canvas.drawLine(y, startX, y, endX, mpaint);

        }


    }

    public void reatart() {
        black.clear();
        wlite.clear();
        Iswhitewinner = false;
        IsgameOver = false;
        invalidate();

    }

    public void netReatart() {
        black.clear();
        wlite.clear();
        Iswhitewinner = false;
        IsgameOver = false;

        invalidate();

        match.setBlackArray(black);
        match.setWhiteArray(wlite);
        match.update(objectId, new UpdateListener() {
            @Override
            public void done(BmobException e) {

            }
        });

    }

    private static final String INSTANCE = "intance";
    private static final String INSTANCE_GAMEOVER = "intance_gameover";
    private static final String INSTANCE_WHILE = "intance_whilearray";
    private static final String INSTANCE_BLACK = "intance_blackarray";

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE, super.onSaveInstanceState());
        bundle.putBoolean(INSTANCE_GAMEOVER, IsgameOver);
        bundle.putParcelableArrayList(INSTANCE_BLACK, black);
        bundle.putParcelableArrayList(INSTANCE_WHILE, wlite);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {

        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            IsgameOver = bundle.getBoolean(INSTANCE_GAMEOVER);
            wlite = bundle.getParcelableArrayList(INSTANCE_WHILE);
            black = bundle.getParcelableArrayList(INSTANCE_BLACK);
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE));
            return;
        }
        super.onRestoreInstanceState(state);
    }

    public void initId(String aa) {
        objectId=aa;
        Log.e("MMM",objectId);
    }
}
