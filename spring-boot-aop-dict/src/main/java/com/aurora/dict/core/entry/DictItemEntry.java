package com.aurora.dict.core.entry;

import java.util.LinkedHashMap;

/**
 * 字典项
 * @author xzbcode
 */
public class DictItemEntry extends LinkedHashMap<String, Object> {

    private DictItemEntry() {}

    private DictItemEntry(int initialCapacity) {
        super(initialCapacity);
    }

    public static DictItemEntry createEmpty() {
        return new DictItemEntry();
    }

    public static DictItemEntry create(int initialCapacity) {
        return new DictItemEntry(initialCapacity);
    }

    public DictItemEntry addItem(String itemKey, String itemValue) {
        this.put(itemKey, itemValue);
        return this;
    }

    public Object getItem(String itemKey) {
        return this.get(itemKey);
    }

    public String getItemStr(String itemKey) {
        Object itemVal = this.get(itemKey);
        if (itemVal==null || itemVal.getClass()!=String.class) {
            return null;
        }
        return String.valueOf(itemVal);
    }

    public void removeItem(String itemKey) {
        this.remove(itemKey);
    }

    public void clearAllItem() {
        this.clear();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
