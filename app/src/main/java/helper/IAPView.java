package helper;

import com.anjlab.android.iab.v3.SkuDetails;

public class IAPView {
    private int Credits;
    public String button_text = "Generate";
    private String creditamount;
    private SkuDetails details;
    private String extraFeature;
    private String price;
    private String productId;
    private String title;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getCreditamount() {
        return this.creditamount;
    }

    public void setCreditamount(String str) {
        this.creditamount = str;
    }

    public String getExtraFeature() {
        return this.extraFeature;
    }

    public void setExtraFeature(String str) {
        this.extraFeature = str;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String str) {
        this.price = str;
    }

    public String getProductId() {
        return this.productId;
    }

    public void setProductId(String str) {
        this.productId = str;
    }

    public SkuDetails getDetails() {
        return this.details;
    }

    public void setDetails(SkuDetails skuDetails) {
        this.details = skuDetails;
    }

    public int getCredits() {
        return this.Credits;
    }

    public void setCredits(int i) {
        this.Credits = i;
    }

    public String getButton_text() {
        return this.button_text;
    }

    public void setButton_text(String str) {
        this.button_text = str;
    }

    public IAPView(String str, String str2, String str3, String str4, SkuDetails skuDetails) {
        this.title = str;
        this.creditamount = str2;
        this.price = str3;
        this.extraFeature = "+Remove Ads";
        this.productId = str4;
        this.details = skuDetails;
    }
}