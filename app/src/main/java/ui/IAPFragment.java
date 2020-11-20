package ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.SkuDetails;
import com.teamvinay.sketch.App;
import com.teamvinay.sketch.MainActivity;
import com.teamvinay.sketch.R;

import helper.Constants;
import helper.Credits;
import helper.IAPItemsAdapter;
import helper.IAPView;
import java.util.ArrayList;
import java.util.List;
import listeners.FragmentUpdateListener;
import listeners.PurchaseButtonListener;
import listeners.RewardAwardedListener;
import listeners.TaskCompleteListener;
import util.FireBaseHelper;

/* renamed from: ui.IAPFragment */
public class IAPFragment extends Fragment implements PurchaseButtonListener, FragmentUpdateListener, RewardAwardedListener, TaskCompleteListener {

    /* renamed from: bg */
    RelativeLayout f6046bg;

    /* renamed from: bp */
    BillingProcessor f6047bp;
    TextView creditText;
    RelativeLayout header;
    IAPItemsAdapter iapItemsAdapter;
    List<IAPView> iapViewList = new ArrayList();
    RecyclerView recyclerView;
    View view;

    public void OnRewardedVideoClosed() {
    }

    public void OnRewardedVideoLoaded() {
    }

    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        this.view = layoutInflater.inflate(R.layout.iap_screen, viewGroup, false);
        this.recyclerView = (RecyclerView) this.view.findViewById(R.id.my_recycler_view);
        this.f6046bg = (RelativeLayout) this.view.findViewById(R.id.background);
        this.header = (RelativeLayout) this.view.findViewById(R.id.credit_title);
        this.creditText = (TextView) this.view.findViewById(R.id.credit_text);
        TextView textView = this.creditText;
        textView.setText("Credits : " + String.valueOf(Credits.getCredits(getActivity())));
        this.f6047bp = ((MainActivity) getActivity()).getBp();
        ((MainActivity) getActivity()).setFragmentUpdateListener(this);
        if (Constants.SALE_IS_ON) {
            if (!(Constants.IAP_BACKGROUND_COLOR == null || Constants.IAP_HEADER_BACKGROUND_COLOR == null || Constants.IAP_HEADER_TEXT_COLOR == null)) {
                this.f6046bg.setBackgroundColor(Color.parseColor(Constants.IAP_BACKGROUND_COLOR));
                this.header.setBackgroundColor(Color.parseColor(Constants.IAP_HEADER_BACKGROUND_COLOR));
                this.creditText.setTextColor(Color.parseColor(Constants.IAP_HEADER_TEXT_COLOR));
            }
            SkuDetails sale_bronze = ((MainActivity) getActivity()).getSale_bronze();
            SkuDetails sale_silver = ((MainActivity) getActivity()).getSale_silver();
            SkuDetails sale_gold = ((MainActivity) getActivity()).getSale_gold();
            if (sale_bronze != null) {
                this.iapViewList.add(new IAPView("Bronze", "25 ", "INR 100.0", Constants.SALE_BRONZE_CREDIT_PACK, sale_bronze));
            } else {
                this.iapViewList.add(new IAPView("Bronze", "25 ", "INR 100.0", Constants.SALE_BRONZE_CREDIT_PACK, (SkuDetails) null));
            }
            if (sale_silver != null) {
                this.iapViewList.add(new IAPView("Silver", "50 ", "INR 150.0", Constants.SALE_SILVER_CREDIT_PACK, sale_silver));
            } else {
                this.iapViewList.add(new IAPView("Silver", "50 ", "INR 150.0", Constants.SALE_SILVER_CREDIT_PACK, (SkuDetails) null));
            }
            if (sale_gold != null) {
                this.iapViewList.add(new IAPView("Gold", "100 ", "INR 250.0", Constants.SALE_GOLD_CREDIT_PACK, sale_gold));
            } else {
                this.iapViewList.add(new IAPView("Gold", "100 ", "INR 250.0", Constants.SALE_GOLD_CREDIT_PACK, (SkuDetails) null));
            }
        } else {
            SkuDetails bronzeDetails = ((MainActivity) getActivity()).getBronzeDetails();
            SkuDetails silverDetails = ((MainActivity) getActivity()).getSilverDetails();
            SkuDetails goldDetails = ((MainActivity) getActivity()).getGoldDetails();
            if (bronzeDetails != null) {
                this.iapViewList.add(new IAPView("Bronze", "25 ", "INR 100.0", Constants.BRONZE_CREDIT_PACK, bronzeDetails));
            } else {
                this.iapViewList.add(new IAPView("Bronze", "25 ", "INR 100.0", Constants.BRONZE_CREDIT_PACK, (SkuDetails) null));
            }
            if (silverDetails != null) {
                this.iapViewList.add(new IAPView("Silver", "50 ", "INR 150.0", Constants.SILVER_CREDIT_PACK, silverDetails));
            } else {
                this.iapViewList.add(new IAPView("Silver", "50 ", "INR 150.0", Constants.SILVER_CREDIT_PACK, (SkuDetails) null));
            }
            if (goldDetails != null) {
                this.iapViewList.add(new IAPView("Gold", "100 ", "INR 250.0", Constants.GOLD_CREDIT_PACK, goldDetails));
            } else {
                this.iapViewList.add(new IAPView("Gold", "100 ", "INR 250.0", Constants.GOLD_CREDIT_PACK, (SkuDetails) null));
            }
        }
        this.iapViewList.add(new IAPView("Token", "For app related query send screenshot of token to support email.", "Generate Token", "Token", (SkuDetails) null));
        this.iapItemsAdapter = new IAPItemsAdapter(this.iapViewList);
        this.iapItemsAdapter.setBp(this.f6047bp);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        this.iapItemsAdapter.setPurchaseButtonListener(this);
        this.recyclerView.setAdapter(this.iapItemsAdapter);
        this.recyclerView.setLayoutManager(gridLayoutManager);
        Bundle bundle2 = new Bundle();
        bundle2.putString("Screen", "IAP Screen");
        FireBaseHelper.getInstance().LogEvent("Screen_Analytics", bundle2);
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
        this.view = null;
    }

    public void onDestroy() {
        ((MainActivity) getActivity()).UnlockMenu();
        super.onDestroy();
    }

    public void onDetach() {
        super.onDetach();
    }

    public void OnPurchaseButtonClick(IAPView iAPView) {
        if (!iAPView.getProductId().equals("VideoAd")) {
            if (!iAPView.getProductId().equals("Token")) {
                ((MainActivity) getActivity()).PurchaseProduct(iAPView.getProductId(), 0);
            } else if (!iAPView.getButton_text().equals("Generate")) {
                Intent intent = new Intent("android.intent.action.SEND");
                intent.putExtra("android.intent.extra.EMAIL", new String[]{"hqgames1080@gmail.com"});
                intent.putExtra("android.intent.extra.SUBJECT", "Pencil Photo Sketch");
                intent.putExtra("android.intent.extra.TEXT", "Token : " + Constants.FIREBASE_TOKEN);
                intent.setType("message/rfc822");
                startActivity(Intent.createChooser(intent, "Select Email Sending App :"));
            } else if (Constants.FIREBASE_TOKEN != null) {
                String str = Constants.FIREBASE_TOKEN;
                Log.d("Token", str);
                if (str != null) {
                    iAPView.setCreditamount(str);
                    iAPView.setButton_text("Send Token");
                    this.iapItemsAdapter.notifyDataSetChanged();
                    return;
                }
                ((MainActivity) getActivity()).ShowErrorToast("Sorry can't generate token, check your internet connection or try again later.");
            }
        }
    }

    public void OnFragmentUpdate() {
        if (getActivity() != null) {
            TextView textView = this.creditText;
            textView.setText("Credits : " + String.valueOf(Credits.getCredits(getActivity())));
        } else {
            TextView textView2 = this.creditText;
            textView2.setText("Credits : " + String.valueOf(Credits.getCredits(App.getInstance().getApplicationContext())));
        }
        this.iapItemsAdapter.notifyDataSetChanged();
        Log.d("MainActivityMethod", "Listener");
    }

    public void OnRewardAwarded(int i) {
        Credits.addCredits(getActivity(), i);
        ((MainActivity) getActivity()).UpdateLabelCreditAmount();
        TextView textView = this.creditText;
        textView.setText("Credits : " + String.valueOf(Credits.getCredits(getActivity())));
    }

    public void OnTaskComplete() {
        this.iapItemsAdapter.notifyDataSetChanged();
    }
}