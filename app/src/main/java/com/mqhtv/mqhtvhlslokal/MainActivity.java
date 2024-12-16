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
//        String alamathlsmqhtv = "http://192.168.101.246:8080/hls/tvkabel.m3u8";
        String alamathlsmqhtv = "https://stream.mqhtv.cloud/hls/tv_kabel.m3u8";
//        String alamathlsmqhtv = "http://192.168.101.246:8080/hls/mqhtv.m3u8";
//        rtmp://192.168.101.246:1935
        DataSource.Factory dataSourceFactory = new DefaultHttpDataSource.Factory();
        HlsMediaSource hlsMediaSource = new HlsMediaSource.Factory(dataSourceFactory).createMediaSource(MediaItem.fromUri(alamathlsmqhtv));

        player = new SimpleExoPlayer.Builder(this).build();
        playerView = (PlayerView) findViewById(R.id.playerView);
        playerView.setPlayer(player);

        player.setMediaSource(hlsMediaSource);

        player.addListener(new PendengarKejadian());

        player.setPlayWhenReady(true);
//        player.setRepeatMode(Player.REPEAT_MODE_ALL);

        player.prepare();
        player.play();
    }

    private class PendengarKejadian implements Player.EventListener{
        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            if (playbackState == Player.STATE_ENDED){
                player.release();
                player = null;
                inisialisasi();
            }
        }

        @Override
        public void onPlayerError(ExoPlaybackException error) {
            player.release();
            player = null;
            inisialisasi();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
//        if (Util.SDK_INT >= 24) {
//            hideSystemUi();
            inisialisasi();
//        }
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