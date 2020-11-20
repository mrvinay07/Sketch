package com.teamvinay.sketch;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;

import helper.Constants;
import java.util.List;
import listeners.AdLoadListener;
import listeners.RecyclerViewClickListener;

public class EffectsAdapter extends RecyclerView.Adapter implements AdLoadListener {
    int AD_TYPE = 1;
    int CONTENT_TYPE = 0;
    public boolean[] IsItemClicked;
    int MOPUB_VIEW = 2;
    private boolean adClicked = false;
    public Context context;
    public List<Object> effects;
    EffectViewHolder g_holde;
    int index = 26;
    /* access modifiers changed from: private */
    public int itemIndex = -1;
    /* access modifiers changed from: private */
    public RecyclerViewClickListener mClickListener;

    public void OnAdLoaded() {
    }

    public void setItemIndex(int i) {
    }

    public EffectsAdapter(Context context2, List<Object> list, RecyclerViewClickListener recyclerViewClickListener) {
        this.context = context2;
        this.effects = list;
        this.mClickListener = recyclerViewClickListener;
        this.IsItemClicked = new boolean[list.size()];
        initClickEffect();
    }

    public void initClickEffect() {
        for (int i = 0; i < this.IsItemClicked.length; i++) {
            this.IsItemClicked[i] = false;
        }
    }

    public void UncheckAll() {
        for (int i = 0; i < this.effects.size(); i++) {
            if (this.effects.get(i) instanceof PencilEffect) {
                PencilEffect pencilEffect = (PencilEffect) this.effects.get(i);
                if (pencilEffect.isChecked()) {
                    pencilEffect.setChecked(false);
                }
            }
        }
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == this.AD_TYPE) {
            return new AdViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.effect_button_ad, viewGroup, false));
        }
        if (i == this.MOPUB_VIEW) {
            return new MoPubAdViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.effect_button_mopub_ad, viewGroup, false));
        }
        return new EffectViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.effect_button_view, viewGroup, false));
    }

    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        int itemViewType = getItemViewType(i);
        if (itemViewType == this.CONTENT_TYPE) {
            PencilEffect pencilEffect = (PencilEffect) this.effects.get(i);
            EffectViewHolder effectViewHolder = (EffectViewHolder) viewHolder;
            effectViewHolder.effectPreview.setImageResource(pencilEffect.getResourceId());
            final PencilEffect pencilEffect2 = pencilEffect;
            final EffectViewHolder effectViewHolder2 = effectViewHolder;
            final int i2 = i;
            final RecyclerView.ViewHolder viewHolder2 = viewHolder;
            effectViewHolder.clickView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Log.d("Effect Size", String.valueOf(EffectsAdapter.this.effects.size()));
                    Log.d("Effect Size", String.valueOf(EffectsAdapter.this.effects.size()));
                    if (pencilEffect2.isChecked()) {
                        if (!Constants.CUSTOM_EDITING_SCREEN_ENABLED || Constants.EFFECT_BOTTOMVIEW_DEFAULT_COLOR == null) {
                            effectViewHolder2.selector.setBackgroundColor(Color.parseColor("#45484a"));
                        } else {
                            effectViewHolder2.selector.setBackgroundColor(Color.parseColor(Constants.EFFECT_BOTTOMVIEW_DEFAULT_COLOR));
                        }
                        pencilEffect2.setChecked(false);
                        if (EffectsAdapter.this.mClickListener != null) {
                            EffectsAdapter.this.mClickListener.onRemove();
                        }
                        Log.d("Item", "Checked  " + String.valueOf(i2));
                        return;
                    }
                    if (!Constants.CUSTOM_EDITING_SCREEN_ENABLED || Constants.EFFECT_BOTTOMVIEW_SELECT_COLOR == null) {
                        effectViewHolder2.selector.setBackgroundColor(Color.parseColor("#2196F3"));
                    } else {
                        effectViewHolder2.selector.setBackgroundColor(Color.parseColor(Constants.EFFECT_BOTTOMVIEW_SELECT_COLOR));
                    }
                    if (EffectsAdapter.this.itemIndex >= 0) {
                        PencilEffect pencilEffect = (PencilEffect) EffectsAdapter.this.effects.get(EffectsAdapter.this.itemIndex);
                        if (pencilEffect.isChecked()) {
                            pencilEffect.setChecked(false);
                        }
                    }
                    int unused = EffectsAdapter.this.itemIndex = i2;
                    if (EffectsAdapter.this.mClickListener != null) {
                        EffectsAdapter.this.mClickListener.onEffect(pencilEffect2.getEffectName(), viewHolder2.getAdapterPosition());
                    }
                    EffectsAdapter.this.notifyDataSetChanged();
                    Log.d("Item", "Not Checked  " + String.valueOf(i2));
                    pencilEffect2.setChecked(true);
                }
            });
            if (this.itemIndex != i || !pencilEffect.isChecked()) {
                if (!Constants.CUSTOM_EDITING_SCREEN_ENABLED || Constants.EFFECT_BOTTOMVIEW_DEFAULT_COLOR == null) {
                    effectViewHolder.selector.setBackgroundColor(Color.parseColor("#45484a"));
                } else {
                    effectViewHolder.selector.setBackgroundColor(Color.parseColor(Constants.EFFECT_BOTTOMVIEW_DEFAULT_COLOR));
                }
            } else if (!Constants.CUSTOM_EDITING_SCREEN_ENABLED || Constants.EFFECT_BOTTOMVIEW_SELECT_COLOR == null) {
                effectViewHolder.selector.setBackgroundColor(Color.parseColor("#2196F3"));
            } else {
                effectViewHolder.selector.setBackgroundColor(Color.parseColor(Constants.EFFECT_BOTTOMVIEW_SELECT_COLOR));
            }
        } else if (itemViewType == this.AD_TYPE) {
            populateNativeAdView((UnifiedNativeAd) this.effects.get(i), ((AdViewHolder) viewHolder).getAdView());
        } else if (itemViewType == this.MOPUB_VIEW) {
           // inflateMopubNative((NativeAd) this.effects.get(i), ((MoPubAdViewHolder) viewHolder).getAdView());
        }
    }

    public int getItemCount() {
        return this.effects.size();
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

    public class MoPubAdViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout mopubAdView;

        MoPubAdViewHolder(View view) {
            super(view);
            this.mopubAdView = (RelativeLayout) view.findViewById(R.id.mopub_ad_view);
        }

        public RelativeLayout getAdView() {
            return this.mopubAdView;
        }
    }

    public class EffectViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout adHover;
        public RelativeLayout clickView;
        public ImageView effectPreview;
        public RelativeLayout selector;

        public EffectViewHolder(View view) {
            super(view);
            this.effectPreview = (ImageView) view.findViewById(R.id.effect_preview);
            this.clickView = (RelativeLayout) view.findViewById(R.id.outer);
            this.selector = (RelativeLayout) view.findViewById(R.id.select_indicator);
            this.adHover = (RelativeLayout) view.findViewById(R.id.ad_hover);
        }
    }

    public int getItemViewType(int i) {
        Object obj = this.effects.get(i);
        if (obj instanceof UnifiedNativeAd) {
            return this.AD_TYPE;
        }
        if (!(obj instanceof NativeAd)) {
            return this.CONTENT_TYPE;
        }
        Log.d("ViewType", "unified");
        return this.MOPUB_VIEW;
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

    public void inflateMopubNative(com.mopub.nativeads.NativeAd nativeAd, View view) {
        nativeAd.renderAdView(view);
        nativeAd.prepare(view);
    }
}
