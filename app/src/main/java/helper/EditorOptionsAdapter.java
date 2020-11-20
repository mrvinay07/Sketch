package helper;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.teamvinay.sketch.R;

import java.util.List;

public class EditorOptionsAdapter extends RecyclerView.Adapter {
    int AD_TYPE = 1;
    int CONTENT_TYPE = 0;
    int MOPUB_VIEW = 2;
    /* access modifiers changed from: private */
    public EditorButtonListener editorbuttonlistener;
    /* access modifiers changed from: private */
    public List<Object> enhanceFiltersList;
    /* access modifiers changed from: private */
    public int itemIndex = -1;
    /* access modifiers changed from: private */
    public Context mContext;

    public int getItemIndex() {
        return this.itemIndex;
    }

    public void setItemIndex(int i) {
        this.itemIndex = i;
    }

    public void reset() {
        if (this.itemIndex >= 0) {
            ((EnhanceFilters) this.enhanceFiltersList.get(this.itemIndex)).setChecked(false);
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout background;
        ImageView imageView;
        LinearLayout layout;
        TextView textView;

        public MyViewHolder(View view) {
            super(view);
            this.textView = (TextView) view.findViewById(R.id.view_text);
            this.imageView = (ImageView) view.findViewById(R.id.view_image);
            this.layout = (LinearLayout) view.findViewById(R.id.click_view);
            this.background = (LinearLayout) view.findViewById(R.id.layout);
        }
    }

    public void UncheckAll() {
        for (int i = 0; i < this.enhanceFiltersList.size(); i++) {
            if (this.enhanceFiltersList.get(i) instanceof EnhanceFilters) {
                EnhanceFilters enhanceFilters = (EnhanceFilters) this.enhanceFiltersList.get(i);
                if (enhanceFilters.isChecked()) {
                    enhanceFilters.setChecked(false);
                }
            }
        }
    }

    public EditorOptionsAdapter(Context context, List<Object> list, EditorButtonListener editorButtonListener) {
        this.enhanceFiltersList = list;
        this.mContext = context;
        this.editorbuttonlistener = editorButtonListener;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == this.AD_TYPE) {
            return new AdViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.editor_button_ad, viewGroup, false));
        }
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.editor_items_view, viewGroup, false));
    }

    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int i) {
        int itemViewType = getItemViewType(i);
        if (itemViewType == this.CONTENT_TYPE) {
            final EnhanceFilters enhanceFilters = (EnhanceFilters) this.enhanceFiltersList.get(i);
            final MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
            myViewHolder.imageView.setImageResource(enhanceFilters.imageId);
            myViewHolder.textView.setText(enhanceFilters.txt);
            myViewHolder.layout.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (enhanceFilters.isChecked()) {
                        if (!Constants.CUSTOM_EDITING_SCREEN_ENABLED || Constants.ENHANCE_BUTTON_VIEW_DEFAULT_COLOR == null) {
                            myViewHolder.imageView.setColorFilter(ContextCompat.getColor(EditorOptionsAdapter.this.mContext, R.color.colorDisable), PorterDuff.Mode.SRC_IN);
                            myViewHolder.textView.setTextColor(Color.parseColor("#666666"));
                        } else {
                            myViewHolder.imageView.setColorFilter(Color.parseColor(Constants.ENHANCE_BUTTON_VIEW_DEFAULT_COLOR), PorterDuff.Mode.SRC_IN);
                            myViewHolder.textView.setTextColor(Color.parseColor(Constants.ENHANCE_BUTTON_VIEW_DEFAULT_COLOR));
                        }
                        enhanceFilters.setChecked(false);
                        EditorOptionsAdapter.this.editorbuttonlistener.OnEnhanceRemove();
                        Log.d("Item", "Checked  " + String.valueOf(i));
                        return;
                    }
                    if (!Constants.CUSTOM_EDITING_SCREEN_ENABLED || Constants.ENHANCE_BUTTON_VIEW_SELECT_COLOR == null) {
                        myViewHolder.imageView.setColorFilter(ContextCompat.getColor(EditorOptionsAdapter.this.mContext,R.color.colorAction), PorterDuff.Mode.SRC_IN);
                        myViewHolder.textView.setTextColor(Color.parseColor("#E71D36"));
                    } else {
                        myViewHolder.imageView.setColorFilter(Color.parseColor(Constants.ENHANCE_BUTTON_VIEW_SELECT_COLOR), PorterDuff.Mode.SRC_IN);
                        myViewHolder.textView.setTextColor(Color.parseColor(Constants.ENHANCE_BUTTON_VIEW_SELECT_COLOR));
                    }
                    if (EditorOptionsAdapter.this.itemIndex >= 0) {
                        EnhanceFilters enhanceFilters = (EnhanceFilters) EditorOptionsAdapter.this.enhanceFiltersList.get(EditorOptionsAdapter.this.itemIndex);
                        if (enhanceFilters.isChecked()) {
                            enhanceFilters.setChecked(false);
                        }
                    }
                    int unused = EditorOptionsAdapter.this.itemIndex = i;
                    if (EditorOptionsAdapter.this.editorbuttonlistener != null) {
                        EditorOptionsAdapter.this.editorbuttonlistener.OnEnhanceClick(enhanceFilters.getTxt(), enhanceFilters.getType(), i, enhanceFilters);
                    }
                    EditorOptionsAdapter.this.notifyDataSetChanged();
                    Log.d("Item", "Not Checked  " + String.valueOf(i));
                    enhanceFilters.setChecked(true);
                }
            });
            if (this.itemIndex != i || !enhanceFilters.isChecked()) {
                if (!Constants.CUSTOM_EDITING_SCREEN_ENABLED || Constants.ENHANCE_BUTTON_VIEW_DEFAULT_COLOR == null) {
                    myViewHolder.imageView.setColorFilter(ContextCompat.getColor(this.mContext, R.color.colorDisable), PorterDuff.Mode.SRC_IN);
                    myViewHolder.textView.setTextColor(Color.parseColor("#666666"));
                    return;
                }
                myViewHolder.imageView.setColorFilter(Color.parseColor(Constants.ENHANCE_BUTTON_VIEW_DEFAULT_COLOR), PorterDuff.Mode.SRC_IN);
                myViewHolder.textView.setTextColor(Color.parseColor(Constants.ENHANCE_BUTTON_VIEW_DEFAULT_COLOR));
            } else if (!Constants.CUSTOM_EDITING_SCREEN_ENABLED || Constants.ENHANCE_BUTTON_VIEW_SELECT_COLOR == null) {
                myViewHolder.imageView.setColorFilter(ContextCompat.getColor(this.mContext, R.color.colorAction), PorterDuff.Mode.SRC_IN);
                myViewHolder.textView.setTextColor(Color.parseColor("#E71D36"));
            } else {
                myViewHolder.imageView.setColorFilter(Color.parseColor(Constants.ENHANCE_BUTTON_VIEW_SELECT_COLOR), PorterDuff.Mode.SRC_IN);
                myViewHolder.textView.setTextColor(Color.parseColor(Constants.ENHANCE_BUTTON_VIEW_SELECT_COLOR));
            }
        } else if (itemViewType == this.AD_TYPE) {
            populateNativeAdView((UnifiedNativeAd) this.enhanceFiltersList.get(i), ((AdViewHolder) viewHolder).getAdView());
        }
    }

    public int getItemViewType(int i) {
        Object obj = this.enhanceFiltersList.get(i);
        if (obj instanceof UnifiedNativeAd) {
            return this.AD_TYPE;
        }
        if (!(obj instanceof NativeAd)) {
            return this.CONTENT_TYPE;
        }
        Log.d("ViewType", "unified");
        return this.MOPUB_VIEW;
    }

    public int getItemCount() {
        return this.enhanceFiltersList.size();
    }

    public void setEditorButtonListener(EditorButtonListener editorButtonListener) {
        this.editorbuttonlistener = editorButtonListener;
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