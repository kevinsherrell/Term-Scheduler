package com.c196.TermScheduler.Utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class Navigation {

    public static boolean navigate(Context context, Class cls) throws Exception {
        try {
            Intent intent = new Intent(context, cls);
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean navigate(Context context, Class cls, Bundle bundle) throws Exception {
        try {
            Intent intent = new Intent(context, cls);
            intent.putExtras(bundle);
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
}
