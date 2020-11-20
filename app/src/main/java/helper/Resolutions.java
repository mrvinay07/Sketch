package helper;


import com.teamvinay.sketch.R;

public class Resolutions {
    private int imageId;
    public ImageResolutions imageResolutions;
    private boolean isLocked = false;
    private String resolution = null;

    public String getResolution() {
        return this.resolution;
    }

    public void setResolution(String str) {
        this.resolution = str;
    }

    public int getImageId() {
        return this.imageId;
    }

    public void setImageId(int i) {
        this.imageId = i;
    }

    public boolean isLocked() {
        return this.isLocked;
    }

    public void setLocked(boolean z) {
        this.isLocked = z;
    }

    public Resolutions(String str, boolean z) {
        this.resolution = str;
        this.isLocked = z;
        this.imageId = R.drawable.ic_lock_outline_black_24dp;
    }
}