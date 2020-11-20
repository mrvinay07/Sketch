package helper;

import com.teamvinay.sketch.FilterType;

public interface EditorButtonListener {
    void OnEnhanceClick(String str, FilterType filterType, int i, EnhanceFilters enhanceFilters);

    void OnEnhanceRemove();
}