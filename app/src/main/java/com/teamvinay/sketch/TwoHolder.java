package com.teamvinay.sketch;

import util.EditorFilter;

public class TwoHolder {
    private EditorFilter filter;
    private FilterType filterType;

    public EditorFilter getFilter() {
        return this.filter;
    }

    public void setFilter(EditorFilter editorFilter) {
        this.filter = editorFilter;
    }

    public FilterType getFilterType() {
        return this.filterType;
    }

    public void setFilterType(FilterType filterType2) {
        this.filterType = filterType2;
    }

    public TwoHolder(EditorFilter editorFilter, FilterType filterType2) {
        this.filter = editorFilter;
        this.filterType = filterType2;
    }
}
