package com.mqhtv.mqhtvhlslokal;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;

public class MainActivity extends AppCompatActivity {
    SimpleExoPlayer player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        MediaItem mediaItem = MediaItem.fromUri(hlsUri);
        String alamathlsmqhtv = "http://demo.unified-streaming.com/video/tears-of-steel/tears-of-steel.ism/.m3u8";
        DataSource.Factory dataSourceFactory = new DefaultHttpDataSource.Factory();
        HlsMediaSource hlsMediaSource = new HlsMediaSource.Factory(dataSourceFactory).createMediaSource(MediaItem.fromUri(alamathlsmqhtv));

        player = new SimpleExoPlayer.Builder(this).build();
        PlayerView playerView = (PlayerView) findViewById(R.id.playerView);
        playerView.setPlayer(player);

        player.setMediaSource(hlsMediaSource);
        player.prepare();
        player.play();
    }


    @Override
    protected void onStop() {
        super.onStop();
//        Log.i("SUDAH KELUAR", "EXOPLAYER SUDAH DITUTUP");
        player.release();
    }
}