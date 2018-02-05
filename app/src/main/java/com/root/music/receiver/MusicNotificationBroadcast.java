package com.root.music.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;

import com.root.music.activities.MainActivity;
import com.root.music.controls.MusicControls;
import com.root.music.service.MusicLstService;
import com.root.music.util.MusicPlayerConstants;
import com.root.music.util.UtilClass;

/**
 * Created by Ashutosh on 20/7/17.
 */
public class MusicNotificationBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_MEDIA_BUTTON)) {
            KeyEvent keyEvent = (KeyEvent) intent.getExtras().get(Intent.EXTRA_KEY_EVENT);
            if (keyEvent.getAction() != KeyEvent.ACTION_DOWN)
                return;

            switch (keyEvent.getKeyCode()) {
                case KeyEvent.KEYCODE_HEADSETHOOK:
                case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE:
                    if (!MusicPlayerConstants.SONG_PAUSED) {
                        MusicControls.pauseControl(context);
                    } else {
                        MusicControls.playControl(context);
                    }
                    break;
                case KeyEvent.KEYCODE_MEDIA_PLAY:
                    break;
                case KeyEvent.KEYCODE_MEDIA_PAUSE:
                    break;
                case KeyEvent.KEYCODE_MEDIA_STOP:
                    break;
                case KeyEvent.KEYCODE_MEDIA_NEXT:
                    MusicControls.nextControl(context);
                    break;
                case KeyEvent.KEYCODE_MEDIA_PREVIOUS:
                    MusicControls.previousControl(context);
                    break;
            }
        } else {
            if (intent.getAction().equals(MusicLstService.NOTIFY_PLAY)) {
                MusicControls.playControl(context);
            } else if (intent.getAction().equals(MusicLstService.NOTIFY_PAUSE)) {
                MusicControls.pauseControl(context);
            } else if (intent.getAction().equals(MusicLstService.NOTIFY_NEXT)) {
                MusicControls.nextControl(context);
            } else if (intent.getAction().equals(MusicLstService.NOTIFY_DELETE)) {
                UtilClass.intentService( MusicLstService.class,context);
                UtilClass.intentClass( MainActivity.class,context);
            } else if (intent.getAction().equals(MusicLstService.NOTIFY_PREVIOUS)) {
                MusicControls.previousControl(context);
            }
        }
    }

    public String ComponentName() {
        return this.getClass().getName();
    }


}
