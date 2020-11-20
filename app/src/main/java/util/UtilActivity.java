package util;

import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class UtilActivity extends AppCompatActivity {
    public static boolean Cross_Promotion = false;
    public static boolean adFailed = false;
    public static boolean adLoaded = false;
    public static boolean adshow = false;
    public static SharedPreferences.Editor editor;
    public static List<String> imageLinks;
    public static List<String> imageLinks_two;
    public static SharedPreferences pref;
    public String PREFS_NAME = "PENCILSKETCH";

    /* renamed from: ad */
    int f6049ad = 0;
    int ads = 0;
    String appTitle = "promotion_app_name";
    String bannerAds = "Banner_Ads";
    int count = 0;
    public boolean editing;
    String extraInterstitial = "Extra_Interstitial";
    public String imageConfig = null;
    public boolean main;
    String packageID = "packageId";
    String savingad = "Saving_Interstitial";

    private void initializeCreditPack(String str, int i) {
        if (!SharedPreferencesManager.HasKey(this, str)) {
            SharedPreferencesManager.setSomeIntValue(this, str, i);
        } else if (SharedPreferencesManager.getSomeIntValue(this, str) != i) {
            SharedPreferencesManager.setSomeIntValue(this, str, i);
        }
    }

    private void initializeRatingText(String str, String str2) {
        if (!SharedPreferencesManager.HasKey(this, str)) {
            SharedPreferencesManager.setSomeStringValue(this, str, str2);
        } else if (SharedPreferencesManager.getSomeStringValue(this, str) != str2) {
            SharedPreferencesManager.setSomeStringValue(this, str, str2);
        }
    }

    private void ConfigureBottomAds(boolean z) {
        if (!SharedPreferencesManager.HasKey(this, "bottom_ads")) {
            SharedPreferencesManager.setSomeBoolValue(this, "bottom_ads", z);
        } else if (SharedPreferencesManager.getSomeBoolValue(this, "bottom_ads") != z) {
            SharedPreferencesManager.setSomeBoolValue(this, "bottom_ads", z);
        }
    }

    private void ConfigureNativeFullScreen(boolean z) {
        if (!SharedPreferencesManager.HasKey(this, "native_full_screen")) {
            SharedPreferencesManager.setSomeBoolValue(this, "native_full_screen", z);
        } else if (SharedPreferencesManager.getSomeBoolValue(this, "native_full_screen") != z) {
            SharedPreferencesManager.setSomeBoolValue(this, "native_full_screen", z);
        }
    }

    private void ConfigureEDNativeFullScreen(boolean z) {
        if (!SharedPreferencesManager.HasKey(this, "ed_native_full_screen")) {
            SharedPreferencesManager.setSomeBoolValue(this, "ed_native_full_screen", z);
        } else if (SharedPreferencesManager.getSomeBoolValue(this, "ed_native_full_screen") != z) {
            SharedPreferencesManager.setSomeBoolValue(this, "ed_native_full_screen", z);
        }
    }
}


