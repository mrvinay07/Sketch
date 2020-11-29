package com.teamvinay.sketch;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.ShareCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;
import com.facebook.CallbackManager;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.mopub.mobileads.MoPubView;
import com.mopub.nativeads.MoPubNative;

import com.willy.ratingbar.BaseRatingBar;
import com.willy.ratingbar.ScaleRatingBar;
import helper.Constants;
import helper.ExportDetails;
import helper.ExportOptionsView;
import helper.ImageResolutions;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import listeners.TaskCompleteListener;
import jp.co.cyberagent.android.gpuimage.GPUImage;
import ui.ExportOptionListener;
import util.AdUtil;
import util.BitmapCache;
import util.FireBaseHelper;
import util.ImageFilePath;
import util.NativeAdUtil;
import util.Resizer;
import util.SharedPreferencesManager;
import util.Utils;

import static android.content.Intent.FILL_IN_ACTION;

public class ExportActivity extends Activity implements ExportOptionListener, View.OnClickListener, TaskCompleteListener {
    private static final String ADMOB_NATIVE_BANNER_ADUNIT_ID = "ca-app-pub-3940256099942544/2247696110";
    private static final String MOPUB_NATIVE_BANNER_ADUNIT_ID = "11a17b188668469fb0412708c3d16813";
    int AD_TYPE = 1;
    int CONTENT_TYPE = 0;
    int MOPUB_VIEW = 2;
    RelativeLayout adContainer;
    AdLoader adLoader;
    boolean ad_available = false;
    private UnifiedNativeAd admob_nativeAd;
    boolean bannerLoaded = false;
    BitmapCache bitmapCache;
    AdLoader.Builder builder;
    String cache_file_name = null;
    List<String> cache_name = new ArrayList();
    CallbackManager callbackManager;
    String closeButtonText;
    String closeButtonText2;
    private float density = 0.0f;
    Boolean dialog_show;
    Bitmap effectBitmap;
    EffectFilterDetails effectFilterDetails;
    /* access modifiers changed from: private */
    public Dialog effectLoadingDialog;
    String effectName = null;
    private boolean effectSelected = false;
    boolean enjoyPencilPhotoSketch_2 = false;
    ExportDetails exportDetails;
    boolean feedback_2 = false;
    Uri fileUri;
    RelativeLayout four_k_effect;
    boolean fresh_start = true;
    RelativeLayout full_hd_effect;
    GPUImage gpuImg;
    boolean handler_onetime_repeat = true;
    RelativeLayout hd_effect;
    RelativeLayout hdv_effect;
    RecyclerView iap_recycler_view;
    /* access modifiers changed from: private */
    public ImageView imageView;
    boolean image_share = false;
    ExportOptionsItemsAdapter itemsAdapter;
    boolean loadingDialoggone = false;
    private MoPubNative moPubNative;
    private MoPubNative.MoPubNativeNetworkListener moPubNativeNetworkListener;
    private MoPubView moPubView;
    String okButtonText;
    String okButtonText2;
    RecyclerView recyclerView;
    RelativeLayout regular_effect;
    boolean rerun = true;
    String res;
    List<ImageResolutions> resolutions;
    boolean reward_awarded = false;
    ShareDialog shareDialog;
    boolean share_dialog_launch = false;
    boolean sharing_done = false;
    boolean show_null_path_dialog = false;
    int targetLength = 0;
    RelativeLayout two_k_effect;
    HashMap<String, String> unlockFeatureList;
    List<Object> viewList;

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0209  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x021e  */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x0233  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onCreate(android.os.Bundle r6) {
        /*
            r5 = this;
            super.onCreate(r6)
            r6 = 2131493018(0x7f0c009a, float:1.8609504E38)
            r5.setContentView(r6)
            android.content.Intent r6 = r5.getIntent()
            java.lang.String r0 = "imageUri"
            java.lang.String r6 = r6.getStringExtra(r0)
            r0 = 2131296502(0x7f0900f6, float:1.8210922E38)
            android.view.View r0 = r5.findViewById(r0)
            android.widget.ImageView r0 = (android.widget.ImageView) r0
            r5.imageView = r0
            r0 = 2131296592(0x7f090150, float:1.8211105E38)
            android.view.View r0 = r5.findViewById(r0)
            androidx.recyclerview.widget.RecyclerView r0 = (androidx.recyclerview.widget.RecyclerView) r0
            r5.recyclerView = r0
            r0 = 2131296371(0x7f090073, float:1.8210657E38)
            android.view.View r0 = r5.findViewById(r0)
            android.widget.RelativeLayout r0 = (android.widget.RelativeLayout) r0
            r5.adContainer = r0
            r0 = 2131296342(0x7f090056, float:1.8210598E38)
            android.view.View r0 = r5.findViewById(r0)
            com.mopub.mobileads.MoPubView r0 = (com.mopub.mobileads.MoPubView) r0
            r5.moPubView = r0
            com.mopub.mobileads.MoPubView r0 = r5.moPubView
            java.lang.String r1 = "191821deb8ab4ef69bb9682048de3821"
            r0.setAdUnitId(r1)
            android.content.res.Resources r0 = r5.getResources()
            android.util.DisplayMetrics r0 = r0.getDisplayMetrics()
            float r0 = r0.density
            r5.density = r0
            r0 = 2131296680(0x7f0901a8, float:1.8211284E38)
            android.view.View r0 = r5.findViewById(r0)
            android.widget.RelativeLayout r0 = (android.widget.RelativeLayout) r0
            r5.regular_effect = r0
            r0 = 2131296678(0x7f0901a6, float:1.821128E38)
            android.view.View r0 = r5.findViewById(r0)
            android.widget.RelativeLayout r0 = (android.widget.RelativeLayout) r0
            r5.hd_effect = r0
            r0 = 2131296679(0x7f0901a7, float:1.8211281E38)
            android.view.View r0 = r5.findViewById(r0)
            android.widget.RelativeLayout r0 = (android.widget.RelativeLayout) r0
            r5.hdv_effect = r0
            r0 = 2131296677(0x7f0901a5, float:1.8211277E38)
            android.view.View r0 = r5.findViewById(r0)
            android.widget.RelativeLayout r0 = (android.widget.RelativeLayout) r0
            r5.full_hd_effect = r0
            r0 = 2131296681(0x7f0901a9, float:1.8211286E38)
            android.view.View r0 = r5.findViewById(r0)
            android.widget.RelativeLayout r0 = (android.widget.RelativeLayout) r0
            r5.two_k_effect = r0
            r0 = 2131296676(0x7f0901a4, float:1.8211275E38)
            android.view.View r0 = r5.findViewById(r0)
            android.widget.RelativeLayout r0 = (android.widget.RelativeLayout) r0
            r5.four_k_effect = r0
            android.widget.RelativeLayout r0 = r5.regular_effect
            java.lang.String r1 = "#2196F3"
            int r1 = android.graphics.Color.parseColor(r1)
            r0.setBackgroundColor(r1)
            java.util.HashMap r0 = new java.util.HashMap
            r0.<init>()
            r5.unlockFeatureList = r0
            android.widget.RelativeLayout r0 = r5.regular_effect
            r0.setOnClickListener(r5)
            android.widget.RelativeLayout r0 = r5.hd_effect
            r0.setOnClickListener(r5)
            android.widget.RelativeLayout r0 = r5.hdv_effect
            r0.setOnClickListener(r5)
            android.widget.RelativeLayout r0 = r5.full_hd_effect
            r0.setOnClickListener(r5)
            android.widget.RelativeLayout r0 = r5.two_k_effect
            r0.setOnClickListener(r5)
            android.widget.RelativeLayout r0 = r5.four_k_effect
            r0.setOnClickListener(r5)
            jp.co.cyberagent.android.gpuimage.GPUImage r0 = new jp.co.cyberagent.android.gpuimage.GPUImage
            r0.<init>(r5)
            r5.gpuImg = r0
            helper.ExportDetails r0 = helper.ExportDetails.getInstance()
            r5.exportDetails = r0
            android.net.Uri r6 = android.net.Uri.parse(r6)
            r5.fileUri = r6
            java.util.ArrayList r6 = new java.util.ArrayList
            r6.<init>()
            r5.viewList = r6
            java.util.List<java.lang.Object> r6 = r5.viewList
            helper.ExportOptionsView r0 = new helper.ExportOptionsView
            java.lang.String r1 = "Save"
            r2 = 2131689707(0x7f0f00eb, float:1.9008437E38)
            java.lang.String r2 = r5.getString(r2)
            r3 = 0
            r4 = 2131230824(0x7f080068, float:1.8077712E38)
            r0.<init>(r1, r3, r4, r2)
            r6.add(r0)
            java.util.List<java.lang.Object> r6 = r5.viewList
            helper.ExportOptionsView r0 = new helper.ExportOptionsView
            java.lang.String r1 = "Share"
            r2 = 2131689712(0x7f0f00f0, float:1.9008447E38)
            java.lang.String r2 = r5.getString(r2)
            r4 = 2131230825(0x7f080069, float:1.8077714E38)
            r0.<init>(r1, r3, r4, r2)
            r6.add(r0)
            java.util.List<java.lang.Object> r6 = r5.viewList
            helper.ExportOptionsView r0 = new helper.ExportOptionsView
            java.lang.String r1 = "Facebook"
            java.lang.String r2 = "Facebook"
            r4 = 2131230940(0x7f0800dc, float:1.8077947E38)
            r0.<init>(r1, r3, r4, r2)
            r6.add(r0)
            java.util.List<java.lang.Object> r6 = r5.viewList
            helper.ExportOptionsView r0 = new helper.ExportOptionsView
            java.lang.String r1 = "Instagram"
            java.lang.String r2 = "Instagram"
            r4 = 2131231001(0x7f080119, float:1.807807E38)
            r0.<init>(r1, r3, r4, r2)
            r6.add(r0)
            boolean r6 = helper.Constants.REMOVE_ADS
            r0 = 1
            r1 = 0
            if (r6 != 0) goto L_0x016c
            boolean r6 = helper.Constants.EXPORT_BUTTON_ADS
            if (r6 == 0) goto L_0x016c
            int r6 = helper.Constants.EXPORT_AD_BUTTON_SPACE
            r2 = 0
        L_0x013a:
            java.util.List<java.lang.Object> r3 = r5.viewList
            int r3 = r3.size()
            if (r6 > r3) goto L_0x016c
            util.NativeAdUtil r3 = util.NativeAdUtil.getInstance()
            java.util.List r3 = r3.getExport_native_ads()
            int r3 = r3.size()
            if (r2 >= r3) goto L_0x0167
            boolean r3 = helper.Constants.REMOVE_ADS
            if (r3 != 0) goto L_0x0167
            java.util.List<java.lang.Object> r3 = r5.viewList
            util.NativeAdUtil r4 = util.NativeAdUtil.getInstance()
            java.util.List r4 = r4.getExport_native_ads()
            java.lang.Object r4 = r4.get(r2)
            r3.add(r6, r4)
            int r2 = r2 + 1
        L_0x0167:
            int r3 = helper.Constants.EXPORT_AD_BUTTON_SPACE
            int r3 = r3 + r0
            int r6 = r6 + r3
            goto L_0x013a
        L_0x016c:
            com.hqgames.pencil.sketch.photo.ExportActivity$ExportOptionsItemsAdapter r6 = new com.hqgames.pencil.sketch.photo.ExportActivity$ExportOptionsItemsAdapter
            java.util.List<java.lang.Object> r2 = r5.viewList
            r6.<init>(r2, r5)
            r5.itemsAdapter = r6
            androidx.recyclerview.widget.LinearLayoutManager r6 = new androidx.recyclerview.widget.LinearLayoutManager
            r6.<init>(r5, r1, r1)
            androidx.recyclerview.widget.RecyclerView r2 = r5.recyclerView
            r2.setLayoutManager(r6)
            androidx.recyclerview.widget.RecyclerView r6 = r5.recyclerView
            com.hqgames.pencil.sketch.photo.ExportActivity$ExportOptionsItemsAdapter r2 = r5.itemsAdapter
            r6.setAdapter(r2)
            android.os.Bundle r6 = new android.os.Bundle
            r6.<init>()
            java.lang.String r2 = "Screen"
            java.lang.String r3 = "Export Screen"
            r6.putString(r2, r3)
            util.FireBaseHelper r2 = util.FireBaseHelper.getInstance()
            java.lang.String r3 = "Screen_Analytics"
            r2.LogEvent(r3, r6)
            helper.ExportDetails r6 = r5.exportDetails
            java.lang.String r2 = helper.Constants.IMAGE_PATH
            r6.setPath(r2)
            helper.ExportDetails r6 = r5.exportDetails
            java.util.List r6 = r6.getResolutionsList()
            r5.resolutions = r6
            java.util.ArrayList r6 = new java.util.ArrayList
            r6.<init>()
            r5.cache_name = r6
            java.lang.String r6 = "Regular"
            r5.res = r6
            helper.ExportDetails r6 = r5.exportDetails
            util.BitmapCache r6 = r6.getBitmapCache()
            r5.bitmapCache = r6
            r5.effectSelected = r1
            helper.ExportDetails r6 = helper.ExportDetails.getInstance()
            java.lang.String r6 = r6.getFragment_title()
            if (r6 == 0) goto L_0x025e
            helper.ExportDetails r6 = helper.ExportDetails.getInstance()
            java.lang.String r6 = r6.getFragment_title()
            r2 = -1
            int r3 = r6.hashCode()
            r4 = 14986017(0xe4ab21, float:2.0999883E-38)
            if (r3 == r4) goto L_0x01fa
            r4 = 1061320445(0x3f4276fd, float:0.7596281)
            if (r3 == r4) goto L_0x01f0
            r4 = 1441129992(0x55e5e608, float:3.15970174E13)
            if (r3 == r4) goto L_0x01e6
            goto L_0x0204
        L_0x01e6:
            java.lang.String r3 = "FilterFragment"
            boolean r6 = r6.equals(r3)
            if (r6 == 0) goto L_0x0204
            r6 = 1
            goto L_0x0205
        L_0x01f0:
            java.lang.String r3 = "EditorFragment"
            boolean r6 = r6.equals(r3)
            if (r6 == 0) goto L_0x0204
            r6 = 2
            goto L_0x0205
        L_0x01fa:
            java.lang.String r3 = "EffectFragment"
            boolean r6 = r6.equals(r3)
            if (r6 == 0) goto L_0x0204
            r6 = 0
            goto L_0x0205
        L_0x0204:
            r6 = -1
        L_0x0205:
            switch(r6) {
                case 0: goto L_0x0233;
                case 1: goto L_0x021e;
                case 2: goto L_0x0209;
                default: goto L_0x0208;
            }
        L_0x0208:
            goto L_0x025e
        L_0x0209:
            helper.ExportDetails r6 = helper.ExportDetails.getInstance()
            com.hqgames.pencil.sketch.photo.EffectFilterDetails r6 = r6.getEffectFilterDetails()
            if (r6 == 0) goto L_0x025e
            helper.ExportDetails r6 = helper.ExportDetails.getInstance()
            com.hqgames.pencil.sketch.photo.EffectFilterDetails r6 = r6.getEffectFilterDetails()
            r5.effectFilterDetails = r6
            goto L_0x025e
        L_0x021e:
            helper.ExportDetails r6 = helper.ExportDetails.getInstance()
            com.hqgames.pencil.sketch.photo.EffectFilterDetails r6 = r6.getEffectFilterDetails()
            if (r6 == 0) goto L_0x025e
            helper.ExportDetails r6 = helper.ExportDetails.getInstance()
            com.hqgames.pencil.sketch.photo.EffectFilterDetails r6 = r6.getEffectFilterDetails()
            r5.effectFilterDetails = r6
            goto L_0x025e
        L_0x0233:
            java.lang.String r6 = "MessageIntent "
            helper.ExportDetails r2 = helper.ExportDetails.getInstance()
            java.lang.String r2 = r2.getFragment_title()
            android.util.Log.d(r6, r2)
            helper.ExportDetails r6 = helper.ExportDetails.getInstance()
            com.hqgames.pencil.sketch.photo.EffectFilterDetails r6 = r6.getEffectFilterDetails()
            if (r6 == 0) goto L_0x025e
            helper.ExportDetails r6 = helper.ExportDetails.getInstance()
            com.hqgames.pencil.sketch.photo.EffectFilterDetails r6 = r6.getEffectFilterDetails()
            r5.effectFilterDetails = r6
            com.hqgames.pencil.sketch.photo.EffectFilterDetails r6 = r5.effectFilterDetails
            boolean r6 = r6.isEffectSelected()
            if (r6 == 0) goto L_0x025e
            r5.effectSelected = r0
        L_0x025e:
            boolean r6 = helper.Constants.REMOVE_ADS
            if (r6 != 0) goto L_0x0269
            boolean r6 = helper.Constants.EXPORT_SCREEN_BOTTOM_ADS
            if (r6 == 0) goto L_0x0269
            r5.initializeAds()
        L_0x0269:
            com.facebook.CallbackManager r6 = com.facebook.CallbackManager.Factory.create()
            r5.callbackManager = r6
            com.facebook.share.widget.ShareDialog r6 = new com.facebook.share.widget.ShareDialog
            r6.<init>((android.app.Activity) r5)
            r5.shareDialog = r6
            com.facebook.share.widget.ShareDialog r6 = r5.shareDialog
            com.facebook.CallbackManager r0 = r5.callbackManager
            com.hqgames.pencil.sketch.photo.ExportActivity$1 r2 = new com.hqgames.pencil.sketch.photo.ExportActivity$1
            r2.<init>()
            r6.registerCallback(r0, r2)
            r5.initList()
            java.lang.String r6 = "Regular"
            r5.res = r6
            java.lang.String r6 = r5.res
            r5.setLength(r6)
            helper.ExportDetails r6 = r5.exportDetails
            java.lang.String r0 = r5.cache_file_name
            r6.setCache_name(r0)
            helper.ExportDetails r6 = r5.exportDetails
            int r0 = r5.targetLength
            r6.setTargetLength(r0)
            com.hqgames.pencil.sketch.photo.ExportActivity$EffectApplyingTask r6 = new com.hqgames.pencil.sketch.photo.ExportActivity$EffectApplyingTask
            helper.ExportDetails r0 = r5.exportDetails
            r6.<init>(r5, r0, r5)
            java.lang.Void[] r0 = new java.lang.Void[r1]
            r6.execute(r0)
            return
        */
        super.onCreate(r6);
        throw new UnsupportedOperationException("Method not decompiled: com.hqgames.pencil.sketch.photo.ExportActivity.onCreate(android.os.Bundle):void");
    }

    public void initializeAds() {
        if (NativeAdUtil.getInstance().getAdmob_nativeAd() != null) {
            infilateAdmobNativeAd(NativeAdUtil.getInstance().getAdmob_nativeAd());
        } else if (NativeAdUtil.getInstance().getMopub_nativeAd() != null) {
           // inflateMopubNativeAd(NativeAdUtil.getInstance().getMopub_nativeAd());
        } else {
            this.moPubView.loadAd();
        }
    }

    public void infilateAdmobNativeAd(UnifiedNativeAd unifiedNativeAd) {
        View inflate = LayoutInflater.from(this).inflate(R.layout.admob_native_banner, (ViewGroup) null);
        populateUnifiedNativeAdView(unifiedNativeAd, (UnifiedNativeAdView) inflate.findViewById(R.id.admob_native));
        this.adContainer.addView(inflate);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.adContainer.getLayoutParams();
        layoutParams.height = -2;
        layoutParams.width = -2;
        this.adContainer.setLayoutParams(layoutParams);
    }

  /*  public void inflateMopubNativeAd(NativeAd nativeAd) {
        RelativeLayout relativeLayout = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.native_ad_banner, this.adContainer, false);
        nativeAd.renderAdView(relativeLayout);
        nativeAd.prepare(relativeLayout);
        this.adContainer.addView(relativeLayout);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.adContainer.getLayoutParams();
        layoutParams.height = -2;
        layoutParams.width = -2;
        this.adContainer.setLayoutParams(layoutParams);
    }
*/
    private void populateUnifiedNativeAdView(UnifiedNativeAd unifiedNativeAd, UnifiedNativeAdView unifiedNativeAdView) {
        unifiedNativeAdView.setHeadlineView(unifiedNativeAdView.findViewById(R.id.ad_headline));
        unifiedNativeAdView.setBodyView(unifiedNativeAdView.findViewById(R.id.ad_body));
        unifiedNativeAdView.setCallToActionView(unifiedNativeAdView.findViewById(R.id.native_ad_call_to_action));
        unifiedNativeAdView.setIconView(unifiedNativeAdView.findViewById(R.id.ad_app_icon));
        ((TextView) unifiedNativeAdView.getHeadlineView()).setText(unifiedNativeAd.getHeadline());
        ((TextView) unifiedNativeAdView.getBodyView()).setText(unifiedNativeAd.getBody());
        if (unifiedNativeAd.getCallToAction() == null) {
            unifiedNativeAdView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            unifiedNativeAdView.getCallToActionView().setVisibility(View.VISIBLE);
            ((Button) unifiedNativeAdView.getCallToActionView()).setText(unifiedNativeAd.getCallToAction());
        }
        if (unifiedNativeAd.getIcon() == null) {
            unifiedNativeAdView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) unifiedNativeAdView.getIconView()).setImageDrawable(unifiedNativeAd.getIcon().getDrawable());
            unifiedNativeAdView.getIconView().setVisibility(View.VISIBLE);
        }
        unifiedNativeAdView.setNativeAd(unifiedNativeAd);
    }

    public void ShareFacebook(Bitmap bitmap) {
        SharePhotoContent build = new SharePhotoContent.Builder().addPhoto(new SharePhoto.Builder().setBitmap(bitmap).build()).build();
        if (ShareDialog.canShow(ShareLinkContent.class)) {
            this.shareDialog.show(build);
        }
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        this.callbackManager.onActivityResult(i, i2, intent);
    }

    public void OnExportButtonClick(ExportOptionsView exportOptionsView) {
        if (exportOptionsView.isLocked() && exportOptionsView.getLockImage() == 0) {
            Log.d("OptionButton", "Locked " + String.valueOf(exportOptionsView.getLockImage()));
            initializeFeatureUnlockDialog();
        } else if (this.effectBitmap != null) {
            feature(exportOptionsView.getTitle());
        } else if (this.imageView == null || ((BitmapDrawable) this.imageView.getDrawable()).getBitmap() == null) {
            Toast.makeText(this, "Sorry , either the effect is not applied correctly or there might be some problem choosing resolution choose it again or apply the effect again.", Toast.LENGTH_SHORT).show();
        } else {
            this.effectBitmap = ((BitmapDrawable) this.imageView.getDrawable()).getBitmap();
            feature(exportOptionsView.getTitle());
        }
    }

    /* access modifiers changed from: package-private */
    @SuppressLint("WrongConstant")
    public void feature(String str) {
        File file;
        File file2;
        if (str.equals("Save")) {
            this.dialog_show = false;
            AppRater.initialize(this).IncrementEvent();
            SaveImage(this.effectBitmap);
        } else if (str.equals("Share")) {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("image/jpeg");
            intent.addFlags(1);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            this.effectBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            String str2 = "image-" + new Random().nextInt(10000) + ".jpg";
            if (Build.VERSION.SDK_INT >= 24) {
                file2 = new File(Environment.getExternalStorageDirectory(), str2);
                Log.d("SDKINT", "Greater 24");
            } else {
                file2 = new File(Environment.getExternalStorageDirectory() + File.separator + str2);
            }
            try {
                file2.createNewFile();
                new FileOutputStream(file2).write(byteArrayOutputStream.toByteArray());
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (Build.VERSION.SDK_INT >= 24) {
                Log.d("SDKINT", "Greater 24 4");
                if (Build.VERSION.SDK_INT >= 29) {
                    Intent intent2 = ShareCompat.IntentBuilder.from(this).setStream(FileProvider.getUriForFile(this, "com.hqgames.pencil.sketch.photo.provider", file2)).getIntent();
                    intent2.setData(FileProvider.getUriForFile(this, "com.hqgames.pencil.sketch.photo.provider", file2));
                    intent2.addFlags(FILL_IN_ACTION);
                    startActivity(Intent.createChooser(intent2, "Share Image"));
                } else {
                    intent.putExtra("android.intent.extra.STREAM", FileProvider.getUriForFile(App.getInstance().getApplicationContext(), "com.hqgames.pencil.sketch.photo.provider", file2));
                    startActivity(Intent.createChooser(intent, "Share Image"));
                }
            } else {
                intent.putExtra("android.intent.extra.STREAM", Uri.fromFile(file2));
                startActivity(Intent.createChooser(intent, "Share Image"));
            }
            if (((BitmapDrawable) this.imageView.getDrawable()).getBitmap() != null && ((BitmapDrawable) this.imageView.getDrawable()).getBitmap() == this.effectBitmap) {
                Bundle bundle = new Bundle();
                if (this.effectName != null) {
                    bundle.putString("image_share", this.effectName + " " + this.res);
                } else {
                    bundle.putString("image_share", "original");
                }
                FireBaseHelper.getInstance().LogEvent("Image_Share", bundle);
            }
        } else if (str.equals("Facebook")) {
            ShareFacebook(this.effectBitmap);
            Toast.makeText(this, "Please wait while uploading to facebook", Toast.LENGTH_SHORT).show();
            if (((BitmapDrawable) this.imageView.getDrawable()).getBitmap() != null && ((BitmapDrawable) this.imageView.getDrawable()).getBitmap() == this.effectBitmap) {
                if (this.effectName != null) {
                    Constants.EFFECT_NAME = this.effectName;
                } else {
                    Constants.EFFECT_NAME = "original";
                }
            }
            Constants.RESOLUTION = this.res;
            Bundle bundle2 = new Bundle();
            bundle2.putString("image_share", "facebook " + Constants.EFFECT_NAME + " " + Constants.RESOLUTION);
            FireBaseHelper.getInstance().LogEvent("Facebook_Share", bundle2);
        } else if (str.equals("Instagram")) {
            Intent intent3 = new Intent("android.intent.action.SEND");
            intent3.setType("image/jpeg");
            ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
            this.effectBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream2);
            if (Build.VERSION.SDK_INT >= 24) {
                file = new File(Utils.getCacheDirectory(this, Constants.CACHE_DIRECTORY).getPath() + File.separator + "Share1.jpg");
            } else {
                file = new File(Environment.getExternalStorageDirectory() + File.separator + "Share1.jpg");
            }
            try {
                file.createNewFile();
                new FileOutputStream(file).write(byteArrayOutputStream2.toByteArray());
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            if (Build.VERSION.SDK_INT >= 24) {
                intent3.addFlags(1);
                intent3.putExtra("android.intent.extra.STREAM", FileProvider.getUriForFile(this, "com.hqgames.pencil.sketch.photo.provider", file));
                intent3.setPackage("com.instagram.android");
            } else {
                intent3.putExtra("android.intent.extra.STREAM", Uri.parse("file:///sdcard/Share1.jpg"));
                intent3.setPackage("com.instagram.android");
            }
            startActivity(Intent.createChooser(intent3, "Share Image"));
            if (((BitmapDrawable) this.imageView.getDrawable()).getBitmap() != null && ((BitmapDrawable) this.imageView.getDrawable()).getBitmap() == this.effectBitmap) {
                Bundle bundle3 = new Bundle();
                if (this.effectName != null) {
                    bundle3.putString("image_share", "insta " + this.effectName + " " + this.res);
                } else {
                    bundle3.putString("image_share", "original");
                }
                FireBaseHelper.getInstance().LogEvent("Insta_Share", bundle3);
            }
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.resol_fourK:
                disableAll();
                this.four_k_effect.setBackgroundColor(Color.parseColor("#2196F3"));
                this.res = "4K";
                setLength(this.res);
                this.exportDetails.setCache_name(this.cache_file_name);
                this.exportDetails.setTargetLength(this.targetLength);
                new EffectApplyingTask(this, this.exportDetails, this).execute(new Void[0]);
                return;
            case R.id.resol_full_hd:
                disableAll();
                this.full_hd_effect.setBackgroundColor(Color.parseColor("#2196F3"));
                this.res = "Full Hd";
                setLength(this.res);
                this.exportDetails.setCache_name(this.cache_file_name);
                this.exportDetails.setTargetLength(this.targetLength);
                new EffectApplyingTask(this, this.exportDetails, this).execute(new Void[0]);
                return;
            case R.id.resol_hd:
                disableAll();
                this.hd_effect.setBackgroundColor(Color.parseColor("#2196F3"));
                this.res = "Hd";
                setLength(this.res);
                this.exportDetails.setCache_name(this.cache_file_name);
                this.exportDetails.setTargetLength(this.targetLength);
                new EffectApplyingTask(this, this.exportDetails, this).execute(new Void[0]);
                return;
            case R.id.resol_hdv:
                disableAll();
                this.hdv_effect.setBackgroundColor(Color.parseColor("#2196F3"));
                this.res = "HDV";
                setLength(this.res);
                this.exportDetails.setCache_name(this.cache_file_name);
                this.exportDetails.setTargetLength(this.targetLength);
                new EffectApplyingTask(this, this.exportDetails, this).execute(new Void[0]);
                return;
            case R.id.resol_norm:
                disableAll();
                this.regular_effect.setBackgroundColor(Color.parseColor("#2196F3"));
                this.res = "Regular";
                setLength(this.res);
                this.exportDetails.setCache_name(this.cache_file_name);
                this.exportDetails.setTargetLength(this.targetLength);
                new EffectApplyingTask(this, this.exportDetails, this).execute(new Void[0]);
                return;
            case R.id.resol_twoK:
                disableAll();
                this.two_k_effect.setBackgroundColor(Color.parseColor("#2196F3"));
                this.res = "2K";
                setLength(this.res);
                this.exportDetails.setCache_name(this.cache_file_name);
                this.exportDetails.setTargetLength(this.targetLength);
                new EffectApplyingTask(this, this.exportDetails, this).execute(new Void[0]);
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: package-private */
    public void disableAll() {
        this.regular_effect.setBackgroundColor(0);
        this.hd_effect.setBackgroundColor(0);
        this.hdv_effect.setBackgroundColor(0);
        this.full_hd_effect.setBackgroundColor(0);
        this.two_k_effect.setBackgroundColor(0);
        this.four_k_effect.setBackgroundColor(0);
    }

    private void setFeaturesCredits(int i, int i2) {
        this.itemsAdapter.SetCredits(0, i);
        this.itemsAdapter.SetCredits(1, i);
        this.itemsAdapter.SetCredits(2, i2);
        this.itemsAdapter.SetCredits(3, i2);
    }

    private void initializeFeatureUnlockDialog() {
        final Dialog dialog = new Dialog(this, R.style.VideAdDialogTheme);
        dialog.getWindow().setDimAmount(0.0f);
        dialog.setContentView(R.layout.feature_unlock_dialog_layout);
        Button button = (Button) dialog.findViewById(R.id.cancel_button);
        ((TextView) dialog.findViewById(R.id.title_text)).setText("Hd Resolutions Locked");
        button.setText(getString(R.string.cancel));
        ((TextView) dialog.findViewById(R.id.msg_text)).setText("HD resolutions are locked to unlock them please purchase HD Resolution pack and unlock all Hd & Ultra Hd Resolutions (Full Hd , 2K , 4K) and save or share your images in Hd resolutions");
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setCancelable(true);
        dialog.show();
    }

    public void OnTaskComplete() {
        if (this.effectFilterDetails == null || ((!this.effectSelected || !this.effectFilterDetails.isEffectSelected() || this.effectFilterDetails.getEffectName() == null) && ((!this.effectFilterDetails.isEffectSelected() || !this.effectFilterDetails.isEffecttofilter()) && (!this.effectFilterDetails.isEffectSelected() || !this.effectFilterDetails.isEditorEffectSelected().booleanValue())))) {
            this.itemsAdapter.UnLock2(this.res, "Save");
            this.itemsAdapter.UnLock2(this.res, "Share");
            this.itemsAdapter.UnLock2(this.res, "Facebook");
            this.itemsAdapter.UnLock2(this.res, "Instagram");
            this.itemsAdapter.RemoveBlockage();
        } else {
            if (!this.res.equals("Full Hd") && !this.res.equals("2K") && !this.res.equals("4K")) {
                this.itemsAdapter.UnLock2(this.res, "Save");
                this.itemsAdapter.UnLock2(this.res, "Share");
                this.itemsAdapter.UnLock2(this.res, "Facebook");
                this.itemsAdapter.UnLock2(this.res, "Instagram");
                this.itemsAdapter.RemoveBlockage();
            } else if (!Constants.HD_PACK_PURCHASED) {
                this.itemsAdapter.ApplyBlockage(0, 0, false);
            } else {
                this.itemsAdapter.UnLock(this.res, "Save");
                this.itemsAdapter.UnLock(this.res, "Share");
                this.itemsAdapter.UnLock(this.res, "Facebook");
                this.itemsAdapter.UnLock(this.res, "Instagram");
                this.itemsAdapter.RemoveBlockage();
            }
            if (this.effectFilterDetails != null && this.effectFilterDetails.isEffectSelected() && this.effectFilterDetails.getEffectName() != null) {
                this.effectName = this.effectFilterDetails.getEffectName();
            } else if (this.effectFilterDetails != null && this.effectFilterDetails.isFilterSelected()) {
                this.effectName = "Filter";
            }
        }
        checkAd();
    }

    /* access modifiers changed from: package-private */
    public void checkAd() {
        if (!Constants.REMOVE_ADS && Constants.START_UP_AD && AdUtil.getInstance().isStartUpAdAvailable()) {
            Constants.START_UP_AD = false;
            if (AdUtil.getInstance().isStartUpAdAvailable()) {
                AdUtil.getInstance().ShowStartUpInterstitial();
            }
        }
    }

    public class ExportOptionsItemsAdapter extends RecyclerView.Adapter {
        /* access modifiers changed from: private */
        public ExportOptionListener optionSelected;
        private List<Object> viewElements;

        public ExportOptionsItemsAdapter(List<Object> list, ExportOptionListener exportOptionListener) {
            this.viewElements = list;
            this.optionSelected = exportOptionListener;
        }

        public void RemoveBlockage() {
            for (int i = 0; i < this.viewElements.size(); i++) {
                if (this.viewElements.get(i) instanceof ExportOptionsView) {
                    ExportOptionsView exportOptionsView = (ExportOptionsView) this.viewElements.get(i);
                    HashMap<String, String> hashMap = ExportActivity.this.unlockFeatureList;
                    if (hashMap.containsKey(ExportActivity.this.res + exportOptionsView.getTitle())) {
                        HashMap<String, String> hashMap2 = ExportActivity.this.unlockFeatureList;
                        if (hashMap2.get(ExportActivity.this.res + exportOptionsView.getTitle()).equals(exportOptionsView.getTitle())) {
                            exportOptionsView.setLocked(false);
                            exportOptionsView.setCredits((String) null);
                            exportOptionsView.setLockImage(1);
                            Log.d("removeblockage 0-3", "featurelist");
                            notifyItemChanged(i);
                            Log.d("CheckExport", "remove");
                        }
                    }
                    exportOptionsView.setLockImage(1);
                    exportOptionsView.setLocked(true);
                    notifyItemChanged(i);
                    Log.d("CheckExport", "remove");
                }
            }
        }

        @NonNull
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            if (i == ExportActivity.this.AD_TYPE) {
                return new AdViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.export_button_ad, viewGroup, false));
            }
            return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.export_bottom_view, viewGroup, false));
        }

        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            int itemViewType = getItemViewType(i);
            if (itemViewType == ExportActivity.this.AD_TYPE) {
                populateNativeAdView((UnifiedNativeAd) this.viewElements.get(i), ((AdViewHolder) viewHolder).getAdView());
                Log.d("ExportActivity", "ExportADButton");
            } else if (itemViewType == ExportActivity.this.CONTENT_TYPE) {
                Log.d("ExportActivity", "ExportButton");
                final ExportOptionsView exportOptionsView = (ExportOptionsView) this.viewElements.get(i);
                MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
                if (exportOptionsView.getCredits() != null) {
                    if (myViewHolder.textLayout.getVisibility() != View.VISIBLE) {
                        myViewHolder.textLayout.setVisibility(View.VISIBLE);
                    }
                    myViewHolder.credit.setText(exportOptionsView.getCredits());
                } else {
                    if (myViewHolder.textLayout.getVisibility() == View.VISIBLE) {
                        myViewHolder.textLayout.setVisibility(View.GONE);
                    }
                    myViewHolder.credit.setText("");
                }
                myViewHolder.optionName.setText(exportOptionsView.getOptionName());
                if (exportOptionsView.getImgId() != 0) {
                    myViewHolder.icon.setImageResource(exportOptionsView.getImgId());
                }
                if (exportOptionsView.getLockImage() != 0) {
                    myViewHolder.lock_image.setVisibility(View.GONE);
                    Log.d("ApplyBlockage image", "gone");
                } else {
                    myViewHolder.lock_image.setVisibility(View.VISIBLE);
                    Log.d("ApplyBlockage image", "visible");
                }
                myViewHolder.layout.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        ExportOptionsItemsAdapter.this.optionSelected.OnExportButtonClick(exportOptionsView);
                    }
                });
            }
        }

        public void UnLock(String str, String str2) {
            for (int i = 0; i < this.viewElements.size(); i++) {
                if (this.viewElements.get(i) instanceof ExportOptionsView) {
                    ExportOptionsView exportOptionsView = (ExportOptionsView) this.viewElements.get(i);
                    if (exportOptionsView.getTitle().equals(str2)) {
                        exportOptionsView.setLocked(false);
                        exportOptionsView.setCredits((String) null);
                        HashMap<String, String> hashMap = ExportActivity.this.unlockFeatureList;
                        hashMap.put(str + str2, str2);
                        notifyItemChanged(i);
                    }
                }
            }
        }

        public void SetCredits(int i, int i2) {
            notifyItemChanged(i);
        }

        public void UnLock2(String str, String str2) {
            for (int i = 0; i < this.viewElements.size(); i++) {
                if (this.viewElements.get(i) instanceof ExportOptionsView) {
                    ExportOptionsView exportOptionsView = (ExportOptionsView) this.viewElements.get(i);
                    if (exportOptionsView.getTitle().equals(str2)) {
                        exportOptionsView.setLocked(false);
                        exportOptionsView.setCredits((String) null);
                        HashMap<String, String> hashMap = ExportActivity.this.unlockFeatureList;
                        hashMap.put(str + str2, str2);
                        notifyItemChanged(i);
                    }
                }
            }
        }

        public void ApplyBlockage(int i, int i2, boolean z) {
            for (int i3 = 0; i3 < this.viewElements.size(); i3++) {
                Log.d("ApplyBlockage size", String.valueOf(this.viewElements.size()));
                if (this.viewElements.get(i3) instanceof ExportOptionsView) {
                    ExportOptionsView exportOptionsView = (ExportOptionsView) this.viewElements.get(i3);
                    if (i3 < 2) {
                        exportOptionsView.setLockImage(0);
                        exportOptionsView.setCredits(String.valueOf("Locked"));
                        exportOptionsView.setLocked(true);
                        HashMap<String, String> hashMap = ExportActivity.this.unlockFeatureList;
                        if (hashMap.containsKey(ExportActivity.this.res + exportOptionsView.getTitle())) {
                            HashMap<String, String> hashMap2 = ExportActivity.this.unlockFeatureList;
                            if (hashMap2.get(ExportActivity.this.res + exportOptionsView.getTitle()).equals(exportOptionsView.getTitle())) {
                                exportOptionsView.setLocked(false);
                                exportOptionsView.setCredits((String) null);
                                exportOptionsView.setLockImage(1);
                                Log.d("ApplyBlockage 0-1", "featurelist");
                            }
                        }
                    } else {
                        exportOptionsView.setLockImage(0);
                        exportOptionsView.setCredits(String.valueOf("Locked"));
                        exportOptionsView.setLocked(true);
                        Log.d("creditsupdate", "ApplyBlockage 0-1 " + String.valueOf(i));
                        HashMap<String, String> hashMap3 = ExportActivity.this.unlockFeatureList;
                        if (hashMap3.containsKey(ExportActivity.this.res + exportOptionsView.getTitle())) {
                            HashMap<String, String> hashMap4 = ExportActivity.this.unlockFeatureList;
                            if (hashMap4.get(ExportActivity.this.res + exportOptionsView.getTitle()).equals(exportOptionsView.getTitle())) {
                                exportOptionsView.setLocked(false);
                                exportOptionsView.setCredits((String) null);
                                exportOptionsView.setLockImage(1);
                                Log.d("ApplyBlockage 0-1", "featurelist");
                            }
                        }
                    }
                }
                notifyItemChanged(i3);
            }
            Log.d("CheckExport", "apply bloc all");
        }

        public int getItemCount() {
            return this.viewElements.size();
        }

        public int getItemViewType(int i) {
            Log.d("ExportActivity", "apply bloc all");
            Object obj = this.viewElements.get(i);
            if (obj instanceof UnifiedNativeAd) {
                return ExportActivity.this.AD_TYPE;
            }
            if (!(obj instanceof NativeAd)) {
                return ExportActivity.this.CONTENT_TYPE;
            }
            Log.d("ViewType", "unified");
            return ExportActivity.this.MOPUB_VIEW;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView credit;
            ImageView icon;
            RelativeLayout layout;
            ImageView lock_image;
            TextView optionName;
            RelativeLayout textLayout;

            public MyViewHolder(View view) {
                super(view);
                this.textLayout = (RelativeLayout) view.findViewById(R.id.text_layout);
                this.credit = (TextView) view.findViewById(R.id.credit_amount);
                this.icon = (ImageView) view.findViewById(R.id.feature_icon);
                this.layout = (RelativeLayout) view.findViewById(R.id.outer);
                this.lock_image = (ImageView) view.findViewById(R.id.lock);
                this.optionName = (TextView) view.findViewById(R.id.option_name);
                this.lock_image.setVisibility(View.VISIBLE);
                if (Constants.CUSTOM_EXPORT_FUNTION_BUTTON_ENABLED && Constants.EXPORT_FUNCTION_BUTTON_COLOR != null) {
                    this.textLayout.setBackgroundColor(Color.parseColor(Constants.EXPORT_FUNCTION_BUTTON_COLOR));
                }
            }
        }

        public class AdViewHolder extends RecyclerView.ViewHolder {
            public UnifiedNativeAdView adView;

            AdViewHolder(View view) {
                super(view);
                this.adView = (UnifiedNativeAdView) view.findViewById(R.id.ad_view);
            }

            public UnifiedNativeAdView getAdView() {
                return this.adView;
            }
        }

        private void populateNativeAdView(UnifiedNativeAd unifiedNativeAd, UnifiedNativeAdView unifiedNativeAdView) {
            unifiedNativeAdView.setHeadlineView(unifiedNativeAdView.findViewById(R.id.native_text));
            unifiedNativeAdView.setCallToActionView(unifiedNativeAdView.findViewById(R.id.native_text));
            unifiedNativeAdView.setIconView(unifiedNativeAdView.findViewById(R.id.native_image));
            ((TextView) unifiedNativeAdView.getHeadlineView()).setText(unifiedNativeAd.getHeadline());
            NativeAd.Image icon = unifiedNativeAd.getIcon();
            if (icon == null) {
                unifiedNativeAdView.getIconView().setVisibility(View.INVISIBLE);
            } else {
                ((ImageView) unifiedNativeAdView.getIconView()).setImageDrawable(icon.getDrawable());
                unifiedNativeAdView.getIconView().setVisibility(View.VISIBLE);
            }
            unifiedNativeAdView.setNativeAd(unifiedNativeAd);
        }
    }

    private void initList() {
        if (this.effectFilterDetails == null || this.effectFilterDetails.getEffectName() == null) {
            for (int i = 0; i < this.resolutions.size(); i++) {
                if (this.resolutions.get(i).getImage_width() == 1280) {
                    this.hd_effect.setVisibility(View.VISIBLE);
                }
                if (this.resolutions.get(i).getImage_width() == 1440) {
                    this.hdv_effect.setVisibility(View.VISIBLE);
                }
                if (this.resolutions.get(i).getImage_width() == 1920) {
                    this.full_hd_effect.setVisibility(View.VISIBLE);
                }
                if (this.resolutions.get(i).getImage_width() == 2048) {
                    this.two_k_effect.setVisibility(View.VISIBLE);
                }
                if (this.resolutions.get(i).getImage_width() == 3840) {
                    this.four_k_effect.setVisibility(View.VISIBLE);
                }
            }
        } else if (!this.effectFilterDetails.getEffectName().equals("Water_Color") && !this.effectFilterDetails.getEffectName().equals("Color Thick Edge") && !this.effectFilterDetails.getEffectName().equals("Color Medium Edge") && !this.effectFilterDetails.getEffectName().equals("Color Thin Edge")) {
            for (int i2 = 0; i2 < this.resolutions.size(); i2++) {
                if (this.resolutions.get(i2).getImage_width() == 1280) {
                    this.hd_effect.setVisibility(View.VISIBLE);
                }
                if (this.resolutions.get(i2).getImage_width() == 1440) {
                    this.hdv_effect.setVisibility(View.VISIBLE);
                }
                if (this.resolutions.get(i2).getImage_width() == 1920) {
                    this.full_hd_effect.setVisibility(View.VISIBLE);
                }
                if (this.resolutions.get(i2).getImage_width() == 2048) {
                    this.two_k_effect.setVisibility(View.VISIBLE);
                }
                if (this.resolutions.get(i2).getImage_width() == 3840) {
                    this.four_k_effect.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0061  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x006a  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0073  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x007c  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0085  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x008e  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00a5  */
    /* JADX WARNING: Removed duplicated region for block: B:42:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setLength(java.lang.String r3) {
        /*
            r2 = this;
            int r0 = r3.hashCode()
            r1 = -1543850116(0xffffffffa3fab77c, float:-2.7182763E-17)
            if (r0 == r1) goto L_0x0052
            r1 = 1625(0x659, float:2.277E-42)
            if (r0 == r1) goto L_0x0048
            r1 = 1687(0x697, float:2.364E-42)
            if (r0 == r1) goto L_0x003e
            r1 = 2332(0x91c, float:3.268E-42)
            if (r0 == r1) goto L_0x0034
            r1 = 71386(0x116da, float:1.00033E-40)
            if (r0 == r1) goto L_0x002a
            r1 = 1153349677(0x44beb82d, float:1525.7555)
            if (r0 == r1) goto L_0x0020
            goto L_0x005c
        L_0x0020:
            java.lang.String r0 = "Full Hd"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x005c
            r0 = 3
            goto L_0x005d
        L_0x002a:
            java.lang.String r0 = "HDV"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x005c
            r0 = 2
            goto L_0x005d
        L_0x0034:
            java.lang.String r0 = "Hd"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x005c
            r0 = 1
            goto L_0x005d
        L_0x003e:
            java.lang.String r0 = "4K"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x005c
            r0 = 5
            goto L_0x005d
        L_0x0048:
            java.lang.String r0 = "2K"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x005c
            r0 = 4
            goto L_0x005d
        L_0x0052:
            java.lang.String r0 = "Regular"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x005c
            r0 = 0
            goto L_0x005d
        L_0x005c:
            r0 = -1
        L_0x005d:
            switch(r0) {
                case 0: goto L_0x008e;
                case 1: goto L_0x0085;
                case 2: goto L_0x007c;
                case 3: goto L_0x0073;
                case 4: goto L_0x006a;
                case 5: goto L_0x0061;
                default: goto L_0x0060;
            }
        L_0x0060:
            goto L_0x0096
        L_0x0061:
            r0 = 3840(0xf00, float:5.381E-42)
            r2.targetLength = r0
            java.lang.String r0 = "fourk"
            r2.cache_file_name = r0
            goto L_0x0096
        L_0x006a:
            r0 = 2048(0x800, float:2.87E-42)
            r2.targetLength = r0
            java.lang.String r0 = "twok"
            r2.cache_file_name = r0
            goto L_0x0096
        L_0x0073:
            r0 = 1920(0x780, float:2.69E-42)
            r2.targetLength = r0
            java.lang.String r0 = "full_hd"
            r2.cache_file_name = r0
            goto L_0x0096
        L_0x007c:
            r0 = 1440(0x5a0, float:2.018E-42)
            r2.targetLength = r0
            java.lang.String r0 = "hdv"
            r2.cache_file_name = r0
            goto L_0x0096
        L_0x0085:
            r0 = 1280(0x500, float:1.794E-42)
            r2.targetLength = r0
            java.lang.String r0 = "hd"
            r2.cache_file_name = r0
            goto L_0x0096
        L_0x008e:
            r0 = 960(0x3c0, float:1.345E-42)
            r2.targetLength = r0
            java.lang.String r0 = "hd"
            r2.cache_file_name = r0
        L_0x0096:
            java.lang.String r0 = "Resolution "
            java.lang.String r1 = r2.cache_file_name
            android.util.Log.d(r0, r1)
            java.lang.String r0 = "Regular"
            boolean r3 = r3.equals(r0)
            if (r3 != 0) goto L_0x00ac
            java.util.List<java.lang.String> r3 = r2.cache_name
            java.lang.String r0 = r2.cache_file_name
            r3.add(r0)
        L_0x00ac:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.hqgames.pencil.sketch.photo.ExportActivity.setLength(java.lang.String):void");
    }

    private class EffectApplyingTask extends AsyncTask<Void, Void, Void> {
        ExportDetails exportDetails;
        boolean isPathnull = false;
        int length;
        Context mContext;
        private File originalImag;
        String path;
        Bitmap resize = null;
        TaskCompleteListener taskCompleteListener;

        public EffectApplyingTask(Context context, ExportDetails exportDetails2, TaskCompleteListener taskCompleteListener2) {
            this.exportDetails = exportDetails2;
            this.length = this.exportDetails.getTargetLength();
            this.path = this.exportDetails.getPath();
            this.taskCompleteListener = taskCompleteListener2;
            this.mContext = context;
            this.isPathnull = false;
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            super.onPreExecute();
            if (this.path != null) {
                this.originalImag = new File(this.path);
            }
            if (ExportActivity.this.gpuImg == null) {
                ExportActivity.this.gpuImg = new GPUImage(ExportActivity.this);
            }
            Dialog unused = ExportActivity.this.effectLoadingDialog = new Dialog(this.mContext, R.style.EffectDialogTheme);
            ExportActivity.this.effectLoadingDialog.requestWindowFeature(1);
            ExportActivity.this.effectLoadingDialog.setContentView(R.layout.effect_loading_dialog);
            ExportActivity.this.effectLoadingDialog.setCancelable(false);
            ExportActivity.this.effectLoadingDialog.show();
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void voidR) {
            super.onPostExecute(voidR);
            try {
                if (ExportActivity.this.effectFilterDetails != null) {
                    ExportActivity.this.effectBitmap = (Bitmap) new EffectFilterAsyncTask(this.resize, ExportActivity.this.effectFilterDetails.getEffect(), ExportActivity.this.effectFilterDetails.getEffectName(), ExportActivity.this.effectFilterDetails, ExportActivity.this.gpuImg, (Dialog) null, this.taskCompleteListener).execute(new Void[0]).get();
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
            ExportActivity.this.imageView.setImageBitmap(ExportActivity.this.effectBitmap);
            if (ExportActivity.this.effectLoadingDialog.isShowing()) {
                ExportActivity.this.effectLoadingDialog.dismiss();
            }
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(Void... voidArr) {
            if (!ExportActivity.this.res.equals("Regular") && ExportActivity.this.bitmapCache != null && ExportActivity.this.bitmapCache.getCacheBitmap(this.exportDetails.getCache_name()) == null) {
                try {
                    this.resize = new Resizer(ExportActivity.this).setTargetLength(this.length).setSourceImage(this.originalImag).getResizedBitmap();
                    return null;
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            } else if (ExportActivity.this.bitmapCache != null && ExportActivity.this.bitmapCache.getCacheBitmap(Constants.ORIGINAL_IMAGE) != null) {
                this.resize = ExportActivity.this.bitmapCache.getCacheBitmap(Constants.ORIGINAL_IMAGE);
                return null;
            } else if (ExportActivity.this.fileUri != null) {
                this.resize = BitmapFactory.decodeFile(ImageFilePath.getPath(ExportActivity.this, ExportActivity.this.fileUri));
                if (ExportActivity.this.bitmapCache == null || this.resize == null) {
                    return null;
                }
                ExportActivity.this.bitmapCache.addBitmapToCache(Constants.ORIGINAL_IMAGE, this.resize);
                return null;
            } else if (Constants.FILE_URI == null) {
                return null;
            } else {
                this.resize = BitmapFactory.decodeFile(ImageFilePath.getPath(ExportActivity.this, Constants.FILE_URI));
                if (ExportActivity.this.bitmapCache == null || this.resize == null) {
                    return null;
                }
                ExportActivity.this.bitmapCache.addBitmapToCache(Constants.ORIGINAL_IMAGE, this.resize);
                return null;
            }
        }
    }

    private void SaveImage(Bitmap bitmap) {
        Constants.AD_ON_SAVING = true;
        Constants.SAVE_INCREMENT++;
        File file = new File(Environment.getExternalStorageDirectory().toString() + "/pencil_photo_sketch");
        file.mkdirs();
        File file2 = new File(file, "Image-" + new Random().nextInt(10000) + ".jpg");
        if (file2.exists()) {
            file2.delete();
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.parse("file://" + file.getAbsolutePath())));
            MediaScannerConnection.scanFile(this, new String[]{file2.toString()}, (String[]) null, new MediaScannerConnection.OnScanCompletedListener() {
                public void onScanCompleted(String str, Uri uri) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(this, "Image Saved Successfully to" + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        if (AppRater.initialize(this).isOverIncrementCount() && !SharedPreferencesManager.getSomeBoolValue(this, Constants.APP_RATED) && !SharedPreferencesManager.HasKey(this, Constants.EXPORT_RATING_VIEW_LIMIT_REACHED)) {
            Log.d("RateDialog", "Show");
            SharedPreferencesManager.setSomeBoolValue(this, Constants.EVENT_RATE_DIALOG_SHOWED, true);
            this.dialog_show = true;
            initializeRateDialog();
        }
        if (!this.dialog_show.booleanValue() && !Constants.REMOVE_ADS && Constants.SAVE_INCREMENT == Constants.SAVE_AD_COUNT) {
            if (MainActivity.isActivityVisible() && AdUtil.getInstance().isSaveInterstitialAvailable() && Constants.SAVE_INTERSTITIAL) {
                AdUtil.getInstance().ShowSaveInterstitial();
            }
            Bundle bundle = new Bundle();
            bundle.putString("AdNetwork", "ImageSaveAd");
            FireBaseHelper.getInstance().LogEvent("Ad_Analytics", bundle);
        }
        if (bitmap == this.effectBitmap) {
            Bundle bundle2 = new Bundle();
            if (this.effectName != null) {
                bundle2.putString("image_saved", this.effectName + " " + this.res);
            } else {
                bundle2.putString("image_saved", "original");
            }
            FireBaseHelper.getInstance().LogEvent("Image_Analytics", bundle2);
        }
    }

    public void initializeRateDialog() {
        Dialog dialog = new Dialog(this, R.style.RateUsDialogTheme);
        dialog.getWindow().setDimAmount(0.0f);
        dialog.setContentView(R.layout.rate_dialog);
        RelativeLayout relativeLayout = (RelativeLayout) dialog.findViewById(R.id.background);
        GradientDrawable gradientDrawable = new GradientDrawable();
        this.closeButtonText = getString(R.string.not);
        this.okButtonText = getString(R.string.ok);
        this.closeButtonText2 = getString(R.string.no_thanks);
        this.okButtonText2 = getString(R.string.ok_sure);
        if (Constants.CUSTOM_EXPORT_RATE_BUTTON_OK_TEXT != null && !Constants.CUSTOM_EXPORT_RATE_BUTTON_OK_TEXT.equals("null")) {
            this.okButtonText = Constants.CUSTOM_EXPORT_RATE_BUTTON_OK_TEXT;
        }
        if (Constants.CUSTOM_EXPORT_RATE_BUTTON_CLOSE_TEXT != null && !Constants.CUSTOM_EXPORT_RATE_BUTTON_CLOSE_TEXT.equals("null")) {
            this.closeButtonText = Constants.CUSTOM_EXPORT_RATE_BUTTON_CLOSE_TEXT;
        }
        if (Constants.CUSTOM_EXPORT_RATE_BUTTON_OK_TEXT_2 != null && !Constants.CUSTOM_EXPORT_RATE_BUTTON_OK_TEXT_2.equals("null")) {
            this.okButtonText2 = Constants.CUSTOM_EXPORT_RATE_BUTTON_OK_TEXT_2;
        }
        if (Constants.CUSTOM_EXPORT_RATE_BUTTON_CLOSE_TEXT_2 != null && !Constants.CUSTOM_EXPORT_RATE_BUTTON_CLOSE_TEXT_2.equals("null")) {
            this.closeButtonText2 = Constants.CUSTOM_EXPORT_RATE_BUTTON_CLOSE_TEXT_2;
        }
        Button button = (Button) dialog.findViewById(R.id.yes);
        Button button2 = (Button) dialog.findViewById(R.id.no);
        TextView textView = (TextView) dialog.findViewById(R.id.rating_text);
        TextView textView2 = (TextView) dialog.findViewById(R.id.dialog_title);
        View findViewById = dialog.findViewById(R.id.divider);
        ScaleRatingBar scaleRatingBar = (ScaleRatingBar) dialog.findViewById(R.id.simpleRatingBar);
        LinearLayout linearLayout = (LinearLayout) dialog.findViewById(R.id.layout);
        final LinearLayout linearLayout2 = linearLayout;
        final TextView textView3 = textView;
        final Button button3 = button;
        Dialog dialog2 = dialog;
        final Button button4 = button2;
        BaseRatingBar.OnRatingChangeListener r0 = new BaseRatingBar.OnRatingChangeListener() {
            public void onRatingChange(BaseRatingBar baseRatingBar, float f, boolean z) {
                if (f >= 4.0f) {
                    try {
                        ExportActivity exportActivity = ExportActivity.this;
                        exportActivity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + ExportActivity.this.getPackageName())));
                    } catch (ActivityNotFoundException unused) {
                        ExportActivity exportActivity2 = ExportActivity.this;
                        exportActivity2.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + ExportActivity.this.getPackageName())));
                    }
                    baseRatingBar.setVisibility(View.GONE);
                    linearLayout2.setVisibility(View.VISIBLE);
                    textView3.setText(ExportActivity.this.getString(R.string.thanks_text));
                    button3.setText(ExportActivity.this.getString(R.string.close));
                    button4.setEnabled(false);
                    Bundle bundle = new Bundle();
                    bundle.putString("User_Experience", "Export_Rate_4_5");
                    FireBaseHelper.getInstance().LogEvent("Rate_Analytics", bundle);
                    return;
                }
                baseRatingBar.setVisibility(View.GONE);
                linearLayout2.setVisibility(View.VISIBLE);
                textView3.setText(ExportActivity.this.getString(R.string.thanks_text));
                button3.setText(ExportActivity.this.getString(R.string.close));
                button4.setEnabled(false);
                Bundle bundle2 = new Bundle();
                bundle2.putString("User_Experience", "Export_Rate_3");
                FireBaseHelper.getInstance().LogEvent("Rate_Analytics", bundle2);
            }
        };
        BaseRatingBar.OnRatingChangeListener r9 = r0;

        scaleRatingBar.setOnRatingChangeListener(r9);
        if (Constants.CUSTOM_EXPORT_RATE_DIALOG_TITLE_TEXT == null || Constants.CUSTOM_EXPORT_RATE_DIALOG_TITLE_TEXT.equals("null")) {
            textView2.setText(R.string.app_name);
        } else {
            textView2.setText(Constants.CUSTOM_EXPORT_RATE_DIALOG_TITLE_TEXT);
        }
        if (Constants.CUSTOM_EXPORT_RATE_DIALOG_TEXT == null || Constants.CUSTOM_EXPORT_RATE_DIALOG_TEXT.equals("null")) {
            textView.setText(R.string.custom_rate_dialog_title);
        } else {
            textView.setText(Constants.CUSTOM_EXPORT_RATE_DIALOG_TEXT);
        }
        button2.setText(this.closeButtonText);
        button.setText("Yes!");
        if (Constants.CUSTOM_EXPORT_RATE_VIEW) {
            if (Constants.EXPORT_RATE_VIEW_OK_BUTTON_COLOR != null) {
                GradientDrawable gradientDrawable2 = new GradientDrawable();
                gradientDrawable2.setColor(Color.parseColor(Constants.EXPORT_RATE_VIEW_OK_BUTTON_COLOR));
                gradientDrawable2.setStroke(dpToPx(2), Color.parseColor(Constants.EXPORT_RATE_VIEW_OK_BUTTON_STROKE));
                gradientDrawable2.setCornerRadius((float) dpToPx(2));
                button.setBackground(gradientDrawable2);
                button.setTextColor(Color.parseColor(Constants.EXPORT_RATE_VIEW_OK_BUTTON_TEXT_COLOR));
            }
            if (Constants.EXPORT_RATE_VIEW_NO_BUTTON_COLOR != null) {
                GradientDrawable gradientDrawable3 = new GradientDrawable();
                gradientDrawable3.setColor(Color.parseColor(Constants.EXPORT_RATE_VIEW_NO_BUTTON_COLOR));
                gradientDrawable3.setStroke(dpToPx(2), Color.parseColor(Constants.EXPORT_RATE_VIEW_NO_BUTTON_STROKE));
                gradientDrawable3.setCornerRadius((float) dpToPx(2));
                button2.setBackground(gradientDrawable3);
                button2.setTextColor(Color.parseColor(Constants.EXPORT_RATE_VIEW_NO_BUTTON_TEXT_COLOR));
            }
            if (Constants.EXPORT_RATE_VIEW_TEXT_COLOR != null) {
                textView.setTextColor(Color.parseColor(Constants.EXPORT_RATE_VIEW_TEXT_COLOR));
                textView2.setTextColor(Color.parseColor(Constants.EXPORT_RATE_VIEW_TEXT_COLOR));
            }
            if (Constants.EXPORT_RATE_VIEW_DIVIDER_COLOR != null) {
                findViewById.setBackgroundColor(Color.parseColor(Constants.EXPORT_RATE_VIEW_DIVIDER_COLOR));
            }
            if (!(Constants.EXPORT_RATE_VIEW_BG_COLOR == null || Constants.CUSTOM_EXPORT_RATE_DIALOG_STROKE_COLOR == null)) {
                gradientDrawable.setColor(Color.parseColor(Constants.EXPORT_RATE_VIEW_BG_COLOR));
                gradientDrawable.setStroke(dpToPx(2), Color.parseColor(Constants.CUSTOM_EXPORT_RATE_DIALOG_STROKE_COLOR));
                relativeLayout.setBackground(gradientDrawable);
            }
        }
        Dialog dialog3 = dialog2;
        dialog3.setCancelable(false);
        if (SharedPreferencesManager.HasKey(this, Constants.NEW_EVENT_CALLED)) {
            Bundle bundle = new Bundle();
            bundle.putString("User_Experience", "Export_Fragment_S_T-RateView_Show");
            FireBaseHelper.getInstance().LogEvent("Rate_Analytics", bundle);
        }
        final Button button5 = button;
        final TextView textView4 = textView;
        final ScaleRatingBar scaleRatingBar2 = scaleRatingBar;
        final LinearLayout linearLayout3 = linearLayout;
        final Button button6 = button2;

        final Dialog dialog4 = dialog3;
        View.OnClickListener r02 = new View.OnClickListener() {
            public void onClick(View view) {
                if (button5.getText().equals("Yes!")) {
                    if (Constants.CUSTOM_EXPORT_RATE_DIALOG_TEXT_2 == null || Constants.CUSTOM_EXPORT_RATE_DIALOG_TEXT_2.equals("null")) {
                        textView4.setText(R.string.rate_text);
                    } else {
                        textView4.setText(Constants.CUSTOM_EXPORT_RATE_DIALOG_TEXT_2);
                    }
                    if (Constants.EXPORT_RATING_BAR) {
                        scaleRatingBar2.setVisibility(View.VISIBLE);
                        linearLayout3.setVisibility(View.GONE);
                    }
                    button5.setText(ExportActivity.this.okButtonText2);
                    button6.setText(ExportActivity.this.closeButtonText2);
                    if (!SharedPreferencesManager.HasKey(ExportActivity.this, Constants.NEW_EVENT_CALLED)) {
                        Bundle bundle = new Bundle();
                        bundle.putString("User_Experience", "Export_Fragment-Enjoyed");
                        FireBaseHelper.getInstance().LogEvent("Rate_Analytics", bundle);
                    } else {
                        Bundle bundle2 = new Bundle();
                        bundle2.putString("User_Experience", "Export_Fragment_S_T-Enjoyed");
                        FireBaseHelper.getInstance().LogEvent("Rate_Analytics", bundle2);
                    }
                    ExportActivity.this.enjoyPencilPhotoSketch_2 = true;
                } else if (button5.getText().equals(ExportActivity.this.okButtonText2)) {
                    if (ExportActivity.this.feedback_2) {
                        Intent intent = new Intent("android.intent.action.SEND");
                        intent.putExtra("android.intent.extra.EMAIL", new String[]{"hqgames1080@gmail.com"});
                        intent.putExtra("android.intent.extra.SUBJECT", "Pencil Photo Sketch:FeedBack");
                        intent.setType("message/rfc822");
                        ExportActivity.this.startActivity(Intent.createChooser(intent, "Select Email Sending App :"));
                        textView4.setText(ExportActivity.this.getString(R.string.thanks_text));
                        button5.setText(ExportActivity.this.getString(R.string.close));
                        button6.setEnabled(false);
                        if (SharedPreferencesManager.HasKey(ExportActivity.this, Constants.NEW_EVENT_CALLED) && SharedPreferencesManager.HasKey(ExportActivity.this, Constants.NEW_EVENT_CALLED)) {
                            if (!SharedPreferencesManager.HasKey(ExportActivity.this, Constants.APP_RATED) && !SharedPreferencesManager.getSomeBoolValue(ExportActivity.this, Constants.APP_RATED)) {
                                Bundle bundle3 = new Bundle();
                                bundle3.putString("not_rate", "feedback_done");
                                FireBaseHelper.getInstance().LogEvent("Not_Rate", bundle3);
                            }
                            SharedPreferencesManager.setSomeBoolValue(ExportActivity.this, Constants.EXPORT_RATING_VIEW_LIMIT_REACHED, true);
                            return;
                        }
                        return;
                    }
                    try {
                        ExportActivity exportActivity = ExportActivity.this;
                        exportActivity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + ExportActivity.this.getPackageName())));
                    } catch (ActivityNotFoundException unused) {
                        ExportActivity exportActivity2 = ExportActivity.this;
                        exportActivity2.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + ExportActivity.this.getPackageName())));
                    }
                    if (!SharedPreferencesManager.HasKey(ExportActivity.this, Constants.NEW_EVENT_CALLED)) {
                        Bundle bundle4 = new Bundle();
                        bundle4.putString("User_Experience", "Export_Fragment-MarketOpen");
                        FireBaseHelper.getInstance().LogEvent("Rate_Analytics", bundle4);
                    } else {
                        Bundle bundle5 = new Bundle();
                        bundle5.putString("User_Experience", "Export_Fragment_S_T-MarketOpen");
                        FireBaseHelper.getInstance().LogEvent("Rate_Analytics", bundle5);
                    }
                    textView4.setText(ExportActivity.this.getString(R.string.thanks_text));
                    button5.setText(ExportActivity.this.getString(R.string.close));
                    button6.setEnabled(false);
                } else if (button5.getText().equals(ExportActivity.this.getString(R.string.close))) {
                    if (dialog4.isShowing()) {
                        dialog4.dismiss();
                        if (!Constants.REMOVE_ADS && AdUtil.getInstance().isSaveInterstitialAvailable() && Constants.SAVE_INTERSTITIAL && MainActivity.isActivityVisible()) {
                            AdUtil.getInstance().ShowSaveInterstitial();
                        }
                    }
                    if (ExportActivity.this.enjoyPencilPhotoSketch_2) {
                        SharedPreferencesManager.setSomeBoolValue(ExportActivity.this, Constants.APP_RATED, true);
                    } else if (ExportActivity.this.feedback_2) {
                        AppRater.initialize(ExportActivity.this).ResetIncrementEvent();
                    } else {
                        AppRater.initialize(ExportActivity.this).ResetIncrementEvent();
                    }
                }
            }
        };
        View.OnClickListener r15 = r02;
        button.setOnClickListener(r15);
        final Button button7 = button2;
        final TextView textView5 = textView2;
        final TextView textView6 = textView;
        final Button button8 = button;
        final Dialog dialog5 = dialog3;
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (button7.getText().equals(ExportActivity.this.closeButtonText)) {
                    textView5.setText(ExportActivity.this.getString(R.string.app_name));
                    textView6.setText(ExportActivity.this.getString(R.string.feedback_text));
                    button8.setText(ExportActivity.this.getString(R.string.ok_sure));
                    button7.setText(ExportActivity.this.getString(R.string.no_thanks));
                    ExportActivity.this.feedback_2 = true;
                } else if (button7.getText().equals(ExportActivity.this.closeButtonText2)) {
                    if (dialog5.isShowing()) {
                        dialog5.dismiss();
                        if (!Constants.REMOVE_ADS && AdUtil.getInstance().isSaveInterstitialAvailable() && Constants.SAVE_INTERSTITIAL && MainActivity.isActivityVisible()) {
                            AdUtil.getInstance().ShowSaveInterstitial();
                        }
                    }
                    if (ExportActivity.this.enjoyPencilPhotoSketch_2) {
                        SharedPreferencesManager.setSomeBoolValue(ExportActivity.this, Constants.APP_ENJOYED, true);
                    } else if (ExportActivity.this.feedback_2) {
                        AppRater.initialize(ExportActivity.this).ResetIncrementEvent();
                    }
                    AppRater.initialize(ExportActivity.this).ResetIncrementEvent();
                    if (SharedPreferencesManager.HasKey(ExportActivity.this, Constants.NEW_EVENT_CALLED)) {
                        if (!SharedPreferencesManager.HasKey(ExportActivity.this, Constants.APP_RATED) && !SharedPreferencesManager.getSomeBoolValue(ExportActivity.this, Constants.APP_RATED)) {
                            Bundle bundle = new Bundle();
                            bundle.putString("not_rate", "Nothing done");
                            FireBaseHelper.getInstance().LogEvent("Not_Rate", bundle);
                        }
                        SharedPreferencesManager.setSomeBoolValue(ExportActivity.this, Constants.EXPORT_RATING_VIEW_LIMIT_REACHED, true);
                    }
                }
            }
        });
        dialog3.show();
        Constants.SHOW_DIALOG_RATE_VIEW = false;
        Bundle bundle2 = new Bundle();
        bundle2.putString("User_Experience", "Export_Fragment-RateView_Show");
        FireBaseHelper.getInstance().LogEvent("Rate_Analytics", bundle2);
    }

    public int dpToPx(int i) {
        return Math.round(((float) i) * this.density);
    }
}
