package ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.facebook.ads.AdIconView;

import com.facebook.ads.NativeAd;

import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;

import com.mopub.mobileads.MoPubView;
import com.teamvinay.sketch.R;
import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.OnSeekChangeListener;
import com.warkiz.widget.SeekParams;
import helper.Constants;
import helper.StyleAdapter;
import java.util.ArrayList;
import java.util.List;
import listeners.ColorChangeListener;
import org.opencv.android.Utils;
import org.opencv.core.Mat;

/* renamed from: ui.ArtEffectFragment */
public class ArtEffectFragment extends Fragment implements View.OnClickListener, StyleAdapter.ClickListener, OnSeekChangeListener {
    RelativeLayout adCloseButton;
    RelativeLayout ad_container;
    RelativeLayout bannerLayout;
    Bitmap blendBitmap = null;
    Bitmap btmp;
    boolean clicked = false;
    ColorChangeListener colorChangeListener;
    Mat filtered;
    Mat finalmat;
    Bitmap image;
    ImageView imageView;
    Dialog loadingDialog;
    LinearLayoutManager mLayoutManager;
    List<Object> mRecyclerViewItems = new ArrayList();
    MoPubView moPubView;
    RelativeLayout nativeAdContainer;

    Mat original;
    RecyclerView recyclerView;
    IndicatorSeekBar seekbar;
    StyleAdapter styleAdapter;
    View view;

    public void isStylesLoaded() {
    }

    public void onRemoveEffect() {
    }

    public void onSeeking(SeekParams seekParams) {
    }

    public void onStartTrackingTouch(IndicatorSeekBar indicatorSeekBar) {
    }

    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        this.view = layoutInflater.inflate(R.layout.art_activity, viewGroup, false);
        return this.view;
    }

    public void onViewCreated(@NonNull View view2, @Nullable Bundle bundle) {
        super.onViewCreated(view2, bundle);
    }

    public void onStart() {
        super.onStart();
    }

    public void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
    }

    public void onStop() {
        super.onStop();
    }

    public void onDestroyView() {
        super.onDestroyView();
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public void onDetach() {
        super.onDetach();
    }

    public void onClick(View view2) {
        if (view2.getId() == R.id.image_view) {
            this.imageView.setVisibility(View.VISIBLE);
            this.imageView.setImageBitmap(this.btmp);
        }
    }

    public void onEffect(String str) {
        Log.d("DeepArt", str);
        this.imageView.setVisibility(4);
        this.ad_container.setVisibility(0);
        this.seekbar.setVisibility(4);
        new StyleTask(str).execute(new Void[0]);
    }

    /* access modifiers changed from: package-private */
    public void changeColor() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if (ArtEffectFragment.this.imageView.getVisibility() == View.VISIBLE) {
                    handler.removeCallbacks(this);
                } else {
                    handler.postDelayed(this, 100);
                }
            }
        }, 100);
    }

    public void onStopTrackingTouch(IndicatorSeekBar indicatorSeekBar) {
        Utils.bitmapToMat(this.image.copy(this.image.getConfig(), true), this.original);
        Utils.bitmapToMat(this.btmp.copy(this.btmp.getConfig(), true), this.filtered);
        this.blendBitmap = this.image.copy(this.image.getConfig(), true);
        this.blendBitmap = util.Utils.blendBitmaps(this.original, this.filtered, this.finalmat, this.blendBitmap, indicatorSeekBar.getProgress());
        this.imageView.setImageBitmap(this.blendBitmap);
    }

    /* renamed from: ui.ArtEffectFragment$StyleTask */
    public class StyleTask extends AsyncTask<Void, Void, Bitmap> {
        Bitmap bitmap;
        String styleId;
        String url;

        public StyleTask(String str) {
            this.styleId = str;
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            super.onPreExecute();
        }

        /* access modifiers changed from: protected */
        public Bitmap doInBackground(Void... voidArr) {
            Log.d("UploadImage", "upload");
            return null;
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Bitmap bitmap2) {
            super.onPostExecute(bitmap2);
            ArtEffectFragment.this.clicked = false;
        }
    }

    /*public void infilateAdmobNativeAd(final UnifiedNativeAd unifiedNativeAd) {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if (ArtEffectFragment.this.getActivity() != null && !Constants.REMOVE_ADS) {
                    handler.removeCallbacks(this);
                    View inflate = LayoutInflater.from(ArtEffectFragment.this.getActivity()).inflate(C3674R.C3678layout.admob_native_ad_layout, (ViewGroup) null);
                    ArtEffectFragment.this.populateUnifiedNativeAdView(unifiedNativeAd, (UnifiedNativeAdView) inflate.findViewById(C3674R.C3676id.admob_native));
                    ArtEffectFragment.this.nativeAdContainer.addView(inflate);
                }
            }
        }, 1500);
    }

    *//* access modifiers changed from: private *//*
    public void populateUnifiedNativeAdView(UnifiedNativeAd unifiedNativeAd, UnifiedNativeAdView unifiedNativeAdView) {
        if (unifiedNativeAd != null) {
            unifiedNativeAdView.setMediaView((MediaView) unifiedNativeAdView.findViewById(C3674R.C3676id.ad_media));
            unifiedNativeAdView.setHeadlineView(unifiedNativeAdView.findViewById(C3674R.C3676id.ad_headline));
            unifiedNativeAdView.setBodyView(unifiedNativeAdView.findViewById(C3674R.C3676id.ad_body));
            unifiedNativeAdView.setCallToActionView(unifiedNativeAdView.findViewById(C3674R.C3676id.ad_call_to_action));
            unifiedNativeAdView.setIconView(unifiedNativeAdView.findViewById(C3674R.C3676id.ad_app_icon));
            ((TextView) unifiedNativeAdView.getHeadlineView()).setText(unifiedNativeAd.getHeadline());
            if (unifiedNativeAd.getBody() == null) {
                unifiedNativeAdView.getBodyView().setVisibility(4);
            } else {
                unifiedNativeAdView.getBodyView().setVisibility(0);
                ((TextView) unifiedNativeAdView.getBodyView()).setText(unifiedNativeAd.getBody());
            }
            if (unifiedNativeAd.getCallToAction() == null) {
                unifiedNativeAdView.getCallToActionView().setVisibility(4);
            } else {
                unifiedNativeAdView.getCallToActionView().setVisibility(0);
                ((Button) unifiedNativeAdView.getCallToActionView()).setText(unifiedNativeAd.getCallToAction());
            }
            if (unifiedNativeAd.getIcon() == null) {
                unifiedNativeAdView.getIconView().setVisibility(8);
            } else {
                ((ImageView) unifiedNativeAdView.getIconView()).setImageDrawable(unifiedNativeAd.getIcon().getDrawable());
                unifiedNativeAdView.getIconView().setVisibility(0);
            }
            unifiedNativeAdView.setNativeAd(unifiedNativeAd);
        }
    }*/

  /*  private void inflateAd(NativeAd nativeAd) {
        nativeAd.unregisterView();
        int i = 0;
        RelativeLayout relativeLayout = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.main_screen_fb_native_ad_layout, this.nativeAdLayout, false);
        this.nativeAdLayout.addView(relativeLayout);
        LinearLayout linearLayout = (LinearLayout) relativeLayout.findViewById(C3674R.C3676id.ad_choices_container);
        AdOptionsView adOptionsView = new AdOptionsView(getActivity(), nativeAd, this.nativeAdLayout);
        linearLayout.removeAllViews();
        linearLayout.addView(adOptionsView, 0);
        AdIconView adIconView = (AdIconView) relativeLayout.findViewById(C3674R.C3676id.native_ad_icon);
        TextView textView = (TextView) relativeLayout.findViewById(C3674R.C3676id.native_ad_title);
        com.facebook.ads.MediaView mediaView = (com.facebook.ads.MediaView) relativeLayout.findViewById(C3674R.C3676id.native_ad_media);
        TextView textView2 = (TextView) relativeLayout.findViewById(C3674R.C3676id.native_ad_sponsored_label);
        Button button = (Button) relativeLayout.findViewById(C3674R.C3676id.native_ad_call_to_action);
        textView.setText(nativeAd.getAdvertiserName());
        ((TextView) relativeLayout.findViewById(C3674R.C3676id.native_ad_body)).setText(nativeAd.getAdBodyText());
        ((TextView) relativeLayout.findViewById(C3674R.C3676id.native_ad_social_context)).setText(nativeAd.getAdSocialContext());
        if (!nativeAd.hasCallToAction()) {
            i = 4;
        }
        button.setVisibility(i);
        button.setText(nativeAd.getAdCallToAction());
        textView2.setText(nativeAd.getSponsoredTranslation());
        ArrayList arrayList = new ArrayList();
        arrayList.add(textView);
        arrayList.add(button);
        nativeAd.registerViewForInteraction((View) relativeLayout, mediaView, (com.facebook.ads.MediaView) adIconView, (List<View>) arrayList);
    }*/
}