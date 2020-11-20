package helper;

public class ArtEffect {
    private String effectName;
    private boolean isChecked = false;
    private int resourceId;

    public ArtEffect(String str, int i) {
        this.effectName = str;
        this.resourceId = i;
    }

    public String getEffectName() {
        return this.effectName;
    }

    public int getResourceId() {
        return this.resourceId;
    }

    public boolean isChecked() {
        return this.isChecked;
    }

    public void setChecked(boolean z) {
        this.isChecked = z;
    }
}