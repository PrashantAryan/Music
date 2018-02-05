package com.root.music.util;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Ashutosh on 24/7/17.
 */

public  class UtilClass {


    public static void intentService(Class<?> intentclass, Context context) {
        Intent intent = new Intent(context, intentclass);
        context.stopService(intent);
    }

    public static void intentClass(Class<?> aClass, Context context) {
        Intent intent = new Intent(context, aClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
