package com.teamvinay.sketch;

import android.app.Activity;

public class PurchaseActivity extends Activity {
    private static PurchaseActivity sSoleInstance;

    public static PurchaseActivity getInstance() {
        if (sSoleInstance == null) {
            sSoleInstance = new PurchaseActivity();
        }
        return sSoleInstance;
    }
}