package com.pixelnx.eacademy.ui.galary.galleryvideos;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.util.Util;
import com.pixelnx.eacademy.R;
import com.pixelnx.eacademy.ui.home.ActivityHome;

public class ExoplayerVideos extends AppCompatActivity {
    PlayerView playerView;
    SimpleExoPlayer player;
    Context mContext;
    private boolean playWhenReady = true;
    private int currentWindow = 0;
    static long playbackPosition = 0;
    String url;
    ImageButton rotateBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_exo_player);
        playerView = findViewById(R.id.playerView);
        mContext = ExoplayerVideos.this;
        rotateBtn=findViewById(R.id.rotateBtn);
        rotateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }
                else {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
            }
        });
        if (getIntent().hasExtra("WEB_URL")) {
        url=getIntent().getStringExtra("WEB_URL");

            Log.v("TESTT","   "+url);





            initializePlayer();
        }



    }

    private void initializePlayer() {
        if (player != null) {
            player.stop();
        }
        player = new SimpleExoPlayer.Builder(this).build();
        playerView.setPlayer(player);
        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playbackPosition);
        MediaItem mediaItem = MediaItem.fromUri(url);
        player.setMediaItem(mediaItem);
        playerView.setControllerShowTimeoutMs(0);
        playerView.setControllerHideOnTouch(false);
        player.prepare();


/*
        new CountDownTimer(player.getDuration(), 1000) {

            public void onTick(long millisUntilFinished) {
                Log.v("saloni1234","seconds remaining: "+millisUntilFinished+"\n"+player.getDuration() +"\n"+player.getCurrentPosition());
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
              Log.v("saloni1234","  Done!!");
            }

        }.start();*/
    }


    void getPlayerPosition() {
        if (player != null) {
            playWhenReady = player.getPlayWhenReady();
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT < 24) {
            getPlayerPosition();
        }

    }


    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT >= 24) {
            getPlayerPosition();
        }
        player.stop();
    }
    @Override
    public void onBackPressed() {
        if (getIntent().hasExtra("firebase/notice")) {
            startActivity(new Intent(getApplicationContext(), ActivityHome.class));
            finish();
        } else {
            super.onBackPressed();
        }
    }

}
