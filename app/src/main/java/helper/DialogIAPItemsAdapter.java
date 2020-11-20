package helper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.SkuDetails;
import com.teamvinay.sketch.App;
import com.teamvinay.sketch.R;

import java.util.List;
import listeners.PurchaseButtonListener;

public class DialogIAPItemsAdapter extends RecyclerView.Adapter<DialogIAPItemsAdapter.MyViewHolder> {

    /* renamed from: bp */
    private BillingProcessor bp;
    private List<IAPView> iapViews;
    /* access modifiers changed from: private */
    public PurchaseButtonListener purchaseButtonListener;

    public BillingProcessor getBp() {
        return this.bp;
    }

    public void setBp(BillingProcessor billingProcessor) {
        this.bp = billingProcessor;
    }

    public PurchaseButtonListener getPurchaseButtonListener() {
        return this.purchaseButtonListener;
    }

    public void setPurchaseButtonListener(PurchaseButtonListener purchaseButtonListener2) {
        this.purchaseButtonListener = purchaseButtonListener2;
    }

    public DialogIAPItemsAdapter(List<IAPView> list) {
        this.iapViews = list;
    }

    @NonNull
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.iap_dialog_view, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        final IAPView iAPView = this.iapViews.get(i);
        if (iAPView.getTitle().equals("Free Credits") || iAPView.getTitle().equals("Token")) {
            myViewHolder.price.setText(iAPView.getPrice());
            myViewHolder.creditamount.setText(iAPView.getCreditamount());
            myViewHolder.extrafeature.setText("");
            if (iAPView.getTitle().equals("Free Credits")) {
                myViewHolder.button_text.setText("Play");
            } else {
                myViewHolder.button_text.setText("Generate");
                if (!iAPView.getCreditamount().equals("For app related query send screenshot of token to support email.")) {
                    myViewHolder.creditamount.setTextSize(1, 10.0f);
                }
            }
        } else {
            SkuDetails details = iAPView.getDetails();
            if (details != null) {
                TextView textView = myViewHolder.price;
                textView.setText(details.currency + " " + details.priceValue);
                iAPView.setCredits(Integer.parseInt(details.description));
            } else {
                myViewHolder.price.setText("Error");
            }
            if (details != null) {
                TextView textView2 = myViewHolder.creditamount;
                textView2.setText(details.description + " Credits");
                if (i == 1) {
                    myViewHolder.popularText.setVisibility(View.VISIBLE);
                }
            } else {
                myViewHolder.creditamount.setText("Cant Fetch details check your internet connection.");
            }
            if (Constants.REMOVE_ADS || myViewHolder.price.getText().equals("Error")) {
                myViewHolder.extrafeature.setText("");
            } else if (App.getInstance().getApplicationContext() != null) {
                TextView textView3 = myViewHolder.extrafeature;
                textView3.setText("+" + App.getInstance().getApplicationContext().getString(R.string.remove_ads));
            } else {
                myViewHolder.extrafeature.setText("+Remove Ads");
            }
        }
        myViewHolder.title_text.setText(iAPView.getTitle());
        myViewHolder.purchase_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (DialogIAPItemsAdapter.this.purchaseButtonListener != null) {
                    DialogIAPItemsAdapter.this.purchaseButtonListener.OnPurchaseButtonClick(iAPView);
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
            view.setOnClickListener(this);
        }
    }
}
