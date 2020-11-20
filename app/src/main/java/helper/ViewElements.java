package helper;

public class ViewElements {
    public int imageId;
    private boolean isChecked = false;
    public String txt;

    public int getImageId() {
        return this.imageId;
    }

    public void setImageId(int i) {
        this.imageId = i;
    }

    public String getTxt() {
        return this.txt;
    }

    public void setTxt(String str) {
        this.txt = str;
    }

    public boolean isChecked() {
        return this.isChecked;
    }

    public void setChecked(boolean z) {
        this.isChecked = z;
    }

    public ViewElements(int i, String str) {
        this.imageId = i;
        this.txt = str;
    }
}
