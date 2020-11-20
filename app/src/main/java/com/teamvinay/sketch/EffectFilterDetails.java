package com.teamvinay.sketch;

import java.util.ArrayList;
import java.util.List;
import util.BitmapCache;
import util.Effect;
import util.FilterValuesHolder;

public class EffectFilterDetails {
    private BitmapCache bitmapCache;
    private Boolean editorEffectSelected = false;
    private Boolean editorFilterSelected = false;
    private Effect effect;
    private String effectName = null;
    private boolean effectSelected = false;
    private boolean effecttofilter = false;
    private Filter filter;
    private String filterConstant = null;
    private String filterName;
    private boolean filterSelected = false;
    private String filterString = null;
    private FilterType filterType;
    private int filterValue;
    private FilterValuesHolder filterValuesHolder;
    private List<String> sequence = new ArrayList();

    public List<String> getSequence() {
        return this.sequence;
    }

    public void setSequence(List<String> list) {
        this.sequence = list;
    }

    public BitmapCache getBitmapCache() {
        return this.bitmapCache;
    }

    public void setBitmapCache(BitmapCache bitmapCache2) {
        this.bitmapCache = bitmapCache2;
    }

    public String getEffectName() {
        return this.effectName;
    }

    public void setEffectName(String str) {
        this.effectName = str;
    }

    public Effect getEffect() {
        return this.effect;
    }

    public void setEffect(Effect effect2) {
        this.effect = effect2;
    }

    public boolean isEffectSelected() {
        return this.effectSelected;
    }

    public void setEffectSelected(boolean z) {
        this.effectSelected = z;
    }

    public boolean isFilterSelected() {
        return this.filterSelected;
    }

    public void setFilterSelected(boolean z) {
        this.filterSelected = z;
    }

    public boolean isEffecttofilter() {
        return this.effecttofilter;
    }

    public void setEffecttofilter(boolean z) {
        this.effecttofilter = z;
    }

    public Filter getFilter() {
        return this.filter;
    }

    public void setFilter(Filter filter2) {
        this.filter = filter2;
    }

    public Boolean isEditorFilterSelected() {
        return this.editorFilterSelected;
    }

    public void setEditorFilterSelected(Boolean bool) {
        this.editorFilterSelected = bool;
    }

    public Boolean isEditorEffectSelected() {
        return this.editorEffectSelected;
    }

    public void setEditorEffectSelected(Boolean bool) {
        this.editorEffectSelected = bool;
    }

    public FilterType getFilterType() {
        return this.filterType;
    }

    public void setFilterType(FilterType filterType2) {
        this.filterType = filterType2;
    }

    public int getFilterValue() {
        return this.filterValue;
    }

    public void setFilterValue(int i) {
        this.filterValue = i;
    }

    public String getFilterName() {
        return this.filterName;
    }

    public void setFilterName(String str) {
        this.filterName = str;
    }

    public String getFilterString() {
        return this.filterString;
    }

    public void setFilterString(String str) {
        this.filterString = str;
    }

    public FilterValuesHolder getFilterValuesHolder() {
        return this.filterValuesHolder;
    }

    public void setFilterValuesHolder(FilterValuesHolder filterValuesHolder2) {
        this.filterValuesHolder = filterValuesHolder2;
    }

    public String getFilterConstant() {
        return this.filterConstant;
    }

    public void setFilterConstant(String str) {
        this.filterConstant = str;
    }
}
