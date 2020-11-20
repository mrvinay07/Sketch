package util;

import java.util.HashMap;

public class FilterValuesHolder {
    HashMap<String, Object> meMap = new HashMap<>();

    public void addFilterwithValue(String str, Object obj) {
        this.meMap.put(str, obj);
    }

    public Object getFilterValue(String str) {
        return this.meMap.get(str);
    }

    public void clear() {
        if (!this.meMap.isEmpty()) {
            this.meMap.clear();
        }
    }

    public boolean hasKey(String str) {
        return this.meMap.containsKey(str);
    }
}
