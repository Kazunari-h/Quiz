package com.hal2016.kazunari.quiz;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import java.util.List;


public class gamePlayActivity extends ActionBarActivity implements SensorEventListener {

    private SoundPool mSoundPool;
    private int mSoundId;
    private MediaPlayer mediaPlayer;

    private Button select1;
    private Button select2;
    private Button select3;
    private Button select4;

    private Boolean selectFlg = false;
    private int selectNum = 0;

    private int anser = 1;
    private SensorManager mSensorManager;

    private ImageView imv;

    @Override
    protected void onResume() {
        super.onResume();
        // 予め音声データを読み込む
        mSoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        mSoundId = mSoundPool.load(getApplicationContext(), R.raw.mino, 0);
        List<Sensor> sensors = mSensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if(sensors.size() > 0) {
            Sensor s = sensors.get(0);
            mSensorManager.registerListener(this, s, SensorManager.SENSOR_DELAY_UI);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);
        playFromMediaPlayer();
        imv = (ImageView)findViewById(R.id.imageView1);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        select1 = (Button)findViewById(R.id.select1);
        select2 = (Button)findViewById(R.id.select2);
        select3 = (Button)findViewById(R.id.select3);
        select4 = (Button)findViewById(R.id.select4);
        select1.setText(R.string.q1s1);
        select2.setText(R.string.q1s2);
        select3.setText(R.string.q1s3);
        select4.setText(R.string.q1s4);

        final AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);

        // ダイアログの設定
        //alertDialog.setIcon(R.drawable.sugi);               //アイコン設定
        alertDialog.setMessage("ファイナルアンサー？");     //内容(メッセージ)設定

        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // OKボタン押下時の処理
                if (selectNum == anser) {
                    Intent intent = new Intent(gamePlayActivity.this, WinActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(gamePlayActivity.this, loseActivity.class);
                    startActivity(intent);
                }
            }
        });

        alertDialog.setNegativeButton("NG", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                playFromMediaPlayer();
            }
        });

        select1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectBtn(1);
            }
        });

        select2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectBtn(2);
            }
        });

        select3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectBtn(3);
            }
        });

        select4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectBtn(4);
            }
        });

        Button btn01 = (Button)findViewById(R.id.anser);
        btn01.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectNum != 0 ){
                    mediaPlayer.pause();
                    playFromSoundPool();
                    alertDialog.setTitle("あなたの答えは"+selectNum);
                    alertDialog.show();
                }
            }
        });

    }


    public void onSelectBtn(int num){
        selectFlg = true;
        this.selectNum = num;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game_play, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void playFromMediaPlayer() {
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.milion);
        mediaPlayer.start();
    }

    private void stopFromMediaPlayer() {
        mediaPlayer.stop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // リリース
        mSoundPool.release();
    }

    private void playFromSoundPool() {
        // 再生
        mSoundPool.play(mSoundId, 1.0F, 1.0F, 0, 0, 1.0F);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            int x = (int)event.values[SensorManager.DATA_X];
            System.out.println(event.values[0] + "a");
            if (event.values[0] < 0.1){
                imv.setImageResource(R.drawable.sugi);

            }else{
                imv.setImageResource(R.drawable.sugi3);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
