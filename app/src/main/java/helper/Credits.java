package helper;

import android.content.Context;
import util.SharedPreferencesManager;

public class Credits {
    public static int getCredits(Context context) {
        return SharedPreferencesManager.getSomeIntValue(context, Constants.CREDITS);
    }

    public static void addCredits(Context context, int i) {
        SharedPreferencesManager.setSomeIntValue(context, Constants.CREDITS, SharedPreferencesManager.getSomeIntValue(context, Constants.CREDITS) + i);
    }

    public static void deductCredits(Context context, int i) {
        SharedPreferencesManager.setSomeIntValue(context, Constants.CREDITS, SharedPreferencesManager.getSomeIntValue(context, Constants.CREDITS) - i);
    }
}
