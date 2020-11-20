package util;

import com.teamvinay.sketch.FilterHolder;
import java.util.ArrayList;
import java.util.List;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter;

public class EditingFragmentDetails {
    private Effect effect = null;
    private String effectName = null;
    private int effectPosition;
    private GPUImageFilter filter;
    private String filterConstant = null;
    private FilterHolder filterHolder = null;
    private int filterPosition;
    private boolean isEditorButtonChecked = false;
    private boolean isEditorSelected = false;
    private boolean isEffectButtonChecked = false;
    private boolean isEffectSelected = false;
    private boolean isFilterButtonChecked = false;
    private boolean isFilterSelected = false;
    private List<String> seq;

    public FilterHolder getFilterHolder() {
        return this.filterHolder;
    }

    public void setFilterHolder(FilterHolder filterHolder2) {
        this.filterHolder = filterHolder2;
    }

    public boolean isEffectSelected() {
        return this.isEffectSelected;
    }

    public void setEffectSelected(boolean z) {
        this.isEffectSelected = z;
    }

    public int getEffectPosition() {
        return this.effectPosition;
    }

    public void setEffectPosition(int i) {
        this.effectPosition = i;
    }

    public boolean isFilterSelected() {
        return this.isFilterSelected;
    }

    public void setFilterSelected(boolean z) {
        this.isFilterSelected = z;
    }

    public int getFilterPosition() {
        return this.filterPosition;
    }

    public void setFilterPosition(int i) {
        this.filterPosition = i;
    }

    public String getEffectName() {
        return this.effectName;
    }

    public void setEffectName(String str) {
        this.effectName = str;
    }

    public boolean isEditorSelected() {
        return this.isEditorSelected;
    }

    public void setEditorSelected(boolean z) {
        this.isEditorSelected = z;
    }

    public boolean isEffectButtonChecked() {
        return this.isEffectButtonChecked;
    }

    public void setEffectButtonChecked(boolean z) {
        this.isEffectButtonChecked = z;
    }

    public boolean isFilterButtonChecked() {
        return this.isFilterButtonChecked;
    }

    public void setFilterButtonChecked(boolean z) {
        this.isFilterButtonChecked = z;
    }

    public boolean isEditorButtonChecked() {
        return this.isEditorButtonChecked;
    }

    public void setEditorButtonChecked(boolean z) {
        this.isEditorButtonChecked = z;
    }

    public GPUImageFilter getFilter() {
        return this.filter;
    }

    public void setFilter(GPUImageFilter gPUImageFilter) {
        this.filter = gPUImageFilter;
    }

    public void setFilterConstant(String str) {
        this.filterConstant = str;
    }

    public String getFilterConstant() {
        return this.filterConstant;
    }

    public Effect getEffect() {
        return this.effect;
    }

    public void setEffect(Effect effect2) {
        this.effect = effect2;
    }

    public List<String> getSeq() {
        return this.seq;
    }

    public void setSeq(List<String> list) {
        this.seq = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            this.seq.add(list.get(i));
        }
    }
}
