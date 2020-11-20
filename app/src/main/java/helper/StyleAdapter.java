package helper;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;


import com.teamvinay.sketch.R;

import java.util.List;
import listeners.ColorChangeListener;

public class StyleAdapter extends RecyclerView.Adapter implements ColorChangeListener {
    public static final int AD_TYPE = 1;
    public static final int CONTENT_TYPE = 0;
    private static final String TAG = "StyleAdapter";
    public static final int UNIFIED_AD_TYPE = 2;
    public boolean[] IsItemClicked;
    private int indexPosition = 0;
    private int itemIndex = -1;
    private ClickListener mClickListener;
    private Context mContext;
    private List<Object> mRecylerViewItems;
    ViewHolder viewHolder;

    public interface ClickListener {
        void onEffect(String str);

        void onRemoveEffect();
    }

    public ColorChangeListener getColorChangeListener() {
        return this;
    }

    public void onColorChange() {
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView styleImage;
        public TextView title;

        public ViewHolder(View view) {
            super(view);
            this.styleImage = (ImageView) view.findViewById(R.id.effect_preview);
            this.title = (TextView) view.findViewById(R.id.title);
            view.setOnClickListener(this);
        }

        public void onClick(View view) {
            if (this.styleImage != null) {
            }
        }
    }

    public void initClickEffect() {
        for (int i = 0; i < this.IsItemClicked.length; i++) {
            this.IsItemClicked[i] = false;
        }
    }

    public StyleAdapter(Context context, List<Object> list, ClickListener clickListener) {
        this.mContext = context;
        this.mClickListener = clickListener;
        this.mRecylerViewItems = list;
        this.IsItemClicked = new boolean[list.size()];
        initClickEffect();
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == 1) {
            View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.native_ad_button_mopub, viewGroup, false);
            Log.d("ViewType", "AD_TYPE");
            return new AdViewHolder(inflate, i);
        } else if (i != 2) {
            return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_style, viewGroup, false));
        } else {
            View inflate2 = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.native_ad_button, viewGroup, false);
            Log.d("ViewType", "unified");
            return new UnifiedAdViewHolder(inflate2, i);
        }
    }

    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder2, int i) {
        int itemViewType = getItemViewType(i);
        if (itemViewType == 1) {
            Log.d("ViewType", "unified");
           // inflateMopubNative((NativeAd) this.mRecylerViewItems.get(i), ((AdViewHolder) viewHolder2).getMopubAdView());
        } else if (itemViewType == 2) {
            populateNativeAdView((UnifiedNativeAd) this.mRecylerViewItems.get(i), ((UnifiedAdViewHolder) viewHolder2).getAdView());
        }
    }

    public int getItemCount() {
        return this.mRecylerViewItems.size();
    }

    public int getItemViewType(int i) {
        Object obj = this.mRecylerViewItems.get(i);
        if (obj instanceof NativeAd) {
            return 1;
        }
        if (!(obj instanceof UnifiedNativeAd)) {
            return 0;
        }
        Log.d("ViewType", "unified");
        return 2;
    }

    public class AdViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout mopubAdView;

        AdViewHolder(View view, int i) {
            super(view);
            this.mopubAdView = (RelativeLayout) view.findViewById(R.id.mopub_ad_view);
        }

        public RelativeLayout getMopubAdView() {
            return this.mopubAdView;
        }
    }

    public class UnifiedAdViewHolder extends RecyclerView.ViewHolder {
        public UnifiedNativeAdView adView;

        UnifiedAdViewHolder(View view, int i) {
            super(view);
            this.adView = (UnifiedNativeAdView) view.findViewById(R.id.unified_ad_view);
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

    public void inflateMopubNative(com.mopub.nativeads.NativeAd nativeAd, View view) {
        nativeAd.renderAdView(view);
        nativeAd.prepare(view);
    }
}
