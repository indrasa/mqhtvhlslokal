package com.mqhtv.mqhtvhlslokal;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.util.Util;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    SimpleExoPlayer player;
    PlayerView playerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void inisialisasi() {
//        String alamathlsmqhtv = "http://demo.unified-streaming.com/video/tears-of-steel/tears-of-steel.ism/.m3u8";
//        String alamathlsmqhtv = "192.168.100.14/hls/mqhtv.m3u8";
//        String alamathlsmqhtv = "http://192.168.1.9/hls/mqhtv.m3u8";
//        String alamathlsmqhtv = "http://127.0.0.1:8080/hls/mqhtv.m3u8";
//        String alamathlsmqhtv = "http://192.168.100.14:8080/hls/mqhtv.m3u8";
//        String alamathlsmqhtv = "http://192.168.100.63/hls/mqhtv.m3u8";A
        String alamathlsmqhtv = "http://192.168.100.14:8080/hls/mqhtv.m3u8";
//        String alamathlsmqhtv = "http://192.168.100.63:8080/hls/mqhtv.m3u8";

        DataSource.Factory dataSourceFactory = new DefaultHttpDataSource.Factory();
        HlsMediaSource hlsMediaSource = new HlsMediaSource.Factory(dataSourceFactory).createMediaSource(MediaItem.fromUri(alamathlsmqhtv));

        player = new SimpleExoPlayer.Builder(this).build();
        playerView = (PlayerView) findViewById(R.id.playerView);
        playerView.setPlayer(player);

        player.setMediaSource(hlsMediaSource);

//        player.addListener(PendengarKejadian);

        player.setPlayWhenReady(true);
//        player.setRepeatMode(Player.REPEAT_MODE_ALL);

        player.prepare();
        player.play();


    }

//    private class PendengarKejadian


//    @SuppressLint("InlinedApi")
//    private void hideSystemUi() {
//        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
//                | View.SYSTEM_UI_FLAG_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
//    }

    @Override
    public void onStart() {
        super.onStart();
//        if (Util.SDK_INT >= 24) {
//            hideSystemUi();
            inisialisasi();
//        }
    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        player.release();
//    }

        @Override
    public void onResume() {
        super.onResume();
//        hideSystemUi();
        if ((Util.SDK_INT < 24 || player == null)) {
            inisialisasi();
        }
    }



    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT < 24) {
            player.release();
            player = null;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT >= 24) {
            player.release();
            player = null;
        }
    }
}