package com.root.music.util;

import android.os.Handler;

import java.util.ArrayList;

public class MusicPlayerConstants {
    public static ArrayList<MediaItem> SONGS_LIST = new ArrayList<>();
    public static int SONG_NUMBER = 0;
    public static boolean SONG_PAUSED = true;
    public static Handler SONG_CHANGE_HANDLER;
    public static Handler PLAY_PAUSE_HANDLER;
    public static Handler PROGRESSBAR_HANDLER;
}
