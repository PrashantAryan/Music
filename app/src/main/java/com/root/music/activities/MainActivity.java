package com.root.music.activities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.root.music.R;
import com.root.music.adapter.MusicPlayerAdapter;
import com.root.music.controls.MusicControls;
import com.root.music.interfaces.MusicPositionInterface;
import com.root.music.service.MusicLstService;
import com.root.music.util.MediaItem;
import com.root.music.util.MusicListUtilFunctions;
import com.root.music.util.MusicPlayerConstants;
import com.root.music.util.UtilClass;

/**
 * Created by Ashutosh on 20/7/17.
 */
public class MainActivity extends Activity implements OnClickListener {
    private MusicPlayerAdapter customAdapter = null;
    private static TextView playingSong;
    private Button btnPlayer;
    private static Button btnPause, btnPlay, btnNext, btnPrevious;
    private Button btnStop;
    private static LinearLayout linearLayoutPlayingSong;
    private RecyclerView mediarecyclerViewMusic;
    ProgressBar progressBar;
    TextView textBufferDuration, textDuration;
    static ImageView imageViewAlbumArt;
    static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().hide();
        setContentView(R.layout.activity_main);
        context = MainActivity.this;
        init();
        btnPlayer.setOnClickListener(this);
        btnPlay.setOnClickListener(this);
        btnPause.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnPrevious.setOnClickListener(this);
        btnStop.setOnClickListener(this);
        imageViewAlbumArt.setOnClickListener(this);
    }

    private void init() {
        getViews();
        playingSong.setSelected(true);
        progressBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.white), Mode.SRC_IN);
        if (MusicPlayerConstants.SONGS_LIST.size() <= 0) {
            MusicPlayerConstants.SONGS_LIST = MusicListUtilFunctions.listOfSongs(getApplicationContext());
        }
        setListItems();
    }

    private void setListItems() {
        customAdapter = new MusicPlayerAdapter(MusicPlayerConstants.SONGS_LIST, new MusicPositionInterface() {
            @Override
            public void musicListPosition(int postion) {
                MusicPlayerConstants.SONG_PAUSED = false;
                MusicPlayerConstants.SONG_NUMBER = postion;
                boolean isServiceRunning = MusicListUtilFunctions.isServiceRunning(MusicLstService.class.getName(), getApplicationContext());
                if (!isServiceRunning) {
                    UtilClass.intentService(MusicLstService.class, MainActivity.this);
                } else {
                    MusicPlayerConstants.SONG_CHANGE_HANDLER.sendMessage(MusicPlayerConstants.SONG_CHANGE_HANDLER.obtainMessage());
                }
                updateUI();
                changeButton();
            }
        });
        mediarecyclerViewMusic.setAdapter(customAdapter);

    }

    private void getViews() {
        playingSong = (TextView) findViewById(R.id.textNowPlaying);
        btnPlayer = (Button) findViewById(R.id.btnMusicPlayer);
        mediarecyclerViewMusic = (RecyclerView) findViewById(R.id.recyclerViewMusic);
        btnPause = (Button) findViewById(R.id.btnPause);
        btnPlay = (Button) findViewById(R.id.btnPlay);
        linearLayoutPlayingSong = (LinearLayout) findViewById(R.id.linearLayoutPlayingSong);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnStop = (Button) findViewById(R.id.btnStop);
        textBufferDuration = (TextView) findViewById(R.id.textBufferDuration);
        textDuration = (TextView) findViewById(R.id.textDuration);
        imageViewAlbumArt = (ImageView) findViewById(R.id.imageViewAlbumArt);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnPrevious = (Button) findViewById(R.id.btnPrevious);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mediarecyclerViewMusic.setLayoutManager(linearLayoutManager);
        mediarecyclerViewMusic.setHasFixedSize(true);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStop:
                UtilClass.intentService(MusicLstService.class, MainActivity.this);
                linearLayoutPlayingSong.setVisibility(View.GONE);
                break;
            case R.id.imageViewAlbumArt:
                UtilClass.intentClass(MusicPlayerActivity.class, MainActivity.this);
                break;
            case R.id.btnPrevious:
                MusicControls.previousControl(getApplicationContext());
                break;
            case R.id.btnNext:
                MusicControls.nextControl(getApplicationContext());
                break;
            case R.id.btnPause:
                MusicControls.pauseControl(getApplicationContext());
                break;
            case R.id.btnPlay:
                MusicControls.playControl(getApplicationContext());
                break;
            case R.id.btnMusicPlayer:
                UtilClass.intentClass(MusicPlayerActivity.class, MainActivity.this);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            boolean isServiceRunning = MusicListUtilFunctions.isServiceRunning(MusicLstService.class.getName(), getApplicationContext());
            if (isServiceRunning) {
                updateUI();
            } else {
                linearLayoutPlayingSong.setVisibility(View.GONE);
            }
            changeButton();
            MusicPlayerConstants.PROGRESSBAR_HANDLER = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    Integer i[] = (Integer[]) msg.obj;
                    textBufferDuration.setText(MusicListUtilFunctions.getDuration(i[0]));
                    textDuration.setText(MusicListUtilFunctions.getDuration(i[1]));
                    progressBar.setProgress(i[2]);
                }
            };
        } catch (Exception e) {
        }
    }

    @SuppressWarnings("deprecation")
    public static void updateUI() {
        try {
            MediaItem data = MusicPlayerConstants.SONGS_LIST.get(MusicPlayerConstants.SONG_NUMBER);
            playingSong.setText(data.getTitle() + " " + data.getArtist() + "-" + data.getAlbum());
            Bitmap albumArt = MusicListUtilFunctions.getAlbumart(context, data.getAlbumId());
            if (albumArt != null) {
                imageViewAlbumArt.setBackgroundDrawable(new BitmapDrawable(albumArt));
            } else {
                imageViewAlbumArt.setBackgroundDrawable(new BitmapDrawable(MusicListUtilFunctions.getDefaultAlbumArt(context)));
            }
            linearLayoutPlayingSong.setVisibility(View.VISIBLE);
        } catch (Exception e) {
        }
    }

    public static void changeButton() {
        if (MusicPlayerConstants.SONG_PAUSED) {
            btnPause.setVisibility(View.GONE);
            btnPlay.setVisibility(View.VISIBLE);
        } else {
            btnPause.setVisibility(View.VISIBLE);
            btnPlay.setVisibility(View.GONE);
        }
    }

    public static void changeUI() {
        updateUI();
        changeButton();
    }


}