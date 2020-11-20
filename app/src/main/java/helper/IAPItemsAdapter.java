package helper;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.SkuDetails;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.teamvinay.sketch.App;
import com.teamvinay.sketch.R;

import java.util.List;
import listeners.PurchaseButtonListener;

public class IAPItemsAdapter extends RecyclerView.Adapter<IAPItemsAdapter.MyViewHolder> {

    /* renamed from: bp */
    private BillingProcessor f5586bp;
    private List<IAPView> iapViews;
    /* access modifiers changed from: private */
    public PurchaseButtonListener purchaseButtonListener;

    public BillingProcessor getBp() {
        return this.f5586bp;
    }

    public void setBp(BillingProcessor billingProcessor) {
        this.f5586bp = billingProcessor;
    }

    public PurchaseButtonListener getPurchaseButtonListener() {
        return this.purchaseButtonListener;
    }

    public void setPurchaseButtonListener(PurchaseButtonListener purchaseButtonListener2) {
        this.purchaseButtonListener = purchaseButtonListener2;
    }

    public IAPItemsAdapter(List<IAPView> list) {
        this.iapViews = list;
    }

    @NonNull
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view;
        if (Constants.SALE_IS_ON) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sale_iap_view, viewGroup, false);
        } else {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.iap_view, viewGroup, false);
        }
        return new MyViewHolder(view);
    }

    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        final IAPView iAPView = this.iapViews.get(i);
        if (iAPView.getTitle().equals("Free Credits") || iAPView.getTitle().equals("Token")) {
            if (Constants.SALE_IS_ON) {
                myViewHolder.originalPrice.setVisibility(View.GONE);
                myViewHolder.original_credit_amount.setVisibility(View.GONE);
            }
            myViewHolder.price.setText(iAPView.getPrice());
            myViewHolder.creditamount.setText(iAPView.getCreditamount());
            myViewHolder.extrafeature.setText("");
            if (iAPView.getTitle().equals("Free Credits")) {
                myViewHolder.button_text.setText("Play");
            } else {
                myViewHolder.button_text.setText(iAPView.button_text);
                if (!iAPView.getCreditamount().equals("For app related query send screenshot of token to support email.")) {
                    myViewHolder.creditamount.setTextSize(1, 10.0f);
                }
            }
        } else {
            SkuDetails details = iAPView.getDetails();
            if (details != null) {
                if (!(!Constants.SALE_IS_ON || Constants.GOLD_PRICE_VALUE == FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE || Constants.SILVER_PRICE_VALUE == FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE || Constants.BRONZE_PRICE_VALUE == FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE)) {
                    if (details.productId.equals(Constants.SALE_BRONZE_CREDIT_PACK)) {
                        TextView textView = myViewHolder.originalPrice;
                        textView.setText(details.currency + " " + String.valueOf(Constants.BRONZE_PRICE_VALUE));
                        if (Constants.BRONZE_PACK_SIZE != null) {
                            TextView textView2 = myViewHolder.original_credit_amount;
                            textView2.setText(String.valueOf(Constants.BRONZE_PACK_SIZE) + " Credits");
                        }
                    } else if (details.productId.equals(Constants.SALE_SILVER_CREDIT_PACK)) {
                        TextView textView3 = myViewHolder.originalPrice;
                        textView3.setText(details.currency + " " + String.valueOf(Constants.SILVER_PRICE_VALUE));
                        if (Constants.SILVER_PACK_SIZE != null) {
                            TextView textView4 = myViewHolder.original_credit_amount;
                            textView4.setText(String.valueOf(Constants.SILVER_PACK_SIZE) + " Credits");
                        }
                    } else if (details.productId.equals(Constants.SALE_GOLD_CREDIT_PACK)) {
                        TextView textView5 = myViewHolder.originalPrice;
                        textView5.setText(details.currency + " " + String.valueOf(Constants.GOLD_PRICE_VALUE));
                        if (Constants.GOLD_PACK_SIZE != null) {
                            TextView textView6 = myViewHolder.original_credit_amount;
                            textView6.setText(String.valueOf(Constants.GOLD_PACK_SIZE) + " Credits");
                        }
                    }
                }
                TextView textView7 = myViewHolder.price;
                textView7.setText(details.currency + " " + details.priceValue);
                iAPView.setCredits(Integer.parseInt(details.description));
            } else {
                myViewHolder.price.setText("Error");
            }
            if (details != null) {
                TextView textView8 = myViewHolder.creditamount;
                textView8.setText(details.description + " Credits");
                if (i == 1 && !Constants.SALE_IS_ON) {
                    myViewHolder.popularText.setVisibility(View.VISIBLE);
                }
            } else {
                myViewHolder.creditamount.setText("Cant Fetch details from play store Either your internet connection is not stable or play store is not installed on your device.");
            }
            if (Constants.REMOVE_ADS || myViewHolder.price.getText().equals("Error")) {
                myViewHolder.extrafeature.setText("");
            } else if (App.instance.getApplicationContext() != null) {
                TextView textView9 = myViewHolder.extrafeature;
                textView9.setText("+" + App.instance.getApplicationContext().getString(R.string.remove_ads));
            } else {
                myViewHolder.extrafeature.setText("+Remove Ads");
            }
        }
        myViewHolder.title_text.setText(iAPView.getTitle());
        myViewHolder.purchase_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (IAPItemsAdapter.this.purchaseButtonListener != null) {
                    IAPItemsAdapter.this.purchaseButtonListener.OnPurchaseButtonClick(iAPView);
                }
            }
        });
    }

    public int getItemCount() {
        return this.iapViews.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView button_text;
        TextView creditamount;
        TextView extrafeature;
        TextView originalPrice;
        TextView original_credit_amount;
        TextView popularText;
        TextView price;
        RelativeLayout purchase_button;
        TextView title_text;

        public void onClick(View view) {
        }

        public MyViewHolder(View view) {
            super(view);
            this.title_text = (TextView) view.findViewById(R.id.title_text);
            this.creditamount = (TextView) view.findViewById(R.id.credit_amount);
            this.extrafeature = (TextView) view.findViewById(R.id.remove_ads);
            this.price = (TextView) view.findViewById(R.id.price);
            this.button_text = (TextView) view.findViewById(R.id.button_text);
            this.purchase_button = (RelativeLayout) view.findViewById(R.id.purchase_button);
            this.popularText = (TextView) view.findViewById(R.id.popular_text);
            if (Constants.SALE_IS_ON) {
                RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.background);
                this.originalPrice = (TextView) view.findViewById(R.id.original_price);
                this.original_credit_amount = (TextView) view.findViewById(R.id.original_credit_amount);
                this.originalPrice.setTypeface((Typeface) null);
                IAPItemsAdapter.this.strikeThroughText(this.originalPrice);
                IAPItemsAdapter.this.strikeThroughText(this.original_credit_amount);
                if (!(Constants.IAP_VIEW_BACKGROUND_COLOR == null || Constants.IAP_VIEW_BUTTON_COLOR == null || Constants.IAP_VIEW_TEXT_COLOR == null)) {
                    GradientDrawable gradientDrawable = new GradientDrawable();
                    gradientDrawable.setColor(Color.parseColor(Constants.IAP_VIEW_BACKGROUND_COLOR));
                    relativeLayout.setBackground(gradientDrawable);
                    this.purchase_button.setBackgroundColor(Color.parseColor(Constants.IAP_VIEW_BUTTON_COLOR));
                    this.button_text.setTextColor(Color.parseColor(Constants.IAP_VIEW_TEXT_COLOR));
                    this.title_text.setTextColor(Color.parseColor(Constants.IAP_VIEW_TEXT_COLOR));
                    this.creditamount.setTextColor(Color.parseColor(Constants.IAP_VIEW_TEXT_COLOR));
                    this.extrafeature.setTextColor(Color.parseColor(Constants.IAP_VIEW_TEXT_COLOR));
                    this.price.setTextColor(Color.parseColor(Constants.IAP_VIEW_TEXT_COLOR));
                }
            }
            view.setOnClickListener(this);
        }
    }

    /* access modifiers changed from: private */
    public void strikeThroughText(TextView textView) {
        textView.setPaintFlags(textView.getPaintFlags() | 16);
    }
}
