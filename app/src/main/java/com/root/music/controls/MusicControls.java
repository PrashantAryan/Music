package com.root.music.controls;

import android.content.Context;

import com.root.music.R;
import com.root.music.service.MusicLstService;
import com.root.music.util.MusicPlayerConstants;
import com.root.music.util.MusicListUtilFunctions;

/**
 * Created by Ashutosh on 20/7/17.
 */
public class MusicControls {
       public static void playControl(Context context) {
        sendMessage(context.getResources().getString(R.string.play));
    }

    public static void pauseControl(Context context) {
        sendMessage(context.getResources().getString(R.string.pause));
    }

    public static void nextControl(Context context) {
        boolean isServiceRunning = MusicListUtilFunctions.isServiceRunning(MusicLstService.class.getName(), context);
        if (!isServiceRunning)
            return;
        if (MusicPlayerConstants.SONGS_LIST.size() > 0) {
            if (MusicPlayerConstants.SONG_NUMBER < (MusicPlayerConstants.SONGS_LIST.size() - 1)) {
                MusicPlayerConstants.SONG_NUMBER++;
                MusicPlayerConstants.SONG_CHANGE_HANDLER.sendMessage(MusicPlayerConstants.SONG_CHANGE_HANDLER.obtainMessage());
            } else {
                MusicPlayerConstants.SONG_NUMBER = 0;
                MusicPlayerConstants.SONG_CHANGE_HANDLER.sendMessage(MusicPlayerConstants.SONG_CHANGE_HANDLER.obtainMessage());
            }
        }
        MusicPlayerConstants.SONG_PAUSED = false;
    }

    public static void previousControl(Context context) {
        boolean isServiceRunning = MusicListUtilFunctions.isServiceRunning(MusicLstService.class.getName(), context);
        if (!isServiceRunning)
            return;
        if (MusicPlayerConstants.SONGS_LIST.size() > 0) {
            if (MusicPlayerConstants.SONG_NUMBER > 0) {
                MusicPlayerConstants.SONG_NUMBER--;
                MusicPlayerConstants.SONG_CHANGE_HANDLER.sendMessage(MusicPlayerConstants.SONG_CHANGE_HANDLER.obtainMessage());
            } else {
                MusicPlayerConstants.SONG_NUMBER = MusicPlayerConstants.SONGS_LIST.size() - 1;
                MusicPlayerConstants.SONG_CHANGE_HANDLER.sendMessage(MusicPlayerConstants.SONG_CHANGE_HANDLER.obtainMessage());
            }
        }
        MusicPlayerConstants.SONG_PAUSED = false;
    }

    private static void sendMessage(String message) {
        try {
            MusicPlayerConstants.PLAY_PAUSE_HANDLER.sendMessage(MusicPlayerConstants.PLAY_PAUSE_HANDLER.obtainMessage(0, message));
        } catch (Exception e) {
        }
    }
}
