package helper;


import com.teamvinay.sketch.R;

public class ExportOptionsView {
    private boolean Locked = true;
    private String credits;
    private int creditsamount = 0;
    private int imgId;
    private int lockImage;
    private String optionName;
    private String title;

    public String getOptionName() {
        return this.optionName;
    }

    public void setOptionName(String str) {
        this.optionName = str;
    }

    public int getCreditsamount() {
        return this.creditsamount;
    }

    public void setCreditsamount(int i) {
        this.creditsamount = i;
    }

    public boolean isLocked() {
        return this.Locked;
    }

    public void setLocked(boolean z) {
        this.Locked = z;
    }

    public int getLockImage() {
        return this.lockImage;
    }

    public void setLockImage(int i) {
        this.lockImage = i;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getCredits() {
        return this.credits;
    }

    public void setCredits(String str) {
        this.credits = str;
    }

    public int getImgId() {
        return this.imgId;
    }

    public void setImgId(int i) {
        this.imgId = i;
    }

    public ExportOptionsView(String str, String str2, int i, String str3) {
        this.title = str;
        this.credits = str2;
        this.imgId = i;
        this.lockImage = R.drawable.ic_lock_black_24dp;
        this.Locked = true;
        this.optionName = str3;
    }
}
