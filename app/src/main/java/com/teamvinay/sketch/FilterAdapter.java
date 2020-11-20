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

import java.util.List;

import helper.Constants;
import helper.FilterButtonListener;

public class FilterAdapter extends RecyclerView.Adapter {
    int AD_TYPE = 1;
    int CONTENT_TYPE = 0;
    int MOPUB_VIEW = 2;
    public Context context;
    public List<Object> effects;
    /* access modifiers changed from: private */
    public FilterButtonListener filterButtonListener;
    /* access modifiers changed from: private */
    public int itemIndex = -1;

    public FilterAdapter(Context context2, List<Object> list, int i, FilterButtonListener filterButtonListener2) {
        this.context = context2;
        this.effects = list;
        this.itemIndex = i;
        this.filterButtonListener = filterButtonListener2;
    }

    public void changeList(List<Object> list) {
        if (this.effects.size() > 0) {
            this.effects.clear();
            this.effects = list;
        }
    }

    public void initClickEffect() {
        for (int i = 0; i < this.effects.size(); i++) {
            if (this.effects.get(i) instanceof Filter) {
                ((Filter) this.effects.get(i)).setChecked(false);
            }
        }
        notifyDataSetChanged();
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

    public void UncheckAll() {
        for (int i = 0; i < this.effects.size(); i++) {
            if (this.effects.get(i) instanceof Filter) {
                ((Filter) this.effects.get(i)).setChecked(false);
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

    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int i) {
        int itemViewType = getItemViewType(i);
        if (itemViewType == this.CONTENT_TYPE) {
            final Filter filter = (Filter) this.effects.get(i);
            final EffectViewHolder effectViewHolder = (EffectViewHolder) viewHolder;
            effectViewHolder.effectPreview.setImageBitmap(filter.getBitmap());
            effectViewHolder.clickView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (filter.isChecked()) {
                        if (!Constants.CUSTOM_EDITING_SCREEN_ENABLED || Constants.EFFECT_BOTTOMVIEW_DEFAULT_COLOR == null) {
                            effectViewHolder.selector.setBackgroundColor(Color.parseColor("#45484a"));
                        } else {
                            effectViewHolder.selector.setBackgroundColor(Color.parseColor(Constants.EFFECT_BOTTOMVIEW_DEFAULT_COLOR));
                        }
                        filter.setChecked(false);
                        FilterAdapter.this.filterButtonListener.OnRemoveEFilter();
                        Log.d("Item", "Checked  " + String.valueOf(i));
                        return;
                    }
                    if (!Constants.CUSTOM_EDITING_SCREEN_ENABLED || Constants.EFFECT_BOTTOMVIEW_SELECT_COLOR == null) {
                        effectViewHolder.selector.setBackgroundColor(Color.parseColor("#2196F3"));
                    } else {
                        effectViewHolder.selector.setBackgroundColor(Color.parseColor(Constants.EFFECT_BOTTOMVIEW_SELECT_COLOR));
                    }
                    if (FilterAdapter.this.itemIndex >= 0) {
                        Filter filter = (Filter) FilterAdapter.this.effects.get(FilterAdapter.this.itemIndex);
                        if (filter.isChecked()) {
                            filter.setChecked(false);
                        }
                    }
                    int unused = FilterAdapter.this.itemIndex = i;
                    if (FilterAdapter.this.filterButtonListener != null) {
                        FilterAdapter.this.filterButtonListener.OnFilterClick(filter, i);
                    }
                    FilterAdapter.this.notifyDataSetChanged();
                    Log.d("Item", "Not Checked  " + String.valueOf(i));
                    filter.setChecked(true);
                }
            });
            if (this.itemIndex != i || !filter.isChecked()) {
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
            //inflateMopubNative((NativeAd) this.effects.get(i), ((MoPubAdViewHolder) viewHolder).getAdView());
        }
    }

    public int getItemCount() {
        return this.effects.size();
    }

    public class EffectViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout clickView;
        public ImageView effectPreview;
        public RelativeLayout selector;

        public EffectViewHolder(View view) {
            super(view);
            this.effectPreview = (ImageView) view.findViewById(R.id.effect_preview);
            this.clickView = (RelativeLayout) view.findViewById(R.id.outer);
            this.selector = (RelativeLayout) view.findViewById(R.id.select_indicator);
        }
    }

    public void setFilterButtonListener(FilterButtonListener filterButtonListener2) {
        this.filterButtonListener = filterButtonListener2;
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
