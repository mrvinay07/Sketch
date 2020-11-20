package helper;

import com.teamvinay.sketch.FilterType;
import java.util.ArrayList;
import java.util.List;

public class EnhanceFilters {
    private String filterTitle = null;
    public int imageId;
    private boolean isChecked = false;
    private List<String> seekIds = new ArrayList();
    private List<String> seeks = new ArrayList();
    public String txt;
    private FilterType type;

    public String getFilterTitle() {
        return this.filterTitle;
    }

    public void setFilterTitle(String str) {
        this.filterTitle = str;
    }

    public List<String> getSeeks() {
        return this.seeks;
    }

    public void setSeeks(List<String> list) {
        this.seeks = list;
    }

    public List<String> getSeekIds() {
        return this.seekIds;
    }

    public void setSeekIds(List<String> list) {
        this.seekIds = list;
    }

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

    public FilterType getType() {
        return this.type;
    }

    public void setType(FilterType filterType) {
        this.type = filterType;
    }

    public EnhanceFilters(int i, String str, FilterType filterType) {
        this.imageId = i;
        this.txt = str;
        this.type = filterType;
    }

    public EnhanceFilters(int i, String str, FilterType filterType, String str2, List<String> list) {
        this.imageId = i;
        this.txt = str;
        this.type = filterType;
        this.seeks = list;
        this.filterTitle = str2;
    }
}
