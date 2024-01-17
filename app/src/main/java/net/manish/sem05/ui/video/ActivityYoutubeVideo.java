package com.pixelnx.eacademy.ui.video;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.pixelnx.eacademy.R;
import com.pixelnx.eacademy.ui.home.ActivityHome;


public class ActivityYoutubeVideo extends AppCompatActivity {


    String key = "";
    String vId = "";
    YouTubePlayerView youTubePlayerView;
    ImageButton rotateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_youtube_video);

        if (Build.VERSION.SDK_INT <= 25) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/"+getIntent().getStringExtra("vId"))).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            finish();
        }

        if (getIntent().hasExtra("firebase/notice")) {
                   vId=getIntent().getStringExtra("vId");
        } else {
            key = getIntent().getStringExtra("key");
            vId = getIntent().getStringExtra("vId");
        }



        youTubePlayerView = findViewById(R.id.youtube_player_view);
       // youTubePlayerView.setDrawingCacheQuality();

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
        getLifecycle().addObserver(youTubePlayerView);

              youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {

                youTubePlayer.loadVideo("" + vId, 0f);



            }

            @Override
            public void onError(YouTubePlayer youTubePlayer, PlayerConstants.PlayerError error) {
                super.onError(youTubePlayer, error);

            }

            @Override
            public void onStateChange(YouTubePlayer youTubePlayer, PlayerConstants.PlayerState state) {
                super.onStateChange(youTubePlayer, state);



            }

            @Override
            public void onPlaybackQualityChange(YouTubePlayer youTubePlayer, PlayerConstants.PlaybackQuality playbackQuality) {
                super.onPlaybackQualityChange(youTubePlayer, playbackQuality);


            }

            @Override
            public void onPlaybackRateChange(YouTubePlayer youTubePlayer, PlayerConstants.PlaybackRate playbackRate) {
                super.onPlaybackRateChange(youTubePlayer, playbackRate);
            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {

        super.onResume();

    }

    @Override
    public void onBackPressed() {
        if (getIntent().hasExtra("firebase/notice")) {
            startActivity(new Intent(getApplicationContext(), ActivityHome.class));
            finish();

        }else{
        super.onBackPressed();
        }
    }
}
